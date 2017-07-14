package com.xwc1125.droidui.recyclerview.adapter;

import android.database.DataSetObservable;
import android.database.DataSetObserver;
import android.support.annotation.LayoutRes;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;

import com.xwc1125.droidui.recyclerview.listener.DroidItemClickListener;
import com.xwc1125.droidui.recyclerview.listener.DroidItemLongClickListener;
import com.xwc1125.droidui.recyclerview.listener.DroidRecyclerBindViewListener;
import com.xwc1125.droidui.recyclerview.viewholder.DroidRecyclerViewHolder;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * RecyclerView的适配器
 * <p>
 * Created by xwc1125 on 2017/4/27.
 */

public class DroidRecyclerAdapter<T> extends RecyclerView.Adapter<DroidRecyclerViewHolder>
        implements ListAdapter {
    private List<T> list;//数据
    private DroidItemClickListener mItemClickListener;//点击事件
    private DroidItemLongClickListener mItemLongClickListener;//长按事件
    private int itemLayoutResId;//item的layout布局
    private DroidRecyclerBindViewListener bindViewListener;//控件绑定的回调

    private final DataSetObservable mDataSetObservable = new DataSetObservable();

    /**
     * 初始化适配器
     *
     * @param layoutId item的layout布局【R.layout.itemlayout】
     */
    public DroidRecyclerAdapter(@LayoutRes int layoutId) {
        setHasStableIds(false);
        this.list = new ArrayList<>();
        this.itemLayoutResId = layoutId;
    }

    /**
     * 初始化适配器
     *
     * @param collection 显示的对象list
     * @param layoutId   item的layout布局【R.layout.itemlayout】
     */
    public DroidRecyclerAdapter(Collection<T> collection, @LayoutRes int layoutId) {
        setHasStableIds(false);
        this.list = new ArrayList<>(collection);
        this.itemLayoutResId = layoutId;
    }

    /**
     * 初始化适配器
     *
     * @param collection       显示的对象list
     * @param layoutId         item的layout布局【R.layout.itemlayout】
     * @param bindViewListener 绑定view<br>
     *                         Item item = (Item) list.get(position);<br>
     *                         TextView text1 = (TextView) itemView.findViewById(R.id.text);<br>
     *                         text1.setText(item.desc);<br>
     *                         ImageView img = (ImageView) itemView.findViewById(R.id.img);<br>
     *                         img.setImageResource(item.imgId);<br>
     */
    public DroidRecyclerAdapter(Collection<T> collection, @LayoutRes int layoutId,
                                DroidRecyclerBindViewListener bindViewListener) {
        setHasStableIds(false);
        this.list = new ArrayList<>(collection);
        this.itemLayoutResId = layoutId;
        this.bindViewListener = bindViewListener;
    }

    @Override
    public void registerDataSetObserver(DataSetObserver observer) {
        mDataSetObservable.registerObserver(observer);
    }

    @Override
    public void unregisterDataSetObserver(DataSetObserver observer) {
        mDataSetObservable.unregisterObserver(observer);
    }

    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        DroidRecyclerViewHolder holder;
        if (convertView != null) {
            holder = (DroidRecyclerViewHolder) convertView.getTag();
        } else {
            holder = onCreateViewHolder(parent, getItemViewType(position));
            convertView = holder.itemView;
            convertView.setTag(holder);
        }
        onBindViewHolder(holder, position);
        return convertView;
    }

    /**
     * 重写这个方法，很重要，是加入Header和Footer的关键，我们通过判断item的类型，从而绑定不同的view    *
     */
    @Override
    public int getItemViewType(int position) {
        return 0;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public boolean isEmpty() {
        return getCount() == 0;
    }

    @Override
    public DroidRecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //绑定一个UI作为Holder 提高性能
        View v = LayoutInflater.from(parent.getContext()).inflate(itemLayoutResId, parent, false);//必须加上 parent,false，否则只是显示部分
        DroidRecyclerViewHolder holder = new DroidRecyclerViewHolder(v, mItemClickListener, mItemLongClickListener);
        return holder;
    }

    //绑定View，这里是根据返回的这个position的类型，从而进行绑定的，   HeaderView和FooterView, 就不同绑定了
    @Override
    public void onBindViewHolder(DroidRecyclerViewHolder holder, int position) {
        //设置数据
        if (bindViewListener != null) {
            bindViewListener.onBindViewHolder(holder, holder.itemView, list, position);
        }
        return;
    }

    /**
     * 返回View中Item的个数
     */
    @Override
    public int getItemCount() {
        return list.size();
    }

    /**
     * 设置Item点击监听
     *
     * @param listener
     */
    public void setOnItemClickListener(DroidItemClickListener listener) {
        this.mItemClickListener = listener;
    }

    /**
     * 设置Item长按监听
     *
     * @param listener
     */
    public void setOnItemLongClickListener(DroidItemLongClickListener listener) {
        this.mItemLongClickListener = listener;
    }

    /**
     * 刷新数据（原有数据清除后，重新负值）
     *
     * @param collection
     */
    public void refresh(Collection<T> collection) {
        list.clear();
        list.addAll(collection);
        notifyDataSetChanged();
        notifyListDataSetChanged();
    }

    /**
     * 刷新数据（刷新指定位置的数据）
     *
     * @param position
     * @param data
     */
    public void refresh(int position, T data) {
        list.remove(position);
        list.add(position, data);
        notifyDataSetChanged();
        notifyListDataSetChanged();
    }

    /**
     * 添加数据（在原有数据前添加数据）
     *
     * @param collection
     */
    public void add(Collection<T> collection) {
        collection.addAll(list);
        list.clear();
        list.addAll(collection);
        notifyDataSetChanged();
        notifyListDataSetChanged();
    }

    /**
     * 添加数据（指定位置添加数据）
     *
     * @param position
     * @param data
     */
    public void add(int position, T data) {
        list.add(position, data);
        notifyItemInserted(position);
        notifyDataSetChanged();
        notifyListDataSetChanged();
    }

    /**
     * 添加数据（在原数据前加一条数据）
     *
     * @param data
     */
    public void add(T data) {
        list.add(0, data);
        notifyDataSetChanged();
        notifyListDataSetChanged();
    }

    /**
     * 加载更多（在原数据后添加数据）
     *
     * @param collection
     */
    public void loadmore(Collection<T> collection) {
        list.addAll(collection);
        notifyDataSetChanged();
        notifyListDataSetChanged();
    }

    /**
     * 加载更多（在原数据后添加一条数据）
     *
     * @param newData
     */
    public void loadmore(T newData) {
        list.add(newData);
        notifyDataSetChanged();
        notifyListDataSetChanged();
    }

    /**
     * 移除数据（移除指定位置的数据）
     *
     * @param position
     */
    public void remove(int position) {
        list.remove(position);
        notifyItemRemoved(position);
        notifyDataSetChanged();
        notifyListDataSetChanged();
    }

    public void notifyListDataSetChanged() {
        mDataSetObservable.notifyChanged();
    }

    public void notifyDataSetInvalidated() {
        mDataSetObservable.notifyInvalidated();
    }

    @Override
    public boolean areAllItemsEnabled() {
        return true;
    }

    @Override
    public boolean isEnabled(int position) {
        return true;
    }
}
