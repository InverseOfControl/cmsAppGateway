package com.zdmoney.credit.framework.wrap;

import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;
import com.zdmoney.credit.common.exception.PlatformException;
import com.zdmoney.credit.common.validator.ValidatorUtil;
import com.zdmoney.credit.common.vo.ResponseInfo;
import com.zdmoney.credit.framework.RequestContext;
import com.zdmoney.credit.framework.vo.common.SecretKeyVo;
import com.zdmoney.credit.framework.vo.request.ParamVo;
import com.zdmoney.credit.system.domain.AccessLog;
import com.zdmoney.credit.system.service.pub.IAccessLogService;

/**
 * 最后释放资源、记录相关日志操作
 * 
 * @author user
 *
 */
@Component
public class DestroyWrap {

	protected static Log logger = LogFactory.getLog(DestroyWrap.class);

	@Autowired
	IAccessLogService accessLogServiceImpl;

	public void destroy() {
		saveAccessLog();
		RequestContext.clear();
	}

	/**
	 * 保存相关日志信息
	 */
	public void saveAccessLog() {
		PreAdviceWrap preAdviceWrap = RequestContext.getPreAdviceWrap();
		ParamVo paramVo = preAdviceWrap.getRequestVo().getParamVo();
		SecretKeyVo secretKeyVo = preAdviceWrap.getSecretKeyVo();

		String arg0 = "";
		JSONObject arg1 = null;
		String arg2 = "";
		String arg3 = "";
		/** 接口出参 **/
		ResponseInfo responseInfo = preAdviceWrap.getResponseWrap().getResponseInfo();
		/** 接口请求时间 **/
		long requestTime = preAdviceWrap.getRequestWrap().getRequestTime();
		/** 接口响应时间 **/
		long responseTime = preAdviceWrap.getResponseWrap().getResponseTime();
		/** 功能号 **/
		String funcId = "";
		/** 接口入参 **/
		String params = "";
		try {
			if (paramVo != null) {
				/** 接口入参 **/
				params = paramVo.getParams();
				funcId = paramVo.getFuncId();
				arg0 = paramVo.getFuncId();
				arg1 = JSONObject.parseObject(paramVo.getParams());
				arg2 = secretKeyVo.getDecryptKey();
				arg3 = paramVo.getSign();
			}

			/** 将请求参数组合 **/
			JSONObject requestInfo = new JSONObject();
			requestInfo.put("arg0", arg0);
			requestInfo.put("arg1", arg1);
			requestInfo.put("arg2", arg2);
			requestInfo.put("arg3", arg3);

			AccessLog accessLog = new AccessLog();
			accessLog.setCaller("1");
			accessLog.setFuncNo(funcId);
			accessLog.setParamIn(JSONObject.toJSONString(requestInfo));
			accessLog.setParamOut(responseInfo.toJSONText());
			accessLog.setUsedTime(responseTime - requestTime);
			accessLog.setReqTime(new Date(requestTime));
			accessLog.setResTime(new Date(responseTime));
			accessLog.setExt1("");
			accessLog.setExt2("");
			accessLog.setExt3("");
			accessLog.setExt4("");

			ValidatorUtil.valid(accessLog);
			/** 将日志写入数据库 **/
			accessLogServiceImpl.insert(accessLog);
			// return;
		} catch (PlatformException ex) {
			logger.warn("写入日志表出错：" + JSONObject.toJSONString(ex.toResponseInfo()));
		} catch (Exception ex) {
			logger.warn("写入日志表系统忙", ex);
		}
		logger.info("功能号:" + funcId);
		logger.info("接口入参:" + params);
		logger.info("接口出参:" + JSONObject.toJSONString(responseInfo));
		logger.info("业务参数解密Key:" + preAdviceWrap.getSecretKeyVo().getDecryptKey());
		logger.info("耗时:" + (responseTime - requestTime));
	}
}
