package com.copasso.cocobook;

import android.app.Application;
import android.content.Context;
import android.content.Intent;

import com.copasso.cocobook.service.DownloadService;

/**
 * Created by zhouas666 on 18-1-23.
 */

public class App extends Application {
    private static Context sInstance;

    @Override
    public void onCreate() {
        super.onCreate();
        sInstance = this;

        startService(new Intent(getContext(), DownloadService.class));
    }

    public static Context getContext(){
        return sInstance;
    }
}