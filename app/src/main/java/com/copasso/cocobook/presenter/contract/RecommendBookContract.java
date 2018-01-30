package com.copasso.cocobook.presenter.contract;

import com.copasso.cocobook.model.bean.BillBookBean;
import com.copasso.cocobook.model.bean.CollBookBean;
import com.copasso.cocobook.ui.base.BaseContract;

import java.util.List;

/**
 * Created by zhouas666 on 17-5-3.
 */

public interface RecommendBookContract {
    interface View extends BaseContract.BaseView{
        void finishRefresh(List<BillBookBean> beans);
    }

    interface Presenter extends BaseContract.BasePresenter<View>{
        void refreshBookBrief(String bookId);
    }
}
