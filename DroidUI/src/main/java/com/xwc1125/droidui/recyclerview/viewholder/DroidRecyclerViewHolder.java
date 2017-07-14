package com.xwc1125.droidui.recyclerview.viewholder;


import android.support.annotation.StringRes;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

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
            mItemClickListener.onItemClick(v, getPosition());//getAdapterPosition()
        }
    }

    @Override
    public boolean onLongClick(View v) {
        if (mItemLongClickListener != null) {
            mItemLongClickListener.onItemLongClick(v, getPosition());
        }
        return true;
    }

    /**
     * 获取控件
     *
     * @param id
     * @param <T>
     * @return
     */
    protected <T extends View> T findViewById(int id) {
        return (T) itemView.findViewById(id);
    }

    public void setText(int id, CharSequence sequence) {
        View view = findViewById(id);
        if (view instanceof TextView) {
            ((TextView) view).setText(sequence);
        }
    }

    public void setText(int id, @StringRes int stringRes) {
        View view = findViewById(id);
        if (view instanceof TextView) {
            ((TextView) view).setText(stringRes);
        }
    }

    public void setTextColor(int id, int colorId) {
        View view = findViewById(id);
        if (view instanceof TextView) {
            ((TextView) view).setTextColor(ContextCompat.getColor(view.getContext(), colorId));
        }
    }

    public void setImageResource(int id, int imageId) {
        View view = findViewById(id);
        if (view instanceof ImageView) {
            ((ImageView) view).setImageResource(imageId);
        }
    }

}
