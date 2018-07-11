package com.copasso.cocobook.ui.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;

import com.copasso.cocobook.model.bean.BookChapterBean;
import com.copasso.cocobook.ui.adapter.view.CategoryHolder;
import com.copasso.cocobook.base.EasyAdapter;
import com.copasso.cocobook.base.adapter.IViewHolder;
import com.copasso.cocobook.widget.page.TxtChapter;

import java.util.List;

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
