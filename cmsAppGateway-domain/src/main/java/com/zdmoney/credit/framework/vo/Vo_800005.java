package com.zdmoney.credit.framework.vo;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.hibernate.validator.constraints.NotEmpty;

import com.zdmoney.credit.framework.vo.common.BaseParamVo;

public class Vo_800005 extends BaseParamVo {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@NotEmpty(message = "文件列表不能为空800005")
	private ArrayList<Map<String,Object>> reOrderFileList;
	
	@NotEmpty(message = "申请单号不能为空800005")
	private String appNo;
	
	@NotEmpty(message = "文件类型不能为空800005")
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

	public ArrayList<Map<String, Object>> getReOrderFileList() {
		return reOrderFileList;
	}

	public void setReOrderFileList(ArrayList<Map<String, Object>> reOrderFileList) {
		this.reOrderFileList = reOrderFileList;
	}





}
