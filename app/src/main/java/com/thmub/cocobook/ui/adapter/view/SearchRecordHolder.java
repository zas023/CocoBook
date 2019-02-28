package com.thmub.cocobook.ui.adapter.view;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.thmub.cocobook.R;
import com.thmub.cocobook.base.adapter.ViewHolderImpl;

/**
 * Created by zhouas666 on 17-6-2.
 */

public class SearchRecordHolder extends ViewHolderImpl<String>{

    private TextView mTvName;
    private ImageView mIvSearch;
    private ImageView mIvDelete;

    @Override
    public void initView() {
        mTvName = findById(R.id.keyword_tv_name);
        mIvSearch = findById(R.id.keyword_iv_search);
        mIvDelete = findById(R.id.keyword_iv_delete);

        mIvSearch.setVisibility(View.GONE);
        mIvDelete.setVisibility(View.VISIBLE);
    }

    @Override
    public void onBind(String data, int pos) {
        mTvName.setText(data);
    }

    @Override
    protected int getItemLayoutId() {
        return R.layout.item_keyword;
    }
}
