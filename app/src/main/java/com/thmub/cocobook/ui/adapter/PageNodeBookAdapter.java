package com.thmub.cocobook.ui.adapter;

import com.thmub.cocobook.model.bean.PageNodeBookBean;
import com.thmub.cocobook.ui.adapter.holder.FeatureBookHolder;
import com.thmub.cocobook.base.adapter.BaseRecyclerAdapter;
import com.thmub.cocobook.base.adapter.IViewHolder;

/**
 * Created by zhouas666 on 18-1-23.
 * 书城Node书籍Adapter
 */

public class PageNodeBookAdapter extends BaseRecyclerAdapter<PageNodeBookBean> {
    @Override
    protected IViewHolder<PageNodeBookBean> createViewHolder(int viewType) {
        return new FeatureBookHolder();
    }
}
