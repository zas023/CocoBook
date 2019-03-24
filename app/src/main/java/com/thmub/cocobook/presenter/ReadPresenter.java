package com.thmub.cocobook.presenter;

import com.thmub.cocobook.manager.RxBusManager;
import com.thmub.cocobook.model.bean.BookChapterBean;
import com.thmub.cocobook.model.bean.ChapterInfoBean;
import com.thmub.cocobook.model.bean.CollBookBean;
import com.thmub.cocobook.manager.BookManager;
import com.thmub.cocobook.model.event.DownloadEvent;
import com.thmub.cocobook.model.local.BookRepository;
import com.thmub.cocobook.model.server.RemoteRepository;
import com.thmub.cocobook.presenter.contract.ReadContract;
import com.thmub.cocobook.base.RxPresenter;
import com.thmub.cocobook.utils.LogUtils;
import com.thmub.cocobook.utils.MD5Utils;
import com.thmub.cocobook.utils.RxUtils;
import com.thmub.cocobook.widget.page.TxtChapter;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by zhouas666 on 18-2-3.
 * 阅读页面presenter
 */

public class ReadPresenter extends RxPresenter<ReadContract.View> implements ReadContract.Presenter {
    private static final String TAG = "ReadPresenter";

    private Subscription mChapterSub;

    @Override
    public void createDownloadTask(CollBookBean bean) {
        RxBusManager.getInstance().post(new DownloadEvent(bean));
    }

    @Override
    public void loadBookSource(String bookId) {
        addDisposable(RemoteRepository.getInstance()
                .getBookSourceByBookId(bookId)
                .compose(RxUtils::toSimpleSingle)
//                .doOnSuccess(bookSourceBeans ->{
//
////                    //进行设定BookChapter所属的书的id。
////                    for (BookChapterBean bookChapter : bookChapterBeen) {
////                        bookChapter.setId(MD5Utils.strToMd5By16(bookChapter.getLink()));
////                        bookChapter.setBookId(bookId);
////                    }
//                })
                .subscribe(
                        beans -> mView.finishSource(beans)
                ));
    }

    /**
     * 从网络中加载目录
     *
     * @param sourceId
     */
    @Override
    public void loadCategory(String sourceId) {

        addDisposable(RemoteRepository.getInstance()
                .getBookChaptersBySourceId(sourceId)
                .compose(RxUtils::toSimpleSingle)
                .subscribe(
                        beans -> mView.showCategory(beans)
                ));
    }

    /**
     * 加载连续的5个章节（在目录中选择某一章节时）
     *
     * @param bookId
     * @param bookChapters
     */
    @Override
    public void loadChapter(String bookId, List<TxtChapter> bookChapters) {
        int size = bookChapters.size();
        //取消上次的任务，防止多次加载
        if (mChapterSub != null) {
            mChapterSub.cancel();
        }

        List<Single<ChapterInfoBean>> chapterInfos = new ArrayList<>(bookChapters.size());
        ArrayDeque<String> titles = new ArrayDeque<>(bookChapters.size());

        //首先判断是否Chapter已经存在
        for (int i = 0; i < size; ++i) {
            TxtChapter bookChapter = bookChapters.get(i);
            if (!(BookManager.isChapterCached(bookId, bookChapter.getTitle()))) {
                //网络中获取数据
                Single<ChapterInfoBean> chapterInfoSingle = RemoteRepository.getInstance()
                        .getChapterInfo(bookChapter.getLink());

                chapterInfos.add(chapterInfoSingle);

                titles.add(bookChapter.getTitle());
            }
            //如果已经存在，再判断是不是我们需要的下一个章节，如果是才返回加载成功
            else if (i == 0) {
                mView.finishChapter();
            }
        }

        Single.concat(chapterInfos)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<ChapterInfoBean>() {
                               String title = titles.poll();

                               @Override
                               public void onSubscribe(Subscription s) {
                                   s.request(Integer.MAX_VALUE);
                                   mChapterSub = s;
                               }

                               @Override
                               public void onNext(ChapterInfoBean chapterInfoBean) {
                                   //存储数据
                                   BookRepository.getInstance().saveChapterInfo(bookId, title, chapterInfoBean.getBody());
                                   mView.finishChapter();
                                   //将获取到的数据进行存储
                                   title = titles.poll();
                               }

                               @Override
                               public void onError(Throwable t) {
                                   //只有第一个加载失败才会调用errorChapter
                                   if (bookChapters.get(0).getTitle().equals(title)) {
                                       mView.errorChapter();
                                   }
                                   LogUtils.e(t);
                               }

                               @Override
                               public void onComplete() {
                               }
                           }
                );
    }
}
