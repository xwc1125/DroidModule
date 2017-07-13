/**
 * <p>
 * Title: ExceptionInfo.java
 * </p>
 * <p>
 * Description: 异常信息－javaBean
 * </p>
 * <p>
 * 
 * </p>
 * @Copyright: Copyright (c) 2016
 * @author zhangqy
 * @date 2016年8月16日 上午10:16:14
 * @version V1.0
 */
package com.yuancy.framework.exception;


import com.xwc1125.droidutils.json.JsonUtils;

/**
 * 
 * <p>
 * Title: ExceptionInfo
 * </p>
 * <p>
 * Description: 上传到服务器的错误信息
 * </p>
 * <p>
 * 
 * </p>
 * 
 * @author zhangqy
 * @date 2016年4月19日下午2:04:48
 * 
 */
public class ExceptionInfo {
	/**
	 * 该错误信息是否是自定义（Customize）
	 */
	private boolean ct;
	/**
	 * 该错误是否引起了系统崩溃(Collapse)
	 */
	private boolean cp;
	/**
	 * 该错误信息发生的原因（reasion）
	 */
	private String rs;
	/**
	 * 该错误信息发生的位置（where），包括类名、函数名、行号
	 */
	private String wr;
	/**
	 * 该错误信息的描述信息(message)
	 */
	private String msg;
	/**
	 * 该错误的发生时间（date）
	 */
	private long dt;

	/**
	 * 
	 * <p>
	 * Title: ExceptionInfo
	 * </p>
	 * <p>
	 * Description: 全参构造方法
	 * </p>
	 * 
	 * @param ct
	 * @param cp
	 * @param rs
	 * @param wr
	 * @param msg
	 * @param dt
	 */
	public ExceptionInfo(boolean ct, boolean cp, String rs, String wr,
			String msg, long dt) {
		super();
		this.ct = ct;
		this.cp = cp;
		this.rs = rs;
		this.wr = wr;
		this.msg = msg;
		this.dt = dt;
	}

	/**
	 * 
	 * <p>
	 * Title: ExceptionInfo
	 * </p>
	 * <p>
	 * Description: 无参构造方法
	 * </p>
	 */
	public ExceptionInfo() {
		super();
	}

	/**
	 * @return the ct
	 */
	public boolean isCt() {
		return ct;
	}

	/**
	 * @param ct the ct to set
	 */
	public void setCt(boolean ct) {
		this.ct = ct;
	}

	/**
	 * @return the cp
	 */
	public boolean isCp() {
		return cp;
	}

	/**
	 * @param cp the cp to set
	 */
	public void setCp(boolean cp) {
		this.cp = cp;
	}

	/**
	 * @return the rs
	 */
	public String getRs() {
		return rs;
	}

	/**
	 * @param rs the rs to set
	 */
	public void setRs(String rs) {
		this.rs = rs;
	}

	/**
	 * @return the wr
	 */
	public String getWr() {
		return wr;
	}

	/**
	 * @param wr the wr to set
	 */
	public void setWr(String wr) {
		this.wr = wr;
	}

	/**
	 * @return the msg
	 */
	public String getMsg() {
		return msg;
	}

	/**
	 * @param msg the msg to set
	 */
	public void setMsg(String msg) {
		this.msg = msg;
	}

	/**
	 * @return the dt
	 */
	public long getDt() {
		return dt;
	}

	/**
	 * @param dt the dt to set
	 */
	public void setDt(long dt) {
		this.dt = dt;
	}

	@Override
	public String toString() {
		return "{'ct':'" + ct + "', 'cp':'" + cp + "', 'rs':'" + rs
				+ "', 'wr':'" + wr + "', 'msg':'" + msg + "', 'dt':'" + dt
				+ "'}";
	}

	/**
	 * 
	 * <p>
	 * Title: toJsonString
	 * </p>
	 * <p>
	 * Description: 返回json格式的异常信息
	 * </p>
	 * <p>
	 * 
	 * </p>
	 * @return
	 * 
	 * @author zhangqy
	 * @date 2016年8月16日 下午4:23:52
	 */
	public String toJsonString() {
		return JsonUtils.toJsonString(this);
	}
}
