package com.thmub.cocobook.model.type;

import com.thmub.cocobook.App;
import com.thmub.cocobook.R;

import androidx.annotation.StringRes;

/**
 * Created by zhouas666 on 18-2-1.
 * 排行榜类型
 */

public enum BookRankType {
    MALE(R.string.tag_boy,"male"),
    FRMALE(R.string.tag_girl,"female");

    private String typeName;
    private String netName;

    BookRankType(@StringRes int typeName, String netName){
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
