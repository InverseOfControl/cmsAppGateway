package com.zdmoney.credit.framework.vo;

import org.hibernate.validator.constraints.NotEmpty;
import com.zdmoney.credit.framework.vo.common.BaseParamVo;

public class Vo_800002 extends BaseParamVo {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@NotEmpty(message = "申请单号不能为空800002")
	private String appNo;
	
	public String getAppNo() {
		return appNo;
	}

	public void setAppNo(String appNo) {
		this.appNo = appNo;
	}

}
