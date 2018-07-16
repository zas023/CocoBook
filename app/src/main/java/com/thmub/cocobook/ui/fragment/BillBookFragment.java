package com.thmub.cocobook.ui.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.thmub.cocobook.R;
import com.thmub.cocobook.model.bean.BillBookBean;
import com.thmub.cocobook.presenter.BillBookPresenter;
import com.thmub.cocobook.presenter.contract.BillBookContract;
import com.thmub.cocobook.ui.activity.BookDetailActivity;
import com.thmub.cocobook.ui.adapter.BillBookAdapter;
import com.thmub.cocobook.base.BaseMVPFragment;
import com.thmub.cocobook.widget.RefreshLayout;
import com.thmub.cocobook.widget.itemdecoration.DividerItemDecoration;

import java.util.List;

import butterknife.BindView;

/**
 * Created by zhouas666 on 18-1-23.
 * 分类fragment
 */

public class BillBookFragment extends BaseMVPFragment<BillBookContract.Presenter>
        implements BillBookContract.View{
    /***************************常量********************************/
    private static final String EXTRA_BILL_ID = "extra_bill_id";

    @BindView(R.id.refresh_layout)
    RefreshLayout mRefreshLayout;
    @BindView(R.id.refresh_rv_content)
    RecyclerView mRvContent;
    /***************************视图********************************/
    private BillBookAdapter mBillBookAdapter;

    /***************************参数********************************/
    private String mBillId;

    /***************************公共方法********************************/
    public static Fragment newInstance(String billId){
        Bundle bundle = new Bundle();
        bundle.putString(EXTRA_BILL_ID,billId);
        Fragment fragment = new BillBookFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    /***************************初始化********************************/
    @Override
    protected int getLayoutId() {
        return R.layout.fragment_refresh_list;
    }

    @Override
    protected BillBookContract.Presenter bindPresenter() {
        return new BillBookPresenter();
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);
        mBillId = getArguments().getString(EXTRA_BILL_ID);
    }

    @Override
    protected void initClick() {
        super.initClick();
        mBillBookAdapter.setOnItemClickListener(
                (view, pos) -> {
                    String bookId = mBillBookAdapter.getItem(pos).get_id();
                    String bookTitle = mBillBookAdapter.getItem(pos).getTitle();
                    BookDetailActivity.startActivity(getContext(),bookId,bookTitle);
                }
        );
    }

    @Override
    protected void initWidget(Bundle savedInstanceState) {
        super.initWidget(savedInstanceState);
        mRvContent.setLayoutManager(new LinearLayoutManager(getContext()));
        mRvContent.addItemDecoration(new DividerItemDecoration(getContext()));
        mBillBookAdapter = new BillBookAdapter();
        mRvContent.setAdapter(mBillBookAdapter);
    }

    /***************************业务逻辑********************************/
    @Override
    protected void processLogic() {
        super.processLogic();
        mRefreshLayout.showLoading();
        mPresenter.refreshBookBrief(mBillId);
    }

    @Override
    public void finishRefresh(List<BillBookBean> beans) {
        mBillBookAdapter.refreshItems(beans);
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
