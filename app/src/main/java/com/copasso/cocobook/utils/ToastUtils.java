package com.copasso.cocobook.utils;

import android.widget.Toast;

import com.copasso.cocobook.App;

/**
 * Created by zhouas666 on 18-2-11.
 */

public class ToastUtils {

    public static void show(String msg){
        Toast.makeText(App.getContext(), msg, Toast.LENGTH_SHORT).show();
    }
}
