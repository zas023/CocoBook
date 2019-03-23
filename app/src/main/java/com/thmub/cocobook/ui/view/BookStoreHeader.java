package com.thmub.cocobook.ui.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.thmub.cocobook.R;
import com.thmub.cocobook.base.adapter.QuickAdapter;
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

/**
 * Created by Zhouas666 on 2019-03-23
 * Github: https://github.com/zas023
 */
public class BookStoreHeader implements QuickAdapter.ItemView {
    private Context mContext;
    private Banner banner;
    private List<SwipePictureBean> mSwipePictures;
    private List<String> mImages = new ArrayList<>();
    private List<String> mTitles = new ArrayList<>();

    public BookStoreHeader(Context context) {
        this.mContext = context;
    }

    @Override
    public View onCreateView(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_book_store_spread, parent, false);
        banner = view.findViewById(R.id.banner);
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
        banner.isAutoPlay(false);
        //设置轮播时间
        banner.setDelayTime(5000);
        //设置指示器位置（当banner模式中有指示器时）
        banner.setIndicatorGravity(BannerConfig.CENTER);
        banner.setOnBannerListener(
                (pos) -> {
                    SwipePictureBean bean = mSwipePictures.get(pos);
                    if (bean.getType().equals("c-bookdetail"))
                        BookDetailActivity.startActivity(mContext, bean.getLink(), bean.getTitle());
                    if (bean.getType().equals("c-booklist"))
                        BookListDetailActivity.startActivity(mContext, bean.getLink());
                });
        return view;
    }

    @Override
    public void onBindView(View view) {
        RemoteRepository.getInstance()
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
                });
    }
}
