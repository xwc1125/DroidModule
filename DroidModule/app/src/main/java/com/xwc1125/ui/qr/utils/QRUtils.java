package com.xwc1125.ui.qr.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.TypedValue;

import com.google.zxing.PlanarYUVLuminanceSource;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Class: com.xwc1125.ui.qr.utils <br>
 * Description: TODO <br>
 *
 * @author xwc1125 <br>
 * @version V1.0
 * @Copyright: Copyright (c) 2017 <br>
 * @date 2017/6/11  16:18 <br>
 */
public class QRUtils {
    /**
     * 将dp值转为px值
     *
     * @param context
     * @param dpValue
     * @return
     */
    public static int dp2px(Context context, float dpValue) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dpValue
                , context.getResources().getDisplayMetrics());
    }

    /**
     * 将sp值转为px值
     *
     * @param context
     * @param spValue
     * @return
     */
    public static int sp2px(Context context, float spValue) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, spValue
                , context.getResources().getDisplayMetrics());
    }

    /**
     * 保存source成图片
     *
     * @param source
     * @param bitName
     * @param imageFilePath
     */
    public static void saveBitmap(PlanarYUVLuminanceSource source, String bitName, String imageFilePath) throws IOException {
        int[] pixels = source.renderThumbnail();
        int width = source.getThumbnailWidth();
        int height = source.getThumbnailHeight();
        Bitmap bitmap = Bitmap.createBitmap(pixels, 0, width, width, height,
                Bitmap.Config.ARGB_8888);
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 50, out);
        saveBitmap(bitmap, bitName, imageFilePath);
    }

    /**
     * 保存生图片
     *
     * @param bitmap        图片
     * @param bitName       图片名称
     * @param imageFilePath 图片所在目录【一定需要已经存在】
     */
    public static void saveBitmap(Bitmap bitmap, String bitName, String imageFilePath) throws IOException {
        //获取与应用相关联的路径
        File imageFile = new File(imageFilePath, "/" + bitName);// 通过路径创建保存文件
        if (imageFile.exists()) {
            imageFile.delete();
        }
        FileOutputStream out = new FileOutputStream(imageFile);
        if (bitmap.compress(Bitmap.CompressFormat.PNG, 100, out)) {
            out.flush();
            out.close();
        }
    }
}
