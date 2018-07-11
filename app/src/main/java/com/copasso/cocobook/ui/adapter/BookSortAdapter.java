package com.copasso.cocobook.ui.adapter;

import com.copasso.cocobook.model.bean.BookSortBean;
import com.copasso.cocobook.ui.adapter.view.BookSortHolder;
import com.copasso.cocobook.base.adapter.BaseListAdapter;
import com.copasso.cocobook.base.adapter.IViewHolder;

/**
 * Created by zhouas666 on 18-1-23.
 * 书籍分类adapter
 */

public class BookSortAdapter extends BaseListAdapter<BookSortBean>{

    @Override
    protected IViewHolder<BookSortBean> createViewHolder(int viewType) {
        return new BookSortHolder();
    }
}
