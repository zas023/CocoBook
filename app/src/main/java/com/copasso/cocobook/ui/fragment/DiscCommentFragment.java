package com.copasso.cocobook.ui.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import butterknife.BindView;
import com.copasso.cocobook.R;
import com.copasso.cocobook.RxBus;
import com.copasso.cocobook.model.event.SelectorEvent;
import com.copasso.cocobook.model.bean.BookCommentBean;
import com.copasso.cocobook.model.type.BookDistillate;
import com.copasso.cocobook.model.type.BookSort;
import com.copasso.cocobook.model.type.CommunityType;
import com.copasso.cocobook.presenter.DiscCommentPresenter;
import com.copasso.cocobook.presenter.contract.DiscCommentContact;
import com.copasso.cocobook.ui.activity.DiscDetailActivity;
import com.copasso.cocobook.ui.adapter.DiscCommentAdapter;
import com.copasso.cocobook.ui.base.BaseMVPFragment;
import com.copasso.cocobook.utils.Constant;
import com.copasso.cocobook.widget.adapter.WholeAdapter;
import com.copasso.cocobook.widget.itemdecoration.DividerItemDecoration;
import com.copasso.cocobook.widget.refresh.ScrollRefreshRecyclerView;
import io.reactivex.android.schedulers.AndroidSchedulers;

import java.util.List;

/**
 * Created by zhouas666 on 18-1-23
 * 综合讨论区、女生区、原创区fragment
 */

public class DiscCommentFragment extends BaseMVPFragment<DiscCommentContact.Presenter> implements DiscCommentContact.View {
    /***************************常量********************************/
    private static final String TAG = "DiscCommentFragment";
    private static final String EXTRA_BLOCK = "extra_block";
    private static final String BUNDLE_BLOCK = "bundle_block";
    private static final String BUNDLE_SORT = "bundle_sort";
    private static final String BUNDLE_DISTILLATE = "bundle_distillate";

    @BindView(R.id.scroll_refresh_rv_content)
    ScrollRefreshRecyclerView mRvContent;
    /***************************视图********************************/
    private DiscCommentAdapter mDiscCommentAdapter;

    /***************************参数********************************/
    private CommunityType mBlock = CommunityType.COMMENT;
    private BookSort mBookSort = BookSort.DEFAULT;
    private BookDistillate mDistillate = BookDistillate.ALL;
    private int mStart = 0;
    private final int mLimited = 20;

    /***************************公共方法********************************/
    public static Fragment newInstance(CommunityType block) {
        Bundle bundle = new Bundle();
        bundle.putSerializable(EXTRA_BLOCK, block);
        Fragment fragment = new DiscCommentFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable(BUNDLE_BLOCK, mBlock);
        outState.putSerializable(BUNDLE_SORT, mBookSort);
        outState.putSerializable(BUNDLE_DISTILLATE, mDistillate);
    }

    /***************************初始化********************************/
    @Override
    protected int getLayoutId() {
        return R.layout.fragment_scroll_refresh_list;
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            mBlock = (CommunityType) savedInstanceState.getSerializable(BUNDLE_BLOCK);
            mBookSort = (BookSort) savedInstanceState.getSerializable(BUNDLE_SORT);
            mDistillate = (BookDistillate) savedInstanceState.getSerializable(BUNDLE_DISTILLATE);
        } else {
            mBlock = (CommunityType) getArguments().getSerializable(EXTRA_BLOCK);
        }
    }

    @Override
    protected void initWidget(Bundle savedInstanceState) {
        initAdapter();
    }

    private void initAdapter() {
        mRvContent.setLayoutManager(new LinearLayoutManager(getContext()));
        mRvContent.addItemDecoration(new DividerItemDecoration(getContext()));
        mDiscCommentAdapter = new DiscCommentAdapter(getContext(), new WholeAdapter.Options());
        mRvContent.setAdapter(mDiscCommentAdapter);
    }

    @Override
    protected void initClick() {
        //下滑刷新
        mRvContent.setOnRefreshListener(() -> refreshData());
        //上滑加载
        mDiscCommentAdapter.setOnLoadMoreListener(
                () -> mPresenter.loadingComment(mBlock, mBookSort, mStart, mLimited, mDistillate)
        );
        mDiscCommentAdapter.setOnItemClickListener(
                (view, pos) -> {
                    BookCommentBean bean = mDiscCommentAdapter.getItem(pos);
                    String detailId = bean.get_id();
                    DiscDetailActivity.startActivity(getContext(), mBlock, detailId);
                }
        );
        //选择刷新
        RxBus.getInstance()
                .toObservable(Constant.MSG_SELECTOR, SelectorEvent.class)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        (event) -> {
                            mBookSort = event.sort;
                            mDistillate = event.distillate;
                            refreshData();
                        }
                );
    }

    @Override
    protected DiscCommentPresenter bindPresenter() {
        return new DiscCommentPresenter();
    }

    /***************************业务逻辑********************************/
    @Override
    protected void processLogic() {
        super.processLogic();
        //首次加载数据
        mRvContent.startRefresh();
        mPresenter.firstLoading(mBlock, mBookSort, mStart, mLimited, mDistillate);
    }

    private void refreshData() {
        mStart = 0;
        mRvContent.startRefresh();
        mPresenter.refreshComment(mBlock, mBookSort, mStart, mLimited, mDistillate);
    }

    @Override
    public void finishRefresh(List<BookCommentBean> beans) {
        mDiscCommentAdapter.refreshItems(beans);
        mStart = beans.size();
    }

    @Override
    public void finishLoading(List<BookCommentBean> beans) {
        mDiscCommentAdapter.addItems(beans);
        mStart += beans.size();
    }

    @Override
    public void showErrorTip() {
        mRvContent.showTip();
    }

    @Override
    public void showError() {
        mDiscCommentAdapter.showLoadError();
    }

    @Override
    public void complete() {
        mRvContent.finishRefresh();
    }

    /***************************状态处理********************************/
    @Override
    public void onStop() {
        super.onStop();
        mPresenter.saveComment(mDiscCommentAdapter.getItems());
    }
}
