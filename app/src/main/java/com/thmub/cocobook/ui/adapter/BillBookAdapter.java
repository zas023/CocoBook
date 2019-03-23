package com.thmub.cocobook.ui.adapter;

import com.thmub.cocobook.base.adapter.BaseRecyclerAdapter;
import com.thmub.cocobook.model.bean.RankBookBean;
import com.thmub.cocobook.ui.adapter.holder.BillBookHolder;
import com.thmub.cocobook.base.adapter.IViewHolder;

/**
 * Created by zhouas666 on 18-1-23.
 * 通用书籍列表adapter
 */

public class BillBookAdapter extends BaseRecyclerAdapter<RankBookBean> {
    @Override
    protected IViewHolder<RankBookBean> createViewHolder(int viewType) {
        return new BillBookHolder();
    }
}
