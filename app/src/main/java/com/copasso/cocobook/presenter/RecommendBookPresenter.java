package com.copasso.cocobook.presenter;

import com.copasso.cocobook.model.remote.RemoteRepository;
import com.copasso.cocobook.presenter.contract.BillBookContract;
import com.copasso.cocobook.presenter.contract.RecommendBookContract;
import com.copasso.cocobook.ui.base.RxPresenter;
import com.copasso.cocobook.utils.LogUtils;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by zhouas666 on 17-5-3.
 */

public class RecommendBookPresenter extends RxPresenter<RecommendBookContract.View>
        implements RecommendBookContract.Presenter {
    private static final String TAG = "RecommendBookPresenter";
    @Override
    public void refreshBookBrief(String bookId) {
        Disposable remoteDisp = RemoteRepository.getInstance()
                .getRecommendBooksByBookId(bookId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        (beans)-> {
                            mView.finishRefresh(beans);
                            mView.complete();
                        }
                        ,
                        (e) ->{
                            mView.showError();
                            LogUtils.e(e);
                        }
                );
        addDisposable(remoteDisp);
    }
}
