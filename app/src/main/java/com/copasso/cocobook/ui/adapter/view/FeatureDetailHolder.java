package com.copasso.cocobook.ui.adapter.view;

import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.copasso.cocobook.App;
import com.copasso.cocobook.R;
import com.copasso.cocobook.model.bean.BillBookBean;
import com.copasso.cocobook.model.bean.FeatureDetailBean;
import com.copasso.cocobook.base.adapter.ViewHolderImpl;
import com.copasso.cocobook.utils.Constant;

/**
 * Created by zhouas666 on 18-1-23.
 */

public class FeatureDetailHolder extends ViewHolderImpl<FeatureDetailBean> {

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
    public void onBind(FeatureDetailBean value, int pos) {

        //头像
        Glide.with(App.getContext())
                .load(value.getBook().getCover())
                .placeholder(R.drawable.ic_default_portrait)
                .error(R.drawable.ic_load_error)
                .fitCenter()
                .into(mIvPortrait);
        //书单名
        mTvTitle.setText(value.getBook().getTitle());
        //作者
        mTvAuthor.setText(value.getBook().getAuthor());
        //简介
        mTvBrief.setText(value.getBook().getShortIntro());
        //信息
        mTvMsg.setText(App.getContext().getString(R.string.book_message,
                value.getBook().getLatelyFollower(),value.getBook().getRetentionRatio()));
    }
}
