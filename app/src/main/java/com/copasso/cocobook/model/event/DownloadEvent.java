package com.copasso.cocobook.model.event;

import com.copasso.cocobook.model.bean.CollBookBean;

/**
 * Created by zhouas666 on 18-2-27.
 * 下载任务event
 */

public class DownloadEvent {
    public CollBookBean collBook;

    public DownloadEvent(CollBookBean collBook){
        this.collBook = collBook;
    }
}
