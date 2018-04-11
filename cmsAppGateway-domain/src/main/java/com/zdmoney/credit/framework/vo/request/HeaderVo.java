package com.zdmoney.credit.framework.vo.request;

import java.io.Serializable;

import org.hibernate.validator.constraints.NotBlank;

/**
 * 请求头部信息
 * 
 * @author Ivan
 *
 */
public class HeaderVo implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -2877357118042662330L;
	/** 会话令牌 **/
	private String sessionToken;
	/** 手机端应用版本号 **/
//	@NotBlank(message = "手机端应用版本号不能为空！")
	private String version;
	/** IOS提交 **/
	private String token;
	/**
	 * 1.系统的名称 如： iPhone OS，Android 2.设备系统的版本号； 如： 5.1、6.0、7.0 3.设备的型号
	 * 如：iPad、iphone、ipod touch
	 **/
	private String userAgent;
	/** 机构 **/
	private String mechanism;
	/** 平台 **/
	private String platform;
	/** 合作类型 **/
	private String togatherType;
	/** 渠道 **/
	private String openchannel;

	public String getSessionToken() {
		return sessionToken;
	}

	public void setSessionToken(String sessionToken) {
		this.sessionToken = sessionToken;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getUserAgent() {
		return userAgent;
	}

	public void setUserAgent(String userAgent) {
		this.userAgent = userAgent;
	}

	public String getMechanism() {
		return mechanism;
	}

	public void setMechanism(String mechanism) {
		this.mechanism = mechanism;
	}

	public String getPlatform() {
		return platform;
	}

	public void setPlatform(String platform) {
		this.platform = platform;
	}

	public String getTogatherType() {
		return togatherType;
	}

	public void setTogatherType(String togatherType) {
		this.togatherType = togatherType;
	}

	public String getOpenchannel() {
		return openchannel;
	}

	public void setOpenchannel(String openchannel) {
		this.openchannel = openchannel;
	}

}
