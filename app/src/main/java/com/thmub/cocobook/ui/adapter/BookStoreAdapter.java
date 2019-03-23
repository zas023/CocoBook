package com.thmub.cocobook.ui.adapter;

import com.thmub.cocobook.base.adapter.IViewHolder;
import com.thmub.cocobook.base.adapter.QuickAdapter;
import com.thmub.cocobook.model.bean.PageNodeBean;
import com.thmub.cocobook.ui.adapter.holder.PageNodeHolder;

/**
 * Created by Zhouas666 on 2019-03-11
 * Github: https://github.com/zas023
 *
 * 书城adapter
 */
public class BookStoreAdapter extends QuickAdapter<PageNodeBean> {

    private static final String TAG = "BookStoreAdapter";

    @Override
    protected IViewHolder<PageNodeBean> createViewHolder(int viewType) {
        return new PageNodeHolder();
    }
}
