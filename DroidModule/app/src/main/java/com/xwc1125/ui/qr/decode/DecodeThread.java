/*
 * Copyright (C) 2008 ZXing authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.xwc1125.ui.qr.decode;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.DecodeHintType;
import com.google.zxing.ResultPointCallback;
import com.xwc1125.ui.qr.camera.CameraManager;
import com.xwc1125.ui.qr.config.QRConfig;

import java.util.Collection;
import java.util.EnumMap;
import java.util.EnumSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CountDownLatch;

/**
 * 二维码扫描的线程
 * <p>
 * This thread does all the heavy lifting of decoding the images.
 *
 * @author dswitkin@google.com (Daniel Switkin)
 */
public final class DecodeThread extends Thread {

    private static final String TAG = DecodeThread.class.getSimpleName();
    private static boolean isDebug = QRConfig.isDebug;

    public static final String BARCODE_BITMAP = "barcode_bitmap";
    public static final String BARCODE_SCALED_FACTOR = "barcode_scaled_factor";

    private final CameraManager mCameraManager;
    private final Handler scannerViewHandler;

    private final Map<DecodeHintType, Object> hints;

    private Handler handler;//处理的handler

    private final CountDownLatch handlerInitLatch;

    //===============一维码的类型========================
    public static final Set<BarcodeFormat> PRODUCT_FORMATS;//一维码：商品
    public static final Set<BarcodeFormat> INDUSTRIAL_FORMATS; // 一维码：工业

    static {
        PRODUCT_FORMATS = EnumSet.of(BarcodeFormat.UPC_A,
                BarcodeFormat.UPC_E,
                BarcodeFormat.EAN_13,
                BarcodeFormat.EAN_8,
                BarcodeFormat.RSS_14,
                BarcodeFormat.RSS_EXPANDED);
        INDUSTRIAL_FORMATS = EnumSet.of(BarcodeFormat.CODE_39,
                BarcodeFormat.CODE_93,
                BarcodeFormat.CODE_128,
                BarcodeFormat.ITF,
                BarcodeFormat.CODABAR);
    }
    //===============一维码的类型========================

    //===============二维码的类型========================
    static final Set<BarcodeFormat> QR_CODE_FORMATS = EnumSet.of(BarcodeFormat.QR_CODE);
    static final Set<BarcodeFormat> DATA_MATRIX_FORMATS = EnumSet.of(BarcodeFormat.DATA_MATRIX);
    //===============二维码的类型========================

    public DecodeThread(CameraManager cameraManager, Handler scannerViewHandler,
                 Collection<BarcodeFormat> decodeFormats,
                 Map<DecodeHintType, ?> baseHints, String characterSet,
                 ResultPointCallback resultPointCallback) {
        this.mCameraManager = cameraManager;
        this.scannerViewHandler = scannerViewHandler;
        handlerInitLatch = new CountDownLatch(1);

        hints = new EnumMap<DecodeHintType, Object>(DecodeHintType.class);
        if (baseHints != null) {
            hints.putAll(baseHints);
        }

        // The prefs can't change while the thread is running, so pick them up
        // once here.
        if (decodeFormats == null || decodeFormats.isEmpty()) {
            decodeFormats = EnumSet.noneOf(BarcodeFormat.class);
            // 一维码：商品
            boolean decode1DProduct = true;
            if (decode1DProduct) {
                decodeFormats.addAll(PRODUCT_FORMATS);
            }
            // 一维码：工业
            boolean decode1DIndustrial = true;
            if (decode1DIndustrial) {
                decodeFormats.addAll(INDUSTRIAL_FORMATS);
            }
            // 二维码
            boolean decodeQR = true;
            if (decodeQR) {
                decodeFormats.addAll(QR_CODE_FORMATS);
            }
            // Data Matrix
            boolean decodeDataMatrix = true;
            if (decodeDataMatrix) {
                decodeFormats.addAll(DATA_MATRIX_FORMATS);
            }
        }
        hints.put(DecodeHintType.POSSIBLE_FORMATS, decodeFormats);

        if (characterSet != null) {
            hints.put(DecodeHintType.CHARACTER_SET, characterSet);
        }
        hints.put(DecodeHintType.NEED_RESULT_POINT_CALLBACK,
                resultPointCallback);
        if (isDebug) {
            Log.i(TAG, "Hints: " + hints);
        }
    }

    public Handler getHandler() {
        try {
            handlerInitLatch.await();
        } catch (InterruptedException ie) {
            // continue?
        }
        return handler;
    }

    @Override
    public void run() {
        Looper.prepare();
        handler = new DecodeHandler(mCameraManager, scannerViewHandler, hints);
        handlerInitLatch.countDown();
        Looper.loop();
    }

}
