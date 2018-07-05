package com.copasso.cocobook.ui.activity;

import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.OnClick;
import com.copasso.cocobook.R;
import com.copasso.cocobook.base.BaseBackActivity;
import com.copasso.cocobook.widget.CircleImageView;

/**
 * Created by zhouas666 on 2017/12/8.
 * 关于activity
 */
public class AboutActivity extends BaseBackActivity {

    /*****************************视图********************************/
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.tv_version_name)
    TextView tvVersionName;
    @BindView(R.id.about_tv_copyright)
    TextView aboutTvCopyright;
    @BindView(R.id.about_cv_avatar)
    CircleImageView aboutCvAvatar;
    @BindView(R.id.about_tv_name)
    TextView aboutTvName;


    /*****************************初始化********************************/
    @Override
    protected int getLayoutId() {
        return R.layout.activity_about;
    }

    @Override
    protected void initWidget() {
        super.initWidget();
    }

    @Override
    protected void setUpToolbar(Toolbar toolbar) {
        super.setUpToolbar(toolbar);
        getSupportActionBar().setTitle("关于");
    }

    /*****************************事件处理********************************/
    /**
     * 监听点击事件
     *
     * @param view
     */
    @OnClick({})
    protected void onClick(View view) {
        switch (view.getId()) {

            default:
                break;
        }
    }

}
