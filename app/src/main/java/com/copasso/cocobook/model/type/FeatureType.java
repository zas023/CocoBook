package com.copasso.cocobook.model.type;

import android.support.annotation.StringRes;
import com.copasso.cocobook.App;
import com.copasso.cocobook.R;

public enum FeatureType {
    NSRM(R.string.nb_fragment_node_nsrm,"59128334694d1cda365b8985"),
    NSZA(R.string.nb_fragment_node_nsza,"5912825ba1dbf3ad33ee7ffe"),
    NSWB(R.string.nb_fragment_node_nswb,"591283eb8973b2fe3361463d"),
    NSWJ(R.string.nb_fragment_node_nswj,"591283afa1dbf3ad33ee7fff"),
    NSDSQ(R.string.nb_fragment_node_nsdsq,"591284376e2e237c36d7b8bd"),
    NSHWQ(R.string.nb_fragment_node_nshwq,"5912872f8973b2fe3361463f");

    private String typeName;
    private String typeId;

    private FeatureType(@StringRes int typeNameId, String  typeId){
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
