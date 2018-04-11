package com.zdmoney.credit.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * 配置信息跟system.properties一致
 * 
 * @author Ivan
 *
 */
@Component
public class SystemProperties {

	/**APP提交展业网关获取地址**/
	@Value("${sys.appPicGateWayUrl}")
	private String appPicGateWayUrl;
	
	/** 业务参数是否解密 **/
	@Value("${sys.isNeedDecryptParam}")
	private boolean isNeedDecryptParam;

	/** 是否检查APP版本 **/
	@Value("${sys.isCheckVersion}")
	private boolean isCheckVersion;

	/** 是否对响应结果进行加密 **/
	@Value("${sys.encryptResponse}")
	private boolean encryptResponse;

	/** 是否校验签名 **/
	@Value("${sys.isCheckSign}")
	private boolean isCheckSign;

	/** 签名Key **/
	@Value("${sys.md5SignKey}")
	private String md5SignKey;

	@Value("${sys.checkTime}")
	private int checkTime;

	/** 公钥文件 **/
	@Value("${sys.rsa.publicFile}")
	private String rsaPublicFile;

	/** 私钥文件 **/
	@Value("${sys.rsa.privateFile}")
	private String rsaPrivateFile;

	/** 响应报文格式及编码 **/
	@Value("${sys.responseContentType}")
	private String responseContentType;

	/** 是否校验会话状态及单点登陆 **/
	@Value("${sys.isSingleLogin}")
	private boolean isSingleLogin;
	
	/** 是否进行系统自动取消 **/
	@Value("${sys.appKey}")
	private String appKey;
	
	/** 是否进行系统自动取消 **/
	@Value("${sys.isAutoCancel}")
	private boolean isAutoCancel;
	
	/** 网关测试地址 **/
	@Value("${sys.testUrl}")
	private String testUrl;
	
	/**是否有征信报告**/
	@Value("${sys.isHasCreditReport}")
	private boolean isHasCreditReport;
	
	
	/** 存储系统HTTP接口地址 **/
	@Value("${saveapp.saveserviceUrl}")
	private String saveserviceUrl;

	public String getSaveserviceUrl() {
		return saveserviceUrl;
	}
	
	public boolean isNeedDecryptParam() {
		return isNeedDecryptParam;
	}

	public boolean isCheckVersion() {
		return isCheckVersion;
	}

	public boolean isEncryptResponse() {
		return encryptResponse;
	}

	public boolean isCheckSign() {
		return isCheckSign;
	}

	public String getMd5SignKey() {
		return md5SignKey;
	}

	public int getCheckTime() {
		return checkTime;
	}

	public String getRsaPublicFile() {
		return rsaPublicFile;
	}

	public String getRsaPrivateFile() {
		return rsaPrivateFile;
	}

	public String getResponseContentType() {
		return responseContentType;
	}

	public boolean isSingleLogin() {
		return isSingleLogin;
	}
	
	public String getAppKey() {
		return appKey;
	}
	
	public boolean isAutoCancel() {
		return isAutoCancel;
	}
	
	public String getTestUrl() {
		return testUrl;
	}
	
	public boolean isHasCreditReport() {
		return isHasCreditReport;
	}

	public String getAppPicGateWayUrl() {
		return appPicGateWayUrl;
	}
}
