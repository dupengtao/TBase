package com.dpt.tbase.app.adapter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import android.widget.BaseAdapter;

public abstract class AbCustomTBaseAdapter<T> extends BaseAdapter {

    private List<T> list;
    
    
    public AbCustomTBaseAdapter() {
        super();
        list = new ArrayList<T>();
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }
    
    
}
