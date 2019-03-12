package com.thmub.cocobook.presenter;

import com.thmub.cocobook.model.type.BookSortListType;
import com.thmub.cocobook.model.server.RemoteRepository;
import com.thmub.cocobook.presenter.contract.BookSortDetailContract;
import com.thmub.cocobook.base.RxPresenter;
import com.thmub.cocobook.utils.LogUtils;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by zhouas666 on 18-1-23.
 * 分类详情presenter
 */

public class BookSortDetailPresenter extends RxPresenter<BookSortDetailContract.View>
        implements BookSortDetailContract.Presenter {
    private static final String TAG = "BookSortDetailPresenter";
    @Override
    public void refreshSortBook(String gender, BookSortListType type, String major, String minor, int start, int limit) {

        if (minor.equals("全部")) minor = "";

        addDisposable(RemoteRepository.getInstance()
                .getSortBooks(gender, type.getNetName(), major, minor, start, limit)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        (beans) -> {
                            mView.finishRefresh(beans);
                            mView.complete();
                        }
                        ,
                        (e) -> {
                            mView.complete();
                            mView.showError();
                            LogUtils.e(e);
                        }
                ));
    }

    @Override
    public void loadSortBook(String gender, BookSortListType type, String major, String minor, int start, int limit) {

        addDisposable(RemoteRepository.getInstance()
                .getSortBooks(gender, type.getNetName(), major, minor, start, limit)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        beans -> mView.finishLoad(beans)
                        ,
                        e -> mView.showLoadError()
                ));
    }
}
