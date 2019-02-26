package com.thmub.cocobook.ui.activity;

import androidx.fragment.app.Fragment;
import androidx.appcompat.widget.Toolbar;

import com.thmub.cocobook.R;
import com.thmub.cocobook.base.BaseTabActivity;
import com.thmub.cocobook.model.type.BookGenderType;
import com.thmub.cocobook.ui.fragment.BookSortFragment;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by zhouas666 on 18-1-23.
 * 分类选择activity
 *
 */
public class BookSortActivity extends BaseTabActivity {
    /*****************************初始化********************************/
    @Override
    protected int getLayoutId() {
        return R.layout.activity_base_tab;
    }

    @Override
    protected void setUpToolbar(Toolbar toolbar) {
        super.setUpToolbar(toolbar);
        getSupportActionBar().setTitle("分类");
    }

    @Override
    protected List<Fragment> createTabFragments() {
        List<Fragment> fragments = new ArrayList<>(BookGenderType.values().length);
        for (BookGenderType type : BookGenderType.values()) {
            fragments.add(BookSortFragment.newInstance(type));
        }
        return fragments;
    }

    @Override
    protected List<String> createTabTitles() {
        List<String> titles = new ArrayList<>(BookGenderType.values().length);
        for (BookGenderType type : BookGenderType.values()) {
            titles.add(type.getTypeName());
        }
        return titles;
    }
}
