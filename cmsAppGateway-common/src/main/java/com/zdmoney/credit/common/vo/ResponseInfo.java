package com.zdmoney.credit.common.vo;

import java.io.Serializable;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.zdmoney.credit.common.exception.ResponseEnum;
import com.zdmoney.credit.common.util.Strings;

/**
 * 
 * 响应数据封装
 * 
 * @author Ivan
 *
 */
public class ResponseInfo<E> implements Serializable, Cloneable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4112845183658483869L;

	/** 响应状态码 **/
	private String code;
	/** 响应内容 **/
	private AttachmentResponseInfo<E> msgEx;

	public ResponseInfo() {

	}

	public Object clone() {
		try {
			return super.clone();
		} catch (CloneNotSupportedException e) {
			return null;
		}
	}

	public ResponseInfo(ResponseEnum responseEnum, Object... arguments) {
		this.code = responseEnum.getCode();
		msgEx = new AttachmentResponseInfo();
		msgEx.setRespDesc(Strings.format(responseEnum.getDesc(), arguments));
		this.msgEx = msgEx;
	}

	public ResponseInfo(String code, String respDesc) {
		this.code = code;
		msgEx = new AttachmentResponseInfo();
		msgEx.setRespDesc(respDesc);
		this.msgEx = msgEx;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public AttachmentResponseInfo<E> getMsgEx() {
		return msgEx;
	}

	public void setMsgEx(AttachmentResponseInfo<E> msgEx) {
		this.msgEx = msgEx;
	}

	public String toJSONText() {
		if (this.msgEx.getInfos() == null) {
			this.msgEx.setInfos(new JSONObject());
		}
		return JSON.toJSONString(this);
	}

}
