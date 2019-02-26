package com.thmub.cocobook.ui.activity;

import androidx.appcompat.widget.Toolbar;
import android.view.View;
import android.widget.ExpandableListView;

import com.thmub.cocobook.R;
import com.thmub.cocobook.base.BaseMVPActivity;
import com.thmub.cocobook.base.BaseTabActivity;
import com.thmub.cocobook.model.bean.BookRankBean;
import com.thmub.cocobook.model.bean.packages.BillboardPackage;
import com.thmub.cocobook.model.type.BookListType;
import com.thmub.cocobook.model.type.BookRankType;
import com.thmub.cocobook.presenter.BillboardPresenter;
import com.thmub.cocobook.presenter.contract.BillboardContract;
import com.thmub.cocobook.ui.adapter.BillboardAdapter;
import com.thmub.cocobook.ui.fragment.BookRankFragment;
import com.thmub.cocobook.widget.RefreshLayout;

import java.util.ArrayList;
import java.util.List;

import androidx.fragment.app.Fragment;
import butterknife.BindView;

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
    protected List<Fragment> createTabFragments() {
        List<Fragment> fragments = new ArrayList<>(BookRankType.values().length);
        for (BookRankType type : BookRankType.values()) {
            fragments.add(BookRankFragment.newInstance(type));
        }
        return fragments;
    }

    @Override
    protected List<String> createTabTitles() {
        List<String> titles = new ArrayList<>(BookRankType.values().length);
        for (BookRankType type : BookRankType.values()) {
            titles.add(type.getTypeName());
        }
        return titles;
    }

    @Override
    protected void initWidget() {
        super.initWidget();
    }

    @Override
    protected void setUpToolbar(Toolbar toolbar) {
        super.setUpToolbar(toolbar);
        getSupportActionBar().setTitle("排行榜");
    }


    @Override
    protected void initClick() {
        super.initClick();
    }

}
