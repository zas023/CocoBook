package com.thmub.cocobook.ui.fragment;

import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import butterknife.BindView;

import com.thmub.cocobook.R;
import com.thmub.cocobook.model.bean.PageNodeBean;
import com.thmub.cocobook.presenter.BookStorePresenter;
import com.thmub.cocobook.presenter.contract.BookStoreContract;
import com.thmub.cocobook.ui.activity.*;
import com.thmub.cocobook.ui.adapter.BookStoreAdapter;
import com.thmub.cocobook.base.BaseMVPFragment;
import com.thmub.cocobook.ui.view.BookStoreHeader;
import com.thmub.cocobook.utils.NetworkUtils;

import java.util.List;

/**
 * Created by zhouas666 on 18-1-23.
 * 书城fragment
 */

public class BookStoreFragment extends BaseMVPFragment<BookStoreContract.Presenter>
        implements BookStoreContract.View {
    /***************************常量********************************/
    @BindView(R.id.store_rv_content)
    RecyclerView storeRvContent;
    /***************************视图********************************/
    private BookStoreAdapter mBookStoreAdapter;
    private BookStoreHeader mHeader;
    /***************************参数********************************/

    /***************************初始化********************************/
    @Override
    protected int getLayoutId() {
        return R.layout.fragment_store;
    }

    @Override
    protected void initWidget(Bundle savedInstanceState) {

        mBookStoreAdapter = new BookStoreAdapter();
        storeRvContent.setHasFixedSize(true);
        storeRvContent.setLayoutManager(new LinearLayoutManager(mContext));
        storeRvContent.setAdapter(mBookStoreAdapter);
    }


    @Override
    protected void initClick() {
        mBookStoreAdapter.setOnItemClickListener((view, pos) -> {
            PageNodeBean bean=mBookStoreAdapter.getItem(pos);
            PageNodeActivity.startActivity(mContext,bean.getTitle(),bean.get_id());
        });
    }

    /***************************业务逻辑********************************/
    @Override
    protected BookStoreContract.Presenter bindPresenter() {
        return new BookStorePresenter();
    }

    @Override
    protected void processLogic() {
        super.processLogic();
        if(NetworkUtils.isConnected()){
            mPresenter.refreshPageNodes();
        }
    }

    @Override
    public void finishRefreshPageNode(List<PageNodeBean> pageNodeBeans) {
        //此处移除第一和第二项：
        //第一项中的书籍格式不对，第二项为轮播数据，放在header中
        pageNodeBeans.remove(0);
        pageNodeBeans.remove(0);
        //连续删除，下面的方法是错误的
        //pageNodeBeans.remove(1);
        mBookStoreAdapter.addItems(pageNodeBeans);
        //添加头部布局
        if (mBookStoreAdapter.getItemCount() > 0 && mHeader == null) {
            mHeader = new BookStoreHeader(getContext());
            mBookStoreAdapter.addHeaderView(mHeader);
        }
    }

    @Override
    public void showErrorTip(String error) {

    }

    @Override
    public void showError() {

    }

    @Override
    public void complete() {

    }
}
