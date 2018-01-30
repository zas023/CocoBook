package com.copasso.cocobook.ui.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import com.bumptech.glide.Glide;
import com.copasso.cocobook.R;
import com.copasso.cocobook.model.bean.SectionBean;
import com.copasso.cocobook.model.bean.SwipePictureBean;
import com.copasso.cocobook.model.flag.CommunityType;
import com.copasso.cocobook.presenter.BookStorePresenter;
import com.copasso.cocobook.presenter.contract.BookStoreContract;
import com.copasso.cocobook.ui.activity.BookDetailActivity;
import com.copasso.cocobook.ui.activity.BookListActivity;
import com.copasso.cocobook.ui.activity.BookListDetailActivity;
import com.copasso.cocobook.ui.adapter.SectionAdapter;
import com.copasso.cocobook.ui.base.BaseMVPFragment;
import com.copasso.cocobook.widget.itemdecoration.DividerItemDecoration;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;
import com.youth.banner.listener.OnBannerListener;
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

    List<SwipePictureBean> mDatas;
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

    /****************************click method********************************/

    @Override
    protected void initClick() {
        banner.setOnBannerListener(
                (pos) -> {
                    SwipePictureBean bean=mDatas.get(pos);
                    if(bean.getType().equals("c-bookdetail"))
                        BookDetailActivity.startActivity(mContext,bean.getLink());
                    if(bean.getType().equals("c-booklist"))
                        BookListDetailActivity.startActivity(mContext,bean.getLink());
                });
    }

    @Override
    public void finishRefresh(List<SwipePictureBean> swipePictureBeans) {

        mDatas=swipePictureBeans;

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
    public void finishUpdate() {

    }

    @Override
    public void showErrorTip(String error) {

    }

    @Override
    protected void processLogic() {
        super.processLogic();
        mPresenter.refreshSwipePictures();
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
