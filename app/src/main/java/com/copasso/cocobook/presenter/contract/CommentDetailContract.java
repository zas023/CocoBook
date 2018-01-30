package com.copasso.cocobook.presenter.contract;

import com.copasso.cocobook.model.bean.CommentBean;
import com.copasso.cocobook.model.bean.CommentDetailBean;
import com.copasso.cocobook.model.flag.CommunityType;
import com.copasso.cocobook.ui.base.BaseContract;

import java.util.List;

/**
 * Created by zhouas666 on 17-4-29.
 */

public interface CommentDetailContract {

    interface View extends BaseContract.BaseView{
        //全部加载的时候显示
        void finishRefresh(CommentDetailBean commentDetail,
                           List<CommentBean> bestComments, List<CommentBean> comments);
        void finishLoad(List<CommentBean> comments);
        void showLoadError();
    }

    interface Presenter extends BaseContract.BasePresenter<View>{
        void refreshCommentDetail(String detailId, int start, int limit);
        void loadComment(String detailId, int start, int limit);
    }
}
