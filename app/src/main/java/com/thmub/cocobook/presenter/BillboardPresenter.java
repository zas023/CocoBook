package com.thmub.cocobook.presenter;

import com.thmub.cocobook.model.bean.packages.BillboardPackage;
import com.thmub.cocobook.model.local.LocalRepository;
import com.thmub.cocobook.model.server.RemoteRepository;
import com.thmub.cocobook.presenter.contract.BillboardContract;
import com.thmub.cocobook.base.RxPresenter;

import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by zhouas666 on 18-1-23.
 */

public class BillboardPresenter extends RxPresenter<BillboardContract.View>
        implements BillboardContract.Presenter {

    @Override
    public void loadBillboardList() {
        //这个最好是设定一个默认时间采用Remote加载，如果Remote加载失败则采用数据中的数据。我这里先写死吧
        BillboardPackage bean = LocalRepository.getInstance()
                .getBillboardPackage();
        if (bean == null){
            RemoteRepository.getInstance()
                    .getBillboardPackage()
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .doOnSuccess(
                            (value) ->{
                                Schedulers.io().createWorker()
                                        .schedule(
                                                () ->{
                                                    LocalRepository.getInstance()
                                                            .saveBillboardPackage(value);
                                                }
                                        );
                            }
                    )
                    .subscribe(new SingleObserver<BillboardPackage>() {
                        @Override
                        public void onSubscribe(Disposable d) {
                            addDisposable(d);
                        }

                        @Override
                        public void onSuccess(BillboardPackage value) {
                            mView.finishRefresh(value);
                            mView.complete();
                        }

                        @Override
                        public void onError(Throwable e) {
                            mView.showError();
                        }
                    });
        }
        else {
            mView.finishRefresh(bean);
            mView.complete();
        }
    }
}
