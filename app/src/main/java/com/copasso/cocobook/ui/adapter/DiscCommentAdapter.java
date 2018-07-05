package com.copasso.cocobook.ui.adapter;

import android.content.Context;

import com.copasso.cocobook.model.bean.BookCommentBean;
import com.copasso.cocobook.ui.adapter.view.DiscCommentHolder;
import com.copasso.cocobook.base.adapter.IViewHolder;
import com.copasso.cocobook.widget.adapter.WholeAdapter;

/**
 * Created by zhouas666 on 17-4-20.
 */

public class DiscCommentAdapter extends WholeAdapter<BookCommentBean> {

    public DiscCommentAdapter(Context context, Options options) {
        super(context, options);
    }

    @Override
    protected IViewHolder<BookCommentBean> createViewHolder(int viewType) {
        return new DiscCommentHolder();
    }
}
