package com.thmub.cocobook.model.bean.packages;

import com.thmub.cocobook.model.bean.BaseBean;
import com.thmub.cocobook.model.bean.SwipePictureBean;

import java.util.List;
/**
 * Created by zhouas666 on 17-6-2.
 */
public class SwipePicturePackage extends BaseBean{

    private List<SwipePictureBean> data;

    public List<SwipePictureBean> getData() {
        return data;
    }

    public void setData(List<SwipePictureBean> data) {
        this.data = data;
    }

}
