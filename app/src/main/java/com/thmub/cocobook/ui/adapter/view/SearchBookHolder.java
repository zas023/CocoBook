package com.thmub.cocobook.ui.adapter.view;

import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.thmub.cocobook.R;
import com.thmub.cocobook.model.bean.packages.SearchBookPackage;
import com.thmub.cocobook.base.adapter.ViewHolderImpl;
import com.thmub.cocobook.utils.Constant;

/**
 * Created by zhouas666 on 17-6-2.
 */

public class SearchBookHolder extends ViewHolderImpl<SearchBookPackage.BooksBean> {

    private ImageView mIvCover;
    private TextView mTvName;
    private TextView mTvBrief;

    @Override
    public void initView() {
        mIvCover = findById(R.id.search_book_iv_cover);
        mTvName = findById(R.id.search_book_tv_name);
        mTvBrief = findById(R.id.search_book_tv_brief);
    }

    @Override
    public void onBind(SearchBookPackage.BooksBean data, int pos) {
        //显示图片
        Glide.with(getContext())
                .load(Constant.IMG_BASE_URL + data.getCover())
                .apply(new RequestOptions()
                        .placeholder(R.drawable.ic_default_book_cover)
                        .error(R.drawable.ic_load_error)
                        .fitCenter())
                .into(mIvCover);

        mTvName.setText(data.getTitle());

        mTvBrief.setText(getContext().getString(R.string.search_book_brief,
                data.getLatelyFollower(),data.getRetentionRatio(),data.getAuthor()));
    }

    @Override
    protected int getItemLayoutId() {
        return R.layout.item_search_book;
    }
}
