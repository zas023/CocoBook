package com.thmub.cocobook.ui.adapter;

import com.thmub.cocobook.model.bean.BookSortBean;
import com.thmub.cocobook.ui.adapter.view.BookSortHolder;
import com.thmub.cocobook.base.adapter.BaseListAdapter;
import com.thmub.cocobook.base.adapter.IViewHolder;

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
