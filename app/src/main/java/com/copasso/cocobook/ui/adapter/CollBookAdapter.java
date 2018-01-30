package com.copasso.cocobook.ui.adapter;

import com.copasso.cocobook.model.bean.CollBookBean;
import com.copasso.cocobook.ui.adapter.view.CollBookHolder;
import com.copasso.cocobook.ui.base.adapter.IViewHolder;
import com.copasso.cocobook.widget.adapter.WholeAdapter;

/**
 * Created by zhouas666 on 17-5-8.
 */

public class CollBookAdapter extends WholeAdapter<CollBookBean> {

    @Override
    protected IViewHolder<CollBookBean> createViewHolder(int viewType) {
        return new CollBookHolder();
    }

}
