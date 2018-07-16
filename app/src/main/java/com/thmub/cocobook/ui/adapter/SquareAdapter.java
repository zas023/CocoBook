package com.thmub.cocobook.ui.adapter;

import com.thmub.cocobook.model.bean.SectionBean;
import com.thmub.cocobook.ui.adapter.view.SquareHolder;
import com.thmub.cocobook.base.adapter.BaseListAdapter;
import com.thmub.cocobook.base.adapter.IViewHolder;

/**
 * Created by zhouas666 on 18-1-23.
 */

public class SquareAdapter extends BaseListAdapter<SectionBean> {
    @Override
    protected IViewHolder<SectionBean> createViewHolder(int viewType) {
        return new SquareHolder();
    }
}
