package com.thmub.cocobook.ui.fragment;

import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.thmub.cocobook.R;
import com.thmub.cocobook.model.local.BookRepository;
import com.thmub.cocobook.ui.adapter.FileSystemAdapter;
import com.thmub.cocobook.utils.media.MediaStoreHelper;
import com.thmub.cocobook.widget.RefreshLayout;
import com.thmub.cocobook.widget.itemdecoration.DividerItemDecoration;

import butterknife.BindView;

/**
 * Created by zhouas666 on 18-2-27.
 * 本地书籍fragment
 */

public class LocalBookFragment extends BaseFileFragment{
    /***************************视图********************************/
    @BindView(R.id.refresh_layout)
    RefreshLayout mRlRefresh;
    @BindView(R.id.local_book_rv_content)
    RecyclerView mRvContent;

    /***************************初始化********************************/
    @Override
    protected int getLayoutId() {
        return R.layout.fragment_local_book;
    }

    @Override
    protected void initWidget(Bundle savedInstanceState) {
        super.initWidget(savedInstanceState);
        initAdapter();
    }

    private void initAdapter(){
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

    /***************************业务逻辑********************************/
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
