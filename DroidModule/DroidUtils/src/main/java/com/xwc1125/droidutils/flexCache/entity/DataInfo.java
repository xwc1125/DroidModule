/**
 * <p>
 * Title: FlexEntity.java
 * </p>
 * <p>
 * Description: TODO(describe the file) 
 * </p>
 * <p>
 * 
 * </p>
 * @Copyright: Copyright (c) 2015
 * @author xwc1125
 * @date 2016年11月2日 上午11:23:32
 * @version V1.0
 */
package com.xwc1125.droidutils.flexCache.entity;

import com.xwc1125.droidutils.flexCache.core.FlexCacheConstants;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * Title: FlexEntity
 * </p>
 * <p>
 * Description: TODO(describe the types)
 * </p>
 * <p>
 * 
 * </p>
 * 
 * @author xwc1125
 * @date 2016年11月2日 上午11:23:32
 * 
 */
@SuppressWarnings("rawtypes")
public class DataInfo {
	private Object obj;
	private List list;
	private Map map;
	private FlexCacheConstants.DATA_TYPE dataType;
	private String tag;

	public Object getObj() {
		return obj;
	}

	public void setObj(Object obj) {
		this.obj = obj;
	}

	public List getList() {
		return list;
	}

	public void setList(List list) {
		this.list = list;
	}

	public Map getMap() {
		return map;
	}

	public void setMap(Map map) {
		this.map = map;
	}

	public FlexCacheConstants.DATA_TYPE getDataType() {
		return dataType;
	}

	public void setDataType(FlexCacheConstants.DATA_TYPE dataType) {
		this.dataType = dataType;
	}

	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}

	@Override
	public String toString() {
		return "DataInfo [obj=" + obj + ", list=" + list + ", map=" + map
				+ ", dataType=" + dataType + ", tag=" + tag + "]";
	}

}
