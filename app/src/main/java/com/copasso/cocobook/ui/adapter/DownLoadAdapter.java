package com.copasso.cocobook.ui.adapter;

import com.copasso.cocobook.model.bean.DownloadTaskBean;
import com.copasso.cocobook.ui.adapter.view.DownloadHolder;
import com.copasso.cocobook.ui.base.adapter.BaseListAdapter;
import com.copasso.cocobook.ui.base.adapter.IViewHolder;

/**
 * Created by zhouas666 on 18-2-12.
 */

public class DownLoadAdapter extends BaseListAdapter<DownloadTaskBean>{

    @Override
    protected IViewHolder<DownloadTaskBean> createViewHolder(int viewType) {
        return new DownloadHolder();
    }
}
