package com.copasso.cocobook.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import butterknife.BindView;
import com.copasso.cocobook.R;
import com.copasso.cocobook.base.BaseMVPActivity;
import com.copasso.cocobook.model.bean.FeatureDetailBean;
import com.copasso.cocobook.presenter.FeatureDetailPresenter;
import com.copasso.cocobook.presenter.contract.FeatureDetailContract;
import com.copasso.cocobook.ui.adapter.FeatureDetailAdapter;
import com.copasso.cocobook.widget.RefreshLayout;
import com.copasso.cocobook.widget.itemdecoration.DividerItemDecoration;

import java.util.List;

/**
 * Created by zhouas666 on 18-1-23.
 * 书城栏目activity
 */

public class FeatureBookActivity extends BaseMVPActivity<FeatureDetailContract.Presenter>
        implements FeatureDetailContract.View{
    private static final String EXTRA_FEATURE_ID = "extra_feature_id";
    private static final String EXTRA_FEATURE_NAME = "extra_feature_name";
    /********************/
    @BindView(R.id.refresh_layout)
    RefreshLayout mRefreshLayout;
    @BindView(R.id.refresh_rv_content)
    RecyclerView mRvContent;
    /*******************/
    private FeatureDetailAdapter mAdapter;
    /*****************/
    private String mFeatureId;
    private String mFeatureName;
    public static void startActivity(Context context,String billName, String billId){
        Intent intent = new Intent(context,FeatureBookActivity.class);
        intent.putExtra(EXTRA_FEATURE_ID,billId);
        intent.putExtra(EXTRA_FEATURE_NAME,billName);

        context.startActivity(intent);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_refresh_list;
    }

    @Override
    protected FeatureDetailContract.Presenter bindPresenter() {
        return new FeatureDetailPresenter();
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);
        if (savedInstanceState != null){
            mFeatureId = savedInstanceState.getString(EXTRA_FEATURE_ID);
            mFeatureName = savedInstanceState.getString(EXTRA_FEATURE_NAME);
        }
        else {
            mFeatureId = getIntent().getStringExtra(EXTRA_FEATURE_ID);
            mFeatureName = getIntent().getStringExtra(EXTRA_FEATURE_NAME);
        }
    }

    @Override
    protected void setUpToolbar(Toolbar toolbar) {
        super.setUpToolbar(toolbar);
        getSupportActionBar().setTitle(mFeatureName);
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
        mPresenter.refreshFeatureDetail(mFeatureId);
    }

    private void initAdapter(){
        mRvContent.setLayoutManager(new LinearLayoutManager(this));
        mRvContent.addItemDecoration(new DividerItemDecoration(this));
        mAdapter = new FeatureDetailAdapter();
        mRvContent.setAdapter(mAdapter);

        mAdapter.setOnItemClickListener((view, pos) -> {
            BookDetailActivity.startActivity(mContext,
                    mAdapter.getItem(pos).getBook().get_id(),mAdapter.getItem(pos).getBook().getTitle());
        });
    }

    @Override
    public void finishRefresh(List<FeatureDetailBean> beans) {
        mAdapter.addItems(beans);
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
        outState.putString(EXTRA_FEATURE_ID,mFeatureId);
        outState.putString(EXTRA_FEATURE_NAME,mFeatureName);
    }

}
