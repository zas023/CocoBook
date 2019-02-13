package com.thmub.cocobook.ui.adapter.view;

import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.thmub.cocobook.App;
import com.thmub.cocobook.R;
import com.thmub.cocobook.model.bean.BillBookBean;
import com.thmub.cocobook.base.adapter.ViewHolderImpl;
import com.thmub.cocobook.utils.Constant;
import com.thmub.cocobook.widget.transform.CircleTransform;

/**
 * Created by zhouas666 on 18-1-23.
 */

public class BillBookHolder extends ViewHolderImpl<BillBookBean> {

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
    public void onBind(BillBookBean value, int pos) {

        //头像
        Glide.with(App.getContext())
                .load(Constant.IMG_BASE_URL+value.getCover())
                .apply(new RequestOptions()
                        .placeholder(R.drawable.ic_default_book_cover)
                        .error(R.drawable.ic_load_error)
                        .fitCenter())
                .into(mIvPortrait);
        //书单名
        mTvTitle.setText(value.getTitle());
        //作者
        mTvAuthor.setText(value.getAuthor());
        //简介
        mTvBrief.setText(value.getShortIntro());
        //信息
        mTvMsg.setText(App.getContext().getString(R.string.book_message,
                value.getLatelyFollower(),value.getRetentionRatio()));
    }
}
