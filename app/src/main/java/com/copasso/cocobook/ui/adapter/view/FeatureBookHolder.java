package com.copasso.cocobook.ui.adapter.view;

import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.copasso.cocobook.R;
import com.copasso.cocobook.model.bean.BillBookBean;
import com.copasso.cocobook.model.bean.FeatureBookBean;
import com.copasso.cocobook.base.adapter.ViewHolderImpl;
import com.copasso.cocobook.utils.Constant;

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
                .placeholder(R.drawable.ic_book_loading)
                .error(R.drawable.ic_load_error)
                .fitCenter()
                .into(mIvCover);
        //书名
        mTvName.setText(value.getBook().getTitle());
    }

    @Override
    protected int getItemLayoutId() {
        return R.layout.item_recommend_book;
    }
}
