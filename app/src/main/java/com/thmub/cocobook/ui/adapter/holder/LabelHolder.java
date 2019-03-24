package com.thmub.cocobook.ui.adapter.holder;

import android.widget.TextView;

import com.thmub.cocobook.R;
import com.thmub.cocobook.base.adapter.ViewHolderImpl;

/**
 * Created by Zhouas666 on 2019-03-25
 * Github: https://github.com/zas023
 */
public class LabelHolder extends ViewHolderImpl<String> {

    private TextView mTvName;

    @Override
    public void initView() {
        mTvName = findById(R.id.label_tv);
    }

    @Override
    public void onBind(String data, int pos) {
        mTvName.setText(data);
    }

    @Override
    protected int getItemLayoutId() {
        return R.layout.item_label_text;
    }

}
