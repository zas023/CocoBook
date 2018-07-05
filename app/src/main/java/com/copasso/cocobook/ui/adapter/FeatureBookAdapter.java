package com.copasso.cocobook.ui.adapter;

import com.copasso.cocobook.model.bean.FeatureBookBean;
import com.copasso.cocobook.model.bean.FeatureDetailBean;
import com.copasso.cocobook.ui.adapter.view.FeatureBookHolder;
import com.copasso.cocobook.ui.adapter.view.FeatureDetailHolder;
import com.copasso.cocobook.ui.adapter.view.RecommendBookHolder;
import com.copasso.cocobook.base.adapter.BaseListAdapter;
import com.copasso.cocobook.base.adapter.IViewHolder;

/**
 * Created by zhouas666 on 18-1-23.
 */

public class FeatureBookAdapter extends BaseListAdapter<FeatureBookBean> {
    @Override
    protected IViewHolder<FeatureBookBean> createViewHolder(int viewType) {
        return new FeatureBookHolder();
    }
}
