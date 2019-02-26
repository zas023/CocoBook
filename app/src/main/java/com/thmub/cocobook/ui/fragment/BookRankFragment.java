package com.thmub.cocobook.ui.fragment;

import android.os.Bundle;
import android.view.View;
import android.widget.ExpandableListView;

import com.thmub.cocobook.R;
import com.thmub.cocobook.base.BaseMVPFragment;
import com.thmub.cocobook.model.bean.BookRankBean;
import com.thmub.cocobook.model.bean.packages.BookRankPackage;
import com.thmub.cocobook.model.type.BookGenderType;
import com.thmub.cocobook.presenter.BookRankPresenter;
import com.thmub.cocobook.presenter.contract.BookRankContract;
import com.thmub.cocobook.ui.activity.BookRankDetailActivity;
import com.thmub.cocobook.ui.activity.OtherBillBookActivity;
import com.thmub.cocobook.ui.adapter.BillboardAdapter;
import com.thmub.cocobook.widget.RefreshLayout;

import java.util.ArrayList;
import java.util.List;

import androidx.fragment.app.Fragment;
import butterknife.BindView;

/**
 * Created by zhouas666 on 18-2-1.
 * 书单fragment
 */

public class BookRankFragment extends BaseMVPFragment<BookRankContract.Presenter>
        implements BookRankContract.View, ExpandableListView.OnGroupClickListener, ExpandableListView.OnChildClickListener {
    /***************************常量********************************/
    private static final String EXTRA_BOOK_RANK_TYPE = "extra_book_rank_type";

    @BindView(R.id.book_rank_rl)
    RefreshLayout mRefreshLayout;
    @BindView(R.id.book_rank_elv)
    ExpandableListView mElv;
    /***************************视图********************************/
    private BillboardAdapter mAdapter;

    /***************************参数********************************/
    private BookGenderType mBookGenderType;

    /***************************公共方法********************************/
    public static Fragment newInstance(BookGenderType bookGenderType) {
        Bundle bundle = new Bundle();
        bundle.putSerializable(EXTRA_BOOK_RANK_TYPE, bookGenderType);
        Fragment fragment = new BookRankFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable(EXTRA_BOOK_RANK_TYPE, mBookGenderType);
    }

    /***************************初始化********************************/
    @Override
    protected int getLayoutId() {
        return R.layout.fragment_book_rank;
    }

    @Override
    protected BookRankContract.Presenter bindPresenter() {
        return new BookRankPresenter();
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);
        if (savedInstanceState != null) {
            mBookGenderType = (BookGenderType) savedInstanceState.getSerializable(EXTRA_BOOK_RANK_TYPE);
        } else {
            mBookGenderType = (BookGenderType) getArguments().getSerializable(EXTRA_BOOK_RANK_TYPE);
        }
    }

    @Override
    protected void initWidget(Bundle savedInstanceState) {
        super.initWidget(savedInstanceState);
        mAdapter = new BillboardAdapter(mContext);
        mElv.setAdapter(mAdapter);
    }

    @Override
    protected void initClick() {
        super.initClick();
        mRefreshLayout.setOnReloadingListener(() -> {
            mPresenter.loadBookRank();
        });
        mElv.setOnGroupClickListener(this);
        mElv.setOnChildClickListener(this);
    }

    @Override
    public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
        if (groupPosition != mAdapter.getGroupCount() - 1) {
            BookRankBean bean = mAdapter.getGroup(groupPosition);
            BookRankDetailActivity.startActivity(mContext, bean.getTitle(), bean.get_id(),
                    bean.getMonthRank(), bean.getTotalRank());
            return true;
        }
        return false;
    }

    @Override
    public boolean onChildClick(ExpandableListView expandableListView, View view, int groupPosition, int childPosition, long id) {
        if (groupPosition == mAdapter.getGroupCount() - 1) {
            BookRankBean bean = mAdapter.getChild(groupPosition, childPosition);
            OtherBillBookActivity.startActivity(mContext, bean.getTitle(), bean.get_id());
            return true;
        }
        return false;
    }

    /***************************业务逻辑********************************/
    @Override
    protected void processLogic() {
        super.processLogic();
        mRefreshLayout.showLoading();
        mPresenter.loadBookRank();
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
    public void finishRefresh(BookRankPackage beans) {
        if (beans == null || beans.getMale() == null || beans.getFemale() == null
                || beans.getMale().size() == 0 || beans.getFemale().size() == 0) {
            mRefreshLayout.showEmpty();
            return;
        }
        if (mBookGenderType == BookGenderType.MALE)
            updateBillboard(mAdapter, beans.getMale());
        else
            updateBillboard(mAdapter, beans.getFemale());
    }

    private void updateBillboard(BillboardAdapter adapter, List<BookRankBean> disposes) {
        List<BookRankBean> groups = new ArrayList<>();
        List<BookRankBean> children = new ArrayList<>();
        for (BookRankBean bean : disposes) {
            if (bean.isCollapse()) {
                children.add(bean);
            } else {
                groups.add(bean);
            }
        }
        groups.add(new BookRankBean("别人家的排行榜"));
        adapter.addGroups(groups);
        adapter.addChildren(children);
    }
}
