package com.thmub.cocobook.ui.view;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.thmub.cocobook.R;
import com.thmub.cocobook.base.adapter.QuickAdapter;
import com.thmub.cocobook.ui.activity.SearchActivity;

/**
 * Created by Zhouas666 on 2019-03-23
 * Github: https://github.com/zas023
 */
public class BookShelfFooter implements QuickAdapter.ItemView {
    private Context mContext;

    public BookShelfFooter(Context mContext) {
        this.mContext = mContext;
    }

    @Override
    public View onCreateView(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.footer_book_shelf, parent, false);
        view.setOnClickListener((v) -> {
                    mContext.startActivity(new Intent(mContext, SearchActivity.class));
                }
        );
        return view;
    }

    @Override
    public void onBindView(View view) {
    }
}
