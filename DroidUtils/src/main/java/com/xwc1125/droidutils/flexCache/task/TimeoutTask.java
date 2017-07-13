/**
 * <p>
 * Title: TimeoutTask.java
 * </p>
 * <p>
 * Description: TODO(describe the file) 
 * </p>
 * <p>
 * 
 * </p>
 * @Copyright: Copyright (c) 2015
 * @author xwc1125
 * @date 2016年11月2日 上午11:04:12
 * @version V1.0
 */
package com.xwc1125.droidutils.flexCache.task;

import com.xwc1125.droidutils.flexCache.core.FlexCallback;
import com.xwc1125.droidutils.flexCache.entity.DataInfo;
import com.xwc1125.droidutils.flexCache.entity.TimeoutData;

import java.util.Date;
import java.util.Map;
import java.util.Set;

/**
 * <p>
 * Title: TimeoutTask
 * </p>
 * <p>
 * Description: 过期任务
 * </p>
 * <p>
 * 
 * </p>
 * 
 * @author xwc1125
 * @date 2016年11月2日 上午11:04:12
 * 
 */
public class TimeoutTask implements Runnable {
	private Map<String, TimeoutData> timeMap;
	private Map<String, DataInfo> flexMap;
	private FlexCallback callBack;

	/**
	 * <p>
	 * Title:
	 * </p>
	 * <p>
	 * Description:
	 * </p>
	 * 
	 * @param timeMap
	 * @param flexMap
	 * @param callBack
	 */
	public TimeoutTask(Map<String, TimeoutData> timeMap,
			Map<String, DataInfo> flexMap, FlexCallback callBack) {
		this.timeMap = timeMap;
		this.flexMap = flexMap;
		this.callBack = callBack;
	}

	public void run() {
		while (true) {
			Set<String> set = timeMap.keySet();
			Object[] objs = set.toArray(new Object[set.size()]);
			for (Object obj : objs) {
				TimeoutData data = (TimeoutData) timeMap.get(obj);
				long time = (new Date().getTime() - data.getDate().getTime()) / 1000;
				if (time > data.getTimeout() && data.getTimeout() != 0) {
					if (callBack != null) {
						callBack.timeOut(flexMap.get(obj), data);
					}
					timeMap.remove(obj);
					flexMap.remove(obj);
				}
			}
			try {
				Thread.sleep(1000 * 60);
			} catch (InterruptedException e) {
				e.printStackTrace();
				break;
			}
		}
	}
}
