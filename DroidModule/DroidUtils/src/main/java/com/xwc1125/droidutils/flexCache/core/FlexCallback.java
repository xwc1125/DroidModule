/**
 * <p>
 * Title: FlexCallBack.java
 * </p>
 * <p>
 * Description: TODO(describe the file) 
 * </p>
 * <p>
 * 
 * </p>
 * @Copyright: Copyright (c) 2015
 * @author xwc1125
 * @date 2016年11月2日 下午3:26:33
 * @version V1.0
 */
package com.xwc1125.droidutils.flexCache.core;

import com.xwc1125.droidutils.flexCache.entity.DataInfo;
import com.xwc1125.droidutils.flexCache.entity.TimeoutData;

/**
 * <p>
 * Title: FlexCallBack
 * </p>
 * <p>
 * Description: TODO(describe the types)
 * </p>
 * <p>
 * 
 * </p>
 * 
 * @author xwc1125
 * @date 2016年11月2日 下午3:26:33
 * 
 */
public interface FlexCallback {
	public void timeOut(DataInfo dataInfo, TimeoutData timeoutData);
}
