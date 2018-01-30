package com.copasso.cocobook.presenter.contract;

import com.copasso.cocobook.model.bean.BillBookBean;
import com.copasso.cocobook.model.bean.FeatureDetailBean;
import com.copasso.cocobook.ui.base.BaseContract;

import java.util.List;

/**
 * Created by zhouas666 on 18-1-23.
 */

public interface FeatureDetailContract {
    interface View extends BaseContract.BaseView{
        void finishRefresh(List<FeatureDetailBean> beans);
    }

    interface Presenter extends BaseContract.BasePresenter<View>{
        void refreshFeatureDetail(String nodeId);
    }
}
