//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.xwc1125.droidmodule.Picture.adapter;

import android.content.Context;
import android.util.SparseIntArray;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

public abstract class CygMultiViewTypeAdapter<T> extends CygAdapter<T> {
    protected SparseIntArray mItemViewTypes;

    public abstract int getItemViewType(T var1, int var2);

    public abstract void onBindData(CygViewHolder var1, T var2, int var3, int var4);

    public CygMultiViewTypeAdapter(Context context, List objects, SparseIntArray itemViewTypes) {
        super(context, 0, objects);
        this.mItemViewTypes = itemViewTypes;
    }

    public void addItemViewType(int viewType, int layoutId) {
        if(this.mItemViewTypes == null) {
            this.mItemViewTypes = new SparseIntArray();
        }

        this.mItemViewTypes.put(viewType, layoutId);
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        int viewType = this.getItemViewType(position);
        int layoutId = this.mItemViewTypes.get(viewType);
        CygViewHolder viewHolder = CygViewHolder.get(this.mContext, layoutId, convertView, parent);
        this.onBindData(viewHolder, this.getItem(position), position, viewType);
        return viewHolder.getItemView();
    }

    public int getViewTypeCount() {
        return this.mItemViewTypes.size();
    }

    public int getItemViewType(int position) {
        return this.getItemViewType(this.getItem(position), position);
    }

    public final void onBindData(CygViewHolder viewHolder, T item, int position) {
    }
}
