package com.copasso.cocobook.ui.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.copasso.cocobook.R;
import com.copasso.cocobook.model.bean.CommentBean;
import com.copasso.cocobook.model.bean.CommentDetailBean;
import com.copasso.cocobook.presenter.CommentDetailPresenter;
import com.copasso.cocobook.presenter.contract.CommentDetailContract;
import com.copasso.cocobook.ui.adapter.CommentAdapter;
import com.copasso.cocobook.ui.adapter.GodCommentAdapter;
import com.copasso.cocobook.ui.base.BaseMVPFragment;
import com.copasso.cocobook.utils.Constant;
import com.copasso.cocobook.utils.StringUtils;
import com.copasso.cocobook.widget.BookTextView;
import com.copasso.cocobook.widget.RefreshLayout;
import com.copasso.cocobook.widget.adapter.WholeAdapter;
import com.copasso.cocobook.widget.itemdecoration.DividerItemDecoration;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import com.copasso.cocobook.widget.transform.CircleTransform;

/**
 * Created by zhouas666 on 18-1-23.
 * 综合讨论区、书荒互助区详情fragment
 */

public class CommentDetailFragment extends BaseMVPFragment<CommentDetailContract.Presenter>
        implements CommentDetailContract.View{
    /*****************************常量***********************************/
    private static final String TAG = "CommentDetailFragment";
    private static final String EXTRA_DETAIL_ID = "extra_detail_id";
    @BindView(R.id.refresh_layout)
    RefreshLayout mRefreshLayout;
    @BindView(R.id.refresh_rv_content)
    RecyclerView mRvContent;
    /*****************************视图***********************************/
    private CommentAdapter mCommentAdapter;
    private DetailHeader mDetailHeader;
    /*****************************参数**********************************/
    private String mDetailId;
    private int start = 0;
    private int limit = 30;

    /*****************************公共方法**********************************/
    public static Fragment newInstance(String detailId){
        Bundle bundle = new Bundle();
        bundle.putSerializable(EXTRA_DETAIL_ID,detailId);
        Fragment fragment = new CommentDetailFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(EXTRA_DETAIL_ID, mDetailId);
    }

    /*****************************初始化**********************************/
    @Override
    protected int getLayoutId() {
        return R.layout.fragment_refresh_list;
    }

    @Override
    protected CommentDetailContract.Presenter bindPresenter() {
        return new CommentDetailPresenter();
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);
        if (savedInstanceState != null){
            mDetailId = savedInstanceState.getString(EXTRA_DETAIL_ID);
        }else {
            mDetailId = getArguments().getString(EXTRA_DETAIL_ID);
        }
    }

    @Override
    protected void initWidget(Bundle savedInstanceState) {
        super.initWidget(savedInstanceState);
        mCommentAdapter = new CommentAdapter(getContext(),new WholeAdapter.Options());
        mDetailHeader = new DetailHeader();
        mCommentAdapter.addHeaderView(mDetailHeader);

        mRvContent.setLayoutManager(new LinearLayoutManager(getContext()));
        mRvContent.addItemDecoration(new DividerItemDecoration(getContext()));
        mRvContent.setAdapter(mCommentAdapter);
    }

    @Override
    protected void initClick() {
        super.initClick();
        mCommentAdapter.setOnLoadMoreListener(
                () -> mPresenter.loadComment(mDetailId, start, limit)
        );
    }
    /*****************************业务逻辑**********************************/
    @Override
    protected void processLogic() {
        super.processLogic();
        //获取数据啦
        mRefreshLayout.showLoading();
        mPresenter.refreshCommentDetail(mDetailId,start,limit);
    }

    @Override
    public void finishRefresh(CommentDetailBean commentDetail,
                              List<CommentBean> bestComments,
                              List<CommentBean> comments) {
        //加载
        start = comments.size();
        mDetailHeader.setCommentDetail(commentDetail);
        mDetailHeader.setGodCommentList(bestComments);
        mCommentAdapter.refreshItems(comments);
    }

    @Override
    public void finishLoad(List<CommentBean> comments){
        start += comments.size();
        mCommentAdapter.addItems(comments);
    }

    @Override
    public void showError() {
        mRefreshLayout.showError();
    }

    @Override
    public void showLoadError() {
        mCommentAdapter.showLoadError();
    }

    @Override
    public void complete() {
        mRefreshLayout.showFinish();
    }

    /***********************************************************************/

    class DetailHeader implements WholeAdapter.ItemView{
        @BindView(R.id.disc_detail_iv_portrait)
        ImageView ivPortrait;
        @BindView(R.id.disc_detail_tv_name)
        TextView tvName;
        @BindView(R.id.disc_detail_tv_time)
        TextView tvTime;
        @BindView(R.id.disc_detail_tv_title)
        TextView tvTitle;
        @BindView(R.id.disc_detail_btv_content)
        BookTextView btvContent;
        @BindView(R.id.disc_detail_tv_best_comment)
        TextView tvBestComment;
        @BindView(R.id.disc_detail_rv_best_comments)
        RecyclerView rvBestComments;
        @BindView(R.id.disc_detail_tv_comment_count)
        TextView tvCommentCount;

        GodCommentAdapter godCommentAdapter;
        CommentDetailBean commentDetailBean;
        List<CommentBean> godCommentList;
        Unbinder detailUnbinder = null;
        @Override
        public View onCreateView(ViewGroup parent) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.header_disc_detail,parent,false);
            return view;
        }

        @Override
        public void onBindView(View view) {
            if (detailUnbinder == null){
                detailUnbinder = ButterKnife.bind(this,view);
            }
            //如果没有值就直接返回
            if (commentDetailBean == null || godCommentList == null){
                return;
            }
            //头像
            Glide.with(getContext())
                    .load(Constant.IMG_BASE_URL+commentDetailBean.getAuthor().getAvatar())
                    .placeholder(R.drawable.ic_loadding)
                    .error(R.drawable.ic_load_error)
                    .transform(new CircleTransform(getContext()))
                    .into(ivPortrait);
            //名字
            tvName.setText(commentDetailBean.getAuthor().getNickname());
            //日期
            tvTime.setText(StringUtils.dateConvert(commentDetailBean.getCreated(),Constant.FORMAT_BOOK_DATE));
            //标题
            tvTitle.setText(commentDetailBean.getTitle());
            //内容
            btvContent.setText(commentDetailBean.getContent());
            //设置书籍的点击事件
            btvContent.setOnBookClickListener(
                    bookName -> {
                        Log.d(TAG, "onBindView: "+bookName);
                    }
            );
            //设置神评论
            if (godCommentList.isEmpty()) {
                tvBestComment.setVisibility(View.GONE);
                rvBestComments.setVisibility(View.GONE);
            }
            else {
                tvBestComment.setVisibility(View.VISIBLE);
                rvBestComments.setVisibility(View.VISIBLE);
                //初始化RecyclerView
                initRecyclerView();
                godCommentAdapter.refreshItems(godCommentList);
            }

            if (mCommentAdapter.getItems().isEmpty()){
                tvCommentCount.setText(getResources().getString(R.string.nb_comment_empty_comment));
            }
            else {
                CommentBean firstComment = mCommentAdapter.getItems().get(0);
                //评论数
                tvCommentCount.setText(getResources()
                        .getString(R.string.nb_comment_comment_count,firstComment.getFloor()));
            }
        }

        private void initRecyclerView(){
            if (godCommentAdapter != null) return;
            godCommentAdapter = new GodCommentAdapter();
            rvBestComments.setLayoutManager(new LinearLayoutManager(getContext()));
            rvBestComments.addItemDecoration(new DividerItemDecoration(getContext()));
            rvBestComments.setAdapter(godCommentAdapter);
        }

        public void setCommentDetail(CommentDetailBean bean){
            commentDetailBean = bean;
        }

        public void setGodCommentList(List<CommentBean> beans){
            godCommentList = beans;
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mDetailHeader.detailUnbinder.unbind();
    }
}
