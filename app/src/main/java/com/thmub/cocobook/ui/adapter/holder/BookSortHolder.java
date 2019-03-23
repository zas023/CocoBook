package com.thmub.cocobook.ui.adapter.holder;

import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.thmub.cocobook.App;
import com.thmub.cocobook.R;
import com.thmub.cocobook.model.bean.BookSortBean;
import com.thmub.cocobook.base.adapter.ViewHolderImpl;
import com.thmub.cocobook.utils.Constant;

/**
 * Created by zhouas666 on 18-1-23.
 */

public class BookSortHolder extends ViewHolderImpl<BookSortBean> {

    private TextView mTvType;
    private TextView mTvCount;
    private ImageView mIvCover;

    @Override
    public void initView() {
        mTvType = findById(R.id.sort_tv_type);
        mTvCount = findById(R.id.sort_tv_count);
        mIvCover = findById(R.id.sort_iv_cover);
    }

    @Override
    public void onBind(BookSortBean value, int pos) {
        mTvType.setText(value.getName());
        mTvCount.setText(getContext().getResources().getString(R.string.sort_book_count, value.getBookCount()));
        //封面
        Glide.with(App.getContext())
                .load(Constant.IMG_BASE_URL+value.getBookCover().get(0))
                .apply(new RequestOptions()
                        .placeholder(R.mipmap.ic_default_book_cover)
                        .error(R.mipmap.ic_load_error)
                        .fitCenter())
                .into(mIvCover);
    }

    @Override
    protected int getItemLayoutId() {
        return R.layout.item_book_sort;
    }
}
