package com.thmub.cocobook.ui.adapter;

import com.thmub.cocobook.base.adapter.BaseRecyclerAdapter;
import com.thmub.cocobook.base.adapter.IViewHolder;
import com.thmub.cocobook.ui.adapter.holder.SearchRecordHolder;

/**
 * Created by zhouas666 on 17-6-2.
 */

public class SearchRecordAdapter extends BaseRecyclerAdapter<String> {
    @Override
    protected IViewHolder<String> createViewHolder(int viewType) {
        return new SearchRecordHolder();
    }
}
