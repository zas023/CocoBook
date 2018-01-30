package com.copasso.cocobook.presenter.contract;

import com.copasso.cocobook.model.bean.CommentBean;
import com.copasso.cocobook.model.bean.ReviewDetailBean;
import com.copasso.cocobook.ui.base.BaseContract;

import java.util.List;

/**
 * Created by zhouas666 on 17-4-30.
 */

public interface ReviewDetailContract {

    interface View extends BaseContract.BaseView{
        //全部加载的时候显示
        void finishRefresh(ReviewDetailBean reviewDetail,
                           List<CommentBean> bestComments, List<CommentBean> comments);
        void finishLoad(List<CommentBean> comments);
        void showLoadError();
    }

    interface Presenter extends BaseContract.BasePresenter<View>{
        void refreshReviewDetail(String detailId, int start, int limit);
        void loadComment(String detailId, int start, int limit);
    }
}
