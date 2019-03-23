package com.thmub.cocobook.ui.adapter.holder;

import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.ImageView;

import com.thmub.cocobook.R;
import com.thmub.cocobook.base.adapter.ViewHolderImpl;
import com.thmub.cocobook.widget.CircleImageView;

/**
 * Created by zhouas666 on 18-1-23.
 */

public class ReadBgHolder extends ViewHolderImpl<Drawable> {

    private CircleImageView mReadBg;
    private ImageView mIvChecked;

    @Override
    public void initView() {
        mReadBg = findById(R.id.read_bg_view);
        mIvChecked = findById(R.id.read_bg_iv_checked);
    }

    @Override
    public void onBind(Drawable data, int pos) {
        mReadBg.setBackground(data);
        mIvChecked.setVisibility(View.GONE);
    }

    @Override
    protected int getItemLayoutId() {
        return R.layout.item_read_bg;
    }

    public void setChecked(){
        mIvChecked.setVisibility(View.VISIBLE);
    }
}
