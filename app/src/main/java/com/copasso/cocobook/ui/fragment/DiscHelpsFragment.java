package com.copasso.cocobook.ui.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;

import com.copasso.cocobook.R;
import com.copasso.cocobook.RxBus;
import com.copasso.cocobook.event.SelectorEvent;
import com.copasso.cocobook.model.bean.BookHelpsBean;
import com.copasso.cocobook.model.type.BookDistillate;
import com.copasso.cocobook.model.type.BookSort;
import com.copasso.cocobook.model.type.CommunityType;
import com.copasso.cocobook.presenter.DiscHelpsPresenter;
import com.copasso.cocobook.presenter.contract.DiscHelpsContract;
import com.copasso.cocobook.ui.activity.DiscDetailActivity;
import com.copasso.cocobook.ui.adapter.DiscHelpsAdapter;
import com.copasso.cocobook.ui.base.BaseMVPFragment;
import com.copasso.cocobook.utils.Constant;
import com.copasso.cocobook.widget.itemdecoration.DividerItemDecoration;
import com.copasso.cocobook.widget.refresh.ScrollRefreshRecyclerView;
import com.copasso.cocobook.widget.adapter.WholeAdapter;

import java.util.List;

import butterknife.BindView;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;

/**
 * Created by zhouas666 on 17-4-21.
 * 帮助区fragment
 */

public class DiscHelpsFragment extends BaseMVPFragment<DiscHelpsContract.Presenter> implements DiscHelpsContract.View{
    /***************************常量********************************/
    private static final String BUNDLE_SORT = "bundle_sort";
    private static final String BUNDLE_DISTILLATE = "bundle_distillate";

    @BindView(R.id.scroll_refresh_rv_content)
    ScrollRefreshRecyclerView mRvContent;
    /***************************视图********************************/
    private DiscHelpsAdapter mDiscHelpsAdapter;

    /***************************参数********************************/
    private BookSort mBookSort = BookSort.DEFAULT;
    private BookDistillate mDistillate = BookDistillate.ALL;
    private int mStart = 0;
    private int mLimited = 20;

    /***************************公共方法********************************/
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable(BUNDLE_SORT, mBookSort);
        outState.putSerializable(BUNDLE_DISTILLATE,mDistillate);
    }

    /***************************初始化********************************/
    @Override
    protected int getLayoutId() {
        return R.layout.fragment_scroll_refresh_list;
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);
        if (savedInstanceState != null){
            mBookSort = (BookSort) savedInstanceState.getSerializable(BUNDLE_SORT);
            mDistillate = (BookDistillate) savedInstanceState.getSerializable(BUNDLE_DISTILLATE);
        }
    }

    @Override
    protected void initWidget(Bundle savedInstanceState) {
        initAdapter();
    }

    private void initAdapter(){
        mRvContent.setLayoutManager(new LinearLayoutManager(getContext()));
        mRvContent.addItemDecoration(new DividerItemDecoration(getContext()));
        mDiscHelpsAdapter = new DiscHelpsAdapter(getContext(),new WholeAdapter.Options());
        mRvContent.setAdapter(mDiscHelpsAdapter);
    }

    @Override
    protected void initClick() {
        mRvContent.setOnRefreshListener(
                () -> startRefresh()
        );
        mDiscHelpsAdapter.setOnLoadMoreListener(
                () -> mPresenter.loadingBookHelps(mBookSort,mStart, mLimited,mDistillate)
        );

        mDiscHelpsAdapter.setOnItemClickListener(
                (view,pos) -> {
                    BookHelpsBean bean = mDiscHelpsAdapter.getItem(pos);
                    DiscDetailActivity.startActivity(getContext(), CommunityType.HELP,bean.get_id());
                }
        );

        Disposable eventDispo = RxBus.getInstance()
                .toObservable(Constant.MSG_SELECTOR, SelectorEvent.class)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        (event) ->{
                            mBookSort = event.sort;
                            mDistillate = event.distillate;
                            startRefresh();
                        }
                );
        addDisposable(eventDispo);
    }

    @Override
    protected DiscHelpsContract.Presenter bindPresenter() {
        return new DiscHelpsPresenter();
    }

    /***************************业务逻辑********************************/
    @Override
    protected void processLogic() {
        super.processLogic();

        mRvContent.startRefresh();
        mPresenter.firstLoading(mBookSort,mStart,mLimited,mDistillate);
    }

    private void startRefresh(){
        mStart = 0;
        mPresenter.refreshBookHelps(mBookSort,mStart,mLimited,mDistillate);
    }

    @Override
    public void finishRefresh(List<BookHelpsBean> beans) {
        mDiscHelpsAdapter.refreshItems(beans);
        mStart = beans.size();
        mRvContent.setRefreshing(false);
    }

    @Override
    public void finishLoading(List<BookHelpsBean> beans) {
        mDiscHelpsAdapter.addItems(beans);
        mStart += beans.size();
    }

    @Override
    public void showErrorTip() {
        mRvContent.showTip();
    }

    @Override
    public void showError() {
        mDiscHelpsAdapter.showLoadError();
    }

    @Override
    public void complete() {
        mRvContent.finishRefresh();
    }

    /***************************状态处理********************************/
    @Override
    public void onStop() {
        super.onStop();
        mPresenter.saveBookHelps(mDiscHelpsAdapter.getItems());
    }
}
