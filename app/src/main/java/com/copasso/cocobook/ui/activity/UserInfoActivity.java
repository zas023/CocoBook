package com.copasso.cocobook.ui.activity;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;
import butterknife.BindView;
import butterknife.OnClick;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.UpdateListener;
import cn.bmob.v3.listener.UploadFileListener;
import com.bumptech.glide.Glide;
import com.copasso.cocobook.R;
import com.copasso.cocobook.model.bean.bmob.CocoUser;
import com.copasso.cocobook.ui.base.BaseBackActivity;
import com.copasso.cocobook.utils.*;
import com.copasso.cocobook.widget.CircleImageView;
import com.copasso.cocobook.widget.CommonItemLayout;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by zhouas666 on 2017/12/8.
 * 用户信息管理activity
 */
public class UserInfoActivity extends BaseBackActivity {
    /*****************************常量********************************/
    protected static final int CHOOSE_PICTURE = 0;
    private static final int CROP_SMALL_PICTURE = 2;
    /*****************************视图********************************/
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.info_iv_icon)
    CircleImageView infoIvIcon;
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
    @BindView((R.id.info_btn_logout))
    Button infoBtnLogout;

    /*****************************参数********************************/
    private CocoUser currentUser = BmobUser.getCurrentUser(CocoUser.class);
    //图片路径
    protected static Uri tempUri = null;

    /*****************************初始化********************************/
    @Override
    protected int getLayoutId() {
        return R.layout.activity_user_info;
    }

    @Override
    protected void initWidget() {
        super.initWidget();
        refreshData();
    }

    private void refreshData() {
        if (currentUser == null) return;
        if (currentUser.getPortrait() != null)
            Glide.with(mContext).load(currentUser.getPortrait()).into(infoIvIcon);

        infoCilUsername.setRightText(currentUser.getUsername());
        infoCilSex.setRightText(currentUser.getSex());
        infoCilPhone.setRightText(currentUser.getMobilePhoneNumber());
        infoCilEmail.setRightText(currentUser.getEmail());

    }

    @Override
    protected void setUpToolbar(Toolbar toolbar) {
        super.setUpToolbar(toolbar);
        getSupportActionBar().setTitle("用户信息");
    }

    /*****************************事件处理********************************/
    /**
     * 监听点击事件
     *
     * @param view
     */
    @OnClick({R.id.info_rlt_update_icon, R.id.info_cil_username, R.id.info_cil_sex
            , R.id.info_cil_phone, R.id.info_cil_email, R.id.info_btn_logout})
    protected void onClick(View view) {
        switch (view.getId()) {
            case R.id.info_rlt_update_icon:
                chooseIcon();
                break;
            case R.id.info_cil_username:
                ToastUtils.show("江湖人行不更名");
                break;
            case R.id.info_cil_sex:
                new AlertDialog.Builder(mContext)
                        .setTitle("性别")
                        .setMessage("请选择你的性别")
                        .setPositiveButton("男", (dialogInterface, i) ->
                                currentUser.setSex("男")
                        )
                        .setNegativeButton("女", (dialogInterface, i) ->
                                currentUser.setSex("女"))
                        .show();
                break;
            case R.id.info_cil_phone:
                final EditText editText=new EditText(mContext);
                new AlertDialog.Builder(this)
                        .setTitle("电话")
                        .setView(editText)
                        .setPositiveButton("确定", (dialogInterface, i) -> {
                            String input = editText.getText().toString();
                            if (input.equals("")) {
                                Toast.makeText(getApplicationContext(), "内容不能为空！" + input,
                                        Toast.LENGTH_SHORT).show();
                            } else {
                                if (StringUtils.checkPhoneNumber(input)) {
                                    currentUser.setMobilePhoneNumber(input);
                                    infoCilPhone.setRightText(input);
                                    updateUser();
                                } else {
                                    Toast.makeText(UserInfoActivity.this,
                                            "请输入正确的电话号码", Toast.LENGTH_LONG).show();
                                }
                            }
                        })
                        .setNegativeButton("取消", null)
                        .create();
                break;
            case R.id.info_cil_email:
                final EditText editText2=new EditText(mContext);
                new AlertDialog.Builder(this)
                        .setTitle("邮箱")
                        .setView(editText2)
                        .setPositiveButton("确定", (dialogInterface, i) -> {
                            String input = editText2.getText().toString();
                            if (input.equals("")) {
                                Toast.makeText(getApplicationContext(), "内容不能为空！" + input,
                                        Toast.LENGTH_SHORT).show();
                            } else {
                                if (StringUtils.checkEmail(input)) {
                                    currentUser.setEmail(input);
                                    infoCilEmail.setRightText(input);
                                    updateUser();
                                } else {
                                    Toast.makeText(UserInfoActivity.this,
                                            "请输入正确的邮箱地址", Toast.LENGTH_LONG).show();
                                }
                            }
                        })
                        .setNegativeButton("取消", null)
                        .create();
                break;
            case R.id.info_btn_logout:  //退出
                new AlertDialog.Builder(mContext)
                        .setTitle("提示")
                        .setMessage("是否退出当前账户？")
                        .setPositiveButton("确定", (dialogInterface, i) -> {
                            BmobUser.logOut();
                            finish();
                        })
                        .setNegativeButton("取消", null)
                        .show();
                break;
            default:
                break;
        }
    }

    /**
     * 显示选择头像来源对话框
     */
    public void chooseIcon() {

        Intent openAlbumIntent = new Intent(
                Intent.ACTION_GET_CONTENT);
        openAlbumIntent.setType("image/*");
        startActivityForResult(openAlbumIntent, CHOOSE_PICTURE);
    }

    /**
     * 更新用户信息
     */
    private void updateUser() {
        ProgressUtils.show(mContext,"正在保存");
        currentUser.update(new UpdateListener() {
            @Override
            public void done(BmobException e) {
                ProgressUtils.dismiss();
                if (e!=null)
                    SnackbarUtils.show(mContext,e.getMessage());
            }
        });
    }

    /**
     * 监听Activity返回结果
     *
     * @param requestCode
     * @param resultCode
     * @param intent
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case CHOOSE_PICTURE:
                    startPhotoZoom(intent.getData()); // 开始对图片进行裁剪处理
                    break;
                case CROP_SMALL_PICTURE:
                    if (intent != null) {
                        setIcon(intent); // 让刚才选择裁剪得到的图片显示在界面上
                    }
                    break;
            }
        }
    }

    /**
     * 裁剪图片方法实现
     *
     * @param uri
     */
    private void startPhotoZoom(Uri uri) {
        if (uri == null) {
            Log.i("tag", "The uri is not exist.");
        }
        tempUri = uri;
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        // 设置裁剪
        intent.putExtra("crop", "true");
        // aspectX aspectY 是宽高的比例
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        // outputX outputY 是裁剪图片宽高
        intent.putExtra("outputX", 150);
        intent.putExtra("outputY", 150);
        intent.putExtra("return-data", true);
        startActivityForResult(intent, CROP_SMALL_PICTURE);
    }

    /**
     * 保存裁剪之后的图片数据
     *
     * @param data
     */
    private void setIcon(Intent data) {
        Bundle extras = data.getExtras();
        if (extras != null) {
            Bitmap photo = extras.getParcelable("data");
            infoIvIcon.setImageBitmap(photo);
            saveIcon(photo);
        }

    }

    /**
     * 保存图片
     *
     * @param bitmap
     */
    private void saveIcon(Bitmap bitmap){
        String filePath=Environment.getExternalStorageDirectory()+"/CocoBook/file/"+currentUser.getObjectId()+".jpg";
        File file=new File(filePath);//将要保存图片的路径
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(file));
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bos);
            bos.flush();
            bos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        upload(filePath);
    }
    /**
     * 将图片上传
     * @param imgPath
     */
    private void upload(String imgPath){
        BmobFile icon = new BmobFile(new File(imgPath));
        icon.uploadblock( new UploadFileListener() {
            @Override
            public void done(BmobException e) {
                if (e==null){
                    currentUser.setPortrait(icon.getFileUrl());
                    currentUser.update();
                }else {
                    System.out.println("++++++++++++++++++++++++++"+e.getMessage());
                }
            }
        });
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        setResult(MainActivity.REQUEST_USER_INFO);
    }


}
