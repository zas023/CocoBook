package com.thmub.cocobook.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;

import com.thmub.cocobook.R;
import com.thmub.cocobook.base.BaseTabActivity;
import com.thmub.cocobook.manager.RxBusManager;
import com.thmub.cocobook.model.event.BookSubSortEvent;
import com.thmub.cocobook.model.bean.BookSubSortBean;
import com.thmub.cocobook.model.type.BookSortListType;
import com.thmub.cocobook.ui.adapter.HorizonTagAdapter;
import com.thmub.cocobook.ui.fragment.BookSortDetailFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by zhouas666 on 18-1-23.
 * 分类书籍列表activity
 */

public class BookSortDetailActivity extends BaseTabActivity {

    /*****************************Constant********************************/
    private static final String EXTRA_GENDER = "extra_gender";
    private static final String EXTRA_SUB_SORT = "extra_sub_sort";

    /*****************************View********************************/
    @BindView(R.id.book_sort_list_rv_tag)
    RecyclerView mRvTag;

    private HorizonTagAdapter mTagAdapter;

    /*****************************Variable********************************/
    private BookSubSortBean mSubSortBean;
    private String mGender;

    public static void startActivity(Context context, String gender, BookSubSortBean subSortBean){
        Intent intent = new Intent(context,BookSortDetailActivity.class);
        intent.putExtra(EXTRA_GENDER,gender);
        intent.putExtra(EXTRA_SUB_SORT, subSortBean);
        context.startActivity(intent);
    }

    /*****************************Initialization********************************/
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
            fragments.add(BookSortDetailFragment.newInstance(mGender,mSubSortBean.getMajor(),type));
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
    /*****************************Life Cycle********************************/
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(EXTRA_GENDER, mGender);
        outState.putParcelable(EXTRA_SUB_SORT, mSubSortBean);
    }
}
