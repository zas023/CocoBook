package com.thmub.cocobook.model.bean.packages;

import com.thmub.cocobook.model.bean.BaseBean;
import com.thmub.cocobook.model.bean.FeatureBookBean;

import java.util.List;

/**
 * Created by zhouas666 on 17-6-2.
 */
public class FeatureBookPackage extends BaseBean{

    private List<FeatureBookBean> data;

    public List<FeatureBookBean> getData() {
        return data;
    }

    public void setData(List<FeatureBookBean> data) {
        this.data = data;
    }


}
