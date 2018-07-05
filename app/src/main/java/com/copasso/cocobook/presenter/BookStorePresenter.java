package com.copasso.cocobook.presenter;

import com.copasso.cocobook.model.server.RemoteRepository;
import com.copasso.cocobook.presenter.contract.BookStoreContract;
import com.copasso.cocobook.base.RxPresenter;
import com.copasso.cocobook.utils.*;
import io.reactivex.disposables.Disposable;

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
