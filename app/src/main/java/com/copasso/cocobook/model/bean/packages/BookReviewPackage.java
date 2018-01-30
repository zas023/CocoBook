package com.copasso.cocobook.model.bean.packages;

import com.copasso.cocobook.model.bean.BaseBean;
import com.copasso.cocobook.model.bean.BookReviewBean;

import java.util.List;

/**
 * Created by zhouas666 on 17-4-21.
 */

public class BookReviewPackage extends BaseBean {

    private List<BookReviewBean> reviews;

    public List<BookReviewBean> getReviews() {
        return reviews;
    }

    public void setReviews(List<BookReviewBean> reviews) {
        this.reviews = reviews;
    }
}
