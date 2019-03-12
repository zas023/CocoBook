package com.thmub.cocobook.ui.fragment;

import android.content.Context;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import butterknife.BindView;

import com.bumptech.glide.Glide;
import com.thmub.cocobook.R;
import com.thmub.cocobook.base.adapter.WholeAdapter;
import com.thmub.cocobook.model.bean.PageNodeBean;
import com.thmub.cocobook.model.bean.SwipePictureBean;
import com.thmub.cocobook.model.server.RemoteRepository;
import com.thmub.cocobook.presenter.BookStorePresenter;
import com.thmub.cocobook.presenter.contract.BookStoreContract;
import com.thmub.cocobook.ui.activity.*;
import com.thmub.cocobook.ui.adapter.BookStoreAdapter;
import com.thmub.cocobook.base.BaseMVPFragment;
import com.thmub.cocobook.utils.NetworkUtils;
import com.thmub.cocobook.utils.RxUtils;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;
import com.youth.banner.loader.ImageLoader;

import java.util.ArrayList;
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
    private HeaderItemView mHeaderItemView;
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
        if (mBookStoreAdapter.getItemCount() > 0 && mHeaderItemView == null) {
            mHeaderItemView = new HeaderItemView();
            mBookStoreAdapter.addHeaderView(new HeaderItemView());
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

    /*********************************************************************/
    class HeaderItemView implements WholeAdapter.ItemView {
        private Banner banner;
        private List<SwipePictureBean> mSwipePictures;
        private List<String> mImages = new ArrayList<>();
        private List<String> mTitles = new ArrayList<>();
        @Override
        public View onCreateView(ViewGroup parent) {
            View view = LayoutInflater.from(mContext)
                    .inflate(R.layout.item_book_store_spread, parent, false);
            banner=view.findViewById(R.id.banner);
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
                            BookDetailActivity.startActivity(getContext(), bean.getLink(),bean.getTitle());
                        if (bean.getType().equals("c-booklist"))
                            BookListDetailActivity.startActivity(getContext(), bean.getLink());
                    });
            return view;
        }

        @Override
        public void onBindView(View view) {
            addDisposable(RemoteRepository.getInstance()
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
    }
}
