package com.thmub.cocobook.presenter.contract;

import com.thmub.cocobook.model.bean.FeatureBean;
import com.thmub.cocobook.model.bean.SwipePictureBean;
import com.thmub.cocobook.base.BaseContract;

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
