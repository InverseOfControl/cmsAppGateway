package com.zdmoney.credit.framework.vo;

import com.zdmoney.credit.framework.vo.common.BaseParamVo;

public class Vo_600014 extends BaseParamVo{

	private String customerName;//客户姓名
	private String customerNo;//客户编号
	private String applicationCaseNo;//申请件编号
	private String fileName;//文件名称
	private String fileKey;//文件KEY
	private String fileType;//文件类型（1、视频;2、音频）
	private String fileSize;//文件大小
	private String downloadUrl;//下载路径
	private String userName;//上传人姓名
	private String userNum;//上传人工号 
	private String certifyBusinessDepart;//证件营业部
	private String uploadTime;//上传时间
	private String sysCode;//CODE
	public String getCustomerName() {
		return customerName;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	public String getCustomerNo() {
		return customerNo;
	}
	public void setCustomerNo(String customerNo) {
		this.customerNo = customerNo;
	}
	public String getApplicationCaseNo() {
		return applicationCaseNo;
	}
	public void setApplicationCaseNo(String applicationCaseNo) {
		this.applicationCaseNo = applicationCaseNo;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String getFileKey() {
		return fileKey;
	}
	public void setFileKey(String fileKey) {
		this.fileKey = fileKey;
	}
	public String getFileType() {
		return fileType;
	}
	public void setFileType(String fileType) {
		this.fileType = fileType;
	}
	public String getFileSize() {
		return fileSize;
	}
	public void setFileSize(String fileSize) {
		this.fileSize = fileSize;
	}
	public String getDownloadUrl() {
		return downloadUrl;
	}
	public void setDownloadUrl(String downloadUrl) {
		this.downloadUrl = downloadUrl;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getUserNum() {
		return userNum;
	}
	public void setUserNum(String userNum) {
		this.userNum = userNum;
	}
	public String getCertifyBusinessDepart() {
		return certifyBusinessDepart;
	}
	public void setCertifyBusinessDepart(String certifyBusinessDepart) {
		this.certifyBusinessDepart = certifyBusinessDepart;
	}
	public String getUploadTime() {
		return uploadTime;
	}
	public void setUploadTime(String uploadTime) {
		this.uploadTime = uploadTime;
	}
	public String getSysCode() {
		return sysCode;
	}
	public void setSysCode(String sysCode) {
		this.sysCode = sysCode;
	}
	

}
