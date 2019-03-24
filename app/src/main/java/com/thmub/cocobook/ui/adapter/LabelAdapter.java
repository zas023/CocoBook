package com.thmub.cocobook.ui.adapter;

import com.thmub.cocobook.base.adapter.BaseRecyclerAdapter;
import com.thmub.cocobook.base.adapter.IViewHolder;
import com.thmub.cocobook.ui.adapter.holder.LabelHolder;

/**
 * Created by zhouas666 on 18-1-23.
 */

public class LabelAdapter extends BaseRecyclerAdapter<String> {
    @Override
    protected IViewHolder<String> createViewHolder(int viewType) {
        return new LabelHolder();
    }
}
