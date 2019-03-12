package com.thmub.cocobook.ui.activity;

import androidx.appcompat.widget.Toolbar;
import android.view.View;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.OnClick;
import com.thmub.cocobook.R;
import com.thmub.cocobook.base.BaseActivity;
import com.thmub.cocobook.widget.CircleImageView;

/**
 * Created by zhouas666 on 2017/12/8.
 * 关于activity
 */
public class AboutActivity extends BaseActivity {

    /*****************************View********************************/
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


    /*****************************Initialization********************************/
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

    /*****************************Transaction********************************/
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
