package com.copasso.cocobook.ui.activity;

import android.content.Intent;
import android.support.design.widget.TextInputLayout;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.OnClick;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.LogInListener;
import com.copasso.cocobook.R;
import com.copasso.cocobook.model.bean.bmob.CocoUser;
import com.copasso.cocobook.ui.base.BaseBackActivity;
import com.copasso.cocobook.utils.ProgressUtils;
import com.copasso.cocobook.utils.SnackbarUtils;
import com.copasso.cocobook.widget.OwlView;

/**
 * Created by zhouas666 on 2017/12/8.
 * 登陆activity
 */
public class UserLoginActivity extends BaseBackActivity {


    @BindView(R.id.land_owl_view)
    OwlView landOwlView;
    @BindView(R.id.land_til_username)
    TextInputLayout landTilUsername;
    @BindView(R.id.land_til_password)
    TextInputLayout landTilPassword;
    @BindView(R.id.land_btn_login)
    Button landBtnLogin;
    @BindView(R.id.land_tv_forget)
    TextView landTvForget;
    @BindView(R.id.land_tv_register)
    TextView landTvRegister;
    /*****************************视图********************************/
    private EditText mEdtUsername;
    private EditText mEdtPassword;

    /*****************************初始化********************************/
    @Override
    protected int getLayoutId() {
        return R.layout.activity_user_land;
    }

    @Override
    protected void initWidget() {
        super.initWidget();
        mEdtUsername=landTilUsername.getEditText();
        mEdtPassword=landTilPassword.getEditText();
        //监听密码输入框的聚焦事件
        mEdtPassword.setOnFocusChangeListener((view, b) -> {
            if (b) {
                landOwlView.open();
            } else {
                landOwlView.close();
            }
        });
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
                login();
                break;
            case R.id.land_tv_register:  //sign
                startActivity(new Intent(mContext,UserRegisterActivity.class));
                break;

            case R.id.land_tv_forget:  //忘记密码

                break;

            default:
                break;
        }
    }

    /**
     * 执行登陆动作
     */
    public void login() {
        String username = mEdtUsername.getText().toString();
        String password = mEdtPassword.getText().toString();
        if (username.length() == 0 || password.length() == 0) {
            SnackbarUtils.show(mContext, "用户名或密码不能为空");
            return;
        }

        ProgressUtils.show(this, "正在登陆...");

        BmobUser.loginByAccount(username, password, new LogInListener<CocoUser>() {
            @Override
            public void done(CocoUser cocoUser, BmobException e) {
                ProgressUtils.dismiss();
                if (e==null){
                    if (cocoUser.getEmailVerified()){
                        setResult(MainActivity.REQUEST_LAND);
                        finish();
                    }else {
                        SnackbarUtils.show(mContext, "请到邮箱激活账户后登陆");
                    }
                }else
                    SnackbarUtils.show(mContext, e.getMessage());
            }
        });
    }

}
