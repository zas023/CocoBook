package com.copasso.cocobook.ui.activity;

import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.widget.AppCompatButton;
import android.view.View;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnFocusChange;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.LogInListener;
import cn.bmob.v3.listener.SaveListener;
import com.copasso.cocobook.R;
import com.copasso.cocobook.ui.base.BaseBackActivity;
import com.copasso.cocobook.utils.ProgressUtils;
import com.copasso.cocobook.utils.SnackbarUtils;
import com.copasso.cocobook.utils.StringUtils;
import com.copasso.cocobook.widget.OwlView;

/**
 * Created by zhouas666 on 2017/12/8.
 */
public class LandActivity extends BaseBackActivity {


    @BindView(R.id.land_owl_view)
    OwlView landOwlView;
    @BindView(R.id.land_til_username)
    TextInputLayout landTilUsername;
    @BindView(R.id.land_til_password)
    TextInputLayout landTilPassword;
    @BindView(R.id.land_btn_login)
    AppCompatButton landBtnLogin;
    @BindView(R.id.land_tv_forget)
    TextView landTvForget;
    @BindView(R.id.land_tv_register)
    TextView landTvRegister;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_user_land;
    }

    /**
     * 监听密码输入框的聚焦事件
     *
     * @param view
     * @param b
     */
    @OnFocusChange({R.id.land_til_username, R.id.land_til_password})
    public void onFocusChange(View view, boolean b) {
        if (b) {
            landOwlView.open();
        } else {
            landOwlView.close();
        }
    }

    /**
     * 监听点击事件
     *
     * @param view
     */
    @OnClick({R.id.land_tv_register, R.id.land_btn_login, R.id.land_tv_forget})
    protected void onClick(View view) {
        switch (view.getId()) {
            case R.id.land_btn_login:  //button

                break;
            case R.id.land_tv_register:  //sign

                break;

            case R.id.land_tv_forget:  //忘记密码

                break;

            default:
                break;
        }
    }

}
