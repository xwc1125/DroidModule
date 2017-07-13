package com.xwc1125.droidmodule.Base.interfaces;

import android.view.View;

import java.util.List;

/**
 * Description:  The callback for PagerAdapter<br>
 *
 * @author xwc1125 <br>
 * @version V1.0
 * @Copyright: Copyright (c) 2017 <br>
 * @date 2017/5/23  15:59 <br>
 */
public interface PagerCallback {
    /**
     * Description:  The item is clicked<br>
     *
     * @param views
     * @param position
     *
     * @author xwc1125 <br>
     * @date 2017/5/23 16:03
     */
    void onItemClick(List<View> views, int position);
}
