package com.thmub.cocobook.model.type;

/**
 * 小说排序类型
 * ("默认排序","最新发布","最多评论"),
 */

public enum BookSortType {
    DEFAULT("默认排序","updated","Updated"),
    CREATED("最新发布","created","Created"),
    HELPFUL("最多推荐","helpful","LikeCount"),
    COMMENT_COUNT("最多评论","comment-count","CommentCount");

    String typeName;
    String netName;
    String dbName;
    BookSortType(String typeName, String netName, String dbName){
        this.typeName = typeName;
        this.netName = netName;
        this.dbName = dbName;
    }

    public String getTypeName(){
        return typeName;
    }

    public String getNetName(){
        return netName;
    }

    public String getDbName(){
        return dbName;
    }
}
