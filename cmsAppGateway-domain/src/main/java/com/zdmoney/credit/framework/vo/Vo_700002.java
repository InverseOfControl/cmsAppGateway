package com.zdmoney.credit.framework.vo;

import org.hibernate.validator.constraints.NotEmpty;

import com.zdmoney.credit.framework.vo.common.BaseParamVo;

public class Vo_700002 extends BaseParamVo {

	@NotEmpty(message = "工号不能为空700002")
	private String userCode; //工号
	
	private String applicantName; //申请人姓名
	
	private String idCardNoLastFourDigits; //身份证后四位
	
	private int pageNum=0; //当前页数
	
	private int pageSize=20; //每页显示行数

	public String getUserCode() {
		return userCode;
	}

	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}

	public String getApplicantName() {
		return applicantName;
	}

	public void setApplicantName(String applicantName) {
		this.applicantName = applicantName;
	}

	public String getIdCardNoLastFourDigits() {
		return idCardNoLastFourDigits;
	}

	public void setIdCardNoLastFourDigits(String idCardNoLastFourDigits) {
		this.idCardNoLastFourDigits = idCardNoLastFourDigits;
	}

	public int getPageNum() {
		return pageNum;
	}

	public void setPageNum(int pageNum) {
		this.pageNum = pageNum;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	
	
}
