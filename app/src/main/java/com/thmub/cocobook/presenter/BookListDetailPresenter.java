package com.thmub.cocobook.presenter;

import com.thmub.cocobook.model.server.RemoteRepository;
import com.thmub.cocobook.presenter.contract.BookListDetailContract;
import com.thmub.cocobook.base.RxPresenter;
import com.thmub.cocobook.utils.LogUtils;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by zhouas666 on 18-2-2.
 * 书单详情Presenter
 */

public class BookListDetailPresenter extends RxPresenter<BookListDetailContract.View>
        implements BookListDetailContract.Presenter {
    private static final String TAG = "BookListDetailPresenter";
    @Override
    public void refreshBookListDetail(String detailId) {
        addDisposable(RemoteRepository.getInstance()
                .getBookListDetail(detailId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        (beans)-> {
                            mView.finishRefresh(beans);
                            mView.complete();
                        }
                        ,
                        (e) ->{
                            mView.showError();
                            LogUtils.e(e);
                        }
                ));
    }
}
