package com.copasso.cocobook;

import android.content.Context;
import android.content.Intent;

import android.support.multidex.MultiDex;
import android.support.multidex.MultiDexApplication;
import com.copasso.cocobook.service.DownloadService;

/**
 * Created by zhouas666 on 18-1-23.
 */

public class App extends MultiDexApplication {
    private static Context sInstance;

    @Override
    public void onCreate() {
        super.onCreate();
        sInstance = this;
        //初始化AppManager
        AppManager.init(this);

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