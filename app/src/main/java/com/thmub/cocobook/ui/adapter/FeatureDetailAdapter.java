package com.thmub.cocobook.ui.adapter;

import com.thmub.cocobook.model.bean.FeatureDetailBean;
import com.thmub.cocobook.ui.adapter.view.FeatureDetailHolder;
import com.thmub.cocobook.base.adapter.BaseListAdapter;
import com.thmub.cocobook.base.adapter.IViewHolder;

/**
 * Created by zhouas666 on 18-1-23.
 */

public class FeatureDetailAdapter extends BaseListAdapter<FeatureDetailBean> {
    @Override
    protected IViewHolder<FeatureDetailBean> createViewHolder(int viewType) {
        return new FeatureDetailHolder();
    }
}
