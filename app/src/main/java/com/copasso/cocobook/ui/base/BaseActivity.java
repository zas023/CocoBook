package com.copasso.cocobook.ui.base;

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
import com.copasso.cocobook.R;
import com.copasso.cocobook.utils.ReadSettingManager;
import com.copasso.cocobook.utils.StatusBarCompat;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/**
 * Created by PC on 2016/9/8.
 */
public abstract class BaseActivity extends AppCompatActivity {
    protected static String TAG;

    protected Activity mContext;

    protected CompositeDisposable mDisposable;

    protected boolean nightMode=ReadSettingManager.getInstance().isNightMode();

    //ButterKnife
    private Toolbar mToolbar;

    private Unbinder unbinder;
    /****************************abstract area*************************************/

    @LayoutRes
    protected abstract int getLayoutId();

    /************************init area************************************/
    protected void addDisposable(Disposable d){
        if (mDisposable == null){
            mDisposable = new CompositeDisposable();
        }
        mDisposable.add(d);
    }

    /**
     * 配置Toolbar
     */
    protected void setUpToolbar(Toolbar toolbar){
    }

    protected void initData(Bundle savedInstanceState){
    }
    /**
     * 初始化主题
     */
    public void initTheme() {
        if (nightMode) {
            getDelegate().setLocalNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        } else {
            getDelegate().setLocalNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }
    }

    /**
     * 初始化零件
     */
    protected void initWidget() {

    }
    /**
     * 初始化点击事件
     */
    protected void initClick(){
    }
    /**
     * 逻辑使用区
     */
    protected void processLogic(){
    }

    /*************************lifecycle area*****************************************************/

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initTheme();
        setContentView(getLayoutId());
        initData(savedInstanceState);
        mContext=this;
        // 设置 TAG
        TAG = this.getClass().getSimpleName();
        unbinder = ButterKnife.bind(this);
        initToolbar();
        initWidget();
        initClick();
        processLogic();

    }

    private void initToolbar(){
        //更严谨是通过反射判断是否存在Toolbar
        mToolbar = ButterKnife.findById(this, R.id.toolbar);
        if (mToolbar != null){
            supportActionBar(mToolbar);
            setUpToolbar(mToolbar);
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
        if (mDisposable != null){
            mDisposable.dispose();
        }
    }

    /**************************used method area*******************************************/

    protected void startActivity(Class<? extends AppCompatActivity> activity){
        Intent intent = new Intent(this,activity);
        startActivity(intent);
    }

    protected ActionBar supportActionBar(Toolbar toolbar){
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowHomeEnabled(true);
        }
        mToolbar.setNavigationOnClickListener(
                (v) -> finish()
        );
        return actionBar;
    }

    protected void setStatusBarColor(int statusColor){
        StatusBarCompat.compat(this, ContextCompat.getColor(this, statusColor));
    }
}
