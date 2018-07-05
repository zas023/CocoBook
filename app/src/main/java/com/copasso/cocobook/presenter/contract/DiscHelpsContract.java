package com.copasso.cocobook.presenter.contract;

import com.copasso.cocobook.model.bean.BookHelpsBean;
import com.copasso.cocobook.model.type.BookDistillate;
import com.copasso.cocobook.model.type.BookSort;
import com.copasso.cocobook.base.BaseContract;

import java.util.List;

/**
 * Created by zhouas666 on 17-4-21.
 */

public interface DiscHelpsContract {

    interface View extends BaseContract.BaseView{
        void finishRefresh(List<BookHelpsBean> beans);
        void finishLoading(List<BookHelpsBean> beans);
        void showErrorTip();
    }

    interface Presenter extends BaseContract.BasePresenter<View>{
        void firstLoading(BookSort sort, int start, int limited, BookDistillate distillate);
        void refreshBookHelps(BookSort sort, int start, int limited, BookDistillate distillate);
        void loadingBookHelps(BookSort sort, int start, int limited, BookDistillate distillate);
        void saveBookHelps(List<BookHelpsBean> beans);
    }
}
