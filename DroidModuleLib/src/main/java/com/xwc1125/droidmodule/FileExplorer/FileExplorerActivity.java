package com.xwc1125.droidmodule.FileExplorer;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.xwc1125.droidmodule.Base.activity.BaseAppCompatActivity;
import com.xwc1125.droidmodule.FileExplorer.utils.FileUtils;
import com.xwc1125.droidmodule.FileExplorer.adapter.FileExplorerAdapter;
import com.xwc1125.droidmodule.FileExplorer.utils.MyFileFilter;
import com.xwc1125.droidmodule.R;
import com.xwc1125.droidui.ToolBar.ToolbarHelper;
import com.xwc1125.droidui.toast.DroidToast;

import java.io.File;

/**
 * Class: com.ugchain.wallet.activity <br>
 * Description: TODO <br>
 *
 * @author xwc1125 <br>
 * @version V1.0
 * @Copyright: Copyright (c) 2017 <br>
 * @date 2017/6/15  15:28 <br>
 */
public class FileExplorerActivity extends BaseAppCompatActivity
        implements AdapterView.OnItemClickListener, AdapterView.OnItemLongClickListener {

    public static final int REQUEST_CODE = 0x234;

    private static final String BUNDLE_KEY_ISFILE = "bundle_key_isfile";
    private static final String BUNDLE_KEY_TYPE = "bundle_key_type";
    private static final String BUNDLE_KEY_FILE_PATH = "bundle_key_file_path";
    private static final String BUNDLE_KEY_FILE_DIR = "bundle_key_file_dir";

    private ListView listView;
    private File[] listFiles;
    private FileExplorerAdapter adapter;
    private File parentFile;
    private File currentFileDir;
    private boolean isFile = true;//查找文件还是文件目录
    /**
     * 0:无选择框，1:单选，2:多选
     */
    private int type = 0;

    private TextView pathInfo;
    private Toolbar toolbar;
    private ToolbarHelper toolbarHelper;
    private File rootFile;
    private TextView tv_cancel;
    private TextView tv_comfirm;
    private LinearLayout ll_bottom;

    /**
     * @param isFile //查找文件还是文件目录
     * @param type   0:无选择框，1:单选，2:多选
     */
    public static void openActivity(Activity activity, boolean isFile, int type) {
        Intent intent = new Intent();
        intent.setClass(activity, FileExplorerActivity.class);
        Bundle bundle = new Bundle();
        bundle.putBoolean(FileExplorerActivity.BUNDLE_KEY_ISFILE, isFile);
        bundle.putInt(FileExplorerActivity.BUNDLE_KEY_TYPE, type);
        intent.putExtras(bundle);
        activity.startActivityForResult(intent, FileExplorerActivity.REQUEST_CODE);
        activity.overridePendingTransition(R.anim.in_from_right, R.anim.out_to_left);
    }

    @Override
    protected void setContentView(Bundle savedInstanceState) {
        setContentView(R.layout.file_explorer_activity);
    }

    @Override
    protected void getBundleExtra() {
        isFile = getIntent().getBooleanExtra(BUNDLE_KEY_ISFILE, true);
        type = getIntent().getIntExtra(BUNDLE_KEY_TYPE, 0);
    }

    @Override
    protected void initViews() {
//        toolbarHelper = new ToolbarHelper(activity,R.id.public_top);
//        toolbar =  toolbarHelper.getToolbar();
//        setSupportActionBar(toolbar);
//        getSupportActionBar().setDisplayShowTitleEnabled(false);
//        toolbarHelper.setTitleResId(R.string.title_file_explorer);

        listView = (ListView) findViewById(R.id.listview);
        pathInfo = (TextView) findViewById(R.id.pathInfo);
        tv_cancel = (TextView) findViewById(R.id.tv_cancel);
        tv_comfirm = (TextView) findViewById(R.id.tv_comfirm);
        ll_bottom = (LinearLayout) findViewById(R.id.ll_bottom);
    }

    @Override
    protected void initListeners() {
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                back();
            }
        });
        listView.setOnItemClickListener(this);
        listView.setOnItemLongClickListener(this);

        tv_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                back();
            }
        });

        tv_comfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                returnResult(currentFileDir, "");
            }
        });
        if (isFile) {
            ll_bottom.setVisibility(View.GONE);
        } else {
            ll_bottom.setVisibility(View.VISIBLE);
        }
    }

    @Override
    protected void initData() {
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            listFiles = getListFiles();
            adapter = new FileExplorerAdapter(activity, listFiles, listView, isFile, type);
            listView.setAdapter(adapter);
        } else {
            DroidToast.makeText(activity,R.string.sdcard_error, Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 获取所以的文件
     *
     * @return
     */
    private File[] getListFiles() {
        rootFile = Environment.getExternalStorageDirectory();
        currentFileDir = rootFile;
        listFiles = rootFile.listFiles(new MyFileFilter(isFile));
        return listFiles = FileUtils.sort(listFiles);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        // 点击文件夹的处理
        if (listFiles[position].isDirectory()) {
            //如果是文件见
            parentFile = listFiles[position].getParentFile();
            currentFileDir = listFiles[position];
            pathInfo.setText(currentFileDir.getAbsolutePath().replace(rootFile.getAbsolutePath(), "/SDCard"));

            listFiles = currentFileDir.listFiles(new MyFileFilter(isFile));
            listFiles = FileUtils.sort(listFiles);
            adapter.update(listFiles);
        } else {
            // 点击文件的处理
            if (isFile && type != 2) {
                returnResult(currentFileDir, listFiles[position].getAbsolutePath());
            }
        }
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
        return false;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // /mnt/sdcard/xxxx
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (parentFile != null && !parentFile.getName().equals("") && !parentFile.getName().equals("mnt")) {
                listFiles = parentFile.listFiles(new MyFileFilter(isFile));
                if (listFiles == null) {
                    returnResult(rootFile, "");
                    back();
                    return true;
                }
                listFiles = FileUtils.sort(listFiles);
                adapter.update(listFiles);
                // parentFile == mnt
                parentFile = parentFile.getParentFile();

                // 更新路径信息
                String path = pathInfo.getText().toString();
                int indexOf = path.lastIndexOf("/");
                path = path.substring(0, indexOf);
                pathInfo.setText(path);

                if (adapter.state != null) {
                    listView.setAdapter(adapter);
                    listView.onRestoreInstanceState(adapter.state);
                }
                // 通过
                // listView.setAdapter(adapter);
                // listView.setSelection(location);
            } else {
                back();
            }
        }
        return true;
    }

    private void returnResult(File parentFile, String filePath) {
        Intent data = new Intent();
        Bundle bundle = new Bundle();
        bundle.putBoolean(BUNDLE_KEY_ISFILE, isFile);
        bundle.putInt(BUNDLE_KEY_TYPE, type);
        bundle.putString(BUNDLE_KEY_FILE_PATH, filePath);
        bundle.putString(BUNDLE_KEY_FILE_DIR, parentFile.getAbsolutePath());
        data.putExtras(bundle);
        setResult(Activity.RESULT_OK, data);
        back();
    }
}
