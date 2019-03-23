package com.thmub.cocobook.ui.adapter;

import com.thmub.cocobook.model.bean.SectionBean;
import com.thmub.cocobook.ui.adapter.holder.SquareHolder;
import com.thmub.cocobook.base.adapter.BaseRecyclerAdapter;
import com.thmub.cocobook.base.adapter.IViewHolder;

/**
 * Created by zhouas666 on 18-1-23.
 */

public class SquareAdapter extends BaseRecyclerAdapter<SectionBean> {
    @Override
    protected IViewHolder<SectionBean> createViewHolder(int viewType) {
        return new SquareHolder();
    }
}
