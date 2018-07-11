package com.copasso.cocobook.ui.adapter.view;

import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.copasso.cocobook.R;
import com.copasso.cocobook.model.bean.SortBookBean;
import com.copasso.cocobook.base.adapter.ViewHolderImpl;
import com.copasso.cocobook.utils.Constant;

/**
 * Created by zhouas666 on 18-1-23.
 */

public class BookSortListHolder extends ViewHolderImpl<SortBookBean>{

    private ImageView mIvPortrait;
    private TextView mTvTitle;
    private TextView mTvAuthor;
    private TextView mTvBrief;
    private TextView mTvMsg;

    @Override
    protected int getItemLayoutId() {
        return R.layout.item_book_brief;
    }

    @Override
    public void initView() {
        mIvPortrait = findById(R.id.book_brief_iv_portrait);
        mTvTitle = findById(R.id.book_brief_tv_title);
        mTvAuthor = findById(R.id.book_brief_tv_author);
        mTvBrief = findById(R.id.book_brief_tv_brief);
        mTvMsg = findById(R.id.book_brief_tv_msg);
    }

    @Override
    public void onBind(SortBookBean value, int pos) {
        //头像
        Glide.with(getContext())
                .load(Constant.IMG_BASE_URL+value.getCover())
                .placeholder(R.drawable.ic_default_book_cover)
                .error(R.drawable.ic_load_error)
                .fitCenter()
                .into(mIvPortrait);
        //书单名
        mTvTitle.setText(value.getTitle());
        //作者
        mTvAuthor.setText(value.getAuthor());
        //简介
        mTvBrief.setText(value.getShortIntro());
        //信息
        mTvMsg.setText(getContext().getResources().getString(R.string.book_message,
                value.getLatelyFollower(),value.getRetentionRatio()));
    }
}
