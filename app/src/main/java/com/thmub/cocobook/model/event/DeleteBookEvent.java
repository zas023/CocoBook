package com.thmub.cocobook.model.event;

import com.thmub.cocobook.model.bean.CollBookBean;

/**
 * Created by zhouas666 on 18-2-27.
 * 移出书架event
 */

public class DeleteBookEvent {
    public boolean isDelete;
    public CollBookBean collBook;
    public DeleteBookEvent(boolean isDelete, CollBookBean collBook){
        this.isDelete = isDelete;
        this.collBook = collBook;
    }
}
