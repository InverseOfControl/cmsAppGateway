package com.zdmoney.credit.callinter;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.alibaba.fastjson.JSONObject;
import com.zdmoney.credit.common.constant.system.LogLevel;
import com.zdmoney.credit.common.exception.PlatformException;
import com.zdmoney.credit.common.exception.ResponseEnum;
import com.zdmoney.credit.common.json.JSONUtil;
import com.zdmoney.credit.common.util.Strings;

/***
 * 调用第三方接口 走HTTP协议
 * 
 * @author Ivan
 *
 */
@Component
public class HttpUrlConnection {

	private static Log logger = LogFactory.getLog(HttpUrlConnection.class);

	static MediaType mediaType = MediaType.parseMediaType("application/json; charset=UTF-8");

	static MediaType mediaTypeForm = MediaType.parseMediaType("application/x-www-form-urlencoded; charset=UTF-8");

	@Autowired
	RestTemplate restTemplate;

	/**
	 * 请求第三方接口
	 * 
	 * @param requestUrl
	 *            请求目标地址
	 * @param requestParam
	 *            请求参数
	 * @param type
	 *            响应数据类型
	 * @return
	 */
	public <T> T postForEntity(String requestUrl, JSONObject param, Class<T> type) {
		return postForEntity(requestUrl, param, mediaTypeForm, type);
	}

	/**
	 * 请求第三方接口
	 * 
	 * @param url
	 *            请求目标地址
	 * @param mediaType
	 *            头文件信息
	 * @param param
	 *            请求参数
	 * @param type
	 *            响应数据类型
	 * @return
	 */
	public <T> T postForEntity(String requestUrl, JSONObject param, MediaType mediaType, Class<T> type) {
		if (Strings.isEmpty(requestUrl)) {
			throw new PlatformException(ResponseEnum.THIRD_PARTY_INTERFACE_ERROR, "请求地址为空！")
					.applyLogLevel(LogLevel.WARN);
		}
		ResponseEntity<T> responseEntity = null;
		try {
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(mediaType);
			HttpEntity<String> request = new HttpEntity<String>(JSONUtil.toUrlParam(param), headers);
			responseEntity = restTemplate.postForEntity(requestUrl, request, type);
			HttpStatus httpStatus = responseEntity.getStatusCode();
			switch (httpStatus) {
			case OK:
				logger.debug("[status:200]" + requestUrl + "参数：" + param);
				/** 正常响应 **/
				T value = responseEntity.getBody();
				if (value instanceof String) {
					//return (T) new String(value.toString().getBytes("ISO-8859-1"), "UTF-8");
				//} else {
					return value;
				}
			default:
				logger.error("[status:" + httpStatus + "]" + requestUrl + "参数：" + param);
				throw new PlatformException(ResponseEnum.THIRD_PARTY_INTERFACE_ERROR, "URL：" + requestUrl + " ; 响应码："
						+ httpStatus).applyLogLevel(LogLevel.WARN);
			}
		} catch (PlatformException ex) {
			throw new PlatformException(ResponseEnum.THIRD_PARTY_INTERFACE_ERROR, "URL：" + requestUrl + " ; "
					+ ex.getMessage()).applyLogLevel(LogLevel.WARN);
		} catch (Exception ex) {
			logger.error(requestUrl + "参数：" + param, ex);
			throw new PlatformException(ResponseEnum.THIRD_PARTY_INTERFACE_ERROR, "URL：" + requestUrl + " 详细信息请查看日志！")
					.applyLogLevel(LogLevel.WARN);
		}
	}
	
	/**
     * Spring RestTemplate 发送post请求,传递json参数
     * @param requestUrl
     * @param json
     * @param type
     * @return
     */
    public <T> T postForJson(String requestUrl, String json, Class<T> type) {
		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(mediaType);
		headers.add("Accept", MediaType.APPLICATION_JSON.toString());
		org.springframework.http.HttpEntity<String> formEntity = new org.springframework.http.HttpEntity<String>(json, headers);
		restTemplate.postForObject(requestUrl, formEntity, type);
		return restTemplate.postForObject(requestUrl, formEntity, type);
	}
}
