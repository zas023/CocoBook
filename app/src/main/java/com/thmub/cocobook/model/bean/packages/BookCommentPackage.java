package com.thmub.cocobook.model.bean.packages;

import com.thmub.cocobook.model.bean.BaseBean;
import com.thmub.cocobook.model.bean.BookCommentBean;

import java.util.List;

/**
 * Created by zhouas666 on 17-4-20.
 */
public class BookCommentPackage extends BaseBean {

    private List<BookCommentBean> posts;

    public List<BookCommentBean> getPosts() {
        return posts;
    }

    public void setPosts(List<BookCommentBean> posts) {
        this.posts = posts;
    }
}
