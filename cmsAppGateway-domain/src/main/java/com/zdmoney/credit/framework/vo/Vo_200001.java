package com.zdmoney.credit.framework.vo;

import org.hibernate.validator.constraints.NotEmpty;

import com.zdmoney.credit.framework.vo.common.BaseParamVo;

/**
 * 功能号200001 Vo对象
 * @author YM10189
 *
 */
public class Vo_200001 extends BaseParamVo {
	private static final long serialVersionUID = 5896653687042056690L;
	
	private String objValue;
	
	@NotEmpty(message = "工号不能为空700007")
	private String userCode;
	
	private int pageSize;
	
	private int pageNum;

	public String getObjValue() {
		return objValue;
	}

	public void setObjValue(String objValue) {
		this.objValue = objValue;
	}

	public String getUserCode() {
		return userCode;
	}

	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getPageNum() {
		return pageNum;
	}

	public void setPageNum(int pageNum) {
		this.pageNum = pageNum;
	}
	
	
	
}
