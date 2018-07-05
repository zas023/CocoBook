package com.copasso.cocobook.ui.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import butterknife.BindView;
import com.copasso.cocobook.R;
import com.copasso.cocobook.model.bean.SectionBean;
import com.copasso.cocobook.model.type.CommunityType;
import com.copasso.cocobook.ui.activity.BookDiscussionActivity;
import com.copasso.cocobook.ui.adapter.SectionAdapter;
import com.copasso.cocobook.base.BaseFragment;
import com.copasso.cocobook.base.adapter.BaseListAdapter;
import com.copasso.cocobook.widget.itemdecoration.DividerItemDecoration;

import java.util.ArrayList;
/**
 * Created by zhouas666 on 18-1-23.
 * 社区fragment
 */

public class CommunityFragment extends BaseFragment implements BaseListAdapter.OnItemClickListener{

    @BindView(R.id.community_rv_content)
    RecyclerView mRvContent;
    /***************************视图********************************/
    private SectionAdapter mAdapter;

    /***************************初始化********************************/
    @Override
    protected int getLayoutId() {
        return R.layout.fragment_community;
    }

    @Override
    protected void initWidget(Bundle savedInstanceState) {
        initAdapter();
    }

    private void initAdapter(){
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

    @Override
    protected void initClick() {
        mAdapter.setOnItemClickListener(this);
    }

    /***************************事件处理********************************/
    @Override
    public void onItemClick(View view, int pos) {
        //根据类型，启动相应的Discussion区
        BookDiscussionActivity.startActivity(mContext,CommunityType.values()[pos]);
    }
}
