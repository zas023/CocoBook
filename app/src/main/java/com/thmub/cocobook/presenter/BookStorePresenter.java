package com.thmub.cocobook.presenter;

import com.thmub.cocobook.model.server.RemoteRepository;
import com.thmub.cocobook.presenter.contract.BookStoreContract;
import com.thmub.cocobook.base.RxPresenter;
import com.thmub.cocobook.utils.*;

import io.reactivex.disposables.Disposable;

/**
 * Created by zhouas666 on 18-2-8.
 * 书城presenter
 */

public class BookStorePresenter extends RxPresenter<BookStoreContract.View>
        implements BookStoreContract.Presenter {

    private static final String TAG = "BookStorePresenter";

    @Override
    public void refreshPageNodes() {
        addDisposable(RemoteRepository.getInstance()
                .getFeature()
                .compose(RxUtils::toSimpleSingle)
                .subscribe(
                        featureBeans ->
                                mView.finishRefreshPageNode(featureBeans)
                        , throwable -> {
                            //提示没有网络
                            LogUtils.e(throwable);
                            mView.showErrorTip(throwable.toString());
                        }
                ));
    }
}
