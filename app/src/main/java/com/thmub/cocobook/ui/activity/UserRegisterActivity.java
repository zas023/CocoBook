package com.thmub.cocobook.ui.activity;

import com.google.android.material.textfield.TextInputLayout;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.OnClick;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;
import com.thmub.cocobook.R;
import com.thmub.cocobook.base.BaseActivity;
import com.thmub.cocobook.model.bean.bmob.CocoUser;
import com.thmub.cocobook.utils.ProgressUtils;
import com.thmub.cocobook.utils.SnackbarUtils;
import com.thmub.cocobook.utils.StringUtils;
import com.thmub.cocobook.widget.OwlView;

/**
 * Created by zhouas666 on 2018-2-3.
 * 注册activity
 */
public class UserRegisterActivity extends BaseActivity {
    
    @BindView(R.id.register_owl_view)
    OwlView registerOwlView;
    @BindView(R.id.register_til_email)
    TextInputLayout registerTilEmail;
    @BindView(R.id.register_til_username)
    TextInputLayout registerTilUsername;
    @BindView(R.id.register_til_password)
    TextInputLayout registerTilPassword;
    @BindView(R.id.register_btn_login)
    Button registerBtnLogin;
    @BindView(R.id.register_tv_forget)
    TextView registerTvForget;
    @BindView(R.id.register_tv_login)
    TextView registerTvRegister;
    /*************************视图****************************/
    private EditText mEdtEmail;
    private EditText mEdtUsername;
    private EditText mEdtPassword;

    /*************************初始化****************************/
    @Override
    protected int getLayoutId() {
        return R.layout.fragment_user_register;
    }

    @Override
    protected void initWidget() {
        super.initWidget();
        mEdtEmail=registerTilEmail.getEditText();
        mEdtUsername=registerTilUsername.getEditText();
        mEdtPassword=registerTilPassword.getEditText();

        //监听邮箱输入框的聚焦事件
        mEdtEmail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String mail = charSequence.toString();
                if (!StringUtils.checkEmail(mail)) {
                    mEdtEmail.setError("请输入正确的邮箱地址");
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        //监听密码输入框的聚焦事件
        mEdtPassword.setOnFocusChangeListener((view, b) -> {
            if (b) {
                registerOwlView.open();
            } else {
                registerOwlView.close();
            }
        });
    }

    /*************************事件处理****************************/
    /**
     * 监听点击事件
     *
     * @param view
     */
    @OnClick({R.id.register_tv_login, R.id.register_btn_login, R.id.register_tv_forget})
    protected void onClick(View view) {
        switch (view.getId()) {
            case R.id.register_btn_login:  //button
                register();
                break;
            case R.id.register_tv_login:  //sign
                finish();
                break;
            case R.id.register_tv_forget:  //忘记密码

                break;

            default:
                break;
        }
    }

    /**
     * 执行注册动作
     */
    public void register() {
        String emal=mEdtEmail.getText().toString();
        String username = mEdtUsername.getText().toString();
        String password = mEdtPassword.getText().toString();
        if (emal.length()==0||username.length() == 0 || password.length() == 0) {
            SnackbarUtils.show(mContext, "请填写必要信息");
            return;
        }

        ProgressUtils.show(mContext, "正在注册...");
        CocoUser user=new CocoUser();
        user.setEmail(emal);
        user.setUsername(username);
        user.setPassword(password);
        user.signUp(new SaveListener<CocoUser>() {
            @Override
            public void done(CocoUser cocoUser, BmobException e) {
                ProgressUtils.dismiss();
                if (e==null)
                    finish();
                else
                    SnackbarUtils.show(mContext, e.getMessage());
            }
        });
    }

}
