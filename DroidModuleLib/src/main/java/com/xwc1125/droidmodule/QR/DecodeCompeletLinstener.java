package com.xwc1125.droidmodule.QR;

import android.graphics.Bitmap;

import com.google.zxing.Result;

/**
 * Class: com.xwc1125.ui.qr <br>
 * Description: TODO <br>
 *
 * @author xwc1125 <br>
 * @version V1.0
 * @Copyright: Copyright (c) 2017 <br>
 * @date 2017/6/11  17:27 <br>
 */
public interface DecodeCompeletLinstener {
    /**
     * A valid barcode has been found, so give an indication of success and show
     * the results.
     *
     * @param rawResult   The contents of the barcode.
     * @param scaleFactor amount by which thumbnail was scaled
     * @param barcode     A greyscale bitmap of the camera data which was decoded.
     */
    void handleDecode(Result rawResult, Bitmap barcode, float scaleFactor);

    /**
     * 完成一次扫描后，只需要再调用此方法即可
     */
    void drawViewfinder();


    void onPermissionErr(Exception e);

    void onPermissionSucc();
}
