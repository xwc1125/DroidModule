package com.xwc1125.ui.recycler;

import android.content.Context;
import android.os.Bundle;
import android.os.Trace;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.xwc1125.droidmodule.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xwc1125 on 2017/4/27.
 */

public class MyRecyclerActivity extends ActionBarActivity implements MyItemClickListener,MyItemLongClickListener{
    private Context mContext;
    private List<Item> itemList;
    private List<Item> nav_itemList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler);
        mContext=this;
        initView();
    }

    private void initView() {
        String title = "测试";
        //    String title = "";
        itemList = new ArrayList<Item>(5);
        nav_itemList=new ArrayList<Item>(5);
        for (int i = 0; i < 100; i++) {
            nav_itemList.add(new Item(R.drawable.icon_cucctv,"头部" + i));
            itemList.add(new Item(R.drawable.recycler_demo, title + "" + i));
        }

        RecyclerView nav_list = (RecyclerView) findViewById(R.id.nav_listview);
        //【xwc1125】如果设置成LinearLayoutManager，那么所有的item都放在一个LinearLayout中
        LinearLayoutManager nav_mg = new LinearLayoutManager(this);
        //水平或垂直摆放，可以不用 HorizontalScrollView
        nav_mg.setOrientation(LinearLayoutManager.HORIZONTAL);
        nav_list.setLayoutManager(nav_mg);
        MyRecyclerAdapter nav_adapter = new MyRecyclerAdapter(this, nav_itemList);
        nav_list.setAdapter(nav_adapter);

        RecyclerView list = (RecyclerView) findViewById(R.id.listview);
        //【xwc1125】所有的item放在GridLayout中
        GridLayoutManager mg = new GridLayoutManager(this, 3);//格子摆放
        //交错性的摆放，有点win8那种格子风格，最好使用CardView作为item，有边框和圆角
        //StaggeredGridLayoutManager mg = new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL);
        list.setLayoutManager(mg);
        MyRecyclerAdapter adapter = new MyRecyclerAdapter(this, itemList);
        list.setAdapter(adapter);

        //设置事件
        RecyclerView.ItemDecoration decoration = new MyRecylerDecoration(this);
        list.addItemDecoration(decoration);
        adapter.setOnItemClickListener(this);
        adapter.setOnItemLongClickListener(this);
    }

    @Override
    public void onItemClick(View view, int postion) {
        Snackbar.make(view, itemList.get(postion).toString(), Snackbar.LENGTH_LONG)
                .setAction("点击", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //点击事件
                        Toast.makeText(mContext, "点击事件", Toast.LENGTH_SHORT).show();
                    }
                }).show();
    }

    @Override
    public void onItemLongClick(View view, int postion) {
        Snackbar.make(view, "长按："+itemList.get(postion).toString(), Snackbar.LENGTH_LONG)
                .setAction("点击", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //点击事件
                        Toast.makeText(mContext, "点击事件", Toast.LENGTH_SHORT).show();
                    }
                }).show();
    }
}
