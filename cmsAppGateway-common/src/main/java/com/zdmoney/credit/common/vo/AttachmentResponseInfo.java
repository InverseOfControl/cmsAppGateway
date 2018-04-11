package com.zdmoney.credit.common.vo;

import com.zdmoney.credit.common.exception.ResponseEnum;
import com.zdmoney.credit.common.util.Strings;

/**
 * 
 * 响应数据封装(带附件)
 * 
 * @author Ivan
 *
 */
public class AttachmentResponseInfo<E> {
	private static final long serialVersionUID = -6276507727857308586L;
	
	private String status = "-1";
	private String respDesc;
	/** 附件 **/
	private Object infos;

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getRespDesc() {
		return respDesc;
	}

	public void setRespDesc(String respDesc) {
		this.respDesc = respDesc;
	}

	public AttachmentResponseInfo() {
		super();
	}

	// public AttachmentResponseInfo(ResponseEnum responseEnum, Object...
	// arguments) {
	// super(responseEnum.getCode(), Strings.format(responseEnum.getDesc(),
	// arguments));
	// }
	//
	// public AttachmentResponseInfo(String preCode, String preMsg) {
	// super(preCode, preMsg);
	// }

	public Object getInfos() {
		return infos;
	}

	public void setInfos(Object infos) {
		this.infos = infos;
	}

}
