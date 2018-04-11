package com.zdmoney.credit.framework.response;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.alibaba.fastjson.JSON;
import com.zdmoney.credit.common.util.SpringContextUtil;
import com.zdmoney.credit.common.util.Strings;
import com.zdmoney.credit.common.util.UrlUtil;
import com.zdmoney.credit.common.util.XXTeaUtil;
import com.zdmoney.credit.common.vo.ResponseInfo;
import com.zdmoney.credit.config.SystemProperties;
import com.zdmoney.credit.framework.RequestContext;
import com.zdmoney.credit.framework.vo.common.SecretKeyVo;
import com.zdmoney.credit.framework.wrap.PreAdviceWrap;

/**
 * 包裹内置HttpServletResponse
 * 
 * @author Ivan
 *
 */
public class ResponseWrap {
	SystemProperties systemProperties;

	protected static Log logger = LogFactory.getLog(ResponseWrap.class);
	/** 内置response对象 **/
	private HttpServletResponse response = null;

	/** 响应时间(用于统计接口耗时) **/
	private long responseTime = 0;

	private ResponseInfo responseInfo;

	public ResponseWrap() {

	}

	public ResponseWrap(HttpServletResponse response) {
		this.response = response;
		systemProperties = SpringContextUtil.getBean(SystemProperties.class);
		String contentType = systemProperties.getResponseContentType();
		this.response.setContentType(contentType);
	}

	public void write(ResponseInfo responseInfo) {
		/** 记录响应时间 **/
		responseTime = System.currentTimeMillis();
		this.responseInfo = responseInfo;
		if (responseInfo == null) {
			logger.warn("response写入失败，响应内容为空");
			return;
		}
		/** 对响应结果进行加密 **/
		ResponseInfo encryptResponseInfo = encrypt();
		String responseText = UrlUtil.paramEncoder(encryptResponseInfo.toJSONText());
		// String responseText = encryptResponseInfo.toJSONText();
		if (response == null) {
			logger.warn("response写入失败,内容：" + responseText);
		} else {
			try {
				response.getWriter().print(responseText);
			} catch (IOException e) {
				logger.warn("response写入出现异常", e);
			}
		}
	}

	/**
	 * 对响应结果进行加密
	 */
	public ResponseInfo encrypt() {
		/** 是否对响应结果进行加密 **/
		boolean isEncryptResponse = systemProperties.isEncryptResponse();
		if (isEncryptResponse) {
			PreAdviceWrap preAdviceWrap = RequestContext.getPreAdviceWrap();
			SecretKeyVo secretKeyVo = preAdviceWrap.getSecretKeyVo();
			String decryptKey = secretKeyVo.getDecryptKey();
			if (Strings.isEmpty(decryptKey)) {
				logger.warn("!!! 获取到decryptKey为空，无法对结果集进行加密");
			} else {
				/** 克隆一个对象 ，不影响源数据，方便记录日志 **/
				ResponseInfo responseInfoClone = (ResponseInfo) this.responseInfo.clone();
				Object infos = responseInfoClone.getMsgEx().getInfos();
				if (!Strings.isEmpty(infos)) {
					/** 进行加密处理 **/
					String decryptStr = JSON.toJSONString(infos);
					String encryptStr = XXTeaUtil.xxteaEncrypt(decryptStr, decryptKey);
					responseInfoClone.getMsgEx().setInfos(encryptStr);
				}
				return responseInfoClone;
			}

		} else {
			/** 响应结果不进行加密处理 **/
			logger.warn("响应结果不进行加密处理");
		}
		return this.responseInfo;
	}

	public ResponseInfo getResponseInfo() {
		return responseInfo;
	}

	public long getResponseTime() {
		return responseTime;
	}

}
