package com.thmub.cocobook.model.local;

import com.thmub.cocobook.model.bean.AuthorBean;
import com.thmub.cocobook.model.bean.DownloadTaskBean;
import com.thmub.cocobook.model.bean.packages.BillboardPackage;
import com.thmub.cocobook.model.bean.ReviewBookBean;
import com.thmub.cocobook.model.bean.BookCommentBean;
import com.thmub.cocobook.model.bean.BookHelpfulBean;
import com.thmub.cocobook.model.bean.BookHelpsBean;
import com.thmub.cocobook.model.bean.BookReviewBean;
import com.thmub.cocobook.model.bean.packages.BookSortPackage;

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
