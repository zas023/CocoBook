package com.thmub.cocobook.ui.adapter.holder;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.widget.TextView;
import com.thmub.cocobook.R;
import com.thmub.cocobook.model.bean.PageNodeBean;
import com.thmub.cocobook.model.server.RemoteRepository;
import com.thmub.cocobook.ui.activity.BookDetailActivity;
import com.thmub.cocobook.ui.adapter.PageNodeBookAdapter;
import com.thmub.cocobook.base.adapter.ViewHolderImpl;
import com.thmub.cocobook.utils.RxUtils;
import io.reactivex.disposables.CompositeDisposable;

/**
 * Created by zhouas666 on 18-1-23.
 * 书城分块view
 */

public class FeatureHolder extends ViewHolderImpl<PageNodeBean>{

    private RecyclerView mRvContent;
    private TextView mTvName;

    private PageNodeBookAdapter mAdapter;

    @Override
    public void initView(){
        mRvContent =findById(R.id.feature_rv_content);
        mTvName =findById(R.id.feature_tv_name);
        mAdapter=new PageNodeBookAdapter();
        mRvContent.setLayoutManager(new GridLayoutManager(getContext(),4));
        mRvContent.setAdapter(mAdapter);

        mAdapter.setOnItemClickListener((view, pos) -> {
            BookDetailActivity.startActivity(getContext(),
                    mAdapter.getItem(pos).getBook().get_id(),mAdapter.getItem(pos).getBook().getTitle());
        });
    }

    @Override
    public void onBind(PageNodeBean value, int pos) {
        mTvName.setText(value.getTitle());
        CompositeDisposable mDisposable=new CompositeDisposable();
        mDisposable.add(RemoteRepository.getInstance()
                .getFeatureBooks(value.get_id())
                .compose(RxUtils::toSimpleSingle)
                .subscribe(featureBookBeans -> {
                    mAdapter.addItems(featureBookBeans);
                }));

    }

    @Override
    protected int getItemLayoutId() {
        return R.layout.item_book_store_node;
    }
}
