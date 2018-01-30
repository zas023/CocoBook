package com.copasso.cocobook.ui.adapter;

import android.content.Context;

import com.copasso.cocobook.model.bean.BookReviewBean;
import com.copasso.cocobook.ui.adapter.view.DiscReviewHolder;
import com.copasso.cocobook.ui.base.adapter.IViewHolder;
import com.copasso.cocobook.widget.adapter.WholeAdapter;

/**
 * Created by zhouas666 on 17-4-21.
 */

public class DiscReviewAdapter extends WholeAdapter<BookReviewBean> {

    public DiscReviewAdapter(Context context, Options options) {
        super(context, options);
    }

    @Override
    protected IViewHolder<BookReviewBean> createViewHolder(int viewType) {
        return new DiscReviewHolder();
    }
}
