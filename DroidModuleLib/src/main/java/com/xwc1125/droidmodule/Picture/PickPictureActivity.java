package com.xwc1125.droidmodule.Picture;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import com.xwc1125.droidmodule.Base.activity.BaseAppCompatActivity;
import com.xwc1125.droidmodule.Picture.adapter.PickPictureAdapter;
import com.xwc1125.droidmodule.R;
import com.xwc1125.droidui.ToolBar.ToolbarHelper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 手机图片列表中的详细
 * Created by hupei on 2016/7/7.
 */
public class PickPictureActivity extends BaseAppCompatActivity {
    private GridView mGridView;
    private List<String> mList;//此相册下所有图片的路径集合
    private PickPictureAdapter mAdapter;
    private ToolbarHelper toolbarHelper;
    private Toolbar toolbar;

    @Override
    protected void setContentView(Bundle savedInstanceState) {
        setContentView(R.layout.pick_picture_activity);
    }

    @Override
    protected void getBundleExtra() {

    }

    @Override
    protected void initViews() {
        mGridView = (GridView) findViewById(R.id.child_grid);
        mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                setResult(mList.get(position));
            }
        });

//        toolbar = (Toolbar) findViewById(R.id.public_top);toolbarHelper = new ToolbarHelper(toolbar);
//        setSupportActionBar(toolbar);
//        getSupportActionBar().setDisplayShowTitleEnabled(false);
//        toolbarHelper.setTitleResId(R.string.album);
//        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                back();
//            }
//        });
    }

    @Override
    protected void initListeners() {

    }

    @Override
    protected void initData() {
        processExtraData();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        processExtraData();
    }

    private void processExtraData() {
        Bundle extras = getIntent().getExtras();
        if (extras == null) return;
        mList = extras.getStringArrayList("data");
        if (mList.size() > 1) {
            SortPictureList sortList = new SortPictureList();
            Collections.sort(mList, sortList);
        }
        mAdapter = new PickPictureAdapter(this, mList);
        mGridView.setAdapter(mAdapter);
    }

    private void setResult(String picturePath) {
        Intent intent = new Intent();
        intent.putExtra(PickPictureTotalActivity.EXTRA_PICTURE_PATH, picturePath);
        setResult(Activity.RESULT_OK, intent);
        finish();
    }

    public static void gotoActivity(Activity activity, ArrayList<String> childList) {
        Intent intent = new Intent(activity, PickPictureActivity.class);
        intent.putStringArrayListExtra("data", childList);
        activity.startActivityForResult(intent, PickPictureTotalActivity.REQUEST_CODE_SELECT_ALBUM);
    }
}
