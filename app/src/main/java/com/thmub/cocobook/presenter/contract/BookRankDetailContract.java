package com.thmub.cocobook.presenter.contract;

import com.thmub.cocobook.model.bean.RankBookBean;
import com.thmub.cocobook.base.BaseContract;

import java.util.List;

/**
 * Created by zhouas666 on 18-1-23.
 */

public interface BookRankDetailContract {
    interface View extends BaseContract.BaseView{
        void finishRefresh(List<RankBookBean> beans);
    }

    interface Presenter extends BaseContract.BasePresenter<View>{
        void refreshBookBrief(String billId);
    }
}
