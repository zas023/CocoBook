package com.copasso.cocobook.ui.adapter;

import com.copasso.cocobook.model.bean.BookSortBean;
import com.copasso.cocobook.ui.adapter.view.BookSortHolder;
import com.copasso.cocobook.ui.base.adapter.BaseListAdapter;
import com.copasso.cocobook.ui.base.adapter.IViewHolder;

/**
 * Created by zhouas666 on 18-1-23.
 */

public class BookSortAdapter extends BaseListAdapter<BookSortBean>{

    @Override
    protected IViewHolder<BookSortBean> createViewHolder(int viewType) {
        return new BookSortHolder();
    }
}
