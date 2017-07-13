package com.xwc1125.droidutils.net;

import java.io.IOException;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.NetworkInfo.State;

import com.xwc1125.droidutils.UtilsConfig;
import com.xwc1125.droidutils.LogUtils;

public class MobileNetworkUtils {

	private static final String TAG = MobileNetworkUtils.class.getName();
	private static final Boolean isDebug = UtilsConfig.isDebug;

	/**
	 * 
	 * <p>
	 * Title: getNetWorkType
	 * </p>
	 * <p>
	 * Description: 获取网络状态
	 * </p>
	 * <p>
	 * 
	 * </p>
	 * 
	 * @param context
	 * @param openData
	 *            是否打开mms数据链路
	 * @return
	 * 
	 * @author zhangqy
	 * @date 2016年3月15日下午1:18:45
	 */
	public static NetworkUtils.NET_TYPE getNetWorkType(final Context context, ArrayList<String> urls, boolean openData) {
		NetworkUtils.NET_TYPE netWorkType = NetworkUtils.NET_TYPE.UNKNOW;
		if (context == null) {
			return netWorkType;
		}
		// [xwc1125]
		if (urls == null || urls.size() == 0) {
			return netWorkType;
		}
		try {
			ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
			if (openData) {
				try {// 使用TYPE_MOBILE_MMS
					boolean connected = forceMobileConnectionForAddress(context, urls);
					if (connected) {
						State state = manager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE_MMS).getState();
						State state1 = manager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState();
						if (0 == state.compareTo(State.CONNECTED) || 0 == state1.compareTo(State.CONNECTED)) {
							return NetworkUtils.NET_TYPE.NET;
						}
					}
				} catch (Throwable e) {
					LogUtils.e(TAG, e.getMessage(), isDebug);
				}
			}
			NetworkInfo networkInfo = manager.getActiveNetworkInfo();
			if (networkInfo != null && networkInfo.isConnected()) {
				String type = networkInfo.getTypeName();
				if (type.equalsIgnoreCase("MOBILE")) {
					netWorkType = NetworkUtils.NET_TYPE.NET;
				} else if (type.equalsIgnoreCase("WIFI")) {
					netWorkType = NetworkUtils.NET_TYPE.WIFI;
				}
			}
		} catch (Throwable e) {
			LogUtils.e(TAG, e.getMessage(), isDebug);
		}
		return netWorkType;
	}

	/**
	 * 
	 * <p>
	 * Title: unforceMobileConnection
	 * </p>
	 * <p>
	 * Description: 取消使用mms数据链路
	 * </p>
	 * <p>
	 * 
	 * </p>
	 * 
	 * @param context
	 * 
	 * @author zhangqy
	 * @date 2016年3月15日上午11:57:40
	 */
	public static void unforceMobileConnection(Context context) {
		try {
			long startTime = System.currentTimeMillis();
			ConnectivityManager connectivityManager = (ConnectivityManager) context
					.getSystemService(Context.CONNECTIVITY_SERVICE);
			State state = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE_MMS).getState();
			if (0 == state.compareTo(State.CONNECTED) || 0 == state.compareTo(State.CONNECTING)) {// 已连接
				connectivityManager.stopUsingNetworkFeature(ConnectivityManager.TYPE_MOBILE, "enableMMS");
			}
			long interTime = System.currentTimeMillis() - startTime;
			if (isDebug) {
				LogUtils.i(TAG, "关闭强制数据流量的时间间隔：" + interTime, isDebug);
			}
		} catch (Throwable e) {
			LogUtils.e(TAG, e.getMessage(), isDebug);
		}
	}

	/**
	 * 
	 * <p>
	 * Title: forceMobileConnectionForAddress
	 * </p>
	 * <p>
	 * Description:指定的地址使用mms数据链路
	 * </p>
	 * <p>
	 * 
	 * </p>
	 * 
	 * @param context
	 * @param address
	 * @return
	 * 
	 * @author zhangqy
	 * @date 2016年5月24日下午1:32:20
	 */
	private static boolean forceMobileConnectionForAddress(Context context, ArrayList<String> address) {
		boolean resultBool = false;
		if (address == null) {
			return resultBool;
		}
		long startTime = System.currentTimeMillis();
		try {
			ConnectivityManager connectivityManager = (ConnectivityManager) context
					.getSystemService(Context.CONNECTIVITY_SERVICE);
			if (null == connectivityManager) {
				LogUtils.i(TAG, "ConnectivityManager 为null", isDebug);
				return false;
			}
			State mobileState = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState();
			if (0 == mobileState.compareTo(State.CONNECTED) || 0 == mobileState.compareTo(State.CONNECTING)) {
				return true;
			}
			connectivityManager.startUsingNetworkFeature(ConnectivityManager.TYPE_MOBILE, "enableMMS");// 使用TYPE_MOBILE_HIPRI
			ArrayList<Integer> hostAddress = lookupHost(address);// 1621741167
			try {
				// wait some time needed to connection manager for waking up
				// for (int counter = 0; counter < 10; counter++) {
				// State checkState = connectivityManager.getNetworkInfo(
				// ConnectivityManager.TYPE_MOBILE_MMS).getState();
				// if (0 == checkState.compareTo(State.CONNECTED))
				// break;
				// Thread.sleep(1000);
				// }
				for (int counter = 0; counter < 5; counter++) {
					State checkState = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE_MMS)
							.getState();
					if (0 == checkState.compareTo(State.CONNECTED)) {
						break;
					}
					Thread.sleep(500);
				}
			} catch (Throwable e) {
				LogUtils.e(TAG, e.getMessage(), isDebug);
			}
			if (hostAddress != null && hostAddress.size() > 0) {
				for (int i = 0; i < hostAddress.size(); i++) {
					resultBool = connectivityManager.requestRouteToHost(ConnectivityManager.TYPE_MOBILE_MMS,
							hostAddress.get(i));
				}
			}
		} catch (Throwable e) {
			LogUtils.e(TAG, e.getMessage(), isDebug);
		}
		long interTime = System.currentTimeMillis() - startTime;
		if (isDebug) {
			LogUtils.i(TAG, "强制开启数据流量的时间间隔：" + interTime, isDebug);
		}
		if (!resultBool) {
			unforceMobileConnection(context);
		}
		return resultBool;
	}

	public static boolean ping(String url) {
		try {
			url = getHost(url);
		} catch (MalformedURLException e1) {
		}
		long startTime = System.currentTimeMillis();
		boolean result = false;
		Process p;
		try {
			// ping -c 3 -w 100 中 ，-c 是指ping的次数 3是指ping 3次 ，-w 100
			// 以秒为单位指定超时间隔，是指超时时间为100秒
			p = Runtime.getRuntime().exec("ping -c 3 -w 100 " + url);
			int status = p.waitFor();
			long interTime = System.currentTimeMillis() - startTime;
			if (isDebug) {
				LogUtils.i(TAG, "ping的时间间隔：" + interTime + ";地址=" + url + ";status=" + status, isDebug);
			}
			// InputStream input = p.getInputStream();
			// BufferedReader in = new BufferedReader(new
			// InputStreamReader(input));
			// StringBuffer buffer = new StringBuffer();
			// String line = "";
			// while ((line = in.readLine()) != null) {
			// buffer.append(line);
			// }
			// System.out.println("Return ============" + buffer.toString());
			if (status == 0) {
				result = true;
			} else {
				result = false;
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return result;
	}

	private static String getHost(String urlStr) throws MalformedURLException {
		URL url = new URL(urlStr);
		String host = url.getHost();// 获取主机名
		return host;
	}

	/**
	 * 
	 * <p>
	 * Title: lookupHost
	 * </p>
	 * <p>
	 * Description: 地址转换成int
	 * </p>
	 * <p>
	 * 
	 * </p>
	 * 
	 * @param address
	 * @return
	 * 
	 * @author zhangqy
	 * @date 2016年3月14日下午5:51:18
	 */
	private static ArrayList<Integer> lookupHost(ArrayList<String> address) {
		ArrayList<Integer> longHost = null;
		try {
			if (address != null && address.size() > 0) {
				longHost = new ArrayList<Integer>();
				for (int i = 0; i < address.size(); i++) {
					try {
						URL url = new URL(address.get(i));
						String hostname = url.getHost();
						InetAddress inetAddress = InetAddress.getByName(hostname);
						byte[] addrBytes;
						addrBytes = inetAddress.getAddress();
						int addr = ((addrBytes[3] & 0xff) << 24) | ((addrBytes[2] & 0xff) << 16)
								| ((addrBytes[1] & 0xff) << 8) | (addrBytes[0] & 0xff);
						longHost.add(addr);
					} catch (Exception e) {
						LogUtils.e(TAG, e.getMessage(), isDebug);
					}
				}
			}
		} catch (Throwable e) {
			LogUtils.e(TAG, e.getMessage(), isDebug);
		}
		return longHost;
	}
}