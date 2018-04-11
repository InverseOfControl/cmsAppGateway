package com.zdmoney.credit.framework.request;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 包裹内置HttpServletRequest
 * 
 * @author Ivan
 *
 */
public class RequestWrap {

	protected static Log logger = LogFactory.getLog(RequestWrap.class);
	/** 内置request对象 **/
	private HttpServletRequest request = null;

	/** 请求时间(用于统计接口耗时) **/
	private long requestTime = System.currentTimeMillis();

	public RequestWrap() {

	}

	public RequestWrap(HttpServletRequest request) {
		this.request = request;
	}

	public long getRequestTime() {
		return requestTime;
	}

}
