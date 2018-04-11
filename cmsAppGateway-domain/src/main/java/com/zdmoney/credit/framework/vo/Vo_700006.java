package com.zdmoney.credit.framework.vo;

import org.hibernate.validator.constraints.NotEmpty;

import com.zdmoney.credit.framework.vo.common.BaseParamVo;

public class Vo_700006 extends BaseParamVo {
	private static final long serialVersionUID = -4976688996329150061L;

	@NotEmpty(message = "工号不能为空700006")
	private String userCode;
	
	@NotEmpty(message = "申请人姓名不能为空700006")
	private String name;
	
	@NotEmpty(message = "身份证号码不能为空700006")
	private String idNo;
	
	public String getUserCode() {
		return userCode;
	}

	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getIdNo() {
		return idNo;
	}

	public void setIdNo(String idNo) {
		this.idNo = idNo;
	}
}
