package com.zdmoney.credit.framework.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.zdmoney.credit.common.annotate.FuncIdAnnotate;
import com.zdmoney.credit.common.constant.system.LogLevel;
import com.zdmoney.credit.common.exception.PlatformException;
import com.zdmoney.credit.common.exception.ResponseEnum;
import com.zdmoney.credit.common.util.Strings;
import com.zdmoney.credit.common.vo.ResponseInfo;
import com.zdmoney.credit.config.SystemProperties;
import com.zdmoney.credit.framework.RequestContext;
import com.zdmoney.credit.framework.funcid.FuncIdMethod;
import com.zdmoney.credit.framework.funcid.FuncIdUtil;
import com.zdmoney.credit.framework.redis.LoginRedisClientUtil;
import com.zdmoney.credit.framework.vo.request.HeaderVo;
import com.zdmoney.credit.framework.vo.request.ParamVo;
import com.zdmoney.credit.framework.vo.request.RequestVo;
import com.zdmoney.credit.framework.wrap.PreAdviceWrap;

/**
 * App会话状态校验
 * 
 * @author Ivan
 *
 */
public class LoginCheckInterceptor implements HandlerInterceptor {

	protected static Log logger = LogFactory.getLog(LoginCheckInterceptor.class);

	@Autowired
	SystemProperties systemProperties;

	@Autowired
	LoginRedisClientUtil loginRedisClientUtil;

	public LoginCheckInterceptor() {

	}

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		
		PreAdviceWrap preAdviceWrap = RequestContext.getPreAdviceWrap();
		RequestVo requestVo = preAdviceWrap.getRequestVo();
		ParamVo paramVo = requestVo.getParamVo();
		HeaderVo headerVo = requestVo.getHeaderVo();
		ResponseInfo responseInfo = null;
		String deviceId = requestVo.getDeviceId();
		if (Strings.isEmpty(deviceId)) {
			responseInfo = new ResponseInfo(ResponseEnum.FUNC_ID_CALL_ERROR, "终端设备ID参数不能为空！");
			preAdviceWrap.getResponseWrap().write(responseInfo);
			return false;
		}
		
		if (!systemProperties.isSingleLogin()) {
			logger.warn("系统关闭单点登陆校验开关");
			return true;
		}
		
		try {
			/** 功能号 **/
			String funcId = paramVo.getFuncId();
			/** 获取请求功能号的实例 **/
			FuncIdMethod funcIdMethod = FuncIdUtil.checkFuncIdExists(funcId);

			/** 方法注解实例 **/
			FuncIdAnnotate funcIdAnnotate = funcIdMethod.getFuncIdAnnotate();
			if (funcIdAnnotate.isDependLogin()) {
				/** 功能号依赖登陆 校验是否已登陆 **/

				/** 会话Token **/
				String sessionToken = headerVo.getSessionToken();
				if (Strings.isEmpty(sessionToken)) {
					throw new PlatformException(ResponseEnum.SESSION_EXPIRE, "无Token!").applyLogLevel(LogLevel.WARN);
				}
				/** 校验Token是否有效 **/
				loginRedisClientUtil.checkTokenInfo(requestVo, headerVo);
			} else {
				/** 功能号不依赖登陆 **/
			}
			return true;
		} catch (PlatformException ex) {
			ex.printStackTraceExt(logger);
			responseInfo = ex.toResponseInfo();
		} catch (Exception ex) {
			logger.error(ex, ex);
			responseInfo = new ResponseInfo(ResponseEnum.DECRYPT_FAIL, "系统忙！详细信息见后台日志！");
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
