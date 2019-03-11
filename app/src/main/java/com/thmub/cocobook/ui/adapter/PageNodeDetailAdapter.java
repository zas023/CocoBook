package com.thmub.cocobook.ui.adapter;

import com.thmub.cocobook.model.bean.PageNodeDetailBean;
import com.thmub.cocobook.ui.adapter.view.FeatureDetailHolder;
import com.thmub.cocobook.base.adapter.BaseListAdapter;
import com.thmub.cocobook.base.adapter.IViewHolder;

/**
 * Created by zhouas666 on 18-1-23.
 * 书城Node全部书籍Adapter
 */

public class PageNodeDetailAdapter extends BaseListAdapter<PageNodeDetailBean> {
    @Override
    protected IViewHolder<PageNodeDetailBean> createViewHolder(int viewType) {
        return new FeatureDetailHolder();
    }
}
