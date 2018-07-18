package com.thmub.cocobook.service;

import android.app.PendingIntent;
import android.content.Intent;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.text.TextUtils;
import android.util.Log;
import com.thmub.cocobook.R;
import com.thmub.cocobook.base.BaseService;
import com.thmub.cocobook.manager.BookManager;
import com.thmub.cocobook.manager.RxBusManager;
import com.thmub.cocobook.model.bean.BookChapterBean;
import com.thmub.cocobook.model.bean.DownloadTaskBean;
import com.thmub.cocobook.model.event.DeleteResponseEvent;
import com.thmub.cocobook.model.event.DeleteTaskEvent;
import com.thmub.cocobook.model.event.DownloadMessage;
import com.thmub.cocobook.model.local.BookRepository;
import com.thmub.cocobook.model.local.LocalRepository;
import com.thmub.cocobook.model.server.RemoteRepository;
import com.thmub.cocobook.model.type.RxBusTag;
import com.thmub.cocobook.ui.activity.DownloadActivity;
import com.thmub.cocobook.utils.NetworkUtils;
import io.reactivex.disposables.Disposable;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by zhouas666 on 18-2-10.
 * 书籍下载Service
 */

public class BookDownloadService extends BaseService {
    private static final String TAG = "BookDownloadService";
    //加载状态
    private static final int LOAD_ERROR = -1;
    private static final int LOAD_NORMAL = 0;
    private static final int LOAD_PAUSE = 1;
    private static final int LOAD_DELETE = 2; //正在加载时候，用户删除收藏书籍的情况。

    //线程池
    private final ExecutorService mSingleExecutor = Executors.newSingleThreadExecutor();
    //下载队列
    private final List<DownloadTaskBean> mDownloadTaskQueue = Collections.synchronizedList(new ArrayList<>());
    //Handler
    private Handler mHandler;

    //所有DownloadTask的列表
    private List<DownloadTaskBean> mDownloadTaskList;

    private OnDownloadListener mDownloadListener;
    private boolean isBusy = false;
    private boolean isCancel = false;

    private final int notificationId = 19931118;

    @Override
    public void onCreate() {
        super.onCreate();
        Log.e(TAG, "-------------onCreate");
        //从数据库中获取所有的任务
        mDownloadTaskList = LocalRepository.getInstance().getDownloadTaskList();
        EventBus.getDefault().register(this);
    }

    @Override
    public IBinder onBind(Intent intent) {
        Log.e(TAG, "-------------onBind");
        return new TaskBuilder();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.e(TAG, "-------------onStartCommand");
        return super.onStartCommand(intent, flags, startId);
    }

    public static void post(DownloadTaskBean downloadTaskBean) {
        EventBus.getDefault().post(downloadTaskBean);
    }

    /**
     * 添加任务到线程池
     *
     * @param taskEvent
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void addToExecutor(DownloadTaskBean taskEvent) {
        Log.e(TAG, "-------------addToExecutor");

        if (TextUtils.isEmpty(taskEvent.getBookId())) return;

        //判断是否为轮询请求
        if (!TextUtils.isEmpty(taskEvent.getBookId())) {

            if (!mDownloadTaskList.contains(taskEvent)) {
                //加入总列表中，表示创建，修改CollBean的状态。
                mDownloadTaskList.add(taskEvent);
            }
            // 添加到下载队列
            mDownloadTaskQueue.add(taskEvent);
        }

        // 从队列顺序取出第一条下载
        if (mDownloadTaskQueue.size() > 0 && !isBusy) {
            isBusy = true;
            executeTask(mDownloadTaskQueue.get(0));
        }
    }

    /**
     * 删除任务
     * @param deleteTaskEvent
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void deleteTask(DeleteTaskEvent deleteTaskEvent){
        //判断是否该数据存在加载列表中
        boolean isDelete = true;
        for (DownloadTaskBean bean : mDownloadTaskQueue) {
            if (bean.getBookId().equals(deleteTaskEvent.collBook.get_id())) {
                isDelete = false;
                break;
            }
        }
        //如果不存在则删除List中的task
        if (isDelete) {
            Iterator<DownloadTaskBean> taskIt = mDownloadTaskList.iterator();
            while (taskIt.hasNext()) {
                DownloadTaskBean task = taskIt.next();
                if (task.getBookId().equals(deleteTaskEvent.collBook.get_id())) {
                    taskIt.remove();
                }
            }
        }
        //返回状态
        EventBus.getDefault().post(new DeleteResponseEvent(isDelete, deleteTaskEvent.collBook));
    }

    /**
     * 执行下载任务
     *
     * @param taskEvent
     */
    private void executeTask(DownloadTaskBean taskEvent) {
        Log.e(TAG, "-------------executeTask");
        Runnable runnable = () -> {

            taskEvent.setStatus(DownloadTaskBean.STATUS_LOADING);

            int result = LOAD_NORMAL;
            List<BookChapterBean> bookChapterBeans = taskEvent.getBookChapters();

            //下载数据，遍历下载所有章节
            for (int i = taskEvent.getCurrentChapter(); i < bookChapterBeans.size(); ++i) {

                BookChapterBean bookChapterBean = bookChapterBeans.get(i);
                //首先判断该章节是否曾经被加载过 (从文件中判断)
                if (BookManager.isChapterCached(taskEvent.getBookId(), bookChapterBean.getTitle())) {
                    //设置任务进度
                    taskEvent.setCurrentChapter(i);
                    //章节加载完成
                    postDownloadChange(taskEvent, DownloadTaskBean.STATUS_LOADING, i + "");
                    //无需进行下一步
                    continue;
                }

                //判断网络是否出问题
                if (!NetworkUtils.isAvailable()) {
                    //章节加载失败
                    result = LOAD_ERROR;
                    break;
                }

                if (isCancel) {
                    Log.e(TAG, "-------------executeTask:LOAD_PAUSE");
                    result = LOAD_PAUSE;
                    isCancel = false;
                    break;
                }

                //加载章节数据
                result = loadChapter(taskEvent.getBookId(), bookChapterBean);
                //章节加载完成
                if (result == LOAD_NORMAL) {
                    taskEvent.setCurrentChapter(i);
                    postDownloadChange(taskEvent, DownloadTaskBean.STATUS_LOADING, i + "");

                    Intent mainIntent = new Intent(this, DownloadActivity.class);
                    PendingIntent mainPendingIntent = PendingIntent.getActivity(this, 0,
                            mainIntent, PendingIntent.FLAG_UPDATE_CURRENT);
                    //创建 Notification.Builder 对象
                    NotificationCompat.Builder builder = new NotificationCompat.Builder(this)
                            .setSmallIcon(R.mipmap.ic_launcher)
                            //点击通知后自动清除
                            .setAutoCancel(true)
                            .setContentTitle("下载：" + taskEvent.getTaskName())
                            .setContentText("正在下载第 " + i + " / " + taskEvent.getLastChapter() + "章")
                            .setContentIntent(mainPendingIntent);
                    //发送通知
                    startForeground(notificationId, builder.build());

                } else {
                    //遇到错误退出
                    Log.e(TAG, "-------------executeTask:LOAD_ERROR");
                    break;
                }
                Log.e(TAG, "-------------executeTask:" + i);
            }

            Log.e(TAG, "-------------executeTask:" + Thread.currentThread());
            Log.e(TAG, "-------------executeTask:" + taskEvent.getTaskName());
            Log.e(TAG, "-------------executeTask:" + taskEvent.getLastChapter());
            Log.e(TAG, "-------------executeTask:" + taskEvent.getSize());
            Log.e(TAG, "-------------executeTask:" + taskEvent.getCurrentChapter());

            //完成循环遍历，判断状态
            if (result == LOAD_NORMAL) {
                //存储DownloadTask的状态
                taskEvent.setCurrentChapter(taskEvent.getBookChapters().size());//当前下载的章节数量
                taskEvent.setSize(BookManager.getBookSize(taskEvent.getBookId()));//Task的大小
                //发送完成状态
                if (taskEvent.getSize() < taskEvent.getLastChapter()) {
                    taskEvent.setStatus(DownloadTaskBean.STATUS_PAUSE);//Task的状态
                    postDownloadChange(taskEvent, DownloadTaskBean.STATUS_PAUSE, "暂停加载");
                } else {
                    taskEvent.setStatus(DownloadTaskBean.STATUS_FINISH);//Task的状态
                    postDownloadChange(taskEvent, DownloadTaskBean.STATUS_FINISH, "下载完成");
                    mDownloadTaskQueue.remove(taskEvent);
                }
            } else if (result == LOAD_ERROR) {
                taskEvent.setStatus(DownloadTaskBean.STATUS_ERROR);//Task的状态
                //任务加载失败
                postDownloadChange(taskEvent, DownloadTaskBean.STATUS_ERROR, "资源或网络错误");
            } else if (result == LOAD_PAUSE) {
                taskEvent.setStatus(DownloadTaskBean.STATUS_PAUSE);//Task的状态
                postDownloadChange(taskEvent, DownloadTaskBean.STATUS_PAUSE, "暂停加载");
            } else if (result == LOAD_DELETE) {
                //没想好怎么做
            }
            Log.e(TAG, "-------------executeTask:" + result);
            //存储状态
            LocalRepository.getInstance().saveDownloadTask(taskEvent);
            //移除完成的任务
//            mDownloadTaskQueue.remove(taskEvent);
            //设置为空闲
            isBusy = false;
            //轮询
            postTaskBean(new DownloadTaskBean());
        };
        mSingleExecutor.execute(runnable);
    }

    /**
     * 判断结果
     *
     * @param folderName
     * @param bean
     * @return
     */
    private int loadChapter(String folderName, BookChapterBean bean) {
        //加载的结果参数
        final int[] result = {LOAD_NORMAL};

        //问题:(这里有个问题，就是body其实比较大，如何获取数据流而不是对象，)是不是直接使用OkHttpClient交互会更好一点
        Disposable disposable = RemoteRepository.getInstance()
                .getChapterInfo(bean.getLink())
                //表示在当前环境下执行
                .subscribe(
                        chapterInfo -> {
                            //TODO:这里文件的名字用的是BookChapter的title,而不是chapter的title。
                            //原因是Chapter的title可能重复，但是BookChapter的title不会重复
                            //BookChapter的title = 卷名 + 章节名 chapter 的 title 就是章节名。。
                            BookRepository.getInstance()
                                    .saveChapterInfo(folderName, bean.getTitle(), chapterInfo.getBody());
                        },
                        e -> {
                            //当前进度加载错误（这里需要判断是什么问题，根据相应的问题做出相应的回答）
                            //设置加载结果
                            result[0] = LOAD_ERROR;
                        }
                );
        addDisposable(disposable);
        return result[0];
    }

    /**
     * 提交下载状态变化
     *
     * @param task
     * @param status
     * @param msg
     */
    private void postDownloadChange(DownloadTaskBean task, int status, String msg) {
        if (mDownloadListener != null) {
            int position = mDownloadTaskList.indexOf(task);
            //通过handler,切换回主线程
            mHandler.post(
                    () -> mDownloadListener.onDownloadChange(position, status, msg)
            );
        }
    }

    private void postMessage(String msg) {
        RxBusManager.getInstance().post(new DownloadMessage(msg));
    }

    private void postTaskBean(DownloadTaskBean task) {
        RxBusManager.getInstance().post(task);
    }


    @Override
    public boolean onUnbind(Intent intent) {
        Log.e(TAG, "-------------onUnbind");
        mDownloadListener = null;
        return super.onUnbind(intent);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.e(TAG, "-------------onDestroy");
        EventBus.getDefault().unregister(this);
    }


    /******************************************************************/
    class TaskBuilder extends Binder implements IDownloadManager {
        @Override
        public List<DownloadTaskBean> getDownloadTaskList() {
            return Collections.unmodifiableList(mDownloadTaskList);
        }

        @Override
        public void setOnDownloadListener(OnDownloadListener listener) {
            mDownloadListener = listener;
        }

        @Override
        public void setDownloadStatus(String taskName, int status) {
            //修改某个Task的状态
            switch (status) {
                //加入缓存队列
                case DownloadTaskBean.STATUS_WAIT:
                    for (int i = 0; i < mDownloadTaskList.size(); ++i) {
                        DownloadTaskBean bean = mDownloadTaskList.get(i);
                        if (taskName.equals(bean.getTaskName())) {
                            bean.setStatus(DownloadTaskBean.STATUS_WAIT);
                            mDownloadListener.onDownloadResponse(i, DownloadTaskBean.STATUS_WAIT);
                            addToExecutor(bean);
                            break;
                        }
                    }
                    break;
                //从缓存队列中删除
                case DownloadTaskBean.STATUS_PAUSE:
                    Iterator<DownloadTaskBean> it = mDownloadTaskQueue.iterator();
                    while (it.hasNext()) {
                        DownloadTaskBean bean = it.next();
                        if (bean.getTaskName().equals(taskName)) {
                            if (bean.getStatus() == DownloadTaskBean.STATUS_LOADING
                                    && bean.getTaskName().equals(taskName)) {
                                isCancel = true;
                                break;
                            } else {
                                bean.setStatus(DownloadTaskBean.STATUS_PAUSE);
                                mDownloadTaskQueue.remove(bean);
                                int position = mDownloadTaskList.indexOf(bean);
                                mDownloadListener.onDownloadResponse(position, DownloadTaskBean.STATUS_PAUSE);
                                break;
                            }
                        }
                    }
                    break;
            }
        }

        @Override
        public void setAllDownloadStatus(int status) {
            //修改所有Task的状态
        }

        //首先判断是否在加载队列中。
        //如果在加载队列中首先判断是否正在下载，
        //然后判断是否在完成队列中。
    }


    /******************************************************************/
    public interface IDownloadManager {
        List<DownloadTaskBean> getDownloadTaskList();

        void setOnDownloadListener(OnDownloadListener listener);

        void setDownloadStatus(String taskName, int status);

        void setAllDownloadStatus(int status);
    }

    public interface OnDownloadListener {
        /**
         * @param pos    : Task在item中的位置
         * @param status : Task的状态
         * @param msg:   传送的Msg
         */
        void onDownloadChange(int pos, int status, String msg);

        /**
         * 回复
         */
        void onDownloadResponse(int pos, int status);
    }
}
