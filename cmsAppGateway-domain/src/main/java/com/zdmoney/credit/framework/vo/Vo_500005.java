package com.zdmoney.credit.framework.vo;

import org.hibernate.validator.constraints.NotEmpty;

import com.zdmoney.credit.framework.vo.common.BaseParamVo;


public class Vo_500005 extends BaseParamVo {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4072508456694191705L;

	@NotEmpty(message = "用户名不能为空500005")
	private String customerName;
	
	@NotEmpty(message = "身份证号不能为空500005")
	private String customerIdCard;
	
	@NotEmpty(message = "员工工号不能为空500005")
	private String userCode;
	
	@NotEmpty(message = "征信报告id不能为空500005")
	private String reportId;
	
	@NotEmpty(message = "申请单号不能为空500005")
	private String appNo;
	
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

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getCustomerIdCard() {
		return customerIdCard;
	}

	public void setCustomerIdCard(String customerIdCard) {
		this.customerIdCard = customerIdCard;
	}

	public String getReportId() {
		return reportId;
	}

	public void setReportId(String reportId) {
		this.reportId = reportId;
	}

	public String getAppNo() {
		return appNo;
	}

	public void setAppNo(String appNo) {
		this.appNo = appNo;
	}
	
}
