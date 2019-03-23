package com.thmub.cocobook.ui.adapter;

import android.graphics.drawable.Drawable;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;

import com.thmub.cocobook.base.adapter.BaseRecyclerAdapter;
import com.thmub.cocobook.ui.adapter.holder.ReadBgHolder;
import com.thmub.cocobook.base.adapter.BaseViewHolder;
import com.thmub.cocobook.base.adapter.IViewHolder;

/**
 * Created by zhouas666 on 18-1-23.
 */

public class ReadBgAdapter extends BaseRecyclerAdapter<Drawable> {
    private int currentChecked;

    @Override
    protected IViewHolder<Drawable> createViewHolder(int viewType) {
        return new ReadBgHolder();
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        super.onBindViewHolder(holder, position);
        IViewHolder iHolder = ((BaseViewHolder) holder).holder;
        ReadBgHolder readBgHolder = (ReadBgHolder) iHolder;
        if (currentChecked == position){
            readBgHolder.setChecked();
        }
    }

    public void setBgChecked(int pos){
        currentChecked = pos;

    }

    @Override
    protected void onItemClick(View v, int pos) {
        super.onItemClick(v, pos);
        currentChecked = pos;
        notifyDataSetChanged();
    }
}
