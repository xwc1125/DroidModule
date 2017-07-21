package com.xwc1125.ui.recycler;

import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.xwc1125.droidapp.R;
import com.xwc1125.droidmodule.Base.activity.BaseAppCompatActivity;
import com.xwc1125.droidui.recyclerview.adapter.DroidRecyclerAdapter;
import com.xwc1125.droidui.recyclerview.listener.DroidItemClickListener;
import com.xwc1125.droidui.recyclerview.listener.DroidItemLongClickListener;
import com.xwc1125.droidui.recyclerview.listener.DroidRecyclerBindViewListener;
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
        for (int i = 0; i < 10; i++) {
            nav_itemList.add(new Item(R.drawable.icon_cucctv, "头部" + i));
            itemList.add(new Item(R.mipmap.ic_launcher, title + "" + i));
        }
    }

    DroidRecyclerAdapter adapter;
    private Handler handler = new Handler();

    @Override
    protected void initViews() {
        final RefreshLayout refreshLayout = (RefreshLayout) findViewById(R.id.smartLayout);
        refreshLayout.setEnableAutoLoadmore(true);//开启自动加载功能（非必须）
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(final RefreshLayout refreshlayout) {
                ((View) refreshlayout).postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        refreshData();
                        refreshlayout.finishRefresh();
                    }
                }, 2000);
            }
        });
        refreshLayout.setOnLoadmoreListener(new OnLoadmoreListener() {
            @Override
            public void onLoadmore(final RefreshLayout refreshlayout) {
                ((View) refreshlayout).postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        loadMoreData(refreshlayout);
                        refreshlayout.finishLoadmore();
                    }
                }, 2000);
            }
        });

        //触发自动刷新
        refreshLayout.autoRefresh();

        final RecyclerView list = FindViewUtils.findViewById(activity, R.id.nav_listview);
        //【xwc1125】所有的item放在GridLayout中
        GridLayoutManager mg = new GridLayoutManager(this, 3);//格子摆放
        //交错性的摆放，有点win8那种格子风格，最好使用CardView作为item，有边框和圆角
        //StaggeredGridLayoutManager mg = new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL);

        LinearLayoutManager nav_mg2 = new LinearLayoutManager(this);
        //水平或垂直摆放，可以不用 HorizontalScrollView
        nav_mg2.setOrientation(LinearLayoutManager.VERTICAL);

        list.setLayoutManager(nav_mg2);

        adapter = new DroidRecyclerAdapter(itemList, R.layout.myrecycler_item, new DroidRecyclerBindViewListener() {
            @Override
            public void onBindViewHolder(DroidRecyclerViewHolder holder, View itemView, List list, int position) {
                Item item = (Item) list.get(position);
                holder.setText(R.id.text, item.desc);
                holder.setImageResource(R.id.img, item.imgId);
            }
        });
        list.setAdapter(adapter);

        //设置事件
//        RecyclerView.ItemDecoration decoration = new DroidRecylerDecoration(this);
//        list.addItemDecoration(decoration);
        adapter.setOnItemClickListener(this);
        adapter.setOnItemLongClickListener(this);

    }

    int timeCount2 = 0;

    private void refreshData() {
        List<Item> list = new ArrayList<>();
        if (timeCount2 < 3) {
            for (int i = 0; i < 5; i++) {
                list.add(0, new Item(R.drawable.icon_cucctv, "下拉刷新(" + timeCount2 + ")" + i));
            }
            timeCount2++;
        }
        adapter.add(list);
    }

    int timeCount = 0;

    private void loadMoreData(RefreshLayout refreshlayout) {
        List<Item> list = new ArrayList<>();
        if (timeCount < 3) {
            for (int i = 0; i < 10; i++) {
                list.add(new Item(R.drawable.icon_cucctv, "上拉加载(" + timeCount + ")" + i));
            }
            timeCount++;
            adapter.loadmore(list);
        } else {
            Toast.makeText(getApplication(), "数据全部加载完毕", Toast.LENGTH_SHORT).show();
            refreshlayout.setLoadmoreFinished(true);//将不会再次触发加载更多事件
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

}
