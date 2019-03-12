package com.thmub.cocobook.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;
import butterknife.BindView;
import com.thmub.cocobook.R;
import com.thmub.cocobook.base.BaseMVPActivity;
import com.thmub.cocobook.model.bean.PageNodeDetailBean;
import com.thmub.cocobook.presenter.PageNodePresenter;
import com.thmub.cocobook.presenter.contract.PageNodeContract;
import com.thmub.cocobook.ui.adapter.PageNodeDetailAdapter;
import com.thmub.cocobook.widget.RefreshLayout;
import com.thmub.cocobook.widget.itemdecoration.DividerItemDecoration;

import java.util.List;

/**
 * Created by zhouas666 on 18-1-23.
 * 书城页面站点详情activity
 */

public class PageNodeActivity extends BaseMVPActivity<PageNodeContract.Presenter>
        implements PageNodeContract.View{

    /*****************************Constant**************************************/
    private static final String EXTRA_FEATURE_ID = "extra_feature_id";
    private static final String EXTRA_FEATURE_NAME = "extra_feature_name";

    /*****************************View**************************************/
    @BindView(R.id.refresh_layout)
    RefreshLayout mRefreshLayout;
    @BindView(R.id.refresh_rv_content)
    RecyclerView mRvContent;

    private PageNodeDetailAdapter mAdapter;

    /*****************************Variable**************************************/
    private String mFeatureId;
    private String mFeatureName;

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
        outState.putString(EXTRA_FEATURE_ID,mFeatureId);
        outState.putString(EXTRA_FEATURE_NAME,mFeatureName);
    }

    public static void startActivity(Context context,String billName, String billId){
        Intent intent = new Intent(context,PageNodeActivity.class);
        intent.putExtra(EXTRA_FEATURE_ID,billId);
        intent.putExtra(EXTRA_FEATURE_NAME,billName);

        context.startActivity(intent);
    }

    /*******************************Initialization************************************/
    @Override
    protected int getLayoutId() {
        return R.layout.activity_refresh_list;
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
        mAdapter = new PageNodeDetailAdapter();
        mRvContent.setAdapter(mAdapter);

        mAdapter.setOnItemClickListener((view, pos) -> {
            BookDetailActivity.startActivity(mContext,
                    mAdapter.getItem(pos).getBook().get_id(),mAdapter.getItem(pos).getBook().getTitle());
        });
    }

    /*******************************Transaction************************************/

    @Override
    protected PageNodeContract.Presenter bindPresenter() {
        return new PageNodePresenter();
    }

    @Override
    public void finishRefresh(List<PageNodeDetailBean> beans) {
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


}
