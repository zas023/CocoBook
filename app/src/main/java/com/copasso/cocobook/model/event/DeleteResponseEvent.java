package com.copasso.cocobook.model.event;

import com.copasso.cocobook.model.bean.CollBookBean;

/**
 * Created by zhouas666 on 18-2-27.
 */

public class DeleteResponseEvent {
    public boolean isDelete;
    public CollBookBean collBook;
    public DeleteResponseEvent(boolean isDelete,CollBookBean collBook){
        this.isDelete = isDelete;
        this.collBook = collBook;
    }
}
