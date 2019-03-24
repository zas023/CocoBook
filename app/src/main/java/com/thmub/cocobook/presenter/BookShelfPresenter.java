package com.thmub.cocobook.presenter;

import com.thmub.cocobook.manager.RxBusManager;
import com.thmub.cocobook.model.bean.BookChapterBean;
import com.thmub.cocobook.model.bean.BookDetailBean;
import com.thmub.cocobook.model.bean.BookUpdateBean;
import com.thmub.cocobook.model.bean.CollBookBean;
import com.thmub.cocobook.model.bean.DownloadTaskBean;
import com.thmub.cocobook.model.local.BookRepository;
import com.thmub.cocobook.model.server.RemoteRepository;
import com.thmub.cocobook.presenter.contract.BookShelfContract;
import com.thmub.cocobook.base.RxPresenter;
import com.thmub.cocobook.utils.Constant;
import com.thmub.cocobook.utils.LogUtils;
import com.thmub.cocobook.utils.MD5Utils;
import com.thmub.cocobook.utils.RxUtils;
import com.thmub.cocobook.utils.StringUtils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import io.reactivex.Single;

/**
 * Created by zhouas666 on 18-2-8.
 * 书架presenter
 */

public class BookShelfPresenter extends RxPresenter<BookShelfContract.View>
        implements BookShelfContract.Presenter {
    private static final String TAG = "BookShelfPresenter";

    @Override
    public void refreshCollBooks() {
        List<CollBookBean> collBooks = BookRepository.getInstance().getCollBooks();
        mView.finishRefresh(collBooks);
    }

    @Override
    public void createDownloadTask(CollBookBean collBookBean) {
        DownloadTaskBean task = new DownloadTaskBean();
        task.setTaskName(collBookBean.getTitle());
        task.setBookId(collBookBean.get_id());
        task.setBookChapters(collBookBean.getBookChapters());
        task.setLastChapter(collBookBean.getBookChapters().size());
        //下载书籍
        RxBusManager.getInstance().post(task);
    }


    @Override
    public void loadRecommendBooks(String gender) {
        addDisposable(RemoteRepository.getInstance()
                .getRecommendBooksByGender(gender)
                .doOnSuccess((collBooks) -> {
                    //更新目录
                    updateCategory(collBooks);
                    //异步存储到数据库中
                    BookRepository.getInstance().saveCollBooksWithAsync(collBooks);
                })
                .compose(RxUtils::toSimpleSingle)
                .subscribe(
                        beans -> {
                            mView.finishRefresh(beans);
                            mView.complete();
                        },
                        e -> {
                            //提示没有网络
                            LogUtils.e(e);
                            mView.showErrorTip(e.toString());
                            mView.complete();
                        }
                ));
    }

    @Override
    public void updateCollBooks(List<CollBookBean> collBookBeans) {
        if (collBookBeans == null || collBookBeans.isEmpty()) return;

        List<CollBookBean> collBooks = new ArrayList<>(collBookBeans);
        String id = "";
        //剔除本地文件书籍，因为不需要更新
        for (int i = 0, n = collBooks.size(); i < n; i++) {
            CollBookBean collBook = collBooks.get(i);
            if (collBook.isLocal()) {
                collBooks.remove(i);
            } else {
                id = id + collBook.get_id();
            }
            //最后一个不需要分隔符
            if (i < n - 1)
                id += ",";
        }

        addDisposable(RemoteRepository.getInstance()
                .getBookUpdateList(id)
                .compose(RxUtils::toSimpleSingle)
                .subscribe((beans) -> {
                            int size = beans.size();
                            //List<CollBookBean> newCollBooks = new ArrayList<>(size);
                            for (int i = 0; i < size; ++i) {
                                CollBookBean collBook = collBooks.get(i);
                                //如果是collBook是update状态，或者bookUpdate与oldBook章节数不同
                                if (collBook.isUpdate() || !collBook.getLastChapter().equals(beans.get(i).getLastChapter())) {
                                    collBook.setUpdate(true);
                                    collBook.setLastChapter(beans.get(i).getLastChapter());
                                } else {
                                    collBook.setUpdate(false);
                                }
                            }
                            //存储到数据库中
                            BookRepository.getInstance().saveCollBooks(collBooks);
                            //因为切换夜间模式会调用onCreate()
                            if (mView != null) {
                                mView.finishUpdate();
                                mView.complete();
                            }
                        }
                        ,
                        (e) -> {
                            //提示没有网络
                            mView.showErrorTip(e.toString());
                            mView.complete();
                            LogUtils.e(e);
                        }));
    }

    //更新每个CollBook的目录
    private void updateCategory(List<CollBookBean> collBookBeans) {
        List<Single<List<BookChapterBean>>> observables = new ArrayList<>(collBookBeans.size());
        for (CollBookBean bean : collBookBeans) {
            observables.add(RemoteRepository.getInstance().getBookChapters(bean.get_id()));
        }
        Iterator<CollBookBean> it = collBookBeans.iterator();
        //执行在上一个方法中的子线程中
        //连接多个Single和Observable发射的数据
        Single.concat(observables)
                .subscribe(
                        chapterList -> {
                            for (BookChapterBean bean : chapterList) {
                                bean.setId(MD5Utils.strToMd5By16(bean.getLink()));
                            }

                            CollBookBean bean = it.next();
                            bean.setLastRead(StringUtils.dateConvert(System.currentTimeMillis(), Constant.FORMAT_BOOK_DATE));
                            bean.setBookChapters(chapterList);
                        }
                );
    }
}
