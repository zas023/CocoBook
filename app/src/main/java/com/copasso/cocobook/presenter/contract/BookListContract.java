package com.copasso.cocobook.presenter.contract;

import com.copasso.cocobook.model.bean.BookListBean;
import com.copasso.cocobook.model.bean.BookListDetailBean;
import com.copasso.cocobook.model.flag.BookListType;
import com.copasso.cocobook.ui.base.BaseContract;

import java.util.List;

/**
 * Created by zhouas666 on 17-5-1.
 */

public interface BookListContract {
    interface View extends BaseContract.BaseView{
        void finishRefresh(List<BookListBean> beans);
        void finishLoading(List<BookListBean> beans);
        void showLoadError();
    }

    interface Presenter extends BaseContract.BasePresenter<View>{
        void refreshBookList(BookListType type, String tag, int start, int limited);
        void loadBookList(BookListType type, String tag, int start, int limited);
    }
}
