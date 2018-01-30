package com.copasso.cocobook.ui.base.adapter;

import android.view.View;
import android.view.ViewGroup;

/**
 * Created by zhouas666 on 17-5-17.
 */

public interface IViewHolder<T> {
    View createItemView(ViewGroup parent);
    void initView();
    void onBind(T data, int pos);
    void onClick();
}
