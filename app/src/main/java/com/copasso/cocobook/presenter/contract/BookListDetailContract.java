package com.copasso.cocobook.presenter.contract;

import com.copasso.cocobook.model.bean.BookListDetailBean;
import com.copasso.cocobook.ui.base.BaseContract;

/**
 * Created by zhouas666 on 18-2-1.
 */

public interface BookListDetailContract {

    interface View extends BaseContract.BaseView{
        void finishRefresh(BookListDetailBean bean);
    }

    interface Presenter extends BaseContract.BasePresenter<View>{
        void refreshBookListDetail(String detailId);
    }
}
