package com.zdmoney.credit.framework.vo;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import com.zdmoney.credit.framework.vo.common.BaseParamVo;


public class Vo_500004 extends BaseParamVo {
	
	@Length(max=30, message="客户姓名长度不能超过30")
	private String name;
	
	@Length(max=4, message="客户身份证号长度不能超过4")
	private String idCard;
	
	@NotEmpty(message = "员工工号不能为空500004")
	private String userCode;
	
	public String getUserCode() {
		return userCode;
	}

	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}

	private int pagerNum = 1;
	
	private int pagerMax = 20;
	public int getPagerNum() {
		return pagerNum;
	}

	public void setPagerNum(int pagerNum) {
		this.pagerNum = pagerNum;
	}

	public int getPagerMax() {
		return pagerMax;
	}

	public void setPagerMax(int pagerMax) {
		this.pagerMax = pagerMax;
	}

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
