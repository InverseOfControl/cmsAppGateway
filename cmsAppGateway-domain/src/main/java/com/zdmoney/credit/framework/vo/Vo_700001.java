package com.zdmoney.credit.framework.vo;

import org.hibernate.validator.constraints.NotEmpty;

import com.zdmoney.credit.framework.vo.common.BaseParamVo;

public class Vo_700001 extends BaseParamVo {
	
	@NotEmpty(message = "申请单号不能为空700001")
	private String appNo; //申请单号
	
	@NotEmpty(message = "参数key不能为空700001")
	private String field_key; //参数key
	
	@NotEmpty(message = "参数值不能为空700001")
	private String field_value; //参数值
	
	@NotEmpty(message = "状态不能为空700001")
	private String state; //状态

	public String getAppNo() {
		return appNo;
	}

	public void setAppNo(String appNo) {
		this.appNo = appNo;
	}

	public String getField_key() {
		return field_key;
	}

	public void setField_key(String field_key) {
		this.field_key = field_key;
	}

	public String getField_value() {
		return field_value;
	}

	public void setField_value(String field_value) {
		this.field_value = field_value;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}
	
}
