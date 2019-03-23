package com.thmub.cocobook.ui.adapter.holder;

import android.widget.ImageView;
import android.widget.TextView;
import com.thmub.cocobook.R;
import com.thmub.cocobook.model.bean.SectionBean;
import com.thmub.cocobook.base.adapter.ViewHolderImpl;

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
