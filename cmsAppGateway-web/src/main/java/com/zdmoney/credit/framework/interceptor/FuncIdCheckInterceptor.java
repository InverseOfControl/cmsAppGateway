package com.zdmoney.credit.framework.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.zdmoney.credit.common.constant.system.LogLevel;
import com.zdmoney.credit.common.exception.PlatformException;
import com.zdmoney.credit.common.exception.ResponseEnum;
import com.zdmoney.credit.common.json.JSONUtil;
import com.zdmoney.credit.common.vo.ResponseInfo;
import com.zdmoney.credit.framework.RequestContext;
import com.zdmoney.credit.framework.funcid.FuncIdInstance;
import com.zdmoney.credit.framework.funcid.FuncIdMethod;
import com.zdmoney.credit.framework.funcid.FuncIdUtil;
import com.zdmoney.credit.framework.vo.common.BaseParamVo;
import com.zdmoney.credit.framework.vo.request.ParamVo;
import com.zdmoney.credit.framework.vo.request.RequestVo;
import com.zdmoney.credit.framework.wrap.PreAdviceWrap;

/**
 * 校验功能号是否存在或不可用
 * 
 * @author Ivan
 *
 */
public class FuncIdCheckInterceptor implements HandlerInterceptor {

	protected static Log logger = LogFactory.getLog(FuncIdCheckInterceptor.class);

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		PreAdviceWrap preAdviceWrap = RequestContext.getPreAdviceWrap();
		RequestVo requestVo = RequestContext.getPreAdviceWrap().getRequestVo();
		ParamVo paramVo = requestVo.getParamVo();
		ResponseInfo responseInfo = null;

		try {
			/** 功能号 **/
			String funcId = paramVo.getFuncId();

			/** 获取功能号业务参数 **/
			String reqParams = paramVo.getReqParam();
			JSONUtil.checkJSONObject(reqParams);
			/** 解析之后的业务参数 **/
//			String parseParams = "";
//			if (jsonObj.size() > 0) {
//				/** 确保存在业务参数 **/
//				if (jsonObj.containsKey(P_REQPARAM_NAME)) {
//					/** 获取reqParam对应的内容 **/
//					parseParams = jsonObj.getJSONObject(P_REQPARAM_NAME).toJSONString();
//				} else {
//					throw new PlatformException(ResponseEnum.VALIDATE_JSON_FORMAT_ERROR, "arg1缺少" + P_REQPARAM_NAME
//							+ "数据").applyLogLevel(LogLevel.WARN);
//				}
//			} else {
//				parseParams = JSON.toJSONString(jsonObj);
//			}

			/** 判断功能号是否存在 校验业务Vo数据格式 **/
			FuncIdMethod funcIdMethod = FuncIdUtil.checkFuncIdExists(funcId);
			BaseParamVo baseParamVo = FuncIdUtil.checkFuncIdParams(funcId, reqParams);

			/** 将功能号对应的方法实例和业务参数实例放到本地线程中 **/
			FuncIdInstance funcIdInstance = new FuncIdInstance();
			funcIdInstance.setFuncIdMethod(funcIdMethod);
			funcIdInstance.setBaseParamVo(baseParamVo);
			preAdviceWrap.setFuncIdInstance(funcIdInstance);
			return true;
		} catch (PlatformException ex) {
			ex.printStackTraceExt(logger);
			responseInfo = ex.toResponseInfo();
		} catch (Exception ex) {
			logger.error(ex, ex);
			responseInfo = new ResponseInfo(ResponseEnum.SYS_FAILD, "系统忙");
		}
		preAdviceWrap.getResponseWrap().write(responseInfo);
		return false;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {

	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {

	}

}
