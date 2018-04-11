package com.zdmoney.credit.applyinfo.domain;

import java.util.Date;

import com.zdmoney.credit.framework.domain.BaseDomain;

/**
 * 申请信息表 tm_apply_main_info
 * 
 * @author zhb
 *
 */
public class TmApplyMainInfo extends BaseDomain {
	
	private static final long serialVersionUID = 146546687878L;
	/** 主键PK **/
	private Long id;
	/** 工号 **/
	private String userId;
	/** 申请单号 **/
	private String appNo;
	/** 身份证号 **/
	private String idNo;
	/** 申请人姓名 **/
	private String userName;
	/** 申请时间  **/
	private Date applyDate;
	/** 申请金额 **/
	private Double applyAccount;
	/** 产品名称 **/
	private String productName;
	/** 申请期限 **/
	private Long applyTerm;
	/** 状态 **/
	private String state;
	/** 产品编码 **/
	private String productCode;
	/** 新申请单号 **/
	private String newAppNo;
	/** 是否有征信报告 **/
	private String hasCreditReport;
	/** 是否下一步 **/
	private String ifNext;
	/** 征审返回信息 **/
	private String promptMessages;
	/** 征信返回信息 **/
	private String promptZXMessage;
	/** 最后第三天工作日*/
	private  Date  lastThreeDay;
	/** 最后一天工作日*/
	private  Date  lastDay;
	/** 最后一次提交的时间*/
	private  Date  lastSubmitTime;
	/** 是否最后三天    Y:是；N：不是*/
	private String isLastThreeDay;
	/**是否移交件*/
	private String isMoved;
	/**认领时间*/
	private Date movedTime;
	
	public String getIsLastThreeDay() {
		return isLastThreeDay;
	}
	public void setIsLastThreeDay(String isLastThreeDay) {
		this.isLastThreeDay = isLastThreeDay;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getAppNo() {
		return appNo;
	}
	public void setAppNo(String appNo) {
		this.appNo = appNo;
	}
	public String getIdNo() {
		return idNo;
	}
	public void setIdNo(String idNo) {
		this.idNo = idNo;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public Date getApplyDate() {
		return applyDate;
	}
	public void setApplyDate(Date applyDate) {
		this.applyDate = applyDate;
	}
	public Double getApplyAccount() {
		return applyAccount;
	}
	public void setApplyAccount(Double applyAccount) {
		this.applyAccount = applyAccount;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public Long getApplyTerm() {
		return applyTerm;
	}
	public void setApplyTerm(Long applyTerm) {
		this.applyTerm = applyTerm;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getProductCode() {
		return productCode;
	}
	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}
	public String getNewAppNo() {
		return newAppNo;
	}
	public void setNewAppNo(String newAppNo) {
		this.newAppNo = newAppNo;
	}
	public String getHasCreditReport() {
		return hasCreditReport;
	}
	public void setHasCreditReport(String hasCreditReport) {
		this.hasCreditReport = hasCreditReport;
	}
	public String getIfNext() {
		return ifNext;
	}
	public void setIfNext(String ifNext) {
		this.ifNext = ifNext;
	}
	public String getPromptMessages() {
		return promptMessages;
	}
	public void setPromptMessages(String promptMessages) {
		this.promptMessages = promptMessages;
	}
	public String getPromptZXMessage() {
		return promptZXMessage;
	}
	public void setPromptZXMessage(String promptZXMessage) {
		this.promptZXMessage = promptZXMessage;
	}
	public Date getLastThreeDay() {
		return lastThreeDay;
	}
	public void setLastThreeDay(Date lastThreeDay) {
		this.lastThreeDay = lastThreeDay;
	}
	public Date getLastDay() {
		return lastDay;
	}
	public void setLastDay(Date lastDay) {
		this.lastDay = lastDay;
	}
	public Date getLastSubmitTime() {
		return lastSubmitTime;
	}
	public void setLastSubmitTime(Date lastSubmitTime) {
		this.lastSubmitTime = lastSubmitTime;
	}
	public String getIsMoved() {
		return isMoved;
	}
	public void setIsMoved(String isMoved) {
		this.isMoved = isMoved;
	}
	public Date getMovedTime() {
		return movedTime;
	}
	public void setMovedTime(Date movedTime) {
		this.movedTime = movedTime;
	}
	@Override
	public String toString() {
		//String str = "";
		return "TmApplyMainInfo [id=" + id + ", userId=" + userId + ", appNo="
				+ appNo + ", idNo=" + idNo + ", userName=" + userName
				+ ", applyDate=" + applyDate + ", applyAccount=" + applyAccount
				+ ", productName=" + productName + ", applyTerm=" + applyTerm
				+ ", state=" + state + ", productCode=" + productCode
				+ ", newAppNo=" + newAppNo + ", hasCreditReport="
				+ hasCreditReport + ", ifNext=" + ifNext + ", promptMessages="
				+ promptMessages + ", promptZXMessage=" + promptZXMessage
				+ ", lastThreeDay=" + lastThreeDay 
				+ ", lastDay=" + lastDay 
				+ ", lastSubmitTime=" + lastSubmitTime + "]";
	}
}