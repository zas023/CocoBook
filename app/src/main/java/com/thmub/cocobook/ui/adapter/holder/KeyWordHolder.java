package com.thmub.cocobook.ui.adapter.holder;

import android.widget.TextView;

import com.thmub.cocobook.R;
import com.thmub.cocobook.base.adapter.ViewHolderImpl;

/**
 * Created by zhouas666 on 17-6-2.
 */

public class KeyWordHolder extends ViewHolderImpl<String>{

    private TextView mTvName;

    @Override
    public void initView() {
        mTvName = findById(R.id.keyword_tv_name);
    }

    @Override
    public void onBind(String data, int pos) {
        mTvName.setText(data);
    }

    @Override
    protected int getItemLayoutId() {
        return R.layout.item_keyword;
    }
}
