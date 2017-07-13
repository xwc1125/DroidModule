package com.xwc1125.droidmodule.FileExplorer.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.media.ThumbnailUtils;
import android.os.Handler;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.xwc1125.droidmodule.FileExplorer.utils.ApkUtil;
import com.xwc1125.droidmodule.FileExplorer.utils.AsyncLoadImage;
import com.xwc1125.droidmodule.R;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * Class: com.ugchain.wallet.adapter <br>
 * Description: TODO <br>
 *
 * @author xwc1125 <br>
 * @version V1.0
 * @Copyright: Copyright (c) 2017 <br>
 * @date 2017/6/15  15:30 <br>
 */
public class FileExplorerAdapter extends BaseAdapter implements AbsListView.OnScrollListener {
    private Context context;
    private File[] listFiles;//所有文件list
    private ListView listView;//listview
    private boolean isFile;//查找文件还是文件目录
    /**
     * 0:无选择框，1:单选，2:多选
     */
    private int type;
    private Map<Integer, Boolean> isSelected;//哪些被选中了
    private LayoutInflater inflater;
    private AsyncLoadImage asyncLoadImage;//异步加载图片
    public static Parcelable state;//listview的状态

    /**
     * @param context
     * @param listFiles
     * @param listView
     * @param isFile    //查找文件还是文件目录
     * @param type      0:无选择框，1:单选，2:多选
     */
    public FileExplorerAdapter(Context context, File[] listFiles, ListView listView, boolean isFile, int type) {
        this.context = context;
        this.listFiles = listFiles;
        this.listView = listView;
        this.isFile = isFile;
        this.type = type;
        inflater = LayoutInflater.from(context);
        listView.setOnScrollListener(this);
        asyncLoadImage = new AsyncLoadImage(context, new Handler());
        init();
    }

    private void init() {
        if (type == 2) {
            isSelected = new HashMap<Integer, Boolean>();
            for (int i = 0; i < listFiles.length; i++) {
                isSelected.put(i, false);
            }
        }
    }

    /**
     * 更新
     *
     * @param listFiles
     */
    public void update(File[] listFiles) {
        this.listFiles = listFiles;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return this.listFiles == null ? 0 : this.listFiles.length;
    }

    @Override
    public Object getItem(int position) {
        return listFiles[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = inflater.inflate(R.layout.file_explorer_activity, null);
            holder.fileIcon = (ImageView) convertView.findViewById(R.id.file_icon);
            holder.fileName = (TextView) convertView.findViewById(R.id.file_name);
            holder.cb = (CheckBox) convertView.findViewById(R.id.checkbox);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        if (type == 0) {
            //不显示
            holder.cb.setVisibility(View.GONE);
        } else if (type == 1) {
            //单选
        } else if (type == 2) {
            //多选
        }

        // 具体如何展示数据
        // 目录的显示处理
        if (listFiles[position].isDirectory()) {
            // 1.文件夹有两种情况，空文件夹和非空文件夹
            if (listFiles[position].listFiles().length == 0 && listFiles[position].listFiles() == null) {
                holder.fileIcon.setImageResource(R.drawable.file_explorer_folder);
                holder.fileName.setText(listFiles[position].getName());
            } else {// 有文件的文件夹
                holder.fileIcon.setImageResource(R.drawable.file_explorer_folder_);
                holder.fileName.setText(listFiles[position].getName());
            }
        } else {// 文件的处理
            String fileName = listFiles[position].getName().toLowerCase();
            if (listFiles[position].getName().endsWith(".txt")) {// 文本的显示
                holder.fileIcon.setImageResource(R.drawable.file_explorer_text);
                holder.fileName.setText(fileName);
            } else if (fileName.endsWith(".html") || fileName.endsWith(".xml")) {
                holder.fileIcon.setImageResource(R.drawable.file_explorer_html);
                holder.fileName.setText(fileName);
            } else if (fileName.endsWith(".xls")) {
                holder.fileIcon.setImageResource(R.drawable.file_explorer_excel);
                holder.fileName.setText(fileName);
            } else if (fileName.endsWith(".apk")) {// 加载APK的图标
                Drawable drawable = ApkUtil.loadApkIcon(context, listFiles[position].getAbsolutePath());
                holder.fileIcon.setImageDrawable(drawable);
                holder.fileName.setText(fileName);
            } else if (fileName.endsWith(".mp3") || fileName.endsWith(".ogg") || fileName.endsWith(".wma")
                    || fileName.endsWith(".ape")) {
                holder.fileIcon.setImageResource(R.drawable.file_explorer_excel);
                holder.fileName.setText(fileName);
            } else if (fileName.endsWith(".jpg")
                    || fileName.endsWith(".jpeg") || fileName.endsWith(".png")) {
                // 3.在子线程当中加载图片，加载完毕主线程更新
                holder.fileIcon.setTag(listFiles[position].getAbsolutePath());
                asyncLoadImage.loadImage(holder.fileIcon);
                holder.fileName.setText(fileName);
            } else if (fileName.endsWith(".mp4")
                    || fileName.endsWith(".3gp")
                    || fileName.endsWith(".avi")
                    || fileName.endsWith(".flv")
                    ) {
                Bitmap bitmap = ThumbnailUtils.createVideoThumbnail(listFiles[position].getAbsolutePath(), MediaStore.Video.Thumbnails.MICRO_KIND);
                holder.fileIcon.setImageBitmap(bitmap);
                holder.fileName.setText(listFiles[position].getName());
            } else {
                holder.fileIcon.setImageResource(R.drawable.file_explorer_file);
                holder.fileName.setText(listFiles[position].getName());
            }
        }
        if (type == 2) {
            // 多选
            final CheckBox myCb = holder.cb;
            myCb.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    Boolean b = isSelected.get(position);
                    if (b) {
                        isSelected.put(position, false);
                        myCb.setChecked(false);
                    } else {
                        isSelected.put(position, true);
                        myCb.setChecked(true);
                        File file = listFiles[position];
                    }
                }
            });
            holder.cb.setChecked(isSelected.get(position));
            holder.cb.setVisibility(View.VISIBLE);
        }
        return convertView;
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        switch (scrollState) {
            case AbsListView.OnScrollListener.SCROLL_STATE_FLING:// 这个滑动中
                // 锁住 isAllow = false
                asyncLoadImage.lock();
                break;
            case AbsListView.OnScrollListener.SCROLL_STATE_IDLE:// 滑动停止
                // isAllow = true;
                state = listView.onSaveInstanceState();
                asyncLoadImage.unLock();
                break;
            case AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL:// 手指还在屏幕上
                // isAllow = false
                asyncLoadImage.lock();
                break;
            default:
                break;
        }
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

    }

    private static final class ViewHolder {
        private ImageView fileIcon;
        private TextView fileName;
        private CheckBox cb;
    }
}
