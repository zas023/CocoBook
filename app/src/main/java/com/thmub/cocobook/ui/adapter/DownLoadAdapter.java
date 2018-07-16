package com.thmub.cocobook.ui.adapter;

import com.thmub.cocobook.model.bean.DownloadTaskBean;
import com.thmub.cocobook.ui.adapter.view.DownloadHolder;
import com.thmub.cocobook.base.adapter.BaseListAdapter;
import com.thmub.cocobook.base.adapter.IViewHolder;

/**
 * Created by zhouas666 on 18-2-12.
 */

public class DownLoadAdapter extends BaseListAdapter<DownloadTaskBean>{

    @Override
    protected IViewHolder<DownloadTaskBean> createViewHolder(int viewType) {
        return new DownloadHolder();
    }
}
