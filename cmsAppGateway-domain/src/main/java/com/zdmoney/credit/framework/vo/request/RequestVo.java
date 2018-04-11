package com.zdmoney.credit.framework.vo.request;

import org.hibernate.validator.constraints.NotBlank;

/**
 * 接口请求参数封装
 * 
 * @author Ivan
 *
 */
public class RequestVo {
	/** 头部信息 **/
	private HeaderVo headerVo;
	/** URL 参数信息 **/
	private ParamVo paramVo;
	@NotBlank(message = "项目编号参数不能为空！")
	/** 项目编号 **/
	private String projectNo;
	
	/** 终端设备ID **/
	private String deviceId;

	public HeaderVo getHeaderVo() {
		return headerVo;
	}

	public void setHeaderVo(HeaderVo headerVo) {
		this.headerVo = headerVo;
	}

	public ParamVo getParamVo() {
		return paramVo;
	}

	public void setParamVo(ParamVo paramVo) {
		this.paramVo = paramVo;
	}

	public String getProjectNo() {
		return projectNo;
	}

	public void setProjectNo(String projectNo) {
		this.projectNo = projectNo;
	}

	public String getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}

}
