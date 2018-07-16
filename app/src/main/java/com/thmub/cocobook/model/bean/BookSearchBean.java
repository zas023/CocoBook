package com.thmub.cocobook.model.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;

/**
 * Created by zhouas666 on 18-1-23.
 */
@Entity
public class BookSearchBean {
    //搜索的关键字,不唯一
    @Id
    private String keyword;


    @Generated(hash = 981561409)
    public BookSearchBean(String keyword) {
        this.keyword = keyword;
    }

    @Generated(hash = 1450667676)
    public BookSearchBean() {
    }


    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    @Override
    public String toString() {
        return "BookSearchBean{" +
                "keyword='" + keyword + '\'' +
                '}';
    }
}
