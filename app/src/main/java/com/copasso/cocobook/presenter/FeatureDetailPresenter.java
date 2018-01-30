package com.copasso.cocobook.presenter;

import com.copasso.cocobook.model.remote.RemoteRepository;
import com.copasso.cocobook.presenter.contract.FeatureDetailContract;
import com.copasso.cocobook.ui.base.RxPresenter;
import com.copasso.cocobook.utils.LogUtils;
import com.copasso.cocobook.utils.RxUtils;

/**
 * Created by zhouas666 on 18-1-23.
 */

public class FeatureDetailPresenter extends RxPresenter<FeatureDetailContract.View>
        implements FeatureDetailContract.Presenter {
    private static final String TAG = "BillBookPresenter";

    @Override
    public void refreshFeatureDetail(String nodeId) {
        addDisposable(RemoteRepository.getInstance()
                .getFeatureDetail(nodeId)
                .compose(RxUtils::toSimpleSingle)
                .subscribe(
                        (beans)-> {
                            mView.finishRefresh(beans);
                            mView.complete();
                            System.out.println("======================================="+beans.size());
                        }
                        ,
                        (e) ->{
                            mView.showError();
                            LogUtils.e(e);
                            System.out.println("======================================"+e.getMessage());
                        }
                ));
    }
}
