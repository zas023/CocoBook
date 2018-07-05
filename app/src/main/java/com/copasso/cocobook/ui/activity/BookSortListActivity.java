package com.copasso.cocobook.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.copasso.cocobook.R;
import com.copasso.cocobook.utils.RxBusManager;
import com.copasso.cocobook.model.event.BookSubSortEvent;
import com.copasso.cocobook.model.bean.BookSubSortBean;
import com.copasso.cocobook.model.type.BookSortListType;
import com.copasso.cocobook.ui.adapter.HorizonTagAdapter;
import com.copasso.cocobook.base.BaseBackTabActivity;
import com.copasso.cocobook.ui.fragment.BookSortListFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by zhouas666 on 18-1-23.
 * Book Sort List: 分类书籍列表
 */

public class BookSortListActivity extends BaseBackTabActivity {
    private static final String EXTRA_GENDER = "extra_gender";
    private static final String EXTRA_SUB_SORT = "extra_sub_sort";

    /*******************/
    @BindView(R.id.book_sort_list_rv_tag)
    RecyclerView mRvTag;
    /************************************/
    private HorizonTagAdapter mTagAdapter;
    /**********************************/
    private BookSubSortBean mSubSortBean;
    private String mGender;

    public static void startActivity(Context context, String gender, BookSubSortBean subSortBean){
        Intent intent = new Intent(context,BookSortListActivity.class);
        intent.putExtra(EXTRA_GENDER,gender);
        intent.putExtra(EXTRA_SUB_SORT, subSortBean);
        context.startActivity(intent);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_book_sort_list;
    }

    @Override
    protected void setUpToolbar(Toolbar toolbar) {
        super.setUpToolbar(toolbar);
        getSupportActionBar().setTitle(mSubSortBean.getMajor());
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);
        if (savedInstanceState != null){
            mGender = savedInstanceState.getString(EXTRA_GENDER);
            mSubSortBean = savedInstanceState.getParcelable(EXTRA_SUB_SORT);
        }
        else {
            mGender = getIntent().getStringExtra(EXTRA_GENDER);
            mSubSortBean = getIntent().getParcelableExtra(EXTRA_SUB_SORT);
        }
    }

    @Override
    protected List<Fragment> createTabFragments() {
        List<Fragment> fragments = new ArrayList<>();
        for (BookSortListType type : BookSortListType.values()){
            fragments.add(BookSortListFragment.newInstance(mGender,mSubSortBean.getMajor(),type));
        }
        return fragments;
    }

    @Override
    protected List<String> createTabTitles() {
        List<String> titles = new ArrayList<>();
        for (BookSortListType type : BookSortListType.values()){
            titles.add(type.getTypeName());
        }
        return titles;
    }

    @Override
    protected void initClick() {
        super.initClick();
        mTagAdapter.setOnItemClickListener(
                (view,pos) -> {
                    String subType = mTagAdapter.getItem(pos);
                    RxBusManager.getInstance().post(new BookSubSortEvent(subType));
                }
        );
    }

    @Override
    protected void initWidget() {
        super.initWidget();
        initAdapter();
    }

    private void initAdapter(){
        mTagAdapter = new HorizonTagAdapter();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        mRvTag.setLayoutManager(linearLayoutManager);
        mRvTag.setAdapter(mTagAdapter);

        mSubSortBean.getMins().add(0,"全部");
        mTagAdapter.addItems(mSubSortBean.getMins());
    }
    /*****************************************************/
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(EXTRA_GENDER, mGender);
        outState.putParcelable(EXTRA_SUB_SORT, mSubSortBean);
    }
}
