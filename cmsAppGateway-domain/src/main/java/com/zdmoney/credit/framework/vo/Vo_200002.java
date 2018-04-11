package com.zdmoney.credit.framework.vo;

import org.hibernate.validator.constraints.NotEmpty;

import com.zdmoney.credit.framework.vo.common.BaseParamVo;

/**
 * 功能号200002 Vo对象
 * @author YM10189
 *
 */
public class Vo_200002 extends BaseParamVo {
	private static final long serialVersionUID = -776579312130453593L;

	@NotEmpty(message = "工号不能为空600011")
	private String userCode; 
	
	private int pageNum=0;
	
	private int pageSize=20;
	
	private String objValue;

	public String getUserCode() {
		return userCode;
	}

	public void setUserCode(String userCode) {
		this.userCode = userCode;
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

	public String getObjValue() {
		return objValue;
	}

	public void setObjValue(String objValue) {
		this.objValue = objValue;
	}
	
}
