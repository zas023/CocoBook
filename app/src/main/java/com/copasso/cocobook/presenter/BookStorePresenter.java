package com.copasso.cocobook.presenter;

import com.copasso.cocobook.RxBus;
import com.copasso.cocobook.model.bean.*;
import com.copasso.cocobook.model.local.BookRepository;
import com.copasso.cocobook.model.remote.RemoteRepository;
import com.copasso.cocobook.presenter.contract.BookShelfContract;
import com.copasso.cocobook.presenter.contract.BookStoreContract;
import com.copasso.cocobook.ui.base.RxPresenter;
import com.copasso.cocobook.utils.*;
import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by zhouas666 on 18-2-8.
 */

public class BookStorePresenter extends RxPresenter<BookStoreContract.View>
        implements BookStoreContract.Presenter {
    private static final String TAG = "BookStorePresenter";


    @Override
    public void refreshSwipePictures() {
        Disposable disposable = RemoteRepository.getInstance()
                .getSwipePictures()
                .compose(RxUtils::toSimpleSingle)
                .subscribe(
                        beans -> {
                            mView.finishRefreshSwipePictures(beans);
                            mView.complete();
                        },
                        (e) -> {
                            //提示没有网络
                            LogUtils.e(e);
                            mView.showErrorTip(e.toString());
                            mView.complete();
                        }
                );
        addDisposable(disposable);
    }

    @Override
    public void refreshFeatures() {
        addDisposable(RemoteRepository.getInstance()
        .getFeature()
        .compose(RxUtils::toSimpleSingle)
        .subscribe(
                featureBeans ->
                        mView.finishRefreshFeatures(featureBeans)
                ,throwable -> {
                    //提示没有网络
                    LogUtils.e(throwable);
                    mView.showErrorTip(throwable.toString());
                }
        ));
    }
}
