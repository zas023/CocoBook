package com.thmub.cocobook.ui.adapter;

import android.content.Context;

import com.thmub.cocobook.model.bean.BookListBean;
import com.thmub.cocobook.ui.adapter.view.BookListHolder;
import com.thmub.cocobook.base.adapter.IViewHolder;
import com.thmub.cocobook.base.adapter.WholeAdapter;

/**
 * Created by zhouas666 on 18-2-1.
 * 书单列表adapter
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
