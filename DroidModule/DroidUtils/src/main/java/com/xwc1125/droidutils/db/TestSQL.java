/**
 * <p>
 * Title: TestSQL.java
 * </p>
 * <p>
 * Description: TODO(describe the file) 
 * </p>
 * <p>
 * 
 * </p>
 * @Copyright: Copyright (c) Jul 25, 2016
 * @author xwc1125
 * @date Jul 25, 201610:37:05 PM
 * @version V1.0
 */
package com.xwc1125.droidutils.db;

import java.util.HashMap;
import java.util.Map;

import android.content.Context;

/**
 * <p>
 * Title: TestSQL
 * </p>
 * <p>
 * Description: TODO(describe the types)
 * </p>
 * <p>
 * 
 * </p>
 * 
 * @author xwc1125
 * @date Jul 25, 201610:37:05 PM
 * 
 */
public class TestSQL {
	public static void test(Context context) {
		Map<String, Object> sqlMap = new HashMap<String, Object>();
		String messageSql = "create table if not exists Messages (_id integer primary key autoincrement,"
				+ "threadId long(32),date long(32),type int(10),"
				+ "read int(10),messageType int(10),messgeForm int(10),"
				+ "status int(10),resendReason varchar(100),sub varchar(200),"
				+ "ct_l varchar(200),m_size long(32),contactName varchar(50),"
				+ "contactNumber varchar(50),flag int(10))";
		String partsSql = "create table if not exists Parts (_id integer primary key autoincrement,"
				+ "mid long(32),"
				+ "ct varchar(100),name varchar(500),"
				+ "path varchar(500),text varchar(1000))";
		String threadsSql = "create table if not exists Threads (_id integer primary key autoincrement,"
				+ "threadId long(32),date long(32),"
				+ "messageType int(10),type int(10),msgCount int(10),sub varchar(500),"
				+ "address varchar(500),name varchar(500),read int(10),hasAttachment int(10),"
				+ "ct_l varchar(200),msgSize long(32),threadType int(10))";

		sqlMap.put("messages", messageSql);
		sqlMap.put("parts", partsSql);
		sqlMap.put("threads", threadsSql);
		SQLiteManager sqLiteManager = SQLiteManager.getInstance(context,
				"zzxmessage.db", true, sqlMap);
		sqLiteManager.setDirName("xwc1225");
		sqLiteManager.openDatabase();//如果需要立马执行，需要调用一下此方法
		// sqLiteManager.createTable(
		// Zzx_Messages.class, null, null);
	}
}
