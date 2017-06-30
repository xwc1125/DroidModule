package com.xwc1125.droidutils.sms;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.xwc1125.droidutils.UtilsConfig;
import com.xwc1125.droidutils.LogUtils;
import com.xwc1125.droidutils.net.NetworkUtils;
import com.xwc1125.droidutils.sms.entity.MmsInfo;
import com.xwc1125.droidutils.StringUtils;

import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;

/**
 * 
 * <p>
 * Title: MmsUtil
 * </p>
 * <p>
 * Description: 彩信处理类
 * </p>
 * <p>
 * 
 * </p>
 * 
 * @author zhangqy
 * @date 2015年7月13日下午3:08:31
 * 
 */
public class MmsUtil {

	private static final String TAG = MmsUtil.class.getName();
	public static boolean isDebug = UtilsConfig.isDebug;
	/**
	 * SharedPreferences缓存字段－数据库是否有delete字段
	 */
	public static String CACHE_SP_DELT = "isDelete";

	/**
	 * 
	 * <p>
	 * Title: isCanDownload
	 * </p>
	 * <p>
	 * Description: 彩信是否可进行下载
	 * </p>
	 * <p>
	 * 
	 * </p>
	 * 
	 * @param context
	 * @return
	 * 
	 * @author zhangqy
	 * @date 2016年4月13日下午4:16:05
	 */
	public static Boolean isCanDownload(Context context) {
		if (context == null) {
			return false;
		}
		Boolean flag = false;
		if (NetworkUtils.isNetActive(context)) {
			flag = true;
		}
		return flag;
	}

	/**
	 * 
	 * <p>
	 * Title: isSpecNumber
	 * </p>
	 * <p>
	 * Description: 判断number是否在指定号码段中
	 * </p>
	 * <p>
	 * 
	 * </p>
	 * 
	 * @param number
	 * @return
	 * 
	 * @author zhangqy
	 * @date 2015年7月10日 下午5:14:38
	 */
	public static Boolean isSpecNumber(String number, String[] SpecNumber) {
		if (StringUtils.isEmpty(number)) {
			return false;
		}
		Boolean flag = false;
		String[] from = SpecNumber;
		for (int i = 0; i < from.length; i++) {
			if (number.startsWith(from[i])) {
				flag = true;
				break;
			}
		}
		return flag;
	}

	/**
	 * 
	 * <p>
	 * Title: checkDelete
	 * </p>
	 * <p>
	 * Description:
	 * </p>
	 * <p>
	 * 
	 * </p>
	 * 
	 * @param context
	 * @param list
	 * @return
	 * 
	 * @author zhangqy
	 * @date 2016年4月18日上午10:44:08
	 */
	@SuppressLint("UseSparseArrays")
	public static List<String> checkDelete(Context context, List<MmsInfo> list) {
		if (list == null || list.size() < 1) {
			return null;
		}
		List<String> rlist = null;
		try {
			StringBuffer buf = new StringBuffer();
			HashMap<String, Boolean> map = new HashMap<String, Boolean>();
			for (MmsInfo m : list) {
				String id_nt = m.getId_nt();
				if (StringUtils.isNotEmpty(id_nt)) {
					buf.append(id_nt).append(',');
					map.put(m.getId_nt(), true);
				}
			}
			if (buf.length() > 0) {
				buf.deleteCharAt(buf.length() - 1);
			}
			LogUtils.d(TAG,
					"所有未下载或未下载完成(id_wf＝null)的彩信通知id = " + buf.toString(),
					isDebug);

			Cursor cur = context.getContentResolver().query(
					Uri.parse("content://mms"), new String[] { "_id" },
					"_id in (" + buf.toString() + ")", null, null);

			if (cur != null) {
				while (cur.moveToNext()) {
					String _id = cur.getString(cur.getColumnIndex("_id"));
					map.put(_id, false);
					LogUtils.d(TAG, "未从数据库删除的彩信通知 ＝  " + _id, isDebug);
				}
				cur.close();
			}

			rlist = new ArrayList<String>();
			for (Map.Entry<String, Boolean> entry : map.entrySet()) {
				if (entry.getValue()) {
					rlist.add(entry.getKey());
				}
			}
			if (rlist.size() > 0) {
				LogUtils.e(TAG, "已从数据库删除的彩信通知 ＝ " + rlist.toString(), isDebug);
			}
		} catch (Throwable e) {
			LogUtils.e(TAG, e.getMessage(), isDebug);
		}
		return rlist;
	}

	/**
	 * @author zhangqy
	 * @data 2015年7月10日 下午6:06:49
	 * @Description
	 * @tags @param context
	 * @tags @param unread 必须为未读
	 * @tags @param index 第几条
	 * @tags @param flag 主题是否必须不为空
	 * @tags @return
	 */
	public static Map<String, String> readMMS(Context context, boolean unread,
			int index, boolean flag) {
		if (context == null)
			return null;
		ContentResolver resolver = context.getContentResolver();
		// 查到发出的短信
		Uri uri = Uri.parse("content://mms");
		// modify by zqy .......华为不支持连表查。。。。
		// String[] projection = new String[] { "_id", "thread_id", "date",
		// "sub",
		// "ct_l", "m_size", "read",
		// "(select address from addr where type='137' and msg_id=pdu._id) as
		// fromNumber "
		// };
		String[] projection = new String[] { "_id", "thread_id", "date", "sub",
				"ct_l", "m_size", "read" };
		// modify end
		String selection = "sub is not null";
		String[] selectionArgs = null;

		if (flag) {// sub必须不为空
			if (unread) {// 未读
				selection = "sub is not null and read= ?";
				selectionArgs = new String[] { "0" };
			}
		} else {
			if (unread) {
				selection = "read= ?";
				selectionArgs = new String[] { "0" };
			} else {
				selection = null;
				selectionArgs = null;
			}
		}
		Boolean isDelete = true;
		if (isDelete) {
			if (flag) {// sub必须不为空
				if (unread) {// 未读
					selection = "sub is not null and read= ? and deleted=0";
					selectionArgs = new String[] { "0" };
				} else {
					selection = "sub is not null and deleted=0";
					selectionArgs = null;
				}
			} else {
				if (unread) {
					selection = "read= ? and deleted=0";
					selectionArgs = new String[] { "0" };
				} else {
					selection = " deleted=0 ";
					selectionArgs = null;
				}
			}
		}
		String order = "_id desc limit " + index + ",1";
		Cursor cursor = resolver.query(uri, projection, selection,
				selectionArgs, order);
		HashMap<String, String> map = null;
		if (cursor != null) {
			while (cursor.moveToNext()) {
				if (map == null) {
					map = readMMS(context, cursor);
				}
			}
			cursor.close();
		}
		return map;
	}

	/**
	 * 
	 * <p>
	 * Title: readMMS
	 * </p>
	 * <p>
	 * Description:
	 * </p>
	 * <p>
	 * 
	 * </p>
	 * 
	 * @param cursor
	 * @return
	 * 
	 * @author zhangqy
	 * @date 2015年7月13日下午3:07:00
	 */
	public static HashMap<String, String> readMMS(Context context, Cursor cursor) {
		HashMap<String, String> map = new HashMap<String, String>();
		try {
			for (int i = 0; i < cursor.getColumnCount(); i++) {
				String columnName = cursor.getColumnName(i);
				String value = cursor.getString(i);
				if ("sub".equals(columnName)) {
					try {
						boolean isISO = Charset.forName("ISO-8859-1")
								.newEncoder().canEncode(value);
						if (isISO) {
							value = new String(value.getBytes("ISO8859_1"),
									"utf-8");
						}
					} catch (Exception e) {
					}
				}
				if ("_id".equals(columnName)) {
					map.put("fromNumber", MmsUtil.getFromNumber(context, value));
				}
				map.put(columnName, value);
			}
		} catch (Exception e) {
			LogUtils.e(TAG, e.getMessage(), isDebug);
		}
		return map;
	}

	/**
	 * 
	 * <p>
	 * Title: deleteDumplicate
	 * </p>
	 * <p>
	 * Description: 根据_id删除彩信
	 * </p>
	 * <p>
	 * 
	 * </p>
	 * 
	 * @param mContext
	 * @param _id
	 * @return
	 * 
	 * @author zhangqy
	 * @date 2015年7月22日上午10:41:44
	 */
	public static int deleteDumplicate(Context mContext, String _id) {
		if (mContext == null) {
			return 0;
		}
		Uri uri = Uri.parse("content://mms");
		String selection = "_id=?";
		String[] selectionArgs = new String[] { _id };
		return mContext.getContentResolver().delete(uri, selection,
				selectionArgs);
	}

	/**
	 * 
	 * <p>
	 * Title: getFromNumber
	 * </p>
	 * <p>
	 * Description:根据彩信id 获取来源
	 * </p>
	 * <p>
	 * 
	 * </p>
	 * 
	 * @param mContext
	 * @param mmsId
	 * @return
	 * 
	 * @author zhangqy
	 * @date 2015年7月23日下午2:58:56
	 */
	public static String getFromNumber(Context mContext, Object mmsId) {
		if (mContext == null) {
			return null;
		}
		String address = null;
		Uri uri = Uri.parse("content://mms/" + mmsId + "/addr");
		Cursor query = mContext.getContentResolver().query(uri,
				new String[] { "address" }, "type = ?", new String[] { "137" },
				null);
		if (query != null) {
			while (query.moveToNext()) {
				address = query.getString(query.getColumnIndex("address"));
				break;
			}
		}
		return address;
	}

	/**
	 * <p>
	 * Title: readMMS
	 * </p>
	 * <p>
	 * Description: 查询彩信
	 * </p>
	 * <p>
	 * 
	 * </p>
	 * 
	 * @tags @param context
	 * @tags @param unread 是否未读
	 * @tags @param index 从第几条开始查
	 * @tags @param count 查询的条数
	 * @tags @param isSubNotNull 主题是否必须不为空
	 * @tags @param minutes 查询最近几分钟的短信
	 * @tags @return
	 * 
	 * @author xwc1125
	 * @date 2017年1月9日 上午10:24:47
	 */
	public static List<Map<String, String>> readMMS(Context context,
			boolean unread, int index, int count, boolean isSubNotNull,
			int minutes) {
		if (context == null) {
			return null;
		}
		StringBuffer selectionBuffer = new StringBuffer();
		StringBuffer sortOrderBuffer = new StringBuffer();
		List<String> selectionArgsList = new ArrayList<String>();
		List<String> projectionList = new ArrayList<String>();
		// "_id", "thread_id", "date", "sub", "ct_l", "m_size", "read"
		projectionList.add("_id");
		projectionList.add("thread_id");
		projectionList.add("date");
		projectionList.add("sub");

		if (isSubNotNull) {// sub必须不为空
			selectionBuffer.append(" sub is not null ");
		}
		if (unread) {
			// 未读
			if (selectionBuffer.length() > 0) {
				selectionBuffer.append(" and ");
			}
			selectionBuffer.append(" read= ? ");
			selectionArgsList.add("0");
		} else {

		}
		if (minutes > 0) {
			if (selectionBuffer.length() > 0) {
				selectionBuffer.append(" and ");
			}
			selectionBuffer.append(" date >?");
			long sec = System.currentTimeMillis() / 1000 - minutes * 60;
			selectionArgsList.add(sec + "");
		}
		Boolean isDelete = true;
		if (isDelete) {
			if (selectionBuffer.length() > 0) {
				selectionBuffer.append(" and ");
			}
			selectionBuffer.append(" deleted=? ");
			selectionArgsList.add("0");
		}

		sortOrderBuffer.append("_id desc ");
		if (index == count && index == 0) {
		} else {
			sortOrderBuffer.append(" limit " + index + "," + count);
		}

		ContentResolver resolver = context.getContentResolver();
		// 查到发出的短信
		Uri uri = Uri.parse("content://mms");
		String[] projection = null;
		String selection = null;
		String[] selectionArgs = null;
		String order = "_id desc ";

		if (projectionList != null && projectionList.size() > 0) {
			projection = new String[projectionList.size()];
			for (int i = 0, len = projectionList.size(); i < len; i++) {
				projection[i] = projectionList.get(i);
			}
		}
		if (selectionArgsList != null && selectionArgsList.size() > 0) {
			selectionArgs = new String[selectionArgsList.size()];
			for (int i = 0, len = selectionArgsList.size(); i < len; i++) {
				selectionArgs[i] = selectionArgsList.get(i);
			}
		}
		if (sortOrderBuffer != null && sortOrderBuffer.length() > 0) {
			order = sortOrderBuffer.toString();
		}
		if (selectionBuffer != null && selectionBuffer.length() > 0) {
			selection = selectionBuffer.toString();
		}
		Cursor cursor = resolver.query(uri, projection, selection,
				selectionArgs, order);

		List<Map<String, String>> list = new ArrayList<Map<String, String>>();
		if (cursor != null) {
			while (cursor.moveToNext()) {
				HashMap<String, String> map = readMMS(context, cursor);
				if (map != null && map.size() > 0) {
					list.add(map);
				}
			}
		}
		if (cursor != null) {
			cursor.close();
		}
		return list;
	}
}