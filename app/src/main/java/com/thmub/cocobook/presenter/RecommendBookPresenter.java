package com.thmub.cocobook.presenter;

import com.thmub.cocobook.model.server.RemoteRepository;
import com.thmub.cocobook.presenter.contract.RecommendBookContract;
import com.thmub.cocobook.base.RxPresenter;
import com.thmub.cocobook.utils.LogUtils;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by zhouas666 on 18-1-23.
 * 推荐书籍详情列表页面presenter
 */

public class RecommendBookPresenter extends RxPresenter<RecommendBookContract.View>
        implements RecommendBookContract.Presenter {

    private static final String TAG = "RecommendBookPresenter";

    @Override
    public void refreshBookBrief(String bookId) {
        addDisposable(RemoteRepository.getInstance()
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
                ));
    }
}
