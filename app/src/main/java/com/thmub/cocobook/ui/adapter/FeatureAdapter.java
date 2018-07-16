package com.thmub.cocobook.ui.adapter;

import com.thmub.cocobook.model.bean.FeatureBean;
import com.thmub.cocobook.ui.adapter.view.FeatureHolder;
import com.thmub.cocobook.base.adapter.BaseListAdapter;
import com.thmub.cocobook.base.adapter.IViewHolder;

/**
 * Created by zhouas666 on 18-1-23.
 * 书城分块Adapter
 */

public class FeatureAdapter extends BaseListAdapter<FeatureBean> {
    @Override
    protected IViewHolder<FeatureBean> createViewHolder(int viewType) {
        return new FeatureHolder();
    }
}
