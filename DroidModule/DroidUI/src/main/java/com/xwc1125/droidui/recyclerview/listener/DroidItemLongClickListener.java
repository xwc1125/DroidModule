package com.xwc1125.droidui.recyclerview.listener;

import android.view.View;

/**
 * item长按事件
 * <p>
 * Created by xwc1125 on 2017/4/27.
 */
public interface DroidItemLongClickListener {
    /**
     * tem长按事件
     *
     * @param view
     * @param poistion 位置
     */
    void onItemLongClick(View view, int position);
}
