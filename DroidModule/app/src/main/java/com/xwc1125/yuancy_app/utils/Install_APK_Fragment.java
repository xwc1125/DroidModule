package com.xwc1125.yuancy_app.utils;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.xwc1125.droidmodule.R;
import com.xwc1125.yuancy_app.base.BaseFragment;
import com.yuancy.framework.utils.app.PackageUtils;
import com.yuancy.framework.utils.string.StringUtils;

/**
 * A simple {@link Fragment} subclass.
 * to handle interaction events.
 * Use the {@link Install_APK_Fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Install_APK_Fragment extends BaseFragment {

    private EditText et_apk_path;
    private Button btn_install;

    public Install_APK_Fragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment Install_APK_Fragment.
     */
    public static Install_APK_Fragment newInstance() {
        Install_APK_Fragment fragment = new Install_APK_Fragment();
        return fragment;
    }

    @Override
    protected void getBundleExtra() {
    }

    @Override
    protected View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.example_fragment_install_apk, container, false);
    }

    @Override
    protected void initView(View view) {
        et_apk_path = (EditText) view.findViewById(R.id.et_apk_path);
        btn_install = (Button) view.findViewById(R.id.btn_install);
    }

    @Override
    protected void initListeners() {
        btn_install.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String path = et_apk_path.getText().toString();
                if (StringUtils.isNotEmpty(path)) {
                    PackageUtils.installNormal(getContext(), path);
                }
            }
        });
    }

    @Override
    protected void initData() {

    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

}
