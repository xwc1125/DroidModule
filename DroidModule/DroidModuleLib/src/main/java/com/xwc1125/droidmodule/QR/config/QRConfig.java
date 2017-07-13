package com.xwc1125.droidmodule.QR.config;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.DecodeHintType;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

/**
 * Class: com.xwc1125.ui.qr.config <br>
 * Description: TODO <br>
 *
 * @author xwc1125 <br>
 * @version V1.0
 * @Copyright: Copyright (c) 2017 <br>
 * @date 2017/6/10  17:23 <br>
 */
public class QRConfig {

    public static boolean isDebug = true;//如果是debug，那么将会打印日志
    public static long AUTO_FOCUS_INTERVAL_MS = 2000L;//自动对焦的时间间隔，毫秒

    public static final int MESSAGE_WHAT_DECODE = 0X123;//二维码解析的message-what
    public static final int MESSAGE_WHAT_QUIT = 0X124;//二维码扫描放弃
    public static final int MESSAGE_WHAT_DECODE_SUCCEEDED = 0X125;//二维码解析成功
    public static final int MESSAGE_WHAT_DECODE_FAILED = 0X126;//二维码解析失败
    public static final int MESSAGE_WHAT_DECODE_SRESTART_PREVIEW = 0X127;//准备进行下一次扫描
    public static final int MESSAGE_WHAT_DECODE_RETURN_SCAN_RESULT = 0X128;
    public static final int MESSAGE_WHAT_DECODE_LAUNCH_PRODUCT_QUERY = 0X129;

    //============================
    public static final Map<DecodeHintType, Object> HINTS = new EnumMap<DecodeHintType, Object>(DecodeHintType.class);

    static {
        List<BarcodeFormat> formats = new ArrayList<BarcodeFormat>();
        formats.add(BarcodeFormat.QR_CODE);
        HINTS.put(DecodeHintType.POSSIBLE_FORMATS, formats);
        HINTS.put(DecodeHintType.CHARACTER_SET, "utf-8");
    }
    //============================

    /**
     * 扫描的颜色
     */
    public static class color {
        public static final int VIEWFINDER_MASK = 0x60000000;
        public static final int RESULT_VIEW = 0xb0000000;
        public static final int VIEWFINDER_LASER = 0xff00ff00;
        public static final int POSSIBLE_RESULT_POINTS = 0xc0ffbd21;
        public static final int RESULT_POINTS = 0xc099cc00;
    }
}
