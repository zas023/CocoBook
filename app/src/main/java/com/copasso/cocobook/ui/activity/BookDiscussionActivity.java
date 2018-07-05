package com.copasso.cocobook.ui.activity;

import static com.copasso.cocobook.model.type.BookSelection.*;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;

import com.copasso.cocobook.R;
import com.copasso.cocobook.utils.RxBusManager;
import com.copasso.cocobook.model.event.SelectorEvent;

import com.copasso.cocobook.model.type.BookDistillate;
import com.copasso.cocobook.model.type.BookSort;
import com.copasso.cocobook.model.type.BookType;
import com.copasso.cocobook.model.type.CommunityType;
import com.copasso.cocobook.base.BaseBackActivity;
import com.copasso.cocobook.ui.fragment.DiscCommentFragment;
import com.copasso.cocobook.ui.fragment.DiscHelpsFragment;
import com.copasso.cocobook.ui.fragment.DiscReviewFragment;
import com.copasso.cocobook.utils.Constant;
import com.copasso.cocobook.widget.SelectorView;

import butterknife.BindView;

/**
 * Created by zhouas666 on 18-1-23.
 * 书籍讨论activity
 */

public class BookDiscussionActivity extends BaseBackActivity implements SelectorView.OnItemSelectedListener {
    /*******************************常量********************************/
    private static final String EXTRA_COMMUNITY = "extra_community";
    private static final int TYPE_FIRST = 0;
    private static final int TYPE_SECOND= 1;

    /*******************************视图*******************************/
    @BindView(R.id.book_discussion_sv_selector)
    SelectorView mSvSelector;

    /********************************参数******************************/
    //当前的讨论组
    private CommunityType mType;
    //每个讨论组中的选择分类
    private BookSort mBookSort = BookSort.DEFAULT;
    private BookDistillate mDistillate = BookDistillate.ALL;
    private BookType mBookType = BookType.ALL;

    /******************************公开方法*****************************/
    public static void startActivity(Context context, CommunityType type){
        Intent intent = new Intent(context,BookDiscussionActivity.class);
        intent.putExtra(EXTRA_COMMUNITY,type);
        context.startActivity(intent);
    }

    /******************************初始化方法******************************/
    @Override
    protected int getLayoutId(){
        return R.layout.activity_book_discussion;
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        if (savedInstanceState != null){
            mType = (CommunityType) savedInstanceState.getSerializable(EXTRA_COMMUNITY);
        }
        else {
            mType = (CommunityType) getIntent().getSerializableExtra(EXTRA_COMMUNITY);
        }
    }

    @Override
    protected void setUpToolbar(Toolbar toolbar) {
        getSupportActionBar().setTitle(mType.getTypeName());
    }

    @Override
    protected void initClick() {
        super.initClick();
        mSvSelector.setOnItemSelectedListener(this);
    }

    /*******************************数据请求************************************/
    /**
     * 逻辑处理
     */
    @Override
    protected void processLogic() {
        Fragment fragment = null;

        switch (mType){
            case REVIEW:
                setUpSelectorView(TYPE_SECOND);
                fragment = new DiscReviewFragment();
                break;
            case HELP:
                setUpSelectorView(TYPE_FIRST);
                fragment = new DiscHelpsFragment();
                break;
            default:
                setUpSelectorView(TYPE_FIRST);
                fragment = DiscCommentFragment.newInstance(mType);
                break;
        }

        if (fragment != null){
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.book_discussion_fl,fragment)
                    .commit();
        }
    }

    private void setUpSelectorView(int type){
        if (type == TYPE_FIRST){
            mSvSelector.setSelectData(DISTILLATE.getTypeParams(),SORT_TYPE.getTypeParams());
        }
        else {
            mSvSelector.setSelectData(DISTILLATE.getTypeParams(),
                    BOOK_TYPE.getTypeParams(), SORT_TYPE.getTypeParams());
        }
    }

    /*******************************事件处理************************************/
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable(EXTRA_COMMUNITY,mType);
    }

    @Override
    public void onItemSelected(int type, int pos) {
        //转换器
        switch (type) {
            case 0:
                mDistillate = BookDistillate.values()[pos];
                break;
            case 1:
                if (mSvSelector.getChildCount() == 2) {
                    //当size = 2的时候，就会到Sort这里。
                    mBookSort = BookSort.values()[pos];
                } else if (mSvSelector.getChildCount() == 3) {
                    mBookType = BookType.values()[pos];
                }
                break;
            case 2:
                mBookSort = BookSort.values()[pos];
                break;
            default:
                break;
        }

        RxBusManager.getInstance()
                .post(Constant.MSG_SELECTOR, new SelectorEvent(mDistillate, mBookType, mBookSort));
    }
}
