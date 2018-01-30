package com.copasso.cocobook.ui.adapter;

import android.content.Context;

import com.copasso.cocobook.model.bean.BookListBean;
import com.copasso.cocobook.ui.adapter.view.BookListHolder;
import com.copasso.cocobook.ui.base.adapter.IViewHolder;
import com.copasso.cocobook.widget.adapter.WholeAdapter;

/**
 * Created by zhouas666 on 17-5-1.
 */

public class BookListAdapter extends WholeAdapter<BookListBean> {
    public BookListAdapter() {
    }

    public BookListAdapter(Context context, Options options) {
        super(context, options);
    }

    @Override
    protected IViewHolder<BookListBean> createViewHolder(int viewType) {
        return new BookListHolder();
    }
}
