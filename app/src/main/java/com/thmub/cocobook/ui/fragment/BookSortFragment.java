package com.thmub.cocobook.ui.fragment;

import android.os.Bundle;

import com.thmub.cocobook.R;
import com.thmub.cocobook.base.BaseMVPFragment;
import com.thmub.cocobook.model.bean.BookSubSortBean;
import com.thmub.cocobook.model.bean.packages.BookSortPackage;
import com.thmub.cocobook.model.bean.packages.BookSubSortPackage;
import com.thmub.cocobook.model.type.BookGenderType;
import com.thmub.cocobook.presenter.BookSortPresenter;
import com.thmub.cocobook.presenter.contract.BookSortContract;
import com.thmub.cocobook.ui.activity.BookSortDetailActivity;
import com.thmub.cocobook.ui.adapter.BookSortAdapter;
import com.thmub.cocobook.widget.RefreshLayout;
import com.thmub.cocobook.widget.itemdecoration.DividerItemDecoration;


import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;

/**
 * Created by zhouas666 on 18-2-1.
 * 书籍分类fragment
 */

public class BookSortFragment extends BaseMVPFragment<BookSortContract.Presenter>
        implements BookSortContract.View {
    /***************************常量********************************/
    private static final String EXTRA_BOOK_GENDER_TYPE = "extra_book_gender_type";

    @BindView(R.id.refresh_layout)
    RefreshLayout mRefreshLayout;
    @BindView(R.id.refresh_rv_content)
    RecyclerView mRecyclerView;
    /***************************视图********************************/
    private BookSortAdapter mAdapter;

    /***************************参数********************************/
    private BookSubSortPackage mSubSortPackage;
    private BookGenderType mBookGenderType;

    /***************************公共方法********************************/
    public static Fragment newInstance(BookGenderType bookGenderType) {
        Bundle bundle = new Bundle();
        bundle.putSerializable(EXTRA_BOOK_GENDER_TYPE, bookGenderType);
        Fragment fragment = new BookSortFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable(EXTRA_BOOK_GENDER_TYPE, mBookGenderType);
    }

    /***************************初始化********************************/
    @Override
    protected int getLayoutId() {
        return R.layout.fragment_refresh_list;
    }

    @Override
    protected BookSortContract.Presenter bindPresenter() {
        return new BookSortPresenter();
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);
        if (savedInstanceState != null) {
            mBookGenderType = (BookGenderType) savedInstanceState.getSerializable(EXTRA_BOOK_GENDER_TYPE);
        } else {
            mBookGenderType = (BookGenderType) getArguments().getSerializable(EXTRA_BOOK_GENDER_TYPE);
        }
    }

    @Override
    protected void initWidget(Bundle savedInstanceState) {
        super.initWidget(savedInstanceState);
        mAdapter = new BookSortAdapter();
        RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(mContext);

        mRecyclerView.setLayoutManager(new GridLayoutManager(mContext,2));
        mRecyclerView.addItemDecoration(itemDecoration);
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    protected void initClick() {
        super.initClick();
        mRefreshLayout.setOnReloadingListener(() -> {
            mPresenter.refreshSortBean();
        });
        mAdapter.setOnItemClickListener(
                (view, pos) -> {
                    if (mBookGenderType == BookGenderType.MALE) {
                        BookSubSortBean subSortBean = mSubSortPackage.getMale().get(pos);
                        //上传
                        BookSortDetailActivity.startActivity(mContext, "male", subSortBean);
                    } else {
                        BookSubSortBean subSortBean = mSubSortPackage.getFemale().get(pos);
                        //上传
                        BookSortDetailActivity.startActivity(mContext, "female", subSortBean);
                    }
                }
        );
    }

    /***************************业务逻辑********************************/
    @Override
    protected void processLogic() {
        super.processLogic();
        mRefreshLayout.showLoading();
        mPresenter.refreshSortBean();
    }

    @Override
    public void showError() {
        mRefreshLayout.showError();
    }

    @Override
    public void complete() {
        mRefreshLayout.showFinish();
    }

    @Override
    public void finishRefresh(BookSortPackage sortPackage, BookSubSortPackage subSortPackage) {
        if (sortPackage == null || sortPackage.getMale().size() == 0 || sortPackage.getFemale().size() == 0) {
            mRefreshLayout.showEmpty();
            return;
        }
        if (mBookGenderType == BookGenderType.MALE) {
            mAdapter.refreshItems(sortPackage.getMale());
        } else {
            mAdapter.refreshItems(sortPackage.getFemale());
        }
        mSubSortPackage = subSortPackage;
    }
}
