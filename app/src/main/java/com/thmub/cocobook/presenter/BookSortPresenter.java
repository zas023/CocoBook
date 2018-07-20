package com.thmub.cocobook.presenter;

import com.thmub.cocobook.model.bean.packages.BookSortPackage;
import com.thmub.cocobook.model.bean.packages.BookSubSortPackage;
import com.thmub.cocobook.model.server.RemoteRepository;
import com.thmub.cocobook.presenter.contract.BookSortContract;
import com.thmub.cocobook.base.RxPresenter;
import com.thmub.cocobook.utils.LogUtils;

import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.BiFunction;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by zhouas666 on 18-1-23.
 */

public class BookSortPresenter extends RxPresenter<BookSortContract.View>
        implements BookSortContract.Presenter {
    private static final String TAG = "BookSortPresenter";
    @Override
    public void refreshSortBean() {
        //这个最好是设定一个默认时间采用Remote加载，如果Remote加载失败则采用数据中的数据。我这里先写死吧
        Single<BookSortPackage> sortSingle = RemoteRepository.getInstance()
                .getBookSortPackage();
        Single<BookSubSortPackage> subSortSingle = RemoteRepository.getInstance()
                .getBookSubSortPackage();

        Single<SortPackage> zipSingle =  Single.zip(sortSingle, subSortSingle,
                (bookSortPackage, subSortPackage) -> new SortPackage(bookSortPackage,subSortPackage));

        addDisposable(zipSingle.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        (bean) ->{
                            mView.finishRefresh(bean.sortPackage,bean.subSortPackage);
                            mView.complete();
                        }
                        ,
                        (e) -> {
                            mView.showError();
                            LogUtils.e(e);
                        }
                ));
    }

    class SortPackage{
        BookSortPackage sortPackage;
        BookSubSortPackage subSortPackage;

        public SortPackage(BookSortPackage sortPackage, BookSubSortPackage subSortPackage){
            this.sortPackage = sortPackage;
            this.subSortPackage = subSortPackage;
        }
    }
}
