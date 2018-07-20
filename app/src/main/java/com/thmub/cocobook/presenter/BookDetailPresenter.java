package com.thmub.cocobook.presenter;

import com.thmub.cocobook.model.bean.BookChapterBean;
import com.thmub.cocobook.model.bean.BookDetailBean;
import com.thmub.cocobook.model.bean.CollBookBean;
import com.thmub.cocobook.model.local.BookRepository;
import com.thmub.cocobook.model.server.RemoteRepository;
import com.thmub.cocobook.presenter.contract.BookDetailContract;
import com.thmub.cocobook.base.RxPresenter;
import com.thmub.cocobook.utils.LogUtils;
import com.thmub.cocobook.utils.MD5Utils;

import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by zhouas666 on 18-2-4.
 * 书籍详情presenter
 */

public class BookDetailPresenter extends RxPresenter<BookDetailContract.View>
        implements BookDetailContract.Presenter {
    private static final String TAG = "BookDetailPresenter";
    private String bookId;

    @Override
    public void refreshBookDetail(String bookId) {
        this.bookId = bookId;
        refreshBook();
        refreshRecommendList();
        refreshRecommendBooks();

    }

    @Override
    public void addToBookShelf(CollBookBean collBookBean) {
        addDisposable(RemoteRepository.getInstance()
                .getBookChapters(collBookBean.get_id())
                .subscribeOn(Schedulers.io())
                .doOnSubscribe((d) -> mView.waitToBookShelf())
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(beans -> {
                            //设置 id
                            for (BookChapterBean bean : beans) {
                                bean.setId(MD5Utils.strToMd5By16(bean.getLink()));
                            }
                            //设置目录
                            collBookBean.setBookChapters(beans);
                            //存储收藏
                            BookRepository.getInstance().saveCollBookWithAsync(collBookBean);
                            mView.succeedToBookShelf();
                        }
                        ,
                        e -> {
                            mView.errorToBookShelf();
                            LogUtils.e(e);
                        }
                ));
    }

    private void refreshBook() {
        RemoteRepository.getInstance()
                .getBookDetail(bookId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<BookDetailBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        addDisposable(d);
                    }

                    @Override
                    public void onSuccess(BookDetailBean value) {
                        mView.finishRefresh(value);
                        mView.complete();
                    }

                    @Override
                    public void onError(Throwable e) {
                        mView.showError();
                    }
                });
    }

    private void refreshRecommendList() {
        addDisposable(RemoteRepository
                .getInstance()
                .getRecommendBookList(bookId, 3)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        (value) -> mView.finishRecommendBookList(value)
                ));
    }

    private void refreshRecommendBooks() {
        addDisposable(RemoteRepository
                .getInstance()
                .getRecommendBooksByBookId(bookId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        (value) -> mView.finishRecommendBooks(value)
                ));
    }
}
