package com.zdmoney.credit.framework.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONObject;
import com.zdmoney.credit.common.exception.PlatformException;
import com.zdmoney.credit.common.exception.ResponseEnum;
import com.zdmoney.credit.common.json.JSONUtil;
import com.zdmoney.credit.common.util.Strings;
import com.zdmoney.credit.common.util.UrlUtil;
import com.zdmoney.credit.common.validator.ValidatorUtil;
import com.zdmoney.credit.common.vo.ResponseInfo;
import com.zdmoney.credit.framework.RequestContext;
import com.zdmoney.credit.framework.request.RequestWrap;
import com.zdmoney.credit.framework.response.ResponseWrap;
import com.zdmoney.credit.framework.vo.request.HeaderVo;
import com.zdmoney.credit.framework.vo.request.ParamVo;
import com.zdmoney.credit.framework.vo.request.RequestVo;
import com.zdmoney.credit.framework.wrap.DestroyWrap;

/**
 * 收集相关请求参数，做基础校验拦截
 * 
 * @author Ivan
 *
 */
public class PreInterceptor implements HandlerInterceptor {

	protected static Log logger = LogFactory.getLog(PreInterceptor.class);

	/** 功能号 Request的Key标识 **/
	private static final String P_FUNC_ID_NAME = "arg0";
	/** 业务参数 Request的Key标识 **/
	private static final String P_PARAMS_NAME = "arg1";
	/** 双方约定Key Request的Key标识 **/
	private static final String P_KEY_NAME = "arg2";
	/** 签名信息 Request的Key标识 **/
	private static final String P_SIGN_NAME = "arg3";

	/** 会话令牌 RequestHeader的Key标识 **/
	private static final String H_SESSIONTOKEN_NAME = "sessionToken";
	/** 手机端应用版本号 RequestHeader的Key标识 **/
	private static final String H_VERSION_NAME = "version";
	/** IOS提交 RequestHeader的Key标识 **/
	private static final String H_TOKEN_NAME = "token";
	/** 系统的名称 RequestHeader的Key标识 **/
	private static final String H_USERAGENT_NAME = "userAgent";
	/** 机构 RequestHeader的Key标识 **/
	private static final String H_MECHANISM_NAME = "mechanism";
	/** 平台 RequestHeader的Key标识 **/
	private static final String H_PLATFORM_NAME = "platform";
	/** 合作类型 RequestHeader的Key标识 **/
	private static final String H_TOGATHERTYPE_NAME = "togatherType";
	/** 渠道 RequestHeader的Key标识 **/
	private static final String H_OPENCHANNEL_NAME = "openchannel";

	/** 请求头部参数Key **/
	private static final String H_REQHEAD_PART = "reqHeadParam";
	/** 请求业务参数Key **/
	private static final String H_REQPARAM_PART = "reqParam";
	/** 请求项目编号参数Key **/
	private static final String H_PROJECTNO_PART = "projectNo";

	/** 设备ID参数Key **/
	private static final String H_DEVICEID_PART = "deviceId";
	
	/** 操作人姓名 **/
	private static final String H_OPERATOR_NAME = "operatorName";
	/** 操作人工号 **/
	private static final String H_OPERATOR_CODE = "operatorCode";

	@Autowired
	DestroyWrap destroyWrap;

	/**
	 * 获取请求参数信息
	 * 
	 * @param request
	 * @return
	 */
	private ParamVo buildAllParamVo(HttpServletRequest request) {
		ParamVo paramVo = new ParamVo();
		HeaderVo headerVo = new HeaderVo();
		RequestVo requestVo = RequestContext.getPreAdviceWrap().getRequestVo();
		/** 功能号 **/
		String funcId = UrlUtil.paramDecoder(Strings.convertValue(request.getParameter(P_FUNC_ID_NAME), String.class));
		/** 业务参数 **/
		String params = UrlUtil.paramDecoder(Strings.convertValue(request.getParameter(P_PARAMS_NAME), String.class));
		/** 双方约定Key **/
		String key = UrlUtil.paramDecoder(Strings.convertValue(request.getParameter(P_KEY_NAME), String.class));
		/** 签名信息 **/
		String sign = UrlUtil.paramDecoder(Strings.convertValue(request.getParameter(P_SIGN_NAME), String.class));
		
		/**操作人姓名*/
		String operatorName = "";
		/**操作人工号*/
		String operatorCode = "";
		try {
			if (!Strings.isEmpty(params)) {
				JSONObject jsonObj = JSONUtil.checkJSONObject(params);
				if (jsonObj.containsKey(H_REQHEAD_PART)) {
					/** 获取头部信息 **/
					JSONObject headerJson = jsonObj.getJSONObject(H_REQHEAD_PART);

					String sessionToken = UrlUtil.paramDecoder(Strings.convertValue(
							headerJson.getString(H_SESSIONTOKEN_NAME), String.class));
					String version = UrlUtil.paramDecoder(Strings.convertValue(headerJson.getString(H_VERSION_NAME),
							String.class));
					String token = UrlUtil.paramDecoder(Strings.convertValue(headerJson.getString(H_TOKEN_NAME),
							String.class));
					String userAgent = UrlUtil.paramDecoder(Strings.convertValue(
							headerJson.getString(H_USERAGENT_NAME), String.class));
					String mechanism = UrlUtil.paramDecoder(Strings.convertValue(
							headerJson.getString(H_MECHANISM_NAME), String.class));
					String platform = UrlUtil.paramDecoder(Strings.convertValue(headerJson.getString(H_PLATFORM_NAME),
							String.class));
					String togatherType = UrlUtil.paramDecoder(Strings.convertValue(
							headerJson.getString(H_TOGATHERTYPE_NAME), String.class));
					String openchannel = UrlUtil.paramDecoder(Strings.convertValue(
							headerJson.getString(H_OPENCHANNEL_NAME), String.class));
					
					operatorName = UrlUtil.paramDecoder(Strings.convertValue(jsonObj.getString(H_OPERATOR_NAME), String.class));
					operatorCode = UrlUtil.paramDecoder(Strings.convertValue(jsonObj.getString(H_OPERATOR_CODE), String.class));
					
					headerVo.setSessionToken(sessionToken);
					headerVo.setVersion(version);
					headerVo.setToken(token);
					headerVo.setUserAgent(userAgent);
					headerVo.setMechanism(mechanism);
					headerVo.setPlatform(platform);
					headerVo.setTogatherType(togatherType);
					headerVo.setOpenchannel(openchannel);
				}
				if (jsonObj.containsKey(H_REQPARAM_PART)) {
					/** 获取业务参数信息 **/
					JSONObject reqParamJson = jsonObj.getJSONObject(H_REQPARAM_PART);
					
					reqParamJson.put("operatorName", operatorName);
					reqParamJson.put("operatorCode", operatorCode);
					
					paramVo.setReqParam(reqParamJson.toJSONString());
				}
				requestVo.setProjectNo(Strings.parseString(jsonObj.getString(H_PROJECTNO_PART)));
				requestVo.setDeviceId(Strings.parseString(jsonObj.getString(H_DEVICEID_PART)));
			}
		} finally {
			paramVo.setFuncId(funcId);
			paramVo.setKey(key);
			paramVo.setSign(sign);
			paramVo.setParams(params);
			requestVo.setHeaderVo(headerVo);
			requestVo.setParamVo(paramVo);
		}
		return paramVo;
	}

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		// request.setCharacterEncoding("UTF-8");
		// response.setCharacterEncoding("UTF-8");
		/** 头部信息 **/
		// HeaderVo headerVo = buildHeaderVo(request);
		ResponseInfo responseInfo = null;
		try {
			/** 将请求所有信息丢到本地线程中 **/
			RequestContext.getPreAdviceWrap().setRequestWrap(new RequestWrap(request));
			RequestContext.getPreAdviceWrap().setResponseWrap(new ResponseWrap(response));
			/** 参数信息 **/
			ParamVo paramVo = buildAllParamVo(request);

			RequestVo requestVo = RequestContext.getPreAdviceWrap().getRequestVo();

			RequestContext.getPreAdviceWrap().getSecretKeyVo().setEncryptKey(paramVo.getKey());
			RequestContext.getPreAdviceWrap().getSecretKeyVo().setSign(paramVo.getSign());

			/** 校验参数格式 **/
			// ValidatorUtil.valid(headerVo);
			ValidatorUtil.valid(requestVo);
			ValidatorUtil.valid(paramVo);
			return true;
		} catch (PlatformException ex) {
			ex.printStackTraceExt(logger);
			responseInfo = ex.toResponseInfo();
		} catch (Exception ex) {
			logger.error(ex, ex);
			responseInfo = new ResponseInfo(ResponseEnum.SYS_FAILD);
		}
		RequestContext.getPreAdviceWrap().getResponseWrap().write(responseInfo);
		try {

		} finally {
			/** 释放资源、记录相关日志操作 **/
			destroyWrap.destroy();
		}

		return false;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {

	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		try {

		} finally {
			/** 释放资源、记录相关日志操作 **/
			destroyWrap.destroy();
		}
	}

}
