package com.copasso.cocobook.ui.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;

import com.copasso.cocobook.R;
import com.copasso.cocobook.utils.RxBusManager;
import com.copasso.cocobook.model.event.SelectorEvent;
import com.copasso.cocobook.model.bean.BookReviewBean;
import com.copasso.cocobook.model.type.BookDistillate;
import com.copasso.cocobook.model.type.BookSort;
import com.copasso.cocobook.model.type.BookType;
import com.copasso.cocobook.model.type.CommunityType;
import com.copasso.cocobook.presenter.DiscReviewPresenter;
import com.copasso.cocobook.presenter.contract.DiscReviewContract;
import com.copasso.cocobook.ui.activity.DiscDetailActivity;
import com.copasso.cocobook.ui.adapter.DiscReviewAdapter;
import com.copasso.cocobook.ui.base.BaseMVPFragment;
import com.copasso.cocobook.utils.Constant;
import com.copasso.cocobook.widget.itemdecoration.DividerItemDecoration;
import com.copasso.cocobook.widget.refresh.ScrollRefreshRecyclerView;
import com.copasso.cocobook.widget.adapter.WholeAdapter;

import java.util.List;

import butterknife.BindView;
import io.reactivex.android.schedulers.AndroidSchedulers;

/**
 * Created by zhouas666 on 18-1-23.
 * 评论区fragment
 */

public class DiscReviewFragment extends BaseMVPFragment<DiscReviewContract.Presenter> implements DiscReviewContract.View{
    /***************************常量********************************/
    private static final String BUNDLE_BOOK = "bundle_book";
    private static final String BUNDLE_SORT = "bundle_sort";
    private static final String BUNDLE_DISTILLATE = "bundle_distillate";

    @BindView(R.id.scroll_refresh_rv_content)
    ScrollRefreshRecyclerView mRvContent;
    /***************************视图********************************/
    private DiscReviewAdapter mDiscReviewAdapter;

    /***************************参数********************************/
    private BookSort mBookSort = BookSort.DEFAULT;
    private BookType mBookType = BookType.ALL;
    private BookDistillate mDistillate = BookDistillate.ALL;
    private int mStart = 0;
    private int mLimited = 20;

    /***************************公共方法********************************/
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable(BUNDLE_BOOK, mBookType);
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
            mBookType = (BookType) savedInstanceState.getSerializable(BUNDLE_BOOK);
            mBookSort = (BookSort) savedInstanceState.getSerializable(BUNDLE_SORT);
            mDistillate = (BookDistillate) savedInstanceState.getSerializable(BUNDLE_DISTILLATE);
        }
    }

    @Override
    protected void initWidget(Bundle savedInstanceState) {
        super.initWidget(savedInstanceState);
        initAdapter();
    }

    private void initAdapter(){
        mRvContent.setLayoutManager(new LinearLayoutManager(getContext()));
        mRvContent.addItemDecoration(new DividerItemDecoration(getContext()));
        mDiscReviewAdapter = new DiscReviewAdapter(getContext(),new WholeAdapter.Options());
        mRvContent.setAdapter(mDiscReviewAdapter);
    }

    @Override
    protected DiscReviewContract.Presenter bindPresenter() {
        return new DiscReviewPresenter();
    }

    @Override
    protected void initClick() {
        super.initClick();

        mRvContent.setOnRefreshListener(() -> refreshData());
        mDiscReviewAdapter.setOnLoadMoreListener(
                () -> {
                    mPresenter.loadingBookReview(mBookSort,mBookType,mStart, mLimited,mDistillate);
                }
        );
        mDiscReviewAdapter.setOnItemClickListener(
                (view,pos) -> {
                    BookReviewBean bean = mDiscReviewAdapter.getItem(pos);
                    String detailId = bean.get_id();
                    DiscDetailActivity.startActivity(getContext(), CommunityType.REVIEW,detailId);
                }
        );

        RxBusManager.getInstance()
                .toObservable(Constant.MSG_SELECTOR, SelectorEvent.class)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        (event) ->{
                            mBookSort = event.sort;
                            mBookType = event.type;
                            mDistillate = event.distillate;
                            refreshData();
                        }
                );
    }

    /***************************业务逻辑********************************/
    @Override
    protected void processLogic() {
        super.processLogic();
        //首次自动刷新
        mRvContent.startRefresh();
        mPresenter.firstLoading(mBookSort,mBookType,mStart,mLimited,mDistillate);
    }

    private void refreshData(){
        mStart = 0;
        mRvContent.startRefresh();
        mPresenter.refreshBookReview(mBookSort,mBookType,mStart,mLimited,mDistillate);
    }

    @Override
    public void finishRefresh(List<BookReviewBean> beans) {
        mDiscReviewAdapter.refreshItems(beans);
        mStart = beans.size();
    }

    @Override
    public void finishLoading(List<BookReviewBean> beans) {
        mDiscReviewAdapter.addItems(beans);
        mStart += beans.size();
    }

    @Override
    public void showErrorTip() {
        mRvContent.showTip();
    }


    @Override
    public void showError() {
            mDiscReviewAdapter.showLoadError();
    }

    @Override
    public void complete() {
        mRvContent.finishRefresh();
    }

    /***************************状态处理********************************/

    @Override
    public void onStop() {
        super.onStop();
        mPresenter.saveBookReview(mDiscReviewAdapter.getItems());
    }
}
