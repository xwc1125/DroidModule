package com.xwc1125.droidmodule.Base.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.xwc1125.droidmodule.Base.entity.TabItemInfo;

import java.util.ArrayList;

public class BaseFragmentPagerAdapter extends FragmentPagerAdapter {
    private ArrayList<TabItemInfo> fragments;//fragment列表
    private Context mContext;

    public BaseFragmentPagerAdapter(FragmentManager fm, Context context, ArrayList<TabItemInfo> fragments) {
        super(fm);
        this.mContext = context;
        this.fragments = fragments;
    }

    @Override
    public Fragment getItem(int position) {
        if (fragments != null && fragments.size() > 0) {
            return fragments.get(position).getFragment();
        }
        return null;
    }

    @Override
    public int getCount() {
        return fragments.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mContext.getResources().getString(fragments.get(position).getTabNameResId());
    }
}
