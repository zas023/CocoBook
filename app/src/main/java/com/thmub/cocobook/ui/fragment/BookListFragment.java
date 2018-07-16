package com.thmub.cocobook.ui.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.thmub.cocobook.R;
import com.thmub.cocobook.manager.RxBusManager;
import com.thmub.cocobook.model.event.BookSubSortEvent;
import com.thmub.cocobook.model.bean.BookListBean;
import com.thmub.cocobook.model.type.BookListType;
import com.thmub.cocobook.presenter.BookListPresenter;
import com.thmub.cocobook.presenter.contract.BookListContract;
import com.thmub.cocobook.ui.activity.BookListDetailActivity;
import com.thmub.cocobook.ui.adapter.BookListAdapter;
import com.thmub.cocobook.base.BaseMVPFragment;
import com.thmub.cocobook.widget.RefreshLayout;
import com.thmub.cocobook.widget.adapter.WholeAdapter;
import com.thmub.cocobook.widget.itemdecoration.DividerItemDecoration;

import java.util.List;

import butterknife.BindView;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;

/**
 * Created by zhouas666 on 18-2-1.
 * 书单fragment
 */

public class BookListFragment extends BaseMVPFragment<BookListContract.Presenter>
        implements BookListContract.View{
    /***************************常量********************************/
    private static final String EXTRA_BOOK_LIST_TYPE = "extra_book_list_type";
    private static final String BUNDLE_BOOK_TAG = "bundle_book_tag";

    @BindView(R.id.refresh_layout)
    RefreshLayout mRefreshLayout;
    @BindView(R.id.refresh_rv_content)
    RecyclerView mRvContent;
    /***************************视图********************************/
    private BookListAdapter mBookListAdapter;

    /***************************参数********************************/
    private BookListType mBookListType;
    private String mTag = "";
    private int mStart = 0;
    private int mLimit = 20;

    /***************************公共方法********************************/
    public static Fragment newInstance(BookListType bookListType){
        Bundle bundle = new Bundle();
        bundle.putSerializable(EXTRA_BOOK_LIST_TYPE,bookListType);
        Fragment fragment = new BookListFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable(EXTRA_BOOK_LIST_TYPE, mBookListType);
        outState.putSerializable(BUNDLE_BOOK_TAG,mTag);
    }

    /***************************初始化********************************/
    @Override
    protected int getLayoutId() {
        return R.layout.fragment_refresh_list;
    }

    @Override
    protected BookListContract.Presenter bindPresenter() {
        return new BookListPresenter();
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);
        if(savedInstanceState != null){
            mBookListType = (BookListType) savedInstanceState.getSerializable(EXTRA_BOOK_LIST_TYPE);
            mTag = savedInstanceState.getString(BUNDLE_BOOK_TAG);
        }
        else {
            mBookListType = (BookListType) getArguments().getSerializable(EXTRA_BOOK_LIST_TYPE);
        }
    }

    @Override
    protected void initWidget(Bundle savedInstanceState) {
        super.initWidget(savedInstanceState);
        initAdapter();
    }


    @Override
    protected void initClick() {
        super.initClick();
        mBookListAdapter.setOnLoadMoreListener(
                () -> {
                   mPresenter.loadBookList(mBookListType,mTag,mStart,mLimit);
                }
        );
        mBookListAdapter.setOnItemClickListener(
                (view,pos) -> {
                    BookListBean bean = mBookListAdapter.getItem(pos);
                    BookListDetailActivity.startActivity(getContext(),bean.get_id());
                }
        );

        Disposable disposable = RxBusManager.getInstance()
                .toObservable(BookSubSortEvent.class)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        event -> {
                            mTag = event.bookSubSort;
                            showRefresh();
                        }
                );
        addDisposable(disposable);
    }

    /***************************业务逻辑********************************/
    @Override
    protected void processLogic() {
        super.processLogic();
        showRefresh();
    }

    private void showRefresh(){
        mStart = 0;
        mRefreshLayout.showLoading();
        mPresenter.refreshBookList(mBookListType,mTag,mStart,mLimit);
    }

    private void initAdapter(){
        mRvContent.setLayoutManager(new LinearLayoutManager(getContext()));
        mRvContent.addItemDecoration(new DividerItemDecoration(getContext()));
        mBookListAdapter = new BookListAdapter(getContext(),new WholeAdapter.Options());
        mRvContent.setAdapter(mBookListAdapter);
    }

    @Override
    public void finishRefresh(List<BookListBean> beans){
        mBookListAdapter.refreshItems(beans);
        mStart = beans.size();
    }

    @Override
    public void finishLoading(List<BookListBean> beans) {
        mBookListAdapter.addItems(beans);
        mStart += beans.size();
    }

    @Override
    public void showLoadError() {
        mBookListAdapter.showLoadError();
    }

    @Override
    public void showError() {
        mRefreshLayout.showError();
    }

    @Override
    public void complete() {
        mRefreshLayout.showFinish();
    }

}
