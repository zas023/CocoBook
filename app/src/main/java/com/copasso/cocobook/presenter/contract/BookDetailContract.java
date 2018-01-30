package com.copasso.cocobook.presenter.contract;

import com.copasso.cocobook.model.bean.*;
import com.copasso.cocobook.ui.base.BaseContract;

import java.util.List;

/**
 * Created by zhouas666 on 17-5-4.
 */

public interface BookDetailContract {
    interface View extends BaseContract.BaseView{
        void finishRefresh(BookDetailBean bean);
        void finishHotComment(List<HotCommentBean> beans);
        void finishRecommendBookList(List<BookListBean> beans);
        void finishRecommendBooks(List<BillBookBean> beans);

        void waitToBookShelf();
        void errorToBookShelf();
        void succeedToBookShelf();
    }

    interface Presenter extends BaseContract.BasePresenter<View>{
        void refreshBookDetail(String bookId);
        //添加到书架上
        void addToBookShelf(CollBookBean collBook);
    }
}
