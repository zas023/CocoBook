package com.copasso.cocobook.model.service;

import cn.bmob.v3.BmobBatch;
import cn.bmob.v3.BmobObject;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BatchResult;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.QueryListListener;
import com.copasso.cocobook.model.bean.bmob.BmobBook;
import com.copasso.cocobook.model.bean.CollBookBean;
import com.copasso.cocobook.model.local.BookRepository;
import retrofit2.Retrofit;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class BmobRepository {

    private static final String TAG = "BmobRepository";

    private static BmobRepository sInstance;
    private Retrofit mRetrofit;
    private BookApi mBookApi;

    private BmobRepository() {
        mRetrofit = RemoteHelper.getInstance()
                .getRetrofit();

        mBookApi = mRetrofit.create(BookApi.class);
    }

    public static BmobRepository getInstance() {
        if (sInstance == null) {
            synchronized (RemoteHelper.class) {
                if (sInstance == null) {
                    sInstance = new BmobRepository();
                }
            }
        }
        return sInstance;
    }


    /****************************同步书架*************************************/
    public void syncBooks(BmobUser user, SyncBookListener listener) {
        List<BmobObject> remoteUpdate = new ArrayList<>();
        List<BmobObject> remoteAdd = new ArrayList<>();
        HashMap<String, BmobBook> remoteShelf = new HashMap<>();
        HashMap<String, CollBookBean> localShelf = new HashMap<>();
        List<BmobBook> remoteBooks = new ArrayList<>();
        List<CollBookBean> pullBooks = new ArrayList<>();
        List<CollBookBean> localBooks = BookRepository.getInstance().getCollBooks();

        //查询服务器数据
        BmobQuery<BmobBook> query = new BmobQuery<>();
        query.addWhereEqualTo("user", user);
        query.findObjects(new FindListener<BmobBook>() {
            @Override
            public void done(List<BmobBook> list, BmobException e) {
                if (e == null) {
                    remoteBooks.addAll(list);
                    //初始化HashMap
                    if (localBooks != null) {
                        for (CollBookBean bean : localBooks) {
                            localShelf.put(bean.get_id(), bean);
                        }
                    }
                    if (remoteBooks != null) {
                        for (BmobBook bean : remoteBooks)
                            remoteShelf.put(bean.getBookId(), bean);
                    }
                    //筛选需要push的数据
                    for (CollBookBean collBookBean : localBooks) {

                        if (remoteShelf.containsKey(collBookBean.get_id())) {
                            BmobBook bean = remoteShelf.get(collBookBean.get_id());
                            bean.update(collBookBean);
                            remoteUpdate.add(bean);
                        } else {
                            BmobBook bean = new BmobBook(collBookBean);
                            bean.setUser(user);
                            remoteAdd.add(bean);
                        }
                    }
                    //筛选需要pull的数据集
                    for (BmobBook bmobBook : remoteBooks) {
                        if (!localShelf.containsKey(bmobBook.getBookId())) {
                            pullBooks.add(new CollBookBean(bmobBook));
                        }
                    }

                    //更新服务器信息
                    new BmobBatch().updateBatch(remoteUpdate).insertBatch(remoteAdd)
                            .doBatch(new QueryListListener<BatchResult>() {
                                @Override
                                public void done(List<BatchResult> list, BmobException e) {
                                    if (e == null)
                                        listener.onSuccess(pullBooks);
                                    else
                                        listener.onError(e);
                                }
                            });
                }
            }
        });

    }

    /**
     * 同步回调消息
     */
    public interface SyncBookListener {
        void onSuccess(List<CollBookBean> list);

        void onError(Throwable e);
    }

}
