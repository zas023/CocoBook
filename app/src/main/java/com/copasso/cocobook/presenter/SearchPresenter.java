package com.copasso.cocobook.presenter;

import com.copasso.cocobook.model.bean.BookSearchBean;
import com.copasso.cocobook.model.local.BookRepository;
import com.copasso.cocobook.model.server.RemoteRepository;
import com.copasso.cocobook.presenter.contract.SearchContract;
import com.copasso.cocobook.base.RxPresenter;
import com.copasso.cocobook.utils.LogUtils;
import com.copasso.cocobook.utils.RxUtils;

import io.reactivex.disposables.Disposable;

/**
 * Created by zhouas666 on 17-6-2.
 */

public class SearchPresenter extends RxPresenter<SearchContract.View>
        implements SearchContract.Presenter{

    @Override
    public void searchHotWord() {
        Disposable disp = RemoteRepository.getInstance()
                .getHotWords()
                .compose(RxUtils::toSimpleSingle)
                .subscribe(
                        bean -> {
                            mView.finishHotWords(bean);
                        },
                        e -> {
                            LogUtils.e(e);
                        }
                );
        addDisposable(disp);
    }

    @Override
    public void searchKeyWord(String query) {
        Disposable disp = RemoteRepository.getInstance()
                .getKeyWords(query)
                .compose(RxUtils::toSimpleSingle)
                .subscribe(
                        bean -> {
                            mView.finishKeyWords(bean);
                        },
                        e -> {
                            LogUtils.e(e);
                        }
                );
        addDisposable(disp);
    }

    @Override
    public void searchBook(String query) {
        Disposable disp = RemoteRepository.getInstance()
                .getSearchBooks(query)
                .compose(RxUtils::toSimpleSingle)
                .subscribe(
                        bean -> {
                            mView.finishBooks(bean);
                        },
                        e -> {
                            LogUtils.e(e);
                            mView.errorBooks();
                        }
                );
        addDisposable(disp);
    }

    @Override
    public void searchRecord() {
        mView.finishRecord(BookRepository.getInstance().getSearchRecord());
    }

    @Override
    public void addSearchRecord(BookSearchBean bean) {
        BookRepository.getInstance().saveBookSearchRecord(bean);
        mView.finishAddRecord(bean);
    }
}
