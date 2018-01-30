package com.copasso.cocobook.model.local;

import com.copasso.cocobook.model.bean.AuthorBean;
import com.copasso.cocobook.model.bean.ReviewBookBean;
import com.copasso.cocobook.model.bean.BookCommentBean;
import com.copasso.cocobook.model.bean.BookHelpfulBean;
import com.copasso.cocobook.model.bean.BookHelpsBean;
import com.copasso.cocobook.model.bean.BookReviewBean;

import java.util.List;

/**
 * Created by zhouas666 on 17-4-28.
 */

public interface DeleteDbHelper {
    void deleteBookComments(List<BookCommentBean> beans);
    void deleteBookReviews(List<BookReviewBean> beans);
    void deleteBookHelps(List<BookHelpsBean> beans);
    void deleteAuthors(List<AuthorBean> beans);
    void deleteBooks(List<ReviewBookBean> beans);
    void deleteBookHelpful(List<BookHelpfulBean> beans);
    void deleteAll();
}
