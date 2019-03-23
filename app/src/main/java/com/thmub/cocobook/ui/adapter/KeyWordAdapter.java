package com.thmub.cocobook.ui.adapter;

import com.thmub.cocobook.base.adapter.BaseRecyclerAdapter;
import com.thmub.cocobook.ui.adapter.holder.KeyWordHolder;
import com.thmub.cocobook.base.adapter.IViewHolder;

/**
 * Created by zhouas666 on 17-6-2.
 */

public class KeyWordAdapter extends BaseRecyclerAdapter<String> {
    @Override
    protected IViewHolder<String> createViewHolder(int viewType) {
        return new KeyWordHolder();
    }
}
