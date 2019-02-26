package com.thmub.cocobook.ui.activity;

import androidx.appcompat.widget.Toolbar;

import com.thmub.cocobook.R;
import com.thmub.cocobook.base.BaseTabActivity;
import com.thmub.cocobook.model.type.BookGenderType;
import com.thmub.cocobook.ui.fragment.BookRankFragment;

import java.util.ArrayList;
import java.util.List;

import androidx.fragment.app.Fragment;

/**
 * Created by zhouas666 on 18-1-23.
 * 排行榜activity
 */

public class BookRankActivity extends BaseTabActivity {

    /*****************************初始化********************************/
    @Override
    protected int getLayoutId() {
        return R.layout.activity_base_tab;
    }

    @Override
    protected void setUpToolbar(Toolbar toolbar) {
        super.setUpToolbar(toolbar);
        getSupportActionBar().setTitle("排行榜");
    }

    @Override
    protected List<Fragment> createTabFragments() {
        List<Fragment> fragments = new ArrayList<>(BookGenderType.values().length);
        for (BookGenderType type : BookGenderType.values()) {
            fragments.add(BookRankFragment.newInstance(type));
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
