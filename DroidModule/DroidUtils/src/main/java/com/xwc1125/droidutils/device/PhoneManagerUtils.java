/**
 * <p>
 * Title: PhoneManagerUtils.java
 * </p>
 * <p>
 * Description: sim卡信息获取
 * </p>
 * <p>
 * 
 * </p>
 * @Copyright: Copyright (c) 2016
 * @author xwc1125
 * @date 2016年8月18日 上午9:29:45
 * @version V1.0
 */
package com.xwc1125.droidutils.device;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.telephony.TelephonyManager;

import com.xwc1125.droidutils.UtilsConfig;
import com.xwc1125.droidutils.app.AppUtils;
import com.xwc1125.droidutils.device.entity.KInfo;
import com.xwc1125.droidutils.device.entity.PInfo;
import com.xwc1125.droidutils.LogUtils;
import com.xwc1125.droidutils.StringUtils;

/**
 * 
 * <p>
 * Title: PhoneManagerUtils
 * </p>
 * <p>
 * Description: 手机卡相关处理
 * </p>
 * <p>
 * 
 * </p>
 * 
 * @author xwc1125
 * @date 2015年7月14日下午4:57:42
 * 
 */
public class PhoneManagerUtils  {

	private static final String TAG = PhoneManagerUtils.class.getName();
	public static boolean isDebug = UtilsConfig.isDebug;
	public static String mMobile = null;

	/**
	 * 
	 * <p>
	 * Title: getPhoneInfo
	 * </p>
	 * <p>
	 * Description: 获取手机信息
	 * </p>
	 * <p>
	 * 
	 * </p>
	 * 
	 * @return
	 * 
	 * @author xwc1125
	 * @date 2016年3月18日下午3:10:50
	 */
	public static PInfo getPhoneInfo(Context context) {
		PInfo phoneInfo = null;
		try {
			PInfo supportInfo = getSimInfoOnSupport(context);
			int api = AppUtils.getAndroidSDKVersion(context);
			if (api >= 21) {// android 5.0 以上
				phoneInfo = getSimInfoOnLollipop(context);
			} else {
				phoneInfo = getSimInfoOnKitKat(context);
			}
			// || phoneInfo.getSmis() == null || phoneInfo.getSmis().size() == 0
			if (phoneInfo == null) {
				// 双卡获取失败返回单卡信息
				return supportInfo;
			} else if (supportInfo != null) {// 不同方式获取的信息合并
				// 合并 imei
				ArrayList<String> imeiList = phoneInfo.getIes();
				ArrayList<String> imeiListSupport = supportInfo.getIes();
				if (imeiListSupport != null && imeiListSupport.size() > 0) {
					if (imeiList != null) {
						String imei = imeiListSupport.get(0);
						boolean isSet = true;
						for (int i = 0; i < imeiList.size(); i++) {
							String ie = imeiList.get(i);
							imeiListSupport.get(0);
							if (imei.equals(ie)) {
								isSet = false;
								break;
							}
							if (isSet) {
								imeiList.add(imei);
							}
						}
					} else {
						imeiList = imeiListSupport;
					}
				}
				phoneInfo.setIes(imeiList);
				// 合并 simInfo
				ArrayList<KInfo> simInfos = phoneInfo.getSmis();
				if (simInfos != null) {
					ArrayList<KInfo> simInfo = supportInfo.getSmis();
					if (simInfo != null && simInfo.size() > 0) {
						simInfos.add(simInfo.get(0));
					}
				} else {
					simInfos = supportInfo.getSmis();
				}
				phoneInfo.setSmis(simInfos);
				// ma
				if (StringUtils.isEmpty(phoneInfo.getMa()) && StringUtils.isNotEmpty(supportInfo.getMa())) {
					phoneInfo.setMa(supportInfo.getMa());
				}
			}
		} catch (Exception e) {
			LogUtils.e(TAG, e.getMessage(), isDebug);
		}
		return phoneInfo;
	}

	/**
	 * 
	 * <p>
	 * Title: getSimInfoOnLollipop
	 * </p>
	 * <p>
	 * Description: android 5.0及其以上获取卡信息
	 * </p>
	 * <p>
	 * subId ：从1开始，数据库主键 有多少条数据，就说明保存了多少个sim卡的信息； slotId ：从0开始，最大为卡槽数
	 * </p>
	 * 
	 * @param context
	 * @return
	 * 
	 * @author xwc1125
	 * @date 2016年3月28日下午3:07:25
	 */
	@SuppressWarnings("unchecked")
	public static PInfo getSimInfoOnLollipop(Context context) {
		long startTime = System.currentTimeMillis();
		PInfo phoneInfo = null;
		try {
			Class<?> subInfoClass = Class.forName("android.telephony.SubscriptionInfo");
			// getIccId
			Method getIccId = subInfoClass.getDeclaredMethod("getIccId");
			getIccId.setAccessible(true);
			// getSubscriptionId
			Method getSubscriptionId = subInfoClass.getDeclaredMethod("getSubscriptionId");
			getSubscriptionId.setAccessible(true);
			// getNumber
			Method getNumber = subInfoClass.getDeclaredMethod("getNumber");
			getNumber.setAccessible(true);
			// getSimSlotIndex
			Method getSimSlotIndex = subInfoClass.getDeclaredMethod("getSimSlotIndex");
			getSimSlotIndex.setAccessible(true);
			// getCarrierName
			Method getCarrierName = subInfoClass.getDeclaredMethod("getCarrierName");
			getCarrierName.setAccessible(true);
			phoneInfo = new PInfo();// 创建PhoneInfo对象
			ArrayList<String> imeiList = new ArrayList<String>();// imeiList
			ArrayList<KInfo> simList = new ArrayList<KInfo>();// simInfoList
			List<Object> activeSubList = (List<Object>) invokeOnSubscriptionManager(context,
					"getActiveSubscriptionInfoList", false, null, null);
			if (activeSubList != null) {
				for (int i = 0; i < activeSubList.size(); i++) {
					Object active = activeSubList.get(i);
					KInfo simInfo = new KInfo();
					simInfo.setT(2);
					int subId = (Integer) getSubscriptionId.invoke(active);// subId
					int slotId = (Integer) getSimSlotIndex.invoke(active);// slotid
					simInfo.setSid(slotId);
					String iccid = (String) getIccId.invoke(active);// iccid
					if (StringUtils.isNotEmpty(iccid)) {
						simInfo.setIc(iccid);
					}
					String cn = (String) getCarrierName.invoke(active);// carrierName-中国联通4G
					if (StringUtils.isNotEmpty(cn)) {
						simInfo.setCn(cn);
					}
					String number = (String) getNumber.invoke(active);// number-18518437838
					if (StringUtils.isNotEmpty(number)) {
						simInfo.setM(number);
					}
					String imei = null;
					imei = (String) invokeOnTelephonyManager(context, "getDeviceId", false,
							new Class<?>[] { int.class }, new Object[] { slotId });// imei
					if (StringUtils.isEmpty(imei)) {
						imei = (String) invokeOnTelephonyManager(context, "getDeviceId", false, null, null);
					}
					if (StringUtils.isNotEmpty(imei)) {
						imeiList.add(imei);
					}
					String imsi = null;
					imsi = (String) invokeOnTelephonyManager(context, "getSubscriberId", false,
							new Class<?>[] { int.class }, new Object[] { subId });// imsi
					if (StringUtils.isEmpty(imsi)) {
						imsi = (String) invokeOnTelephonyManager(context, "getSubscriberId", false, null, null);
					}
					if (StringUtils.isNotEmpty(imsi)) {
						simInfo.setIs(imsi);
					}
					int defalutDataSubId = (Integer) invokeOnSubscriptionManager(context, "getDefaultDataSubId", true,
							null, null);
					if (subId == defalutDataSubId) {
						simInfo.setIdfd(true);// 首选该卡使用数量流量
					}
					try {
						int defalutSmsSubId = (Integer) invokeOnSubscriptionManager(context, "getDefaultSmsSubId", true,
								null, null);
						if (subId == defalutSmsSubId) {
							simInfo.setIdfs(true);// 首选该卡发送短信
						}
					} catch (Throwable e) {
					}
					simList.add(simInfo);
				}
			}
			String macAddress = getMacAddress(context);
			if (StringUtils.isNotEmpty(macAddress)) {
				phoneInfo.setMa(macAddress);
			}
			phoneInfo.setIes(imeiList);
			phoneInfo.setSmis(simList);
		} catch (Throwable e) {
			LogUtils.e(TAG, e.getMessage(), isDebug);
		}
		if (isDebug) {
			LogUtils.i(TAG, "android5.1获取sim卡信息耗时：" + (System.currentTimeMillis() - startTime), isDebug);
		}
		return phoneInfo;
	}

	/**
	 * 
	 * <p>
	 * Title: getSimInfoOnKitKat
	 * </p>
	 * <p>
	 * Description: android 5.0以下获取sim信息
	 * </p>
	 * <p>
	 * 
	 * </p>
	 * 
	 * @param context
	 * @return
	 * 
	 * @author xwc1125
	 * @date 2016年3月18日下午3:33:41
	 */
	private static PInfo getSimInfoOnKitKat(Context context) {
		long startTime = System.currentTimeMillis();
		PInfo phoneInfo = getGaoTongDoubleInfo(context);
		if (isDebug) {
			if (phoneInfo != null) {
				LogUtils.i(TAG, "高通获取sim卡信息：" + phoneInfo.toString() + (System.currentTimeMillis() - startTime),
						isDebug);
			} else {
				LogUtils.i(TAG, "高通获取sim卡信息：" + (System.currentTimeMillis() - startTime), isDebug);
			}
		}
		if (phoneInfo == null) {
			phoneInfo = getMtkDoubleInfo(context);
			if (isDebug) {
				if (phoneInfo != null) {
					LogUtils.i(TAG, "联发科获取sim卡信息：" + phoneInfo.toString() + (System.currentTimeMillis() - startTime),
							isDebug);
				} else {
					LogUtils.i(TAG, "联发科获取sim卡信息：" + (System.currentTimeMillis() - startTime), isDebug);
				}
			}
		}
		try {
			Class<?> tm = Class.forName("android.telephony.TelephonyManager");
			Constructor<?> constructor = tm.getDeclaredConstructor(Context.class);
			Object manager = constructor.newInstance(context);
			Method method = tm.getDeclaredMethod("getSubscriberInfo");
			method.setAccessible(true);
			Object subInfo = method.invoke(manager);
			Class<?> is = subInfo.getClass();
			// getIccSerialNumber
			Method getIccSerialNumber = is.getDeclaredMethod("getIccSerialNumber");
			getIccSerialNumber.setAccessible(true);
			// getSubscriberId
			Method getSubscriberId = is.getDeclaredMethod("getSubscriberId");
			getSubscriberId.setAccessible(true);
			// getDeviceId
			Method getDeviceId = is.getDeclaredMethod("getDeviceId");
			getDeviceId.setAccessible(true);
			// getLine1Number
			Method getLine1Number = is.getDeclaredMethod("getLine1Number");
			getLine1Number.setAccessible(true);
			// 获取iccid
			String iccid = (String) getIccSerialNumber.invoke(subInfo);
			// 获取imsi
			String imsi = (String) getSubscriberId.invoke(subInfo);
			// 获取imei
			String imei = (String) getDeviceId.invoke(subInfo);

			// 获取手机号
			String mobile = (String) getLine1Number.invoke(subInfo);
			KInfo simInfo = new KInfo();
			simInfo.setSid(0);
			simInfo.setT(1);
			simInfo.setM(mobile);
			simInfo.setIc(iccid);
			simInfo.setIs(imsi);
			if (phoneInfo != null) {
				if (StringUtils.isNotEmpty(imei)) {
					boolean isSet = true;
					ArrayList<String> imeiList = phoneInfo.getIes();
					for (int i = 0; i < imeiList.size(); i++) {
						String ie = imeiList.get(i);
						if (imei.equals(ie)) {
							isSet = false;
							break;
						}
						if (isSet) {
							imeiList.add(imei);
						}
					}
					ArrayList<KInfo> simInfos = phoneInfo.getSmis();
					simInfos.add(simInfo);
				}
			} else {
				ArrayList<KInfo> simInfos = new ArrayList<KInfo>();
				simInfos.add(simInfo);
				ArrayList<String> imeiList = new ArrayList<String>();
				if (StringUtils.isNotEmpty(imei)) {
					imeiList.add(imei);
				}
				phoneInfo = new PInfo();
				phoneInfo.setIes(imeiList);
				phoneInfo.setSmis(simInfos);
			}
		} catch (Exception e) {
			LogUtils.e(TAG, e.getMessage(), isDebug);
		}
		if (isDebug) {
			LogUtils.i(TAG, "获取sim卡信息结束：" + (System.currentTimeMillis() - startTime), isDebug);
		}
		return phoneInfo;
	}

	/**
	 * 
	 * <p>
	 * Title: getSimInfoOnSupport
	 * </p>
	 * <p>
	 * Description: 兼容的手机卡信息获取方法
	 * </p>
	 * <p>
	 * 
	 * </p>
	 * 
	 * @param context
	 * @return
	 * 
	 * @author xwc1125
	 * @date 2016年3月28日下午3:25:57
	 */
	private static PInfo getSimInfoOnSupport(Context context) {
		long startTime = System.currentTimeMillis();
		PInfo phoneInfo = null;
		try {
			TelephonyManager manager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
			Class<?> telephonyClass = Class.forName(manager.getClass().getName());
			Object iPhoneSubInfo = null;
			Method getDeviceId = null;
			Method getSubscriberId = null;
			Method getLine1Number = null;
			Method getIccSerialNumber = null;
			boolean flag = false;
			try {// 深层反射
					// getSubscriberInfo
				Method getSubscriberInfo = telephonyClass.getDeclaredMethod("getSubscriberInfo");
				getSubscriberInfo.setAccessible(true);
				iPhoneSubInfo = getSubscriberInfo.invoke(manager);
				Class<?> iphoneSubClass = Class.forName("com.android.internal.telephony.IPhoneSubInfo");
				// getDeviceId
				getDeviceId = iphoneSubClass.getDeclaredMethod("getDeviceId");
				getDeviceId.setAccessible(true);
				// getSubscriberId
				getSubscriberId = iphoneSubClass.getDeclaredMethod("getSubscriberId");
				getSubscriberId.setAccessible(true);
				// getLine1Number
				getLine1Number = iphoneSubClass.getDeclaredMethod("getLine1Number");
				getLine1Number.setAccessible(true);
				// getIccSerialNumber
				getIccSerialNumber = iphoneSubClass.getDeclaredMethod("getIccSerialNumber");
				getIccSerialNumber.setAccessible(true);
				flag = true;
			} catch (Exception e) {
				// getDeviceId
				getDeviceId = telephonyClass.getDeclaredMethod("getDeviceId");
				getDeviceId.setAccessible(true);
				// getSubscriberId
				getSubscriberId = telephonyClass.getDeclaredMethod("getSubscriberId");
				getSubscriberId.setAccessible(true);
				// getLine1Number
				getLine1Number = telephonyClass.getDeclaredMethod("getLine1Number");
				getLine1Number.setAccessible(true);
				// getSimSerialNumber
				getIccSerialNumber = telephonyClass.getDeclaredMethod("getSimSerialNumber");
				getIccSerialNumber.setAccessible(true);
			}
			String iccid = null;
			String imsi = null;
			String imei = null;
			String mobile = null;
			if (flag) {
				iccid = (String) getIccSerialNumber.invoke(iPhoneSubInfo);
				imsi = (String) getSubscriberId.invoke(iPhoneSubInfo);
				imei = (String) getDeviceId.invoke(iPhoneSubInfo);
				mobile = (String) getLine1Number.invoke(iPhoneSubInfo);
			} else {
				iccid = (String) getIccSerialNumber.invoke(manager);
				imsi = (String) getSubscriberId.invoke(manager);
				imei = (String) getDeviceId.invoke(manager);
				mobile = (String) getLine1Number.invoke(manager);
			}
			KInfo simInfo = new KInfo();// 创建SimInfo对象
			simInfo.setSid(0);
			simInfo.setT(1);
			simInfo.setM(mobile);
			simInfo.setIc(iccid);
			simInfo.setIs(imsi);
			ArrayList<String> imeiList = new ArrayList<String>();// imeiList
			imeiList.add(imei);
			ArrayList<KInfo> simList = new ArrayList<KInfo>();// simInfoList
			simList.add(simInfo);
			phoneInfo = new PInfo();// 创建PhoneInfo对象
			String macAddress = getMacAddress(context);
			if (StringUtils.isNotEmpty(macAddress)) {
				phoneInfo.setMa(macAddress);
			}
			phoneInfo.setIes(imeiList);
			phoneInfo.setSmis(simList);
		} catch (Exception e) {
			LogUtils.e(TAG, e.getMessage(), isDebug);
		}
		if (isDebug) {
			LogUtils.i(TAG, "应用层获取sim卡信息耗时：" + (System.currentTimeMillis() - startTime), isDebug);
		}
		return phoneInfo;
	}

	/**
	 * 
	 * <p>
	 * Title: getInvokeOnTeleManager
	 * </p>
	 * <p>
	 * Description: TelephonyManager类方法调用
	 * </p>
	 * <p>
	 * 
	 * </p>
	 * 
	 * @param context
	 * @param methodName
	 *            方法名称
	 * @param isStatic
	 *            是否为静态方法
	 * @param classes
	 *            方法参数类型
	 * @param params
	 *            方法参数
	 * @return 方法返回结果
	 * 
	 * @author xwc1125
	 * @date 2016年3月28日下午2:16:21
	 */
	private static Object invokeOnTelephonyManager(Context context, String methodName, Boolean isStatic,
			Class<?>[] classes, Object[] params) {
		Object result = null;
		try {
			TelephonyManager manager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
			Class<?> telephonyClass = Class.forName(manager.getClass().getName());
			Method method = telephonyClass.getDeclaredMethod(methodName, classes);
			method.setAccessible(true);
			if (isStatic) {
				result = method.invoke(null, params);
			} else {
				result = method.invoke(manager, params);
			}
		} catch (Exception e) {
			LogUtils.e(TAG, e.getMessage(), isDebug);
		}
		return result;
	}

	/**
	 * 
	 * <p>
	 * Title: invokeSubscriptionManager
	 * </p>
	 * <p>
	 * Description: SubscriptionManager类方法调用
	 * </p>
	 * <p>
	 * 
	 * </p>
	 * 
	 * @param context
	 * @param methodName
	 *            方法名称
	 * @param isStatic
	 *            是否是静态方法
	 * @param classes
	 *            方法参数类型
	 * @param params
	 *            方法参数
	 * @return 方法调用结果
	 * 
	 * @author xwc1125
	 * @date 2016年3月28日下午2:20:00
	 */
	private static Object invokeOnSubscriptionManager(Context context, String methodName, Boolean isStatic,
			Class<?>[] classes, Object[] params) {
		Object result = null;
		try {
			Class<?> subManagerClass = Class.forName("android.telephony.SubscriptionManager");
			Constructor<?> constructor = subManagerClass.getConstructor(Class.forName("android.content.Context"));
			Object subManager = constructor.newInstance(context);
			Method method = subManagerClass.getDeclaredMethod(methodName, classes);
			method.setAccessible(true);
			if (isStatic) {
				result = method.invoke(null, params);
			} else {
				result = method.invoke(subManager, params);
			}
		} catch (Exception e) {
			LogUtils.e(TAG, e.getMessage(), isDebug);
		}
		return result;
	}

	/**
	 * 
	 * <p>
	 * Title: getGaoTongDoubleInfo
	 * </p>
	 * <p>
	 * Description: 双卡 高通平台信息
	 * </p>
	 * <p>
	 * 
	 * </p>
	 * 
	 * @param mContext
	 * @return
	 * 
	 * @author xwc1125
	 * @date 2015年7月14日下午5:25:38
	 */
	private static PInfo getGaoTongDoubleInfo(Context mContext) {
		PInfo phoneInfo = null;
		try {
			Class<?> cx = Class.forName("android.telephony.MSimTelephonyManager");
			if (cx != null) {
				Object obj = mContext.getSystemService("phone_msim");
				int simId_1 = 0;
				int simId_2 = 1;
				// getSubscriberId
				Method getSubscriberId = cx.getDeclaredMethod("getSubscriberId", int.class);
				getSubscriberId.setAccessible(true);
				// getDeviceId
				Method getDeviceId = cx.getDeclaredMethod("getDeviceId", int.class);
				getDeviceId.setAccessible(true);
				// getSimSerialNumber
				Method getSimSerialNumber = cx.getDeclaredMethod("getSimSerialNumber", int.class);
				getSimSerialNumber.setAccessible(true);
				// getLine1Number
				Method getLine1Number = cx.getDeclaredMethod("getLine1Number", int.class);
				getLine1Number.setAccessible(true);
				// getPreferredDataSubscription
				Method getPreferredDataSubscription = cx.getDeclaredMethod("getPreferredDataSubscription");
				getPreferredDataSubscription.setAccessible(true);
				// 获取imsi
				String imsi1 = (String) getSubscriberId.invoke(obj, simId_1);
				String imsi2 = (String) getSubscriberId.invoke(obj, simId_2);
				// 获取imei
				String imei1 = (String) getDeviceId.invoke(obj, simId_1);
				String imei2 = (String) getDeviceId.invoke(obj, simId_2);
				// 获取iccid
				String iccid1 = (String) getSimSerialNumber.invoke(obj, simId_1);
				String iccid2 = (String) getSimSerialNumber.invoke(obj, simId_2);
				// 获取mobile
				String mobile1 = (String) getLine1Number.invoke(obj, simId_1);
				String mobile2 = (String) getLine1Number.invoke(obj, simId_2);
				// 获取默认的数据网络
				int soltId = (Integer) getPreferredDataSubscription.invoke(obj);
				ArrayList<String> imeiList = new ArrayList<String>();
				ArrayList<KInfo> simList = new ArrayList<KInfo>();
				if (StringUtils.isNotEmpty(imei1)) {
					imeiList.add(imei1);
				}
				if (StringUtils.isNotEmpty(imei2)) {
					imeiList.add(imei2);
				}
				KInfo simInfo1 = new KInfo();
				simInfo1.setSid(0);
				simInfo1.setT(0);
				simInfo1.setIc(iccid1);
				simInfo1.setIs(imsi1);
				simInfo1.setM(mobile1);
				if (soltId == 0) {
					simInfo1.setIdfd(true);
				}
				simList.add(simInfo1);
				KInfo simInfo2 = new KInfo();
				simInfo2.setSid(1);
				simInfo2.setT(0);
				simInfo2.setIc(iccid2);
				simInfo2.setIs(imsi2);
				simInfo2.setM(mobile2);
				if (soltId != 0) {
					simInfo2.setIdfd(true);
				}
				simList.add(simInfo2);
				phoneInfo = new PInfo();
				phoneInfo.setMa(getMacAddress(mContext));
				phoneInfo.setIes(imeiList);
				phoneInfo.setSmis(simList);
			}
		} catch (Exception e) {
			LogUtils.e(TAG, e.getMessage(), isDebug);
		}
		return phoneInfo;
	}

	/**
	 * 
	 * <p>
	 * Title: getMtkDoubleInfo
	 * </p>
	 * <p>
	 * Description: 双卡 联发科（MTK）平台 信息
	 * </p>
	 * <p>
	 * 
	 * </p>
	 * 
	 * @param mContext
	 * @return
	 * 
	 * @author xwc1125
	 * @date 2015年7月14日下午5:26:34
	 */
	private static PInfo getMtkDoubleInfo(Context mContext) {
		PInfo phoneInfo = null;
		try {
			TelephonyManager tm = (TelephonyManager) mContext.getSystemService(Context.TELEPHONY_SERVICE);
			if (tm != null) {
				Class<?> c = Class.forName("com.android.internal.telephony.Phone");
				if (c != null) {
					int simId_1 = 0;
					int simId_2 = 0;
					Field fields1 = c.getField("GEMINI_SIM_1");
					Field fields2 = c.getField("GEMINI_SIM_2");
					if (fields1 != null) {
						fields1.setAccessible(true);
						simId_1 = (Integer) fields1.get(null);
					}
					if (fields2 != null) {
						fields2.setAccessible(true);
						simId_2 = (Integer) fields2.get(null);
					}
					// getSubscriberIdGemini
					Method getSubscriberIdGemini = TelephonyManager.class.getMethod("getSubscriberIdGemini", int.class);
					getSubscriberIdGemini.setAccessible(true);
					// getDeviceIdGemini
					Method getDeviceIdGemini = TelephonyManager.class.getDeclaredMethod("getDeviceIdGemini", int.class);
					getDeviceIdGemini.setAccessible(true);
					// getSimSerialNumberGemini
					Method getSimSerialNumberGemini = TelephonyManager.class
							.getDeclaredMethod("getSimSerialNumberGemini", int.class);
					getSimSerialNumberGemini.setAccessible(true);
					// getLine1NumberGemini
					Method getLine1NumberGemini = TelephonyManager.class.getDeclaredMethod("getLine1NumberGemini",
							int.class);
					getLine1NumberGemini.setAccessible(true);
					// 获取imsi
					String imsi1 = (String) getSubscriberIdGemini.invoke(tm, simId_1);
					String imsi2 = (String) getSubscriberIdGemini.invoke(tm, simId_2);
					// 获取imei
					String imei1 = (String) getDeviceIdGemini.invoke(tm, simId_1);
					String imei2 = (String) getDeviceIdGemini.invoke(tm, simId_2);
					// 获取iccid
					String iccid1 = (String) getSimSerialNumberGemini.invoke(tm, simId_1);
					String iccid2 = (String) getSimSerialNumberGemini.invoke(tm, simId_2);
					// 获取mobile
					String mobile1 = (String) getLine1NumberGemini.invoke(tm, simId_1);
					String mobile2 = (String) getLine1NumberGemini.invoke(tm, simId_2);
					ArrayList<String> imeiList = new ArrayList<String>();
					ArrayList<KInfo> simList = new ArrayList<KInfo>();
					if (StringUtils.isNotEmpty(imei1)) {
						imeiList.add(imei1);
					}
					if (StringUtils.isNotEmpty(imei2)) {
						imeiList.add(imei2);
					}
					KInfo simInfo1 = new KInfo();
					simInfo1.setSid(0);
					simInfo1.setT(0);
					simInfo1.setIc(iccid1);
					simInfo1.setIs(imsi1);
					simInfo1.setM(mobile1);
					simList.add(simInfo1);
					KInfo simInfo2 = new KInfo();
					simInfo2.setSid(1);
					simInfo2.setT(0);
					simInfo2.setIc(iccid2);
					simInfo2.setIs(imsi2);
					simInfo2.setM(mobile2);
					simList.add(simInfo2);
					phoneInfo = new PInfo();
					phoneInfo.setMa(getMacAddress(mContext));
					phoneInfo.setIes(imeiList);
					phoneInfo.setSmis(simList);
				}
			}
		} catch (Exception e) {
			LogUtils.e(TAG, e.getMessage(), isDebug);
		}
		return phoneInfo;
	}

	/**
	 * 
	 * <p>
	 * Title: getMacAddress
	 * </p>
	 * <p>
	 * Description: 获取mac地址
	 * </p>
	 * <p>
	 * 
	 * </p>
	 * 
	 * @param context
	 * @return
	 * 
	 * @author xwc1125
	 * @date 2016年3月15日下午4:10:50
	 */
	private static String getMacAddress(Context context) {
		WifiManager wifi = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
		WifiInfo info = wifi.getConnectionInfo();
		String macAddress = info.getMacAddress();
		if (StringUtils.isNotEmpty(macAddress)) {
			return macAddress.replaceAll(":", "");
		}
		return null;
	}

	/**
	 * 
	 * <p>
	 * Title: isPhoneDevice
	 * </p>
	 * <p>
	 * Description: 通过是否能打电话判断是否是手机
	 * </p>
	 * <p>
	 * 
	 * </p>
	 * 
	 * @tags @param context
	 * @tags @return
	 * 
	 * @author xwc1125
	 * @date 2016-2-16下午4:11:43
	 */
	public static boolean isPhoneDevice(Context context) {
		TelephonyManager telephony = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
		int type = telephony.getPhoneType();
		if (type == TelephonyManager.PHONE_TYPE_NONE) {
			return false;
		} else {
			return true;
		}
	}

	/**
	 * 
	 * <p>
	 * Title: getImsi
	 * </p>
	 * <p>
	 * Description: 获取imsi
	 * </p>
	 * <p>
	 * 
	 * </p>
	 * 
	 * @param context
	 * @return
	 * 
	 * @author xwc1125
	 * @date 2016年4月11日上午9:39:48
	 */
	public static String getImsi(Context context) {
		String imsi = (String) invokeOnTelephonyManager(context, "getSubscriberId", false, null, null);
		return imsi;
	}

	/**
	 * 
	 * <p>
	 * Title: getImei
	 * </p>
	 * <p>
	 * Description: 获取imei
	 * </p>
	 * <p>
	 * 
	 * </p>
	 * 
	 * @param context
	 * @return
	 * 
	 * @author xwc1125
	 * @date 2016年9月13日 下午2:12:42
	 */
	public static String getImei(Context context) {
		String imei = (String) invokeOnTelephonyManager(context, "getDeviceId", false, null, null);
		return imei;
	}

	/**
	 * 
	 * <p>
	 * Title: getMNC
	 * </p>
	 * <p>
	 * Description: 获取mnc
	 * </p>
	 * <p>
	 * 
	 * </p>
	 * 
	 * @return
	 * 
	 * @author xwc1125
	 * @date 2016年3月31日下午2:10:47
	 */
	public static String getMNC(Context context) {
		String imsi = (String) invokeOnTelephonyManager(context, "getSubscriberId", false, null, null);
		String mnc = null;
		if (StringUtils.isEmpty(imsi)) {
			return mnc;
		}
		mnc = imsi.substring(3, 5);
		return mnc;
	}

}
