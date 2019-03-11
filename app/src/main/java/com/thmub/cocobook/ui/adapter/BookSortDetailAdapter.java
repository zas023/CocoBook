package com.thmub.cocobook.ui.adapter;

import android.content.Context;

import com.thmub.cocobook.model.bean.SortBookBean;
import com.thmub.cocobook.ui.adapter.view.BookSortDetailHolder;
import com.thmub.cocobook.base.adapter.IViewHolder;
import com.thmub.cocobook.base.adapter.WholeAdapter;

/**
 * Created by zhouas666 on 18-1-23.
 * 书籍分类列表中书籍adapter
 */

public class BookSortDetailAdapter extends WholeAdapter<SortBookBean>{
    public BookSortDetailAdapter(Context context, Options options) {
        super(context, options);
    }

    @Override
    protected IViewHolder<SortBookBean> createViewHolder(int viewType) {
        return new BookSortDetailHolder();
    }
}
