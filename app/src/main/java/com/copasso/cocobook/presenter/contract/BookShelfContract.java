package com.copasso.cocobook.presenter.contract;

import com.copasso.cocobook.model.bean.CollBookBean;
import com.copasso.cocobook.model.bean.DownloadTaskBean;
import com.copasso.cocobook.ui.base.BaseContract;

import java.util.List;

/**
 * Created by zhouas666 on 18-2-8.
 */

public interface BookShelfContract {

    interface View extends BaseContract.BaseView{
        void finishRefresh(List<CollBookBean> collBookBeans);
        void finishUpdate();
        void showErrorTip(String error);
    }

    interface Presenter extends BaseContract.BasePresenter<View>{
        void refreshCollBooks();
        void createDownloadTask(CollBookBean collBookBean);
        void updateCollBooks(List<CollBookBean> collBookBeans);
        void loadRecommendBooks(String gender);
    }
}
