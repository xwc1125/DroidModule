/**
 * <p>
 * Title: FlexCacheConstants.java
 * </p>
 * <p>
 * Description: TODO(describe the file) 
 * </p>
 * <p>
 * 
 * </p>
 * @Copyright: Copyright (c) 2015
 * @author xwc1125
 * @date 2016年11月2日 上午11:46:35
 * @version V1.0
 */
package com.xwc1125.droidutils.flexCache.core;

/**
 * <p>
 * Title: FlexCacheConstants
 * </p>
 * <p>
 * Description: TODO(describe the types)
 * </p>
 * <p>
 * 
 * </p>
 * 
 * @author xwc1125
 * @date 2016年11月2日 上午11:46:35
 * 
 */
public class FlexCacheConstants {
	/**
	 * 
	 * <p>
	 * Title: DATA_TYPE
	 * </p>
	 * <p>
	 * Description: 数据类型
	 * </p>
	 * <p>
	 * 
	 * </p>
	 * @author xwc1125
	 * @date 2016年11月2日 下午4:11:57
	 *
	 */
	public enum DATA_TYPE {
		OBJ {
			@Override
			public String getVales() {
				return "obj_";
			}
		},
		LIST {
			@Override
			public String getVales() {
				return "list_";
			}
		},
		MAP {
			@Override
			public String getVales() {
				return "map_";
			}
		};
		public abstract String getVales();
	}
	
	public static final int NOT_EXIST_KEY=-2;
	public static final int EMPTY_KEY=-1;
	public static final int ERR=0;
	public static final int SUCC=1;
	public static final int FAIL=2;
	
	
}
