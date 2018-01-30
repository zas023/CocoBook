package com.copasso.cocobook.ui.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;
import butterknife.BindView;
import com.bumptech.glide.Glide;
import com.copasso.cocobook.R;
import com.copasso.cocobook.model.bean.FeatureBean;
import com.copasso.cocobook.model.bean.SwipePictureBean;
import com.copasso.cocobook.presenter.BookStorePresenter;
import com.copasso.cocobook.presenter.contract.BookStoreContract;
import com.copasso.cocobook.ui.activity.BookDetailActivity;
import com.copasso.cocobook.ui.activity.BookListDetailActivity;
import com.copasso.cocobook.ui.adapter.FeatureAdapter;
import com.copasso.cocobook.ui.base.BaseMVPFragment;
import com.copasso.cocobook.widget.itemdecoration.DividerItemDecoration;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;
import com.youth.banner.loader.ImageLoader;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhouas666 on 18-1-23.
 * 讨论区
 */

public class BookStoreFragment extends BaseMVPFragment<BookStoreContract.Presenter> implements BookStoreContract.View {
    /***************view******************/
    @BindView(R.id.banner)
    Banner banner;
    @BindView(R.id.store_rv_content)
    RecyclerView storeRvContent;

    private FeatureAdapter mAdapter;

    List<SwipePictureBean> mSwipePictures;
    List<FeatureBean> mFeatures;
    private List<String> mImages = new ArrayList<>();
    private List<String> mTitles = new ArrayList<>();

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_store;
    }

    /***********************************init method*************************************************/

    @Override
    protected void initWidget(Bundle savedInstanceState) {

        //设置banner样式
        banner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR_TITLE);
        //设置图片加载器
        banner.setImageLoader(new ImageLoader() {
            @Override
            public void displayImage(Context context, Object path, ImageView imageView) {
                imageView.setScaleType(ImageView.ScaleType.FIT_XY);
                Glide.with(context).load(path).into(imageView);
            }
        });
        //设置banner动画效果
        banner.setBannerAnimation(Transformer.DepthPage);
        //设置自动轮播，默认为true
        banner.isAutoPlay(true);
        //设置轮播时间
        banner.setDelayTime(5000);
        //设置指示器位置（当banner模式中有指示器时）
        banner.setIndicatorGravity(BannerConfig.CENTER);
    }

    private void setUpAdapter() {
        if (mFeatures == null)
            return;
        List<FeatureBean> mTemp=new ArrayList<>();
        mTemp.add(mFeatures.get(0));
        mTemp.add(mFeatures.get(2));
        mTemp.addAll(mFeatures.subList(4,10));
        mAdapter = new FeatureAdapter();
        storeRvContent.setHasFixedSize(true);
        storeRvContent.setLayoutManager(new GridLayoutManager(mContext,2));
        storeRvContent.addItemDecoration(new DividerItemDecoration(mContext));
        storeRvContent.setAdapter(mAdapter);
        mAdapter.addItems(mTemp);

        mAdapter.setOnItemClickListener((view, pos) -> {
            BookListDetailActivity.startActivity(mContext, mAdapter.getItem(pos).get_id());
        });
    }


    /****************************click method********************************/

    @Override
    protected void initClick() {
        banner.setOnBannerListener(
                (pos) -> {
                    SwipePictureBean bean = mSwipePictures.get(pos);
                    if (bean.getType().equals("c-bookdetail"))
                        BookDetailActivity.startActivity(mContext, bean.getLink());
                    if (bean.getType().equals("c-booklist"))
                        BookListDetailActivity.startActivity(mContext, bean.getLink());
                });
    }

    @Override
    public void finishRefreshSwipePictures(List<SwipePictureBean> swipePictureBeans) {

        mSwipePictures = swipePictureBeans;

        for (SwipePictureBean bean : swipePictureBeans) {
            mImages.add(bean.getImg());
            mTitles.add(bean.getTitle());
        }
        //设置图片集合
        banner.setImages(mImages);
        //设置标题集合（当banner样式有显示title时）
        banner.setBannerTitles(mTitles);

        banner.start();
    }

    @Override
    public void finishRefreshFeatures(List<FeatureBean> featureBeans) {
        mFeatures = featureBeans;
        setUpAdapter();
    }

    @Override
    public void finishUpdate() {

    }

    @Override
    public void showErrorTip(String error) {

    }

    @Override
    protected void processLogic() {
        super.processLogic();
        mPresenter.refreshSwipePictures();
        mPresenter.refreshFeatures();
    }

    @Override
    protected BookStoreContract.Presenter bindPresenter() {
        return new BookStorePresenter();
    }

    @Override
    public void showError() {

    }

    @Override
    public void complete() {

    }
}
