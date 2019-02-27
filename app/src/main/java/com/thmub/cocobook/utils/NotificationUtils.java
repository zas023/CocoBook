package com.thmub.cocobook.utils;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

import com.thmub.cocobook.App;
import com.thmub.cocobook.R;
import com.thmub.cocobook.ui.activity.MainActivity;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Zhouas666 on 2019-02-27
 * Github: https://github.com/zas023
 *
 * source: https://blog.csdn.net/true100/article/details/51302150
 */
public class NotificationUtils {
    private Context mContext;
    // NotificationManager ： 是状态栏通知的管理类，负责发通知、清楚通知等。
    private NotificationManager manager;
    // 定义Map来保存Notification对象
    private Map<Integer, Notification> map = null;

    public NotificationUtils(Context context) {
        this.mContext = context;
        // NotificationManager 是一个系统Service，必须通过 getSystemService()方法来获取。
        manager = (NotificationManager) App.getContext().getSystemService(Context.NOTIFICATION_SERVICE);
        map = new HashMap<>();
    }

    public void showNotification(int notificationId) {
        // 判断对应id的Notification是否已经显示， 以免同一个Notification出现多次
        if (!map.containsKey(notificationId)) {
            // 创建通知对象
            Notification notification = new Notification();
            // 设置通知栏滚动显示文字
            notification.tickerText = "开始下载xx文件";
            // 设置显示时间
            notification.when = System.currentTimeMillis();
            // 设置通知显示的图标
            notification.icon = R.mipmap.ic_launcher;
            // 设置通知的特性: 通知被点击后，自动消失
            notification.flags = Notification.FLAG_AUTO_CANCEL;
            // 设置点击通知栏操作
            Intent in = new Intent(mContext, MainActivity.class);// 点击跳转到指定页面
            PendingIntent pIntent = PendingIntent.getActivity(mContext, 0, in, 0);
            notification.contentIntent = pIntent;
            // 设置通知的显示视图
            RemoteViews remoteViews = new RemoteViews(
                    mContext.getPackageName(),
                    R.layout.app_notification_download);
            // 设置暂停按钮的点击事件
            Intent pause = new Intent(mContext, MainActivity.class);// 设置跳转到对应界面
            PendingIntent pauseIn = PendingIntent.getActivity(mContext, 0, in,
                    0);
            // 这里可以通过Bundle等传递参数
            remoteViews.setOnClickPendingIntent(R.id.pause, pauseIn);
            /********** 简单分隔 **************************/
            // 设置取消按钮的点击事件
            Intent stop = new Intent(mContext, MainActivity.class);// 设置跳转到对应界面
            PendingIntent stopIn = PendingIntent
                    .getActivity(mContext, 0, in, 0);
            // 这里可以通过Bundle等传递参数
            remoteViews.setOnClickPendingIntent(R.id.cancel, stopIn);
            // 设置通知的显示视图
            notification.contentView = remoteViews;
            // 发出通知
            manager.notify(notificationId, notification);
            map.put(notificationId, notification);// 存入Map中
        }
    }

    /**
     * 取消通知操作
     *
     * @description：
     * @author ldm
     * @date 2016-5-3 上午9:32:47
     */
    public void cancel(int notificationId) {
        manager.cancel(notificationId);
        map.remove(notificationId);
    }

    public void updateProgress(int notificationId, int progress,int max) {
        Notification notify = map.get(notificationId);
        if (null != notify) {
            // 修改进度条
            notify.contentView.setProgressBar(R.id.pBar, max, progress, false);
            manager.notify(notificationId, notify);
        }
    }
}