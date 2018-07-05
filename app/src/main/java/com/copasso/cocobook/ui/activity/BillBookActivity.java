package com.copasso.cocobook.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;

import com.copasso.cocobook.R;
import com.copasso.cocobook.base.BaseActivity;
import com.copasso.cocobook.base.BaseBackTabActivity;
import com.copasso.cocobook.base.BaseTabActivity;
import com.copasso.cocobook.ui.fragment.BillBookFragment;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by zhouas666 on 18-1-23.
 * 排行榜中书籍activity
 */

public class BillBookActivity extends BaseBackTabActivity {
    /**常量**/
    private static final String EXTRA_WEEK_ID = "extra_week_id";
    private static final String EXTRA_MONTH_ID = "extra_month_id";
    private static final String EXTRA_TOTAL_ID = "extra_total_id";
    private static final String EXTRA_TITLE = "title";
    /**参数**/
    private String mWeekId;
    private String mMonthId;
    private String mTotalId;
    /**公共方法**/
    public static void startActivity(Context context,String title ,String weekId, String monthId, String totalId){
        Intent intent = new Intent(context,BillBookActivity.class);
        intent.putExtra(EXTRA_WEEK_ID,weekId);
        intent.putExtra(EXTRA_MONTH_ID,monthId);
        intent.putExtra(EXTRA_TOTAL_ID,totalId);
        intent.putExtra(EXTRA_TITLE,title);
        context.startActivity(intent);
    }
    /**初始化**/
    @Override
    protected int getLayoutId() {
        return R.layout.activity_base_tab;
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);
        if (savedInstanceState != null){
            mWeekId = savedInstanceState.getString(EXTRA_WEEK_ID);
            mMonthId = savedInstanceState.getString(EXTRA_MONTH_ID);
            mTotalId = savedInstanceState.getString(EXTRA_TOTAL_ID);
        }
        else {
            mWeekId = getIntent().getStringExtra(EXTRA_WEEK_ID);
            mMonthId = getIntent().getStringExtra(EXTRA_MONTH_ID);
            mTotalId = getIntent().getStringExtra(EXTRA_TOTAL_ID);
        }
    }

    @Override
    protected List<Fragment> createTabFragments() {
        List<Fragment> fragments = new ArrayList<>();
        fragments.add(BillBookFragment.newInstance(mWeekId));
        fragments.add(BillBookFragment.newInstance(mMonthId));
        fragments.add(BillBookFragment.newInstance(mTotalId));
        return fragments;
    }

    @Override
    protected List<String> createTabTitles() {
        String [] title = getResources().getStringArray(R.array.fragment_bill_book);
        return Arrays.asList(title);
    }

    @Override
    protected void setUpToolbar(Toolbar toolbar) {
        super.setUpToolbar(toolbar);
        getSupportActionBar().setTitle(getIntent().getStringExtra(EXTRA_TITLE));
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(EXTRA_WEEK_ID,mWeekId);
        outState.putString(EXTRA_MONTH_ID,mMonthId);
        outState.putString(EXTRA_TOTAL_ID,mTotalId);
    }
}
