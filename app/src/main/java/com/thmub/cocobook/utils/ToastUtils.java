package com.thmub.cocobook.utils;

import android.widget.Toast;

import com.thmub.cocobook.App;

/**
 * Created by zhouas666 on 18-2-11.
 * Toast工具类
 */

public class ToastUtils {

    public static void show(String msg){
        Toast.makeText(App.getContext(), msg, Toast.LENGTH_SHORT).show();
    }
}
