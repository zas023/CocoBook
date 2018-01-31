package com.copasso.cocobook.ui.adapter;

import com.copasso.cocobook.model.bean.SectionBean;
import com.copasso.cocobook.ui.adapter.view.SectionHolder;
import com.copasso.cocobook.ui.adapter.view.SquareHolder;
import com.copasso.cocobook.ui.base.adapter.BaseListAdapter;
import com.copasso.cocobook.ui.base.adapter.IViewHolder;

/**
 * Created by zhouas666 on 18-1-23.
 */

public class SquareAdapter extends BaseListAdapter<SectionBean> {
    @Override
    protected IViewHolder<SectionBean> createViewHolder(int viewType) {
        return new SquareHolder();
    }
}
