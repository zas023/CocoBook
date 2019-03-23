package com.thmub.cocobook.ui.adapter;

import android.content.Context;

import com.thmub.cocobook.model.bean.BookListDetailBean;
import com.thmub.cocobook.ui.adapter.holder.BookListInfoHolder;
import com.thmub.cocobook.base.adapter.IViewHolder;
import com.thmub.cocobook.base.adapter.QuickAdapter;

/**
 * Created by zhouas666 on 18-2-2.
 * 书单详情中书籍adapter
 */

public class BookListDetailAdapter extends QuickAdapter<BookListDetailBean.BooksBean.BookBean> {
    public BookListDetailAdapter(Context context, Options options) {
        super(context, options);
    }

    @Override
    protected IViewHolder<BookListDetailBean.BooksBean.BookBean> createViewHolder(int viewType) {
        return new BookListInfoHolder();
    }
}
