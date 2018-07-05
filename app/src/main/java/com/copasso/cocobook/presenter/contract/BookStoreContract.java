package com.copasso.cocobook.presenter.contract;

import com.copasso.cocobook.model.bean.CollBookBean;
import com.copasso.cocobook.model.bean.FeatureBean;
import com.copasso.cocobook.model.bean.SwipePictureBean;
import com.copasso.cocobook.base.BaseContract;

import java.util.List;

/**
 * Created by zhouas666 on 18-2-8.
 */

public interface BookStoreContract {

    interface View extends BaseContract.BaseView{
        void finishRefreshSwipePictures(List<SwipePictureBean> swipePictureBeans);
        void finishRefreshFeatures(List<FeatureBean> featureBeans);
        void finishUpdate();
        void showErrorTip(String error);
    }

    interface Presenter extends BaseContract.BasePresenter<View>{
        void refreshSwipePictures();
        void refreshFeatures();
    }
}
