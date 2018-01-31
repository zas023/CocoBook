package com.copasso.cocobook.model.bean;

import android.support.annotation.DrawableRes;
import android.support.annotation.StringRes;
import com.copasso.cocobook.App;

/**
 * Created by zhouas666 on 18-1-23.
 */

public class SquareBean {
    private String name;
    private int drawableId;

    public SquareBean(String name, @DrawableRes int drawableId){
        this.name = name;
        this.drawableId = drawableId;
    }

    public SquareBean(@StringRes int strRes, @DrawableRes int drawableId){
        this.name = App.getContext()
                .getString(strRes);

        this.drawableId = drawableId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getDrawableId() {
        return drawableId;
    }

    public void setDrawableId(int drawableId) {
        this.drawableId = drawableId;
    }
}
