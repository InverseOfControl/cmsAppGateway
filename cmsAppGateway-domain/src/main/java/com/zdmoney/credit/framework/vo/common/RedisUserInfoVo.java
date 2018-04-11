package com.zdmoney.credit.framework.vo.common;

import java.util.Date;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * 记录登陆用户信息（序列化保存到Redis中）
 * 
 * @author Ivan
 *
 */
public class RedisUserInfoVo extends BaseRedisVo {
	/**
	 * 
	 */
	private static final long serialVersionUID = -4059354658339851368L;
	/** 工号 **/
	private String userCode;
	/** 设备ID **/
	private String deviceId;
	/** App端会话Token **/
	private String token;
	/** 登陆时间 **/
	@JSONField (format="yyyy-MM-dd HH:mm:ss")  
	private Date loginTime;

	public String getUserCode() {
		return userCode;
	}

	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}

	public String getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public Date getLoginTime() {
		return loginTime;
	}

	public void setLoginTime(Date loginTime) {
		this.loginTime = loginTime;
	}

}
