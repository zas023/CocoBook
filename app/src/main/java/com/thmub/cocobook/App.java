package com.thmub.cocobook;

import android.content.Context;
import android.content.Intent;

import androidx.multidex.MultiDex;
import androidx.multidex.MultiDexApplication;
import cn.bmob.v3.Bmob;
import com.thmub.cocobook.service.DownloadService;

/**
 * Created by zhouas666 on 18-1-23.
 * 自定义application
 */

public class App extends MultiDexApplication {

    private static Context sInstance;

    @Override
    public void onCreate() {
        super.onCreate();
        sInstance = this;
        //默认初始化Bmob
        Bmob.initialize(this, "3f3b7628bf00914940a6919da16b33bf");
        //初始化下载服务
        startService(new Intent(getContext(), DownloadService.class));
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    public static Context getContext(){
        return sInstance;
    }
}