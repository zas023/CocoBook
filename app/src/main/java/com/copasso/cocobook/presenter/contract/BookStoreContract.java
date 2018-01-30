package com.copasso.cocobook.presenter.contract;

import com.copasso.cocobook.model.bean.CollBookBean;
import com.copasso.cocobook.model.bean.SwipePictureBean;
import com.copasso.cocobook.ui.base.BaseContract;

import java.util.List;

/**
 * Created by zhouas666 on 17-5-8.
 */

public interface BookStoreContract {

    interface View extends BaseContract.BaseView{
        void finishRefresh(List<SwipePictureBean> swipePictureBeans);
        void finishUpdate();
        void showErrorTip(String error);
    }

    interface Presenter extends BaseContract.BasePresenter<View>{
        void refreshSwipePictures();
    }
}
