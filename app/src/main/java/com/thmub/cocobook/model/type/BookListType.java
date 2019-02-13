package com.thmub.cocobook.model.type;

import androidx.annotation.StringRes;

import com.thmub.cocobook.App;
import com.thmub.cocobook.R;

/**
 * Created by zhouas666 on 18-2-1.
 * 书单排序类型
 */

public enum  BookListType{
    HOT(R.string.fragment_book_list_hot,"last-seven-days"),
    NEWEST(R.string.fragment_book_list_newest,"created"),
    COLLECT(R.string.fragment_book_list_collect,"collectorCount")
    ;
    private String typeName;
    private String netName;

    BookListType(@StringRes int typeName, String netName){
        this.typeName = App.getContext().getString(typeName);
        this.netName = netName;
    }

    public String getTypeName(){
        return typeName;
    }

    public String getNetName(){
        return netName;
    }
}
