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

import io.reactivex.Single;

/**
 * Created by zhouas666 on 17-4-28.
 */

public interface GetDbHelper {
    Single<List<BookCommentBean>> getBookComments(String block, String sort, int start, int limited, String distillate);
    Single<List<BookHelpsBean>> getBookHelps(String sort, int start, int limited, String distillate);
    Single<List<BookReviewBean>> getBookReviews(String sort, String bookType, int start, int limited, String distillate);
    BookSortPackage getBookSortPackage();
    BillboardPackage getBillboardPackage();

    AuthorBean getAuthor(String id);
    ReviewBookBean getReviewBook(String id);
    BookHelpfulBean getBookHelpful(String id);

    /******************************/
    List<DownloadTaskBean> getDownloadTaskList();
}
