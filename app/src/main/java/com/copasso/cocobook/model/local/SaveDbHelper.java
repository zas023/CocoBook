package com.copasso.cocobook.model.local;

import com.copasso.cocobook.model.bean.AuthorBean;
import com.copasso.cocobook.model.bean.DownloadTaskBean;
import com.copasso.cocobook.model.bean.packages.BillboardPackage;
import com.copasso.cocobook.model.bean.ReviewBookBean;
import com.copasso.cocobook.model.bean.BookCommentBean;
import com.copasso.cocobook.model.bean.BookHelpfulBean;
import com.copasso.cocobook.model.bean.BookHelpsBean;
import com.copasso.cocobook.model.bean.BookReviewBean;
import com.copasso.cocobook.model.bean.packages.BookSortPackage;

import java.util.List;

/**
 * Created by zhouas666 on 17-4-28.
 */

public interface SaveDbHelper {
    void saveBookComments(List<BookCommentBean> beans);
    void saveBookHelps(List<BookHelpsBean> beans);
    void saveBookReviews(List<BookReviewBean> beans);
    void saveAuthors(List<AuthorBean> beans);
    void saveBooks(List<ReviewBookBean> beans);
    void saveBookHelpfuls(List<BookHelpfulBean> beans);

    void saveBookSortPackage(BookSortPackage bean);
    void saveBillboardPackage(BillboardPackage bean);
    /*************DownloadTask*********************/
    void saveDownloadTask(DownloadTaskBean bean);
}
