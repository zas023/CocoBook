package com.thmub.cocobook.model.bean.packages;

import com.thmub.cocobook.model.bean.BaseBean;
import com.thmub.cocobook.model.bean.BillBookBean;

import java.util.List;

/**
 * Created by zhouas666 on 18-2-8.
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
