package com.copasso.cocobook.model.bean.packages;

import com.copasso.cocobook.model.bean.BaseBean;
import com.copasso.cocobook.model.bean.BillBookBean;
import com.copasso.cocobook.model.bean.CollBookBean;

import java.util.List;

/**
 * Created by zhouas666 on 17-5-8.
 */

public class RecommendBookPackage2 extends BaseBean {

    private List<BillBookBean> books;

    public List<BillBookBean> getBooks() {
        return books;
    }

    public void setBooks(List<BillBookBean> books) {
        this.books = books;
    }
}
