package com.thmub.cocobook.presenter.contract;

import com.thmub.cocobook.model.bean.BookChapterBean;
import com.thmub.cocobook.model.bean.CollBookBean;
import com.thmub.cocobook.base.BaseContract;
import com.thmub.cocobook.widget.page.TxtChapter;

import java.util.List;

/**
 * Created by zhouas666 on 18-2-3.
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
