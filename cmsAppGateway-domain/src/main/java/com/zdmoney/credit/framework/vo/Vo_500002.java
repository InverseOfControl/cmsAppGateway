package com.zdmoney.credit.framework.vo;

import org.hibernate.validator.constraints.NotEmpty;

import com.zdmoney.credit.framework.vo.common.BaseParamVo;


public class Vo_500002 extends BaseParamVo {
	
	@NotEmpty(message = "客户姓名不能为空500002")
	private String name;
	
	@NotEmpty(message = "客户身份证号不能为空500002")
	private String idCard;
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getIdCard() {
		return idCard;
	}

	public void setIdCard(String idCard) {
		this.idCard = idCard;
	}

}
