package com.copasso.cocobook.ui.adapter;

import com.copasso.cocobook.model.bean.HotCommentBean;
import com.copasso.cocobook.ui.adapter.view.HotCommentHolder;
import com.copasso.cocobook.base.adapter.BaseListAdapter;
import com.copasso.cocobook.base.adapter.IViewHolder;

/**
 * Created by zhouas666 on 18-2-4.
 */

public class HotCommentAdapter extends BaseListAdapter<HotCommentBean>{
    @Override
    protected IViewHolder<HotCommentBean> createViewHolder(int viewType) {
        return new HotCommentHolder();
    }
}
