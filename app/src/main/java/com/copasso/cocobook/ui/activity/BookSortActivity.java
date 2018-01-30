package com.copasso.cocobook.ui.activity;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.copasso.cocobook.R;
import com.copasso.cocobook.model.bean.packages.BookSortPackage;
import com.copasso.cocobook.model.bean.BookSubSortBean;
import com.copasso.cocobook.model.bean.packages.BookSubSortPackage;
import com.copasso.cocobook.presenter.BookSortPresenter;
import com.copasso.cocobook.presenter.contract.BookSortContract;
import com.copasso.cocobook.ui.adapter.BookSortAdapter;
import com.copasso.cocobook.ui.base.BaseMVPActivity;
import com.copasso.cocobook.widget.RefreshLayout;
import com.copasso.cocobook.widget.itemdecoration.DividerGridItemDecoration;

import butterknife.BindView;

/**
 * Created by zhouas666 on 18-1-23.
 * 分类选择
 *
 */

public class BookSortActivity extends BaseMVPActivity<BookSortContract.Presenter> implements BookSortContract.View{
    /*******************Constant*********************/
    private static final int SPAN_COUNT = 3;

    @BindView(R.id.book_sort_rl_refresh)
    RefreshLayout mRlRefresh;
    @BindView(R.id.book_sort_rv_boy)
    RecyclerView mRvBoy;
    @BindView(R.id.book_sort_rv_girl)
    RecyclerView mRvGirl;

    private BookSortAdapter mBoyAdapter;
    private BookSortAdapter mGirlAdapter;

    private BookSubSortPackage mSubSortPackage;
    /**********************init***********************************/
    @Override
    protected int getLayoutId() {
        return R.layout.activity_book_sort;
    }

    @Override
    protected void setUpToolbar(Toolbar toolbar) {
        super.setUpToolbar(toolbar);
        getSupportActionBar().setTitle(
                getResources().getString(R.string.nb_fragment_find_sort));
    }

    @Override
    protected void initWidget() {
        super.initWidget();
        setUpAdapter();
    }

    private void setUpAdapter(){
        mBoyAdapter = new BookSortAdapter();
        mGirlAdapter = new BookSortAdapter();

        RecyclerView.ItemDecoration itemDecoration = new DividerGridItemDecoration(this,R.drawable.shape_divider_row,R.drawable.shape_divider_col);

        mRvBoy.setLayoutManager(new GridLayoutManager(this,SPAN_COUNT));
        mRvBoy.addItemDecoration(itemDecoration);
        mRvBoy.setAdapter(mBoyAdapter);

        mRvGirl.setLayoutManager(new GridLayoutManager(this,SPAN_COUNT));
        mRvGirl.addItemDecoration(itemDecoration);
        mRvGirl.setAdapter(mGirlAdapter);
    }

    @Override
    protected BookSortContract.Presenter bindPresenter() {
        return new BookSortPresenter();
    }

    @Override
    protected void initClick() {
        super.initClick();
        mBoyAdapter.setOnItemClickListener(
                (view,pos) -> {
                    BookSubSortBean subSortBean = mSubSortPackage.getMale().get(pos);
                    //上传
                    BookSortListActivity.startActivity(this,"male",subSortBean);
                }
        );
        mGirlAdapter.setOnItemClickListener(
                (view,pos) -> {
                    BookSubSortBean subSortBean = mSubSortPackage.getFemale().get(pos);
                    //上传
                    BookSortListActivity.startActivity(this,"female",subSortBean);
                }
        );
    }

    /*********************logic*******************************/

    @Override
    protected void processLogic() {
        super.processLogic();

        mRlRefresh.showLoading();
        mPresenter.refreshSortBean();
    }

    /***********************rewrite**********************************/
    @Override
    public void finishRefresh(BookSortPackage sortPackage, BookSubSortPackage subSortPackage) {
        if (sortPackage == null || sortPackage.getMale().size() == 0 || sortPackage.getFemale().size() == 0){
            mRlRefresh.showEmpty();
        }
        else {
            mBoyAdapter.refreshItems(sortPackage.getMale());
            mGirlAdapter.refreshItems(sortPackage.getFemale());
        }
        mSubSortPackage = subSortPackage;
    }

    @Override
    public void showError() {
        mRlRefresh.showError();
    }

    @Override
    public void complete() {
        mRlRefresh.showFinish();
    }
}
