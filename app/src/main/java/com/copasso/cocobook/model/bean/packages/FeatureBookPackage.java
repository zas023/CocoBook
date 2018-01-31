package com.copasso.cocobook.model.bean.packages;

import com.copasso.cocobook.model.bean.BaseBean;
import com.copasso.cocobook.model.bean.FeatureBookBean;

import java.util.List;

public class FeatureBookPackage extends BaseBean{

    private List<FeatureBookBean> data;

    public List<FeatureBookBean> getData() {
        return data;
    }

    public void setData(List<FeatureBookBean> data) {
        this.data = data;
    }


}
