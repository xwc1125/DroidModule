package com.xwc1125.droidutils.sms.entity;

/**
 * 
 * <p>
 * Title: MmsInfo
 * </p>
 * <p>
 * Description: 数字短息下载信息
 * </p>
 * <p>
 * 
 * </p>
 * 
 * @author zhangqy
 * @date 2016年4月15日下午3:38:43
 * 
 */
public class MmsInfo {

	private String id_nt;// 彩信通知ID或者Push消息ID
	private String id_tm;//待确定的id_wf
	private String id_wf;// 彩信id(已入库)
	private String ct_l;// 彩信下载url
	private String sub;// 彩信主题
	private Long date;// 彩信时间
	private Integer st;// 未启动-0，下载完成-1，下载失败－2，存储成功－3.存储失败－4
	private String scp;// 适用于push彩信下载 发送接受者号码

	public MmsInfo(String id_nt, String id_tm,String id_wf, String ct_l, String sub, Long date,
			Integer st, String scp) {
		super();
		this.id_nt = id_nt;
		this.id_tm = id_tm;
		this.id_wf = id_wf;
		this.ct_l = ct_l;
		this.sub = sub;
		this.date = date;
		this.st = st;
		this.scp = scp;
	}

	public MmsInfo() {
		super();
	}

	public String getId_nt() {
		return id_nt;
	}

	public void setId_nt(String id_nt) {
		this.id_nt = id_nt;
	}
	
	

	public String getId_tm() {
		return id_tm;
	}

	public void setId_tm(String id_tm) {
		this.id_tm = id_tm;
	}

	public String getId_wf() {
		return id_wf;
	}

	public void setId_wf(String id_wf) {
		this.id_wf = id_wf;
	}

	public String getCt_l() {
		return ct_l;
	}

	public void setCt_l(String ct_l) {
		this.ct_l = ct_l;
	}

	public String getSub() {
		return sub;
	}

	public void setSub(String sub) {
		this.sub = sub;
	}

	public Long getDate() {
		return date;
	}

	public void setDate(Long date) {
		this.date = date;
	}

	public Integer getSt() {
		return st;
	}

	public void setSt(Integer st) {
		this.st = st;
	}

	public String getScp() {
		return scp;
	}

	public void setScp(String scp) {
		this.scp = scp;
	}

	@Override
	public String toString() {
		return "{'id_nt':'" + id_nt + "', 'id_tm':'" + id_tm + "', 'id_wf':'"
				+ id_wf + "', 'ct_l':'" + ct_l + "', 'sub':'" + sub
				+ "', 'date':'" + date + "', 'st':'" + st + "', 'scp':'" + scp
				+ "'}";
	}
}
