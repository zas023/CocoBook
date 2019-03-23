package com.thmub.cocobook.ui.adapter;

import com.thmub.cocobook.base.adapter.QuickAdapter;
import com.thmub.cocobook.model.bean.CollBookBean;
import com.thmub.cocobook.ui.adapter.holder.CollBookHolder;
import com.thmub.cocobook.base.adapter.IViewHolder;

import java.util.*;

/**
 * Created by zhouas666 on 18-2-8.
 * 书架书籍adapter
 */

public class CollBookAdapter extends QuickAdapter<CollBookBean> {
    private static final String TAG = "CollBookAdapter";

    //记录item是否被选中的Map
    public static HashMap<CollBookBean,Boolean> mCheckMap = new HashMap<>();
    private int mCheckedCount = 0;
    //判断是否显示CheckBox
    private boolean showCheckBox;

    public void setShowCheckBox(boolean showCheckBox) {
        this.showCheckBox = showCheckBox;
        //notifyDataSetChanged(); 仅可刷新全部可见的item
        notifyItemRangeChanged(0,mList.size());
    }

    @Override
    protected IViewHolder<CollBookBean> createViewHolder(int viewType) {
        return new CollBookHolder(mCheckMap,showCheckBox).setListener((bean, b) -> mCheckMap.put(bean,b));
    }

    @Override
    public void refreshItems(List<CollBookBean> list) {
        mCheckMap.clear();
        for(CollBookBean bean : list){
            mCheckMap.put(bean, false);
        }
        super.refreshItems(list);
    }

    @Override
    public void addItem(CollBookBean value) {
        mCheckMap.put(value, false);
        super.addItem(value);
    }

    @Override
    public void addItem(int index, CollBookBean value) {
        mCheckMap.put(value, false);
        super.addItem(index, value);
    }

    @Override
    public void addItems(List<CollBookBean> values) {
        for(CollBookBean value : values){
            mCheckMap.put(value, false);
        }
        super.addItems(values);
    }

    @Override
    public void removeItem(CollBookBean value) {
        mCheckMap.remove(value);
        super.removeItem(value);
    }

    @Override
    public void removeItems(List<CollBookBean> values) {
        //删除在HashMap中的文件
        for (CollBookBean value : values){
            mCheckMap.remove(value);
            //因为，能够被移除的文件，肯定是选中的
            --mCheckedCount;
        }
        //删除列表中的文件
        super.removeItems(values);
    }

    public int getCheckedCount(){
        return mCheckedCount;
    }
}

