/**
 * <p>
 * Title: TestFlexCache.java
 * </p>
 * <p>
 * Description: TODO(describe the file) 
 * </p>
 * <p>
 * 
 * </p>
 * @Copyright: Copyright (c) 2015
 * @author xwc1125
 * @date 2016年11月2日 下午2:41:51
 * @version V1.0
 */
package com.xwc1125.droidutils.flexCache.test;

import com.xwc1125.droidutils.flexCache.LocalFlexCache;
import com.xwc1125.droidutils.flexCache.core.FlexCallback;
import com.xwc1125.droidutils.flexCache.entity.DataInfo;
import com.xwc1125.droidutils.flexCache.entity.TimeoutData;

import java.util.ArrayList;
import java.util.TreeMap;

/**
 * <p>
 * Title: TestFlexCache
 * </p>
 * <p>
 * Description: TODO(describe the types) 
 * </p>
 * <p>
 * 
 * </p>
 * @author xwc1125
 * @date 2016年11月2日 下午2:41:51
 *  
 */
public class TestFlexCache {
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static void main(String[] args) {
		LocalFlexCache localFlexCache=LocalFlexCache.getInstance(new FlexCallback() {
			
			@Override
			public void timeOut(DataInfo dataInfo, TimeoutData timeoutData) {
				System.out.println("===>"+dataInfo);
				System.out.println("===>"+timeoutData);
			}
		});
		String key="aaa";
		Object object=localFlexCache.get(key);
		System.out.println(object);
		localFlexCache.set(key, "111",100);
		object=localFlexCache.get(key);
		System.out.println(object);
		localFlexCache.replace(key, "222", 10000);
		object=localFlexCache.get(key);
		System.out.println(object);
		
		localFlexCache.ins(key, "false");
		object=localFlexCache.get(key);
		System.out.println(object);
		
		ArrayList<String> list=new ArrayList<String>();
		list.add("1");
		list.add("2");
		list.add("3");
		list.add("4");
		
		ArrayList<Object> arrayList=(ArrayList<Object>) localFlexCache.getList(key);
		System.out.println(arrayList);
		localFlexCache.setList(key, list, 1);
		arrayList=(ArrayList<Object>) localFlexCache.getList(key);
		System.out.println(arrayList);
		list.add("5555");
		localFlexCache.replace(key, list, 10000);
		arrayList=(ArrayList<Object>) localFlexCache.getList(key);
		System.out.println(arrayList);
		
		ArrayList<Integer> arrayList2=new ArrayList<Integer>();
		arrayList2.add(1);
		arrayList2.add(2);
		arrayList2.add(3);
		localFlexCache.insList(key, arrayList2, 5);
		arrayList=(ArrayList<Object>) localFlexCache.getList(key);
		System.out.println(arrayList);
		
		TreeMap<String, String> treeMap=new TreeMap<String, String>();
		treeMap.put("111", "111");
		treeMap.put("222", "222");
		treeMap.put("333", "333");
		treeMap.put("444", "444");
		TreeMap map=(TreeMap) localFlexCache.getMap(key);
		System.out.println(map);
		localFlexCache.setMap(key, treeMap, 10);
		map=(TreeMap) localFlexCache.getMap(key);
		System.out.println(map);
		treeMap.put("5555", "5555");
		localFlexCache.replace(key, treeMap, 1000);
		map=(TreeMap) localFlexCache.getMap(key);
		System.out.println(map);
		
		map.put("www", "wwww");
		localFlexCache.insMap(key, map, 10);
		map=(TreeMap) localFlexCache.getMap(key);
		System.out.println(map);
		
		long remainTime=localFlexCache.getReTime(key);
		System.out.println(remainTime);
	}
}
