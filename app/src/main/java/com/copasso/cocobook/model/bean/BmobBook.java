package com.copasso.cocobook.model.bean;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.BmobUser;


/**
 * Created by zhouas666 on 18-2-8.
 * 收藏的书籍
 */
public class BmobBook extends BmobObject{

    /**
     * _id : 53663ae356bdc93e49004474
     * title : 逍遥派
     * author : 白马出淤泥
     * shortIntro : 金庸武侠中有不少的神秘高手，书中或提起名字，或不曾提起，总之他们要么留下了绝世秘笈，要么就名震武林。 独孤九剑的创始者，独孤求败，他真的只创出九剑吗？ 残本葵花...
     * cover : /cover/149273897447137
     * hasCp : true
     * latelyFollower : 60213
     * retentionRatio : 22.87
     * updated : 2017-05-07T18:24:34.720Z
     *
     * chaptersCount : 1660
     * lastChapter : 第1659章 朱长老
     */

    private BmobUser user;//用户

    private String bookId;   // 本地书籍中，path 的 md5 值作为本地书籍的 id
    private String title;
    private String author;
    private String shortIntro;
    private String cover; // 在本地书籍中，该字段作为本地文件的路径
    private boolean hasCp;
    private int latelyFollower;
    private double retentionRatio;
    //最新更新日期
    private String updated;
    //最新阅读日期
    private String lastRead;
    private int chaptersCount;
    private String lastChapter;

    //是否是本地文件,打开的本地文件，而非指缓存文件
    private boolean isLocal = false;

    public BmobBook(CollBookBean collBookBean){
        super();
        this.bookId=collBookBean.get_id();
        this.title=collBookBean.getTitle();
        this.author=collBookBean.getAuthor();
        this.cover=collBookBean.getCover();
        this.shortIntro=collBookBean.getShortIntro();
        this.hasCp=collBookBean.getHasCp();
        this.latelyFollower=collBookBean.getLatelyFollower();
        this.retentionRatio=collBookBean.getRetentionRatio();
        this.updated=collBookBean.getUpdated();
        this.lastRead=collBookBean.getLastRead();
        this.chaptersCount=collBookBean.getChaptersCount();
        this.lastChapter=collBookBean.getLastChapter();
        this.isLocal=collBookBean.isLocal();
    }

    public BmobBook(CollBookBean collBookBean , BmobUser user){
        super();
        this.user=user;
        this.bookId=collBookBean.get_id();
        this.title=collBookBean.getTitle();
        this.author=collBookBean.getAuthor();
        this.cover=collBookBean.getCover();
        this.shortIntro=collBookBean.getShortIntro();
        this.hasCp=collBookBean.getHasCp();
        this.latelyFollower=collBookBean.getLatelyFollower();
        this.retentionRatio=collBookBean.getRetentionRatio();
        this.updated=collBookBean.getUpdated();
        this.lastRead=collBookBean.getLastRead();
        this.chaptersCount=collBookBean.getChaptersCount();
        this.lastChapter=collBookBean.getLastChapter();
        this.isLocal=collBookBean.isLocal();
    }

    public void update(CollBookBean collBookBean){
        this.bookId=collBookBean.get_id();
        this.title=collBookBean.getTitle();
        this.author=collBookBean.getAuthor();
        this.cover=collBookBean.getCover();
        this.shortIntro=collBookBean.getShortIntro();
        this.hasCp=collBookBean.getHasCp();
        this.latelyFollower=collBookBean.getLatelyFollower();
        this.retentionRatio=collBookBean.getRetentionRatio();
        this.updated=collBookBean.getUpdated();
        this.lastRead=collBookBean.getLastRead();
        this.chaptersCount=collBookBean.getChaptersCount();
        this.lastChapter=collBookBean.getLastChapter();
        this.isLocal=collBookBean.isLocal();
    }


    public BmobUser getUser() {
        return user;
    }

    public void setUser(BmobUser user) {
        this.user = user;
    }

    public String getBookId() {
        return bookId;
    }

    public void setBookId(String bookId) {
        this.bookId = bookId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getShortIntro() {
        return shortIntro;
    }

    public void setShortIntro(String shortIntro) {
        this.shortIntro = shortIntro;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public boolean isHasCp() {
        return hasCp;
    }

    public void setHasCp(boolean hasCp) {
        this.hasCp = hasCp;
    }

    public int getLatelyFollower() {
        return latelyFollower;
    }

    public void setLatelyFollower(int latelyFollower) {
        this.latelyFollower = latelyFollower;
    }

    public double getRetentionRatio() {
        return retentionRatio;
    }

    public void setRetentionRatio(double retentionRatio) {
        this.retentionRatio = retentionRatio;
    }

    public String getUpdated() {
        return updated;
    }

    public void setUpdated(String updated) {
        this.updated = updated;
    }

    public String getLastRead() {
        return lastRead;
    }

    public void setLastRead(String lastRead) {
        this.lastRead = lastRead;
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

    public boolean isLocal() {
        return isLocal;
    }

    public void setLocal(boolean local) {
        isLocal = local;
    }
}