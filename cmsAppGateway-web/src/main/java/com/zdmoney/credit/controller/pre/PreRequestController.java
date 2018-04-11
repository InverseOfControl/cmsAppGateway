package com.zdmoney.credit.controller.pre;

import java.io.InputStream;
import java.net.URL;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.zdmoney.credit.common.constant.system.LogLevel;
import com.zdmoney.credit.common.exception.PlatformException;
import com.zdmoney.credit.common.exception.ResponseEnum;
import com.zdmoney.credit.common.util.AesUtil;
import com.zdmoney.credit.common.util.Strings;
import com.zdmoney.credit.common.util.WebUtils;
import com.zdmoney.credit.common.vo.FuncResult;
import com.zdmoney.credit.common.vo.ResponseInfo;
import com.zdmoney.credit.config.SystemProperties;
import com.zdmoney.credit.framework.RequestContext;
import com.zdmoney.credit.framework.controller.BaseController;
import com.zdmoney.credit.framework.funcid.FuncIdInstance;
import com.zdmoney.credit.framework.funcid.FuncIdMethod;
import com.zdmoney.credit.framework.redis.LoginRedisClientUtil;
import com.zdmoney.credit.framework.vo.common.BaseParamVo;
import com.zdmoney.credit.framework.vo.common.RedisUserInfoVo;
import com.zdmoney.credit.framework.vo.request.RequestVo;
import com.zdmoney.credit.framework.wrap.PreAdviceWrap;

/**
 * 外围接口前置口 （所有请求接口统一由此处进行过滤和拦截）
 * 
 * @author Ivan
 *
 */
@Controller
public class PreRequestController extends BaseController {

	@Autowired
	LoginRedisClientUtil loginRedisClientUtil;
	
	@Autowired
	SystemProperties systemProperties;

	/**
	 * 请求前置 过滤和拦截 转发到业务方法
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/preRequest", method = RequestMethod.POST)
	@ResponseBody
	public void preRequest(HttpServletRequest request, HttpServletResponse response) {
		ResponseInfo responseInfo = null;
		/** 从本地线程中获取请求包裹对象 **/
		PreAdviceWrap preAdviceWrap = RequestContext.getPreAdviceWrap();
		RequestVo requestVo = preAdviceWrap.getRequestVo();
		FuncIdInstance funcIdInstance = preAdviceWrap.getFuncIdInstance();
		try {
			/** 获取业务方法实例 **/
			FuncIdMethod funcIdMethod = funcIdInstance.getFuncIdMethod();
			/** 获取业务方法参数实例 **/
			BaseParamVo baseParamVo = funcIdInstance.getBaseParamVo();

			FuncResult funcResult = (FuncResult) funcIdMethod.runMethod(baseParamVo);
			responseInfo = new ResponseInfo(ResponseEnum.SYS_SUCCESS);
			if (funcResult.getSuccess()) {
				responseInfo.getMsgEx().setStatus("0");
				responseInfo.getMsgEx().setInfos(funcResult.getData());

				String funcId = funcIdMethod.getFuncId();
				switch (funcId) {
				case "400001":
				case "400006":
					/** 登陆接口成功后，保存会话信息（载体：Redis） **/
					JSONObject json = (JSONObject) funcResult.getData();
					/** 员工工号 **/
					String userCode = json.getString("userCode");
					/** App端会话Token **/
					String token = json.getString("token");
					if (Strings.isEmpty(userCode) || Strings.isEmpty(token)) {
						throw new PlatformException(ResponseEnum.VALIDATE_PARAM_VALID_ERROR, "缺少工号或Token参数!")
								.applyLogLevel(LogLevel.WARN);
					}
					RedisUserInfoVo redisUserInfoVo = new RedisUserInfoVo();
					redisUserInfoVo.setUserCode(userCode);
					redisUserInfoVo.setToken(token);
					loginRedisClientUtil.saveLoginUserInfo(requestVo, redisUserInfoVo);
					break;
				default:
					break;
				}

			} else {
				responseInfo.setCode("9001");
				responseInfo.getMsgEx().setStatus("-1");
				responseInfo.getMsgEx().setRespDesc(funcResult.getMessage());
				if(funcResult.getData()!=null){
					responseInfo.getMsgEx().setInfos(funcResult.getData());
				}
			}
		} catch (PlatformException ex) {
			ex.printStackTraceExt(logger);
			responseInfo = ex.toResponseInfo();
		} catch (Exception ex) {
			logger.error(ex, ex);
		}
		preAdviceWrap.getResponseWrap().write(responseInfo);
	}
	
	 @RequestMapping(value = "/getImg", method = RequestMethod.GET)
	    public void getImg(HttpServletRequest request, HttpServletResponse response) {
	    	try {
	    		Map<String, String> map = WebUtils.getParamMap();
	    		String picUrl = map.get("picUrl");
	    		picUrl=	picUrl.replace(" ","+");
	    		picUrl = AesUtil.decryptAES(picUrl, systemProperties.getAppKey());//解密后的
	    		logger.info("☆☆☆☆☆☆☆☆☆☆☆☆解密后的图片地址："+picUrl);
	    		String[] subUrl = picUrl.split("\\.");
	    		String picType=subUrl[subUrl.length-1];//图片后缀类型
	    		/** 拼装图片地址 **/
//	    		String newPicUrl = systemProperties.getPicServerUrl()+deUrl;
//	    		logger.info("☆☆☆☆☆☆☆☆☆☆☆☆组装后的图片地址："+newPicUrl);   		
	    		if("pdf".equalsIgnoreCase(picType)){
	    			response.setHeader("Content-Type", "application/pdf");
	    		}else if("png".equalsIgnoreCase(picType)){
	    			response.setHeader("Content-Type", "image/png");
	    		}else if("jpg".equalsIgnoreCase(picType)){
	    			response.setHeader("Content-Type", "image/jpeg");
	    		}else if("bmp".equalsIgnoreCase(picType)){
	    			response.setHeader("Content-Type", "application/x-bmp");
	    		}else if("jpeg".equalsIgnoreCase(picType)){
	    			response.setHeader("Content-Type", "image/jpeg");
	    		}else if("gif".equalsIgnoreCase(picType)){
	    			response.setHeader("Content-Type", "image/gif");
	    		}else{
	    			logger.info("☆☆☆☆☆☆☆☆☆☆☆☆图片后缀不支持");
	    			throw new PlatformException("APP调用网关系统出现异常");
	    		}
	    		InputStream is = new URL(picUrl).openStream();
	    		byte[] buff = IOUtils.toByteArray(is);
	    		response.getOutputStream().write(buff);
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
				throw new PlatformException("APP调用网关系统出现异常");
			}
	    	
	    }
}
