package com.xwc1125.droidui.toast;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.xwc1125.droidui.R;
import com.xwc1125.droidui.language.AppTextView;

/**
 * 自定义提示Toast
 *
 * @author zihao
 */
public class DroidToast extends Toast {

    private DroidToast(Context context) {
        super(context);
    }

    /**
     * 生成一个图文并存的Toast
     *
     * @param context  // 上下文对象
     * @param drawable // 要显示的图片
     * @param text     // 要显示的文字
     * @param duration // 显示时间
     * @return
     */
    @SuppressLint("InflateParams")
    @SuppressWarnings("deprecation")
    public static DroidToast makeToast(Context context,
                                       Drawable drawable, CharSequence text, int duration) {
        return init(context, drawable, text, duration);
    }

    public static DroidToast makeToast(Context context,
                                       Drawable drawable, int resStrId, int duration) {
        return init(context, drawable, resStrId, duration);
    }

    /**
     * 生成一个只有文本的自定义Toast
     *
     * @param context  // 上下文对象
     * @param text     // 要显示的文字
     * @param duration // Toast显示时间
     * @return
     */
    @SuppressLint("InflateParams")
    public static DroidToast makeToast(Context context, CharSequence text,
                                       int duration) {
        return init(context, null, text, duration);
    }

    public static DroidToast makeToast(Context context, int resStrId,
                                       int duration) {
        return init(context, null, resStrId, duration);
    }

    /**
     * 生成一个只有图片的自定义Toast
     *
     * @param context  // 上下文对象
     * @param drawable // 图片对象
     * @param duration // Toast显示时间
     * @return
     */
    @SuppressLint("InflateParams")
    @SuppressWarnings("deprecation")
    public static DroidToast makeToast(Context context, Drawable drawable,
                                       int duration) {
        return init(context, drawable, null, duration);
    }

    private static DroidToast init(Context context, Drawable drawable, int resStrId,
                                   int duration) {
        return init(context, drawable, null, resStrId, duration);
    }

    private static DroidToast init(Context context, Drawable drawable, CharSequence text,
                                   int duration) {
        return init(context, drawable, text, 0, duration);
    }

    private static DroidToast init(Context context, Drawable drawable, CharSequence text,
                                   int resStrId, int duration) {
        DroidToast result = new DroidToast(context);
        LayoutInflater inflate = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = inflate.inflate(R.layout.public_toast_view, null);
        ImageView img = (ImageView) v.findViewById(R.id.tips_icon);
        if (drawable != null) {
            img.setBackgroundDrawable(drawable);
            img.setVisibility(View.VISIBLE);
        } else {
            img.setVisibility(View.GONE);
        }

        View tvmsg = v.findViewById(R.id.tips_msg);

        if (tvmsg instanceof AppTextView) {
            AppTextView tv = (AppTextView) tvmsg;
            if (text != null) {
                tv.setText(text);
                tv.setVisibility(View.VISIBLE);
            } else if (resStrId > 0) {
                tv.setTextById(resStrId);
                tv.setVisibility(View.VISIBLE);
            } else {
                tv.setVisibility(View.GONE);
            }
        } else {
            TextView tv = (TextView) tvmsg;
            if (text != null) {
                tv.setText(text);
                tv.setVisibility(View.VISIBLE);
            } else {
                tv.setVisibility(View.GONE);
            }
        }


        result.setView(v);
        // setGravity方法用于设置位置，此处为垂直居中
        result.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
        result.setDuration(duration);
        return result;
    }

}