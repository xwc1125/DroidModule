/**
 * <p>
 * Title: HintUtils.java
 * </p>
 * <p>
 * Description: 提示类
 * </p>
 * <p>
 * 1、toast提示
 * 2、对话框提示
 * </p>
 * @Copyright: Copyright (c) 2016
 * @author zhangqy
 * @date 2016年8月18日 上午11:42:15
 * @version V1.0
 */
package com.xwc1125.droidutils.hint;

import com.xwc1125.droidutils.UtilsConfig;
import com.xwc1125.droidutils.LogUtils;
import com.xwc1125.droidutils.StringUtils;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface.OnKeyListener;
import android.widget.Toast;

/**
 * 
 * <p>
 * Title: HintUtils
 * </p>
 * <p>
 * Description: 提示
 * </p>
 * <p>
 * 
 * </p>
 * 
 * @author zhangqy
 * @date 2016年7月18日下午6:28:40
 * 
 */
public class HintUtils {

	private static final String TAG = HintUtils.class.getName();
	private static final Boolean isDebug = UtilsConfig.isDebug;

	/**
	 * 
	 * <p>
	 * Title: toast
	 * </p>
	 * <p>
	 * Description: 指定内容提示－toast
	 * </p>
	 * <p>
	 * 
	 * </p>
	 * 
	 * @param context
	 * @param msg
	 * 
	 * @author zhangqy
	 * @date 2016年7月18日下午6:30:06
	 */
	public static void toast(Context context, String msg) {
		if (StringUtils.isNotEmpty(msg)) {
			Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
		}
	}

	/**
	 * 
	 * <p>
	 * Title: progressDialog
	 * </p>
	 * <p>
	 * Description: 指定内容提示－progressDialog
	 * </p>
	 * <p>
	 * 
	 * </p>
	 * 
	 * @param context
	 * @param msg
	 * @return
	 * 
	 * @author zhangqy
	 * @date 2016年7月18日下午6:34:53
	 */
	public static ProgressDialog showProgressDialog(Context context,
			final String msg, OnKeyListener listener) {
		ProgressDialog dialog = null;
		try {
			dialog = new ProgressDialog(context);
			dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
			dialog.setMessage(msg);
			dialog.setCancelable(false);
			dialog.setOnKeyListener(listener);
			dialog.show();
		} catch (Exception e) {
			LogUtils.e(TAG, e.getMessage(), isDebug);
		}
		return dialog;
	}

	/**
	 * 
	 * <p>
	 * Title: hideLoadDialog
	 * </p>
	 * <p>
	 * Description: 提示内容隐藏－progressDialog
	 * </p>
	 * <p>
	 * 
	 * </p>
	 * 
	 * @author zhangqy
	 * @date 2016年7月18日下午6:35:57
	 */
	public static void hideProgressDialog(ProgressDialog dialog) {
		try {
			if (dialog != null) {
				dialog.dismiss();
			}
		} catch (Exception e) {
			LogUtils.e(TAG, e.getMessage(), isDebug);
		}
	}

}
