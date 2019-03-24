package com.thmub.cocobook.ui.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import androidx.annotation.NonNull;
import com.google.android.material.navigation.NavigationView;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.view.menu.MenuBuilder;
import androidx.appcompat.widget.Toolbar;
import android.view.*;
import android.widget.Switch;
import android.widget.TextView;

import butterknife.BindView;
import cn.bmob.v3.BmobUser;
import com.bumptech.glide.Glide;
import com.thmub.cocobook.R;
import com.thmub.cocobook.manager.RxBusManager;
import com.thmub.cocobook.model.bean.CollBookBean;
import com.thmub.cocobook.model.bean.bmob.CocoUser;
import com.thmub.cocobook.model.event.DownloadMessage;
import com.thmub.cocobook.model.local.BookRepository;
import com.thmub.cocobook.model.server.BmobRepository;
import com.thmub.cocobook.base.BaseTabActivity;
import com.thmub.cocobook.ui.fragment.BookShelfFragment;
import com.thmub.cocobook.ui.fragment.BookStoreFragment;
import com.thmub.cocobook.ui.fragment.DiscoverFragment;
import com.thmub.cocobook.utils.*;
import com.thmub.cocobook.widget.CircleImageView;
import io.reactivex.android.schedulers.AndroidSchedulers;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 主界面activity
 */
public class MainActivity extends BaseTabActivity implements NavigationView.OnNavigationItemSelectedListener {

    /*************************Constant**************************/
    private static final int WAIT_INTERVAL = 2000;
    private static final int PERMISSIONS_REQUEST_STORAGE = 1;
    static final String[] PERMISSIONS = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };
    public static final int REQUEST_LAND = 1;
    public static final int REQUEST_USER_INFO = 2;

    /*****************************View********************************/
    @BindView(R.id.drawer)
    DrawerLayout drawer;
    @BindView(R.id.nav_view)
    NavigationView navigationView;

    private View drawerHeader;
    private CircleImageView drawerIv;
    private TextView drawerTvAccount, drawerTvMail;
    private Switch swNightMode;

    private BookShelfFragment bookShelfFragment;

    private final ArrayList<Fragment> mFragmentList = new ArrayList<>();

    /*****************************Variable********************************/
    private PermissionUtils mPermissionUtils;
    private boolean isPrepareFinish = false;
    private CocoUser  currentUser;

    /*****************************Initialization********************************/
    @Override
    protected int getLayoutId() {
        return R.layout.activity_main_tab;
    }

    @Override
    protected boolean initSwipeBackEnable() {
        return false;
    }

    @Override
    protected void setUpToolbar(Toolbar toolbar) {
        super.setUpToolbar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setTitle(UiUtils.getString(R.string.app_name_en));
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        drawerHeader = navigationView.inflateHeaderView(R.layout.drawer_header);
        drawerIv =  drawerHeader.findViewById(R.id.drawer_iv);
        drawerTvAccount =  drawerHeader.findViewById(R.id.drawer_tv_name);
        drawerTvMail =  drawerHeader.findViewById(R.id.drawer_tv_mail);
    }

    @Override
    protected List<Fragment> createTabFragments() {
        bookShelfFragment=new BookShelfFragment();
        mFragmentList.add(bookShelfFragment);
        mFragmentList.add(new BookStoreFragment());
        mFragmentList.add(new DiscoverFragment());
        return mFragmentList;
    }

    @Override
    protected List<String> createTabTitles() {
        String[] titles = getResources().getStringArray(R.array.fragment_title);
        return Arrays.asList(titles);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);
        currentUser=BmobUser.getCurrentUser(CocoUser.class);
    }

    @Override
    protected void initWidget() {
        super.initWidget();
        //实现侧滑菜单状态栏透明
//        getWindow().setStatusBarColor(Color.TRANSPARENT);
//        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);

        refreshDrawerHeader();

        swNightMode = navigationView.getMenu().findItem(R.id.action_night_mode).
                getActionView().findViewById(R.id.view_switch);
        swNightMode.setChecked(isNightTheme());
    }

    @Override
    protected void initClick() {
        super.initClick();
        //监听菜单栏头部
        drawerHeader.setOnClickListener(view -> {
            if (currentUser==null)
                startActivityForResult(new Intent(mContext,UserLoginActivity.class),REQUEST_LAND);
            else
                startActivityForResult(new Intent(mContext,UserInfoActivity.class),REQUEST_USER_INFO);
        });

        //监听左滑菜单栏
        navigationView.setNavigationItemSelectedListener(this);

        //监听夜间模式switch
        swNightMode.setOnCheckedChangeListener((compoundButton, b) -> {
            if (compoundButton.isPressed()) {
                setNightTheme(b);
            }
        });
    }

    @Override
    protected void initEvent() {
        //下载书籍通知
        addDisposable(RxBusManager.getInstance()
                .toObservable(DownloadMessage.class)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        event -> {
                            //使用Toast提示
                            ToastUtils.show(event.message);
                        }
                ));
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        if (swNightMode != null) {
            swNightMode.setChecked(isNightTheme());
        }
    }

    /**
     * 刷新用户信息
     */
    private void refreshDrawerHeader() {
        if (currentUser==null) {
            drawerIv.setImageDrawable(UiUtils.getDrawable(R.mipmap.ic_default_portrait));
            drawerTvAccount.setText("账户");
            drawerTvMail.setText("点我登陆");
            return;
        }
        if (currentUser.getPortrait()!=null)
            Glide.with(mContext).load(currentUser.getPortrait()).into(drawerIv);
        drawerTvAccount.setText(BmobUser.getCurrentUser(CocoUser.class).getUsername());
        drawerTvMail.setText(BmobUser.getCurrentUser(CocoUser.class).getEmail());
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main_search, menu);
        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onPreparePanel(int featureId, View view, Menu menu) {
        if (menu != null && menu instanceof MenuBuilder) {
            try {
                Method method = menu.getClass().
                        getDeclaredMethod("setOptionalIconsVisible", Boolean.TYPE);
                method.setAccessible(true);
                method.invoke(menu, true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return super.onPreparePanel(featureId, view, menu);
    }

    /**
     * 请求权限
     *
     * @param requestCode
     * @param permissions
     * @param grantResults
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case PERMISSIONS_REQUEST_STORAGE: {
                // 如果取消权限，则返回的值为0
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    //跳转到 LocalBookActivity
                    Intent intent = new Intent(this, LocalBookActivity.class);
                    startActivity(intent);

                } else {
                    ToastUtils.show("用户拒绝开启读写权限");
                }
                return;
            }
        }
    }

    /*****************************Event********************************/
    /**
     * 双击退出
     */
    @Override
    public void onBackPressed() {
        if (!isPrepareFinish) {
            mVp.postDelayed(() -> isPrepareFinish = false, WAIT_INTERVAL);
            isPrepareFinish = true;
            ToastUtils.show("再按一次退出");
        } else {
            super.onBackPressed();
        }
    }

    /**
     * 标题栏点击事件
     *
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_search:
                startActivity(new Intent(this, SearchActivity.class));
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * 侧滑菜单点击事件
     *
     * @param item
     * @return
     */
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Class<?> activityClasss = null;
        switch (item.getItemId()) {
            case R.id.action_my_message:
                SnackbarUtils.show(mContext,"暂无消息");
                break;
            case R.id.action_download:
                activityClasss = DownloadActivity.class;
                break;
            case R.id.action_sync_bookshelf:
                if (currentUser==null) break;
                ProgressUtils.show(mContext,"正在同步");
                BmobRepository.getInstance().syncBooks(BmobUser.getCurrentUser(CocoUser.class)
                        , new BmobRepository.SyncBookListener() {
                            @Override
                            public void onSuccess(List<CollBookBean> list) {
                                ProgressUtils.dismiss();
                                BookRepository.getInstance().saveCollBooks(list);
                                bookShelfFragment.refreshShelf();
                                ToastUtils.show("同步完成");
                            }

                            @Override
                            public void onError(Throwable e) {
                                ProgressUtils.dismiss();
                            }
                        });
                break;
            case R.id.action_scan_local_book:

                if (Build.VERSION.SDK_INT > Build.VERSION_CODES.M) {
                    if (mPermissionUtils == null) {
                        mPermissionUtils = new PermissionUtils(this);
                    }

                    //获取读取和写入SD卡的权限
                    if (mPermissionUtils.lacksPermissions(PERMISSIONS)) {
                        //请求权限
                        ActivityCompat.requestPermissions(this, PERMISSIONS, PERMISSIONS_REQUEST_STORAGE);
                        return super.onOptionsItemSelected(item);
                    }
                }

                activityClasss = LocalBookActivity.class;
                break;
            case R.id.action_about:
                activityClasss=AboutActivity.class;
                break;
            case R.id.action_night_mode:
                setNightTheme(!isNightTheme());
                swNightMode.setChecked(isNightTheme());
                break;
            case R.id.action_settings:
                activityClasss=MoreSettingActivity.class;
                break;
            default:
                break;
        }
        drawer.closeDrawer(Gravity.LEFT);
        if (activityClasss != null) {
            startActivity(new Intent(mContext, activityClasss));
        }
        return super.onOptionsItemSelected(item);
    }


    /**
     * 处理返回事件
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //如果登陆账户，则刷新Drawer
        if (requestCode == REQUEST_LAND||requestCode==REQUEST_USER_INFO) {
            currentUser=BmobUser.getCurrentUser(CocoUser.class);
            refreshDrawerHeader();
        }
    }
}
