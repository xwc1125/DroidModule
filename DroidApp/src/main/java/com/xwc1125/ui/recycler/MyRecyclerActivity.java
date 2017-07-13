package com.xwc1125.ui.recycler;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.xwc1125.droidapp.R;
import com.xwc1125.droidmodule.Base.activity.BaseAppCompatActivity;
import com.xwc1125.droidui.recyclerview.adapter.DroidRecyclerAdapter;
import com.xwc1125.droidui.recyclerview.event.DroidRecylerDecoration;
import com.xwc1125.droidui.recyclerview.listener.DroidItemClickListener;
import com.xwc1125.droidui.recyclerview.listener.DroidItemLongClickListener;
import com.xwc1125.droidui.recyclerview.listener.DroidRecyclerBindViewListener;
import com.xwc1125.droidui.recyclerview.listener.LoadingListener;
import com.xwc1125.droidui.recyclerview.viewholder.DroidRecyclerViewHolder;
import com.xwc1125.droidutils.view.FindViewUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xwc1125 on 2017/4/27.
 */

public class MyRecyclerActivity extends BaseAppCompatActivity
        implements DroidItemClickListener, DroidItemLongClickListener {
    private List<Item> itemList;
    private List<Item> nav_itemList;
    SwipeRefreshLayout swipeRefreshLayout;

    @Override
    protected void setContentView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_recycler);
    }

    @Override
    protected void getBundleExtra() {
        String title = "测试";
        //    String title = "";
        itemList = new ArrayList<Item>(5);
        nav_itemList = new ArrayList<Item>(5);
        for (int i = 0; i < 100; i++) {
            nav_itemList.add(new Item(R.drawable.icon_cucctv, "头部" + i));
            itemList.add(new Item(R.mipmap.ic_launcher, title + "" + i));
        }
    }

    DroidRecyclerAdapter adapter;

    @Override
    protected void initViews() {
        swipeRefreshLayout = FindViewUtils.findViewById(activity, R.id.swipe_refresh);

        RecyclerView nav_list = FindViewUtils.findViewById(activity, R.id.nav_listview);
        //【xwc1125】如果设置成LinearLayoutManager，那么所有的item都放在一个LinearLayout中
        LinearLayoutManager nav_mg = new LinearLayoutManager(this);
        //水平或垂直摆放，可以不用 HorizontalScrollView
        nav_mg.setOrientation(LinearLayoutManager.HORIZONTAL);
        nav_list.setLayoutManager(nav_mg);
        DroidRecyclerAdapter nav_adapter = new DroidRecyclerAdapter(activity, nav_itemList, R.layout.myrecycler_item, new DroidRecyclerBindViewListener() {
            @Override
            public void onBindViewHolder(DroidRecyclerViewHolder holder, View itemView, List list, int position) {
                TextView text = FindViewUtils.findViewById(itemView, R.id.text);
                text.setText(nav_itemList.get(position).desc);
            }
        });
        nav_list.setAdapter(nav_adapter);

        final RecyclerView list = FindViewUtils.findViewById(activity, R.id.listview);
        //【xwc1125】所有的item放在GridLayout中
        GridLayoutManager mg = new GridLayoutManager(this, 3);//格子摆放
        //交错性的摆放，有点win8那种格子风格，最好使用CardView作为item，有边框和圆角
        //StaggeredGridLayoutManager mg = new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL);

        LinearLayoutManager nav_mg2 = new LinearLayoutManager(this);
        //水平或垂直摆放，可以不用 HorizontalScrollView
        nav_mg2.setOrientation(LinearLayoutManager.VERTICAL);

        list.setLayoutManager(nav_mg2);

        adapter = new DroidRecyclerAdapter(activity, itemList, R.layout.myrecycler_item, new DroidRecyclerBindViewListener() {
            @Override
            public void onBindViewHolder(DroidRecyclerViewHolder holder, View itemView, List list, int position) {
                TextView text = FindViewUtils.findViewById(itemView, R.id.text);
                Item item=(Item) list.get(position);
                text.setText(item.desc);
            }
        });
        list.setAdapter(adapter);

        //设置事件
        RecyclerView.ItemDecoration decoration = new DroidRecylerDecoration(this);
        list.addItemDecoration(decoration);
        adapter.setOnItemClickListener(this);
        adapter.setOnItemLongClickListener(this);
        list.addOnScrollListener(new LoadingListener(nav_mg2) {
            @Override
            public void onLoadMore(int currentPage) {
                loadMoreData();
            }
        });

        //为RecyclerView添加HeaderView和FooterView
        setHeaderView(list);
        setFooterView(list);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            public void onRefresh() {
                //我在List最前面加入一条数据
                itemList.add(0, new Item(R.drawable.icon_cucctv, "下拉刷新"));
                //数据重新加载完成后，提示数据发生改变，并且设置现在不在刷新
                adapter.notifyDataSetChanged();
                swipeRefreshLayout.setRefreshing(false);
            }
        });
    }

    private void loadMoreData() {
        for (int i =0; i < 10; i++){
            itemList.add(new Item(R.drawable.icon_cucctv,"上拉加载"+i));
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    protected void initListeners() {

    }

    @Override
    protected void initData() {

    }

    @Override
    public void onItemClick(View view, int postion) {
        Snackbar.make(view, itemList.get(postion).toString(), Snackbar.LENGTH_LONG)
                .setAction("点击", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //点击事件
                        Toast.makeText(activity, "点击事件", Toast.LENGTH_SHORT).show();
                    }
                }).show();
    }

    @Override
    public void onItemLongClick(View view, int postion) {
        Snackbar.make(view, "长按：" + itemList.get(postion).toString(), Snackbar.LENGTH_LONG)
                .setAction("点击", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //点击事件
                        Toast.makeText(activity, "点击事件", Toast.LENGTH_SHORT).show();
                    }
                }).show();
    }

    private void setHeaderView(RecyclerView view) {
        View header = LayoutInflater.from(activity).inflate(R.layout.recycler_view_header, view, false);
//        adapter.setHeaderView(header);
    }

    private void setFooterView(RecyclerView view) {
        View footer = LayoutInflater.from(activity).inflate(R.layout.recycler_view_footer, view, false);
//        adapter.setFooterView(footer);
    }
}
