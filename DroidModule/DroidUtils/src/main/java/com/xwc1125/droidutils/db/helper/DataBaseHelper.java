/**
 * <p>
 * Title: DataBaseHelper.java
 * </p>
 * <p>
 * Description: SSQLite数据库的帮助类
 * </p>
 * <p>
 * 
 * </p>
 * @Copyright: Copyright (c) 2016
 * @author zhangqy
 * @date 2016年8月16日 上午10:16:14
 * @version V1.0
 */
package com.xwc1125.droidutils.db.helper;

import java.io.File;
import java.util.Map;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.util.Log;

import com.xwc1125.droidutils.db.helper.impl.SDCardSQLiteOpenHelper;

/**
 * SQLite数据库的帮助类
 * <p>
 * Title: DataBaseHelper
 * </p>
 * <p>
 * Description: 该类属于扩展类,主要承担数据库初始化和版本升级使用,其他核心全由核心父类完成
 * </p>
 * <p>
 * Company:
 * </p>
 * 
 * @author xwc1125
 * @date 2015-6-7上午8:58:03
 */
public class DataBaseHelper extends SDCardSQLiteOpenHelper {
	private static final String TAG = DataBaseHelper.class.getSimpleName();
	/**
	 * 创建表的sql语句map集合
	 */
	private Map<String, Object> map;

	public DataBaseHelper(Context context, String name, CursorFactory factory,
			int version, Map<String, Object> SQL_List) {
		super(context, name, factory, version);
		this.map = SQL_List;
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		Log.i(TAG, "开始创建数据库");
		db.beginTransaction();
		try {
			if (map != null) {
				for (Map.Entry<String, Object> entry : map.entrySet()) {
					// String name = entry.getKey();
					if (entry.getValue() != null) {
						String aaString = entry.getValue().toString();
						db.execSQL(aaString);
					}
				}
				db.setTransactionSuccessful();
				Log.i(TAG, "创建数据库成功！");
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.endTransaction();
		}
	}

	/*
	 * 数据库更新操作
	 */
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

	}

	@Override
	public void onOpen(SQLiteDatabase db) {
		super.onOpen(db);
	}

	/**
	 * 
	 * <p>
	 * Title: setDirName
	 * </p>
	 * <p>
	 * Description: 设置数据库的目录名称。
	 * </p>
	 * <p>
	 * 默认路径是：/xwc1125/database/
	 * </p>
	 * 
	 * @tags @param dirName
	 *       如果dirName=zhongzhuoxin,那么数据库的路径是/zhongzhuoxin/database/
	 * 
	 *       如果有SD卡，那么默认优先选择SD卡存储
	 * 
	 * @author xwc1125
	 * @date Jul 25, 20169:53:20 PM
	 */
	@Override
	public void setDirName(String dirName) {
		super.setDirName(dirName);
	}

	/**
	 * 
	 * <p>
	 * Title: setDataBaseFile
	 * </p>
	 * <p>
	 * Description: 设置数据库的存储路径
	 * </p>
	 * <p>
	 * 
	 * </p>
	 * 
	 * @tags @param file
	 * 
	 * @author xwc1125
	 * @date Jul 25, 201610:06:07 PM
	 */
	@Override
	public void setDataBaseFile(File file) {
		super.setDataBaseFile(file);
	}
}
