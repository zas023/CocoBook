package com.copasso.cocobook.ui.activity;

import android.Manifest;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.view.menu.MenuBuilder;
import android.support.v7.widget.Toolbar;
import android.view.*;
import android.widget.Switch;
import android.widget.TextView;

import butterknife.BindView;
import cn.bmob.v3.BmobUser;
import com.bumptech.glide.Glide;
import com.copasso.cocobook.R;
import com.copasso.cocobook.manager.RxBusManager;
import com.copasso.cocobook.model.bean.CollBookBean;
import com.copasso.cocobook.model.bean.bmob.CocoUser;
import com.copasso.cocobook.model.event.DownloadMessage;
import com.copasso.cocobook.model.local.BookRepository;
import com.copasso.cocobook.model.server.BmobRepository;
import com.copasso.cocobook.base.BaseTabActivity;
import com.copasso.cocobook.ui.fragment.BookShelfFragment;
import com.copasso.cocobook.ui.fragment.BookStoreFragment;
import com.copasso.cocobook.ui.fragment.DiscoverFragment;
import com.copasso.cocobook.utils.*;
import com.copasso.cocobook.ui.dialog.SexChooseDialog;
import com.copasso.cocobook.widget.CircleImageView;
import io.reactivex.android.schedulers.AndroidSchedulers;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 主界面activity
 */
public class MainActivity extends BaseTabActivity implements NavigationView.OnNavigationItemSelectedListener {

    /*************************常量**************************/
    private static final int WAIT_INTERVAL = 2000;
    private static final int PERMISSIONS_REQUEST_STORAGE = 1;
    static final String[] PERMISSIONS = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };
    public static final int REQUEST_LAND = 1;
    public static final int REQUEST_USER_INFO = 2;

    @BindView(R.id.drawer)
    DrawerLayout drawer;
    @BindView(R.id.nav_view)
    NavigationView navigationView;
    /*************************视图**************************/
    private View drawerHeader;
    private CircleImageView drawerIv;
    private TextView drawerTvAccount, drawerTvMail;
    private Switch swNightMode;

    private BookShelfFragment bookShelfFragment;

    private final ArrayList<Fragment> mFragmentList = new ArrayList<>();
    private PermissionsChecker mPermissionsChecker;
    /*************************参数**************************/
    private boolean isPrepareFinish = false;
//    private CocoUser  currentUser=BmobUser.getCurrentUser(CocoUser.class);
    private CocoUser  currentUser;

    /*************************初始化**************************/
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
        drawerIv = (CircleImageView) drawerHeader.findViewById(R.id.drawer_iv);
        drawerTvAccount = (TextView) drawerHeader.findViewById(R.id.drawer_tv_name);
        drawerTvMail = (TextView) drawerHeader.findViewById(R.id.drawer_tv_mail);
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
    protected void initWidget() {
        super.initWidget();
        //实现侧滑菜单状态栏透明
        getWindow().setStatusBarColor(Color.TRANSPARENT);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);

        //性别选择框
        showSexChooseDialog();

        refreshDrawerHeader();

        swNightMode = (Switch) navigationView.getMenu().findItem(R.id.action_night_mode).
                getActionView().findViewById(R.id.view_switch);
        swNightMode.setChecked(isNightTheme());

        //监听菜单栏头部
        drawerHeader.setOnClickListener(view -> {
            if (BmobUser.getCurrentUser()==null)
                startActivityForResult(new Intent(mContext,UserLoginActivity.class),REQUEST_LAND);
            else
                startActivityForResult(new Intent(mContext,UserInfoActivity.class),REQUEST_USER_INFO);
        });

        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    protected void initClick() {
        super.initClick();
        swNightMode.setOnCheckedChangeListener((compoundButton, b) -> {
            if (compoundButton.isPressed()) {
                setNightTheme(b);
                recreate();
            }
        });
    }

    @Override
    protected void initEvent() {
        //下载书籍
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

    /**
     * 刷新用户信息
     */
    private void refreshDrawerHeader() {
        if (currentUser==null) {
            drawerIv.setImageDrawable(UiUtils.getDrawable(R.mipmap.ic_def_icon));
            drawerTvAccount.setText("账户");
            drawerTvMail.setText("点我登陆");
            return;
        }
        if (currentUser.getPortrait()!=null)
            Glide.with(mContext).load(currentUser.getPortrait()).into(drawerIv);
        drawerTvAccount.setText(BmobUser.getCurrentUser().getUsername());
        drawerTvMail.setText(BmobUser.getCurrentUser().getEmail());
    }

    /**
     * 首次进入应用，性别选择
     */
    private void showSexChooseDialog() {
        String sex = SharedPreUtils.getInstance().getString(Constant.SHARED_SEX);
        if (sex.equals("")) {
            mVp.postDelayed(() -> {
                Dialog dialog = new SexChooseDialog(this);
                dialog.show();
            }, 500);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_search, menu);
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
                BmobRepository.getInstance().syncBooks(BmobUser.getCurrentUser()
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
                    if (mPermissionsChecker == null) {
                        mPermissionsChecker = new PermissionsChecker(this);
                    }

                    //获取读取和写入SD卡的权限
                    if (mPermissionsChecker.lacksPermissions(PERMISSIONS)) {
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
                swNightMode.setChecked(!isNightTheme());
                setNightTheme(!isNightTheme());
                recreate();
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
     * 切换夜间模式
     */
    private void switchNightMode(){
//        if(isNightMode)
//            getDelegate().setLocalNightMode(AppCompatDelegate.MODE_NIGHT_NO);
//        else
//            getDelegate().setLocalNightMode(AppCompatDelegate.MODE_NIGHT_YES);
//        ReadSettingManager.getInstance().setNightMode(isNightMode);
//        //重绘当前activity
//        recreate();
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
