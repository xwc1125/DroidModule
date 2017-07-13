package com.xwc1125.droidui.recyclerview.listener;

import android.view.View;

import com.xwc1125.droidui.recyclerview.viewholder.DroidRecyclerViewHolder;

import java.util.List;

/**
 * recyclerview绑定事件
 * <p>
 * Created by xwc1125 on 2017/6/29.
 */
public interface DroidRecyclerBindViewListener<T> {
    /**
     * recyclerview绑定事件
     *
     * @param holder
     * @param itemView
     * @param list
     * @param position
     */
    void onBindViewHolder(DroidRecyclerViewHolder holder, View itemView, List<T> list, int position);
}
