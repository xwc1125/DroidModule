package com.xwc1125.yuancy_app.base;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public abstract class BaseFragment extends Fragment {
    protected static Activity activity;
    protected static String KEY_BUNDLE_TITLE="key_bundle_title";

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        activity=getActivity();
        getBundleExtra();
        initView(view);
        initListeners();
        initData();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return initView(inflater, container,savedInstanceState);
    }

    /**
     * init the data
     */
    protected abstract void getBundleExtra();

    /**
     * init the View
     */
    protected abstract View initView(LayoutInflater inflater, ViewGroup container,
                                     Bundle savedInstanceState);

    /**
     * init the View
     */
    protected abstract void initView(View view);

    /**
     * init the initListeners
     */
    protected abstract void initListeners();

    /**
     * init the data
     */
    protected abstract void initData();

}

