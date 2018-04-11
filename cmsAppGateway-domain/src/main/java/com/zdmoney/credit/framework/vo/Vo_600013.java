package com.zdmoney.credit.framework.vo;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import com.zdmoney.credit.framework.vo.common.BaseParamVo;

public class Vo_600013 extends BaseParamVo {
	@NotEmpty(message = "工号不能为空600013")
	@Length(max=50, message="工号长度不能超过50")
	private String userCode;//工号
	@Length(max=50, message="申请人姓名长度不能超过50")
	private String applicantName;//申请人姓名
	
	private String appNo;//申请人编号
	
	private Integer uploadState;//上传状态
	
	private Integer pageNum;//当前页数
	private Integer pageSize;//每页显示的行
//	@NotEmpty(message = "申请单状态不能为空600013")
//	@Length(max=20, message="申请单状态长度不能超过20")
	private String status;//申请单状态
	private String sysCode;//系统CODE
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
	public String getAppNo() {
		return appNo;
	}
	public void setAppNo(String appNo) {
		this.appNo = appNo;
	}
	public Integer getUploadState() {
		return uploadState;
	}
	public void setUploadState(Integer uploadState) {
		this.uploadState = uploadState;
	}
	public Integer getPageNum() {
		return pageNum;
	}
	public void setPageNum(Integer pageNum) {
		this.pageNum = pageNum;
	}
	public Integer getPageSize() {
		return pageSize;
	}
	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getSysCode() {
		return sysCode;
	}
	public void setSysCode(String sysCode) {
		this.sysCode = sysCode;
	}
	
	
}
