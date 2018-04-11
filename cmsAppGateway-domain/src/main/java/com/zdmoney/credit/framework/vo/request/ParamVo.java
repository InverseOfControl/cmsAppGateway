package com.zdmoney.credit.framework.vo.request;

import java.io.Serializable;

import org.hibernate.validator.constraints.NotBlank;

/**
 * 请求参数Vo
 * 
 * @author Ivan
 *
 */
public class ParamVo implements Serializable {
	/** 功能号 参数名：arg0 **/
	@NotBlank(message = "功能号不能为空！")
	private String funcId;
	/** 业务参数(密文) 参数名：arg1 **/
	@NotBlank(message = "业务参数不能为空！")
	private String params;
	/** 功能号业务参数 **/
	private String reqParam;
	/** 解密时用到的Key 参数名：arg2 **/
	private String key;
	/** 签名信息 参数名：arg3 **/
	private String sign;

	public String getFuncId() {
		return funcId;
	}

	public void setFuncId(String funcId) {
		this.funcId = funcId;
	}

	public String getParams() {
		return params;
	}

	public void setParams(String params) {
		this.params = params;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}

	public String getReqParam() {
		return reqParam;
	}

	public void setReqParam(String reqParam) {
		this.reqParam = reqParam;
	}

}
