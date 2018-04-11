package com.zdmoney.credit.common.exception;

/**
 * 返回响应状态枚举
 * @author Ivan
 *
 */
public enum ResponseEnum {
	
	/**图片管理系统响应码*/
	PIC_SUCCESS("000000","图片管理系统调用成功"),
	PIC_FAIL("111111","图片管理系统调用失败"),
	FILE_NOT_GENERATE("0332","图片生成失败"),
	
	/** JSON格式不正确 **/
	VALIDATE_JSON_FORMAT_ERROR("0105","JSON格式有误!{0}"),
	
	THIRD_PARTY_INTERFACE_ERROR("0222","调用第三方接口异常!{0}"),
	
	/** 请求参数不正确 **/
	VALIDATE_PARAM_VALID_ERROR("0103","{0}"),
	/** 功能号未定义 **/
	FUNC_ID_NOT_EXISTS("0104","功能号：{0} 未定义!"),
	APP_VERSION_FAIL("2101","App版本号校验失败！{0}"),
	APP_VERSION_LOW("2100","App版本号过低！{0}"),
	
	DECRYPT_FAIL("0109","解密失败! {0}"),
	REQUEST_TIMEOUT("0300","请求超时! {0}"),
	/** 签名校验不通过 **/
	SIGN_FAIL("0110","签名校验不通过! {0}"),
	FUNC_ID_CALL_ERROR("0210","功能号内部异常!{0}"),
	SESSION_EXPIRE("0310","会话超时或已失效!{0}"),
	SESSION_KILL("0311","当前用户已在其它终端登陆，强制下线!{0}"),
	SYS_SUCCESS("0000","操作成功"),
	SYS_FAILD("9000","系统忙,请联系管理员"),
	/** 未知 **/
	FULL_MSG("9001","{0}");
	

	
	private final String code;
	private final String desc;

	private ResponseEnum(String code, String desc) {
		this.code = code;
		this.desc = desc;
	}

	public String getCode() {
		return code;
	}

	public String getDesc() {
		return desc;
	}
}
