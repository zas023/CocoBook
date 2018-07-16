package com.thmub.cocobook.model.bean;

public class FeatureDetailBean {
    /**
     * _id : 5a6a87013e7d02eb63822f21
     * __v : 0
     * order : 1084
     * node : {"_id":"5912825ba1dbf3ad33ee7ffe","title":"女生最爱"}
     * page : 5910018c8094b1e228e5868f
     * book : {"_id":"59bf8a60aeb7e61533606e27","title":"总裁大人，限量宠！","author":"江雁声","shortIntro":"白天，他是权势遮天的商界帝王，晚上，也是最霸道狂野的男人。一场联姻，江雁声嫁给他上位成为人人羡艳的霍太太，从此在整个宛城横着走，每天跟老公上演秀恩爱戏码。\u201c老公，有人欺负我\u2026\u2026\u201d\u201c乖，我弄死她！\u201d\u201c老公，有人挖你墙脚\u2026\u2026\u201d\u201c谁？我弄死他！\u201d\u2014\u2014某日，有人问：听说你妻奴？\u201c\u2026\u2026\u201d霍先生情深低喃：爱她，就要把她捧在在掌心,宠着,惯着，这叫霍氏宠妻宗旨！","longIntro":"白天，他是权势遮天的商界帝王，晚上，也是最霸道狂野的男人。一场联姻，江雁声嫁给他上位成为人人羡艳的霍太太，从此在整个宛城横着走，每天跟老公上演秀恩爱戏码。\u201c老公，有人欺负我\u2026\u2026\u201d\u201c乖，我弄死她！\u201d\u201c老公，有人挖你墙脚\u2026\u2026\u201d\u201c谁？我弄死他！\u201d\u2014\u2014某日，有人问：听说你妻奴？\u201c\u2026\u2026\u201d霍先生情深低喃：爱她，就要把她捧在在掌心,宠着,惯着，这叫霍氏宠妻宗旨！","cover":"http://statics.zhuishushenqi.com/agent/http%3A%2F%2Fimg.1391.com%2Fapi%2Fv1%2Fbookcenter%2Fcover%2F1%2F2148348%2F2148348_e17a30136c62409ea0b089e25e207c54.jpg%2F","majorCate":"现代言情","minorCate":"豪门总裁","sizetype":-1,"superscript":"","contentType":"txt","latelyFollower":17597,"wordCount":621084,"retentionRatio":56,"isSerial":true,"chaptersCount":286}
     * show : true
     */

    private String _id;
    private int __v;
    private int order;
    private NodeBean node;
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

    public NodeBean getNode() {
        return node;
    }

    public void setNode(NodeBean node) {
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

    public static class NodeBean {
        /**
         * _id : 5912825ba1dbf3ad33ee7ffe
         * title : 女生最爱
         */

        private String _id;
        private String title;

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
    }

    public static class BookBean {
        /**
         * _id : 59bf8a60aeb7e61533606e27
         * title : 总裁大人，限量宠！
         * author : 江雁声
         * shortIntro : 白天，他是权势遮天的商界帝王，晚上，也是最霸道狂野的男人。一场联姻，江雁声嫁给他上位成为人人羡艳的霍太太，从此在整个宛城横着走，每天跟老公上演秀恩爱戏码。“老公，有人欺负我……”“乖，我弄死她！”“老公，有人挖你墙脚……”“谁？我弄死他！”——某日，有人问：听说你妻奴？“……”霍先生情深低喃：爱她，就要把她捧在在掌心,宠着,惯着，这叫霍氏宠妻宗旨！
         * longIntro : 白天，他是权势遮天的商界帝王，晚上，也是最霸道狂野的男人。一场联姻，江雁声嫁给他上位成为人人羡艳的霍太太，从此在整个宛城横着走，每天跟老公上演秀恩爱戏码。“老公，有人欺负我……”“乖，我弄死她！”“老公，有人挖你墙脚……”“谁？我弄死他！”——某日，有人问：听说你妻奴？“……”霍先生情深低喃：爱她，就要把她捧在在掌心,宠着,惯着，这叫霍氏宠妻宗旨！
         * cover : http://statics.zhuishushenqi.com/agent/http%3A%2F%2Fimg.1391.com%2Fapi%2Fv1%2Fbookcenter%2Fcover%2F1%2F2148348%2F2148348_e17a30136c62409ea0b089e25e207c54.jpg%2F
         * majorCate : 现代言情
         * minorCate : 豪门总裁
         * sizetype : -1
         * superscript :
         * contentType : txt
         * latelyFollower : 17597
         * wordCount : 621084
         * retentionRatio : 56
         * isSerial : true
         * chaptersCount : 286
         */

        private String _id;
        private String title;
        private String author;
        private String shortIntro;
        private String longIntro;
        private String cover;
        private String majorCate;
        private String minorCate;
        private int sizetype;
        private String superscript;
        private String contentType;
        private int latelyFollower;
        private int wordCount;
        private String retentionRatio;
        private boolean isSerial;
        private int chaptersCount;

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

        public String getLongIntro() {
            return longIntro;
        }

        public void setLongIntro(String longIntro) {
            this.longIntro = longIntro;
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

        public int getLatelyFollower() {
            return latelyFollower;
        }

        public void setLatelyFollower(int latelyFollower) {
            this.latelyFollower = latelyFollower;
        }

        public int getWordCount() {
            return wordCount;
        }

        public void setWordCount(int wordCount) {
            this.wordCount = wordCount;
        }

        public String getRetentionRatio() {
            return retentionRatio;
        }

        public void setRetentionRatio(String retentionRatio) {
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
    }
}