package com.copasso.cocobook.ui.adapter;

import android.content.Context;

import com.copasso.cocobook.model.bean.BookListDetailBean;
import com.copasso.cocobook.ui.adapter.view.BookListInfoHolder;
import com.copasso.cocobook.ui.base.adapter.IViewHolder;
import com.copasso.cocobook.widget.adapter.WholeAdapter;

/**
 * Created by zhouas666 on 17-5-2.
 */

public class BookListDetailAdapter extends WholeAdapter<BookListDetailBean.BooksBean.BookBean> {
    public BookListDetailAdapter(Context context, Options options) {
        super(context, options);
    }

    @Override
    protected IViewHolder<BookListDetailBean.BooksBean.BookBean> createViewHolder(int viewType) {
        return new BookListInfoHolder();
    }
}
