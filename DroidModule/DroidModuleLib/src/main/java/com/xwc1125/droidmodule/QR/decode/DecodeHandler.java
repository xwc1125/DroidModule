/*
 * Copyright (C) 2010 ZXing authors
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

package com.xwc1125.droidmodule.QR.decode;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;

import com.google.zxing.BinaryBitmap;
import com.google.zxing.DecodeHintType;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.PlanarYUVLuminanceSource;
import com.google.zxing.ReaderException;
import com.google.zxing.Result;
import com.google.zxing.common.HybridBinarizer;
import com.xwc1125.droidmodule.QR.config.QRConfig;
import com.xwc1125.droidmodule.QR.utils.QRUtils;
import com.xwc1125.droidutils.io.FileUtils;
import com.xwc1125.droidmodule.QR.camera.CameraManager;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Map;

/**
 * 二维码解码的handler
 */
final class DecodeHandler extends Handler {

    private static final String TAG = DecodeHandler.class.getSimpleName();
    private static boolean isDebug = QRConfig.isDebug;

    private final CameraManager mCameraManager;
    private final MultiFormatReader multiFormatReader;
    private final Handler mHandler;
    private boolean running = true;//是否正在运行

    DecodeHandler(CameraManager cameraManager, Handler handler, Map<DecodeHintType, Object> hints) {
        multiFormatReader = new MultiFormatReader();
        multiFormatReader.setHints(hints);
        this.mCameraManager = cameraManager;
        this.mHandler = handler;
    }

    @Override
    public void handleMessage(Message message) {
        if (!running) {
            return;
        }
        if (message.what == QRConfig.MESSAGE_WHAT_DECODE) {
            decode((byte[]) message.obj, message.arg1, message.arg2);
        } else if (message.what == QRConfig.MESSAGE_WHAT_QUIT) {
            running = false;
            Looper.myLooper().quit();
        }
    }

    /**
     * 捕捉画面并解码
     * <p>
     * Decode the data within the viewfinder rectangle, and time how long it
     * took. For efficiency, reuse the same reader objects from one decode to
     * the next.
     *
     * @param data   The YUV preview frame.
     * @param width  The width of the preview frame.
     * @param height The height of the preview frame.
     */
    int count=0;
    private void decode(byte[] data, int width, int height) {
        long start = System.currentTimeMillis();
        Result rawResult = null;

        byte[] rotatedData = new byte[data.length];
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++)
                rotatedData[x * height + height - y - 1] = data[x + y * width];
        }
        int tmp = width;
        width = height;
        height = tmp;

        // 构造基于平面的YUV亮度源，即包含二维码区域的数据源
        PlanarYUVLuminanceSource source = mCameraManager
                .buildLuminanceSource(rotatedData, width, height);

        try {
            QRUtils.saveBitmap(source,count+".png", FileUtils.createFolderAuto("/xwc1125/img/").getAbsolutePath());
            count++;
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (source != null) {
            // 构造二值图像比特流，使用HybridBinarizer算法解析数据源
            BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));
            try {
                // 预览界面最终取到的是个bitmap，然后对其进行解码
                // 采用MultiFormatReader解析图像，可以解析多种数据格式
                rawResult = multiFormatReader.decodeWithState(bitmap);
            } catch (ReaderException re) {
                // continue
            } finally {
                multiFormatReader.reset();
            }
        }

        if (rawResult != null) {
            // Don't log the barcode contents for security.
            if (isDebug) {
                long end = System.currentTimeMillis();
                Log.d(TAG, "Found barcode in " + (end - start) + " ms");
            }
            if (mHandler != null) {
                Message message = Message.obtain(mHandler,
                        QRConfig.MESSAGE_WHAT_DECODE_SUCCEEDED, rawResult);
                Bundle bundle = new Bundle();
                bundleThumbnail(source, bundle);
                message.setData(bundle);
                message.sendToTarget();
            }
        } else {
            if (mHandler != null) {
                Message message = Message.obtain(mHandler, QRConfig.MESSAGE_WHAT_DECODE_FAILED);
                message.sendToTarget();
            }
        }
    }

    private static void bundleThumbnail(PlanarYUVLuminanceSource source,
                                        Bundle bundle) {
        int[] pixels = source.renderThumbnail();
        int width = source.getThumbnailWidth();
        int height = source.getThumbnailHeight();
        Bitmap bitmap = Bitmap.createBitmap(pixels, 0, width, width, height,
                Bitmap.Config.ARGB_8888);
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 50, out);
        bundle.putByteArray(DecodeThread.BARCODE_BITMAP, out.toByteArray());
        bundle.putFloat(DecodeThread.BARCODE_SCALED_FACTOR, (float) width
                / source.getWidth());
    }

}
