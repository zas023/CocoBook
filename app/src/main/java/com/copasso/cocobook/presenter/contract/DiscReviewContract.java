package com.copasso.cocobook.presenter.contract;

import com.copasso.cocobook.model.bean.BookReviewBean;
import com.copasso.cocobook.model.flag.BookDistillate;
import com.copasso.cocobook.model.flag.BookSort;
import com.copasso.cocobook.model.flag.BookType;
import com.copasso.cocobook.ui.base.BaseContract;

import java.util.List;

/**
 * Created by zhouas666 on 17-4-21.
 */

public interface DiscReviewContract {
    interface View extends BaseContract.BaseView {
        void finishRefresh(List<BookReviewBean> beans);
        void finishLoading(List<BookReviewBean> beans);
        void showErrorTip();
    }

    interface Presenter extends BaseContract.BasePresenter<View> {
        void firstLoading(BookSort sort, BookType bookType, int start, int limited, BookDistillate distillate);
        void refreshBookReview(BookSort sort, BookType bookType, int start, int limited, BookDistillate distillate);
        void loadingBookReview(BookSort sort, BookType bookType, int start, int limited, BookDistillate distillate);
        void saveBookReview(List<BookReviewBean> beans);
    }
}
