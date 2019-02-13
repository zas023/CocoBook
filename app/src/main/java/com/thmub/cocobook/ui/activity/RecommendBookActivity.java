package com.thmub.cocobook.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;
import android.view.View;
import butterknife.BindView;
import com.thmub.cocobook.R;
import com.thmub.cocobook.base.BaseMVPActivity;
import com.thmub.cocobook.model.bean.BillBookBean;
import com.thmub.cocobook.presenter.RecommendBookPresenter;
import com.thmub.cocobook.presenter.contract.RecommendBookContract;
import com.thmub.cocobook.ui.adapter.BillBookAdapter;
import com.thmub.cocobook.base.adapter.BaseListAdapter;
import com.thmub.cocobook.widget.RefreshLayout;
import com.thmub.cocobook.widget.itemdecoration.DividerItemDecoration;

import java.util.List;

/**
 * Created by zhouas666 on 18-1-23.
 * 推荐书籍activity
 */

public class RecommendBookActivity extends BaseMVPActivity<RecommendBookContract.Presenter>
        implements RecommendBookContract.View{
    private static final String EXTRA_BOOK_ID = "extra_book_id";
    /********************/
    @BindView(R.id.refresh_layout)
    RefreshLayout mRefreshLayout;
    @BindView(R.id.refresh_rv_content)
    RecyclerView mRvContent;
    /*******************/
    private BillBookAdapter mBooksAdapter;
    /*****************/
    private String mBookId;
    public static void startActivity(Context context,String bookId){
        Intent intent = new Intent(context,RecommendBookActivity.class);
        intent.putExtra(EXTRA_BOOK_ID,bookId);

        context.startActivity(intent);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_refresh_list;
    }

    @Override
    protected RecommendBookContract.Presenter bindPresenter() {
        return new RecommendBookPresenter();
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);
        if (savedInstanceState != null){
            mBookId = savedInstanceState.getString(EXTRA_BOOK_ID);
        }
        else {
            mBookId = getIntent().getStringExtra(EXTRA_BOOK_ID);
        }
    }

    @Override
    protected void setUpToolbar(Toolbar toolbar) {
        super.setUpToolbar(toolbar);
        getSupportActionBar().setTitle("猜你喜欢");
    }

    @Override
    protected void initWidget() {
        super.initWidget();
        initAdapter();
    }

    @Override
    protected void processLogic() {
        super.processLogic();
        mRefreshLayout.showLoading();
        mPresenter.refreshBookBrief(mBookId);
    }

    private void initAdapter(){
        mRvContent.setLayoutManager(new LinearLayoutManager(this));
        mRvContent.addItemDecoration(new DividerItemDecoration(this));
        mBooksAdapter = new BillBookAdapter();
        mRvContent.setAdapter(mBooksAdapter);

        mBooksAdapter.setOnItemClickListener(new BaseListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int pos) {
                BookDetailActivity.startActivity(mContext,
                        mBooksAdapter.getItem(pos).get_id(),mBooksAdapter.getItem(pos).getTitle());
            }
        });
    }

    @Override
    public void finishRefresh(List<BillBookBean> beans) {
        mBooksAdapter.refreshItems(beans);
    }

    @Override
    public void showError() {
        mRefreshLayout.showError();
    }

    @Override
    public void complete() {
        mRefreshLayout.showFinish();
    }
    /***************************************************/
    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
        outState.putString(EXTRA_BOOK_ID,mBookId);
    }
}
