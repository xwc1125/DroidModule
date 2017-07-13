//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.xwc1125.droidmodule.Picture.adapter;

import android.content.Context;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public final class CygViewHolder {
    private Context mContext;
    private SparseArray<View> mViews;
    private View mItemView;

    public static CygViewHolder get(Context context, int resource, View convertView, ViewGroup parent) {
        if (convertView == null) {
            View view = LayoutInflater.from(context).inflate(resource, parent, false);
            return new CygViewHolder(context, view);
        } else {
            return (CygViewHolder) convertView.getTag();
        }
    }

    private CygViewHolder(Context context, View itemView) {
        this.mContext = context;
        this.mViews = new SparseArray();
        this.mItemView = itemView;
        this.mItemView.setTag(this);
    }

    //    public <T extends View> T findViewById(int id) {
    public View findViewById(int id) {
        View view = (View) this.mViews.get(id);
        if (view == null) {
            view = this.mItemView.findViewById(id);
            this.mViews.put(id, view);
        }
        return view;
    }

    public CygViewHolder setText(int viewId, int textId) {
        return this.setText(viewId, this.mContext.getString(textId));
    }

    public CygViewHolder setText(int viewId, String text) {
        View view = this.findViewById(viewId);
        if (view instanceof TextView) {
            ((TextView) view).setText(text);
        }

        return this;
    }

    public View getItemView() {
        return this.mItemView;
    }
}
