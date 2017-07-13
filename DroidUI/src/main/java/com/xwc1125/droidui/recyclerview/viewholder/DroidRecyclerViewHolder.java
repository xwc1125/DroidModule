package com.xwc1125.droidui.recyclerview.viewholder;


import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.xwc1125.droidui.recyclerview.listener.DroidItemClickListener;
import com.xwc1125.droidui.recyclerview.listener.DroidItemLongClickListener;

/**
 * Created by xwc1125 on 2017/4/27.
 */

public class DroidRecyclerViewHolder extends RecyclerView.ViewHolder
        implements View.OnClickListener, View.OnLongClickListener {

    private DroidItemClickListener mItemClickListener;
    private DroidItemLongClickListener mItemLongClickListener;

    public DroidRecyclerViewHolder(View itemView) {
        super(itemView);
    }

    public DroidRecyclerViewHolder(View itemView, DroidItemClickListener itemClickListener,
                                   DroidItemLongClickListener itemLongClickListener) {
        super(itemView);
        this.mItemClickListener = itemClickListener;
        this.mItemLongClickListener = itemLongClickListener;
        itemView.setOnClickListener(this);
        itemView.setOnLongClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (mItemClickListener != null) {
            mItemClickListener.onItemClick(v, getPosition());
        }
    }

    @Override
    public boolean onLongClick(View v) {
        if (mItemLongClickListener != null) {
            mItemLongClickListener.onItemLongClick(v, getPosition());
        }
        return true;
    }
}
