package com.thmub.cocobook.model.bean.packages;

import com.thmub.cocobook.model.bean.BaseBean;
import com.thmub.cocobook.model.bean.PageNodeBookBean;

import java.util.List;

/**
 * Created by zhouas666 on 17-6-2.
 */
public class PageNodeBookPackage extends BaseBean{

    private List<PageNodeBookBean> data;

    public List<PageNodeBookBean> getData() {
        return data;
    }

    public void setData(List<PageNodeBookBean> data) {
        this.data = data;
    }


}
