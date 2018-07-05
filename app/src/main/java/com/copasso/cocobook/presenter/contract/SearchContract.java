package com.copasso.cocobook.presenter.contract;

import com.copasso.cocobook.model.bean.BookSearchBean;
import com.copasso.cocobook.model.bean.packages.SearchBookPackage;
import com.copasso.cocobook.base.BaseContract;

import java.util.List;

/**
 * Created by zhouas666 on 17-6-2.
 */

public interface SearchContract extends BaseContract {

    interface View extends BaseView{
        void finishHotWords(List<String> hotWords);
        void finishKeyWords(List<String> keyWords);
        void finishBooks(List<SearchBookPackage.BooksBean> books);
        void finishRecord(List<BookSearchBean> records);
        void finishAddRecord(BookSearchBean bean);

        void errorBooks();
    }

    interface Presenter extends BasePresenter<View>{
        void searchHotWord();
        //搜索提示
        void searchKeyWord(String query);
        //搜索书籍
        void searchBook(String query);
        //搜索记录
        void searchRecord();
        //添加搜索记录
        void addSearchRecord(BookSearchBean bean);
    }
}
