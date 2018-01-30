package com.copasso.cocobook.ui.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.copasso.cocobook.R;
import com.copasso.cocobook.model.local.BookRepository;
import com.copasso.cocobook.ui.adapter.FileSystemAdapter;
import com.copasso.cocobook.utils.media.MediaStoreHelper;
import com.copasso.cocobook.widget.RefreshLayout;
import com.copasso.cocobook.widget.itemdecoration.DividerItemDecoration;

import java.io.File;
import java.util.List;

import butterknife.BindView;

/**
 * Created by zhouas666 on 17-5-27.
 * 本地书籍
 */

public class LocalBookFragment extends BaseFileFragment{
    @BindView(R.id.refresh_layout)
    RefreshLayout mRlRefresh;
    @BindView(R.id.local_book_rv_content)
    RecyclerView mRvContent;
    @Override
    protected int getLayoutId() {
        return R.layout.fragment_local_book;
    }

    @Override
    protected void initWidget(Bundle savedInstanceState) {
        super.initWidget(savedInstanceState);
        setUpAdapter();
    }

    private void setUpAdapter(){
        mAdapter = new FileSystemAdapter();
        mRvContent.setLayoutManager(new LinearLayoutManager(getContext()));
        mRvContent.addItemDecoration(new DividerItemDecoration(getContext()));
        mRvContent.setAdapter(mAdapter);
    }

    @Override
    protected void initClick() {
        super.initClick();
        mAdapter.setOnItemClickListener(
                (view, pos) -> {
                    //如果是已加载的文件，则点击事件无效。
                    String id = mAdapter.getItem(pos).getAbsolutePath();
                    if (BookRepository.getInstance().getCollBook(id) != null){
                        return;
                    }

                    //点击选中
                    mAdapter.setCheckedItem(pos);

                    //反馈
                    if (mListener != null){
                        mListener.onItemCheckedChange(mAdapter.getItemIsChecked(pos));
                    }
                }
        );
    }

    @Override
    protected void processLogic() {
        super.processLogic();
        MediaStoreHelper.getAllBookFile(getActivity(),
                (files) -> {
                    if (files.isEmpty()){
                        mRlRefresh.showEmpty();
                    }
                    else {
                        mAdapter.refreshItems(files);
                        mRlRefresh.showFinish();
                        //反馈
                        if (mListener != null){
                            mListener.onCategoryChanged();
                        }
                    }
                });
    }
}
