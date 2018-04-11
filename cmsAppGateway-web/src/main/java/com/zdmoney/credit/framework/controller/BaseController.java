package com.zdmoney.credit.framework.controller;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.alibaba.fastjson.JSONObject;
import com.zdmoney.credit.common.vo.ResponseInfo;

/**
 * Controller 父类
 * 
 * @author Ivan
 *
 */
public class BaseController {

	protected static Log logger = LogFactory.getLog(BaseController.class);

	/**
	 * 转JSON字符串
	 * 
	 * @param responseInfo
	 * @return
	 */
	public static String toResponseJSON(ResponseInfo responseInfo) {
		if (responseInfo == null) {
			logger.warn("ResponseInfoToJSONString Method Param IS NULL");
			return "{}";
		}
		String jsonStr = JSONObject.toJSONString(responseInfo);
		return jsonStr;
	}

}
