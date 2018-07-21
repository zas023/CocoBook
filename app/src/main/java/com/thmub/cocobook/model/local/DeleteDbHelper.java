package com.thmub.cocobook.model.local;

import com.thmub.cocobook.model.bean.AuthorBean;

import java.util.List;

/**
 * Created by zhouas666 on 17-4-28.
 */

public interface DeleteDbHelper {
    void deleteAuthors(List<AuthorBean> beans);
    void deleteAll();
}
