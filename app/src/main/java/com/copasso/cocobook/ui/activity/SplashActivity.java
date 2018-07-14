package com.copasso.cocobook.ui.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.widget.AppCompatImageView;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import butterknife.BindView;
import com.copasso.cocobook.BuildConfig;
import com.copasso.cocobook.R;
import com.copasso.cocobook.base.BaseActivity;
import com.copasso.cocobook.utils.Constant;
import com.copasso.cocobook.utils.SharedPreUtils;
import com.copasso.cocobook.utils.SystemBarUtils;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * Created by zhouas666 on 18-2-3.
 * splash activity
 */

public class SplashActivity extends BaseActivity {
    /*************************常量******************************/
    private static final int WAIT_TIME = 3;

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

    /*************************参数******************************/
    private boolean isSkip = false;

    /*************************初始化******************************/

    @Override
    protected int getLayoutId() {
        return R.layout.activity_splash;
    }

    @Override
    public void initTheme() {
        super.initTheme();
        SystemBarUtils.hideStableStatusBar(this);
    }

    @Override
    protected boolean initSwipeBackEnable() {
        return false;
    }

    protected void initWidget() {
        //判断是否为第一次
        String sex = SharedPreUtils.getInstance().getString(Constant.SHARED_SEX);
        if (sex.equals("")){
            startActivity(new Intent(mContext,WelcomeActivity.class));
//            finish();
        }else {
            splashTvDate.setText(new SimpleDateFormat("yyyy年MM月dd日，EEEE").format(new Date()));
            splashTvVersion.setText(BuildConfig.VERSION_NAME);
            splashLltWelcome.setAnimation(AnimationUtils.loadAnimation(this, R.anim.alpha_in));

            //开始倒计时
            startCount(WAIT_TIME);
        }
    }

    private void startCount(int second) {
        //开始计时后监听是否跳过
        splashTvSkip.setOnClickListener((view) -> skipToMain());

        Observable.interval(0, 1, TimeUnit.SECONDS)
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

}
