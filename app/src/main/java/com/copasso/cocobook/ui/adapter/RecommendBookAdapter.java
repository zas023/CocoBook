package com.copasso.cocobook.ui.adapter;

import com.copasso.cocobook.model.bean.BillBookBean;
import com.copasso.cocobook.ui.adapter.view.RecommendBookHolder;
import com.copasso.cocobook.ui.base.adapter.IViewHolder;
import com.copasso.cocobook.widget.adapter.WholeAdapter;

/**
 * Created by zhouas666 on 18-2-8.
 */

public class RecommendBookAdapter extends WholeAdapter<BillBookBean> {

    @Override
    protected IViewHolder<BillBookBean> createViewHolder(int viewType) {
        return new RecommendBookHolder();
    }

}
