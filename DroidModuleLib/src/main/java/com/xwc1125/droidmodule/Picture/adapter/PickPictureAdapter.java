package com.xwc1125.droidmodule.Picture.adapter;


import android.content.Context;
import android.widget.ImageView;

import com.xwc1125.droidmodule.Picture.adapter.CygViewHolder;
import com.xwc1125.droidmodule.Picture.adapter.CygAdapter;
import com.xwc1125.droidmodule.R;

import org.xutils.x;

import java.util.List;

/**
 * 照片浏览
 * Created by hupei on 2016/7/7.
 */
public class PickPictureAdapter extends CygAdapter<String> {

    public PickPictureAdapter(Context context, List<String> datas) {
        super(context, R.layout.pick_picture_activity_grid_item, datas);
    }

    @Override
    public void onBindData(CygViewHolder viewHolder, String item, int position) {
        ImageView imageView = (ImageView) viewHolder.findViewById(R.id.activity_pick_picture_grid_item_image);
        x.image().bind(imageView, item);
    }
}
