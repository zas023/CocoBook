package com.thmub.cocobook.model.bean.packages;

import com.thmub.cocobook.model.bean.BaseBean;
import com.thmub.cocobook.model.bean.PageNodeDetailBean;

import java.util.List;

/**
 * Created by zhouas666 on 17-6-2.
 */
public class PageNodeDetailPackage extends BaseBean {


    private List<PageNodeDetailBean> data;

    public List<PageNodeDetailBean> getData() {
        return data;
    }

    public void setData(List<PageNodeDetailBean> data) {
        this.data = data;
    }

}
