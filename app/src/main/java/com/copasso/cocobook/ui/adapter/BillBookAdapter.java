package com.copasso.cocobook.ui.adapter;

import com.copasso.cocobook.model.bean.BillBookBean;
import com.copasso.cocobook.ui.adapter.view.BillBookHolder;
import com.copasso.cocobook.ui.base.adapter.BaseListAdapter;
import com.copasso.cocobook.ui.base.adapter.IViewHolder;

/**
 * Created by zhouas666 on 18-1-23.
 */

public class BillBookAdapter extends BaseListAdapter<BillBookBean> {
    @Override
    protected IViewHolder<BillBookBean> createViewHolder(int viewType) {
        return new BillBookHolder();
    }
}
