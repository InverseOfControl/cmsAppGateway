package com.zdmoney.credit.framework.vo;

import java.util.ArrayList;
import java.util.Map;

import org.hibernate.validator.constraints.NotEmpty;

import com.zdmoney.credit.framework.vo.common.BaseParamVo;

public class Vo_800006 extends BaseParamVo {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@NotEmpty(message = "原来的申请单号不能为空800006")
	private String oldAppNo;
	
	@NotEmpty(message = "文件类型不能为空800006")
	private String fileType;
	
	@NotEmpty(message = "新的申请单号不能为空800006")
	private String newAppNo;
	

	public String getOldAppNo() {
		return oldAppNo;
	}

	public void setOldAppNo(String oldAppNo) {
		this.oldAppNo = oldAppNo;
	}

	

	public String getNewAppNo() {
		return newAppNo;
	}

	public void setNewAppNo(String newAppNo) {
		this.newAppNo = newAppNo;
	}

	public String getFileType() {
		return fileType;
	}

	public void setFileType(String fileType) {
		this.fileType = fileType;
	}

	
}
