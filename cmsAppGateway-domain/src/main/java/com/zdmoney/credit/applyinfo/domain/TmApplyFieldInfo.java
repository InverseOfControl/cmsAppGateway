package com.zdmoney.credit.applyinfo.domain;

import java.util.Date;

import com.zdmoney.credit.common.constant.system.Constant;
import com.zdmoney.credit.framework.domain.BaseDomain;

/**
 * 申请信息表
 * tm_apply_main_info
 * @author zhb
 *
 */
public class TmApplyFieldInfo extends BaseDomain {
	
	
	private static final long serialVersionUID = 146543487878L;
	/** 主键PK **/
	private Long id;
	/** 申请单号 **/
	private String appNo;
	/** 参数key **/
	private String fieldKey;
	/** obj对象 **/
	private String fieldObjValue = Constant.dObjValue;
	/** arr对象 **/
	private String fieldArrValue = Constant.dArrValue;
	/** 对象类型 **/
	private String fieldType;
	/** 状态  **/
	private String state;
	/** 创建时间 **/
	private Date createDate;
	/** 修改时间 **/
	private Date updateDate;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getAppNo() {
		return appNo;
	}
	public void setAppNo(String appNo) {
		this.appNo = appNo;
	}
	public String getFieldKey() {
		return fieldKey;
	}
	public void setFieldKey(String fieldKey) {
		this.fieldKey = fieldKey;
	}
	public String getFieldObjValue() {
		return fieldObjValue;
	}
	public void setFieldObjValue(String fieldObjValue) {
		this.fieldObjValue = fieldObjValue;
	}
	public String getFieldArrValue() {
		return fieldArrValue;
	}
	public void setFieldArrValue(String fieldArrValue) {
		this.fieldArrValue = fieldArrValue;
	}
	public String getFieldType() {
		return fieldType;
	}
	public void setFieldType(String fieldType) {
		this.fieldType = fieldType;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public Date getUpdateDate() {
		return updateDate;
	}
	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}
	
	@Override
	public String toString() {
		return "TmApplyFieldInfo [id=" + id + ", appNo=" + appNo
				+ ", fieldKey=" + fieldKey + ", fieldObjValue=" + fieldObjValue
				+ ", fieldArrValue=" + fieldArrValue + ", fieldType="
				+ fieldType + ", state=" + state + ", createDate=" + createDate
				+ ", updateDate=" + updateDate +  "]";
	}
   
	
}