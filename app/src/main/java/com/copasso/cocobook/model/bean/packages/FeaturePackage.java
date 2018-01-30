package com.copasso.cocobook.model.bean.packages;

import com.copasso.cocobook.model.bean.BaseBean;
import com.copasso.cocobook.model.bean.FeatureBean;

import java.util.List;

public class FeaturePackage extends BaseBean {

    private List<FeatureBean> data;

    public List<FeatureBean> getData() {
        return data;
    }

    public void setData(List<FeatureBean> data) {
        this.data = data;
    }

}
