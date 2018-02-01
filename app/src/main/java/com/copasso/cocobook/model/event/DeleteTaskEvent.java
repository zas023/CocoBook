package com.copasso.cocobook.model.event;

import com.copasso.cocobook.model.bean.CollBookBean;

/**
 * Created by zhouas666 on 18-2-27.
 */

public class DeleteTaskEvent {
    public CollBookBean collBook;

    public DeleteTaskEvent(CollBookBean collBook){
        this.collBook = collBook;
    }
}
