package com.copasso.cocobook.model.bean;

public class FeatureBean {
    /**
     * _id : 5912825ba1dbf3ad33ee7ffe
     * __v : 0
     * created : 2017-05-10T03:00:43.333Z
     * order : 1
     * advType :
     * bookType : 全文推
     * type : 0
     * alias : mvsza
     * title : 女生最爱
     * page : 5910018c8094b1e228e5868f
     * sellType : 无
     * icon :
     * sex : none
     * secondTitle :
     */

    private String _id;
    private int __v;
    private String created;
    private int order;
    private String advType;
    private String bookType;
    private int type;
    private String alias;
    private String title;
    private String page;
    private String sellType;
    private String icon;
    private String sex;
    private String secondTitle;

    public FeatureBean(String _id, String title) {
        this._id = _id;
        this.title = title;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public int get__v() {
        return __v;
    }

    public void set__v(int __v) {
        this.__v = __v;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public String getAdvType() {
        return advType;
    }

    public void setAdvType(String advType) {
        this.advType = advType;
    }

    public String getBookType() {
        return bookType;
    }

    public void setBookType(String bookType) {
        this.bookType = bookType;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPage() {
        return page;
    }

    public void setPage(String page) {
        this.page = page;
    }

    public String getSellType() {
        return sellType;
    }

    public void setSellType(String sellType) {
        this.sellType = sellType;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getSecondTitle() {
        return secondTitle;
    }

    public void setSecondTitle(String secondTitle) {
        this.secondTitle = secondTitle;
    }
}
