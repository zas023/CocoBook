package com.thmub.cocobook.ui.adapter.view;

import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.thmub.cocobook.R;
import com.thmub.cocobook.model.bean.FeatureBookBean;
import com.thmub.cocobook.base.adapter.ViewHolderImpl;

/**
 * Created by zhouas666 on 18-2-8.
 * CollectionBookView
 * 书城书籍view
 */

public class FeatureBookHolder extends ViewHolderImpl<FeatureBookBean>{

    private static final String TAG = "RecommendBookView";
    private ImageView mIvCover;
    private TextView mTvName;


    @Override
    public void initView() {
        mIvCover = findById(R.id.recommend_book_iv_cover);
        mTvName = findById(R.id.recommend_book_tv_name);
    }

    @Override
    public void onBind(FeatureBookBean value, int pos) {
        //书的图片
        Glide.with(getContext())
                .load(value.getBook().getCover())
                .apply(new RequestOptions()
                        .placeholder(R.drawable.ic_default_book_cover)
                        .error(R.drawable.ic_load_error)
                        .fitCenter())
                .into(mIvCover);
        //书名
        mTvName.setText(value.getBook().getTitle());
    }

    @Override
    protected int getItemLayoutId() {
        return R.layout.item_recommend_book;
    }
}
