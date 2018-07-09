package com.copasso.cocobook.ui.activity;

import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ExpandableListView;

import com.copasso.cocobook.R;
import com.copasso.cocobook.model.bean.BillboardBean;
import com.copasso.cocobook.model.bean.packages.BillboardPackage;
import com.copasso.cocobook.presenter.BillboardPresenter;
import com.copasso.cocobook.presenter.contract.BillboardContract;
import com.copasso.cocobook.ui.adapter.BillboardAdapter;
import com.copasso.cocobook.base.BaseBackMVPActivity;
import com.copasso.cocobook.widget.RefreshLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by zhouas666 on 18-1-23.
 * 排行榜activity
 */

public class BillboardActivity extends BaseBackMVPActivity<BillboardContract.Presenter>
        implements BillboardContract.View, ExpandableListView.OnGroupClickListener, ExpandableListView.OnChildClickListener {

    @BindView(R.id.billboard_rl_refresh)
    RefreshLayout mRlRefresh;
    @BindView(R.id.billboard_elv_boy)
    ExpandableListView mElvBoy;
    @BindView(R.id.billboard_elv_girl)
    ExpandableListView mElvGirl;
    /*****************************视图********************************/
    private BillboardAdapter mBoyAdapter;
    private BillboardAdapter mGirlAdapter;

    /*****************************初始化********************************/
    @Override
    protected int getLayoutId() {
        return R.layout.activity_bilboard;
    }

    @Override
    protected void initWidget() {
        super.initWidget();
        initAdapter();
    }

    @Override
    protected void setUpToolbar(Toolbar toolbar) {
        getSupportActionBar().setTitle("排行榜");
    }

    private void initAdapter() {
        mBoyAdapter = new BillboardAdapter(this);
        mGirlAdapter = new BillboardAdapter(this);
        mElvBoy.setAdapter(mBoyAdapter);
        mElvGirl.setAdapter(mGirlAdapter);
    }

    @Override
    protected void initClick() {
        super.initClick();
        mRlRefresh.setOnReloadingListener(()->{
            mPresenter.loadBillboardList();
        });
        mElvBoy.setOnGroupClickListener(this);
        mElvBoy.setOnChildClickListener(this);

        mElvGirl.setOnGroupClickListener(this);
        mElvGirl.setOnChildClickListener(this);
    }

    @Override
    protected BillboardContract.Presenter bindPresenter() {
        return new BillboardPresenter();
    }

    /*****************************业务逻辑********************************/
    @Override
    protected void processLogic() {
        super.processLogic();

        mRlRefresh.showLoading();
        mPresenter.loadBillboardList();
    }

    @Override
    public void finishRefresh(BillboardPackage beans) {
        if (beans == null || beans.getMale() == null || beans.getFemale() == null
                || beans.getMale().size() == 0 || beans.getFemale().size() == 0) {
            mRlRefresh.showEmpty();
            return;
        }
        updateBillboard(mBoyAdapter, beans.getMale());
        updateBillboard(mGirlAdapter, beans.getFemale());
    }

    private void updateBillboard(BillboardAdapter adapter, List<BillboardBean> disposes) {
        List<BillboardBean> maleGroups = new ArrayList<>();
        List<BillboardBean> maleChildren = new ArrayList<>();
        for (BillboardBean bean : disposes) {
            if (bean.isCollapse()) {
                maleChildren.add(bean);
            } else {
                maleGroups.add(bean);
            }
        }
        maleGroups.add(new BillboardBean("别人家的排行榜"));
        adapter.addGroups(maleGroups);
        adapter.addChildren(maleChildren);
    }

    @Override
    public void showError() {
        mRlRefresh.showError();
    }

    @Override
    public void complete() {
        mRlRefresh.showFinish();
    }

    @Override
    public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
        switch (parent.getId()) {
            case R.id.billboard_elv_boy:
                if (groupPosition != mBoyAdapter.getGroupCount() - 1) {
                    BillboardBean bean = mBoyAdapter.getGroup(groupPosition);
                    BillBookActivity.startActivity(this, bean.getTitle(), bean.get_id(),
                            bean.getMonthRank(), bean.getTotalRank());
                    return true;
                }
                break;
            case R.id.billboard_elv_girl:
                if (groupPosition != mGirlAdapter.getGroupCount() - 1) {
                    BillboardBean bean = mGirlAdapter.getGroup(groupPosition);
                    BillBookActivity.startActivity(this, bean.getTitle(), bean.get_id(),
                            bean.getMonthRank(), bean.getTotalRank());
                    return true;
                }
                break;
            default:
                break;
        }
        return false;
    }

    @Override
    public boolean onChildClick(ExpandableListView expandableListView, View view, int groupPosition, int childPosition, long id) {
        switch (expandableListView.getId()) {
            case R.id.billboard_elv_boy:
                if (groupPosition == mBoyAdapter.getGroupCount() - 1) {
                    BillboardBean bean = mBoyAdapter.getChild(groupPosition, childPosition);
                    OtherBillBookActivity.startActivity(this, bean.getTitle(), bean.get_id());
                    return true;
                }
                break;
            case R.id.billboard_elv_girl:
                if (groupPosition == mGirlAdapter.getGroupCount() - 1) {
                    BillboardBean bean = mGirlAdapter.getChild(groupPosition, childPosition);
                    OtherBillBookActivity.startActivity(this, bean.getTitle(), bean.get_id());
                    return true;
                }
                break;
            default:
                break;
        }
        return false;
    }
}
