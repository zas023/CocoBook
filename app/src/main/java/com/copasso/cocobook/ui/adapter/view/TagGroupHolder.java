package com.copasso.cocobook.ui.adapter.view;

import android.widget.TextView;

import com.copasso.cocobook.R;
import com.copasso.cocobook.ui.base.adapter.ViewHolderImpl;

/**
 * Created by zhouas666 on 18-2-5.
 */

public class TagGroupHolder extends ViewHolderImpl<String> {
    private TextView mTvGroupName;

    @Override
    public void initView() {
        mTvGroupName = findById(R.id.tag_group_name);
    }

    @Override
    public void onBind(String value, int pos) {
        mTvGroupName.setText(value);
    }

    @Override
    protected int getItemLayoutId() {
        return R.layout.item_tag_group;
    }
}
