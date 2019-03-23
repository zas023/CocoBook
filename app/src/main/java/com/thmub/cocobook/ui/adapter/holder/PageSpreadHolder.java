package com.thmub.cocobook.ui.adapter.holder;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.thmub.cocobook.R;
import com.thmub.cocobook.base.adapter.ViewHolderImpl;
import com.thmub.cocobook.model.bean.PageNodeBean;
import com.thmub.cocobook.model.bean.SwipePictureBean;
import com.thmub.cocobook.model.server.RemoteRepository;
import com.thmub.cocobook.ui.activity.BookDetailActivity;
import com.thmub.cocobook.ui.activity.BookListDetailActivity;
import com.thmub.cocobook.utils.RxUtils;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;
import com.youth.banner.loader.ImageLoader;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.disposables.CompositeDisposable;

/**
 * Created by zhouas666 on 18-1-23.
 * 书城轮播view
 */

public class PageSpreadHolder extends ViewHolderImpl<PageNodeBean>{

    private Banner banner;

    List<SwipePictureBean> mSwipePictures;
    private List<String> mImages = new ArrayList<>();
    private List<String> mTitles = new ArrayList<>();

    @Override
    public void initView(){
        banner =findById(R.id.banner);
        //设置banner样式
        banner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR_TITLE_INSIDE);
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
        banner.setOnBannerListener(
                (pos) -> {
                    SwipePictureBean bean = mSwipePictures.get(pos);
                    if (bean.getType().equals("c-bookdetail"))
                        BookDetailActivity.startActivity(getContext(), bean.getLink(),bean.getTitle());
                    if (bean.getType().equals("c-booklist"))
                        BookListDetailActivity.startActivity(getContext(), bean.getLink());
                });
    }

    @Override
    public void onBind(PageNodeBean data, int pos) {
        CompositeDisposable mDisposable=new CompositeDisposable();
        mDisposable.add(RemoteRepository.getInstance()
                .getSwipePictures()
                .compose(RxUtils::toSimpleSingle)
                .subscribe(beans -> {
                    mSwipePictures = beans;
                    for (SwipePictureBean bean : beans) {
                        mImages.add(bean.getImg());
                        mTitles.add(bean.getTitle());
                    }
                    //设置图片集合
                    banner.setImages(mImages);
                    //设置标题集合（当banner样式有显示title时）
                    banner.setBannerTitles(mTitles);

                    banner.start();
                }));
    }

    @Override
    protected int getItemLayoutId() {
        return R.layout.item_book_store_spread;
    }
}
