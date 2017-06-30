/**
 * <p>
 * Title: KInfo.java
 * </p>
 * <p>
 * Description: sim信息-javaBean文件
 * </p>
 * <p>
 * 
 * </p>
 * @Copyright: Copyright (c) 2016
 * @author xwc1125
 * @date 2016年8月16日 上午10:19:13
 * @version V1.0
 */
package com.xwc1125.droidutils.device.entity;

import com.xwc1125.droidutils.json.JsonUtils;

/**
 * 
 * <p>
 * Title: SimInfo
 * </p>
 * <p>
 * Description: sim卡信息
 * </p>
 * <p>
 * 
 * </p>
 * 
 * @author xwc1125
 * @date 2016年3月18日下午3:07:38
 * 
 */
public class KInfo {
	/**
	 * imsi
	 */
	private String is;
	/**
	 * iccid
	 */
	private String ic;
	/**
	 * iccid
	 */
	private String m;
	/**
	 * CarrierName -中国联通 4G
	 */
	private String cn;
	/**
	 * 从0开始，最大为卡槽数。 0 即表示卡1的数据
	 */
	private int sid;
	/**
	 * 类别 0:高通、联发科反射数据 1:兼容的4.0，5.0的反射数据 2:5.0以上的反射数据
	 */
	private int t;
	/**
	 * 移动网络是否首选该卡
	 */
	private boolean idfd;
	/**
	 * 短信是否首选该卡
	 */
	private boolean idfs;
	/**
	 * 随机码;
	 */
	private Long r;

	/**
	 * 
	 * <p>
	 * Title: KInfo
	 * </p>
	 * <p>
	 * Description: 构造方法
	 * </p>
	 */
	public KInfo() {
		super();
		r = System.currentTimeMillis();
	}

	/**
	 * @return the is
	 */
	public String getIs() {
		return is;
	}

	/**
	 * @param is
	 *            the is to set
	 */
	public void setIs(String is) {
		this.is = is;
	}

	/**
	 * @return the ic
	 */
	public String getIc() {
		return ic;
	}

	/**
	 * @param ic
	 *            the ic to set
	 */
	public void setIc(String ic) {
		this.ic = ic;
	}

	/**
	 * @return the m
	 */
	public String getM() {
		return m;
	}

	/**
	 * @param m
	 *            the m to set
	 */
	public void setM(String m) {
		this.m = m;
	}

	/**
	 * @return the cn
	 */
	public String getCn() {
		return cn;
	}

	/**
	 * @param cn
	 *            the cn to set
	 */
	public void setCn(String cn) {
		this.cn = cn;
	}

	/**
	 * @return the sid
	 */
	public int getSid() {
		return sid;
	}

	/**
	 * @param sid
	 *            the sid to set
	 */
	public void setSid(int sid) {
		this.sid = sid;
	}

	/**
	 * @return the t
	 */
	public int getT() {
		return t;
	}

	/**
	 * @param t
	 *            the t to set
	 */
	public void setT(int t) {
		this.t = t;
	}

	/**
	 * @return the idfd
	 */
	public boolean isIdfd() {
		return idfd;
	}

	/**
	 * @param idfd
	 *            the idfd to set
	 */
	public void setIdfd(boolean idfd) {
		this.idfd = idfd;
	}

	/**
	 * @return the idfs
	 */
	public boolean isIdfs() {
		return idfs;
	}

	/**
	 * @param idfs
	 *            the idfs to set
	 */
	public void setIdfs(boolean idfs) {
		this.idfs = idfs;
	}

	/**
	 * @return the r
	 */
	public Long getR() {
		return r;
	}

	/**
	 * @param r
	 *            the r to set
	 */
	public void setR(Long r) {
		this.r = r;
	}

	/*
	 * （非 Javadoc）
	 * <p>
	 * Title: toString
	 * </p>
	 * <p>
	 * Description: 返回Json格式的字符串
	 * </p>
	 * @return
	 * @see java.lang.Object#toString()
	 * @author xwc1125
	 * @date 2016年8月16日 上午10:27:25
	 */
	@Override
	public String toString() {
		return JsonUtils.toJsonString(this);
	}

}