package com.copasso.cocobook.ui.adapter.view;

import android.widget.ImageView;
import android.widget.TextView;
import com.copasso.cocobook.R;
import com.copasso.cocobook.model.bean.SectionBean;
import com.copasso.cocobook.ui.base.adapter.ViewHolderImpl;

/**
 * Created by zhouas666 on 18-1-23.
 */

public class SquareHolder extends ViewHolderImpl<SectionBean>{

    private ImageView mIvIcon;
    private TextView mTvName;

    @Override
    public void initView(){
        mIvIcon =findById(R.id.square_iv_cover);
        mTvName =findById(R.id.square_tv_name);
    }

    @Override
    public void onBind(SectionBean value, int pos) {
        mIvIcon.setImageResource(value.getDrawableId());
        mTvName.setText(value.getName());
    }

    @Override
    protected int getItemLayoutId() {
        return R.layout.item_square;
    }
}
