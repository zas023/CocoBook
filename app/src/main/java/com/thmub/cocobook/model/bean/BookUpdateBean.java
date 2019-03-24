package com.thmub.cocobook.model.bean;

/**
 * Created by Zhouas666 on 2019-03-24
 * Github: https://github.com/zas023
 */
public class BookUpdateBean {

    /**
     * _id : 531169b3173bfacb4904ca67
     * author : 耳根
     * isFineBook : false
     * allowMonthly : false
     * referenceSource : sogou
     * updated : 2017-11-24T01:42:04.150Z
     * chaptersCount : 1609
     * lastChapter : 第十卷 我看沧海化桑田 第1614章 孤帆一片日边来！（终）
     */

    private String _id;
    private String author;
    private boolean isFineBook;
    private boolean allowMonthly;
    private String referenceSource;
    private String updated;
    private int chaptersCount;
    private String lastChapter;

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public boolean isIsFineBook() {
        return isFineBook;
    }

    public void setIsFineBook(boolean isFineBook) {
        this.isFineBook = isFineBook;
    }

    public boolean isAllowMonthly() {
        return allowMonthly;
    }

    public void setAllowMonthly(boolean allowMonthly) {
        this.allowMonthly = allowMonthly;
    }

    public String getReferenceSource() {
        return referenceSource;
    }

    public void setReferenceSource(String referenceSource) {
        this.referenceSource = referenceSource;
    }

    public String getUpdated() {
        return updated;
    }

    public void setUpdated(String updated) {
        this.updated = updated;
    }

    public int getChaptersCount() {
        return chaptersCount;
    }

    public void setChaptersCount(int chaptersCount) {
        this.chaptersCount = chaptersCount;
    }

    public String getLastChapter() {
        return lastChapter;
    }

    public void setLastChapter(String lastChapter) {
        this.lastChapter = lastChapter;
    }
}
