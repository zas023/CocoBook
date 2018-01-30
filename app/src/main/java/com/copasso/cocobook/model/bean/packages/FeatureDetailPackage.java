package com.copasso.cocobook.model.bean.packages;

import com.copasso.cocobook.model.bean.BaseBean;
import com.copasso.cocobook.model.bean.FeatureDetailBean;

import java.util.List;

public class FeatureDetailPackage extends BaseBean {


    private List<FeatureDetailBean> data;

    public List<FeatureDetailBean> getData() {
        return data;
    }

    public void setData(List<FeatureDetailBean> data) {
        this.data = data;
    }

}
