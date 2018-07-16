package com.thmub.cocobook.ui.adapter;

import com.thmub.cocobook.model.bean.BillBookBean;
import com.thmub.cocobook.ui.adapter.view.RecommendBookHolder;
import com.thmub.cocobook.base.adapter.IViewHolder;
import com.thmub.cocobook.widget.adapter.WholeAdapter;

/**
 * Created by zhouas666 on 18-2-8.
 */

public class RecommendBookAdapter extends WholeAdapter<BillBookBean> {

    @Override
    protected IViewHolder<BillBookBean> createViewHolder(int viewType) {
        return new RecommendBookHolder();
    }

}
