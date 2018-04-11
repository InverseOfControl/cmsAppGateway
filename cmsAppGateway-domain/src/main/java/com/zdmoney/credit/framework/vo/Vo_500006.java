package com.zdmoney.credit.framework.vo;

import org.hibernate.validator.constraints.NotEmpty;

import com.zdmoney.credit.framework.vo.common.BaseParamVo;

public class Vo_500006 extends BaseParamVo {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@NotEmpty(message = "手机号码不能为空500006")
	private String mobile;
	
	@NotEmpty(message = "文件名称不能为空500006")
	private String fileName;
	
	@NotEmpty(message = "文件内容不能为空500006")
	private String fileContent;
//	private Map<String,Object> fileContent;
	// 操作人姓名
	private String staffName;
	
	//备用字段
	private String field;

  public String getMobile() {
    return mobile;
  }

  public void setMobile(String mobile) {
    this.mobile = mobile;
  }

  public String getFileName() {
    return fileName;
  }

  public String getField() {
    return field;
  }

  public void setField(String field) {
    this.field = field;
  }

  public String getStaffName() {
    return staffName;
  }

  public void setStaffName(String staffName) {
    this.staffName = staffName;
  }

  public String getFileContent() {
    return fileContent;
  }

  public void setFileContent(String fileContent) {
    this.fileContent = fileContent;
  }

  public void setFileName(String fileName) {
    this.fileName = fileName;
  }

  
  
  
  
  
	
  
	
}
