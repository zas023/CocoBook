package com.thmub.cocobook.model.bean;

import java.util.List;

/**
 * Created by zhouas666 on 18-1-23.
 */
public class BookSortBean {
    /**
     * name : 玄幻
     * bookCount : 578022
     * monthlyCount : 21592
     * icon : /icon/玄幻_.png
     * bookCover : ["/agent/http%3A%2F%2Fimg.1391.com%2Fapi%2Fv1%2Fbookcenter%2Fcover%2F1%2F1228859%2F1228859_d14f18e849b34420904ead54936e440a.jpg%2F","/agent/http%3A%2F%2Fimg.1391.com%2Fapi%2Fv1%2Fbookcenter%2Fcover%2F1%2F2107590%2F2107590_2607eaf03b2542ba88e4b75ac96bb12c.jpg%2F","/agent/http://img.1391.com/api/v1/bookcenter/cover/1/41584/41584_873f1d8b2f7a447a8c59f3573094db1b.jpg/"]
     */

    private String name;
    private int bookCount;
    private int monthlyCount;
    private String icon;
    private List<String> bookCover;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getBookCount() {
        return bookCount;
    }

    public void setBookCount(int bookCount) {
        this.bookCount = bookCount;
    }

    public int getMonthlyCount() {
        return monthlyCount;
    }

    public void setMonthlyCount(int monthlyCount) {
        this.monthlyCount = monthlyCount;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public List<String> getBookCover() {
        return bookCover;
    }

    public void setBookCover(List<String> bookCover) {
        this.bookCover = bookCover;
    }

}