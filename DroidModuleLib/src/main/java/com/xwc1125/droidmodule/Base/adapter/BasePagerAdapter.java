package com.xwc1125.droidmodule.Base.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;

import com.xwc1125.droidmodule.Base.interfaces.PagerCallback;

import java.util.ArrayList;
import java.util.List;

/**
 * Description: TODO <br>
 *
 * @author xwc1125 <br>
 * @version V1.0
 * @Copyright: Copyright (c) 2017 <br>
 * @date 2017/5/23  15:54 <br>
 */
public class BasePagerAdapter extends PagerAdapter {
    private Context mContext;
    private List<View> views = new ArrayList<View>();
    private PagerCallback callback;

    public BasePagerAdapter(Context context, List<View> views, PagerCallback callback) {
        this.views = views;
        this.mContext = context;
        this.callback = callback;
    }

    @Override
    public int getCount() {
        return views.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        ((ViewPager) container).removeView(views.get(position));
    }

    @Override
    public Object instantiateItem(View container, int position) {
        ((ViewPager) container).addView(views.get(position));
        callback.onItemClick(views, position);
        return views.get(position);
    }
}
