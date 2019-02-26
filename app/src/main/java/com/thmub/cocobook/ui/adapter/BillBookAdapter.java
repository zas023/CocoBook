package com.thmub.cocobook.ui.adapter;

import com.thmub.cocobook.model.bean.RankBookBean;
import com.thmub.cocobook.ui.adapter.view.BillBookHolder;
import com.thmub.cocobook.base.adapter.BaseListAdapter;
import com.thmub.cocobook.base.adapter.IViewHolder;

/**
 * Created by zhouas666 on 18-1-23.
 * 通用书籍列表adapter
 */

public class BillBookAdapter extends BaseListAdapter<RankBookBean> {
    @Override
    protected IViewHolder<RankBookBean> createViewHolder(int viewType) {
        return new BillBookHolder();
    }
}
