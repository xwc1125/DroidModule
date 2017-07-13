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

package com.xwc1125.droidmodule.QR.camera;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Point;
import android.graphics.Rect;
import android.hardware.Camera;
import android.util.Log;
import android.view.Display;
import android.view.WindowManager;

import com.xwc1125.droidmodule.QR.config.QRConfig;

/**
 * A class which deals with reading, parsing, and setting the camera parameters
 * which are used to configure the camera hardware.
 * <p>
 * 相机辅助类，主要用于设置相机的各类参数
 */
final class CameraConfigurationManager {

    private static final String TAG = "CameraConfiguration";
    private static final int FRONT_LIGHT_MODE_ON = 0;//前端闪光灯开
    private static final int FRONT_LIGHT_MODE_OFF = 1;//前端闪光灯关

    private static boolean isDebug = QRConfig.isDebug;

    // This is bigger than the size of a small screen, which is still supported.
    // The routine
    // below will still select the default (presumably 320x240) size for these.
    // This prevents
    // accidental selection of very low resolution on some devices.
    private static final int MIN_PREVIEW_PIXELS = 480 * 320; // normal screen
    // private static final float MAX_EXPOSURE_COMPENSATION = 1.5f;
    // private static final float MIN_EXPOSURE_COMPENSATION = 0.0f;
    private static final double MAX_ASPECT_DISTORTION = 0.15;
    private static final int AREA_PER_1000 = 400;
    private int cwRotationFromDisplayToCamera = 90;//相机旋转

    private final Context context;
    /**
     * 屏幕分辨率
     */
    private Point screenResolution;

    /**
     * 相机分辨率
     */
    private Point cameraResolution;

    CameraConfigurationManager(Context context) {
        this.context = context;
    }

    /**
     * 计算了屏幕分辨率和当前最适合的相机像素
     * <p>
     * Reads, one time, values from the camera that are needed by the app.
     */
    void initFromCameraParameters(Camera camera) {
        Camera.Parameters parameters = camera.getParameters();
        WindowManager manager = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        Display display = manager.getDefaultDisplay();

        Point theScreenResolution = new Point();
        // sdk8出错
        // display.getSize(theScreenResolution);

        // 改为此方式
        theScreenResolution = getDisplaySize(display);

        screenResolution = theScreenResolution;//屏幕的大小
        cameraResolution = findBestPreviewSizeValue(parameters,
                screenResolution);//相机的大小

        //=============================
//        int displayRotation = display.getRotation();
//        int cwRotationFromNaturalToDisplay;
//        switch (displayRotation) {
//            case Surface.ROTATION_0:
//                cwRotationFromNaturalToDisplay = 0;
//                break;
//            case Surface.ROTATION_90:
//                cwRotationFromNaturalToDisplay = 90;
//                break;
//            case Surface.ROTATION_180:
//                cwRotationFromNaturalToDisplay = 180;
//                break;
//            case Surface.ROTATION_270:
//                cwRotationFromNaturalToDisplay = 270;
//                break;
//            default:
//                // Have seen this return incorrect values like -90
//                if (displayRotation % 90 == 0) {
//                    cwRotationFromNaturalToDisplay = (360 + displayRotation) % 360;
//                } else {
//                    throw new IllegalArgumentException("Bad rotation: "
//                            + displayRotation);
//                }
//        }
//        int cwRotationFromNaturalToCamera = camera.getOrientation();
//
//        if (camera.getFacing() == CameraFacing.FRONT) {
//            cwRotationFromNaturalToCamera = (360 - cwRotationFromNaturalToCamera) % 360;
//        }
//
//        cwRotationFromDisplayToCamera = (360 +
//                cwRotationFromNaturalToCamera - cwRotationFromNaturalToDisplay) % 360;
//
//        if (camera.getFacing() == CameraFacing.FRONT) {
//            cwNeededRotation = (360 - cwRotationFromDisplayToCamera) % 360;
//        } else {
//            cwNeededRotation = cwRotationFromDisplayToCamera;
//        }
        //===========================
    }

    /**
     * 获取显示的大小
     *
     * @param display
     * @return
     */
    @SuppressLint("NewApi")
    private Point getDisplaySize(final Display display) {
        final Point point = new Point();
        try {
            display.getSize(point);
        } catch (NoSuchMethodError ignore) { // Older device
            point.x = display.getWidth();
            point.y = display.getHeight();
        }
        return point;
    }

    /**
     * 读取配置设置相机的对焦模式、闪光灯模式等等
     *
     * @param camera
     * @param safeMode
     */
    void setDesiredCameraParameters(Camera camera, boolean safeMode) {
        Camera.Parameters parameters = camera.getParameters();

        if (parameters == null) {
            if (isDebug) {
                Log.w(TAG,
                        "Device error: no camera parameters are available. Proceeding without configuration.");
            }
            return;
        }

        if (isDebug) {
            Log.i(TAG, "Initial camera parameters: " + parameters.flatten());
        }
        if (safeMode) {
            Log.w(TAG,
                    "In camera config safe mode -- most settings will not be honored");
        }

        // 初始化闪光灯
        initializeTorch(parameters, FRONT_LIGHT_MODE_OFF, safeMode);
        // 默认使用自动对焦
        String focusMode = findSettableValue(
                parameters.getSupportedFocusModes(),
                Camera.Parameters.FOCUS_MODE_AUTO);

        // Maybe selected auto-focus but not available, so fall through here:
        if (!safeMode && focusMode == null) {
            focusMode = findSettableValue(parameters.getSupportedFocusModes(),
                    Camera.Parameters.FOCUS_MODE_MACRO,
                    Camera.Parameters.FOCUS_MODE_EDOF);
        }
        if (focusMode != null) {
            parameters.setFocusMode(focusMode);
        }

        if (!safeMode) {
            // 反色，扫描黑色背景上的白色条码。仅适用于部分设备。
            boolean invertScan = false;
            if (invertScan) {
                if (Camera.Parameters.EFFECT_NEGATIVE.equals(parameters.getColorEffect())) {
                    if (isDebug) {
                        Log.i(TAG, "Negative effect already set");
                    }
                    return;
                } else {
                    String colorMode = findSettableValue(
                            parameters.getSupportedColorEffects(),
                            Camera.Parameters.EFFECT_NEGATIVE);
                    if (colorMode != null) {
                        parameters.setColorEffect(colorMode);
                    }
                }
            }
            // 不进行条形码场景匹配
            boolean barCodeSceneMode = true;
            if (!barCodeSceneMode) {
                if (Camera.Parameters.EFFECT_NEGATIVE.equals(parameters.getColorEffect())) {
                    if (isDebug) {
                        Log.i(TAG, "Negative effect already set");
                    }
                    return;
                } else {
                    String colorMode = findSettableValue(
                            parameters.getSupportedColorEffects(),
                            Camera.Parameters.SCENE_MODE_BARCODE);
                    if (colorMode != null) {
                        parameters.setColorEffect(colorMode);
                    }
                }
            }

            // 不使用距离测量
            boolean disableMetering = true;
            if (!disableMetering) {
                //setVideoStabilization
                if (parameters.isVideoStabilizationSupported()) {
                    if (!parameters.getVideoStabilization()) {
                        //"Enabling video stabilization..."
                        parameters.setVideoStabilization(true);
                    }
                }
                //setFocusAreas
                if (parameters.getMaxNumFocusAreas() > 0) {
                    List<Camera.Area> middleArea = buildMiddleArea(AREA_PER_1000);
                    parameters.setFocusAreas(middleArea);
                }
                //setMetering
                if (parameters.getMaxNumMeteringAreas() > 0) {
                    List<Camera.Area> middleArea = buildMiddleArea(AREA_PER_1000);
                    parameters.setMeteringAreas(middleArea);
                }
            }
        }

        parameters.setPreviewSize(cameraResolution.x, cameraResolution.y);
        camera.setParameters(parameters);

        Camera.Parameters afterParameters = camera.getParameters();
        Camera.Size afterSize = afterParameters.getPreviewSize();
        if (afterSize != null
                && (cameraResolution.x != afterSize.width || cameraResolution.y != afterSize.height)) {
            if (isDebug) {
                Log.w(TAG, "Camera said it supported preview size "
                        + cameraResolution.x + 'x' + cameraResolution.y
                        + ", but after setting it, preview size is "
                        + afterSize.width + 'x' + afterSize.height);
            }
            cameraResolution.x = afterSize.width;
            cameraResolution.y = afterSize.height;
        }

        camera.setDisplayOrientation(cwRotationFromDisplayToCamera);

    }

    private static List<Camera.Area> buildMiddleArea(int areaPer1000) {
        return Collections.singletonList(
                new Camera.Area(new Rect(-areaPer1000, -areaPer1000, areaPer1000, areaPer1000), 1));
    }

    Point getCameraResolution() {
        return cameraResolution;
    }

    Point getScreenResolution() {
        return screenResolution;
    }

    /**
     * 获取闪光灯的状态
     *
     * @param camera
     * @return
     */
    boolean getTorchState(Camera camera) {
        if (camera != null) {
            Camera.Parameters parameters = camera.getParameters();
            if (parameters != null) {
                String flashMode = camera.getParameters().getFlashMode();
                return flashMode != null
                        && (Camera.Parameters.FLASH_MODE_ON.equals(flashMode) || Camera.Parameters.FLASH_MODE_TORCH
                        .equals(flashMode));
            }
        }
        return false;
    }

    /**
     * 设置闪光灯
     *
     * @param camera
     * @param newSetting
     */
    void setTorch(Camera camera, boolean newSetting) {
        Camera.Parameters parameters = camera.getParameters();
        doSetTorch(parameters, newSetting, false);
        camera.setParameters(parameters);
    }

    /**
     * 初始化闪光灯
     *
     * @param parameters
     * @param frontLightMode
     * @param safeMode
     */
    private void initializeTorch(Camera.Parameters parameters,
                                 int frontLightMode, boolean safeMode) {
        boolean currentSetting = frontLightMode == FRONT_LIGHT_MODE_ON;
        doSetTorch(parameters, currentSetting, safeMode);
    }

    /**
     * 设置闪光灯
     *
     * @param parameters
     * @param newSetting
     * @param safeMode
     */
    private void doSetTorch(Camera.Parameters parameters, boolean newSetting,
                            boolean safeMode) {
        String flashMode;
        if (newSetting) {
            flashMode = findSettableValue(parameters.getSupportedFlashModes(),
                    Camera.Parameters.FLASH_MODE_TORCH,
                    Camera.Parameters.FLASH_MODE_ON);
        } else {
            flashMode = findSettableValue(parameters.getSupportedFlashModes(),
                    Camera.Parameters.FLASH_MODE_OFF);
        }
        if (flashMode != null && !flashMode.equals(parameters.getFlashMode())) {
            parameters.setFlashMode(flashMode);
        }
    }

    /**
     * 从相机支持的分辨率中计算出最适合的预览界面尺寸
     *
     * @param parameters
     * @param screenResolution
     * @return
     */
    private Point findBestPreviewSizeValue(Camera.Parameters parameters,
                                           Point screenResolution) {
        List<Camera.Size> rawSupportedSizes = parameters
                .getSupportedPreviewSizes();
        if (rawSupportedSizes == null) {
            if (isDebug) {
                Log.w(TAG,
                        "Device returned no supported preview sizes; using default");
            }
            Camera.Size defaultSize = parameters.getPreviewSize();
            return new Point(defaultSize.width, defaultSize.height);
        }

        // Sort by size, descending
        List<Camera.Size> supportedPreviewSizes = new ArrayList<Camera.Size>(
                rawSupportedSizes);
        Collections.sort(supportedPreviewSizes, new Comparator<Camera.Size>() {
            @Override
            public int compare(Camera.Size a, Camera.Size b) {
                int aPixels = a.height * a.width;
                int bPixels = b.height * b.width;
                if (bPixels < aPixels) {
                    return -1;
                }
                if (bPixels > aPixels) {
                    return 1;
                }
                return 0;
            }
        });


//        if (Log.isLoggable(TAG, Log.INFO)) {
//            StringBuilder previewSizesString = new StringBuilder();
//            for (Camera.Size supportedPreviewSize : supportedPreviewSizes) {
//                previewSizesString.append(supportedPreviewSize.width)
//                        .append('x').append(supportedPreviewSize.height)
//                        .append(' ');
//            }
//            Log.i(TAG, "Supported preview sizes: " + previewSizesString);
//        }

        double screenAspectRatio = (double) screenResolution.x
                / (double) screenResolution.y;

        // Remove sizes that are unsuitable
        Iterator<Camera.Size> it = supportedPreviewSizes.iterator();
        while (it.hasNext()) {
            Camera.Size supportedPreviewSize = it.next();
            int realWidth = supportedPreviewSize.width;
            int realHeight = supportedPreviewSize.height;
            if (realWidth * realHeight < MIN_PREVIEW_PIXELS) {
                it.remove();
                continue;
            }

            boolean isCandidatePortrait = realWidth < realHeight;
            int maybeFlippedWidth = isCandidatePortrait ? realHeight
                    : realWidth;
            int maybeFlippedHeight = isCandidatePortrait ? realWidth
                    : realHeight;

            double aspectRatio = (double) maybeFlippedWidth
                    / (double) maybeFlippedHeight;
            double distortion = Math.abs(aspectRatio - screenAspectRatio);
            if (distortion > MAX_ASPECT_DISTORTION) {
                it.remove();
                continue;
            }

            if (maybeFlippedWidth == screenResolution.x
                    && maybeFlippedHeight == screenResolution.y) {
                Point exactPoint = new Point(realWidth, realHeight);
                if (isDebug) {
                    Log.i(TAG, "Found preview size exactly matching screen size: "
                            + exactPoint);
                }
                return exactPoint;
            }
        }

        // If no exact match, use largest preview size. This was not a great
        // idea on older devices because
        // of the additional computation needed. We're likely to get here on
        // newer Android 4+ devices, where
        // the CPU is much more powerful.
        if (!supportedPreviewSizes.isEmpty()) {
            Camera.Size largestPreview = supportedPreviewSizes.get(0);
            Point largestSize = new Point(largestPreview.width,
                    largestPreview.height);
            if (isDebug) {
                Log.i(TAG, "Using largest suitable preview size: " + largestSize);
            }
            return largestSize;
        }

        // If there is nothing at all suitable, return current preview size
        Camera.Size defaultPreview = parameters.getPreviewSize();
        Point defaultSize = new Point(defaultPreview.width,
                defaultPreview.height);
        if (isDebug) {
            Log.i(TAG, "No suitable preview sizes, using default: " + defaultSize);
        }

        return defaultSize;
    }

    /**
     * 在supportedValues中寻找desiredValues，找不到则返回null
     *
     * @param supportedValues
     * @param desiredValues
     * @return
     */
    private static String findSettableValue(Collection<String> supportedValues,
                                            String... desiredValues) {
        if (isDebug) {
            Log.i(TAG, "Supported values: " + supportedValues);
        }
        String result = null;
        if (supportedValues != null) {
            for (String desiredValue : desiredValues) {
                if (supportedValues.contains(desiredValue)) {
                    result = desiredValue;
                    break;
                }
            }
        }
        if (isDebug) {
            Log.i(TAG, "Settable value: " + result);
        }
        return result;
    }

}
