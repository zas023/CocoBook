package com.thmub.cocobook.ui.adapter.holder;

import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.thmub.cocobook.R;
import com.thmub.cocobook.model.bean.CollBookBean;
import com.thmub.cocobook.base.adapter.ViewHolderImpl;
import com.thmub.cocobook.utils.Constant;
import com.thmub.cocobook.utils.StringUtils;

import java.util.HashMap;

/**
 * Created by zhouas666 on 18-2-8.
 * CollectionBookView
 */

public class CollBookHolder extends ViewHolderImpl<CollBookBean>{

    private static final String TAG = "CollBookView";
    private ImageView mIvCover;
    private TextView mTvName;
    private TextView mTvChapter;
    private TextView mTvTime;
    private CheckBox mCbSelected;
    private ImageView mIvRedDot;
    private ImageView mIvTop;

    private OnChickListener listener;

    //判断是否显示CheckBox
    private boolean showCheckBox;
    private HashMap<CollBookBean,Boolean> mSelectedMap;



    public CollBookHolder(HashMap<CollBookBean,Boolean> selectedMap, boolean showCheckBox){
        this.mSelectedMap = selectedMap;
        this.showCheckBox = showCheckBox;
    }


    public CollBookHolder setListener(OnChickListener listener) {
        this.listener = listener;
        return this;
    }

    @Override
    public void initView() {
        mIvCover = findById(R.id.coll_book_iv_cover);
        mTvName = findById(R.id.coll_book_tv_name);
        mTvChapter = findById(R.id.coll_book_tv_chapter);
        mTvTime = findById(R.id.coll_book_tv_lately_update);
        mCbSelected = findById(R.id.coll_book_cb_selected);
        mIvRedDot = findById(R.id.coll_book_iv_red_rot);
        mIvTop = findById(R.id.coll_book_iv_top);
    }

    @Override
    public void onBind(CollBookBean value, int pos) {
        if (value.isLocal()){
            //本地文件的图片
            Glide.with(getContext())
                    .load(R.mipmap.ic_local_file)
                    .apply(new RequestOptions()
                            .fitCenter())
                    .into(mIvCover);
        }
        else {
            //书的图片
            Glide.with(getContext())
                    .load(Constant.IMG_BASE_URL+value.getCover())
                    .apply(new RequestOptions()
                            .placeholder(R.mipmap.ic_default_book_cover)
                            .error(R.mipmap.ic_load_error)
                            .fitCenter())
                    .into(mIvCover);
        }
        //书名
        mTvName.setText(value.getTitle());
        if (!value.isLocal()){
            //时间
            mTvTime.setText(StringUtils.
                    dateConvert(value.getUpdated(), Constant.FORMAT_BOOK_DATE)+":");
            mTvTime.setVisibility(View.VISIBLE);
        }
        else {
            mTvTime.setText("阅读进度:");
        }
        //章节
        mTvChapter.setText(value.getLastChapter());
        //选择
        //防止复用导致的checkbox显示错乱
        mCbSelected.setTag(pos);
        mCbSelected.setOnCheckedChangeListener((compoundButton, b) -> {

        });
        if (showCheckBox){
            mCbSelected.setVisibility(View.VISIBLE);
            mCbSelected.setSelected(mSelectedMap.get(value));
        }else {
            mCbSelected.setVisibility(View.GONE);
        }

        //当更新的时候，最新数据跟旧数据进行比较，如果更新的话，设置为true。
        if (value.isUpdate()){
            mIvRedDot.setVisibility(View.VISIBLE);
        }
        else {
            mIvRedDot.setVisibility(View.GONE);
        }
    }

    @Override
    protected int getItemLayoutId() {
        return R.layout.item_coll_book;
    }

    public interface OnChickListener{
        void onCheckedChanged(CollBookBean bean, boolean b);
    }
}
