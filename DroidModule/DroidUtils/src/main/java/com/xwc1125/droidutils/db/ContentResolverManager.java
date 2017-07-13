/**
 * <p>
 * Title: ContentResolverManager.java
 * </p>
 * <p>
 * Description: TODO(describe the file) 
 * </p>
 * <p>
 * 
 * </p>
 * @Copyright: Copyright (c) 2016年9月7日
 * @author xwc1125
 * @date 2016年9月7日 下午1:52:07
 * @version V1.0
 */
package com.xwc1125.droidutils.db;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;

/**
 * <p>
 * Title: ContentResolverManager
 * </p>
 * <p>
 * Description: TODO(describe the types)
 * </p>
 * <p>
 * 
 * </p>
 * 
 * @author xwc1125
 * @date 2016年9月7日 下午1:52:07
 * 
 */
public class ContentResolverManager<T> {
	/**
	 * 
	 * <p>
	 * Title: insert
	 * </p>
	 * <p>
	 * Description: insert
	 * </p>
	 * <p>
	 * 
	 * </p>
	 * 
	 * @tags @param context
	 * @tags @param url
	 * @tags @param values
	 * @tags @return
	 * 
	 * @author xwc1125
	 * @date 2016年9月7日 下午2:12:34
	 */
	public static Uri insert(Context context, Uri url, ContentValues values) {
		Uri uri = null;
		try {
			ContentResolver resolver = context.getContentResolver();
			uri = resolver.insert(url, values);
		} finally {
		}
		return uri;
	}

	/**
	 * 
	 * <p>
	 * Title: delete
	 * </p>
	 * <p>
	 * Description: delete
	 * </p>
	 * <p>
	 * 
	 * </p>
	 * 
	 * @tags @param context
	 * @tags @param url
	 * @tags @param where
	 * @tags @param selectionArgs
	 * @tags @return
	 * 
	 * @author xwc1125
	 * @date 2016年9月7日 下午2:15:12
	 */
	public static int delete(Context context, Uri url, String where, String[] selectionArgs) {
		int count = 0;
		try {
			ContentResolver resolver = context.getContentResolver();
			count = resolver.delete(url, where, selectionArgs);
		} finally {
		}
		return count;
	}

	/**
	 * 
	 * <p>
	 * Title: update
	 * </p>
	 * <p>
	 * Description: update
	 * </p>
	 * <p>
	 * 
	 * </p>
	 * 
	 * @tags @param context
	 * @tags @param uri
	 * @tags @param values
	 * @tags @param where
	 * @tags @param selectionArgs
	 * @tags @return
	 * 
	 * @author xwc1125
	 * @date 2016年9月7日 下午2:18:41
	 */
	public static int update(Context context, Uri uri, ContentValues values, String where, String[] selectionArgs) {
		int count = 0;
		try {
			ContentResolver resolver = context.getContentResolver();
			count = resolver.update(uri, values, where, selectionArgs);
		} finally {
		}
		return count;
	}

	/**
	 * 
	 * <p>
	 * Title: queryForList
	 * </p>
	 * <p>
	 * Description: query
	 * </p>
	 * <p>
	 * 
	 * </p>
	 * 
	 * @tags @param context
	 * @tags @param uri
	 * @tags @param projection
	 * @tags @param selection
	 * @tags @param selectionArgs
	 * @tags @param sortOrder
	 * @tags @param rowMapper
	 * @tags @return
	 * 
	 * @author xwc1125
	 * @date 2016年9月7日 下午2:04:15
	 */
	public static <T> List<T> queryForList(Context context, Uri uri, String[] columns, String selection,
			String[] selectionArgs, String sortOrder, RowMapper<T> rowMapper) {
		Cursor cursor = null;
		List<T> list = null;
		try {
			ContentResolver resolver = context.getContentResolver();
			cursor = resolver.query(uri, columns, selection, selectionArgs, sortOrder);
			list = new ArrayList<T>();
			while (cursor.moveToNext()) {
				list.add(rowMapper.mapRow(cursor, cursor.getPosition()));
			}
		} finally {
			cursor.close();
		}
		return list;
	}

	/**
	 * 
	 * <p>
	 * Title: queryForList
	 * </p>
	 * <p>
	 * Description: query
	 * </p>
	 * <p>
	 * 
	 * </p>
	 * 
	 * @tags @param context
	 * @tags @param uri
	 * @tags @param projection
	 * @tags @param selection
	 * @tags @param selectionArgs
	 * @tags @param sortOrder
	 * @tags @param offset
	 * @tags @param size
	 * @tags @param rowMapper
	 * @tags @return
	 * 
	 * @author xwc1125
	 * @date 2016年9月7日 下午2:09:11
	 */
	public static <T> List<T> queryForList(Context context, Uri uri, String[] columns, String selection,
			String[] selectionArgs, String sortOrder, int offset, int size, RowMapper<T> rowMapper) {
		Cursor cursor = null;
		List<T> list = null;
		try {
			ContentResolver resolver = context.getContentResolver();
			if (sortOrder.contains("limit")) {
			} else {
				if (offset < 0) {
					offset = 0;
				}
				sortOrder = sortOrder + " limit " + offset;
				if (size <= 0) {
				} else {
					sortOrder = sortOrder + "," + size;
				}
			}

			cursor = resolver.query(uri, columns, selection, selectionArgs, sortOrder);
			list = new ArrayList<T>();
			while (cursor.moveToNext()) {
				list.add(rowMapper.mapRow(cursor, cursor.getPosition()));
			}
		} finally {
			cursor.close();
		}
		return list;
	}

}
