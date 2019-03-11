package com.thmub.cocobook.model.bean.packages;

import com.thmub.cocobook.model.bean.BaseBean;
import com.thmub.cocobook.model.bean.PageNodeBean;

import java.util.List;

/**
 * Created by zhouas666 on 17-6-2.
 */
public class PageNodePackage extends BaseBean {

    private List<PageNodeBean> data;

    public List<PageNodeBean> getData() {
        return data;
    }

    public void setData(List<PageNodeBean> data) {
        this.data = data;
    }

}
