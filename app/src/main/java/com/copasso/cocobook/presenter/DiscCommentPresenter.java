package com.copasso.cocobook.presenter;

import static com.copasso.cocobook.utils.LogUtils.*;

import com.copasso.cocobook.model.bean.BookCommentBean;
import com.copasso.cocobook.model.type.BookDistillate;
import com.copasso.cocobook.model.type.BookSort;
import com.copasso.cocobook.model.type.CommunityType;
import com.copasso.cocobook.model.local.LocalRepository;
import com.copasso.cocobook.model.server.RemoteRepository;
import com.copasso.cocobook.presenter.contract.DiscCommentContact;
import com.copasso.cocobook.ui.base.RxPresenter;

import java.util.List;

import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by zhouas666 on 17-4-20.
 */

public class DiscCommentPresenter extends RxPresenter<DiscCommentContact.View> implements DiscCommentContact.Presenter{
    private static final String TAG = "DiscCommentPresenter";
    //是否采取直接从数据库加载
    private boolean isLocalLoad = true;

    @Override
    public void firstLoading(CommunityType block, BookSort sort, int start, int limited, BookDistillate distillate) {
        //获取数据库中的数据
        Single<List<BookCommentBean>> localObserver = LocalRepository.getInstance()
                .getBookComments(block.getNetName(), sort.getDbName(),
                        start, limited, distillate.getDbName());
        Single<List<BookCommentBean>> remoteObserver = RemoteRepository.getInstance()
                .getBookComment(block.getNetName(), sort.getNetName(),
                        start, limited, distillate.getNetName());

        //这里有问题，但是作者却用的好好的，可能是2.0之后的问题
        Disposable disposable =  localObserver
                .concatWith(remoteObserver)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        (beans) -> {
                            mView.finishRefresh(beans);
                        }
                        ,
                        (e) ->{
                            isLocalLoad = true;
                            mView.complete();
                            mView.showErrorTip();
                            e(e);
                        }
                        ,
                        ()-> {
                            isLocalLoad = false;
                            mView.complete();
                        }
                );
        addDisposable(disposable);
    }

    @Override
    public void refreshComment(CommunityType block, BookSort sort,
                               int start, int limited, BookDistillate distillate) {
        Disposable refreshDispo = RemoteRepository.getInstance()
                .getBookComment(block.getNetName(),sort.getNetName(),
                        start,limited,distillate.getNetName())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        (beans)-> {
                            isLocalLoad = false;
                            mView.finishRefresh(beans);
                            mView.complete();
                        }
                        ,
                        (e) ->{
                            mView.complete();
                            mView.showErrorTip();
                            e(e);
                        }
                );
        addDisposable(refreshDispo);
    }

    @Override
    public void loadingComment(CommunityType block, BookSort sort, int start, int limited, BookDistillate distillate) {
        if (isLocalLoad){
            Single<List<BookCommentBean>> single = LocalRepository.getInstance()
                    .getBookComments(block.getNetName(), sort.getDbName(),
                            start, limited, distillate.getDbName());
            loadComment(single);
        }

        else{
            //单纯的加载数据
            Single<List<BookCommentBean>> single = RemoteRepository.getInstance()
                    .getBookComment(block.getNetName(),sort.getNetName(),
                            start,limited,distillate.getNetName());
            loadComment(single);

        }
    }

    private void loadComment(Single<List<BookCommentBean>> observable){
        Disposable loadDispo =observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        (beans) -> {
                            mView.finishLoading(beans);
                        }
                        ,
                        (e) -> {
                            mView.showError();
                            e(e);
                        }
                );
        addDisposable(loadDispo);
    }

    @Override
    public void saveComment(List<BookCommentBean> beans) {
        LocalRepository.getInstance()
                .saveBookComments(beans);
    }
}
