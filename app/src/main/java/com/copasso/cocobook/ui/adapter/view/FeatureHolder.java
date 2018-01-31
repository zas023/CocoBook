package com.copasso.cocobook.ui.adapter.view;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.copasso.cocobook.R;
import com.copasso.cocobook.model.bean.FeatureBean;
import com.copasso.cocobook.model.bean.FeatureBookBean;
import com.copasso.cocobook.model.bean.SectionBean;
import com.copasso.cocobook.model.remote.RemoteRepository;
import com.copasso.cocobook.ui.activity.BookDetailActivity;
import com.copasso.cocobook.ui.adapter.FeatureBookAdapter;
import com.copasso.cocobook.ui.adapter.FeatureDetailAdapter;
import com.copasso.cocobook.ui.base.adapter.ViewHolderImpl;
import com.copasso.cocobook.utils.RxUtils;
import io.reactivex.disposables.CompositeDisposable;

import java.util.List;

/**
 * Created by zhouas666 on 18-1-23.
 */

public class FeatureHolder extends ViewHolderImpl<FeatureBean>{

    private RecyclerView mRvContent;
    private TextView mTvName;

    private FeatureBookAdapter mAdapter;

    @Override
    public void initView(){
        mRvContent =findById(R.id.feature_rv_content);
        mTvName =findById(R.id.feature_tv_name);
        mAdapter=new FeatureBookAdapter();
        mRvContent.setLayoutManager(new GridLayoutManager(getContext(),4));
        mRvContent.setAdapter(mAdapter);

        mAdapter.setOnItemClickListener((view, pos) -> {
            BookDetailActivity.startActivity(getContext(),mAdapter.getItem(pos).getBook().get_id());
        });
    }

    @Override
    public void onBind(FeatureBean value, int pos) {
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
        return R.layout.item_feature;
    }
}
