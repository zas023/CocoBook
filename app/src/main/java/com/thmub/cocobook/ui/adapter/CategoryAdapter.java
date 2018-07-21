package com.thmub.cocobook.ui.adapter;

import android.view.View;
import android.view.ViewGroup;

import com.thmub.cocobook.ui.adapter.view.CategoryHolder;
import com.thmub.cocobook.base.adapter.EasyAdapter;
import com.thmub.cocobook.base.adapter.IViewHolder;
import com.thmub.cocobook.widget.page.TxtChapter;

/**
 * Created by zhouas666 on 17-6-5.
 * 目录adapter
 */

public class CategoryAdapter extends EasyAdapter<TxtChapter> {
    private int currentSelected = 0;
    @Override
    protected IViewHolder<TxtChapter> onCreateViewHolder(int viewType) {
        return new CategoryHolder();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = super.getView(position, convertView, parent);
        CategoryHolder holder = (CategoryHolder) view.getTag();

        if (position == currentSelected){
            holder.setSelectedChapter();
        }

        return view;
    }

    public void setChapter(int pos){
        currentSelected = pos;
        notifyDataSetChanged();
    }
}
