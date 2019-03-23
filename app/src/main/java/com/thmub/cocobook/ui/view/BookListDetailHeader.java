package com.thmub.cocobook.ui.view;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.thmub.cocobook.App;
import com.thmub.cocobook.R;
import com.thmub.cocobook.base.adapter.QuickAdapter;
import com.thmub.cocobook.model.bean.BookListDetailBean;
import com.thmub.cocobook.utils.Constant;
import com.thmub.cocobook.widget.transform.CircleTransform;

/**
 * Created by Zhouas666 on 2019-03-23
 * Github: https://github.com/zas023
 */
public class BookListDetailHeader implements QuickAdapter.ItemView {

    private TextView tvTitle;
    private TextView tvDesc;
    private ImageView ivPortrait;
    private TextView tvCreate;
    private TextView tvAuthor;
    private TextView tvShare;

    private BookListDetailBean detailBean;

    public void setBookListDetail(BookListDetailBean bean) {
        detailBean = bean;
    }

    @Override
    public View onCreateView(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.header_book_list_detail, parent, false);

        tvTitle = view.findViewById(R.id.book_list_info_tv_title);
        tvDesc = view.findViewById(R.id.book_list_detail_tv_desc);
        ivPortrait = view.findViewById(R.id.book_list_info_iv_cover);
        tvCreate = view.findViewById(R.id.book_list_detail_tv_create);
        tvAuthor = view.findViewById(R.id.book_list_info_tv_author);
        tvShare = view.findViewById(R.id.book_list_detail_tv_share);

        return view;
    }

    @Override
    public void onBindView(View view) {

        //如果没有值就直接返回
        if (detailBean == null) {
            return;
        }
        //标题
        tvTitle.setText(detailBean.getTitle());
        //描述
        tvDesc.setText(detailBean.getDesc());
        //头像
        Glide.with(App.getContext())
                .load(Constant.IMG_BASE_URL + detailBean.getAuthor().getAvatar())
                .apply(new RequestOptions()
                        .placeholder(R.mipmap.ic_default_book_cover)
                        .error(R.mipmap.ic_load_error)
                        .transform(new CircleTransform(App.getContext())))
                .into(ivPortrait);
        //作者
        tvAuthor.setText(detailBean.getAuthor().getNickname());
    }
}
