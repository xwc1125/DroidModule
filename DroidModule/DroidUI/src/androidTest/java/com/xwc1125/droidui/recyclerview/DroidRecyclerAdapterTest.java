package com.xwc1125.droidui.recyclerview;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.xwc1125.droidui.recyclerview.adapter.DroidRecyclerAdapter;
import com.xwc1125.droidui.recyclerview.event.DroidRecylerDecoration;
import com.xwc1125.droidui.recyclerview.listener.DroidItemClickListener;
import com.xwc1125.droidui.recyclerview.listener.DroidItemLongClickListener;
import com.xwc1125.droidui.recyclerview.listener.DroidRecyclerBindViewListener;
import com.xwc1125.droidui.recyclerview.viewholder.DroidRecyclerViewHolder;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Description: TODO <br>
 *
 * @author xwc1125 <br>
 * @version V1.0
 * @Copyright: Copyright (c) 2017 <br>
 * @date 2017/6/30  17:22 <br>
 */
public class DroidRecyclerAdapterTest {
    DroidRecyclerAdapter adapter;

    private void bindAdapter(Context context, RecyclerView recyclerView) {
        List itemList = new ArrayList();
        int recycleViewResId = 00;
        RecyclerView recyclerView1 = null;
        adapter = new DroidRecyclerAdapter(context, itemList, recycleViewResId, new DroidRecyclerBindViewListener() {
            @Override
            public void onBindViewHolder(DroidRecyclerViewHolder holder, View itemView, List list, int position) {

            }
        });

        //【xwc1125】如果设置成LinearLayoutManager，那么所有的item都放在一个LinearLayout中
        LinearLayoutManager nav_mg = new LinearLayoutManager(context);
        //水平或垂直摆放，可以不用 HorizontalScrollView
        nav_mg.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView1.setLayoutManager(nav_mg);
        recyclerView1.setAdapter(adapter);
//        } else {
//            //【xwc1125】所有的item放在GridLayout中
//            GridLayoutManager mg = new GridLayoutManager(this, 3);//格子摆放
//            //交错性的摆放，有点win8那种格子风格，最好使用CardView作为item，有边框和圆角
//            //StaggeredGridLayoutManager mg = new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL);
//            recyclerView1.setLayoutManager(mg);
//            recyclerView1.setAdapter(adapter);
//        }
//
        //设置事件
        RecyclerView.ItemDecoration decoration = new DroidRecylerDecoration(context);
        recyclerView1.addItemDecoration(decoration);
        adapter.setOnItemClickListener(new DroidItemClickListener() {
            @Override
            public void onItemClick(View view, int postion) {
            }
        });
        adapter.setOnItemLongClickListener(new DroidItemLongClickListener() {
            @Override
            public void onItemLongClick(View view, int postion) {

            }
        });
    }
}