package com.copasso.cocobook.ui.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.copasso.cocobook.R;
import com.copasso.cocobook.RxBus;
import com.copasso.cocobook.event.BookSubSortEvent;
import com.copasso.cocobook.model.bean.BookListBean;
import com.copasso.cocobook.model.type.BookListType;
import com.copasso.cocobook.presenter.BookListPresenter;
import com.copasso.cocobook.presenter.contract.BookListContract;
import com.copasso.cocobook.ui.activity.BookListDetailActivity;
import com.copasso.cocobook.ui.adapter.BookListAdapter;
import com.copasso.cocobook.ui.base.BaseMVPFragment;
import com.copasso.cocobook.widget.RefreshLayout;
import com.copasso.cocobook.widget.adapter.WholeAdapter;
import com.copasso.cocobook.widget.itemdecoration.DividerItemDecoration;

import java.util.List;

import butterknife.BindView;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;

/**
 * Created by zhouas666 on 17-5-1.
 * 书单页面
 */

public class BookListFragment extends BaseMVPFragment<BookListContract.Presenter>
        implements BookListContract.View{
    private static final String EXTRA_BOOK_LIST_TYPE = "extra_book_list_type";
    private static final String BUNDLE_BOOK_TAG = "bundle_book_tag";
    @BindView(R.id.refresh_layout)
    RefreshLayout mRefreshLayout;
    @BindView(R.id.refresh_rv_content)
    RecyclerView mRvContent;
    /************************************/
    private BookListAdapter mBookListAdapter;
    /***************************************/
    private BookListType mBookListType;
    private String mTag = "";
    private int mStart = 0;
    private int mLimit = 20;

    public static Fragment newInstance(BookListType bookListType){
        Bundle bundle = new Bundle();
        bundle.putSerializable(EXTRA_BOOK_LIST_TYPE,bookListType);
        Fragment fragment = new BookListFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

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
        setUpAdapter();
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

        Disposable disposable = RxBus.getInstance()
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

    private void setUpAdapter(){
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

    /***********************************************************************/
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable(EXTRA_BOOK_LIST_TYPE, mBookListType);
        outState.putSerializable(BUNDLE_BOOK_TAG,mTag);
    }
}
