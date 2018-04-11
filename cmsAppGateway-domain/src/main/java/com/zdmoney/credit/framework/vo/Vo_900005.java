package com.zdmoney.credit.framework.vo;

import org.hibernate.validator.constraints.NotEmpty;

import com.zdmoney.credit.framework.vo.common.BaseParamVo;

public class Vo_900005 extends BaseParamVo {
	
	@NotEmpty(message = "申请单号不能为空900005")
	private String appNo;
	@NotEmpty(message = "手机号不能为空900005")
	private String cellphone;
	
	public String getAppNo() {
		return appNo;
	}

	public void setAppNo(String appNo) {
		this.appNo = appNo;
	}

	public String getCellphone() {
		return cellphone;
	}

	public void setCellphone(String cellphone) {
		this.cellphone = cellphone;
	}
}
