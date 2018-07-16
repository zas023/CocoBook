package com.thmub.cocobook.model.bean.packages;

import com.thmub.cocobook.model.bean.BaseBean;
import com.thmub.cocobook.model.bean.FeatureBean;

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
