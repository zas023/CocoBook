package com.thmub.cocobook.ui.adapter;

import com.thmub.cocobook.model.bean.FeatureBookBean;
import com.thmub.cocobook.ui.adapter.view.FeatureBookHolder;
import com.thmub.cocobook.base.adapter.BaseListAdapter;
import com.thmub.cocobook.base.adapter.IViewHolder;

/**
 * Created by zhouas666 on 18-1-23.
 * 书城书籍Adapter
 */

public class FeatureBookAdapter extends BaseListAdapter<FeatureBookBean> {
    @Override
    protected IViewHolder<FeatureBookBean> createViewHolder(int viewType) {
        return new FeatureBookHolder();
    }
}
