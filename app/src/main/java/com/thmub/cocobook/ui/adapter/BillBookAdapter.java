package com.thmub.cocobook.ui.adapter;

import com.thmub.cocobook.model.bean.BillBookBean;
import com.thmub.cocobook.ui.adapter.view.BillBookHolder;
import com.thmub.cocobook.base.adapter.BaseListAdapter;
import com.thmub.cocobook.base.adapter.IViewHolder;

/**
 * Created by zhouas666 on 18-1-23.
 * 排行榜书籍adapter
 */

public class BillBookAdapter extends BaseListAdapter<BillBookBean> {
    @Override
    protected IViewHolder<BillBookBean> createViewHolder(int viewType) {
        return new BillBookHolder();
    }
}
