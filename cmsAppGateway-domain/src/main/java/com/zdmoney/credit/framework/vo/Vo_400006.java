package com.zdmoney.credit.framework.vo;

import org.hibernate.validator.constraints.NotEmpty;

import com.zdmoney.credit.framework.vo.common.BaseParamVo;

/**
 * 功能号400001 Vo对象
 * 
 * @author zln
 *
 */
public class Vo_400006 extends BaseParamVo {
	@NotEmpty(message = "工号不能为空400001")
	private String userCode;
	
	@NotEmpty(message = "密码不能为空400001")
	private String passWord;
	
	public String getUserCode() {
		return userCode;
	}

	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}

	public String getPassWord() {
		return passWord;
	}

	public void setPassWord(String passWord) {
		this.passWord = passWord;
	}
	
}
