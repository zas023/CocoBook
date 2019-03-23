package com.thmub.cocobook.ui.adapter;

import android.content.Context;

import com.thmub.cocobook.base.adapter.QuickAdapter;
import com.thmub.cocobook.model.bean.BookListBean;
import com.thmub.cocobook.ui.adapter.holder.BookListHolder;
import com.thmub.cocobook.base.adapter.IViewHolder;

/**
 * Created by zhouas666 on 18-2-1.
 * 书单列表adapter
 */

public class BookListAdapter extends QuickAdapter<BookListBean> {
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
