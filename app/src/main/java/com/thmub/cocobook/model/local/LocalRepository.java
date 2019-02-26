package com.thmub.cocobook.model.local;

import com.thmub.cocobook.model.bean.AuthorBean;
import com.thmub.cocobook.model.bean.DownloadTaskBean;
import com.thmub.cocobook.model.bean.packages.BookRankPackage;
import com.thmub.cocobook.model.bean.packages.BookSortPackage;
import com.thmub.cocobook.model.dao.AuthorBeanDao;

import com.thmub.cocobook.model.dao.DaoSession;
import com.thmub.cocobook.utils.Constant;
import com.thmub.cocobook.utils.LogUtils;
import com.thmub.cocobook.utils.SharedPreUtils;
import com.google.gson.Gson;

import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.query.QueryBuilder;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.Single;
import io.reactivex.SingleEmitter;
import io.reactivex.SingleOnSubscribe;

/**
 * Created by zhouas666 on 17-4-26.
 */

public class LocalRepository implements SaveDbHelper,GetDbHelper,DeleteDbHelper{
    private static final String TAG = "LocalRepository";
    private static final String DISTILLATE_ALL = "normal";
    private static final String DISTILLATE_BOUTIQUES = "distillate";

    private static volatile LocalRepository sInstance;
    private DaoSession mSession;
    private LocalRepository(){
        mSession = DaoDbHelper.getInstance().getSession();
    }

    public static LocalRepository getInstance(){
        if (sInstance == null){
            synchronized (LocalRepository.class){
                if (sInstance == null){
                    sInstance = new LocalRepository();
                }
            }
        }
        return sInstance;
    }

    /*************************************save*******************************************/

    public void saveAuthors(List<AuthorBean> beans){
        mSession.getAuthorBeanDao()
                .insertOrReplaceInTx(beans);
    }

    @Override
    public void saveBookSortPackage(BookSortPackage bean) {
        String json = new Gson().toJson(bean);
        SharedPreUtils.getInstance()
                .putString(Constant.SHARED_SAVE_BOOK_SORT,json);
    }

    @Override
    public void saveBillboardPackage(BookRankPackage bean) {
        String json = new Gson().toJson(bean);
        SharedPreUtils.getInstance()
                .putString(Constant.SHARED_SAVE_BILLBOARD,json);
    }

    @Override
    public void saveDownloadTask(DownloadTaskBean bean) {
        BookRepository.getInstance()
                .saveBookChaptersWithAsync(bean.getBookChapters());
        mSession.getDownloadTaskBeanDao()
                .insertOrReplace(bean);
    }

    /***************************************get****************************************************/

    @Override
    public BookSortPackage getBookSortPackage() {
        String json = SharedPreUtils.getInstance()
                .getString(Constant.SHARED_SAVE_BOOK_SORT);
        if (json == null){
            return null;
        }
        else {
            return new Gson().fromJson(json,BookSortPackage.class);
        }
    }

    @Override
    public BookRankPackage getBillboardPackage() {
        String json = SharedPreUtils.getInstance()
                .getString(Constant.SHARED_SAVE_BILLBOARD);
        if (json == null){
            return null;
        }
        else {
            return new Gson().fromJson(json,BookRankPackage.class);
        }
    }

    public AuthorBean getAuthor(String id){
        return mSession.getAuthorBeanDao()
                .queryBuilder()
                .where(AuthorBeanDao.Properties._id.eq(id))
                .unique();
    }

    @Override
    public List<DownloadTaskBean> getDownloadTaskList() {
        return mSession.getDownloadTaskBeanDao()
                .loadAll();
    }

    private <T> void queryOrderBy(QueryBuilder queryBuilder, Class<T> daoCls,String orderBy){
        //获取Dao中的Properties
        Class<?>[] innerCls = daoCls.getClasses();
        Class<?> propertiesCls = null;
        for (Class<?> cls : innerCls){
            if (cls.getSimpleName().equals("Properties")){
                propertiesCls = cls;
                break;
            }
        }
        //如果不存在则返回
        if (propertiesCls == null) return;

        //这里没有进行异常处理有点小问题
        try {
            Field field = propertiesCls.getField(orderBy);
            Property property = (Property) field.get(propertiesCls);
            queryBuilder.orderDesc(property);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
            LogUtils.e(e);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            LogUtils.e(e);
        }
    }

    private <T> Single<List<T>> queryToRx(QueryBuilder<T> builder){
        return Single.create(new SingleOnSubscribe<List<T>>() {
            @Override
            public void subscribe(SingleEmitter<List<T>> e) throws Exception {
                List<T> data = builder.list();
                if (data == null){
                    data = new ArrayList<T>(1);
                }
                e.onSuccess(data);
            }
        });
    }

    /************************************delete********************************************/

    @Override
    public void deleteAuthors(List<AuthorBean> beans) {
        mSession.getAuthorBeanDao()
                .deleteInTx(beans);
    }

    @Override
    public void deleteAll() {
        //清空全部数据。
    }
}
