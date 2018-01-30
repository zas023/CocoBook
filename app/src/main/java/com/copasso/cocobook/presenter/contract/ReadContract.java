package com.copasso.cocobook.presenter.contract;

import com.copasso.cocobook.model.bean.BookChapterBean;
import com.copasso.cocobook.model.bean.CollBookBean;
import com.copasso.cocobook.ui.base.BaseContract;
import com.copasso.cocobook.widget.page.TxtChapter;

import java.util.List;

/**
 * Created by zhouas666 on 17-5-16.
 */

public interface ReadContract extends BaseContract{
    interface View extends BaseContract.BaseView {
        void showCategory(List<BookChapterBean> bookChapterList);
        void finishChapter();
        void errorChapter();
    }

    interface Presenter extends BaseContract.BasePresenter<View>{
        void loadCategory(String bookId);
        void loadChapter(String bookId, List<TxtChapter> bookChapterList);
        void createDownloadTask(CollBookBean collBookBean);
    }
}
