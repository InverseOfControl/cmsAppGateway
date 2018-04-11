package com.zdmoney.credit.framework.vo;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import com.zdmoney.credit.framework.vo.common.BaseParamVo;


public class Vo_500001 extends BaseParamVo {
	
	@NotEmpty(message = "客户姓名不能为空500001")
	@Length(max=30, message="客户姓名长度不能超过30")
	private String name;
	
	@NotEmpty(message = "客户身份证号不能为空500001")
	@Length(max=30, message="客户身份证号长度不能超过30")
	private String idCard;

	@NotEmpty(message = "客户征信报告内容不能为空500001")
	private String htmlContent;
	
	@NotEmpty(message = "员工工号不能为空500001")
	@Length(max=30, message="员工工号长度不能超过30")
	private String userCode;

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

	public String getHtmlContent() {
		return htmlContent;
	}

	public void setHtmlContent(String htmlContent) {
		this.htmlContent = htmlContent;
	}

	public String getUserCode() {
		return userCode;
	}

	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}
}
