package com.copasso.cocobook.presenter.contract;

import com.copasso.cocobook.model.bean.SortBookBean;
import com.copasso.cocobook.model.type.BookSortListType;
import com.copasso.cocobook.base.BaseContract;

import java.util.List;

/**
 * Created by zhouas666 on 18-1-23.
 */

public interface BookSortListContract {
    interface View extends BaseContract.BaseView{
        void finishRefresh(List<SortBookBean> beans);
        void finishLoad(List<SortBookBean> beans);
        void showLoadError();
    }

    interface Presenter extends BaseContract.BasePresenter<View>{
        void refreshSortBook(String gender, BookSortListType type, String major, String minor, int start, int limit);
        void loadSortBook(String gender, BookSortListType type, String major, String minor, int start, int limit);
    }
}
