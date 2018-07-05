package com.copasso.cocobook.ui.adapter;

import com.copasso.cocobook.model.bean.CommentBean;
import com.copasso.cocobook.ui.adapter.view.CommentHolder;
import com.copasso.cocobook.base.adapter.BaseListAdapter;
import com.copasso.cocobook.base.adapter.IViewHolder;

/**
 * Created by zhouas666 on 18-1-23.
 */

public class GodCommentAdapter extends BaseListAdapter<CommentBean>{
    @Override
    protected IViewHolder<CommentBean> createViewHolder(int viewType) {
        return new CommentHolder(true);
    }
}
