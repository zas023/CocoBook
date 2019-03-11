package com.thmub.cocobook.presenter.contract;

import com.thmub.cocobook.model.bean.PageNodeBean;
import com.thmub.cocobook.base.BaseContract;

import java.util.List;

/**
 * Created by zhouas666 on 18-2-8.
 */

public interface BookStoreContract {

    interface View extends BaseContract.BaseView{
        void finishRefreshPageNode(List<PageNodeBean> pageNodeBeans);
        void showErrorTip(String error);
    }

    interface Presenter extends BaseContract.BasePresenter<View>{
        void refreshPageNodes();
    }
}
