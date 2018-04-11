package com.zdmoney.credit.framework.vo;

import org.hibernate.validator.constraints.NotEmpty;

import com.zdmoney.credit.framework.vo.common.BaseParamVo;

public class Vo_700007 extends BaseParamVo {
	
	@NotEmpty(message = "申请单号不能为空700007")
	private String appNo; //申请单号
	
	@NotEmpty(message = "拒绝原因不能为空700007")
	private String refuseReason; //拒绝原因
	
	private String ip; //ip地址

	public String getAppNo() {
		return appNo;
	}

	public void setAppNo(String appNo) {
		this.appNo = appNo;
	}

	public String getRefuseReason() {
		return refuseReason;
	}

	public void setRefuseReason(String refuseReason) {
		this.refuseReason = refuseReason;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}
	
}
