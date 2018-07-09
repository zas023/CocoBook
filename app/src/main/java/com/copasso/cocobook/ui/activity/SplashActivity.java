package com.copasso.cocobook.ui.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.widget.AppCompatImageView;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import com.copasso.cocobook.BuildConfig;
import com.copasso.cocobook.R;
import com.copasso.cocobook.utils.PermissionsChecker;
import com.copasso.cocobook.manager.ReadSettingManager;
import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * Created by zhouas666 on 18-2-3.
 * splash activity
 */

public class SplashActivity extends AppCompatActivity {
    /*************************常量******************************/
    private static final int WAIT_TIME = 3;
    private static final int PERMISSIONS_REQUEST_STORAGE = 0;
    //需要申请的权限
    static final String[] PERMISSIONS = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };

    @BindView(R.id.splash_tv_skip)
    TextView splashTvSkip;
    @BindView(R.id.splash_iv_smile)
    AppCompatImageView splashIvSmile;
    @BindView(R.id.splash_tv_date)
    TextView splashTvDate;
    @BindView(R.id.splash_llt_welcome)
    LinearLayout splashLltWelcome;
    @BindView(R.id.splash_tv_version)
    TextView splashTvVersion;
    @BindView(R.id.splash_flt_content)
    FrameLayout splashFltContent;
    /*************************视图******************************/
    private Unbinder unbinder;
    private Subscription subscribe;
    private PermissionsChecker mPermissionsChecker;

    /*************************参数******************************/
    private boolean isSkip = false;

    /*************************初始化******************************/
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        unbinder = ButterKnife.bind(this);

        initWidget();

        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.M) {
            // 检查该权限是否已经获取
            int i1 = ContextCompat.checkSelfPermission(this, PERMISSIONS[0]);
            int i2 = ContextCompat.checkSelfPermission(this, PERMISSIONS[0]);
            // 权限是否已经 授权 GRANTED---授权  DINIED---拒绝
            if (i1 == PackageManager.PERMISSION_DENIED||i2 == PackageManager.PERMISSION_DENIED) {
                // 如果没有授予该权限，就去提示用户请求
                mPermissionsChecker = new PermissionsChecker(this);
                //获取读取和写入SD卡的权限
                if (mPermissionsChecker.lacksPermissions(PERMISSIONS)) {
                    //是否应该展示详细信息
                    if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                            Manifest.permission.READ_EXTERNAL_STORAGE)) {
                    } else {
                        //请求权限
                        ActivityCompat.requestPermissions(this, PERMISSIONS, PERMISSIONS_REQUEST_STORAGE);
                    }
                }
                startCount(WAIT_TIME);
            }else
                startCount(WAIT_TIME);
        } else {
            //开始计时
            startCount(WAIT_TIME);
        }
    }



    private void initWidget() {
        //是否为夜间模式
        if(ReadSettingManager.getInstance().isNightMode())
            getDelegate().setLocalNightMode(AppCompatDelegate.MODE_NIGHT_YES);

        splashTvDate.setText(new SimpleDateFormat("yyyy年MM月dd日，EEEE").format(new Date()));
        splashTvVersion.setText(BuildConfig.VERSION_NAME);
        splashLltWelcome.setAnimation(AnimationUtils.loadAnimation(this, R.anim.alpha_in));
    }

    private void startCount(int second) {
        //开始计时后监听是否跳过
        splashTvSkip.setOnClickListener((view) -> skipToMain());

        subscribe = Observable.interval(0, 1, TimeUnit.SECONDS)
                .map(increaseTime -> second - increaseTime.intValue())
                .take(second + 1)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(integer -> {
                    if (integer == 0) {
                        skipToMain();
                    } else {
                        splashTvSkip.setText("跳过(" + integer + ")");
                    }
                });
    }

    /**
     * 跳转
     */
    private  void skipToMain() {
        if (!isSkip) {
            isSkip = true;
            startActivity(new Intent(this, MainActivity.class));
            finish();
        }
    }

    /*******************************************************/
    /**
     * 获取权限返回
     *
     * @param requestCode
     * @param permissions
     * @param grantResults
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case PERMISSIONS_REQUEST_STORAGE:
                startCount(WAIT_TIME);
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        isSkip = true;
        if(subscribe!=null&&!subscribe.isUnsubscribed()){
            subscribe.unsubscribe();
        }
        unbinder.unbind();
    }
}
