package com.copasso.cocobook.ui.adapter;

import com.copasso.cocobook.model.bean.FeatureBean;
import com.copasso.cocobook.ui.adapter.view.FeatureHolder;
import com.copasso.cocobook.base.adapter.BaseListAdapter;
import com.copasso.cocobook.base.adapter.IViewHolder;

/**
 * Created by zhouas666 on 18-1-23.
 */

public class FeatureAdapter extends BaseListAdapter<FeatureBean> {
    @Override
    protected IViewHolder<FeatureBean> createViewHolder(int viewType) {
        return new FeatureHolder();
    }
}
