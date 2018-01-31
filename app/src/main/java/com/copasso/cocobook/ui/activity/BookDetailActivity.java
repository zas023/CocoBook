package com.copasso.cocobook.ui.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import butterknife.OnClick;
import com.bumptech.glide.Glide;
import com.copasso.cocobook.R;
import com.copasso.cocobook.model.bean.*;
import com.copasso.cocobook.model.local.BookRepository;
import com.copasso.cocobook.presenter.BookDetailPresenter;
import com.copasso.cocobook.presenter.contract.BookDetailContract;
import com.copasso.cocobook.ui.adapter.BookListAdapter;
import com.copasso.cocobook.ui.adapter.HotCommentAdapter;
import com.copasso.cocobook.ui.adapter.RecommendBookAdapter;
import com.copasso.cocobook.ui.base.BaseMVPActivity;
import com.copasso.cocobook.ui.base.adapter.BaseListAdapter;
import com.copasso.cocobook.utils.Constant;
import com.copasso.cocobook.utils.StringUtils;
import com.copasso.cocobook.utils.ToastUtils;
import com.copasso.cocobook.utils.UiUtils;
import com.copasso.cocobook.widget.RefreshLayout;
import com.copasso.cocobook.widget.itemdecoration.DividerGridItemDecoration;
import com.copasso.cocobook.widget.itemdecoration.DividerItemDecoration;

import java.util.List;

import butterknife.BindView;

/**
 * Created by zhouas666 on 18-1-23.
 * 书籍详情activity
 */

public class BookDetailActivity extends BaseMVPActivity<BookDetailContract.Presenter>
        implements BookDetailContract.View {
    /************************************常量************************************/
    public static final String RESULT_IS_COLLECTED = "result_is_collected";
    private static final String EXTRA_BOOK_ID = "extra_book_id";
    private static final int REQUEST_READ = 1;

    @BindView(R.id.refresh_layout)
    RefreshLayout mRefreshLayout;
    @BindView(R.id.book_detail_iv_cover)
    ImageView mIvCover;
    @BindView(R.id.book_detail_tv_title)
    TextView mTvTitle;
    @BindView(R.id.book_detail_tv_author)
    TextView mTvAuthor;
    @BindView(R.id.book_detail_tv_type)
    TextView mTvType;
    @BindView(R.id.book_detail_tv_word_count)
    TextView mTvWordCount;
    @BindView(R.id.book_detail_tv_lately_update)
    TextView mTvLatelyUpdate;
    @BindView(R.id.book_list_tv_chase)
    TextView mTvChase;
    @BindView(R.id.book_detail_tv_read)
    TextView mTvRead;
    @BindView(R.id.book_detail_tv_follower_count)
    TextView mTvFollowerCount;
    @BindView(R.id.book_detail_tv_retention)
    TextView mTvRetention;
    @BindView(R.id.book_detail_tv_day_word_count)
    TextView mTvDayWordCount;
    @BindView(R.id.book_detail_tv_brief)
    TextView mTvBrief;
    @BindView(R.id.book_detail_tv_more_comment)
    TextView mTvMoreComment;
    @BindView(R.id.book_detail_rv_hot_comment)
    RecyclerView mRvHotComment;
    @BindView(R.id.book_detail_rv_community)
    RelativeLayout mRvCommunity;
    @BindView(R.id.book_detail_tv_community)
    TextView mTvCommunity;
    @BindView(R.id.book_detail_tv_posts_count)
    TextView mTvPostsCount;
    @BindView(R.id.book_list_tv_recommend_books)
    TextView mTvRecommendBooks;
    @BindView(R.id.book_detail_rv_recommend_books)
    RecyclerView mRvRecommendBooks;
    @BindView(R.id.book_list_tv_recommend_book_list)
    TextView mTvRecommendBookList;
    @BindView(R.id.book_detail_rv_recommend_book_list)
    RecyclerView mRvRecommendBookList;
    /************************************视图***********************************/
    private HotCommentAdapter mHotCommentAdapter;
    private RecommendBookAdapter mBooksAdapter;
    private BookListAdapter mBookListAdapter;
    private CollBookBean mCollBookBean;
    private ProgressDialog mProgressDialog;
    /************************************参数************************************/
    private String mBookId;
    private boolean isBriefOpen = false;
    private boolean isCollected = false;

    /**********************************公共方法**********************************/
    public static void startActivity(Context context, String bookId) {
        Intent intent = new Intent(context, BookDetailActivity.class);
        intent.putExtra(EXTRA_BOOK_ID, bookId);
        context.startActivity(intent);
    }
    /************************************初始化***********************************/
    @Override
    protected int getLayoutId() {
        return R.layout.activity_book_detail;
    }

    @Override
    protected void initWidget() {
        super.initWidget();
        //热门评论列表
        mHotCommentAdapter = new HotCommentAdapter();
        mRvHotComment.setLayoutManager(new LinearLayoutManager(mContext));
        mRvHotComment.addItemDecoration(new DividerItemDecoration(mContext));
        mRvHotComment.setAdapter(mHotCommentAdapter);

        //推荐如图书列表
        mBooksAdapter = new RecommendBookAdapter();
        mRvRecommendBooks.setLayoutManager(new GridLayoutManager(mContext, 3));
        mRvRecommendBooks.addItemDecoration(new DividerGridItemDecoration(mContext
                , R.drawable.shape_divider_row, R.drawable.shape_divider_col));
        mRvRecommendBooks.setAdapter(mBooksAdapter);

        //推荐书单列表
        mBookListAdapter = new BookListAdapter();
        mRvRecommendBookList.setLayoutManager(new LinearLayoutManager(mContext));
        mRvRecommendBookList.addItemDecoration(new DividerItemDecoration(mContext));
        mRvRecommendBookList.setAdapter(mBookListAdapter);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);
        if (savedInstanceState != null) {
            mBookId = savedInstanceState.getString(EXTRA_BOOK_ID);
        } else {
            mBookId = getIntent().getStringExtra(EXTRA_BOOK_ID);
        }
    }

    @Override
    protected void setUpToolbar(Toolbar toolbar) {
        super.setUpToolbar(toolbar);
        getSupportActionBar().setTitle("书籍详情");
    }

    @Override
    protected void initClick() {
        super.initClick();
        //监听推荐图书
        mBooksAdapter.setOnItemClickListener((view, pos) -> {
            BookDetailActivity.startActivity(mContext, mBooksAdapter.getItem(pos).get_id());
        });
        //监听推荐书单
        mBookListAdapter.setOnItemClickListener((view, pos) -> {
            BookListDetailActivity.startActivity(mContext, mBookListAdapter.getItem(pos).get_id());
        });
    }

    /************************************数据请求************************************/
    @Override
    protected BookDetailContract.Presenter bindPresenter() {
        return new BookDetailPresenter();
    }

    @Override
    protected void processLogic() {
        super.processLogic();
        mRefreshLayout.showLoading();
        mPresenter.refreshBookDetail(mBookId);
    }

    /**
     * 书籍详情
     *
     * @param bean
     */
    @Override
    public void finishRefresh(BookDetailBean bean) {
        //封面
        Glide.with(this)
                .load(Constant.IMG_BASE_URL + bean.getCover())
                .placeholder(R.drawable.ic_book_loading)
                .error(R.drawable.ic_load_error)
                .centerCrop()
                .into(mIvCover);
        //书名
        mTvTitle.setText(bean.getTitle());
        //作者
        mTvAuthor.setText(bean.getAuthor());
        //类型
        mTvType.setText(bean.getMajorCate());

        //总字数
        mTvWordCount.setText(getResources().getString(R.string.nb_book_word, bean.getWordCount() / 10000));
        //更新时间
        mTvLatelyUpdate.setText(StringUtils.dateConvert(bean.getUpdated(), Constant.FORMAT_BOOK_DATE));
        //追书人数
        mTvFollowerCount.setText(bean.getFollowerCount() + "");
        //存留率
        mTvRetention.setText(bean.getRetentionRatio() + "%");
        //日更字数
        mTvDayWordCount.setText(bean.getSerializeWordCount() + "");
        //简介
        mTvBrief.setText(bean.getLongIntro());
        //社区
        mTvCommunity.setText(getResources().getString(R.string.nb_book_detail_community, bean.getTitle()));
        //帖子数
        mTvPostsCount.setText(getResources().getString(R.string.nb_book_detail_posts_count, bean.getPostCount()));
        mCollBookBean = BookRepository.getInstance().getCollBook(bean.get_id());

        //判断是否收藏
        if (mCollBookBean != null) {
            isCollected = true;

            mTvChase.setText(UiUtils.getString(R.string.nb_book_detail_give_up));
            mTvRead.setText(UiUtils.getString(R.string.nb_book_detail_go_on_read));
        } else {
            mCollBookBean = bean.getCollBookBean();
        }
    }

    /**
     * 热门评论
     *
     * @param beans
     */
    @Override
    public void finishHotComment(List<HotCommentBean> beans) {
        if (beans.isEmpty()) return;
        mHotCommentAdapter.addItems(beans);
    }


    /**
     * 推荐图书
     *
     * @param beans
     */
    @Override
    public void finishRecommendBooks(List<BillBookBean> beans) {
        if (beans.isEmpty()) return;
        mBooksAdapter.addItems(beans.subList(0, 6));
    }

    /**
     * 推荐书单
     *
     * @param beans
     */
    @Override
    public void finishRecommendBookList(List<BookListBean> beans) {
        if (beans.isEmpty()) {
            mTvRecommendBookList.setVisibility(View.GONE);
            return;
        }
        mBookListAdapter.addItems(beans);
    }

    @Override
    public void waitToBookShelf() {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(this);
            mProgressDialog.setTitle("正在添加到书架中");
        }
        mProgressDialog.show();
    }

    @Override
    public void errorToBookShelf() {
        if (mProgressDialog != null) {
            mProgressDialog.dismiss();
        }
        ToastUtils.show("加入书架失败，请检查网络");
    }

    @Override
    public void succeedToBookShelf() {
        if (mProgressDialog != null) {
            mProgressDialog.dismiss();
        }
        ToastUtils.show("加入书架成功");
    }

    @Override
    public void showError() {
        mRefreshLayout.showError();
    }

    @Override
    public void complete() {
        mRefreshLayout.showFinish();
    }

    /**************************事件处理*****************************/
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(EXTRA_BOOK_ID, mBookId);
    }

    /**
     * 处理返回事件
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //如果进入阅读页面收藏了，就需要返回改变收藏按钮
        if (requestCode == REQUEST_READ) {
            if (data == null) return;

            isCollected = data.getBooleanExtra(RESULT_IS_COLLECTED, false);

            if (isCollected) {
                mTvChase.setText(UiUtils.getString(R.string.nb_book_detail_give_up));
                //修改背景
                mTvChase.setBackground(UiUtils.getDrawable(R.drawable.shape_common_gray_corner));
                mTvRead.setText(UiUtils.getString(R.string.nb_book_detail_go_on_read));
            }
        }
    }

    /**
     * 监听点击事件
     *
     * @param view
     */
    @OnClick({R.id.book_detail_tv_brief, R.id.book_list_tv_chase, R.id.book_detail_tv_read
            , R.id.book_detail_tv_more_books})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.book_detail_tv_brief: //展开简介
                if (isBriefOpen) {
                    mTvBrief.setMaxLines(4);
                    isBriefOpen = false;
                } else {
                    mTvBrief.setMaxLines(8);
                    isBriefOpen = true;
                }
                break;
            case R.id.book_list_tv_chase:  //加入书架
                addShelf();
                break;
            case R.id.book_detail_tv_read:  //开始阅读
                startActivityForResult(new Intent(this, ReadActivity.class)
                        .putExtra(ReadActivity.EXTRA_IS_COLLECTED, isCollected)
                        .putExtra(ReadActivity.EXTRA_COLL_BOOK, mCollBookBean), REQUEST_READ);
                break;
            case R.id.book_detail_tv_more_books:  //更多推荐图书
                RecommendBookActivity.startActivity(mContext, mBookId);
                break;
        }
    }

    /**
     * 追更：加入书架
     */
    private void addShelf() {
        if (isCollected) {
            //放弃点击
            BookRepository.getInstance().deleteCollBookInRx(mCollBookBean);
            mTvChase.setText(UiUtils.getString(R.string.nb_book_detail_chase_update));
            //修改背景
            mTvChase.setBackground(UiUtils.getDrawable(R.drawable.selector_btn_book_list));
            isCollected = false;
        } else {
            mPresenter.addToBookShelf(mCollBookBean);
            mTvChase.setText(UiUtils.getString(R.string.nb_book_detail_give_up));
            //修改背景
            mTvChase.setBackground(UiUtils.getDrawable(R.drawable.shape_common_gray_corner));
            isCollected = true;
        }
    }

}
