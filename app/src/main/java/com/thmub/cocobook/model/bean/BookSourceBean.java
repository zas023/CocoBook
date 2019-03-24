package com.thmub.cocobook.model.bean;

/**
 * Created by Zhouas666 on 2019-03-24
 * Github: https://github.com/zas023
 */
public class BookSourceBean {

    /**
     * _id : 5b2a0cb337b772524b7a45fe
     * source : zhuishuvip
     * name : 优质书源
     * link : http://vip.zhuishushenqi.com/toc/5b2a0cb337b772524b7a45fe
     * lastChapter : 尾声　黎明之战
     * isCharge : false
     * chaptersCount : 13
     * updated : 2018-06-20T08:13:39.346Z
     * starting : true
     * host : vip.zhuishushenqi.com
     */

    private String _id;
    private String source;
    private String name;
    private String link;
    private String lastChapter;
    private boolean isCharge;
    private int chaptersCount;
    private String updated;
    private boolean starting;
    private String host;

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getLastChapter() {
        return lastChapter;
    }

    public void setLastChapter(String lastChapter) {
        this.lastChapter = lastChapter;
    }

    public boolean isIsCharge() {
        return isCharge;
    }

    public void setIsCharge(boolean isCharge) {
        this.isCharge = isCharge;
    }

    public int getChaptersCount() {
        return chaptersCount;
    }

    public void setChaptersCount(int chaptersCount) {
        this.chaptersCount = chaptersCount;
    }

    public String getUpdated() {
        return updated;
    }

    public void setUpdated(String updated) {
        this.updated = updated;
    }

    public boolean isStarting() {
        return starting;
    }

    public void setStarting(boolean starting) {
        this.starting = starting;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }
}
