package com.zdmoney.credit.framework.vo;

import java.io.Serializable;
import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;

import org.hibernate.validator.constraints.NotEmpty;

import com.zdmoney.credit.framework.vo.common.BaseParamVo;

public class Vo_600004 extends BaseParamVo {
	
	@NotEmpty(message = "员工工号不能为空,600004")
	private String userCode;
	
	@NotEmpty(message = "申请时间不能为空,600004")
	private Date applyDate;
	
	@NotEmpty(message = "拒绝原因不能为空,600004")
	private String refuseReason;

	@NotEmpty(message = "申请信息不能为空,600004")
	private HashMap<String,Serializable> applyInfo;//申请信息
	
	private HashMap<String,Serializable> persionInfo;//个人信息
	
	private ArrayList<HashMap<String,Serializable>> empItemInfo;//工作信息
	
	private ArrayList<HashMap<String,Serializable>> contactPersonInfo;//联系人信息列表
	
	private ArrayList<HashMap<String,Serializable>> assetsInfo;//资产信息
	
	public String getUserCode() {
		return userCode;
	}

	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}
	
	public Date getApplyDate() {
		return applyDate;
	}

	public void setApplyDate(Date applyDate) {
		this.applyDate = applyDate;
	}

	public String getRefuseReason() {
		return refuseReason;
	}

	public void setRefuseReason(String refuseReason) {
		this.refuseReason = refuseReason;
	}
	
	public HashMap<String, Serializable> getApplyInfo() {
		return applyInfo;
	}

	public void setApplyInfo(HashMap<String, Serializable> applyInfo) {
		this.applyInfo = applyInfo;
	}

	public HashMap<String, Serializable> getPersionInfo() {
		return persionInfo;
	}

	public void setPersionInfo(HashMap<String, Serializable> persionInfo) {
		this.persionInfo = persionInfo;
	}

	public ArrayList<HashMap<String, Serializable>> getEmpItemInfo() {
		return empItemInfo;
	}

	public void setEmpItemInfo(ArrayList<HashMap<String, Serializable>> empItemInfo) {
		this.empItemInfo = empItemInfo;
	}

	public ArrayList<HashMap<String, Serializable>> getContactPersonInfo() {
		return contactPersonInfo;
	}

	public void setContactPersonInfo(ArrayList<HashMap<String, Serializable>> contactPersonInfo) {
		this.contactPersonInfo = contactPersonInfo;
	}

	public ArrayList<HashMap<String, Serializable>> getAssetsInfo() {
		return assetsInfo;
	}

	public void setAssetsInfo(ArrayList<HashMap<String, Serializable>> assetsInfo) {
		this.assetsInfo = assetsInfo;
	}

}
