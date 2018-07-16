package com.thmub.cocobook.model.bean;

import java.util.List;

public class FeatureBookBean {
    /**
     * _id : 5a6a8753e5d557a76400033a
     * __v : 0
     * order : 1029
     * node : 591284376e2e237c36d7b8bd
     * page : 5910018c8094b1e228e5868f
     * book : {"_id":"53e0a6589dce9adb46d74084","title":"我的董事长老婆","author":"黑夜de白羊","shortIntro":"雇佣兵王秦川回归都市，被未婚妻安排到超级商场做保安，无意中卷入商场的斗争当中。总裁未婚妻，萝莉女销售，妖媚女秘书\u2026\u2026总之，是我的别动，不是我的也给我放下！且看兵王如何醉卧美人，执掌都市，创造传奇。","cover":"http://statics.zhuishushenqi.com/agent/http%3A%2F%2Fimg.1391.com%2Fapi%2Fv1%2Fbookcenter%2Fcover%2F1%2F685491%2F_685491_790763.jpg%2F","majorCate":"都市","minorCate":"都市生活","rating":{"count":60,"score":6.668},"recommendIntro":"","bigCover":"","rectangleCover":"","sizetype":-1,"superscript":"","contentType":"txt","allowMonthly":true,"latelyFollower":1969,"retentionRatio":41.09,"isSerial":false,"chaptersCount":1099,"tags":["都市","都市生活","美女如云","现代","热血"]}
     * show : true
     */

    private String _id;
    private int __v;
    private int order;
    private String node;
    private String page;
    private BookBean book;
    private boolean show;

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

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public String getNode() {
        return node;
    }

    public void setNode(String node) {
        this.node = node;
    }

    public String getPage() {
        return page;
    }

    public void setPage(String page) {
        this.page = page;
    }

    public BookBean getBook() {
        return book;
    }

    public void setBook(BookBean book) {
        this.book = book;
    }

    public boolean isShow() {
        return show;
    }

    public void setShow(boolean show) {
        this.show = show;
    }

    public static class BookBean {
        /**
         * _id : 53e0a6589dce9adb46d74084
         * title : 我的董事长老婆
         * author : 黑夜de白羊
         * shortIntro : 雇佣兵王秦川回归都市，被未婚妻安排到超级商场做保安，无意中卷入商场的斗争当中。总裁未婚妻，萝莉女销售，妖媚女秘书……总之，是我的别动，不是我的也给我放下！且看兵王如何醉卧美人，执掌都市，创造传奇。
         * cover : http://statics.zhuishushenqi.com/agent/http%3A%2F%2Fimg.1391.com%2Fapi%2Fv1%2Fbookcenter%2Fcover%2F1%2F685491%2F_685491_790763.jpg%2F
         * majorCate : 都市
         * minorCate : 都市生活
         * rating : {"count":60,"score":6.668}
         * recommendIntro :
         * bigCover :
         * rectangleCover :
         * sizetype : -1
         * superscript :
         * contentType : txt
         * allowMonthly : true
         * latelyFollower : 1969
         * retentionRatio : 41.09
         * isSerial : false
         * chaptersCount : 1099
         * tags : ["都市","都市生活","美女如云","现代","热血"]
         */

        private String _id;
        private String title;
        private String author;
        private String shortIntro;
        private String cover;
        private String majorCate;
        private String minorCate;
        private RatingBean rating;
        private String recommendIntro;
        private String bigCover;
        private String rectangleCover;
        private int sizetype;
        private String superscript;
        private String contentType;
        private boolean allowMonthly;
        private int latelyFollower;
        private double retentionRatio;
        private boolean isSerial;
        private int chaptersCount;
        private List<String> tags;

        public String get_id() {
            return _id;
        }

        public void set_id(String _id) {
            this._id = _id;
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

        public String getMajorCate() {
            return majorCate;
        }

        public void setMajorCate(String majorCate) {
            this.majorCate = majorCate;
        }

        public String getMinorCate() {
            return minorCate;
        }

        public void setMinorCate(String minorCate) {
            this.minorCate = minorCate;
        }

        public RatingBean getRating() {
            return rating;
        }

        public void setRating(RatingBean rating) {
            this.rating = rating;
        }

        public String getRecommendIntro() {
            return recommendIntro;
        }

        public void setRecommendIntro(String recommendIntro) {
            this.recommendIntro = recommendIntro;
        }

        public String getBigCover() {
            return bigCover;
        }

        public void setBigCover(String bigCover) {
            this.bigCover = bigCover;
        }

        public String getRectangleCover() {
            return rectangleCover;
        }

        public void setRectangleCover(String rectangleCover) {
            this.rectangleCover = rectangleCover;
        }

        public int getSizetype() {
            return sizetype;
        }

        public void setSizetype(int sizetype) {
            this.sizetype = sizetype;
        }

        public String getSuperscript() {
            return superscript;
        }

        public void setSuperscript(String superscript) {
            this.superscript = superscript;
        }

        public String getContentType() {
            return contentType;
        }

        public void setContentType(String contentType) {
            this.contentType = contentType;
        }

        public boolean isAllowMonthly() {
            return allowMonthly;
        }

        public void setAllowMonthly(boolean allowMonthly) {
            this.allowMonthly = allowMonthly;
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

        public boolean isIsSerial() {
            return isSerial;
        }

        public void setIsSerial(boolean isSerial) {
            this.isSerial = isSerial;
        }

        public int getChaptersCount() {
            return chaptersCount;
        }

        public void setChaptersCount(int chaptersCount) {
            this.chaptersCount = chaptersCount;
        }

        public List<String> getTags() {
            return tags;
        }

        public void setTags(List<String> tags) {
            this.tags = tags;
        }

        public static class RatingBean {
            /**
             * count : 60
             * score : 6.668
             */

            private int count;
            private double score;

            public int getCount() {
                return count;
            }

            public void setCount(int count) {
                this.count = count;
            }

            public double getScore() {
                return score;
            }

            public void setScore(double score) {
                this.score = score;
            }
        }
    }
}
