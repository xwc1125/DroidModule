/**
 * <p>
 * Title: TimeUtil.java
 * </p>
 * <p>
 * Description: 时间格式化类
 * </p>
 * <p>
 * 
 * </p>
 * @Copyright: Copyright (c) 2016
 * @author xwc1125
 * @date 2016年8月18日 上午11:42:15
 * @version V1.0
 */
package com.xwc1125.droidutils.datetime;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import android.annotation.SuppressLint;

/**
 * 
 * <p>
 * Title: TimeUtil
 * </p>
 * <p>
 * Description:时间格式化工具类
 * </p>
 * <p>
 * 
 * </p>
 * 
 * @author xwc1125
 * @date 2016年8月18日 上午11:15:21
 * 
 */
@SuppressLint("SimpleDateFormat")
public class TimeUtil {

	/**
	 * 格式－yyyy
	 */
	public final static String FORMAT_YEAR = "yyyy";
	/**
	 * 格式－MM月dd
	 */
	public final static String FORMAT_MONTH_DAY = "MM月dd日";
	/**
	 * 格式－yyyy-MM-dd
	 */
	public final static String FORMAT_DATE = "yyyy-MM-dd";
	/**
	 * 格式－HH:mm
	 */
	public final static String FORMAT_TIME = "HH:mm";
	/**
	 * 格式－MM月dd日 hh:mm
	 */
	public final static String FORMAT_MONTH_DAY_TIME = "MM月dd日  hh:mm";
	/**
	 * 格式－yyyy-MM-dd HH:mm
	 */
	public final static String FORMAT_DATE_TIME = "yyyy-MM-dd HH:mm";
	/**
	 * 格式－yyyy/MM/dd HH:mm
	 */
	public final static String FORMAT_DATE1_TIME = "yyyy/MM/dd HH:mm";
	/**
	 * 格式－yyyy/MM/dd HH:mm:ss
	 */
	public final static String FORMAT_DATE_TIME_SECOND = "yyyy/MM/dd HH:mm:ss";
	/**
	 * 格式－yyyyMMddHHmmss
	 */
	public final static String FORMAT_DATE_TIME_SECOND1 = "yyyyMMddHHmmss";
	private static SimpleDateFormat sdf = new SimpleDateFormat();
	/**
	 * 年
	 */
	private static final int YEAR = 365 * 24 * 60 * 60;
	/**
	 * 月
	 */
	private static final int MONTH = 30 * 24 * 60 * 60;
	/**
	 * 天
	 */
	private static final int DAY = 24 * 60 * 60;
	/**
	 * 小时
	 */
	private static final int HOUR = 60 * 60;
	/**
	 * 分钟
	 */
	private static final int MINUTE = 60;

	/**
	 * 
	 * <p>
	 * Title: getDescriptionTimeFromTimestamp
	 * </p>
	 * <p>
	 * Description: 根据时间戳获取描述性时间，如3分钟前，1天前
	 * </p>
	 * <p>
	 * 
	 * </p>
	 * 
	 * @param timestamp
	 *            :时间戳 单位为秒
	 * @return:时间字符串
	 * 
	 * @author xwc1125
	 * @date 2016年8月18日 上午11:19:05
	 */
	public static String getDescriptionTimeFromTimestamp(long timestamp) {
		long currentTime = System.currentTimeMillis() / 1000;
		long timeGap = currentTime - timestamp;// 与现在时间相差秒数
		String timeStr = null;
		if (timeGap > YEAR) {
			timeStr = timeGap / YEAR + "年前";
		} else if (timeGap > MONTH) {
			timeStr = timeGap / MONTH + "个月前";
		} else if (timeGap > DAY) {// 1天以上
			timeStr = timeGap / DAY + "天前";
		} else if (timeGap > HOUR) {// 1小时-24小时
			timeStr = timeGap / HOUR + "小时前";
		} else if (timeGap > MINUTE) {// 1分钟-59分钟
			timeStr = timeGap / MINUTE + "分钟前";
		} else {// 1秒钟-59秒钟
			timeStr = "刚刚";
		}
		return timeStr;
	}

	/**
	 * 
	 * <p>
	 * Title: getDescriptionTime
	 * </p>
	 * <p>
	 * Description: 根据时间获取时间描述，形如今天、昨天
	 * </p>
	 * <p>
	 * 
	 * </p>
	 * 
	 * @param time
	 * @return
	 * 
	 * @author xwc1125
	 * @date 2016年8月18日 上午11:37:10
	 */
	public static String getDescriptionTime(Long time) {
		try {
			Calendar cur = Calendar.getInstance();
			Calendar calendar = Calendar.getInstance();
			calendar.setTimeInMillis(time);
			SimpleDateFormat sdf;
			if (calendar.get(Calendar.YEAR) < cur.get(Calendar.YEAR)) {
				sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
			} else {
				if (calendar.get(Calendar.DAY_OF_YEAR) == cur
						.get(Calendar.DAY_OF_YEAR)) {
					sdf = new SimpleDateFormat("今天 HH:mm");
				} else if (calendar.get(Calendar.DAY_OF_YEAR) + 1 == cur
						.get(Calendar.DAY_OF_YEAR)) {
					sdf = new SimpleDateFormat("昨天 HH:mm");
				} else {
					sdf = new SimpleDateFormat("MM-dd HH:mm");
				}
			}
			return sdf.format(calendar.getTime());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}

	/**
	 * 
	 * <p>
	 * Title: getCurrentTime
	 * </p>
	 * <p>
	 * Description: 获取当前日期的指定格式的字符串
	 * </p>
	 * <p>
	 * 
	 * </p>
	 * 
	 * @param format
	 *            :指定的日期时间格式，若为null或""则使用指定的格式"yyyy-MM-dd HH:MM"
	 * @return
	 * 
	 * @author xwc1125
	 * @date 2016年8月18日 上午11:19:32
	 */
	public static String getCurrentTime(String format) {
		if (format == null || format.trim().equals("")) {
			sdf.applyPattern(FORMAT_DATE_TIME);
		} else {
			sdf.applyPattern(format);
		}
		return sdf.format(new Date());
	}

	/**
	 * 
	 * <p>
	 * Title: dateToString
	 * </p>
	 * <p>
	 * Description: date类型转换为String类型
	 * </p>
	 * <p>
	 * formatType格式为yyyy-MM-dd HH:mm:ss//yyyy年MM月dd日 HH时mm分ss秒
	 * </p>
	 * 
	 * @param data
	 * @param formatType
	 * @return
	 * 
	 * @author xwc1125
	 * @date 2016年8月18日 上午11:20:43
	 */
	public static String dateToString(Date data, String formatType) {
		return new SimpleDateFormat(formatType).format(data);
	}

	/**
	 * 
	 * <p>
	 * Title: longToString
	 * </p>
	 * <p>
	 * Description: Tlong类型转换为String类型
	 * </p>
	 * <p>
	 * 
	 * </p>
	 * 
	 * @param currentTime
	 *            :转换的long类型的时间
	 * @param formatType
	 *            :转换的string类型的时间格式
	 * @return
	 * 
	 * @author xwc1125
	 * @date 2016年8月18日 上午11:22:08
	 */
	public static String longToString(long currentTime, String formatType) {
		String strTime = "";
		Date date = longToDate(currentTime, formatType);// long类型转成Date类型
		strTime = dateToString(date, formatType); // date类型转成String
		return strTime;
	}

	/**
	 * 
	 * <p>
	 * Title: stringToDate
	 * </p>
	 * <p>
	 * Description: string类型转换为date类型
	 * </p>
	 * <p>
	 * strTime的时间格式必须要与formatType的时间格式相同
	 * </p>
	 * 
	 * @param strTime
	 *            :转换的string类型的时间
	 * @param formatType
	 *            :转换的格式yyyy-MM-dd HH:mm:ss//yyyy年MM月dd日 HH时mm分ss秒
	 * @return
	 * 
	 * @author xwc1125
	 * @date 2016年8月18日 上午11:22:35
	 */
	public static Date stringToDate(String strTime, String formatType) {
		SimpleDateFormat formatter = new SimpleDateFormat(formatType);
		Date date = null;
		try {
			date = formatter.parse(strTime);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}

	/**
	 * 
	 * <p>
	 * Title: longToDate
	 * </p>
	 * <p>
	 * Description: Tlong转换为Date类型
	 * </p>
	 * <p>
	 * 
	 * </p>
	 * 
	 * @param currentTime
	 *            :转换的long类型的时间
	 * @param formatType
	 *            :转换的时间格式yyyy-MM-dd HH:mm:ss//yyyy年MM月dd日 HH时mm分ss秒
	 * @return
	 * 
	 * @author xwc1125
	 * @date 2016年8月18日 上午11:23:41
	 */
	public static Date longToDate(long currentTime, String formatType) {
		Date dateOld = new Date(currentTime); // 根据long类型的毫秒数生命一个date类型的时间
		String sDateTime = dateToString(dateOld, formatType); // 把date类型的时间转换为string
		Date date = stringToDate(sDateTime, formatType); // 把String类型转换为Date类型
		return date;
	}

	/**
	 * 
	 * <p>
	 * Title: stringToLong
	 * </p>
	 * <p>
	 * Description: string类型转换为long类型
	 * </p>
	 * <p>
	 * strTime的时间格式和formatType的时间格式必须相同
	 * </p>
	 * 
	 * @param strTime
	 *            :要转换的String类型的时间
	 * @param formatType
	 *            :要转换的时间格式
	 * @return
	 * 
	 * @author xwc1125
	 * @date 2016年8月18日 上午11:24:02
	 */
	public static long stringToLong(String strTime, String formatType) {
		Date date = stringToDate(strTime, formatType); // String类型转成date类型
		if (date == null) {
			return 0;
		} else {
			long currentTime = dateToLong(date); // date类型转成long类型
			return currentTime;
		}
	}

	/**
	 * 
	 * <p>
	 * Title: dateToLong
	 * </p>
	 * <p>
	 * Description: date类型转换为long类型
	 * </p>
	 * <p>
	 * 
	 * </p>
	 * 
	 * @param date
	 *            :转换的date类型的时间
	 * @return
	 * 
	 * @author xwc1125
	 * @date 2016年8月18日 上午11:24:46
	 */
	public static long dateToLong(Date date) {
		return date.getTime();
	}
	
	public static String getTime(long currentTimeMillis) {
		Date date = new Date(currentTimeMillis);
		SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm");
		return dateFormat.format(date);
	}
}