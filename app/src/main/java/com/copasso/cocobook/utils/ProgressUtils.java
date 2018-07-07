package com.copasso.cocobook.utils;

import android.app.ProgressDialog;
import android.content.Context;
import com.copasso.cocobook.App;
import com.copasso.cocobook.R;

/**
 * Created by zhouas666 on 2017/12/28.
 * ProgressDialog封装工具类
 */
public class ProgressUtils {

    private static ProgressDialog dialog = null;

    public static void show(Context context){
        show(context, null);
    }

    public static void show(String msg){
        show(App.getContext(), msg);
    }

    public static void show(Context context, String msg){
        dialog = new ProgressDialog(context);
        dialog.setMessage(msg == null ? App.getContext().getString(R.string.load_msg) : msg);
        dialog.setCancelable(false);
        dialog.show();
    }

    public static void dismiss(){
        if(dialog != null && dialog.isShowing()){
            dialog.dismiss();
        }
    }
}
