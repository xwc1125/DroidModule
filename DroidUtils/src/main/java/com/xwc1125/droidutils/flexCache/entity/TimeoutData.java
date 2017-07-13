/**
 * <p>
 * Title: TimeoutData.java
 * </p>
 * <p>
 * Description: TODO(describe the file) 
 * </p>
 * <p>
 * 
 * </p>
 * @Copyright: Copyright (c) 2015
 * @author xwc1125
 * @date 2016年11月2日 上午11:03:12
 * @version V1.0
 */
package com.xwc1125.droidutils.flexCache.entity;

import java.util.Date;

/**
 * <p>
 * Title: TimeoutData
 * </p>
 * <p>
 * Description: TODO(describe the types)
 * </p>
 * <p>
 * 
 * </p>
 * 
 * @author xwc1125
 * @date 2016年11月2日 上午11:03:12
 * 
 */
public class TimeoutData {

	private Date date;
	private long timeout;

	public TimeoutData(Date date, long timeout) {
		this.date = date;
		this.timeout = timeout;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public long getTimeout() {
		return timeout;
	}

	public void setTimeout(long timeout) {
		this.timeout = timeout;
	}

	@Override
	public String toString() {
		return "TimeoutData [date=" + date + ", timeout=" + timeout + "]";
	}

}
