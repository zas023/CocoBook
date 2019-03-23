package com.thmub.cocobook.ui.adapter;

import com.thmub.cocobook.base.adapter.QuickAdapter;
import com.thmub.cocobook.model.bean.RankBookBean;
import com.thmub.cocobook.ui.adapter.holder.RecommendBookHolder;
import com.thmub.cocobook.base.adapter.IViewHolder;

/**
 * Created by zhouas666 on 18-2-8.
 */

public class RecommendBookAdapter extends QuickAdapter<RankBookBean> {

    @Override
    protected IViewHolder<RankBookBean> createViewHolder(int viewType) {
        return new RecommendBookHolder();
    }

}
