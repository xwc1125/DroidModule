package com.xwc1125.droidutils.flexCache.core;

import java.util.List;
import java.util.Map;

/**
 * 
 * <p>
 * Title: IFlexCache
 * </p>
 * <p>
 * Description: 接口
 * </p>
 * <p>
 * 
 * </p>
 * 
 * @author xwc1125
 * @date 2016年11月2日 上午10:57:09
 * 
 */
@SuppressWarnings("rawtypes")
public interface IFlexCache {
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
	public Object get(String key);

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
	public List getList(String key);

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
	public Map getMap(String key);

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
	public int set(String key, Object obj);

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
	 * 
	 * @author xwc1125
	 * @date 2016年11月2日 下午2:37:19
	 */
	public int set(String key, Object obj, long timeout);

	/**
	 * 
	 * <p>
	 * Title: set
	 * </p>
	 * <p>
	 * Description: TODO(describe the methods)
	 * </p>
	 * <p>
	 * 
	 * </p>
	 * 
	 * @param key
	 * @param tag
	 * @param obj
	 * @param timeout
	 * @return
	 * 
	 * @author xwc1125
	 * @date 2016年11月2日 下午6:09:58
	 */
	public int set(String key, String tag, Object obj, long timeout);

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
	public int setMap(String key, Map map);

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
	 * 
	 * @author xwc1125
	 * @date 2016年11月2日 下午2:37:43
	 */
	public int setMap(String key, Map map, long timeout);

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
	 * @param tag
	 * @param map
	 * @param timeout
	 * @return
	 * 
	 * @author xwc1125
	 * @date 2016年11月2日 下午6:10:12
	 */
	public int setMap(String key, String tag, Map map, long timeout);

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
	public int setList(String key, List list);

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
	 * 
	 * @author xwc1125
	 * @date 2016年11月2日 下午2:37:52
	 */
	public int setList(String key, List list, long timeout);

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
	 * @param tag
	 * @param list
	 * @param timeout
	 * @return
	 * 
	 * @author xwc1125
	 * @date 2016年11月2日 下午6:10:26
	 */
	public int setList(String key, String tag, List list, long timeout);

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
	public int ins(String key, Object obj);

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
	public int ins(String key, Object obj, long timeout);

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
	 * @param tag
	 * @param obj
	 * @param timeout
	 * @return
	 * 
	 * @author xwc1125
	 * @date 2016年11月2日 下午6:10:41
	 */
	public int ins(String key, String tag, Object obj, long timeout);

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
	public int insMap(String key, Map<?, ?> map);

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
	public int insMap(String key, Map<?, ?> map, long timeout);

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
	 * @param tag
	 * @param map
	 * @param timeout
	 * @return
	 * 
	 * @author xwc1125
	 * @date 2016年11月2日 下午6:10:54
	 */
	public int insMap(String key, String tag, Map<?, ?> map, long timeout);

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
	public int insList(String key, List list);

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
	public int insList(String key, List list, long timeout);

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
	 * @param tag
	 * @param list
	 * @param timeout
	 * @return
	 * 
	 * @author xwc1125
	 * @date 2016年11月2日 下午6:11:09
	 */
	public int insList(String key, String tag, List list, long timeout);

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
	public int addToList(String key, Object obj);

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
	public int addToList(String key, Object obj, long timeout);

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
	 * @param tag
	 * @param obj
	 * @param timeout
	 * @return
	 * 
	 * @author xwc1125
	 * @date 2016年11月2日 下午6:11:22
	 */
	public int addToList(String key, String tag, Object obj, long timeout);

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
	public int addToMap(String key, String valueKey, Object value);

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
	public int addToMap(String key, String valueKey, Object value, long timeout);

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
	 * @param tag
	 * @param valueKey
	 * @param value
	 * @param timeout
	 * @return
	 * 
	 * @author xwc1125
	 * @date 2016年11月2日 下午6:11:38
	 */
	public int addToMap(String key, String tag, String valueKey, Object value,
                        long timeout);

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
	public int delete(String key);

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
	public void clear();

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
	 * 
	 * @author xwc1125
	 * @date 2016年11月2日 下午2:38:12
	 */
	public int replace(String key, Object obj, long timeout);

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
	 * 
	 * @author xwc1125
	 * @date 2016年11月2日 下午2:38:16
	 */
	public int replace(String key, Map map, long timeout);

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
	 * 
	 * @author xwc1125
	 * @date 2016年11月2日 下午2:38:21
	 */
	public int replace(String key, List list, long timeout);

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
	public boolean isExsit(String key);

	// =========================时间=====================
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
	public boolean isTimeOut(String key);

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
	public long getReTime(String key);
}
