package com.xwc1125.droidmodule.Picture;

import android.content.Context;
import android.widget.ImageView;

import com.xwc1125.droidmodule.Picture.adapter.CygAdapter;
import com.xwc1125.droidmodule.Picture.adapter.CygViewHolder;
import com.xwc1125.droidmodule.R;

import org.xutils.x;

import java.util.List;

/**
 * Created by hupei on 2016/7/14.
 */
class PickPictureTotalAdapter extends CygAdapter<Picture> {
    public PickPictureTotalAdapter(Context context, List<Picture> objects) {
        super(context, R.layout.pick_picture_total_activity_list_item, objects);
    }

    @Override
    public void onBindData(CygViewHolder viewHolder, Picture item, int position) {
        viewHolder.setText(R.id.pick_picture_total_list_item_group_title, item.getFolderName());
        viewHolder.setText(R.id.pick_picture_total_list_item_group_count
                , "(" + Integer.toString(item.getPictureCount()) + ")");
        ImageView imageView = (ImageView) viewHolder.findViewById(R.id.pick_picture_total_list_item_group_image);
        x.image().bind(imageView,item.getTopPicturePath());
    }
}
