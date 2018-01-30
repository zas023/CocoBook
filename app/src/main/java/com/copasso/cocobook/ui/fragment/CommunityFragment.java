package com.copasso.cocobook.ui.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.copasso.cocobook.R;
import com.copasso.cocobook.model.flag.CommunityType;
import com.copasso.cocobook.model.bean.SectionBean;
import com.copasso.cocobook.ui.activity.BookDiscussionActivity;
import com.copasso.cocobook.ui.adapter.SectionAdapter;
import com.copasso.cocobook.ui.base.BaseFragment;
import com.copasso.cocobook.ui.base.adapter.BaseListAdapter;
import com.copasso.cocobook.widget.itemdecoration.DividerItemDecoration;

import java.util.ArrayList;

import butterknife.BindView;

/**
 * Created by zhouas666 on 17-4-15.
 * 讨论区
 */

public class CommunityFragment extends BaseFragment implements BaseListAdapter.OnItemClickListener{
    /***************view******************/
    @BindView(R.id.community_rv_content)
    RecyclerView mRvContent;

    private SectionAdapter mAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_community;
    }

    /***********************************init method*************************************************/

    @Override
    protected void initWidget(Bundle savedInstanceState) {
        setUpAdapter();
    }

    private void setUpAdapter(){
        ArrayList<SectionBean> sections = new ArrayList<>();

        /*觉得采用枚举会好一些，要不然就是在Constant中创建Map类*/
        for (CommunityType type : CommunityType.values()){
            sections.add(new SectionBean(type.getTypeName(),type.getIconId()));
        }

        mAdapter = new SectionAdapter();
        mRvContent.setHasFixedSize(true);
        mRvContent.setLayoutManager(new LinearLayoutManager(getContext()));
        mRvContent.addItemDecoration(new DividerItemDecoration(getContext()));
        mRvContent.setAdapter(mAdapter);
        mAdapter.addItems(sections);
    }

    /****************************click method********************************/

    @Override
    protected void initClick() {
        mAdapter.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(View view, int pos) {
        //根据类型，启动相应的Discussion区
        CommunityType type = CommunityType.values()[pos];
        BookDiscussionActivity.startActivity(getContext(),type);
    }
}
