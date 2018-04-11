package com.zdmoney.credit.framework.vo;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import com.zdmoney.credit.framework.vo.common.BaseParamVo;

public class Vo_600012 extends BaseParamVo{

	@NotEmpty(message = "身份证不能为空600012")
	@Length(max=50, message="身份证长度不能超过50")
	private String idNo;//身份证
	private String sysCode;//系统编号
	public String getIdNo() {
		return idNo;
	}
	public void setIdNo(String idNo) {
		this.idNo = idNo;
	}
	public String getSysCode() {
		return sysCode;
	}
	public void setSysCode(String sysCode) {
		this.sysCode = sysCode;
	}
	
	
}
