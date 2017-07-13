package com.xwc1125.droidmodule.QR.decode;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.TextUtils;
import android.util.Log;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.ChecksumException;
import com.google.zxing.DecodeHintType;
import com.google.zxing.FormatException;
import com.google.zxing.NotFoundException;
import com.google.zxing.RGBLuminanceSource;
import com.google.zxing.Result;
import com.google.zxing.client.result.ParsedResult;
import com.google.zxing.client.result.ResultParser;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.qrcode.QRCodeReader;
import com.xwc1125.droidmodule.QR.config.QRConfig;
import com.xwc1125.droidmodule.QR.OnScannerCompletionListener;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

/**
 * Created by hupei on 2016/7/7.
 */
public final class QRDecode {

    private static final String TAG = QRDecode.class.getName();
    private static boolean isDebug = QRConfig.isDebug;

    private static int reqWidth = 480;//   中间框框的宽
    private static int reqHeight = 800;//   中间框框的高


    public static final Map<DecodeHintType, Object> HINTS = new EnumMap<DecodeHintType, Object>(DecodeHintType.class);

    static {
        List<BarcodeFormat> formats = new ArrayList<BarcodeFormat>();
        formats.add(BarcodeFormat.QR_CODE);
        HINTS.put(DecodeHintType.POSSIBLE_FORMATS, formats);
        HINTS.put(DecodeHintType.CHARACTER_SET, "utf-8");
    }

    private QRDecode() {
    }

    /**
     * 解析二维码图片
     *
     * @param picturePath
     * @param listener
     * @return
     */
    public static void decodeQR(String picturePath, OnScannerCompletionListener listener) {
        try {
            decodeQR(loadBitmap(picturePath, reqWidth, reqHeight), listener);
        } catch (FileNotFoundException e) {
            if (isDebug) {
                Log.e(TAG, e.getMessage());
            }
        }
    }

    //==========edit by xwc1125===================

    /**
     * 扫描图片解析二维码
     *
     * @param path
     * @return
     */
    public Result scanningImage(String path) {
        if (TextUtils.isEmpty(path)) {
            return null;
        }
        // DecodeHintType 和EncodeHintType
        Hashtable<DecodeHintType, String> hints = new Hashtable<DecodeHintType, String>();
        hints.put(DecodeHintType.CHARACTER_SET, "utf-8"); // 设置二维码内容的编码
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true; // 先获取原大小
        Bitmap scanBitmap = BitmapFactory.decodeFile(path, options);
        options.inJustDecodeBounds = false; // 获取新的大小
        int sampleSize = (int) (options.outHeight / (float) 200);

        if (sampleSize <= 0)
            sampleSize = 1;
        options.inSampleSize = sampleSize;
        scanBitmap = BitmapFactory.decodeFile(path, options);

        int width = scanBitmap.getWidth();
        int height = scanBitmap.getHeight();
        int[] pixels = new int[width * height];
        scanBitmap.getPixels(pixels, 0, width, 0, 0, width, height);
        RGBLuminanceSource source = new RGBLuminanceSource(width, height, pixels);

        BinaryBitmap bitmap1 = new BinaryBitmap(new HybridBinarizer(source));
        QRCodeReader reader = new QRCodeReader();
        try {
            return reader.decode(bitmap1, hints);
        } catch (NotFoundException e) {
            if (isDebug) {
                Log.e(TAG, e.getMessage());
            }
        } catch (ChecksumException e) {
            if (isDebug) {
                Log.e(TAG, e.getMessage());
            }
        } catch (FormatException e) {
            if (isDebug) {
                Log.e(TAG, e.getMessage());
            }
        }
        return null;
    }
    //==========edit by xwc1125 end===================

    /**
     * 解析二维码图片
     *
     * @param srcBitmap
     * @param listener
     * @return
     */
    public static void decodeQR(Bitmap srcBitmap, final OnScannerCompletionListener listener) {
        Result result = null;
        if (srcBitmap != null) {
            int width = srcBitmap.getWidth();
            int height = srcBitmap.getHeight();
            int[] pixels = new int[width * height];
            srcBitmap.getPixels(pixels, 0, width, 0, 0, width, height);
            //新建一个RGBLuminanceSource对象
            RGBLuminanceSource source = new RGBLuminanceSource(width, height, pixels);
            //将图片转换成二进制图片
//            BinaryBitmap binaryBitmap = new BinaryBitmap(new GlobalHistogramBinarizer(source));
            //==========edit by xwc1125===================
            BinaryBitmap binaryBitmap = new BinaryBitmap(new HybridBinarizer(source));
            //==========edit by xwc1125===================
            QRCodeReader reader = new QRCodeReader();//初始化解析对象
            try {
                result = reader.decode(binaryBitmap, HINTS);//开始解析
            } catch (NotFoundException e) {
                if (isDebug) {
                    Log.e(TAG, e.getMessage());
                }
            } catch (ChecksumException e) {
                if (isDebug) {
                    Log.e(TAG, e.getMessage());
                }
            } catch (FormatException e) {
                if (isDebug) {
                    Log.e(TAG, e.getMessage());
                }
            }
        }
        if (listener != null) {
            listener.onScannerCompletion(result, parseResult(result), srcBitmap);
        }
    }

    /**
     * 转成目标类型
     *
     * @param rawResult
     * @return
     */
    public static ParsedResult parseResult(Result rawResult) {
        if (rawResult == null) return null;
        return ResultParser.parseResult(rawResult);
    }

    /**
     * 加载图片
     *
     * @param picturePath
     * @param reqWidth    中间框框的宽
     * @param reqHeight   中间框框的高
     * @return
     * @throws FileNotFoundException
     */
    private static Bitmap loadBitmap(String picturePath, int reqWidth, int reqHeight) throws FileNotFoundException {
        BitmapFactory.Options opt = new BitmapFactory.Options();
        opt.inJustDecodeBounds = true;
        Bitmap bitmap = BitmapFactory.decodeFile(picturePath, opt);
        // 获取到这个图片的原始宽度和高度
        int picWidth = opt.outWidth;
        int picHeight = opt.outHeight;
        // 获取画布中间方框的宽度和高度

        // isSampleSize是表示对图片的缩放程度，比如值为2图片的宽度和高度都变为以前的1/2
        opt.inSampleSize = 1;
        // 根据屏的大小和图片大小计算出缩放比例
        if (picWidth > picHeight) {
            if (picWidth > reqWidth)
                opt.inSampleSize = picWidth / reqWidth;
        } else {
            if (picHeight > reqHeight)
                opt.inSampleSize = picHeight / reqHeight;
        }
        // 生成有像素经过缩放了的bitmap
        opt.inJustDecodeBounds = false;
        bitmap = BitmapFactory.decodeFile(picturePath, opt);
        if (bitmap == null) {
            throw new FileNotFoundException("Couldn't open " + picturePath);
        }
        return bitmap;
    }

}
