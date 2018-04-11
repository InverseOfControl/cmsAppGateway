package com.zdmoney.credit.framework.vo;

import org.hibernate.validator.constraints.NotEmpty;

import com.zdmoney.credit.framework.vo.common.BaseParamVo;

public class Vo_600005 extends BaseParamVo {
	

  private static final long serialVersionUID = 1L;

  @NotEmpty(message = "工号不能为空")
	private String userCode;
	
	@NotEmpty(message = "产品类型不能为空")
	private String productCd;
	
	@NotEmpty(message = "申请金额不能为空")
	private String applyLmt;
	
	@NotEmpty(message = "申请期限不能为空")
	private String applyTerm;
	
	@NotEmpty(message = "申请人姓名不能为空")
	private String name;
	
	@NotEmpty(message = "身份证号码不能为空")
	private String idNo;
	
	@NotEmpty(message = "贷款用途不能为空")
	private String creditApplication;
	
	private String ifPri;
	
	private String remark;
	
	@NotEmpty(message = " 基本信息保存字段不能为空")
	private String applyInfoField;
	
	@NotEmpty(message = "产品名称不能为空")
	private String productName;
	
	private String link; //对接规则网关-申请所处环节
	
	private String appNo;

	public String getUserCode() {
		return userCode;
	}

	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}

	public String getProductCd() {
		return productCd;
	}

	public void setProductCd(String productCd) {
		this.productCd = productCd;
	}

	public String getApplyLmt() {
		return applyLmt;
	}

	public void setApplyLmt(String applyLmt) {
		this.applyLmt = applyLmt;
	}

	public String getApplyTerm() {
		return applyTerm;
	}

	public void setApplyTerm(String applyTerm) {
		this.applyTerm = applyTerm;
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

	public String getCreditApplication() {
		return creditApplication;
	}

	public void setCreditApplication(String creditApplication) {
		this.creditApplication = creditApplication;
	}

	public String getIfPri() {
		return ifPri;
	}

	public void setIfPri(String ifPri) {
		this.ifPri = ifPri;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getApplyInfoField() {
		return applyInfoField;
	}

	public void setApplyInfoField(String applyInfoField) {
		this.applyInfoField = applyInfoField;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getAppNo() {
		return appNo;
	}

	public void setAppNo(String appNo) {
		this.appNo = appNo;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}
}
