package com.xwc1125.droidui.recyclerview.ui;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;

/**
 * Description: TODO <br>
 *
 * @author xwc1125 <br>
 * @version V1.0
 * @Copyright: Copyright (c) 2017 <br>
 * @date 2017/7/9  12:04 <br>
 */
public class DroidRecyclerView extends RecyclerView {

////设置布局管理器
//mRecyclerView.setLayoutManager(layout);
////设置adapter
//mRecyclerView.setAdapter(adapter)
////设置Item增加、移除动画
//mRecyclerView.setItemAnimator(new DefaultItemAnimator());
////添加分割线
//mRecyclerView.addItemDecoration(new DividerItemDecoration( activity,DividerItemDecoration.HORIZONTAL_LIST));

    public DroidRecyclerView(Context context) {
        super(context);
        init();
    }

    public DroidRecyclerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public DroidRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    public void init() {

    }
}
