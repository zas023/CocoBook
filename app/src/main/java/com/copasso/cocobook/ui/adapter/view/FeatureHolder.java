package com.copasso.cocobook.ui.adapter.view;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.copasso.cocobook.R;
import com.copasso.cocobook.model.bean.FeatureBean;
import com.copasso.cocobook.model.bean.SectionBean;
import com.copasso.cocobook.ui.base.adapter.ViewHolderImpl;

/**
 * Created by zhouas666 on 18-1-23.
 */

public class FeatureHolder extends ViewHolderImpl<FeatureBean>{

    private ImageView mIvIcon;
    private TextView mTvName;

    @Override
    public void initView(){
        mIvIcon =findById(R.id.section_iv_icon);
        mTvName =findById(R.id.section_tv_name);
    }

    @Override
    public void onBind(FeatureBean value, int pos) {
        mIvIcon.setVisibility(View.GONE);
        mTvName.setText(value.getTitle());
    }

    @Override
    protected int getItemLayoutId() {
        return R.layout.item_section;
    }
}
