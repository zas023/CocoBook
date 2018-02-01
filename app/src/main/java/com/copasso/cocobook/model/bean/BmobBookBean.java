package com.copasso.cocobook.model.bean;

import cn.bmob.v3.BmobObject;


/**
 * Created by zhouas666 on 18-2-8.
 * 收藏的书籍
 */
public class BmobBookBean extends BmobObject{

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

    private String _id; // 本地书籍中，path 的 md5 值作为本地书籍的 id
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

    public BmobBookBean(CollBookBean collBookBean){
        this._id=collBookBean.get_id();
        this.title=collBookBean.getTitle();
        this.author=collBookBean.getAuthor();
        this.cover=collBookBean.getCover();
        this.shortIntro=collBookBean.getShortIntro();
        this.isLocal=collBookBean.isLocal();
    }
}