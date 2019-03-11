package com.thmub.cocobook.ui.activity;

import android.content.Intent;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import butterknife.BindView;
import com.thmub.cocobook.R;
import com.thmub.cocobook.base.BaseActivity;
import com.thmub.cocobook.utils.Constant;
import com.thmub.cocobook.utils.SharedPreUtils;
import com.thmub.cocobook.utils.SystemBarUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhouas666 on 18-7-12.
 * 欢迎activity
 * 首次进入应用开启此activity
 */

public class WelcomeActivity extends BaseActivity implements ViewPager.OnPageChangeListener {

    @BindView(R.id.welcome_vp)
    ViewPager viewPager;
    @BindView(R.id.welcome_ll_point)
    LinearLayout viewPoints;

    private ImageView imageView;

    private List<View> pageViews; //装载各页面的视图
    private ImageView[] imageViews;//装载导航小圆点


    @Override
    protected int getLayoutId() {
        return R.layout.activity_welcome;
    }

    @Override
    public void initTheme() {
        super.initTheme();
        SystemBarUtils.hideStableStatusBar(this);
    }

    @Override
    protected boolean initSwipeBackEnable() {
        return false;
    }

    @Override
    protected void initWidget() {
        super.initWidget();
        //初始化view
        LayoutInflater inflater = getLayoutInflater();
        pageViews = new ArrayList<>();

        pageViews.add(inflater.inflate(R.layout.layout_welcome_page, null));

        //创建指示器数组，大小是要显示的图片的数量
        imageViews = new ImageView[pageViews.size()];
        for (int i = 0; i < pageViews.size(); i++) {
            imageView = new ImageView(mContext);
            //设置小圆点的参数
            imageView.setLayoutParams(new ViewGroup.LayoutParams(50, 50));//创建一个宽高均为20 的布局
            imageView.setPadding(50, 0, 50, 0);
            //将小圆点layout添加到数组中
            imageViews[i] = imageView;

            //默认选中的是第一张图片，此时第一个小圆点是选中状态，其他不是
            if (i == 0) {
                imageViews[i].setBackgroundResource(R.mipmap.page_indicator_focused);
            } else {
                imageViews[i].setBackgroundResource(R.mipmap.page_indicator_unfocused);
            }

            //添加到小圆点视图组
            viewPoints.addView(imageViews[i]);
        }

        //设置viewpager的适配器和监听事件
        viewPager.setAdapter(new PagerAdapter() {
            @Override
            public int getCount() {
                return pageViews.size();
            }

            @Override
            public boolean isViewFromObject(View view, Object o) {
                // 当划到新的条目, 又返回来, view是否可以被复用.
                // 返回判断规则
                return view == o;
            }

            @Override
            public void destroyItem(ViewGroup container, int position, Object object) {
                //销毁条目
                container.removeView(pageViews.get(position));
            }


            @Override
            public Object instantiateItem(ViewGroup container, int position) {
                //返回要显示的条目内容, 创建条目
                container.addView(pageViews.get(position));
                if (position == pageViews.size()-1) {
                    Button mBtnBoy = container.findViewById(R.id.welcome_btn_boy);
                    Button mBtnGirl = container.findViewById(R.id.welcome_btn_girl);
                    //监听button
                    mBtnBoy.setOnClickListener(v -> {
                        //保存到SharePreference中
                        SharedPreUtils.getInstance().putString(Constant.SHARED_SEX, "male");
                        startActivity(new Intent(mContext, MainActivity.class));
                        finish();
                    });
                    mBtnGirl.setOnClickListener(v -> {
                        //保存到SharePreference中
                        SharedPreUtils.getInstance().putString(Constant.SHARED_SEX, "female");
                        startActivity(new Intent(mContext, MainActivity.class));
                        finish();
                    });
                }
                return pageViews.get(position);
            }
        });

    }

    @Override
    protected void initClick() {
        super.initClick();
        //设置页面更新监听
        viewPager.addOnPageChangeListener(this);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        for(int i=0;i<imageViews.length;i++){
            imageViews[position].setBackgroundResource(R.mipmap.page_indicator_focused);
            //不是当前选中的page，其小圆点设置为未选中的状态
            if(position !=i){
                imageViews[i].setBackgroundResource(R.mipmap.page_indicator_unfocused);
            }
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}

