package com.zdmoney.credit.framework.vo;

import java.util.ArrayList;
import java.util.Map;

import org.hibernate.validator.constraints.NotEmpty;

import com.zdmoney.credit.framework.vo.common.BaseParamVo;

public class Vo_800003 extends BaseParamVo {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	
	
	@NotEmpty(message = "申请单号不能为空800003")
	private String appNo;
	
	@NotEmpty(message = "类型不能为空800003")
	private String fileType;
	
	public String getFileType() {
		return fileType;
	}

	public void setFileType(String fileType) {
		this.fileType = fileType;
	}

	public String getAppNo() {
		return appNo;
	}

	public void setAppNo(String appNo) {
		this.appNo = appNo;
	}
}
