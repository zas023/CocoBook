package com.thmub.cocobook.presenter.contract;

import com.thmub.cocobook.model.bean.packages.BookSortPackage;
import com.thmub.cocobook.model.bean.packages.BookSubSortPackage;
import com.thmub.cocobook.base.BaseContract;

/**
 * Created by zhouas666 on 18-1-23.
 */

public interface BookSortContract {

    interface View extends BaseContract.BaseView{
        void finishRefresh(BookSortPackage sortPackage, BookSubSortPackage subSortPackage);
    }

    interface Presenter extends BaseContract.BasePresenter<View>{
        void refreshSortBean();
    }
}
