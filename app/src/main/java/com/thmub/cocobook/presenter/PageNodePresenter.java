package com.thmub.cocobook.presenter;

import com.thmub.cocobook.model.server.RemoteRepository;
import com.thmub.cocobook.presenter.contract.PageNodeContract;
import com.thmub.cocobook.base.RxPresenter;
import com.thmub.cocobook.utils.LogUtils;
import com.thmub.cocobook.utils.RxUtils;

/**
 * Created by zhouas666 on 18-1-23.
 * 书城页面站点presenter
 */

public class PageNodePresenter extends RxPresenter<PageNodeContract.View>
        implements PageNodeContract.Presenter {
    private static final String TAG = "PageNodePresenter";

    @Override
    public void refreshFeatureDetail(String nodeId) {
        addDisposable(RemoteRepository.getInstance()
                .getFeatureDetail(nodeId)
                .compose(RxUtils::toSimpleSingle)
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
