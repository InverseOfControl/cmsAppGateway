package com.zdmoney.credit.framework.vo.common;

/**
 * 密钥信息封装
 * 
 * @author Ivan
 *
 */
public class SecretKeyVo {
	/** 未解密的Key(密文) arg2 **/
	private String encryptKey;
	/** 已解密的Key(明文) arg2 **/
	private String decryptKey;
	/** 签名信息 **/
	private String sign;
	/** 签名是否通过 **/
	private boolean sigSuccess = false;

	public String getEncryptKey() {
		return encryptKey;
	}

	public void setEncryptKey(String encryptKey) {
		this.encryptKey = encryptKey;
	}

	public String getDecryptKey() {
		return decryptKey;
	}

	public void setDecryptKey(String decryptKey) {
		this.decryptKey = decryptKey;
	}

	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}

	public boolean isSigSuccess() {
		return sigSuccess;
	}

	public void setSigSuccess(boolean sigSuccess) {
		this.sigSuccess = sigSuccess;
	}

}
