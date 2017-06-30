package com.xwc1125.droidutils.flexCache;

import com.xwc1125.droidutils.flexCache.core.FlexCacheConstants;
import com.xwc1125.droidutils.flexCache.core.FlexCallback;
import com.xwc1125.droidutils.flexCache.core.IFlexCache;
import com.xwc1125.droidutils.flexCache.entity.DataInfo;
import com.xwc1125.droidutils.flexCache.entity.TimeoutData;
import com.xwc1125.droidutils.flexCache.task.TimeoutTask;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@SuppressWarnings("rawtypes")
public class LocalFlexCache implements IFlexCache {
	private static Map<String, TimeoutData> timeMap = new HashMap<String, TimeoutData>();
	private static Map<String, DataInfo> flexMap = new HashMap<String, DataInfo>();
	private static LocalFlexCache instance;
	private FlexCallback callBack;

	private LocalFlexCache(FlexCallback callBack) {
		new Thread(new TimeoutTask(timeMap, flexMap, callBack)).start();
		this.callBack = callBack;
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
	 * @return
	 * 
	 * @author xwc1125
	 * @date 2016年11月2日 上午11:06:48
	 */
	public static synchronized LocalFlexCache getInstance(FlexCallback callBack) {
		if (instance == null) {
			instance = new LocalFlexCache(callBack);
		}
		return instance;
	}

	// =========================Get==========================
	/**
	 * 
	 * <p>
	 * Title: get
	 * </p>
	 * <p>
	 * Description: 获取单个对象
	 * </p>
	 * <p>
	 * 
	 * </p>
	 * 
	 * @param key
	 * @return
	 * 
	 * @author xwc1125
	 * @date 2016年11月2日 下午2:36:21
	 */
	public Object get(String key) {
		try {
			if (isTimeOut(key)) {
				delete(key);
				return null;
			}
			DataInfo dataInfo = flexMap.get(key);
			if (dataInfo != null && dataInfo.getDataType() == FlexCacheConstants.DATA_TYPE.OBJ) {
				return dataInfo.getObj();
			}
		} catch (Throwable e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 
	 * <p>
	 * Title: getList
	 * </p>
	 * <p>
	 * Description: 获取list数据
	 * </p>
	 * <p>
	 * 
	 * </p>
	 * 
	 * @param key
	 * @return
	 * 
	 * @author xwc1125
	 * @date 2016年11月2日 下午2:36:36
	 */
	public List getList(String key) {
		try {
			if (isTimeOut(key)) {
				delete(key);
				return null;
			}
			DataInfo dataInfo = flexMap.get(key);
			if (dataInfo != null && dataInfo.getDataType() == FlexCacheConstants.DATA_TYPE.LIST) {
				return dataInfo.getList();
			}
		} catch (Throwable e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 
	 * <p>
	 * Title: getMap
	 * </p>
	 * <p>
	 * Description: 获取hashmap数据
	 * </p>
	 * <p>
	 * 
	 * </p>
	 * 
	 * @param key
	 * @return
	 * 
	 * @author xwc1125
	 * @date 2016年11月2日 下午2:36:48
	 */
	public Map getMap(String key) {
		try {
			if (isTimeOut(key)) {
				delete(key);
				return null;
			}
			DataInfo dataInfo = flexMap.get(key);
			if (dataInfo != null && dataInfo.getDataType() == FlexCacheConstants.DATA_TYPE.MAP) {
				return dataInfo.getMap();
			}
		} catch (Throwable e) {
			e.printStackTrace();
		}
		return null;
	}

	// =========================Set==========================
	/**
	 * 
	 * <p>
	 * Title: set
	 * </p>
	 * <p>
	 * Description: 设置单个对象
	 * </p>
	 * <p>
	 * 
	 * </p>
	 * 
	 * @param key
	 * @param obj
	 * 
	 * @author xwc1125
	 * @date 2016年11月2日 下午2:37:08
	 */
	public int set(String key, Object obj) {
		return set(key, obj, 0);
	}

	public int set(String key, Object obj, long timeout) {
		return set(key, null, obj, timeout);
	}

	/**
	 * 
	 * <p>
	 * Title: set
	 * </p>
	 * <p>
	 * Description:
	 * </p>
	 * <p>
	 * 
	 * </p>
	 * 
	 * @param key
	 * @param obj
	 * @param timeout
	 *            秒
	 * 
	 * @author xwc1125
	 * @date 2016年11月2日 下午2:37:19
	 */
	public int set(String key, String tag, Object obj, long timeout) {
		try {
			if (isEmpty(key)) {
				return FlexCacheConstants.EMPTY_KEY;
			}
			DataInfo dataInfo = new DataInfo();
			dataInfo.setObj(obj);
			if (!isEmpty(tag)) {
				dataInfo.setTag(tag);
			}
			dataInfo.setDataType(FlexCacheConstants.DATA_TYPE.OBJ);
			flexMap.put(key, dataInfo);
			timeMap.put(key, new TimeoutData(new Date(), timeout));
			return FlexCacheConstants.SUCC;
		} catch (Throwable e) {
			e.printStackTrace();
		}
		return FlexCacheConstants.ERR;
	}

	/**
	 * 
	 * <p>
	 * Title: setMap
	 * </p>
	 * <p>
	 * Description: TODO(describe the methods)
	 * </p>
	 * <p>
	 * 
	 * </p>
	 * 
	 * @param key
	 * @param map
	 * 
	 * @author xwc1125
	 * @date 2016年11月2日 下午2:37:38
	 */
	public int setMap(String key, Map map) {
		return setMap(key, map, 0);
	}

	public int setMap(String key, Map map, long timeout) {
		return setMap(key, null, map, timeout);
	}

	/**
	 * 
	 * <p>
	 * Title: setMap
	 * </p>
	 * <p>
	 * Description: TODO(describe the methods)
	 * </p>
	 * <p>
	 * 
	 * </p>
	 * 
	 * @param key
	 * @param map
	 * @param timeout
	 *            秒
	 * 
	 * @author xwc1125
	 * @date 2016年11月2日 下午2:37:43
	 */
	public int setMap(String key, String tag, Map map, long timeout) {
		try {
			if (isEmpty(key)) {
				return FlexCacheConstants.EMPTY_KEY;
			}
			DataInfo dataInfo = new DataInfo();
			dataInfo.setMap(map);
			if (!isEmpty(tag)) {
				dataInfo.setTag(tag);
			}
			dataInfo.setDataType(FlexCacheConstants.DATA_TYPE.MAP);
			flexMap.put(key, dataInfo);
			timeMap.put(key, new TimeoutData(new Date(), timeout));
			return FlexCacheConstants.SUCC;
		} catch (Throwable e) {
			e.printStackTrace();
		}
		return FlexCacheConstants.ERR;
	}

	/**
	 * 
	 * <p>
	 * Title: setList
	 * </p>
	 * <p>
	 * Description: TODO(describe the methods)
	 * </p>
	 * <p>
	 * 
	 * </p>
	 * 
	 * @param key
	 * @param list
	 * 
	 * @author xwc1125
	 * @date 2016年11月2日 下午2:37:48
	 */
	public int setList(String key, List list) {
		return setList(key, list, 0);
	}

	public int setList(String key, List list, long timeout) {
		return setList(key, null, list, timeout);
	}

	/**
	 * 
	 * <p>
	 * Title: setList
	 * </p>
	 * <p>
	 * Description: TODO(describe the methods)
	 * </p>
	 * <p>
	 * 
	 * </p>
	 * 
	 * @param key
	 * @param list
	 * @param timeout
	 *            秒
	 * 
	 * @author xwc1125
	 * @date 2016年11月2日 下午2:37:52
	 */
	public int setList(String key, String tag, List list, long timeout) {
		try {
			if (isEmpty(key)) {
				return FlexCacheConstants.EMPTY_KEY;
			}
			DataInfo dataInfo = new DataInfo();
			dataInfo.setList(list);
			if (!isEmpty(tag)) {
				dataInfo.setTag(tag);
			}
			dataInfo.setDataType(FlexCacheConstants.DATA_TYPE.LIST);
			flexMap.put(key, dataInfo);
			timeMap.put(key, new TimeoutData(new Date(), timeout));
			return FlexCacheConstants.SUCC;
		} catch (Throwable e) {
			e.printStackTrace();
		}
		return FlexCacheConstants.ERR;
	}

	// ===========================insert========================
	/**
	 * 
	 * <p>
	 * Title: ins
	 * </p>
	 * <p>
	 * Description: TODO(describe the methods)
	 * </p>
	 * <p>
	 * 
	 * </p>
	 * 
	 * @param key
	 * @param obj
	 * 
	 * @author xwc1125
	 * @date 2016年11月2日 下午4:34:35
	 */
	public int ins(String key, Object obj) {
		return ins(key, obj, 0);
	}

	public int ins(String key, Object obj, long timeout) {
		return ins(key, null, obj, timeout);
	}

	/**
	 * 
	 * <p>
	 * Title: ins
	 * </p>
	 * <p>
	 * Description: TODO(describe the methods)
	 * </p>
	 * <p>
	 * 
	 * </p>
	 * 
	 * @param key
	 * @param obj
	 * @param timeout
	 * 
	 * @author xwc1125
	 * @date 2016年11月2日 下午4:34:39
	 */
	public int ins(String key, String tag, Object obj, long timeout) {
		try {
			if (isEmpty(key)) {
				return FlexCacheConstants.EMPTY_KEY;
			}
			if (isExsit(key)) {
				DataInfo dataInfo = flexMap.get(key);
				FlexCacheConstants.DATA_TYPE dataType = dataInfo.getDataType();
				if (dataType != FlexCacheConstants.DATA_TYPE.OBJ) {
					return FlexCacheConstants.FAIL;
				}
				Object oldObject = dataInfo.getObj();
				if (oldObject instanceof String) {
					oldObject = oldObject + "" + obj;
				} else if (oldObject instanceof Integer) {
					oldObject = (Integer) oldObject + (Integer) obj;
				}
				dataInfo.setObj(oldObject);
				if (!isEmpty(tag)) {
					dataInfo.setTag(tag);
				}
				dataInfo.setDataType(FlexCacheConstants.DATA_TYPE.OBJ);
				flexMap.put(key, dataInfo);
				timeMap.put(key, new TimeoutData(new Date(), timeout));
			} else {
				DataInfo dataInfo = new DataInfo();
				dataInfo.setObj(obj);
				if (!isEmpty(tag)) {
					dataInfo.setTag(tag);
				}
				dataInfo.setDataType(FlexCacheConstants.DATA_TYPE.OBJ);
				flexMap.put(key, dataInfo);
				timeMap.put(key, new TimeoutData(new Date(), timeout));
			}
			return FlexCacheConstants.SUCC;
		} catch (Throwable e) {
			e.printStackTrace();
		}
		return FlexCacheConstants.ERR;
	}

	/**
	 * 
	 * <p>
	 * Title: insMap
	 * </p>
	 * <p>
	 * Description: TODO(describe the methods)
	 * </p>
	 * <p>
	 * 
	 * </p>
	 * 
	 * @param key
	 * @param map
	 * @return
	 * 
	 * @author xwc1125
	 * @date 2016年11月2日 下午5:12:16
	 */
	public int insMap(String key, Map<?, ?> map) {
		return insMap(key, map, 0);
	}

	public int insMap(String key, Map<?, ?> map, long timeout) {
		return insMap(key, null, map, timeout);
	}

	/**
	 * 
	 * <p>
	 * Title: insMap
	 * </p>
	 * <p>
	 * Description: TODO(describe the methods)
	 * </p>
	 * <p>
	 * 
	 * </p>
	 * 
	 * @param key
	 * @param map
	 * @param timeout
	 * @return
	 * 
	 * @author xwc1125
	 * @date 2016年11月2日 下午5:12:12
	 */
	@SuppressWarnings("unchecked")
	public int insMap(String key, String tag, Map<?, ?> map, long timeout) {
		try {
			if (isEmpty(key) || map == null) {
				return FlexCacheConstants.EMPTY_KEY;
			}
			if (isExsit(key)) {
				DataInfo dataInfo = flexMap.get(key);
				FlexCacheConstants.DATA_TYPE dataType = dataInfo.getDataType();
				if (dataType != FlexCacheConstants.DATA_TYPE.MAP) {
					return FlexCacheConstants.FAIL;
				}
				Map oldMap = dataInfo.getMap();
				for (Map.Entry en : map.entrySet()) {
					Object key2 = en.getKey();
					Object values2 = en.getValue();
					oldMap.put(key2, values2);
				}
				dataInfo.setMap(oldMap);
				if (!isEmpty(tag)) {
					dataInfo.setTag(tag);
				}
				dataInfo.setDataType(FlexCacheConstants.DATA_TYPE.MAP);
				flexMap.put(key, dataInfo);
				timeMap.put(key, new TimeoutData(new Date(), timeout));
			} else {
				DataInfo dataInfo = new DataInfo();
				dataInfo.setMap(map);
				if (!isEmpty(tag)) {
					dataInfo.setTag(tag);
				}
				dataInfo.setDataType(FlexCacheConstants.DATA_TYPE.MAP);
				flexMap.put(key, dataInfo);
				timeMap.put(key, new TimeoutData(new Date(), timeout));
			}
			return FlexCacheConstants.SUCC;
		} catch (Throwable e) {
			e.printStackTrace();
		}
		return FlexCacheConstants.ERR;
	}

	/**
	 * 
	 * <p>
	 * Title: insList
	 * </p>
	 * <p>
	 * Description: TODO(describe the methods)
	 * </p>
	 * <p>
	 * 
	 * </p>
	 * 
	 * @param key
	 * @param list
	 * @return
	 * 
	 * @author xwc1125
	 * @date 2016年11月2日 下午5:12:08
	 */
	public int insList(String key, List list) {
		return insList(key, list, 0);
	}

	public int insList(String key, List list, long timeout) {
		return insList(key, null, list, timeout);
	}

	/**
	 * 
	 * <p>
	 * Title: insList
	 * </p>
	 * <p>
	 * Description: TODO(describe the methods)
	 * </p>
	 * <p>
	 * 
	 * </p>
	 * 
	 * @param key
	 * @param list
	 * @param timeout
	 * @return
	 * 
	 * @author xwc1125
	 * @date 2016年11月2日 下午5:12:04
	 */
	@SuppressWarnings("unchecked")
	public int insList(String key, String tag, List list, long timeout) {
		try {
			if (isEmpty(key) || list == null || list.size() == 0) {
				return FlexCacheConstants.EMPTY_KEY;
			}
			if (isExsit(key)) {
				DataInfo dataInfo = flexMap.get(key);
				FlexCacheConstants.DATA_TYPE dataType = dataInfo.getDataType();
				if (dataType != FlexCacheConstants.DATA_TYPE.LIST) {
					return FlexCacheConstants.FAIL;
				}
				List oldList = dataInfo.getList();
				oldList.addAll(list);
				if (!isEmpty(tag)) {
					dataInfo.setTag(tag);
				}
				dataInfo.setDataType(FlexCacheConstants.DATA_TYPE.LIST);
				flexMap.put(key, dataInfo);
				timeMap.put(key, new TimeoutData(new Date(), timeout));
			} else {
				DataInfo dataInfo = new DataInfo();
				dataInfo.setList(list);
				if (!isEmpty(tag)) {
					dataInfo.setTag(tag);
				}
				dataInfo.setDataType(FlexCacheConstants.DATA_TYPE.LIST);
				flexMap.put(key, dataInfo);
				timeMap.put(key, new TimeoutData(new Date(), timeout));
			}
			return FlexCacheConstants.SUCC;
		} catch (Throwable e) {
			e.printStackTrace();
		}
		return FlexCacheConstants.ERR;
	}

	// ====================add=================================
	/**
	 * 
	 * <p>
	 * Title: addToList
	 * </p>
	 * <p>
	 * Description: TODO(describe the methods)
	 * </p>
	 * <p>
	 * 
	 * </p>
	 * 
	 * @param key
	 * @param obj
	 * @return
	 * 
	 * @author xwc1125
	 * @date 2016年11月2日 下午5:27:03
	 */
	public int addToList(String key, Object obj) {
		return addToList(key, obj, 0);
	}

	public int addToList(String key, Object obj, long timeout) {
		return addToList(key, null, obj, timeout);
	}

	/**
	 * 
	 * <p>
	 * Title: addToList
	 * </p>
	 * <p>
	 * Description: TODO(describe the methods)
	 * </p>
	 * <p>
	 * 
	 * </p>
	 * 
	 * @param key
	 * @param obj
	 * @param timeout
	 * @return
	 * 
	 * @author xwc1125
	 * @date 2016年11月2日 下午5:27:07
	 */
	@SuppressWarnings("unchecked")
	public int addToList(String key, String tag, Object obj, long timeout) {
		try {
			if (isEmpty(key) || obj == null) {
				return FlexCacheConstants.EMPTY_KEY;
			}
			if (isExsit(key)) {
				DataInfo dataInfo = flexMap.get(key);
				FlexCacheConstants.DATA_TYPE dataType = dataInfo.getDataType();
				if (dataType != FlexCacheConstants.DATA_TYPE.LIST) {
					return FlexCacheConstants.FAIL;
				}
				List oldList = dataInfo.getList();
				oldList.add(obj);
				if (!isEmpty(tag)) {
					dataInfo.setTag(tag);
				}
				dataInfo.setDataType(FlexCacheConstants.DATA_TYPE.LIST);
				flexMap.put(key, dataInfo);
				timeMap.put(key, new TimeoutData(new Date(), timeout));
			} else {
				DataInfo dataInfo = new DataInfo();
				List list = new ArrayList();
				list.add(obj);
				dataInfo.setList(list);
				if (!isEmpty(tag)) {
					dataInfo.setTag(tag);
				}
				dataInfo.setDataType(FlexCacheConstants.DATA_TYPE.LIST);
				flexMap.put(key, dataInfo);
				timeMap.put(key, new TimeoutData(new Date(), timeout));
			}
			return FlexCacheConstants.SUCC;
		} catch (Throwable e) {
			e.printStackTrace();
		}
		return FlexCacheConstants.ERR;
	}

	/**
	 * 
	 * <p>
	 * Title: addToMap
	 * </p>
	 * <p>
	 * Description: TODO(describe the methods)
	 * </p>
	 * <p>
	 * 
	 * </p>
	 * 
	 * @param key
	 * @param valueKey
	 * @param value
	 * @return
	 * 
	 * @author xwc1125
	 * @date 2016年11月2日 下午5:27:59
	 */
	public int addToMap(String key, String valueKey, Object value) {
		return addToMap(key, valueKey, value, 0);
	}

	public int addToMap(String key, String valueKey, Object value, long timeout) {
		return addToMap(key, null, valueKey, value, timeout);
	}

	/**
	 * 
	 * <p>
	 * Title: addToMap
	 * </p>
	 * <p>
	 * Description: TODO(describe the methods)
	 * </p>
	 * <p>
	 * 
	 * </p>
	 * 
	 * @param key
	 * @param valueKey
	 * @param value
	 * @param timeout
	 * @return
	 * 
	 * @author xwc1125
	 * @date 2016年11月2日 下午5:27:13
	 */
	@SuppressWarnings("unchecked")
	public int addToMap(String key, String tag, String valueKey, Object value,
			long timeout) {
		try {
			if (isEmpty(key, valueKey)) {
				return FlexCacheConstants.EMPTY_KEY;
			}
			if (isExsit(key)) {
				DataInfo dataInfo = flexMap.get(key);
				FlexCacheConstants.DATA_TYPE dataType = dataInfo.getDataType();
				if (dataType != FlexCacheConstants.DATA_TYPE.MAP) {
					return FlexCacheConstants.FAIL;
				}
				Map oldMap = dataInfo.getMap();
				oldMap.put(valueKey, value);
				dataInfo.setMap(oldMap);
				if (!isEmpty(tag)) {
					dataInfo.setTag(tag);
				}
				dataInfo.setDataType(FlexCacheConstants.DATA_TYPE.MAP);
				flexMap.put(key, dataInfo);
				timeMap.put(key, new TimeoutData(new Date(), timeout));
			} else {
				DataInfo dataInfo = new DataInfo();
				Map map = new HashMap();
				map.put(valueKey, value);
				dataInfo.setMap(map);
				if (!isEmpty(tag)) {
					dataInfo.setTag(tag);
				}
				dataInfo.setDataType(FlexCacheConstants.DATA_TYPE.MAP);
				flexMap.put(key, dataInfo);
				timeMap.put(key, new TimeoutData(new Date(), timeout));
			}
			return FlexCacheConstants.SUCC;
		} catch (Throwable e) {
			e.printStackTrace();
		}
		return FlexCacheConstants.ERR;
	}

	// =========================Delete==========================
	/**
	 * 
	 * <p>
	 * Title: delete
	 * </p>
	 * <p>
	 * Description: TODO(describe the methods)
	 * </p>
	 * <p>
	 * 
	 * </p>
	 * 
	 * @param key
	 * 
	 * @author xwc1125
	 * @date 2016年11月2日 下午2:37:59
	 */
	public int delete(String key) {
		try {
			timeMap.remove(key);
			flexMap.remove(key);
			return FlexCacheConstants.SUCC;
		} catch (Throwable e) {
			e.printStackTrace();
		}
		return FlexCacheConstants.ERR;
	}

	// =========================Clear==========================
	/**
	 * 
	 * <p>
	 * Title: clear
	 * </p>
	 * <p>
	 * Description: TODO(describe the methods)
	 * </p>
	 * <p>
	 * 
	 * </p>
	 * 
	 * @author xwc1125
	 * @date 2016年11月2日 下午2:38:06
	 */
	public void clear() {
		timeMap.clear();
		flexMap.clear();
	}

	// =========================Replace==========================
	/**
	 * 
	 * <p>
	 * Title: replace
	 * </p>
	 * <p>
	 * Description: TODO(describe the methods)
	 * </p>
	 * <p>
	 * 
	 * </p>
	 * 
	 * @param key
	 * @param obj
	 * @param timeout
	 *            秒
	 * 
	 * @author xwc1125
	 * @date 2016年11月2日 下午2:38:12
	 */
	public int replace(String key, Object obj, long timeout) {
		if (isExsit(key)) {
			return set(key, obj, timeout);
		}
		return FlexCacheConstants.NOT_EXIST_KEY;
	}

	/**
	 * 
	 * <p>
	 * Title: replace
	 * </p>
	 * <p>
	 * Description: TODO(describe the methods)
	 * </p>
	 * <p>
	 * 
	 * </p>
	 * 
	 * @param key
	 * @param map
	 * @param timeout
	 *            秒
	 * 
	 * @author xwc1125
	 * @date 2016年11月2日 下午2:38:16
	 */
	public int replace(String key, Map map, long timeout) {
		if (isExsit(key)) {
			return setMap(key, map, timeout);
		}
		return FlexCacheConstants.NOT_EXIST_KEY;
	}

	/**
	 * 
	 * <p>
	 * Title: replace
	 * </p>
	 * <p>
	 * Description: TODO(describe the methods)
	 * </p>
	 * <p>
	 * 
	 * </p>
	 * 
	 * @param key
	 * @param list
	 * @param timeout
	 *            秒
	 * 
	 * @author xwc1125
	 * @date 2016年11月2日 下午2:38:21
	 */
	public int replace(String key, List list, long timeout) {
		if (isExsit(key)) {
			return setList(key, list, timeout);
		}
		return FlexCacheConstants.NOT_EXIST_KEY;
	}

	// =======================isExsit============================
	/**
	 * 
	 * <p>
	 * Title: isExsit
	 * </p>
	 * <p>
	 * Description: 是否存在
	 * </p>
	 * <p>
	 * 
	 * </p>
	 * 
	 * @param key
	 * @return
	 * 
	 * @author xwc1125
	 * @date 2016年11月2日 下午2:32:26
	 */
	public boolean isExsit(String key) {
		if (isTimeOut(key)) {
			delete(key);
			return false;
		} else {
			return true;
		}
	}

	// ====================时间问题==================
	/**
	 * 
	 * <p>
	 * Title: isTimeOut
	 * </p>
	 * <p>
	 * Description: 是否过期
	 * </p>
	 * <p>
	 * 
	 * </p>
	 * 
	 * @param key
	 * @return
	 * 
	 * @author xwc1125
	 * @date 2016年11月2日 下午2:03:56
	 */
	public boolean isTimeOut(String key) {
		TimeoutData data = (TimeoutData) timeMap.get(key);
		if (data == null) {
			return true;
		}
		long time = (new Date().getTime() - data.getDate().getTime()) / 1000;
		if (time > data.getTimeout() && data.getTimeout() != 0) {
			if (callBack != null) {
				DataInfo dataInfo = flexMap.get(key);
				callBack.timeOut(dataInfo, data);
			}
			return true;
		}
		return false;
	}

	/**
	 * 
	 * <p>
	 * Title: getReTime
	 * </p>
	 * <p>
	 * Description: 剩余时间
	 * </p>
	 * <p>
	 * 
	 * </p>
	 * 
	 * @param key
	 * @return 秒
	 * 
	 * @author xwc1125
	 * @date 2016年11月2日 下午5:10:44
	 */
	public long getReTime(String key) {
		TimeoutData data = (TimeoutData) timeMap.get(key);
		if (data == null) {
			return 0;
		}
		long time = (new Date().getTime() - data.getDate().getTime()) / 1000;
		return data.getTimeout() - time;
	}

	/**
	 * @Title isEmpty
	 * @Description TODO(describe the methods)
	 * @param @param ss
	 * @param @return
	 * @return boolean
	 * @author xwc1125
	 * @date 2016年1月22日 下午2:36:09
	 */
	private static boolean isEmpty(String... ss) {
		if (ss == null)
			return true;
		for (String s : ss) {
			if (s == null || s.length() == 0 || s.trim().length() == 0
					|| s.equals("null") || s.equals("")) {
				return true;
			}
		}
		return false;
	}
}
