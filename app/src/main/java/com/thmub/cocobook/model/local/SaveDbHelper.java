package com.thmub.cocobook.model.local;

import com.thmub.cocobook.model.bean.AuthorBean;
import com.thmub.cocobook.model.bean.DownloadTaskBean;
import com.thmub.cocobook.model.bean.packages.BookRankPackage;
import com.thmub.cocobook.model.bean.packages.BookSortPackage;

import java.util.List;

/**
 * Created by zhouas666 on 17-4-28.
 */

public interface SaveDbHelper {
    void saveAuthors(List<AuthorBean> beans);
    void saveBookSortPackage(BookSortPackage bean);
    void saveBillboardPackage(BookRankPackage bean);
    /*************DownloadTask*********************/
    void saveDownloadTask(DownloadTaskBean bean);
}
