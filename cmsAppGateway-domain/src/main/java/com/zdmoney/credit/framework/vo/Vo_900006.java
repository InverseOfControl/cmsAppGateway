package com.zdmoney.credit.framework.vo;

import org.hibernate.validator.constraints.NotEmpty;

import com.zdmoney.credit.framework.vo.common.BaseParamVo;

public class Vo_900006 extends BaseParamVo {
	
	private static final long serialVersionUID = -6923583800206361968L;
	
	@NotEmpty(message = "身份证号不能为空900006")
	private String idNo;
	
	private String flag;
	
	private String appNo;
	
	public String getIdNo() {
		return idNo;
	}
	public void setIdNo(String idNo) {
		this.idNo = idNo;
	}
	public void setAppNo(String appNo) {
		this.appNo = appNo;
	}
	public String getAppNo() {
		return appNo;
	}
	public void setFlag(String flag) {
		this.flag = flag;
	}
	public String getFlag() {
		return flag;
	}
}
