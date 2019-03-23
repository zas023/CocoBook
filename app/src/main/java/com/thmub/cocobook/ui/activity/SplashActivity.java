package com.thmub.cocobook.ui.activity;

import android.content.Intent;

import androidx.appcompat.widget.AppCompatImageView;

import android.os.Handler;
import android.os.Message;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import butterknife.BindView;

import com.thmub.cocobook.BuildConfig;
import com.thmub.cocobook.R;
import com.thmub.cocobook.base.BaseActivity;
import com.thmub.cocobook.utils.Constant;
import com.thmub.cocobook.utils.SharedPreUtils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by zhouas666 on 18-2-3.
 * splash activity
 */

public class SplashActivity extends BaseActivity {
    /*****************************Constant********************************/
    private static final int WAIT_TIME = 3;

    /*****************************View********************************/
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

    /*****************************Variable********************************/
    private boolean isSkip = false;
    private int timer = 3;

//    private Handler handler=new Handler(){
//        @Override
//        public void handleMessage(Message msg) {
//            super.handleMessage(msg);
//        }
//    };

    //上面的用法可能会造成内存泄漏
    //https://blog.csdn.net/m0_37678565/article/details/79623620
    private Handler mHandler = new Handler(new Handler.Callback() {

        @Override
        public boolean handleMessage(Message msg) {
            if (timer > 0) {
                splashTvSkip.setText("跳过(" + timer + ")");
                timer--;
            } else {
                skipToMain();
            }
            return true;
        }
    });

    /*****************************Initialization********************************/

    @Override
    protected int getLayoutId() {
        return R.layout.activity_splash;
    }

    @Override
    public void initTheme() {
        super.initTheme();
    }

    @Override
    protected boolean initSwipeBackEnable() {
        return false;
    }

    protected void initWidget() {
        //判断是否为第一次
        String sex = SharedPreUtils.getInstance().getString(Constant.SHARED_SEX);
        if (sex.equals("")) {
            startActivity(new Intent(mContext, WelcomeActivity.class));
            finish();
        } else {
            splashTvDate.setText(new SimpleDateFormat("yyyy年MM月dd日，EEEE").format(new Date()));
            splashTvVersion.setText(BuildConfig.VERSION_NAME);
            splashLltWelcome.setAnimation(AnimationUtils.loadAnimation(this, R.anim.alpha_in));
            //开始计时
            startTimer();
        }
    }

    @Override
    protected void initClick() {
        super.initClick();
        //监听是否跳过
        splashTvSkip.setOnClickListener((view) -> skipToMain());
    }

    /**
     * 计时
     */
    private void startTimer() {
        //每隔1s执行一次
        new Thread(() -> {
            while (true){
                try {
                    Message msg = new Message();
                    msg.what = 1;
                    mHandler.sendMessage(msg);
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    /**
     * 跳转
     */
    private void skipToMain() {
        if (!isSkip) {
            isSkip = true;
            startActivity(new Intent(mContext, MainActivity.class));
            finish();
        }
    }

}
