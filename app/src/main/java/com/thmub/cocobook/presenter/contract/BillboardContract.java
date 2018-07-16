package com.thmub.cocobook.presenter.contract;

import com.thmub.cocobook.model.bean.packages.BillboardPackage;
import com.thmub.cocobook.base.BaseContract;

/**
 * Created by zhouas666 on 18-1-23.
 */

public interface BillboardContract {

    interface View extends BaseContract.BaseView{
        void finishRefresh(BillboardPackage beans);
    }

    interface Presenter extends BaseContract.BasePresenter<View>{
        void loadBillboardList();
    }
}
