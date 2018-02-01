package com.copasso.cocobook.ui.activity;

import android.support.design.widget.Snackbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.OnClick;
import butterknife.OnFocusChange;
import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.LogInListener;
import cn.bmob.v3.listener.SaveListener;
import com.copasso.cocobook.R;
import com.copasso.cocobook.ui.base.BaseBackActivity;
import com.copasso.cocobook.utils.MD5Utils;
import com.copasso.cocobook.utils.ProgressUtils;
import com.copasso.cocobook.utils.SnackbarUtils;
import com.copasso.cocobook.utils.StringUtils;
import com.copasso.cocobook.widget.OwlView;

/**
 * Created by zhouas666 on 2017/12/8.
 */
public class LandActivity extends BaseBackActivity {

    @BindView(R.id.land_et_email)
    EditText landEtEmail;
    @BindView(R.id.land_et_name)
    EditText landEtName;
    @BindView(R.id.land_et_pw1)
    EditText landEtPw1;
    @BindView(R.id.land_et_pw2)
    EditText landEtPw2;
    @BindView(R.id.land_tv_forget)
    TextView landTvForget;
    @BindView(R.id.land_btn_login)
    Button landBtnLogin;
    @BindView(R.id.land_tv_sign)
    TextView landTvSign;
    @BindView(R.id.land_owl_view)
    OwlView landOwlView;



    //是否是登陆操作
    private boolean isLogin = true;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_user_land;
    }

    /**
     * 监听密码输入框的聚焦事件
     * @param view
     * @param b
     */
    @OnFocusChange({R.id.land_et_pw1,R.id.land_et_pw2})
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
    @OnClick({R.id.land_tv_sign, R.id.land_btn_login, R.id.land_tv_forget})
    protected void onClick(View view) {
        switch (view.getId()) {
            case R.id.land_btn_login:  //button
                if (isLogin) {
                    login();  //登陆
                } else {
                    sign();  //注册
                }
                break;
            case R.id.land_tv_sign:  //sign
                if (isLogin) {
                    //置换注册界面
                    landTvSign.setText("Login");
                    landBtnLogin.setText("Sign Up");
                    landEtPw2.setVisibility(View.VISIBLE);
                    landEtEmail.setVisibility(View.VISIBLE);
                } else {
                    //置换登陆界面
                    landTvSign.setText("Sign Up");
                    landBtnLogin.setText("Login");
                    landEtPw2.setVisibility(View.GONE);
                    landEtEmail.setVisibility(View.GONE);
                }
                isLogin = !isLogin;
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
        String username = landEtName.getText().toString();
        String password = landEtPw1.getText().toString();
        if (username.length() == 0 || password.length() == 0) {
            SnackbarUtils.show(mContext, "用户名或密码不能为空");
            return;
        }

        ProgressUtils.show(this, "正在登陆...");
        BmobUser.loginByAccount(username, password,
                new LogInListener<BmobUser>() {
                    @Override
                    public void done(BmobUser bmobUser, BmobException e) {
                        if (e==null){
                            if (bmobUser.getEmailVerified())
                                SnackbarUtils.show(mContext,"登陆成功");
                            else
                                SnackbarUtils.show(mContext,"请到邮箱激活");
                        }else {
                            SnackbarUtils.show(mContext,e.getMessage());
                        }
                        ProgressUtils.dismiss();
                    }
                });
    }

    /**
     * 执行注册动作
     */
    public void sign() {
        String email = landEtEmail.getText().toString();
        String username = landEtName.getText().toString();
        String password = landEtPw1.getText().toString();
        String rpassword = landEtPw2.getText().toString();
        if (email.length() == 0 || username.length() == 0 || password.length() == 0 || rpassword.length() == 0) {
            SnackbarUtils.show(mContext, "请填写必要信息");
            return;
        }
        if (!StringUtils.checkEmail(email)) {
            SnackbarUtils.show(mContext, "请输入正确的邮箱格式");
            return;
        }
        if (!password.equals(rpassword)) {
            SnackbarUtils.show(mContext, "两次密码不一致");
            return;
        }

        ProgressUtils.show(this, "正在注册...");
        BmobUser user = new BmobUser();
        user.setEmail(email);
        user.setUsername(username);
        user.setPassword(password);
        user.signUp(new SaveListener<BmobUser>() {
            @Override
            public void done(BmobUser bmobUser, BmobException e) {
                if (e==null){
                    SnackbarUtils.show(mContext,"注册成功");
                }else {
                    SnackbarUtils.show(mContext,e.getMessage());
                }
                ProgressUtils.dismiss();
            }
        });


    }
}
