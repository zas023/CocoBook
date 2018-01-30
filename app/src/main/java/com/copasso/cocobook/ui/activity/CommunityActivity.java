package com.copasso.cocobook.ui.activity;

import android.app.Activity;
import android.support.v7.widget.Toolbar;

import com.copasso.cocobook.R;
import com.copasso.cocobook.ui.base.BaseActivity;
import com.copasso.cocobook.ui.base.BaseBackActivity;

/**
 * Created by zhouas666 on 17-5-26.
 */

public class CommunityActivity extends BaseBackActivity {

    @Override
    protected int getLayoutId() {
        return R.layout.activity_community;
    }

    @Override
    protected void setUpToolbar(Toolbar toolbar) {
        super.setUpToolbar(toolbar);
        getSupportActionBar().setTitle("社区");
    }
}
