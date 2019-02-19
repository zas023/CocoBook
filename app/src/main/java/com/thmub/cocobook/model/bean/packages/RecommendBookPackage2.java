package com.thmub.cocobook.model.bean.packages;

import com.thmub.cocobook.model.bean.BaseBean;
import com.thmub.cocobook.model.bean.RankBookBean;

import java.util.List;

/**
 * Created by zhouas666 on 18-2-8.
 */

public class RecommendBookPackage2 extends BaseBean {

    private List<RankBookBean> books;

    public List<RankBookBean> getBooks() {
        return books;
    }

    public void setBooks(List<RankBookBean> books) {
        this.books = books;
    }
}
