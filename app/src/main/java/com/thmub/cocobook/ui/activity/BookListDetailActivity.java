package com.thmub.cocobook.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;

import com.thmub.cocobook.R;
import com.thmub.cocobook.base.BaseMVPActivity;
import com.thmub.cocobook.model.bean.BookListDetailBean;
import com.thmub.cocobook.presenter.BookListDetailPresenter;
import com.thmub.cocobook.presenter.contract.BookListDetailContract;
import com.thmub.cocobook.ui.adapter.BookListDetailAdapter;
import com.thmub.cocobook.ui.view.BookListDetailHeader;
import com.thmub.cocobook.widget.RefreshLayout;
import com.thmub.cocobook.base.adapter.QuickAdapter;
import com.thmub.cocobook.widget.itemdecoration.DividerItemDecoration;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by zhouas666 on 18-2-1.
 * 书单详情activity
 */

public class BookListDetailActivity extends BaseMVPActivity<BookListDetailContract.Presenter>
        implements BookListDetailContract.View {

    /***************************Constant********************************/
    private static final String EXTRA_DETAIL_ID = "extra_detail_id";

    /***************************View********************************/
    @BindView(R.id.refresh_layout)
    RefreshLayout mRefreshLayout;
    @BindView(R.id.refresh_rv_content)
    RecyclerView mRvContent;

    private BookListDetailAdapter mDetailAdapter;
    private BookListDetailHeader mHeader;
    private List<BookListDetailBean.BooksBean> mBooksList;

    /***************************Variable********************************/
    private String mDetailId;
    private int start = 0;
    private int limit = 20;

    /******************************initialization******************************/
    public static void startActivity(Context context,String detailId){
        Intent intent  =new Intent(context,BookListDetailActivity.class);
        intent.putExtra(EXTRA_DETAIL_ID,detailId);
        context.startActivity(intent);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(EXTRA_DETAIL_ID, mDetailId);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_refresh_list;
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);
        if (savedInstanceState != null){
            mDetailId = savedInstanceState.getString(EXTRA_DETAIL_ID);
        }else {
            mDetailId = getIntent().getStringExtra(EXTRA_DETAIL_ID);
        }
    }

    @Override
    protected void setUpToolbar(Toolbar toolbar) {
        super.setUpToolbar(toolbar);
        getSupportActionBar().setTitle("书单详情");
    }

    @Override
    protected void initWidget() {
        super.initWidget();
        //初始化adapter
        mDetailAdapter = new BookListDetailAdapter(this,new QuickAdapter.Options());
        mHeader = new BookListDetailHeader();
        mDetailAdapter.addHeaderView(mHeader);

        mRvContent.setLayoutManager(new LinearLayoutManager(this));
        mRvContent.addItemDecoration(new DividerItemDecoration(this));
        mRvContent.setAdapter(mDetailAdapter);
    }

    @Override
    protected void initClick() {
        super.initClick();
        mDetailAdapter.setOnItemClickListener(
                (view, pos) -> {
                    String bookId = mBooksList.get(pos).getBook().get_id();
                    BookDetailActivity.startActivity(this,bookId,mBooksList.get(pos).getBook().getTitle());
                }
        );

        mDetailAdapter.setOnLoadMoreListener(
                () -> loadBook()
        );
    }

    /*****************************Transaction*******************************/
    @Override
    protected BookListDetailContract.Presenter bindPresenter() {
        return new BookListDetailPresenter();
    }

    @Override
    protected void processLogic() {
        super.processLogic();
        mRefreshLayout.showLoading();
        mPresenter.refreshBookListDetail(mDetailId);
    }

    @Override
    public void finishRefresh(BookListDetailBean bean) {
        mHeader.setBookListDetail(bean);
        mBooksList = bean.getBooks();
        refreshBook();
    }

    private void refreshBook(){
        start = 0;
        List<BookListDetailBean.BooksBean.BookBean> books = getBookList();
        mDetailAdapter.refreshItems(books);
        start = books.size();
    }

    private void loadBook(){
        List<BookListDetailBean.BooksBean.BookBean> books = getBookList();
        mDetailAdapter.addItems(books);
        start += books.size();
    }

    private List<BookListDetailBean.BooksBean.BookBean> getBookList(){
        int end = start + limit;
        if (end > mBooksList.size()){
            end = mBooksList.size();
        }
        List<BookListDetailBean.BooksBean.BookBean> books = new ArrayList<>(limit);
        for (int i=start; i < end; ++i){
            books.add(mBooksList.get(i).getBook());
        }
        return books;
    }

    @Override
    public void showError() {
        mRefreshLayout.showError();
    }

    @Override
    public void complete() {
        mRefreshLayout.showFinish();
    }


}
