package com.thmub.cocobook.ui.adapter.view;

import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.widget.TextView;

import com.thmub.cocobook.R;
import com.thmub.cocobook.manager.BookManager;
import com.thmub.cocobook.base.adapter.ViewHolderImpl;
import com.thmub.cocobook.widget.page.TxtChapter;

/**
 * Created by zhouas666 on 18-2-3.
 * 章节itemView
 */

public class CategoryHolder extends ViewHolderImpl<TxtChapter> {

    private TextView mTvChapter;

    @Override
    public void initView() {
        mTvChapter = findById(R.id.category_tv_chapter);
    }

    @Override
    public void onBind(TxtChapter value, int pos){
        //首先判断是否该章已下载
        Drawable drawable = null;

        //如果没有链接地址表示是本地文件
        if (value.getLink() == null){
            drawable = ContextCompat.getDrawable(getContext(),R.drawable.selector_category_load);
        }
        else {
            if (value.getBookId() != null && BookManager.isChapterCached(value.getBookId(),value.getTitle())){
                drawable = ContextCompat.getDrawable(getContext(),R.drawable.selector_category_load);
            }
            else {
                drawable = ContextCompat.getDrawable(getContext(), R.drawable.selector_category_unload);
            }
        }

        mTvChapter.setSelected(false);
        mTvChapter.setTextColor(ContextCompat.getColor(getContext(),R.color.textPrimary));
        mTvChapter.setCompoundDrawablesWithIntrinsicBounds(drawable,null,null,null);
        mTvChapter.setText(value.getTitle());
    }

    @Override
    protected int getItemLayoutId() {
        return R.layout.item_category;
    }

    public void setSelectedChapter(){
        mTvChapter.setTextColor(ContextCompat.getColor(getContext(),R.color.light_red));
        mTvChapter.setSelected(true);
    }
}
