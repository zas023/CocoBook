package com.thmub.cocobook.model.type;

import androidx.annotation.StringRes;
import com.thmub.cocobook.App;
import com.thmub.cocobook.R;

/**
 * 商城推荐类型
 */
public enum FeatureType {
    NSRM(R.string.fragment_node_nsrm,"59128334694d1cda365b8985"),
    NSZA(R.string.fragment_node_nsza,"5912825ba1dbf3ad33ee7ffe"),
    NSWB(R.string.fragment_node_nswb,"591283eb8973b2fe3361463d"),
    NSWJ(R.string.fragment_node_nswj,"591283afa1dbf3ad33ee7fff"),
    NSDSQ(R.string.fragment_node_nsdsq,"591284376e2e237c36d7b8bd"),
    NSHWQ(R.string.fragment_node_nshwq,"5912872f8973b2fe3361463f"),
    NPXM(R.string.fragment_node_npxm,"5a9cfc6bf43ec14c2714a0a2"),
    NVPXM(R.string.fragment_node_nvpxm,"5a9cfc7df43ec14c2714a0a3");

    private String typeName;
    private String typeId;

    FeatureType(@StringRes int typeNameId, String  typeId){
        this.typeName = App.getContext().getResources().getString(typeNameId);
        this.typeId = typeId;
    }

    public String getTypeName(){
        return typeName;
    }

    public String getTypeId(){
        return typeId;
    }
}
