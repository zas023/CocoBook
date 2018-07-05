package com.copasso.cocobook.model.type;

import android.support.annotation.DrawableRes;
import android.support.annotation.StringRes;

import com.copasso.cocobook.App;
import com.copasso.cocobook.R;

/**
 * 社区类型
 */

public enum CommunityType {

    COMMENT(R.string.fragment_community_comment, "ramble",R.drawable.ic_section_comment),
    REVIEW(R.string.fragment_community_discussion,"",R.drawable.ic_section_discuss),
    HELP(R.string.fragment_community_help,"",R.drawable.ic_section_help),
    GIRL(R.string.fragment_community_girl,"girl",R.drawable.ic_section_girl),
    COMPOSE(R.string.fragment_community_compose,"original",R.drawable.ic_section_compose);

    private String typeName;
    private String netName;
    private int iconId;
    CommunityType(@StringRes int typeId,String netName,@DrawableRes int iconId){
        this.typeName = App.getContext().getResources().getString(typeId);
        this.netName = netName;
        this.iconId = iconId;
    }

    public String getTypeName(){
        return typeName;
    }

    public String getNetName(){
        return netName;
    }

    public int getIconId(){
        return iconId;
    }
}
