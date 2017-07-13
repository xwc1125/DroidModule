//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.xwc1125.droidmodule.Picture.adapter;

import android.content.Context;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;

import java.util.ArrayList;
import java.util.List;

public abstract class CygCheckedAdapter<T> extends CygAdapter<T> {
    private int mCheckViewId;
    protected SparseArray<T> mSparseObjects;

    public void onCompoundButtonCheckedChanged(CompoundButton buttonView, boolean isChecked, int position) {
    }

    public CygCheckedAdapter(Context context, int resource, int checkViewId, List objects) {
        super(context, resource, objects);
        this.mCheckViewId = checkViewId;
        this.mSparseObjects = new SparseArray();
    }

    public final View getView(int position, View convertView, ViewGroup parent) {
        View view = super.getView(position, convertView, parent);
        CygViewHolder viewHolder = (CygViewHolder)view.getTag();
        View checkView = viewHolder.findViewById(this.mCheckViewId);
        if(checkView instanceof CompoundButton) {
            CompoundButton compoundButton = (CompoundButton)checkView;
            this.setOnCheckedChangeListener(compoundButton, position);
            if(this.getCheckObject(position) != null) {
                compoundButton.setChecked(true);
            } else {
                compoundButton.setChecked(false);
            }
        }

        return view;
    }

    private void setOnCheckedChangeListener(CompoundButton compoundButton, final int position) {
        if(compoundButton.isClickable() && compoundButton.isEnabled()) {
            compoundButton.setOnCheckedChangeListener(new OnCheckedChangeListener() {
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    CygCheckedAdapter.this.toggleCheckObject(isChecked, position);
                    CygCheckedAdapter.this.onCompoundButtonCheckedChanged(buttonView, isChecked, position);
                }
            });
        }

    }

    public final void toggleCheckObject(View view, int position) {
        CygViewHolder viewHolder = (CygViewHolder)view.getTag();
        CompoundButton compoundButton = (CompoundButton)viewHolder.findViewById(this.mCheckViewId);
        compoundButton.toggle();
        this.toggleCheckObject(compoundButton.isChecked(), position);
    }

    public final void toggleCheckObject(boolean isChecked, int position) {
        if(isChecked) {
            this.putCheckObject(position);
        } else {
            this.removeCheckObject(position);
        }

    }

    public final void removeCheckObjects() {
        int size = this.getCheckSize();

        for(int i = 0; i < size; ++i) {
            this.remove(this.mSparseObjects.valueAt(i));
        }

        this.clearCheckObjects();
    }

    public final int getCheckSize() {
        return this.mSparseObjects.size();
    }

    public final List<T> getCheckObjects() {
        ArrayList list = new ArrayList();

        for(int i = 0; i < this.mSparseObjects.size(); ++i) {
            list.add(this.mSparseObjects.valueAt(i));
        }

        return list;
    }

    private final void clearCheckObjects() {
        this.mSparseObjects.clear();
    }

    private final void putCheckObject(int position) {
        this.mSparseObjects.put(position, this.getItem(position));
    }

    private final void removeCheckObject(int position) {
        this.mSparseObjects.remove(position);
    }

    private final T getCheckObject(int position) {
        return this.mSparseObjects.get(position);
    }
}
