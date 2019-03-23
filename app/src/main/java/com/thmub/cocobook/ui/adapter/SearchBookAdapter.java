package com.thmub.cocobook.ui.adapter;

import com.thmub.cocobook.base.adapter.BaseRecyclerAdapter;
import com.thmub.cocobook.model.bean.packages.SearchBookPackage;
import com.thmub.cocobook.ui.adapter.holder.SearchBookHolder;
import com.thmub.cocobook.base.adapter.IViewHolder;

/**
 * Created by zhouas666 on 17-6-2.
 */

public class SearchBookAdapter extends BaseRecyclerAdapter<SearchBookPackage.BooksBean> {
    @Override
    protected IViewHolder<SearchBookPackage.BooksBean> createViewHolder(int viewType) {
        return new SearchBookHolder();
    }
}
