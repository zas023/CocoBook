package com.thmub.cocobook.presenter.contract;

import com.thmub.cocobook.model.bean.CommentBean;
import com.thmub.cocobook.model.bean.HelpsDetailBean;
import com.thmub.cocobook.base.BaseContract;

import java.util.List;

/**
 * Created by zhouas666 on 17-4-30.
 */

public interface HelpsDetailContract{

    interface View extends BaseContract.BaseView{
        //全部加载的时候显示
        void finishRefresh(HelpsDetailBean commentDetail,
                           List<CommentBean> bestComments, List<CommentBean> comments);
        void finishLoad(List<CommentBean> comments);
        void showLoadError();
    }

    interface Presenter extends BaseContract.BasePresenter<View>{
        void refreshHelpsDetail(String detailId, int start, int limit);
        void loadComment(String detailId, int start, int limit);
    }
}
