package com.zdmoney.credit.core.Service;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.alibaba.fastjson.JSONObject;
import com.zdmoney.credit.api.framework.service.BusinessService;
import com.zdmoney.credit.callinter.HttpUrlConnection;
import com.zdmoney.credit.common.annotate.FuncIdAnnotate;
import com.zdmoney.credit.common.util.BeanUtils;
import com.zdmoney.credit.common.vo.FuncResult;
import com.zdmoney.credit.config.CoreProperties;
import com.zdmoney.credit.framework.vo.Vo_400002;
import com.zdmoney.credit.framework.vo.Vo_400003;
import com.zdmoney.credit.framework.vo.Vo_400004;
import com.zdmoney.credit.system.service.pub.ISysParamDefineService;

/**
 * 密码维护
 * 
 * @author Ivan
 *
 */
@Service
public class PassWordService extends BusinessService {

	protected static Log logger = LogFactory.getLog(PassWordService.class);

	@Autowired
	RestTemplate restTemplate;

	@Autowired
	CoreProperties coreProperties;

	@Autowired
	private ISysParamDefineService sysParamDefineService;

	@Autowired
	HttpUrlConnection httpUrlConnection;

	/**
	 * 发送手机验证码接口
	 * 
	 * @param vo_400002
	 * @return
	 */
	@FuncIdAnnotate(value = "400002", desc = "发送手机验证码", voCls = Vo_400002.class, isDependLogin = false)
	public FuncResult sendMobileCode(Vo_400002 vo_400002) {
		/** 调用接口返回数据 **/
		String result = null;
		JSONObject param = new JSONObject(BeanUtils.toMap(vo_400002));
		/** 查询调用核心登录校验接口url **/
		String requestUrl = coreProperties.getServiceUrl() + "/app/passWord/sendMobileCode";
		result = httpUrlConnection.postForEntity(requestUrl, param, String.class);

		JSONObject obj = JSONObject.parseObject(result);
		String resCode = "";
		FuncResult funcResult = null;
		if (obj.containsKey("resCode")) {
			resCode = obj.getString("resCode");
			if ("000000".equals(resCode)) {
				funcResult = FuncResult.success("正常", obj.getJSONObject("attachment"));
			} else {
				funcResult = FuncResult.fail(obj.getString("resMsg"));
			}
		} else {
			funcResult = FuncResult.fail("三方返回缺少【resCode】Key");
		}
		return funcResult;
	}

	/**
	 * 校验手机验证码接口
	 * 
	 * @param vo_400003
	 * @return
	 */
	@FuncIdAnnotate(value = "400003", desc = "校验手机验证码", voCls = Vo_400003.class, isDependLogin = false)
	public FuncResult validMobileCode(Vo_400003 vo_400003) {
		/** 调用接口返回数据 **/
		String result = null;
		JSONObject param = new JSONObject(BeanUtils.toMap(vo_400003));
		/** 查询调用核心登录校验接口url **/
		String requestUrl = coreProperties.getServiceUrl() + "/app/passWord/validMobileCode";
		result = httpUrlConnection.postForEntity(requestUrl, param, String.class);

		JSONObject obj = JSONObject.parseObject(result);
		String resCode = "";
		FuncResult funcResult = null;
		if (obj.containsKey("resCode")) {
			resCode = obj.getString("resCode");
			if ("000000".equals(resCode)) {
				funcResult = FuncResult.success();
			} else {
				funcResult = FuncResult.fail(obj.getString("resMsg"));
			}
		} else {
			funcResult = FuncResult.fail("三方返回缺少【resCode】Key");
		}
		return funcResult;
	}

	/**
	 * 修改登陆密码接口
	 * 
	 * @param vo_400004
	 * @return
	 */
	@FuncIdAnnotate(value = "400004", desc = "修改登陆密码", voCls = Vo_400004.class, isDependLogin = false)
	public FuncResult modifyLoginPassword(Vo_400004 vo_400004) {
		/** 调用接口返回数据 **/
		String result = null;
		JSONObject param = new JSONObject(BeanUtils.toMap(vo_400004));
		/** 查询调用核心登录校验接口url **/
		String requestUrl = coreProperties.getServiceUrl() + "/app/passWord/modifyLoginPassword";
		result = httpUrlConnection.postForEntity(requestUrl, param, String.class);

		JSONObject obj = JSONObject.parseObject(result);
		String resCode = "";
		FuncResult funcResult = null;
		if (obj.containsKey("resCode")) {
			resCode = obj.getString("resCode");
			if ("000000".equals(resCode)) {
				funcResult = FuncResult.success();
			} else {
				funcResult = FuncResult.fail(obj.getString("resMsg"));
			}
		} else {
			funcResult = FuncResult.fail("三方返回缺少【resCode】Key");
		}
		return funcResult;
	}
}
