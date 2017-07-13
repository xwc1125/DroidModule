/**
 * <p>
 * Title: PhoneInfo.java
 * </p>
 * <p>
 * Description: 手机及sim信息-javaBean文件
 * </p>
 * <p>
 * 
 * </p>
 * @Copyright: Copyright (c) 2016
 * @author xwc1125
 * @date 2016年8月16日 上午10:29:13
 * @version V1.0
 */
package com.xwc1125.droidutils.device.entity;

import com.xwc1125.droidutils.json.JsonUtils;

import java.util.ArrayList;

/**
 * 
 * <p>
 * Title: PhoneInfo
 * </p>
 * <p>
 * Description: 手机及sim卡相关信息
 * </p>
 * <p>
 * 
 * </p>
 * 
 * @author xwc1125
 * @date 2016年3月24日下午3:46:39
 * 
 */
public class PInfo {

	/**
	 * 随机码
	 */
	private Long r;
	/**
	 * mac地址
	 */
	private String ma;
	/**
	 * imei集合
	 */
	private ArrayList<String> ies;
	/**
	 * sim信息集合
	 */
	private ArrayList<KInfo> smis;

	/**
	 * 
	 * <p>
	 * Title: PInfo
	 * </p>
	 * <p>
	 * Description: 构造方法
	 * </p>
	 */
	public PInfo() {
		super();
		r=System.currentTimeMillis();
	}

	/**
	 * @return the r
	 */
	public Long getR() {
		return r;
	}


	/**
	 * @param r the r to set
	 */
	public void setR(Long r) {
		this.r = r;
	}



	/**
	 * @return the ma
	 */
	public String getMa() {
		return ma;
	}



	/**
	 * @param ma the ma to set
	 */
	public void setMa(String ma) {
		this.ma = ma;
	}



	/**
	 * @return the ies
	 */
	public ArrayList<String> getIes() {
		return ies;
	}



	/**
	 * @param ies the ies to set
	 */
	public void setIes(ArrayList<String> ies) {
		this.ies = ies;
	}



	/**
	 * @return the smis
	 */
	public ArrayList<KInfo> getSmis() {
		return smis;
	}



	/**
	 * @param smis the smis to set
	 */
	public void setSmis(ArrayList<KInfo> smis) {
		this.smis = smis;
	}



	/*
	 * （非 Javadoc）
	 * <p>
	 * Title: toString
	 * </p>
	 * <p>
	 * Description: 返回JSON格式的字符串
	 * </p>
	 * @return
	 * @see java.lang.Object#toString()
	 * @author xwc1125
	 * @date 2016年8月16日 上午10:31:47
	 */
	@Override
	public String toString() {
		return JsonUtils.toJsonString(this);
	}

}
