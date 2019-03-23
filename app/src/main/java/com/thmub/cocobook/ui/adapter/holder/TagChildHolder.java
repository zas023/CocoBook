package com.thmub.cocobook.ui.adapter.holder;

import androidx.core.content.ContextCompat;
import android.widget.TextView;

import com.thmub.cocobook.R;
import com.thmub.cocobook.base.adapter.ViewHolderImpl;

/**
 * Created by zhouas666 on 18-2-2.
 */

public class TagChildHolder extends ViewHolderImpl<String> {
    private TextView mTvName;
    private int mSelectTag = -1;

    @Override
    public void initView(){
        mTvName = findById(R.id.tag_child_btn_name);
    }

    @Override
    public void onBind(String value, int pos) {
        mTvName.setText(value);
        //这里要重置点击事件
        if (mSelectTag == pos){
            mTvName.setTextColor(ContextCompat.getColor(getContext(),R.color.light_red));
        }
        else {
            mTvName.setTextColor(ContextCompat.getColor(getContext(),R.color.textPrimary));
        }
    }

    public void setTagSelected(int pos){
        mSelectTag = pos;
    }

    @Override
    protected int getItemLayoutId() {
        return R.layout.item_tag_child;
    }
}