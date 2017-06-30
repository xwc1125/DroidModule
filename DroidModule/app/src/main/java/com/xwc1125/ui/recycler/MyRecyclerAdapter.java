package com.xwc1125.ui.recycler;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.xwc1125.droidmodule.R;

import java.util.List;

/**
 * Created by xwc1125 on 2017/4/27.
 */

public class MyRecyclerAdapter extends RecyclerView.Adapter<MyRecyclerViewHolder> {
    private Activity activity;
    private List<Item> list;
    private MyItemClickListener mItemClickListener;
    private MyItemLongClickListener mItemLongClickListener;

    public MyRecyclerAdapter(Activity act, List<Item> list) {
        this.activity = act;
        this.list = list;
    }

    @Override
    public MyRecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //绑定一个UI作为Holder 提高性能
        View v = LayoutInflater.from(activity).inflate(R.layout.myrecycler_item, null);
        MyRecyclerViewHolder holder = new MyRecyclerViewHolder(v,mItemClickListener,mItemLongClickListener);
        return holder;
    }

    @Override
    public void onBindViewHolder(MyRecyclerViewHolder holder, int position) {
        //设置数据
        Item item = list.get(position);
        TextView text1 = (TextView) holder.itemView.findViewById(R.id.text);
        text1.setText(item.desc);
        ImageView img = (ImageView) holder.itemView.findViewById(R.id.img);
        img.setImageResource(item.imgId);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
    /**
     * 设置Item点击监听
     * @param listener
     */
    public void setOnItemClickListener(MyItemClickListener listener){
        this.mItemClickListener = listener;
    }

    public void setOnItemLongClickListener(MyItemLongClickListener listener){
        this.mItemLongClickListener = listener;
    }
}
