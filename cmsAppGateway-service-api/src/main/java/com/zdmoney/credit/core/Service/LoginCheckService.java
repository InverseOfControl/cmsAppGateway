package com.zdmoney.credit.core.Service;

import java.net.URLDecoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.alibaba.fastjson.JSONObject;
import com.ymkj.base.core.biz.api.message.Response;
import com.ymkj.cms.biz.api.service.app.IAPPExecuter;
import com.ymkj.cms.biz.api.vo.request.app.Req_VO_600002;
import com.zdmoney.credit.api.framework.service.BusinessService;
import com.zdmoney.credit.applyinfo.domain.TmApplyMainInfo;
import com.zdmoney.credit.applyinfo.service.pub.ITmApplyMainInfoService;
import com.zdmoney.credit.callinter.HttpUrlConnection;
import com.zdmoney.credit.common.annotate.FuncIdAnnotate;
import com.zdmoney.credit.common.vo.FuncResult;
import com.zdmoney.credit.config.ApsAppProperties;
import com.zdmoney.credit.config.CoreProperties;
import com.zdmoney.credit.framework.vo.Vo_400001;
import com.zdmoney.credit.framework.vo.Vo_400006;
import com.zdmoney.credit.system.service.pub.ISysParamDefineService;

/**
 * 登录校验
 * 
 * @author 00235528
 *
 */
@Service
public class LoginCheckService extends BusinessService {

	protected static Log logger = LogFactory.getLog(LoginCheckService.class);

	@Autowired
	RestTemplate restTemplate;

	@Autowired
	CoreProperties coreProperties;

	@Autowired
	private ISysParamDefineService sysParamDefineService;
	
	@Autowired
	private ITmApplyMainInfoService applyMainInfoService;

	@Autowired
	HttpUrlConnection httpUrlConnection;

	@Autowired
	ApsAppProperties apsAppProperties;
	
	@Autowired
	IAPPExecuter iAPPExecuter;
	

	/**
	 * 调用核心登录校验接口
	 * 
	 * @param vo_400001
	 * @return
	 */
	@FuncIdAnnotate(value = "400001", desc = "登录接口校验（征信查询App端）", voCls = Vo_400001.class, isDependLogin = false)
	public FuncResult loginCheck(Vo_400001 vo_400001) throws Exception {

		/** 工号 **/
		String userCode = vo_400001.getUserCode();
		/** 密码 **/
		String passWord = vo_400001.getPassWord();
		/** 调用接口返回数据 **/
		String result = null;
		JSONObject param = new JSONObject();
		/** 查询调用核心登录校验接口url **/
		String requestUrl = coreProperties.getServiceUrl() + "/core/loginCheckCore/loginCheck";
		param.put("userCode", userCode);
		param.put("passWord", passWord);
		result = httpUrlConnection.postForEntity(requestUrl, param, String.class);

		JSONObject obj = JSONObject.parseObject(result);
		String resCode = "";
		FuncResult funcResult = null;
		if (obj.containsKey("resCode")) {
			resCode = obj.getString("resCode");
			if ("000000".equals(resCode)) {
				/** 获取登陆用户信息 **/
				JSONObject loginVo = obj.getJSONObject("loginCheckVo");
				if (loginVo == null) {
					loginVo = new JSONObject();
				}
				/** App前端会话Token **/
				loginVo.put("token", UUID.randomUUID().toString());
				funcResult = FuncResult.success("登陆成功", loginVo);
			} else {
				funcResult = FuncResult.fail(obj.getString("resMsg"));
			}
		} else {
			funcResult = FuncResult.fail("三方返回缺少【resCode】Key");
		}
		return funcResult;
	}

	/**
	 * 调用核心登录校验接口
	 * 
	 * @param vo_400001
	 * @return
	 */
	@FuncIdAnnotate(value = "400006", desc = "登录接口校验（录单App端）", voCls = Vo_400006.class, isDependLogin = false)
	public FuncResult loginCheckByLoan(Vo_400006 vo_400006) throws Exception {

		/** 工号 **/
		String userCode = vo_400006.getUserCode();
		/** 密码 **/
		String passWord = vo_400006.getPassWord();
		
		/** 调用接口返回数据 **/
		String result = null;
		JSONObject param = new JSONObject();
		/** 查询调用核心登录校验接口url **/
		String requestUrl = coreProperties.getServiceUrl() + "/core/loginCheckCore/loginCheckByLoan";
		param.put("userCode", userCode);
		param.put("passWord", passWord);
		param.put("newOrOld", "n");
		
		logger.info("请求参数："+param.toString());
		logger.info("请求地址："+requestUrl);
		
		result = httpUrlConnection.postForEntity(requestUrl, param, String.class);

		JSONObject obj = JSONObject.parseObject(result);
		String resCode = "";
		FuncResult funcResult = null;
		if (obj.containsKey("resCode")) {
			resCode = obj.getString("resCode");
			if ("000000".equals(resCode)) {
				/** 获取登陆用户信息 **/
				JSONObject loginVo = obj.getJSONObject("loginCheckVo");
				if (loginVo == null) {
					loginVo = new JSONObject();
				}

				String isFirst = loginVo.containsKey("isFirst")?loginVo.getString("isFirst"):"";
				if("f".equals(isFirst)){
					Map<String,Object> paramMap = new HashMap<String,Object>();
					paramMap.put("userId", userCode);
					List<TmApplyMainInfo> tamiList = applyMainInfoService.queryAPP2(paramMap);
					if(tamiList!=null && tamiList.size()>0){
						loginVo.put("submitRemind", "您有"+tamiList.size()+"笔申请距最后可提交日期小于3个工作日，请尽快提交!");
					}else{
						loginVo.put("submitRemind", "");
					}
				}else{
					loginVo.put("submitRemind", "");
				}
				
				/** 产品信息 **/
				/*JSONObject paramP = new JSONObject();
				String requestUrlP = apsAppProperties.getServiceUrl() + "/App/rpc/apsApp/getProductListData";
				paramP.put("userCode", userCode);

				String resultP = httpUrlConnection.postForEntity(requestUrlP, paramP, String.class);

				resultP = URLDecoder.decode(resultP, "UTF-8");

				JSONObject productJsonObj = JSONObject.parseObject(resultP);*/
				
				Req_VO_600002 req_VO_600002 = new Req_VO_600002();
				req_VO_600002.setSysCode("app");
				req_VO_600002.setUserCode(userCode);
				
				Response<Object> response = iAPPExecuter.getProductListData(req_VO_600002);
				if(!"000000".equals(response.getRepCode())){
					return FuncResult.fail("请配置登录员工营业部下的网点产品");
				}
				JSONObject productJsonObj = JSONObject.parseObject(JSONObject.toJSONString(response.getData()));	
				loginVo.putAll(productJsonObj);
				/** App前端会话Token **/
				loginVo.put("token", UUID.randomUUID().toString());
				funcResult = FuncResult.success("登陆成功", loginVo);

			} else {
				funcResult = FuncResult.fail(obj.getString("resMsg"));
			}
		} else {
			funcResult = FuncResult.fail("三方返回缺少【resCode】Key");
		}
		return funcResult;
	}
	public static void main(String[] args) {
		JSONObject productJsonObj = JSONObject.parseObject(JSONObject.toJSONString(null));
		JSONObject loginVo = new JSONObject();
		loginVo.putAll(null);
	}
}
