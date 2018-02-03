package com.copasso.cocobook.ui.activity;

import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import butterknife.BindView;
import butterknife.OnClick;
import com.copasso.cocobook.R;
import com.copasso.cocobook.ui.base.BaseBackActivity;
import com.copasso.cocobook.widget.CommonItemLayout;

/**
 * Created by zhouas666 on 2017/12/8.
 * 用户管理activity
 */
public class UserInfoActivity extends BaseBackActivity {


    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.info_iv_icon)
    ImageView infoIvIcon;
    @BindView(R.id.info_rlt_update_icon)
    RelativeLayout infoRltUpdateIcon;
    @BindView(R.id.info_cil_username)
    CommonItemLayout infoCilUsername;
    @BindView(R.id.info_cil_sex)
    CommonItemLayout infoCilSex;
    @BindView(R.id.info_cil_phone)
    CommonItemLayout infoCilPhone;
    @BindView(R.id.info_cil_email)
    CommonItemLayout infoCilEmail;
    /*****************************视图********************************/

    @Override
    protected int getLayoutId() {
        return R.layout.activity_user_info;
    }

    @Override
    protected void initWidget() {
        super.initWidget();
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
