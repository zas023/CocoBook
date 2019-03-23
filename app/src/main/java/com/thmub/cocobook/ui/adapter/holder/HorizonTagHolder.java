package com.thmub.cocobook.ui.adapter.holder;

import android.widget.TextView;

import com.thmub.cocobook.R;
import com.thmub.cocobook.base.adapter.ViewHolderImpl;

/**
 * Created by zhouas666 on 18-2-2.
 */

public class HorizonTagHolder extends ViewHolderImpl<String> {

    private TextView mTvName;

    @Override
    public void initView() {
        mTvName = findById(R.id.horizon_tag_tv_name);
    }

    @Override
    public void onBind(String value, int pos) {
        mTvName.setText(value);
        mTvName.setTextColor(getContext().getResources().getColor(R.color.textAssist));
    }

    public void setSelectedTag(){
        mTvName.setTextColor(getContext().getResources().getColor(R.color.light_red));
    }

    @Override
    protected int getItemLayoutId() {
        return R.layout.item_horizon_tag;
    }
}
