package com.zdmoney.credit.framework.vo;

import org.hibernate.validator.constraints.NotEmpty;

import com.zdmoney.credit.framework.vo.common.BaseParamVo;

public class Vo_600008 extends BaseParamVo {
	
	@NotEmpty(message = "姓名不能为空600008")
	private String name;
	
	@NotEmpty(message = "渠道编号不能为空600008")
	private String channelCode;
	
	@NotEmpty(message = "申请产品不能为空600008")
	private String productCd;
	
	@NotEmpty(message = "申请金额不能为空600008")
	private String applyLmt;
	
	@NotEmpty(message = "申请期限不能为空600008")
	private String applyTerm;
	
	@NotEmpty(message = "预计首次还款日不能为空600008")
	private String fristPaymentDate;
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
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
	
	public String getFristPaymentDate() {
		return fristPaymentDate;
	}
	
	public void setFristPaymentDate(String fristPaymentDate) {
		this.fristPaymentDate = fristPaymentDate;
	}

	public String getChannelCode() {
		return channelCode;
	}

	public void setChannelCode(String channelCode) {
		this.channelCode = channelCode;
	}
	
}

