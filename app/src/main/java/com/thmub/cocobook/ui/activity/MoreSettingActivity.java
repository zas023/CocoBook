package com.thmub.cocobook.ui.activity;

import android.os.Bundle;
import androidx.appcompat.widget.SwitchCompat;
import androidx.appcompat.widget.Toolbar;
import android.widget.RelativeLayout;

import com.thmub.cocobook.R;
import com.thmub.cocobook.base.BaseActivity;
import com.thmub.cocobook.manager.ReadSettingManager;

import butterknife.BindView;

/**
 * Created by zhouas666 on 17-6-6.
 * 更多设置activity
 */

public class MoreSettingActivity extends BaseActivity {
    /*****************************View********************************/
    @BindView(R.id.more_setting_rl_volume)
    RelativeLayout mRlVolume;
    @BindView(R.id.more_setting_sc_volume)
    SwitchCompat mScVolume;
    @BindView(R.id.more_setting_rl_full_screen)
    RelativeLayout mRlFullScreen;
    @BindView(R.id.more_setting_sc_full_screen)
    SwitchCompat mScFullScreen;

    /*****************************Variable********************************/
    private ReadSettingManager mSettingManager;
    private boolean isVolumeTurnPage;
    private boolean isFullScreen;

    /*****************************Initialization********************************/
    @Override
    protected int getLayoutId() {
        return R.layout.activity_more_setting;
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);
        mSettingManager = ReadSettingManager.getInstance();
        isVolumeTurnPage = mSettingManager.isVolumeTurnPage();
        isFullScreen = mSettingManager.isFullScreen();
    }

    @Override
    protected void setUpToolbar(Toolbar toolbar) {
        super.setUpToolbar(toolbar);
        getSupportActionBar().setTitle("阅读设置");
    }

    @Override
    protected void initWidget() {
        super.initWidget();
        initSwitchStatus();
    }

    private void initSwitchStatus(){
        mScVolume.setChecked(isVolumeTurnPage);
        mScFullScreen.setChecked(isFullScreen);
    }

    @Override
    protected void initClick() {
        super.initClick();
        mRlVolume.setOnClickListener(
                (v) -> {
                    if (isVolumeTurnPage){
                        isVolumeTurnPage = false;
                    }
                    else {
                        isVolumeTurnPage = true;
                    }
                    mScVolume.setChecked(isVolumeTurnPage);
                    mSettingManager.setVolumeTurnPage(isVolumeTurnPage);
                }
        );

        mRlFullScreen.setOnClickListener(
                (v) -> {
                    if (isFullScreen){
                        isFullScreen = false;
                    }
                    else {
                        isFullScreen = true;
                    }
                    mScFullScreen.setChecked(isFullScreen);
                    mSettingManager.setFullScreen(isFullScreen);
                }
        );
    }
}
