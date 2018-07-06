package com.copasso.cocobook.model.event;

/**
 * Created by zhouas666 on 18-2-5.
 * 切换子分类event
 */

public class BookSubSortEvent {
    public String bookSubSort;

    public BookSubSortEvent(String bookSubSort){
        this.bookSubSort = bookSubSort;
    }
}
