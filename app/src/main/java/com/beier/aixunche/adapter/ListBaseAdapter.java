package com.beier.aixunche.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;

/**
 * Created by jacob on 15/6/27.
 */
public class ListBaseAdapter<T> extends BaseAdapter {
    protected List<T> arrays;
    protected Context mContext;

    public ListBaseAdapter(Context context, List<T> arrays) {
        this.arrays = arrays;
        this.mContext = context;
    }

    @Override
    public int getCount() {
        if(arrays == null)
        {
            return 0;
        }
        return arrays.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return getRealView(position,convertView,parent);
    }

    protected View getRealView(int position, View convertView, ViewGroup parent)
    {
        return convertView;
    }
}
