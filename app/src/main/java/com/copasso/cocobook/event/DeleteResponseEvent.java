package com.copasso.cocobook.event;

import com.copasso.cocobook.model.bean.CollBookBean;

/**
 * Created by zhouas666 on 17-5-27.
 */

public class DeleteResponseEvent {
    public boolean isDelete;
    public CollBookBean collBook;
    public DeleteResponseEvent(boolean isDelete,CollBookBean collBook){
        this.isDelete = isDelete;
        this.collBook = collBook;
    }
}
