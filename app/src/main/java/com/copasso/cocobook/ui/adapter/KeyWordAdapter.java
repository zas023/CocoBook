package com.copasso.cocobook.ui.adapter;

import com.copasso.cocobook.ui.adapter.view.KeyWordHolder;
import com.copasso.cocobook.base.adapter.BaseListAdapter;
import com.copasso.cocobook.base.adapter.IViewHolder;

/**
 * Created by zhouas666 on 17-6-2.
 */

public class KeyWordAdapter extends BaseListAdapter<String> {
    @Override
    protected IViewHolder<String> createViewHolder(int viewType) {
        return new KeyWordHolder();
    }
}
