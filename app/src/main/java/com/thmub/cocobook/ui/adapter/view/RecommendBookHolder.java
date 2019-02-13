package com.thmub.cocobook.ui.adapter.view;

import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.thmub.cocobook.R;
import com.thmub.cocobook.model.bean.BillBookBean;
import com.thmub.cocobook.base.adapter.ViewHolderImpl;
import com.thmub.cocobook.utils.Constant;

/**
 * Created by zhouas666 on 18-2-8.
 * CollectionBookView
 */

public class RecommendBookHolder extends ViewHolderImpl<BillBookBean>{

    private static final String TAG = "RecommendBookView";
    private ImageView mIvCover;
    private TextView mTvName;


    @Override
    public void initView() {
        mIvCover = findById(R.id.recommend_book_iv_cover);
        mTvName = findById(R.id.recommend_book_tv_name);
    }

    @Override
    public void onBind(BillBookBean value, int pos) {
        //书的图片
        Glide.with(getContext())
                .load(Constant.IMG_BASE_URL+value.getCover())
                .apply(new RequestOptions()
                        .placeholder(R.drawable.ic_default_book_cover)
                        .error(R.drawable.ic_load_error)
                        .fitCenter())
                .into(mIvCover);
        //书名
        mTvName.setText(value.getTitle());
    }

    @Override
    protected int getItemLayoutId() {
        return R.layout.item_recommend_book;
    }
}
