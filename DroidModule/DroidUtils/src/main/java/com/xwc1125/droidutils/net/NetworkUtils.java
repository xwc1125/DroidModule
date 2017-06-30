package com.xwc1125.droidutils.net;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.telephony.TelephonyManager;

import com.xwc1125.droidutils.UtilsConfig;
import com.xwc1125.droidutils.LogUtils;

/**
 * 
 * <p>
 * Title: NetworkUtils
 * </p>
 * <p>
 * Description: 网络相关工具类
 * </p>
 * <p>
 * 
 * </p>
 * 
 * @author zhangqy
 * @date 2015年7月13日下午3:05:27
 * 
 */
public class NetworkUtils {
	private static final String TAG = NetworkUtils.class.getName();
	private static boolean isDebug = UtilsConfig.isDebug;

	/**
	 * 
	 * <p>
	 * Title: isWifiActive
	 * </p>
	 * <p>
	 * Description: 是否存在wifi网络连接
	 * </p>
	 * <p>
	 * 
	 * </p>
	 * 
	 * @param context
	 * @return
	 * 
	 * @author zhangqy
	 * @date 2015年7月13日下午3:05:35
	 */
	public static boolean isWifiActive(Context context) {
		if (context == null) {
			LogUtils.e(TAG,"context 为空", isDebug);
			return false;
		}
		try {
			ConnectivityManager connectivity = (ConnectivityManager) context
					.getSystemService(Context.CONNECTIVITY_SERVICE);
			if (connectivity != null) {
				NetworkInfo[] info = connectivity.getAllNetworkInfo();
				if (info != null) {
					for (int i = 0; i < info.length; i++) {
						if (info[i].getTypeName().equals("WIFI") && info[i].isConnected()) {
							return true;
						}
					}
				}
			}
		} catch (Exception e) {
			LogUtils.e(TAG, e.getMessage(), isDebug);
		}
		return false;
	}

	/**
	 * 
	 * <p>
	 * Title: isNetActive
	 * </p>
	 * <p>
	 * Description:是否有网络
	 * </p>
	 * <p>
	 * 
	 * </p>
	 * 
	 * @param context
	 * @return
	 * 
	 * @author zhangqy
	 * @date 2015年8月14日下午9:42:09
	 */
	public static boolean isNetActive(Context context) {
		if (context == null) {
			LogUtils.e(TAG, "context 为空", isDebug);
			return false;
		}
		ConnectivityManager connectivityManager = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo info = connectivityManager.getActiveNetworkInfo();
		if (info != null && info.isAvailable() && info.isConnected()) {
			// 有网络
			return true;
		} else {
			// 无网络
			return false;
		}
	}

	/**
	 * 
	 * <p>
	 * Title: NET_TYPE
	 * </p>
	 * <p>
	 * Description: 网络状态
	 * </p>
	 * <p>
	 * 
	 * </p>
	 * 
	 * @author xwc1125
	 * @date May 14, 201610:58:23 PM
	 * 
	 */
	public enum NET_TYPE {
		/**
		 * WIFI：0
		 */
		WIFI {
			@Override
			public String getValue() {
				return "WIFI";
			}

			@Override
			public int getIndex() {
				return 0;
			}
		},
		/**
		 * NET：1
		 */
		NET {
			@Override
			public String getValue() {
				return "NET";
			}

			@Override
			public int getIndex() {
				return 1;
			}
		},
		/**
		 * 未知运营商：-1
		 */
		UNKNOW {
			@Override
			public String getValue() {
				return "UNKNOW";
			}

			@Override
			public int getIndex() {
				return -1;
			}
		};
		public abstract String getValue();

		public abstract int getIndex();
	}

	/**
	 * 
	 * <p>
	 * Title: getNetworkType
	 * </p>
	 * <p>
	 * Description: 获取网络状态
	 * </p>
	 * <p>
	 * 
	 * </p>
	 * 
	 * @tags @param context
	 * @tags @return
	 * 
	 * @author xwc1125
	 * @date 2017年2月14日 下午5:29:33
	 */
	public static String getNetworkType(Context context) {
		String strNetworkType = "";
		try {
			NetworkInfo networkInfo = (NetworkInfo) ((ConnectivityManager) context
					.getSystemService(Context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo();
			if (networkInfo != null && networkInfo.isConnected()) {
				if (networkInfo.getType() == ConnectivityManager.TYPE_WIFI) {
					strNetworkType = "WIFI";
				} else if (networkInfo.getType() == ConnectivityManager.TYPE_MOBILE) {
					String _strSubTypeName = networkInfo.getSubtypeName();

					// TD-SCDMA networkType is 17
					int networkType = networkInfo.getSubtype();
					switch (networkType) {
					case TelephonyManager.NETWORK_TYPE_GPRS:
					case TelephonyManager.NETWORK_TYPE_EDGE:
					case TelephonyManager.NETWORK_TYPE_CDMA:
					case TelephonyManager.NETWORK_TYPE_1xRTT:
					case TelephonyManager.NETWORK_TYPE_IDEN: // api<8 : replace
																// by
																// 11
						strNetworkType = "2G";
						break;
					case TelephonyManager.NETWORK_TYPE_UMTS:
					case TelephonyManager.NETWORK_TYPE_EVDO_0:
					case TelephonyManager.NETWORK_TYPE_EVDO_A:
					case TelephonyManager.NETWORK_TYPE_HSDPA:
					case TelephonyManager.NETWORK_TYPE_HSUPA:
					case TelephonyManager.NETWORK_TYPE_HSPA:
					case TelephonyManager.NETWORK_TYPE_EVDO_B: // api<9 :
																// replace by
																// 14
					case TelephonyManager.NETWORK_TYPE_EHRPD: // api<11 :
																// replace by
																// 12
					case TelephonyManager.NETWORK_TYPE_HSPAP: // api<13 :
																// replace by
																// 15
						strNetworkType = "3G";
						break;
					case TelephonyManager.NETWORK_TYPE_LTE: // api<11 : replace
															// by
															// 13
						strNetworkType = "4G";
						break;
					default:
						// http://baike.baidu.com/item/TD-SCDMA 中国移动 联通 电信
						// 三种3G制式
						if (_strSubTypeName.equalsIgnoreCase("TD-SCDMA") || _strSubTypeName.equalsIgnoreCase("WCDMA")
								|| _strSubTypeName.equalsIgnoreCase("CDMA2000")) {
							strNetworkType = "3G";
						} else {
							strNetworkType = _strSubTypeName;
						}

						break;
					}
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return strNetworkType;
	}
}