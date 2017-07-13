/**
 * <p>
 * Title: SDCardSQLiteOpenHelper.java
 * </p>
 * <p>
 * Description:  SQLite数据库针对SD卡的核心帮助类
 * </p>
 * <p>
 * 
 * </p>
 * @Copyright: Copyright (c) 2016
 * @author zhangqy
 * @date 2016年8月16日 上午10:16:14
 * @version V1.0
 */
package com.xwc1125.droidutils.db.helper.impl;

import java.io.File;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteException;
import android.os.Environment;
import android.util.Log;

import com.xwc1125.droidutils.LogUtils;
import com.xwc1125.droidutils.UtilsConfig;

/**
 * SQLite数据库针对SD卡的核心帮助类
 * <p>
 * Title: SDCardSQLiteOpenHelper
 * </p>
 * <p>
 * Description: 该类主要用于直接设置,创建,升级数据库以及打开,关闭数据库资源
 * </p>
 * <p>
 * Company:
 * </p>
 * 
 * @author xwc1125
 * @date 2015-6-7上午9:00:10
 */
public abstract class SDCardSQLiteOpenHelper {
	private static final String TAG = SDCardSQLiteOpenHelper.class.getSimpleName();
	private static boolean isDebug = UtilsConfig.isDebug;
	private final String mName;
	private final CursorFactory mFactory;
	private final int mNewVersion;

	private static String dirName = "xwc1125";
	private static String db_dirName = "database";
	private static File dbFile;

	private SQLiteDatabase mDatabase = null;
	private boolean mIsInitializing = false;

	/**
	 * 外置SD卡的根路径
	 */
	private static File SD_DIR = Environment.getExternalStorageDirectory();
	/**
	 * 内置存储的根路径
	 */
	private static File DATA_DIR = Environment.getDataDirectory();
	/**
	 * 当前存储状态【有外置SD卡还是只有内置存储】
	 */
	private static String STORAGE_STATE = Environment.getExternalStorageState();

	/**
	 * Create a helper object to create, open, and/or manage a database. The
	 * database is not actually created or opened until one of
	 * {@link #getWritableDatabase} or {@link #getReadableDatabase} is called.
	 * 
	 * @param context
	 *            to use to open or create the database
	 * @param name
	 *            of the database file, or null for an in-memory database
	 * @param factory
	 *            to use for creating cursor objects, or null for the default
	 * @param version
	 *            number of the database (starting at 1); if the database is
	 *            older, {@link #onUpgrade} will be used to upgrade the database
	 */
	public SDCardSQLiteOpenHelper(Context context, String name, CursorFactory factory, int version) {
		if (version < 1) {
			throw new IllegalArgumentException("Version must be >= 1, was " + version);
		}
		mName = name;
		mFactory = factory;
		mNewVersion = version;
	}

	/**
	 * Create and/or open a database that will be used for reading and writing.
	 * Once opened successfully, the database is cached, so you can call this
	 * method every time you need to write to the database. Make sure to call
	 * {@link #close} when you no longer need it.
	 * 
	 * <p>
	 * Errors such as bad permissions or a full disk may cause this operation to
	 * fail, but future attempts may succeed if the problem is fixed.
	 * </p>
	 * 
	 * @throws SQLiteException
	 *             if the database cannot be opened for writing
	 * @return a read/write database object valid until {@link #close} is called
	 */
	public synchronized SQLiteDatabase getWritableDatabase() {
		if (mDatabase != null && mDatabase.isOpen() && !mDatabase.isReadOnly()) {
			return mDatabase; // The database is already open for business
		}
		if (mIsInitializing) {
			throw new IllegalStateException("getWritableDatabase called recursively");
		}
		// If we have a read-only database open, someone could be using it
		// (though they shouldn't), which would cause a lock to be held on
		// the file, and our attempts to open the database read-write would
		// fail waiting for the file lock. To prevent that, we acquire the
		// lock on the read-only database, which shuts out other users.
		boolean success = false;
		SQLiteDatabase db = null;
		try {
			mIsInitializing = true;
			if (mName == null) {
				db = SQLiteDatabase.create(null);
			} else {
				String path = getDatabasePath(mName).getPath();
				db = SQLiteDatabase.openOrCreateDatabase(path, mFactory);
			}

			int version = db.getVersion();
			if (version != mNewVersion) {
				db.beginTransaction();
				try {
					if (version == 0) {
						onCreate(db);
					} else {
						onUpgrade(db, version, mNewVersion);
					}
					db.setVersion(mNewVersion);
					db.setTransactionSuccessful();
				} finally {
					db.endTransaction();
				}
			}
			onOpen(db);
			success = true;
			return db;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			mIsInitializing = false;
			if (success) {
				if (mDatabase != null) {
					try {
						mDatabase.close();
					} catch (Exception e) {
					}
				}
				mDatabase = db;
			} else {
				if (db != null && db.isOpen()) {
					db.close();
				}
			}
		}
		return db;
	}

	/**
	 * Create and/or open a database. This will be the same object returned by
	 * {@link #getWritableDatabase} unless some problem, such as a full disk,
	 * requires the database to be opened read-only. In that case, a read-only
	 * database object will be returned. If the problem is fixed, a future call
	 * to {@link #getWritableDatabase} may succeed, in which case the read-only
	 * database object will be closed and the read/write object will be returned
	 * in the future.
	 * 
	 * @throws SQLiteException
	 *             if the database cannot be opened
	 * @return a database object valid until {@link #getWritableDatabase} or
	 *         {@link #close} is called.
	 */
	public synchronized SQLiteDatabase getReadableDatabase() {
		if (mDatabase != null && mDatabase.isOpen()) {
			return mDatabase; // The database is already open for business
		}
		if (mIsInitializing) {
			throw new IllegalStateException("getReadableDatabase called recursively");
		}
		try {
			return getWritableDatabase();
		} catch (SQLiteException e) {
			if (mName == null) {
				throw e; // Can't open a temp database read-only!
			}
			Log.e(TAG, "Couldn't open " + mName + " for writing (will try read-only):", e);
		}
		SQLiteDatabase db = null;
		try {
			mIsInitializing = true;
			String path = getDatabasePath(mName).getPath();
			db = SQLiteDatabase.openDatabase(path, mFactory, SQLiteDatabase.OPEN_READWRITE);
			if (db.getVersion() != mNewVersion) {
				throw new SQLiteException("Can't upgrade read-only database from version " + db.getVersion() + " to "
						+ mNewVersion + ": " + path);
			}
			onOpen(db);
			Log.w(TAG, "Opened " + mName + " in read-only mode");
			mDatabase = db;
			return mDatabase;
		} finally {
			mIsInitializing = false;
			if (db != null && db != mDatabase) {
				db.close();
			}
		}
	}

	/**
	 * Close any open database object.
	 */
	public synchronized void close() {
		if (mIsInitializing) {
			throw new IllegalStateException("Closed during initialization");
		}
		if (mDatabase != null && mDatabase.isOpen()) {
			mDatabase.close();
			mDatabase = null;
		}
	}

	public File getDatabasePath(String name) {
		File f;
		if (dbFile == null) {
			String EXTERN_PATH = null;
			File folderPath = null;
			if (STORAGE_STATE.equals(Environment.MEDIA_MOUNTED)) {
				folderPath = SD_DIR;
			} else {
				folderPath = DATA_DIR;
			}
			String dbPath = "/" + dirName + "/" + db_dirName + "/";
			EXTERN_PATH = folderPath.getAbsolutePath() + dbPath;
			f = new File(EXTERN_PATH);
		} else {
			f = dbFile;
		}
		boolean isSucc = true;
		if (!f.exists()) {
			isSucc = f.mkdirs();
		}
		LogUtils.i(TAG, isSucc + "", isDebug);
		return new File(f.getAbsolutePath() + "/" + name);
	}

	/**
	 * Called when the database is created for the first time. This is where the
	 * creation of tables and the initial population of the tables should
	 * happen.
	 * 
	 * @param db
	 *            The database.
	 */
	public abstract void onCreate(SQLiteDatabase db);

	/**
	 * Called when the database needs to be upgraded. The implementation should
	 * use this method to drop tables, add tables, or do anything else it needs
	 * to upgrade to the new schema version.
	 * 
	 * <p>
	 * The SQLite ALTER TABLE documentation can be found
	 * <a href="http://sqlite.org/lang_altertable.html">here</a>. If you add new
	 * columns you can use ALTER TABLE to insert them into a live table. If you
	 * rename or remove columns you can use ALTER TABLE to rename the old table,
	 * then create the new table and then populate the new table with the
	 * contents of the old table.
	 * 
	 * @param db
	 *            The database.
	 * @param oldVersion
	 *            The old database version.
	 * @param newVersion
	 *            The new database version.
	 */
	public abstract void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion);

	/**
	 * Called when the database has been opened. Override method should check
	 * {@link SQLiteDatabase#isReadOnly} before updating the database.
	 * 
	 * @param db
	 *            The database.
	 */
	public void onOpen(SQLiteDatabase db) {

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
	@SuppressWarnings("static-access")
	public void setDirName(String dirName) {
		this.dirName = dirName;
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
	@SuppressWarnings("static-access")
	public void setDataBaseFile(File file) {
		this.dbFile = file;
	}

}
