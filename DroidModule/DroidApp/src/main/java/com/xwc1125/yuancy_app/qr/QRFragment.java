package com.xwc1125.yuancy_app.qr;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.DecodeHintType;
import com.google.zxing.Result;
import com.google.zxing.client.result.ResultParser;
import com.xwc1125.droidmodule.QR.CaptureActivityHandler;
import com.xwc1125.droidmodule.QR.DecodeCompeletLinstener;
import com.xwc1125.droidmodule.QR.camera.CameraManager;
import com.xwc1125.droidmodule.QR.common.BeepManager;
import com.xwc1125.droidmodule.QR.common.FlashLightManager;
import com.xwc1125.droidmodule.QR.config.QRConfig;
import com.xwc1125.droidmodule.QR.view.ViewfinderView;
import com.xwc1125.droidapp.R;
import com.xwc1125.droidmodule.Base.fragment.BaseFragment;

import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.Collection;
import java.util.Map;

/**
 * Class: com.xwc1125.yuancy_app.qr <br>
 * Description: 1.开启camera，在后台独立线程中完成扫描任务；
 * 2.绘制了一个扫描区（viewfinder）来帮助用户将条码置于其中以准确扫描； 3.扫描成功后会将扫描结果展示在界面上。
 *
 * @author xwc1125 <br>
 * @version V1.0
 * @Copyright: Copyright (c) 2017 <br>
 * @date 2017/6/11  16:26 <br>
 */
public class QRFragment extends BaseFragment implements
        SurfaceHolder.Callback {
    private static final String TAG = QRFragment.class.getSimpleName();
    private String title;
    private ViewfinderView viewfinderView;
    private SurfaceView surfaceView;
    /**
     * 声音震动管理器。如果扫描成功后可以播放一段音频，也可以震动提醒，可以通过配置来决定扫描成功后的行为。
     */
    private BeepManager beepManager;
    /**
     * 闪光灯调节器。自动检测环境光线强弱并决定是否开启闪光灯
     */
    private FlashLightManager flashLightManager;
    /**
     * 相机管理
     */
    private CameraManager cameraManager;

    /**
     * 是否有预览
     */
    private boolean hasSurface;

    private Result savedResultToShow;

    /**
     * 【辅助解码的参数(用作MultiFormatReader的参数)】 编码类型，该参数告诉扫描器采用何种编码方式解码，即EAN-13，QR
     * Code等等 对应于DecodeHintType.POSSIBLE_FORMATS类型
     * 参考DecodeThread构造函数中如下代码：hints.put(DecodeHintType.POSSIBLE_FORMATS,
     * decodeFormats);
     */
    private Collection<BarcodeFormat> decodeFormats;

    /**
     * 【辅助解码的参数(用作MultiFormatReader的参数)】 该参数最终会传入MultiFormatReader，
     * 上面的decodeFormats和characterSet最终会先加入到decodeHints中 最终被设置到MultiFormatReader中
     * 参考DecodeHandler构造器中如下代码：multiFormatReader.setHints(hints);
     */
    private Map<DecodeHintType, ?> decodeHints;

    /**
     * 【辅助解码的参数(用作MultiFormatReader的参数)】 字符集，告诉扫描器该以何种字符集进行解码
     * 对应于DecodeHintType.CHARACTER_SET类型
     * 参考DecodeThread构造器如下代码：hints.put(DecodeHintType.CHARACTER_SET,
     * characterSet);
     */
    private String characterSet;

    private Handler mHandler = new MyHandler(getActivity());

    static class MyHandler extends Handler {

        private WeakReference<Activity> activityReference;

        public MyHandler(Activity activity) {
            activityReference = new WeakReference<Activity>(activity);
        }

        @Override
        public void handleMessage(Message msg) {

            switch (msg.what) {
                case QRConfig.MESSAGE_WHAT_DECODE_SUCCEEDED: // 解析图片成功
                    Toast.makeText(activityReference.get(),
                            "解析成功，结果为：" + msg.obj, Toast.LENGTH_SHORT).show();
                    break;

                case QRConfig.MESSAGE_WHAT_DECODE_FAILED:// 解析图片失败
                    Toast.makeText(activityReference.get(), "解析图片失败",
                            Toast.LENGTH_SHORT).show();
                    break;

                default:
                    break;
            }

            super.handleMessage(msg);
        }

    }

    public static QRFragment newInstance(String title) {
        QRFragment fragment = new QRFragment();
        Bundle args = new Bundle();
        args.putString(KEY_BUNDLE_TITLE, title);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected void getBundleExtra() {
        if (getArguments() != null) {
            title = getArguments().getString(KEY_BUNDLE_TITLE);
        }

        hasSurface = false;
    }

    @Override
    protected View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.example_fragment_qr, container, false);
    }

    @Override
    protected void initView(View view) {
        viewfinderView = (ViewfinderView) view.findViewById(R.id.viewfinderView);
        // 摄像头预览功能必须借助SurfaceView，因此也需要在一开始对其进行初始化
        // 如果需要了解SurfaceView的原理
        // 参考:http://blog.csdn.net/luoshengyang/article/details/8661317
        surfaceView = (SurfaceView) view.findViewById(R.id.surfaceView);
    }

    @Override
    protected void initListeners() {
        beepManager = new BeepManager(activity, true, R.raw.beep);
        flashLightManager = new FlashLightManager(activity, FlashLightManager.FrontLightMode.AUTO);
    }

    @Override
    protected void initData() {

    }

    @Override
    public void onResume() {
        super.onResume();

        // 相机初始化的动作需要开启相机并测量屏幕大小，这些操作
        // 不建议放到onCreate中，因为如果在onCreate中加上首次启动展示帮助信息的代码的 话，
        // 会导致扫描窗口的尺寸计算有误的bug
        cameraManager = new CameraManager(activity);
        viewfinderView.setCameraManager(cameraManager);

        SurfaceHolder surfaceHolder = surfaceView.getHolder();
        if (hasSurface) {
            // The activity was paused but not stopped, so the surface still
            // exists. Therefore
            // surfaceCreated() won't be called, so init the camera here.
            initCamera(surfaceHolder);

        } else {
            // 防止sdk8的设备初始化预览异常
            surfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);

            // Install the callback and wait for surfaceCreated() to init the
            // camera.
            surfaceHolder.addCallback(this);
        }

        // 加载声音配置，其实在BeemManager的构造器中也会调用该方法，即在onCreate的时候会调用一次
        beepManager.updatePrefs();
        // 启动闪光灯调节器
        flashLightManager.start(cameraManager);


        decodeFormats = null;
        characterSet = null;
    }


    @Override
    public void onPause() {
        if (handler != null) {
            handler.quitSynchronously();
            handler = null;
        }
        flashLightManager.stop();
        beepManager.close();

        // 关闭摄像头
        cameraManager.closeDriver();
        if (!hasSurface) {
            SurfaceHolder surfaceHolder = surfaceView.getHolder();
            surfaceHolder.removeCallback(this);
        }

        super.onPause();


    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        if (holder == null) {
            Log.e(TAG,
                    "*** WARNING *** surfaceCreated() gave us a null surface!");
        }
        if (!hasSurface) {
            hasSurface = true;
            initCamera(holder);
        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        hasSurface = false;
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {

    }

    private void initCamera(SurfaceHolder surfaceHolder) {
        if (surfaceHolder == null) {
            throw new IllegalStateException("No SurfaceHolder provided");
        }

        if (cameraManager.isOpen()) {
            Log.w(TAG,
                    "initCamera() while already open -- late SurfaceView callback?");
            return;
        }
        try {
            cameraManager.openDriver(surfaceHolder);
            // Creating the handler starts the preview, which can also throw a
            // RuntimeException.
            if (handler == null) {

                handler = new CaptureActivityHandler(viewfinderView, decodeFormats,
                        decodeHints, characterSet, cameraManager, new DecodeCompeletLinstener() {
                    @Override
                    public void handleDecode(Result rawResult, Bitmap barcode, float scaleFactor) {
                        // 把图片画到扫描框
                        viewfinderView.drawResultBitmap(barcode);
                        beepManager.playBeepSoundAndVibrate();
                        Toast.makeText(activity,
                                "识别结果:" + ResultParser.parseResult(rawResult).toString(),
                                Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void drawViewfinder() {
                        viewfinderView.drawViewfinder();
                    }

                    @Override
                    public void onPermissionErr(Exception e) {

                    }

                    @Override
                    public void onPermissionSucc() {

                    }
                });
            }
            decodeOrStoreSavedBitmap(null, null);
        } catch (IOException ioe) {
            Log.w(TAG, ioe);
        } catch (RuntimeException e) {
            // Barcode Scanner has seen crashes in the wild of this variety:
            // java.?lang.?RuntimeException: Fail to connect to camera service
            Log.w(TAG, "Unexpected error initializing camera", e);
        }
    }

    private CaptureActivityHandler handler;

    /**
     * 向CaptureActivityHandler中发送消息，并展示扫描到的图像
     *
     * @param bitmap
     * @param result
     */
    private void decodeOrStoreSavedBitmap(Bitmap bitmap, Result result) {
        // Bitmap isn't used yet -- will be used soon
        if (handler == null) {
            savedResultToShow = result;
        } else {
            if (result != null) {
                savedResultToShow = result;
            }
            if (savedResultToShow != null) {
                Message message = Message.obtain(handler,
                        QRConfig.MESSAGE_WHAT_DECODE_SUCCEEDED, savedResultToShow);
                handler.sendMessage(message);
            }
            savedResultToShow = null;
        }
    }
}
