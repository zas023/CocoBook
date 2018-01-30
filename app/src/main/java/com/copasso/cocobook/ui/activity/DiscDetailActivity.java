package com.copasso.cocobook.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;

import com.copasso.cocobook.R;
import com.copasso.cocobook.model.flag.CommunityType;
import com.copasso.cocobook.ui.base.BaseActivity;
import com.copasso.cocobook.ui.base.BaseBackActivity;
import com.copasso.cocobook.ui.fragment.CommentDetailFragment;
import com.copasso.cocobook.ui.fragment.HelpsDetailFragment;
import com.copasso.cocobook.ui.fragment.ReviewDetailFragment;

/**
 * Created by zhouas666 on 17-4-22.
 */

public class DiscDetailActivity extends BaseBackActivity {

    private static final String EXTRA_COMMENT_TYPE = "extra_comment_type";
    private static final String EXTRA_DETAIL_ID = "extra_detail_id";
    private CommunityType mCommentType;
    private String mDetailId;
    public static void startActivity(Context context,CommunityType communityType,
                                     String detailId){
        Intent intent = new Intent(context,DiscDetailActivity.class);
        intent.putExtra(EXTRA_COMMENT_TYPE,communityType);
        intent.putExtra(EXTRA_DETAIL_ID,detailId);
        context.startActivity(intent);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_discussion_detail;
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);
        if (savedInstanceState != null){
            mCommentType = (CommunityType) savedInstanceState.getSerializable(EXTRA_COMMENT_TYPE);
            mDetailId = savedInstanceState.getString(EXTRA_DETAIL_ID);
        }else {
            mCommentType = (CommunityType) getIntent().getSerializableExtra(EXTRA_COMMENT_TYPE);
            mDetailId = getIntent().getStringExtra(EXTRA_DETAIL_ID);
        }
    }

    @Override
    protected void setUpToolbar(Toolbar toolbar) {
        super.setUpToolbar(toolbar);
        getSupportActionBar().setTitle("详情");
    }

    @Override
    protected void processLogic() {
        super.processLogic();
        Fragment fragment = null;
        switch (mCommentType){
            case REVIEW:
                fragment = ReviewDetailFragment.newInstance(mDetailId);
                break;
            case HELP:
                //因为HELP中的布局内容完全一致，所以就直接用了。
                fragment = HelpsDetailFragment.newInstance(mDetailId);
                break;
            default:
                fragment = CommentDetailFragment.newInstance(mDetailId);
                break;
        }
        getSupportFragmentManager().beginTransaction()
                .add(R.id.discussion_detail_fl,fragment)
                .commit();
    }
    /*****************************lifecycler*************************************/
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable(EXTRA_COMMENT_TYPE, mCommentType);
        outState.putString(EXTRA_DETAIL_ID, mDetailId);
    }
}
