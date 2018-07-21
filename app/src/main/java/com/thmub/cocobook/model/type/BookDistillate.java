package com.thmub.cocobook.model.type;

/**
 * Created by zhouas666 on 18-1-23.
 *
 */

public enum BookDistillate{
    ALL("全部","","normal"),
    BOUTIQUES("精品","true","distillate");

    String typeName;
    String netName;
    String dbName;
    BookDistillate(String typeName, String netName, String dbName){
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
