package com.copasso.cocobook.base;

/**
 * Created by zhouas666 on 17-4-25.
 */

public abstract class BaseBackMVPActivity<T extends BaseContract.BasePresenter> extends BaseBackActivity{

    protected T mPresenter;

    protected abstract T bindPresenter();

    @Override
    protected void processLogic() {
        attachView(bindPresenter());
    }

    private void attachView(T presenter){
        mPresenter = presenter;
        mPresenter.attachView(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.detachView();
    }
}