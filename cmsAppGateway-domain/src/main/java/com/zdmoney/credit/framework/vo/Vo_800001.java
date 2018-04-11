package com.zdmoney.credit.framework.vo;

import java.util.ArrayList;
import java.util.Map;

import org.hibernate.validator.constraints.NotEmpty;

import com.zdmoney.credit.framework.vo.common.BaseParamVo;

public class Vo_800001 extends BaseParamVo {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@NotEmpty(message = "申请单号不能为空800001")
	private String appNo;
	
	private String id;
	
	@NotEmpty(message = "文件列表不能为空800001")
	private ArrayList<Map<String,Object>> uploadFileList;
	
	//文件类型
	private String fileType;
	
	public String getFileType() {
		return fileType;
	}

	public void setFileType(String fileType) {
		this.fileType = fileType;
	}
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getAppNo() {
		return appNo;
	}

	public void setAppNo(String appNo) {
		this.appNo = appNo;
	}

	public ArrayList<Map<String,Object>> getUploadFileList() {
		return uploadFileList;
	}

	public void setUploadFileList(ArrayList<Map<String,Object>> uploadFileList) {
		this.uploadFileList = uploadFileList;
	}
}
