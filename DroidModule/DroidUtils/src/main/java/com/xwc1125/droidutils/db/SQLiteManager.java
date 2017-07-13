/**
 * <p>
 * Title: SQLiteManager.java
 * </p>
 * <p>
 * Description: SQLite数据库模板工具类
 * </p>
 * <p>
 * 
 * </p>
 * @Copyright: Copyright (c) 2016
 * @author zhangqy
 * @date 2016年8月16日 上午10:16:14
 * @version V1.0
 */
package com.xwc1125.droidutils.db;

import java.io.File;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.xwc1125.droidutils.LogUtils;
import com.xwc1125.droidutils.db.helper.DataBaseHelper;

/**
 * 
 * <p>
 * Title: SQLiteManager
 * </p>
 * <p>
 * Description: SQLite数据库模板工具类
 * <li>该类提供了数据库操作常用的增删改查,以及各种复杂条件匹配,分页,排序等操作</li>
 * </p>
 * 
 * @author xwc1125
 * @date 2015-6-21上午10:34:31
 * 
 */
public class SQLiteManager {
	private static final String TAG = "SQLiteManager";
	private Context mContext;
	/**
	 * 设置是否是debug模式，如果是debug模式，将会打印日志
	 */
	private boolean isDBDebug = false;
	/**
	 * 创建表的sql语句map集合
	 */
	private Map<String, Object> sqlMap;
	/**
	 * Default Primary key自增长
	 */
	protected String mPrimaryKey = "_id";
	/**
	 * 是否为一个事务
	 */
	private boolean isTransaction = false;
	/**
	 * 数据库连接
	 */
	private SQLiteDatabase dataBase = null;
	/**
	 * 单例对象
	 */
	private static SQLiteManager sqLiteManager;
	/**
	 * 数据库版本号
	 */
	private int version = 1;
	/**
	 * 数据库版本名称
	 */
	private String databaseName;

	private SQLiteManager() {
		throw new AssertionError();
	}

	/**
	 * 
	 * <p>
	 * Title: SQLiteManager
	 * </p>
	 * <p>
	 * Description: 带参构造方法
	 * </p>
	 * 
	 * @param context
	 *            上下文
	 * @param isTransaction
	 *            是否为一个事务
	 */
	private SQLiteManager(Context context, boolean isTransaction) {
		this.mContext = context;
		this.isTransaction = isTransaction;
	}

	private static synchronized void syncInit(Context mContext, boolean isTransaction) {
		if (sqLiteManager == null) {
			sqLiteManager = new SQLiteManager(mContext, isTransaction);
		}
	}

	/**
	 * 
	 * <p>
	 * Title: getInstance
	 * </p>
	 * <p>
	 * Description: 单例模式
	 * </p>
	 * 
	 * @param isTransaction
	 *            : 是否属于一个事务 注:一旦isTransaction设为true
	 *            所有的SQLiteTemplate方法都不会自动关闭资源,需在事务成功后手动关闭
	 * @param sqlMap
	 *            : sql语句
	 * @return
	 * 
	 * @author xwc1125
	 * @date 2015-6-21上午10:35:03
	 */
	public static SQLiteManager getInstance(Context context, String databaseName, boolean isTransaction,
			Map<String, Object> sqlMap) {
		if (sqLiteManager == null) {
			syncInit(context, isTransaction);
		}
		sqLiteManager.databaseName = databaseName;
		sqLiteManager.sqlMap = sqlMap;
		return sqLiteManager;
	}

	/**
	 * 
	 * <p>
	 * Title: getInstance
	 * </p>
	 * <p>
	 * Description: 单例模式
	 * </p>
	 * <p>
	 * 
	 * </p>
	 * 
	 * @param context
	 * @param databaseName
	 * @param isTransaction
	 *            : 是否属于一个事务 注:一旦isTransaction设为true
	 *            所有的SQLiteTemplate方法都不会自动关闭资源,需在事务成功后手动关闭
	 * @return
	 * 
	 * @author zhangqy
	 * @date 2016年8月16日 下午2:04:57
	 */
	public static SQLiteManager getInstance(Context context, String databaseName, boolean isTransaction) {
		if (sqLiteManager == null) {
			syncInit(context, isTransaction);
		}
		sqLiteManager.databaseName = databaseName;
		return sqLiteManager;
	}

	/**
	 * 
	 * <p>
	 * Title: setDebug
	 * </p>
	 * <p>
	 * Description: 设置是否是debug模式，如果是debug模式，将会打印日志
	 * </p>
	 * 
	 * @param isDbDebug
	 * 
	 * @author xwc1125
	 * @date 2015-6-21下午5:28:09
	 */
	public void setDebug(boolean isDbDebug) {
		this.isDBDebug = isDbDebug;
	}

	// =====================原生态的sql=================//
	/**
	 * 
	 * <p>
	 * Title: execSQL
	 * </p>
	 * <p>
	 * Description: 执行一条原生态的sql语句
	 * </p>
	 * 
	 * @param sql
	 * 
	 * @author xwc1125
	 * @date 2015-6-21上午10:35:51
	 */
	public void execSQL(String sql) {
		try {
			dataBase = openDatabase();
			if (isDBDebug) {
				Log.i(TAG, sql);
			}
			dataBase.execSQL(sql);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeDatabase(null);
		}
	}

	/**
	 * 
	 * <p>
	 * Title: execSQL
	 * </p>
	 * <p>
	 * Description: 执行带参数的sql的语句，Execute a single SQL statement that is NOT a
	 * SELECT/INSERT/UPDATE/DELETE.
	 * </p>
	 * 
	 * @param sql
	 *            the SQL statement to be executed. Multiple statements
	 *            separated by semicolons are not supported.
	 * @param bindArgs
	 *            only byte[], String, Long and Double are supported in
	 *            bindArgs.
	 * @throws SQLException
	 *             if the SQL string is invalid
	 * 
	 * @param sql
	 * @param bindArgs
	 * 
	 * @author xwc1125
	 * @date 2015-6-20下午4:47:40
	 */
	public void execSQL(String sql, Object[] bindArgs) {
		try {
			dataBase = openDatabase();
			if (isDBDebug) {
				Log.i(TAG, sql + "," + bindArgs.toString());
			}
			dataBase.execSQL(sql, bindArgs);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeDatabase(null);
		}
	}

	// =====================表的操作=================//
	/**
	 * 
	 * <p>
	 * Title: getTableName
	 * </p>
	 * <p>
	 * Description: 通过映射获取表名
	 * </p>
	 * 
	 * @param clazz
	 * @param prefix
	 *            前缀
	 * @return
	 * 
	 * @author xwc1125
	 * @date 2015-6-21下午5:34:06
	 */
	@SuppressLint("DefaultLocale")
	public String getTableName(Class<?> clazz, String prefix) {
		String tableName = clazz.getSimpleName();
		if (prefix == null) {
			tableName = tableName.toLowerCase();
		} else {
			tableName = prefix + tableName.toLowerCase();
		}
		return tableName;
	}

	/**
	 * 
	 * <p>
	 * Title: createTable
	 * </p>
	 * <p>
	 * Description: 通过反射，建立表结构SQL代码
	 * <li>创建表时自动添加前缀</li>
	 * </p>
	 * 
	 * @param clazz
	 * @param prefix
	 *            前缀，如果传入的为null时，系统自动添加t_前缀
	 * @param pk
	 *            主键，如果pk为null时，系统自动添加Integer的_id座位主键
	 * @return
	 * 
	 * @author xwc1125
	 * @date 2015-6-21下午5:03:29
	 */
	@SuppressLint("DefaultLocale")
	public void createTable(Class<?> clazz, String prefix, String pk) {
		try {
			StringBuffer sb = new StringBuffer();
			String tableName = clazz.getSimpleName();
			if (prefix == null) {
				tableName = tableName.toLowerCase();
			} else {
				tableName = prefix + tableName.toLowerCase();
			}
			// create table if not exists Threads (_id integer primary key
			// autoincrement,
			sb.append("create table if not exists ").append(tableName).append(" (");
			if (pk == null) {
				// _id integer primary key_id integer primary key autoincrement
				sb.append("_id integer primary key autoincrement,");
			}
			Field[] fields = clazz.getDeclaredFields();

			for (Field fItem : fields) {
				sb.append(fItem.getName());
				if (fItem.getType().equals(Date.class)) {
					sb.append(" date");
				} else if (fItem.getType().equals(int.class)) {
					sb.append(" int");
				} else if (fItem.getType().equals(long.class)) {
					sb.append(" long");
				} else if (fItem.getType().equals(float.class)) {
					sb.append(" float");
				} else if (fItem.getType().equals(double.class)) {
					sb.append(" double");
				} else if (fItem.getType().equals(boolean.class)) {
					sb.append(" boolean");
				} else if (fItem.getType().equals(byte.class)) {
					sb.append(" byte");
				} else if (fItem.getType().equals(short.class)) {
					sb.append(" short");
				} else if (fItem.getType().equals(String.class)) {
					sb.append(" String");
				} else {
					sb.append(" text");
				}
				if (pk != null && fItem.getName().equals(pk)) {
					sb.append(" primary key");
				}
				sb.append(",");
			}
			sb.deleteCharAt(sb.length() - 1);
			sb.append(");");
			String sql = sb.toString();
			execSQL(sql);
		} catch (Exception e) {
		} finally {
			closeDatabase(null);
		}
	}

	/**
	 * 
	 * <p>
	 * Title: dropTable
	 * </p>
	 * <p>
	 * Description: 删除表
	 * </p>
	 * 
	 *
	 * @author xwc1125
	 * @date 2015-6-21下午4:51:59
	 */
	@SuppressLint("DefaultLocale")
	public void dropTableByClass(String tableName) {
		try {
			StringBuffer sb = new StringBuffer();
			sb.append("drop table if exists ").append(tableName);
			String sql = sb.toString();
			execSQL(sql);
		} catch (Exception e) {
			// TODO: handle exception
		} finally {
			closeDatabase(null);
		}
	}

	/**
	 * 
	 * <p>
	 * Title: dropTableByClass
	 * </p>
	 * <p>
	 * Description: 通过映射删除表
	 * </p>
	 * 
	 * @param clazz
	 * @param prefix
	 *            前缀
	 * 
	 * @author xwc1125
	 * @date 2015-6-21下午5:07:42
	 */
	@SuppressLint("DefaultLocale")
	public void dropTableByClass(Class<?> clazz, String prefix) {
		try {
			StringBuffer sb = new StringBuffer();
			String tableName = clazz.getSimpleName();
			if (prefix == null) {
				tableName = tableName.toLowerCase();
			} else {
				tableName = prefix + tableName.toLowerCase();
			}
			sb.append("drop table if exists ").append(tableName);
			execSQL(sb.toString());
		} catch (Exception e) {
			// TODO: handle exception
		} finally {
			closeDatabase(null);
		}
	}

	// =====================更新插入=================//
	/**
	 * 
	 * <p>
	 * Title: insert
	 * </p>
	 * <p>
	 * Description: 向数据库表中插入一条数据
	 * </p>
	 * 
	 * @param table
	 * @param content
	 * @return 返回ID
	 * 
	 * @author xwc1125
	 * @date 2015-6-20下午4:51:34
	 */
	public long insert(String table, ContentValues content) {
		try {
			dataBase = openDatabase();
			// insert方法第一参数：数据库表名，第二个参数如果CONTENT为空时则向表中插入一个NULL,第三个参数为插入的内容
			return dataBase.insert(table, null, content);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeDatabase(null);
		}
		return 0;
	}

	public long insert(String table, String nullColumnHack, ContentValues values) {
		try {
			dataBase = openDatabase();
			// insert方法第一参数：数据库表名，第二个参数如果CONTENT为空时则向表中插入一个NULL,第三个参数为插入的内容
			return dataBase.insert(table, nullColumnHack, values);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeDatabase(null);
		}
		return 0;
	}

	public long insert(String sql, String... valus) {
		try {
			dataBase = openDatabase();
			// 执行SQL语句
			if (valus == null || valus.length == 0) {
				dataBase.execSQL(sql);
			} else {
				for (int i = 0; i < valus.length; i++) {
					sql.replace("?", valus[i]);
				}
			}
			dataBase.execSQL(sql);
			return 1;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeDatabase(null);
		}
		return 0;
	}

	/**
	 * 
	 * <p>
	 * Title: updateById
	 * </p>
	 * <p>
	 * Description: 根据主键更新一行数据
	 * </p>
	 * 
	 * @param table
	 * @param id
	 * @param values
	 * @return 返回值大于0表示更新成功
	 * 
	 * @author xwc1125
	 * @date 2015-6-20下午4:53:54
	 */
	public int updateById(String table, String id, ContentValues values) {
		try {
			dataBase = openDatabase();
			return dataBase.update(table, values, mPrimaryKey + "=?", new String[] { id });
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeDatabase(null);
		}
		return 0;
	}

	/**
	 * 
	 * <p>
	 * Title: update
	 * </p>
	 * <p>
	 * Description: 根据条件更新数据
	 * </p>
	 * 
	 * @param table
	 * @param values
	 * @param whereClause
	 * @param whereArgs
	 * @return 返回值大于0表示更新成功
	 * 
	 * @author xwc1125
	 * @date 2015-6-20下午4:54:14
	 */
	public int update(String table, ContentValues values, String whereClause, String[] whereArgs) {
		try {
			dataBase = openDatabase();
			return dataBase.update(table, values, whereClause, whereArgs);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeDatabase(null);
		}
		return 0;
	}

	// =====================删除=================//
	/**
	 * 
	 * <p>
	 * Title: deleteById
	 * </p>
	 * <p>
	 * Description: 根据主键删除一行数据
	 * </p>
	 * 
	 * @param table
	 * @param id
	 * @return 返回值大于0表示删除成功
	 * 
	 * @author xwc1125
	 * @date 2015-6-20下午4:53:31
	 */
	public int deleteById(String table, String id) {
		try {
			dataBase = openDatabase();
			return deleteByField(table, mPrimaryKey, id);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeDatabase(null);
		}
		return 0;
	}

	/**
	 * 
	 * <p>
	 * Title: deleteByIds
	 * </p>
	 * <p>
	 * Description: 批量删除指定主键数据
	 * </p>
	 * 
	 * @param table
	 * @param primaryKeys
	 * 
	 * @author xwc1125
	 * @date 2015-6-20下午4:52:01
	 */
	public void deleteByIds(String table, Object... primaryKeys) {
		try {
			if (primaryKeys.length > 0) {
				StringBuilder sb = new StringBuilder();
				for (@SuppressWarnings("unused")
				Object id : primaryKeys) {
					sb.append("?").append(",");
				}
				sb.deleteCharAt(sb.length() - 1);
				dataBase = openDatabase();
				dataBase.execSQL("delete from " + table + " where " + mPrimaryKey + " in(" + sb + ")",
						(Object[]) primaryKeys);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeDatabase(null);
		}
	}

	/**
	 * 
	 * <p>
	 * Title: deleteByField
	 * </p>
	 * <p>
	 * Description: 根据某一个字段和值删除一行数据, 如 name="jack"
	 * </p>
	 * 
	 * @param table
	 *            表名
	 * @param field
	 *            字段名
	 * @param value
	 *            字段的值
	 * @return 返回值大于0表示删除成功
	 * 
	 * @author xwc1125
	 * @date 2015-6-20下午4:52:21
	 */
	public int deleteByField(String table, String field, String value) {
		try {
			dataBase = openDatabase();
			return dataBase.delete(table, field + "=?", new String[] { value });
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeDatabase(null);
		}
		return 0;
	}

	/**
	 * 
	 * <p>
	 * Title: deleteByCondition
	 * </p>
	 * <p>
	 * Description: 根据条件删除数据
	 * </p>
	 * 
	 * @param table
	 *            表名
	 * @param whereClause
	 *            查询语句 参数采用?
	 * @param whereArgs
	 *            参数值
	 * @return 返回值大于0表示删除成功
	 * 
	 * @author xwc1125
	 * @date 2015-6-20下午4:53:00
	 */
	public int deleteByCondition(String table, String whereClause, String[] whereArgs) {
		try {
			dataBase = openDatabase();
			return dataBase.delete(table, whereClause, whereArgs);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeDatabase(null);
		}
		return 0;
	}

	@SuppressLint("DefaultLocale")
	public int deleteSQLByClass(Class<?> clazz, String prefix, String whereClause, String[] whereArgs) {
		String tableName = clazz.getSimpleName();
		if (prefix == null) {
			tableName = "t_" + tableName.toLowerCase();
		} else {
			tableName = prefix + tableName.toLowerCase();
		}
		return deleteByCondition(tableName, whereClause, whereArgs);
	}

	// ====================判断是否存在=================//
	/**
	 * 
	 * <p>
	 * Title: isExistsById
	 * </p>
	 * <p>
	 * Description: 根据主键查看某条数据是否存在
	 * </p>
	 * 
	 * @param table
	 * @param id
	 * @return
	 * 
	 * @author xwc1125
	 * @date 2015-6-20下午4:54:34
	 */
	public Boolean isExistsById(String table, String id) {
		try {
			dataBase = openDatabase();
			return isDataExistsByField(table, mPrimaryKey, id);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeDatabase(null);
		}
		return null;
	}

	/**
	 * 
	 * <p>
	 * Title: isExistsByField
	 * </p>
	 * <p>
	 * Description: 根据某字段/值查看某条数据是否存在
	 * </p>
	 * 
	 * @param table
	 * @param field
	 * @param value
	 * @return
	 * 
	 * @author xwc1125
	 * @date 2015-6-20下午4:54:52
	 */
	public Boolean isDataExistsByField(String table, String field, String value) {
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT COUNT(*) FROM ").append(table).append(" WHERE ").append(field).append(" =?");
		try {
			dataBase = openDatabase();
			return isExistsBySQL(sql.toString(), new String[] { value });
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeDatabase(null);
		}
		return null;
	}

	/**
	 * 
	 * <p>
	 * Title: isExistsBySQL
	 * </p>
	 * <p>
	 * Description: 使用SQL语句查看某条数据是否存在
	 * </p>
	 * 
	 * @param sql
	 * @param selectionArgs
	 * @return
	 * 
	 * @author xwc1125
	 * @date 2015-6-20下午4:55:04
	 */
	public Boolean isExistsBySQL(String sql, String[] selectionArgs) {
		Cursor cursor = null;
		try {
			dataBase = openDatabase();
			if (isDBDebug) {
				Log.i(TAG, sql + "," + selectionArgs.toString());
			}
			cursor = dataBase.rawQuery(sql, selectionArgs);
			if (cursor.moveToFirst()) {
				return (cursor.getInt(0) > 0);
			} else {
				return false;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeDatabase(cursor);
		}
		return null;
	}

	// =====================查询=================//
	public Cursor query(String table, String[] columns, String selection, String[] selectionArgs, String groupBy,
			String having, String orderBy) throws Exception {
		Cursor cursor = null;
		try {
			dataBase = openDatabase();
			cursor = dataBase.query(table, columns, selection, selectionArgs, groupBy, having, orderBy);
			return cursor;
		} finally {
			closeDatabase(cursor);
		}
	}

	/**
	 * 
	 * <p>
	 * Title: queryForObject
	 * </p>
	 * <p>
	 * Description: 查询一条数据得到对象
	 * </p>
	 * 
	 * @param sql
	 * @param args
	 * @param clazz
	 * @return
	 * @throws Exception
	 * 
	 * @author xwc1125
	 * @date 2015-6-20下午4:55:17
	 */
	@SuppressWarnings("unchecked")
	public <T> T queryForObject(String sql, String[] args, Class<T> clazz) throws Exception {
		Cursor cursor = null;
		T object = null;
		try {
			dataBase = openDatabase();
			cursor = dataBase.rawQuery(sql, args);

			if (cursor.moveToFirst()) {
				// object = rowMapper.mapRow(cursor, cursor.getCount());
				object = (T) setValues2Fields(cursor, clazz);
			}
		} finally {
			closeDatabase(cursor);
		}
		return object;
	}

	public <T> List<HashMap<String, String>> queryForObject(String sql, String[] args) throws Exception {
		List<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();
		Cursor cursor = null;
		try {
			dataBase = openDatabase();
			cursor = dataBase.rawQuery(sql, args);
			// 判断游标是否为空
			if (cursor.moveToFirst()) {
				HashMap<String, String> hashMap = new HashMap<String, String>();
				// 遍历游标
				for (int i = 0; i < cursor.getCount(); i++) {
					cursor.move(i);
					String[] columnNames = cursor.getColumnNames();
					if (columnNames != null && columnNames.length > 0) {
						for (int j = 0; j < columnNames.length; j++) {
							hashMap.put(columnNames[j], cursor.getString(j));
						}
					}
					if (hashMap != null && hashMap.size() > 0) {
						list.add(hashMap);
					}
				}
			}
		} finally {
			closeDatabase(cursor);
		}
		return list;
	}

	/**
	 * 
	 * <p>
	 * Title: queryForList
	 * </p>
	 * <p>
	 * Description: 查询得到对象的list
	 * </p>
	 * 
	 * @param sql
	 * @param selectionArgs
	 * @param clazz
	 * @return
	 * 
	 * @author xwc1125
	 * @date 2015-6-20下午4:55:32
	 */
	@SuppressWarnings("unchecked")
	public <T> List<T> queryForList(String sql, String[] selectionArgs, Class<T> clazz) {
		Cursor cursor = null;
		List<T> list = null;
		try {
			dataBase = openDatabase();
			cursor = dataBase.rawQuery(sql, selectionArgs);
			list = new ArrayList<T>();
			list = cursor2VOList(cursor, clazz);
			// while (cursor.moveToNext()) {
			// list.add(rowMapper.mapRow(cursor, cursor.getPosition()));
			// }
		} finally {
			closeDatabase(cursor);
		}
		return list;
	}

	/**
	 * 
	 * <p>
	 * Title: queryForList
	 * </p>
	 * <p>
	 * Description: 查询
	 * </p>
	 * <p>
	 * 
	 * </p>
	 * 
	 * @tags @param rowMapper
	 * @tags @param sql
	 * @tags @param selectionArgs
	 * @tags @return
	 * 
	 * @author xwc1125
	 * @date 2016年8月30日 下午2:42:08
	 */
	public <T> List<T> queryForList(String sql, String[] selectionArgs, RowMapper<T> rowMapper) {
		Cursor cursor = null;
		List<T> list = null;
		try {
			dataBase = openDatabase();
			cursor = dataBase.rawQuery(sql, selectionArgs);
			list = new ArrayList<T>();
			while (cursor.moveToNext()) {
				if (rowMapper != null) {
					list.add(rowMapper.mapRow(cursor, cursor.getPosition()));
				}
			}
		} finally {
			closeDatabase(cursor);
		}
		return list;
	}

	/**
	 * 
	 * <p>
	 * Title: queryForList
	 * </p>
	 * <p>
	 * Description: 分页查询
	 * </p>
	 * <p>
	 * 
	 * </p>
	 * 
	 * @tags @param rowMapper
	 * @tags @param sql
	 * @tags @param offset 开始索引 注:第一条记录索引为0
	 * @tags @param size 步长
	 * @tags @return
	 * 
	 * @author xwc1125
	 * @date 2016年8月30日 下午2:38:24
	 */
	public <T> List<T> queryForList(String sql, int offset, int size, RowMapper<T> rowMapper) {
		Cursor cursor = null;
		List<T> list = null;
		try {
			dataBase = openDatabase();
			cursor = dataBase.rawQuery(sql + " limit ?,?",
					new String[] { String.valueOf(offset), String.valueOf(size) });
			list = new ArrayList<T>();
			while (cursor.moveToNext()) {
				if (rowMapper != null) {
					list.add(rowMapper.mapRow(cursor, cursor.getPosition()));
				}
			}
		} finally {
			closeDatabase(cursor);
		}
		return list;
	}

	/**
	 * 
	 * <p>
	 * Title: queryForList
	 * </p>
	 * <p>
	 * Description: 分页查询,得到对象的list
	 * </p>
	 * 
	 * @param sql
	 * @param offset
	 *            开始索引 注:第一条记录索引为0
	 * @param count
	 *            步长
	 * @param clazz
	 * @return
	 * 
	 * @author xwc1125
	 * @date 2015-6-20下午4:56:02
	 */
	@SuppressWarnings("unchecked")
	public <T> List<T> queryForList(String sql, int offset, int count, Class<T> clazz) {
		Cursor cursor = null;
		List<T> list = null;
		try {
			dataBase = openDatabase();
			cursor = dataBase.rawQuery(sql + " limit ?,?",
					new String[] { String.valueOf(offset), String.valueOf(count) });
			list = new ArrayList<T>();
			list = cursor2VOList(cursor, clazz);
		} finally {
			closeDatabase(cursor);
		}
		return list;
	}

	/**
	 * 分页查询
	 * 
	 * @param table
	 *            检索的表
	 * @param columns
	 *            由需要返回列的列名所组成的字符串数组，传入null会返回所有的列。
	 * @param selection
	 *            查询条件子句，相当于select语句where关键字后面的部分，在条件子句允许使用占位符"?"
	 * @param selectionArgs
	 *            对应于selection语句中占位符的值，值在数组中的位置与占位符在语句中的位置必须一致，否则就会有异常
	 * @param groupBy
	 *            对结果集进行分组的group by语句（不包括GROUP BY关键字）。传入null将不对结果集进行分组
	 * @param having
	 *            对查询后的结果集进行过滤,传入null则不过滤
	 * @param orderBy
	 *            对结果集进行排序的order by语句（不包括ORDER BY关键字）。传入null将对结果集使用默认的排序
	 * @param limit
	 *            指定偏移量和获取的记录数，相当于select语句limit关键字后面的部分,如果为null则返回所有行
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public <T> List<T> queryForList(String table, String[] columns, String selection, String[] selectionArgs,
			String groupBy, String having, String orderBy, String limit, @SuppressWarnings("rawtypes") Class clazz) {
		List<T> list = null;
		Cursor cursor = null;
		try {
			dataBase = openDatabase();
			cursor = dataBase.query(table, columns, selection, selectionArgs, groupBy, having, orderBy, limit);
			list = cursor2VOList(cursor, clazz);
		} finally {
			closeDatabase(cursor);
		}
		return list;
	}

	/**
	 * 分页查询
	 * 
	 * @param rowMapper
	 * @param table
	 *            检索的表
	 * @param columns
	 *            由需要返回列的列名所组成的字符串数组，传入null会返回所有的列。
	 * @param selection
	 *            查询条件子句，相当于select语句where关键字后面的部分，在条件子句允许使用占位符"?"
	 * @param selectionArgs
	 *            对应于selection语句中占位符的值，值在数组中的位置与占位符在语句中的位置必须一致，否则就会有异常
	 * @param groupBy
	 *            对结果集进行分组的group by语句（不包括GROUP BY关键字）。传入null将不对结果集进行分组
	 * @param having
	 *            对查询后的结果集进行过滤,传入null则不过滤
	 * @param orderBy
	 *            对结果集进行排序的order by语句（不包括ORDER BY关键字）。传入null将对结果集使用默认的排序
	 * @param limit
	 *            指定偏移量和获取的记录数，相当于select语句limit关键字后面的部分,如果为null则返回所有行
	 * @return
	 */
	public <T> List<T> queryForList(String table, String[] columns, String selection, String[] selectionArgs,
			String groupBy, String having, String orderBy, String limit, RowMapper<T> rowMapper) {
		List<T> list = null;
		Cursor cursor = null;
		try {
			dataBase = openDatabase();
			cursor = dataBase.query(table, columns, selection, selectionArgs, groupBy, having, orderBy, limit);
			list = new ArrayList<T>();
			while (cursor.moveToNext()) {
				if (rowMapper != null) {
					list.add(rowMapper.mapRow(cursor, cursor.getPosition()));
				}
			}
		} finally {
			closeDatabase(cursor);
		}
		return list;
	}

	/**
	 * Get Primary Key
	 * 
	 * @return
	 */
	public String getPrimaryKey() {
		return mPrimaryKey;
	}

	/**
	 * Set Primary Key
	 * 
	 * @param primaryKey
	 */
	public void setPrimaryKey(String primaryKey) {
		this.mPrimaryKey = primaryKey;
	}

	/**
	 * 
	 * <p>
	 * Title: getCount
	 * </p>
	 * <p>
	 * Description: 获取记录数
	 * </p>
	 * 
	 * @param sql
	 * @param args
	 * @return
	 * 
	 * @author xwc1125
	 * @date 2015-6-20下午4:56:23
	 */
	public Integer getCount(String sql, String[] args) {
		Cursor cursor = null;
		try {
			dataBase = openDatabase();
			cursor = dataBase.rawQuery("select count(*) from (" + sql + ")", args);
			if (cursor.moveToNext()) {
				return cursor.getInt(0);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeDatabase(cursor);
		}
		return 0;
	}

	/**
	 * 
	 * <p>
	 * Title: closeDatabase
	 * </p>
	 * <p>
	 * Description: 关闭数据库
	 * </p>
	 * 
	 * @param cursor
	 * 
	 * @author xwc1125
	 * @date 2015-6-21上午10:45:08
	 */
	public void closeDatabase(Cursor cursor) {
		try {
			if (cursor != null && !cursor.isClosed()) {
				cursor.close();
			}
			if (!isTransaction) {
				if (dataBase.isOpen()) {
					dataBase.close();
				}
			}
		} catch (Exception ex) {
		}
	}

	/**
	 * 
	 * <p>
	 * Title: isTableExist
	 * </p>
	 * <p>
	 * Description: 判断表是否存在
	 * </p>
	 * 
	 * @param tabName
	 * @return
	 * @author xwc1125
	 * @date 2015-6-8下午1:52:41
	 */
	public boolean isTableExist(String tabName) throws Exception {
		Cursor cursor = null;
		boolean result = false;
		try {
			if (tabName == null) {
				return false;
			}
			String sql = "select count(*) as c from sqlite_master where type ='table' and name ='" + tabName.trim()
					+ "' ";
			cursor = dataBase.rawQuery(sql, null);
			if (cursor.moveToNext()) {
				int count = cursor.getInt(0);
				if (count > 0) {
					result = true;
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
		} finally {
			closeDatabase(cursor);
		}
		return result;
	}

	/**
	 * 
	 * <p>
	 * Title: createTableIfNotExist
	 * </p>
	 * <p>
	 * Description: 如果表不存在，则创建
	 * </p>
	 * 
	 * @param tabName
	 * @param createDBSql
	 * @author xwc1125
	 * @date 2015-6-8下午1:52:24
	 */
	public void createTableIfNotExist(String tabName, String createDBSql) throws Exception {
		try {
			if (!isTableExist(tabName)) {
				SQLiteDatabase db = openDatabase();
				db.execSQL(createDBSql);
				db.close();
				LogUtils.i(TAG, "createTableIfNotExist创建" + tabName + "数据库成功！", isDBDebug);
			}
		} catch (Exception e) {
			// TODO: handle exception
		} finally {
			closeDatabase(null);
		}
	}

	/**
	 * 
	 * <p>
	 * Title: cursor2VOList
	 * </p>
	 * <p>
	 * Description: 通过Cursor转换成对应的VO集合。注意：Cursor里的字段名（可用别名）必须要和VO的属性名一致
	 * </p>
	 * 
	 * @param c
	 * @param clazz
	 * @return
	 * 
	 * @author xwc1125
	 * @date 2015-6-21上午10:45:50
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private List cursor2VOList(Cursor c, Class clazz) {
		if (c == null) {
			return null;
		}
		List list = new LinkedList();
		Object obj;
		try {
			while (c.moveToNext()) {
				obj = setValues2Fields(c, clazz);
				list.add(obj);
			}
			return list;
		} catch (Exception e) {
			e.printStackTrace();
			Log.e(TAG, "ERROR @：cursor2VOList");
			return null;
		} finally {
			c.close();
		}
	}

	/**
	 * 
	 * <p>
	 * Title: setValues2Fields
	 * </p>
	 * <p>
	 * Description: 把值设置进类属性里
	 * </p>
	 * 
	 * @param c
	 * @param clazz
	 * @return
	 * @throws Exception
	 * 
	 * @author xwc1125
	 * @date 2015-6-21上午10:46:11
	 */
	@SuppressWarnings("rawtypes")
	private Object setValues2Fields(Cursor c, Class clazz) throws Exception {
		String[] columnNames = c.getColumnNames();// 字段数组
		Object obj = clazz.newInstance();
		Field[] fields = clazz.getDeclaredFields();

		for (Field _field : fields) {
			Class<? extends Object> typeClass = _field.getType();// 属性类型
			for (int j = 0; j < columnNames.length; j++) {
				String columnName = columnNames[j];
				typeClass = getBasicClass(typeClass);
				boolean isBasicType = isBasicType(typeClass);
				if (isBasicType) {
					if (columnName.equalsIgnoreCase(_field.getName())) {// 是基本类型

						String _str = c.getString(c.getColumnIndex(columnName));
						if (_str == null) {
							break;
						}
						_str = _str == null ? "" : _str;
						Constructor<? extends Object> cons = typeClass.getConstructor(String.class);
						Object attribute = cons.newInstance(_str);
						_field.setAccessible(true);
						_field.set(obj, attribute);
						break;
					}
				} else {
					Object obj2 = setValues2Fields(c, typeClass);// 递归
					_field.set(obj, obj2);
					break;
				}
			}
		}
		return obj;
	}

	/**
	 * 
	 * <p>
	 * Title: isBasicType
	 * </p>
	 * <p>
	 * Description: 判断是不是基本类型
	 * </p>
	 * 
	 * @param typeClass
	 * @return
	 * 
	 * @author xwc1125
	 * @date 2015-6-21上午10:46:27
	 */
	@SuppressWarnings("rawtypes")
	private boolean isBasicType(Class typeClass) {
		if (typeClass.equals(Integer.class) || typeClass.equals(Long.class) || typeClass.equals(Float.class)
				|| typeClass.equals(Double.class) || typeClass.equals(Boolean.class) || typeClass.equals(Byte.class)
				|| typeClass.equals(Short.class) || typeClass.equals(String.class)) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 获得包装类
	 * 
	 * @param typeClass
	 * @return
	 */
	@SuppressWarnings("all")
	public static Class<? extends Object> getBasicClass(Class typeClass) {
		Class _class = basicMap.get(typeClass);
		if (_class == null)
			_class = typeClass;
		return _class;
	}

	@SuppressWarnings("rawtypes")
	private static Map<Class, Class> basicMap = new HashMap<Class, Class>();
	static {
		basicMap.put(int.class, Integer.class);
		basicMap.put(long.class, Long.class);
		basicMap.put(float.class, Float.class);
		basicMap.put(double.class, Double.class);
		basicMap.put(boolean.class, Boolean.class);
		basicMap.put(byte.class, Byte.class);
		basicMap.put(short.class, Short.class);
		basicMap.put(Date.class, Date.class);
		basicMap.put(String.class, String.class);
	}

	/**
	 * 
	 * @Title: openDatabase
	 * @Description: 打开数据库 注:SQLiteDatabase资源一旦被关闭,该底层会重新产生一个新的SQLiteDatabase
	 * @return
	 * 
	 * @author xwc1125
	 * @date 2015-6-20下午4:38:14
	 */
	public SQLiteDatabase openDatabase() {
		return getDatabaseHelper().getWritableDatabase();
	}

	/**
	 * 
	 * @Title: getDatabaseHelper
	 * @Description: 获取DataBaseHelper
	 * @return
	 * 
	 * @author xwc1125
	 * @date 2015-6-20下午4:38:27
	 */
	public DataBaseHelper getDatabaseHelper() {
		return new DataBaseHelper(mContext, this.databaseName, null, this.version, sqlMap);
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
	public void setDirName(String dirName) {
		getDatabaseHelper().setDirName(dirName);
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
	public void setDataBaseFile(File file) {
		getDatabaseHelper().setDataBaseFile(file);
	}
}
