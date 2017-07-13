/**
 * <p>
 * Title: RowMapper.java
 * </p>
 * <p>
 * Description: TODO(describe the file) 
 * </p>
 * <p>
 * 
 * </p>
 * @Copyright: Copyright (c) 2016年8月30日
 * @author xwc1125
 * @date 2016年8月30日 下午2:37:47
 * @version V1.0
 */
package com.xwc1125.droidutils.db;

import android.database.Cursor;

/**
 * <p>
 * Title: RowMapper
 * </p>
 * <p>
 * Description: TODO(describe the types)
 * </p>
 * <p>
 * 
 * </p>
 * 
 * @author xwc1125
 * @date 2016年8月30日 下午2:37:47
 * 
 */
public interface RowMapper<T> {
	/**
	 * 
	 * <p>
	 * Title: mapRow
	 * </p>
	 * <p>
	 * Description:
	 * RowMapper可以将数据中的每一行封装成用户定义的类，在数据库查询中，如果返回的类型是用户自定义的类型则需要包装，如果是Java自定义的类型，如：String则不需要
	 * </p>
	 * <p>
	 * 类似回调的方法
	 * </p>
	 * 
	 * @tags @param cursor 游标
	 * @tags @param index 下标索引
	 * @tags @return
	 * 
	 * @author xwc1125
	 * @date 2016年8月30日 下午3:21:02
	 */
	public T mapRow(Cursor cursor, int index);
}
