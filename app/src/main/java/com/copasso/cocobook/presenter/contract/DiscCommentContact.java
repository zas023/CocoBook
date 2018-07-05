package com.copasso.cocobook.presenter.contract;

import com.copasso.cocobook.model.bean.BookCommentBean;
import com.copasso.cocobook.model.type.BookDistillate;
import com.copasso.cocobook.model.type.BookSort;
import com.copasso.cocobook.model.type.CommunityType;
import com.copasso.cocobook.base.BaseContract;

import java.util.List;

/**
 * Created by zhouas666 on 17-4-20.
 */

public interface DiscCommentContact {

    interface View extends BaseContract.BaseView{
        void finishRefresh(List<BookCommentBean> beans);
        void finishLoading(List<BookCommentBean> beans);
        void showErrorTip();
    }

    interface Presenter extends BaseContract.BasePresenter<View>{
        void firstLoading(CommunityType block, BookSort sort, int start, int limited, BookDistillate distillate);
        void refreshComment(CommunityType block, BookSort sort, int start, int limited, BookDistillate distillate);
        void loadingComment(CommunityType block, BookSort sort, int start, int limited, BookDistillate distillate);
        void saveComment(List<BookCommentBean> beans);
    }
}
