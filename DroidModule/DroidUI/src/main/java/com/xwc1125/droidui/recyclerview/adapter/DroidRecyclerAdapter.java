package com.xwc1125.droidui.recyclerview.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.xwc1125.droidui.R;
import com.xwc1125.droidui.recyclerview.listener.DroidItemClickListener;
import com.xwc1125.droidui.recyclerview.listener.DroidItemLongClickListener;
import com.xwc1125.droidui.recyclerview.listener.DroidRecyclerBindViewListener;
import com.xwc1125.droidui.recyclerview.viewholder.DroidRecyclerViewHolder;

import java.util.List;

/**
 * RecyclerView的适配器
 * <p>
 * Created by xwc1125 on 2017/4/27.
 */

public class DroidRecyclerAdapter<T> extends RecyclerView.Adapter<DroidRecyclerViewHolder> {
    private Context context;
    private List<T> list;
    private DroidItemClickListener mItemClickListener;
    private DroidItemLongClickListener mItemLongClickListener;
    private int itemLayoutResId;
    private DroidRecyclerBindViewListener bindViewListener;

//    public static final int TYPE_HEADER = 0;  //说明是带有Header的
//    public static final int TYPE_FOOTER = 1;  //说明是带有Footer的
//    public static final int TYPE_NORMAL = 2;  //说明是不带有header和footer的
//
//    private View headerView;
//    private View footerView;

    /**
     * 初始化适配器
     *
     * @param context
     * @param list             显示的对象list
     * @param itemLayoutResId  item的layout布局【R.layout.itemlayout】
     * @param bindViewListener 绑定view<br>
     *                         Item item = (Item) list.get(position);<br>
     *                         TextView text1 = (TextView) itemView.findViewById(R.id.text);<br>
     *                         text1.setText(item.desc);<br>
     *                         ImageView img = (ImageView) itemView.findViewById(R.id.img);<br>
     *                         img.setImageResource(item.imgId);<br>
     */
    public DroidRecyclerAdapter(Context context, List<T> list, int itemLayoutResId, DroidRecyclerBindViewListener bindViewListener) {
        this.context = context;
        this.list = list;
        this.itemLayoutResId = itemLayoutResId;
        this.bindViewListener = bindViewListener;
    }

    /**
     * 重写这个方法，很重要，是加入Header和Footer的关键，我们通过判断item的类型，从而绑定不同的view    *
     */
    @Override
    public int getItemViewType(int position) {
//        if (headerView == null && footerView == null) {
//            return TYPE_NORMAL;
//        }
//        if (position == 0) {
//            //第一个item应该加载Header
//            return TYPE_HEADER;
//        }
//        if (position == getItemCount() - 1) {
//            //最后一个,应该加载Footer
//            return TYPE_FOOTER;
//        }
//        return TYPE_NORMAL;
        return 0;
    }

    @Override
    public DroidRecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//        if (headerView != null && viewType == TYPE_HEADER) {
//            return new DroidRecyclerViewHolder(headerView);
//        }
//        if (footerView != null && viewType == TYPE_FOOTER) {
//            return new DroidRecyclerViewHolder(footerView);
//        }
        //绑定一个UI作为Holder 提高性能
        View v = LayoutInflater.from(context).inflate(itemLayoutResId, parent, false);//必须加上 parent,false，否则只是显示部分
        DroidRecyclerViewHolder holder = new DroidRecyclerViewHolder(v, mItemClickListener, mItemLongClickListener);
        return holder;
    }

    //绑定View，这里是根据返回的这个position的类型，从而进行绑定的，   HeaderView和FooterView, 就不同绑定了
    @Override
    public void onBindViewHolder(DroidRecyclerViewHolder holder, int position) {
//        if (getItemViewType(position) == TYPE_NORMAL) {
////            if (holder instanceof DroidRecyclerViewHolder) {
////                //这里加载数据的时候要注意，是从position-1开始，因为position==0已经被header占用了
//////                ((DroidRecyclerViewHolder) holder).tv.setText(list.get(position - 1));
////                return;
////            }
//            //设置数据
//            if (bindViewListener != null) {
//                bindViewListener.onBindViewHolder(holder, holder.itemView, list, position-1);
//            }
//            return;
//        } else if (getItemViewType(position) == TYPE_HEADER) {
//            return;
//        }else {
//            return;
//        }

        //设置数据
        if (bindViewListener != null) {
            bindViewListener.onBindViewHolder(holder, holder.itemView, list, position);
        }
        return;
    }

    //返回View中Item的个数，这个时候，总的个数应该是ListView中Item的个数加上HeaderView和FooterView
    @Override
    public int getItemCount() {
//        if (headerView == null && footerView == null) {
//            return list.size();
//        } else if (headerView == null && footerView != null) {
//            return list.size() + 1;
//        } else if (headerView != null && footerView == null) {
//            return list.size() + 1;
//        } else {
//            return list.size() + 2;
//        }

        return list.size();
    }

    /**
     * 设置Item点击监听
     *
     * @param listener
     */
    public void setOnItemClickListener(DroidItemClickListener listener) {
        this.mItemClickListener = listener;
    }

    /**
     * 设置Item长按监听
     *
     * @param listener
     */
    public void setOnItemLongClickListener(DroidItemLongClickListener listener) {
        this.mItemLongClickListener = listener;
    }

//    public View getHeaderView() {
//        return headerView;
//    }
//
//    public void setHeaderView(View headerView) {
//        this.headerView = headerView;
//        notifyItemInserted(0);
//    }
//
//    public View getFooterView() {
//        return footerView;
//    }
//
//    public void setFooterView(View footerView) {
//        this.footerView = footerView;
//        notifyItemInserted(getItemCount());
//    }

}
