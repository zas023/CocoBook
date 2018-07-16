package com.thmub.cocobook.base;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.widget.Toolbar;
import com.thmub.cocobook.R;
import com.thmub.cocobook.manager.ReadSettingManager;
import com.thmub.cocobook.utils.StatusBarCompat;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import me.imid.swipebacklayout.lib.app.SwipeBackActivity;

/**
 * Created by PC on 2016/9/8.
 * 继承自SwipeBackActivity,默认开启左滑手势
 */
public abstract class BaseActivity extends SwipeBackActivity {
    protected static String TAG;

    protected Activity mContext;

    protected CompositeDisposable mDisposable;

    //ButterKnife
    protected Unbinder unbinder;

    protected Toolbar mToolbar;

    /****************************abstract area*************************************/

    @LayoutRes
    protected abstract int getLayoutId();


    /************************初始化************************************/
    protected void addDisposable(Disposable d) {
        if (mDisposable == null) {
            mDisposable = new CompositeDisposable();
        }
        mDisposable.add(d);
    }

    /**
     * 配置Toolbar
     */
    protected void setUpToolbar(Toolbar toolbar) {
    }

    /**
     * 初始化数据
     *
     * @param savedInstanceState
     */
    protected void initData(Bundle savedInstanceState) {
    }

    /**
     * 初始化零件
     */
    protected void initWidget() {
    }

    /**
     * 初始化事件
     */
    protected void initEvent() {
    }

    /**
     * 初始化点击事件
     */
    protected void initClick() {
    }

    /**
     * 逻辑使用区
     */
    protected void processLogic() {
    }

    /**
     * 是否开启左滑手势
     *
     * @return
     */
    protected boolean initSwipeBackEnable() {
        return true;
    }

    /**
     * @return 是否夜间模式
     */
    protected boolean isNightTheme() {
        return ReadSettingManager.getInstance().isNightMode();
    }

    protected void setNightTheme(boolean isNightMode) {
        ReadSettingManager.getInstance().setNightMode(isNightMode);
        initTheme();
    }

    /*************************lifecycle area*****************************************************/

    /**
     * 初始化主题
     */
    public void initTheme() {
        if (isNightTheme()) {
            getDelegate().setLocalNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        } else {
            getDelegate().setLocalNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initTheme();
        setContentView(getLayoutId());
        initData(savedInstanceState);
        mContext = this;
        // 设置 TAG
        TAG = this.getClass().getSimpleName();
        unbinder = ButterKnife.bind(this);
        //左滑手势
        setSwipeBackEnable(initSwipeBackEnable());
        //init
        initToolbar();
        initWidget();
        initEvent();
        initClick();
        processLogic();
    }

    private void initToolbar() {
        //更严谨是通过反射判断是否存在Toolbar
        mToolbar = ButterKnife.findById(this, R.id.toolbar);
        if (mToolbar != null) {
            supportActionBar(mToolbar);
            setUpToolbar(mToolbar);
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
        if (mDisposable != null) {
            mDisposable.dispose();
        }
    }

    /**************************used method area*******************************************/

    protected void startActivity(Class<? extends AppCompatActivity> activity) {
        Intent intent = new Intent(this, activity);
        startActivity(intent);
    }

    protected ActionBar supportActionBar(Toolbar toolbar) {
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowHomeEnabled(true);
        }
        mToolbar.setNavigationOnClickListener(
                (v) -> finish()
        );
        return actionBar;
    }

    protected void setStatusBarColor(int statusColor) {
        StatusBarCompat.compat(this, ContextCompat.getColor(this, statusColor));
    }
}
