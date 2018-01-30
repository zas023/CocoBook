package com.copasso.cocobook.event;

import com.copasso.cocobook.model.flag.BookDistillate;
import com.copasso.cocobook.model.flag.BookSort;
import com.copasso.cocobook.model.flag.BookType;
import com.copasso.cocobook.utils.Constant;

/**
 * Created by zhouas666 on 17-4-21.
 */

public class SelectorEvent {

    public BookDistillate distillate;

    public BookType type;

    public BookSort sort;

    public SelectorEvent(BookDistillate distillate,
                         BookType type,
                         BookSort sort) {
        this.distillate = distillate;
        this.type = type;
        this.sort = sort;
    }
}
