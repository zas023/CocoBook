package com.thmub.cocobook.ui.adapter.view;

import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.thmub.cocobook.R;
import com.thmub.cocobook.model.bean.DownloadTaskBean;
import com.thmub.cocobook.base.adapter.ViewHolderImpl;
import com.thmub.cocobook.utils.FileUtils;
import com.thmub.cocobook.utils.StringUtils;

/**
 * Created by zhouas666 on 18-2-12.
 */

public class DownloadHolder extends ViewHolderImpl<DownloadTaskBean> {

    private TextView mTvTitle;
    private TextView mTvMsg;
    private TextView mTvTip;
    private ProgressBar mPbShow;
    private RelativeLayout mRlToggle;
    private ImageView mIvStatus;
    private TextView mTvStatus;

    @Override
    public void initView() {
        mTvTitle = findById(R.id.download_tv_title);
        mTvMsg = findById(R.id.download_tv_msg);
        mTvTip = findById(R.id.download_tv_tip);
        mPbShow = findById(R.id.download_pb_show);
        mRlToggle = findById(R.id.download_rl_toggle);
        mIvStatus = findById(R.id.download_iv_status);
        mTvStatus = findById(R.id.download_tv_status);
    }

    @Override
    public void onBind(DownloadTaskBean value, int pos) {

        if (!mTvTitle.getText().equals(value.getTaskName())){
            mTvTitle.setText(value.getTaskName());
        }

        switch (value.getStatus()){
            case DownloadTaskBean.STATUS_LOADING:
                changeBtnStyle(R.string.download_pause,
                        R.color.download_pause,R.mipmap.ic_download_pause);

                //进度状态
                setProgressMax(value);
                mPbShow.setProgress(value.getCurrentChapter());

                setTip(R.string.download_loading);

                mTvMsg.setText(StringUtils.getString(R.string.download_progress,
                        value.getCurrentChapter(),value.getBookChapters().size()));
                break;
            case DownloadTaskBean.STATUS_PAUSE:
                //按钮状态
                changeBtnStyle(R.string.download_start,
                        R.color.download_loading,R.mipmap.ic_download_loading);

                //进度状态
                setProgressMax(value);
                setTip(R.string.download_pausing);

                mPbShow.setProgress(value.getCurrentChapter());
                mTvMsg.setText(StringUtils.getString(R.string.download_progress,
                        value.getCurrentChapter(),value.getBookChapters().size()));
                break;
            case DownloadTaskBean.STATUS_WAIT:
                //按钮状态
                changeBtnStyle(R.string.download_wait,
                        R.color.download_wait,R.mipmap.ic_download_wait);

                //进度状态
                setProgressMax(value);
                setTip(R.string.download_waiting);

                mPbShow.setProgress(value.getCurrentChapter());
                mTvMsg.setText(StringUtils.getString(R.string.download_progress,
                        value.getCurrentChapter(),value.getBookChapters().size()));
                break;
            case DownloadTaskBean.STATUS_ERROR:
                //按钮状态
                changeBtnStyle(R.string.download_error,
                        R.color.download_error,R.mipmap.ic_download_error);
                setTip(R.string.download_source_error);
                mPbShow.setVisibility(View.INVISIBLE);
                mTvMsg.setVisibility(View.GONE);
                break;
            case DownloadTaskBean.STATUS_FINISH:
                //按钮状态
                changeBtnStyle(R.string.download_finish,
                        R.color.download_finish,R.mipmap.ic_download_complete);
                setTip(R.string.download_complete);
                mPbShow.setVisibility(View.INVISIBLE);

                //设置文件大小
                mTvMsg.setText(FileUtils.getFileSize(value.getSize()));
                break;
        }
    }

    private void changeBtnStyle(int strRes,int colorRes,int drawableRes){
        //按钮状态
        if (!mTvStatus.getText().equals(
                StringUtils.getString(strRes))){
            mTvStatus.setText(StringUtils.getString(strRes));
            mTvStatus.setTextColor(getContext().getResources().getColor(colorRes));
            mIvStatus.setImageResource(drawableRes);
        }
    }

    private void setProgressMax(DownloadTaskBean value){
        if (mPbShow.getMax() != value.getBookChapters().size()){
            mPbShow.setVisibility(View.VISIBLE);
            mPbShow.setMax(value.getBookChapters().size());
        }
    }

    //提示
    private void setTip(int strRes){
        if (!mTvTip.getText().equals(StringUtils.getString(strRes))){
            mTvTip.setText(StringUtils.getString(strRes));
        }
    }

    @Override
    protected int getItemLayoutId() {
        return R.layout.item_download;
    }
}
