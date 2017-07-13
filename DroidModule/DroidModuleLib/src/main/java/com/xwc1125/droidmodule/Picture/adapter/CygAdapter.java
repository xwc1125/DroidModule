package com.xwc1125.droidmodule.Picture.adapter;

/**
 * Class: com.ugchain.wallet.picture.adapter <br>
 * Description: TODO <br>
 *
 * @author xwc1125 <br>
 * @version V1.0
 * @Copyright: Copyright (c) 2017 <br>
 * @date 2017/6/2  10:50 <br>
 */
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public abstract class CygAdapter<T> extends BaseAdapter implements Filterable {
    private final Object mLock = new Object();
    private ObjectFilter mFilter;
    private ArrayList<T> mOriginalValues;
    protected List<T> mObjects;
    protected int mCurrentCheckPosition = -1;
    protected Context mContext;
    protected int mResource;

    public abstract void onBindData(CygViewHolder var1, T var2, int var3);

    public CygAdapter(Context context, int resource, List<T> objects) {
        this.mContext = context;
        this.mResource = resource;
        this.mObjects = objects;
    }

    public int getCount() {
        return this.mObjects == null?0:this.mObjects.size();
    }

    public T getItem(int position) {
        return this.mObjects.get(position);
    }

    public long getItemId(int position) {
        return (long)position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        CygViewHolder viewHolder = CygViewHolder.get(this.mContext, this.mResource, convertView, parent);
        this.onBindData(viewHolder, this.getItem(position), position);
        return viewHolder.getItemView();
    }

    public final void add(T object) {
        Object var2 = this.mLock;
        synchronized(this.mLock) {
            if(this.mOriginalValues != null) {
                this.mOriginalValues.add(object);
            } else {
                this.mObjects.add(object);
            }
        }

        this.notifyDataSetChanged();
    }

    public final void addAll(List<T> collection) {
        Object var2 = this.mLock;
        synchronized(this.mLock) {
            if(this.mOriginalValues != null) {
                this.mOriginalValues.addAll(collection);
            } else {
                this.mObjects.addAll(collection);
            }
        }

        this.notifyDataSetChanged();
    }

    public final void insert(T object, int index) {
        Object var3 = this.mLock;
        synchronized(this.mLock) {
            if(this.mOriginalValues != null) {
                this.mOriginalValues.add(index, object);
            } else {
                this.mObjects.add(index, object);
            }
        }

        this.notifyDataSetChanged();
    }

    public final void set(T object) {
        Object var2 = this.mLock;
        synchronized(this.mLock) {
            int index = this.mObjects.indexOf(object);
            if(this.mOriginalValues != null) {
                this.mOriginalValues.set(index, object);
            } else {
                this.mObjects.set(index, object);
            }
        }

        this.notifyDataSetChanged();
    }

    public final void set(T object, int index) {
        Object var3 = this.mLock;
        synchronized(this.mLock) {
            if(this.mOriginalValues != null) {
                this.mOriginalValues.set(index, object);
            } else {
                this.mObjects.set(index, object);
            }
        }

        this.notifyDataSetChanged();
    }

    public final void remove(T object) {
        Object var2 = this.mLock;
        synchronized(this.mLock) {
            if(this.mOriginalValues != null) {
                this.mOriginalValues.remove(object);
            } else {
                this.mObjects.remove(object);
            }
        }

        this.notifyDataSetChanged();
    }

    public final void remove(int location) {
        Object var2 = this.mLock;
        synchronized(this.mLock) {
            if(this.mOriginalValues != null) {
                this.mOriginalValues.remove(location);
            } else {
                this.mObjects.remove(location);
            }
        }

        this.notifyDataSetChanged();
    }

    public final void clear(boolean notify) {
        Object var2 = this.mLock;
        synchronized(this.mLock) {
            if(this.mOriginalValues != null) {
                this.mOriginalValues.clear();
            } else {
                this.mObjects.clear();
            }
        }

        if(notify) {
            this.notifyDataSetChanged();
        }

    }

    public final int getCurrentCheckPosition() {
        return this.mCurrentCheckPosition;
    }

    public final void setCurrentCheckPosition(int position) {
        this.mCurrentCheckPosition = position;
        this.notifyDataSetChanged();
    }

    public final List<T> getObjects() {
        return this.mObjects;
    }

    public final void sort(Comparator<? super T> comparator) {
        Object var2 = this.mLock;
        synchronized(this.mLock) {
            if(this.mOriginalValues != null) {
                Collections.sort(this.mOriginalValues, comparator);
            } else {
                Collections.sort(this.mObjects, comparator);
            }
        }

        this.notifyDataSetChanged();
    }

    public Filter getFilter() {
        if(this.mFilter == null) {
            this.mFilter = new ObjectFilter();
        }

        return this.mFilter;
    }

    class ObjectFilter extends Filter {
        ObjectFilter() {
        }

        protected FilterResults performFiltering(CharSequence prefix) {
            FilterResults results = new FilterResults();
            if(CygAdapter.this.mOriginalValues == null) {
                synchronized(CygAdapter.this.mLock) {
                    CygAdapter.this.mOriginalValues = new ArrayList(CygAdapter.this.mObjects);
                }
            }

            ArrayList values;
            if(prefix != null && prefix.length() != 0) {
                synchronized(CygAdapter.this.mLock) {
                    values = new ArrayList(CygAdapter.this.mOriginalValues);
                }

                ArrayList newValues = new ArrayList();
                int count = values.size();
                String prefixString = prefix.toString().toLowerCase();

                for(int i = 0; i < count; ++i) {
                    Object value = values.get(i);
                    String valueText = value.toString().toLowerCase();
                    if(valueText.contains(prefixString)) {
                        newValues.add(value);
                    }
                }

                results.values = newValues;
                results.count = newValues.size();
            } else {
                synchronized(CygAdapter.this.mLock) {
                    values = new ArrayList(CygAdapter.this.mOriginalValues);
                }

                results.values = values;
                results.count = values.size();
            }

            return results;
        }

        protected void publishResults(CharSequence constraint, FilterResults results) {
            CygAdapter.this.mObjects = (List)results.values;
            if(results.count > 0) {
                CygAdapter.this.notifyDataSetChanged();
            } else {
                CygAdapter.this.notifyDataSetInvalidated();
            }

        }
    }
}
