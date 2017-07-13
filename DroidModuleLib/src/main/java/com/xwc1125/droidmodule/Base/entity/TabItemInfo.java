package com.xwc1125.droidmodule.Base.entity;

import android.support.v4.app.Fragment;

/**
 * Description: TODO <br>
 *
 * @author xwc1125 <br>
 * @version V1.0
 * @Copyright: Copyright (c) 2017 <br>
 * @date 2017/7/11  09:04 <br>
 */
public class TabItemInfo {
    private Fragment fragment;
    private int tabNameResId;
    private int tabImageResId;

    public TabItemInfo() {
    }

    public TabItemInfo(Fragment fragment, int tabNameResId) {
        this.fragment = fragment;
        this.tabNameResId = tabNameResId;
    }

    public TabItemInfo(Fragment fragment, int tabNameResId, int tabImageResId) {
        this.fragment = fragment;
        this.tabNameResId = tabNameResId;
        this.tabImageResId = tabImageResId;
    }

    public Fragment getFragment() {
        return fragment;
    }

    public void setFragment(Fragment fragment) {
        this.fragment = fragment;
    }

    public int getTabNameResId() {
        return tabNameResId;
    }

    public void setTabNameResId(int tabNameResId) {
        this.tabNameResId = tabNameResId;
    }

    public int getTabImageResId() {
        return tabImageResId;
    }

    public void setTabImageResId(int tabImageResId) {
        this.tabImageResId = tabImageResId;
    }
}
