package com.zdmoney.credit.framework.vo.common;

import java.io.Serializable;

/**
 * 业务参数基类
 * 
 * @author Ivan
 *
 */
public class BaseParamVo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8251939043126669854L;
	
	public String operatorName; //操作人姓名
	public String operatorCode; //操作人工号
	
	public String getOperatorName() {
		return operatorName;
	}
	public void setOperatorName(String operatorName) {
		this.operatorName = operatorName;
	}
	public String getOperatorCode() {
		return operatorCode;
	}
	public void setOperatorCode(String operatorCode) {
		this.operatorCode = operatorCode;
	}

}
