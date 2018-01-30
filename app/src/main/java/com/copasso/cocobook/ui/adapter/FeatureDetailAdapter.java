package com.copasso.cocobook.ui.adapter;

import com.copasso.cocobook.model.bean.BillBookBean;
import com.copasso.cocobook.model.bean.FeatureDetailBean;
import com.copasso.cocobook.ui.adapter.view.FeatureDetailHolder;
import com.copasso.cocobook.ui.base.adapter.BaseListAdapter;
import com.copasso.cocobook.ui.base.adapter.IViewHolder;

/**
 * Created by zhouas666 on 18-1-23.
 */

public class FeatureDetailAdapter extends BaseListAdapter<FeatureDetailBean> {
    @Override
    protected IViewHolder<FeatureDetailBean> createViewHolder(int viewType) {
        return new FeatureDetailHolder();
    }
}
