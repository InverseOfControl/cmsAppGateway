package com.zdmoney.credit.api.framework.service;

import java.io.Serializable;
import java.math.BigDecimal;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.ObjectUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.alibaba.dubbo.common.utils.CollectionUtils;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ymkj.base.core.biz.api.message.PageResponse;
import com.ymkj.base.core.biz.api.message.Response;
import com.ymkj.base.core.common.excption.CoreErrorCode;
import com.ymkj.base.core.common.http.HttpResponse;
import com.ymkj.cms.biz.api.service.app.IAPPExecuter;
import com.ymkj.cms.biz.api.service.apply.IApplyValidateExecuter;
import com.ymkj.cms.biz.api.vo.request.app.Req_VO_600001;
import com.ymkj.cms.biz.api.vo.request.app.Req_VO_600002;
import com.ymkj.cms.biz.api.vo.request.app.Req_VO_600003;
import com.ymkj.cms.biz.api.vo.request.app.Req_VO_600004;
import com.ymkj.cms.biz.api.vo.request.app.Req_VO_600005;
import com.ymkj.cms.biz.api.vo.request.app.Req_VO_600006;
import com.ymkj.cms.biz.api.vo.request.app.Req_VO_600007;
import com.ymkj.cms.biz.api.vo.request.app.Req_VO_600008;
import com.ymkj.cms.biz.api.vo.request.app.Req_VO_600013;
import com.ymkj.cms.biz.api.vo.request.apply.ReqCreditCheckVO;
import com.ymkj.cms.biz.api.vo.request.apply.ValidateNameIdNoVO;
import com.ymkj.cms.biz.api.vo.response.app.Res_VO_600006;
import com.ymkj.cms.biz.api.vo.response.app.Res_VO_600013;
import com.ymkj.cms.biz.api.vo.response.apply.ResTrialBeforeCreditVO;

import com.zdmoney.credit.applyinfo.domain.TmApplyMainInfo;
import com.zdmoney.credit.applyinfo.service.pub.ITmApplyFieldInfoService;
import com.zdmoney.credit.applyinfo.service.pub.ITmApplyMainInfoService;
import com.zdmoney.credit.callinter.HttpUrlConnection;
import com.zdmoney.credit.common.APPEnumConstants;
import com.zdmoney.credit.common.annotate.FuncIdAnnotate;
import com.zdmoney.credit.common.constant.system.Constant;
import com.zdmoney.credit.common.exception.PlatformException;
import com.zdmoney.credit.common.exception.ResponseEnum;
import com.zdmoney.credit.common.util.Dates;
import com.zdmoney.credit.common.util.Strings;
import com.zdmoney.credit.common.vo.FuncResult;
import com.zdmoney.credit.config.ApsAppProperties;
import com.zdmoney.credit.config.CoreProperties;
import com.zdmoney.credit.config.CreditZxProperties;
import com.zdmoney.credit.config.SystemProperties;
import com.zdmoney.credit.framework.vo.Vo_600001;
import com.zdmoney.credit.framework.vo.Vo_600002;
import com.zdmoney.credit.framework.vo.Vo_600003;
import com.zdmoney.credit.framework.vo.Vo_600004;
import com.zdmoney.credit.framework.vo.Vo_600005;
import com.zdmoney.credit.framework.vo.Vo_600006;
import com.zdmoney.credit.framework.vo.Vo_600007;
import com.zdmoney.credit.framework.vo.Vo_600008;
import com.zdmoney.credit.framework.vo.Vo_600009;
import com.zdmoney.credit.framework.vo.Vo_600012;
import com.zdmoney.credit.framework.vo.Vo_600013;
import com.zdmoney.credit.framework.vo.Vo_600014;
import com.zdmoney.credit.framework.vo.Vo_700006;
import com.zdmoney.credit.framework.vo.common.BaseLoanStatusVo;

@Service
public class ApsAppService extends BusinessService{

	@Autowired
	ApsAppProperties apsAppProperties;

	@Autowired
	SystemProperties systemProperties;

	@Autowired
	CreditZxProperties creditZxProperties;

	@Autowired
	RestTemplate restTemplate;

	@Autowired
	private ITmApplyMainInfoService applyMainInfoService;

	@Autowired
	private ITmApplyFieldInfoService applyFieldInfoService;

	@Autowired
	private IAPPExecuter IAPPExecuter;

	@Autowired
	private CoreProperties coreProperties;

	@Autowired
	private HttpUrlConnection httpUrlConnection;

	@Autowired
	private IApplyValidateExecuter applyValidateExecuter;

	protected static Log logger = LogFactory.getLog(ApsAppService.class);

	@FuncIdAnnotate(value = "600001", desc = "展业APP获取页面控件", voCls = Vo_600001.class,isDependLogin = false)
	public FuncResult getShowComponents(Vo_600001 vo_600001) throws Exception {

		Req_VO_600001 req_VO_600001 = new Req_VO_600001();
		req_VO_600001.setSysCode("app");
		req_VO_600001.setAppCurrentTime(vo_600001.getAppCurrentTime());

		Response<Object> response = IAPPExecuter.initField(req_VO_600001);

		if(response.getRepCode().equals("000000")){
			return FuncResult.success("展业APP获取页面控件成功", response.getData());
		}else{
			throw new PlatformException("展业APP获取页面控件失败！错误编码："+response.getRepCode());
		}
	}

	@FuncIdAnnotate(value = "600002", desc = "产品信息初始化接口", voCls = Vo_600002.class,isDependLogin = false)
	public FuncResult getProductListData(Vo_600002 vo_600002) throws Exception {

		Req_VO_600002 req_VO_600002 = new Req_VO_600002();
		req_VO_600002.setSysCode("app");
		req_VO_600002.setUserCode(vo_600002.getUserCode());

		Response<Object> response = IAPPExecuter.getProductListData(req_VO_600002);

		if(response.getRepCode().equals("000000")){
			return FuncResult.success("产品信息初始化接口调用成功", response.getData());
		}else{
			throw new PlatformException("产品信息初始化接口调用失败！错误编码："+response.getRepCode());
		}

	}

	@FuncIdAnnotate(value = "600003", desc = "提交保存录入接口", voCls = Vo_600003.class)
	public FuncResult saveApplyInput(Vo_600003 vo_600003) throws Exception {

		HashMap<String,Object> applyInputMap = new HashMap<String,Object>();
		Req_VO_600003 req_VO_600003 = new Req_VO_600003();

		String userCode = vo_600003.getUserCode();
		Date applyDate = vo_600003.getApplyDate();

		applyInputMap.put("applyInfo", vo_600003.getApplyInfo()); //申请信息
		applyInputMap.put("persionInfo", vo_600003.getPersionInfo()); //个人信息

		ArrayList<HashMap<String,Serializable>> empItemInfo = vo_600003.getEmpItemInfo();//工作信息

		if(CollectionUtils.isNotEmpty(empItemInfo)){
			for(HashMap<String,Serializable> eInfoMap : empItemInfo){
				String sectionlKey = (String)eInfoMap.get("sectionKey");
				if(sectionlKey!=null && "empItemInfo".equals(sectionlKey)){
					applyInputMap.put("empItemInfo", eInfoMap); //工作信息
				}
				if(sectionlKey!=null && "baseItemInfo".equals(sectionlKey)){
					applyInputMap.put("baseItemInfo", eInfoMap); //私营业主信息
				}
			}
		}

		ArrayList<HashMap<String,Serializable>> contactPersonInfo = vo_600003.getContactPersonInfo();//联系人信息列表
		applyInputMap.put("contactPersonInfo", contactPersonInfo);

		ArrayList<HashMap<String,Serializable>> assetsInfo = vo_600003.getAssetsInfo();//资产信息

		if(CollectionUtils.isNotEmpty(assetsInfo)){
			for(HashMap<String,Serializable> aInfoMap : assetsInfo){
				String sectionlKey = (String)aInfoMap.get("sectionKey");
				if(sectionlKey!=null && "salaryLoanInfo".equals(sectionlKey)){
					applyInputMap.put("salaryLoanInfo", aInfoMap); //随薪贷信息
				}
				if(sectionlKey!=null && "assetsInfo".equals(sectionlKey)){
					applyInputMap.put("estateInfo", aInfoMap);  //房产信息
				}
				if(sectionlKey!=null && "carInfo".equals(sectionlKey)){
					applyInputMap.put("carInfo", aInfoMap);  //车辆信息
				}
				if(sectionlKey!=null && "policyInfo".equals(sectionlKey)){
					applyInputMap.put("policyInfo", aInfoMap);  //保单信息
				}
				if(sectionlKey!=null && "providentInfo".equals(sectionlKey)){
					applyInputMap.put("providentInfo", aInfoMap);  //公积金信息
				}
				if(sectionlKey!=null && "cardLoanInfo".equals(sectionlKey)){
					applyInputMap.put("cardLoanInfo", aInfoMap);  //卡友贷信息
				}
				if(sectionlKey!=null && "masterLoanBInfo".equals(sectionlKey)){
					applyInputMap.put("masterLoanBInfo", aInfoMap);  //网购达人贷B信息
				}
				if(sectionlKey!=null && "masterLoanInfo".equals(sectionlKey)){
					applyInputMap.put("masterLoanInfo", aInfoMap);  //网购达人贷信息
				}
				if(sectionlKey!=null && "merchantLoanInfo".equals(sectionlKey)){
					applyInputMap.put("merchantLoanInfo", aInfoMap);  //淘宝商户贷信息
				}
			}
		}

		req_VO_600003.setSysCode("app");
		req_VO_600003.setUserCode(vo_600003.getUserCode());
		req_VO_600003.setIsZXBH("Y");
		req_VO_600003.setSysCode("app");
		req_VO_600003.setApplyDate(ObjectUtils.toString(vo_600003.getApplyDate()));
		req_VO_600003.setApplyInfoMap(applyInputMap);

		Response<Object> response = IAPPExecuter.saveApplyInfo(req_VO_600003);

		if(response.getRepCode().equals("000000")){
			return FuncResult.success("产品信息初始化接口调用成功", response.getData());
		}else{
			throw new PlatformException("产品信息初始化接口调用失败！错误原因："+response.getRepMsg());
		}
	}

	@FuncIdAnnotate(value = "600004", desc = "保存取消的申请单信息接口", voCls = Vo_600004.class,isDependLogin = false)
	public FuncResult saveCancelApplyInput(Vo_600004 vo_600004) throws Exception {

		HashMap<String,Object> applyInputMap = new HashMap<String,Object>();
		Req_VO_600004 req_VO_600004 = new Req_VO_600004();

		String userCode = vo_600004.getUserCode();
		Date applyDate = vo_600004.getApplyDate();

		applyInputMap.put("applyInfo", vo_600004.getApplyInfo()); //申请信息
		applyInputMap.put("persionInfo", vo_600004.getPersionInfo()); //个人信息
		applyInputMap.put("refuseReason", vo_600004.getRefuseReason());//拒绝原因

		ArrayList<HashMap<String,Serializable>> empItemInfo = vo_600004.getEmpItemInfo();//工作信息

		if(CollectionUtils.isNotEmpty(empItemInfo)){
			for(HashMap<String,Serializable> eInfoMap : empItemInfo){
				String sectionlKey = (String)eInfoMap.get("sectionKey");
				if(sectionlKey!=null && "empItemInfo".equals(sectionlKey)){
					applyInputMap.put("empItemInfo", eInfoMap); //工作信息
				}
				if(sectionlKey!=null && "baseItemInfo".equals(sectionlKey)){
					applyInputMap.put("baseItemInfo", eInfoMap); //私营业主信息
				}
			}
		}

		ArrayList<HashMap<String,Serializable>> contactPersonInfo = vo_600004.getContactPersonInfo();//联系人信息列表
		applyInputMap.put("contactPersonInfo", contactPersonInfo);

		ArrayList<HashMap<String,Serializable>> assetsInfo = vo_600004.getAssetsInfo();//资产信息

		if(CollectionUtils.isNotEmpty(assetsInfo)){
			for(HashMap<String,Serializable> aInfoMap : assetsInfo){
				String sectionlKey = (String)aInfoMap.get("sectionKey");
				if(sectionlKey!=null && "salaryLoanInfo".equals(sectionlKey)){
					applyInputMap.put("salaryLoanInfo", aInfoMap); //随薪贷信息
				}
				if(sectionlKey!=null && "assetsInfo".equals(sectionlKey)){
					applyInputMap.put("estateInfo", aInfoMap);  //房产信息
				}
				if(sectionlKey!=null && "carInfo".equals(sectionlKey)){
					applyInputMap.put("carInfo", aInfoMap);  //车辆信息
				}
				if(sectionlKey!=null && "policyInfo".equals(sectionlKey)){
					applyInputMap.put("policyInfo", aInfoMap);  //保单信息
				}
				if(sectionlKey!=null && "providentInfo".equals(sectionlKey)){
					applyInputMap.put("providentInfo", aInfoMap);  //公积金信息
				}
				if(sectionlKey!=null && "cardLoanInfo".equals(sectionlKey)){
					applyInputMap.put("cardLoanInfo", aInfoMap);  //卡友贷信息
				}
				if(sectionlKey!=null && "masterLoanBInfo".equals(sectionlKey)){
					applyInputMap.put("masterLoanBInfo", aInfoMap);  //网购达人贷B信息
				}
				if(sectionlKey!=null && "masterLoanInfo".equals(sectionlKey)){
					applyInputMap.put("masterLoanInfo", aInfoMap);  //网购达人贷信息
				}
				if(sectionlKey!=null && "merchantLoanInfo".equals(sectionlKey)){
					applyInputMap.put("merchantLoanInfo", aInfoMap);  //淘宝商户贷信息
				}
			}
		}

		req_VO_600004.setUserCode(vo_600004.getUserCode());
		req_VO_600004.setIsZXBH("Y");
		req_VO_600004.setSysCode("app");
		req_VO_600004.setApplyDate(ObjectUtils.toString(vo_600004.getApplyDate()));
		req_VO_600004.setApplyInfoMap(applyInputMap);

		Response<Object> response = IAPPExecuter.saveCancelApplyInput(req_VO_600004);

		if(response.getRepCode().equals("000000")){
			return FuncResult.success("产品信息初始化接口调用成功", response.getData());
		}else{
			throw new PlatformException("产品信息初始化接口调用失败！错误原因："+response.getRepMsg());
		}
	} 

	@FuncIdAnnotate(value = "600005", desc = "身份证录单校验接口", voCls = Vo_600005.class,isDependLogin = false)
	public FuncResult checkIDCard(Vo_600005 vo_600005) throws Exception {

		String oldAppNo = vo_600005.getAppNo();
		String userCode = vo_600005.getUserCode();
		String idCardNo = vo_600005.getIdNo();
		String productCd = vo_600005.getProductCd();

		String isExists = "N";

		Date applyDate = null;
		Date lastThreeDay = null;
		Date lastDay = null;
		Date lastSubmitDate = null;

		
		Map<String, Object> map = new HashMap<String,Object>();
		map.put("appNo", oldAppNo);
		map.put("idNo", vo_600005.getIdNo());
	/*	List<TmApplyMainInfo> queryByMap = applyMainInfoService.queryByMap2(map);		
		if( !queryByMap.isEmpty() && null != queryByMap.get(0)){
			String idNo = queryByMap.get(0).getIdNo();
			throw new PlatformException(ResponseEnum.FULL_MSG,"身份证号码【"+idNo+"】已存在申请");
		}*/

		if(oldAppNo!=null && !oldAppNo.equals("")){
			map.remove("idNo");
			List<TmApplyMainInfo> qMap = applyMainInfoService.queryByMap3(map);
			if(qMap==null || qMap.size()==0){
				throw new PlatformException(ResponseEnum.FULL_MSG,"申请单号【"+oldAppNo+"】的申请不存在");
			}

			isExists="Y";

			TmApplyMainInfo tmi = qMap.get(0);
			applyDate = tmi.getApplyDate();
			lastThreeDay = tmi.getLastThreeDay();
			lastDay = tmi.getLastDay();
			lastSubmitDate = tmi.getLastSubmitTime();
		}

		Boolean isThrowE = false;

		System.err.println("appinfo: "+vo_600005.getApplyInfoField());

		/**改造部分*/
		String link = vo_600005.getLink();

		Req_VO_600005 req_VO_600005 = new Req_VO_600005();
		req_VO_600005.setSysCode("app");
		req_VO_600005.setUserCode(userCode);
		req_VO_600005.setIdCardNo(idCardNo);
		req_VO_600005.setName(vo_600005.getName());
		req_VO_600005.setProductCd(productCd);
		req_VO_600005.setIsExists(isExists);
		req_VO_600005.setApplyInfoMap(map);

		if(link.equals("app-dksq")){
			req_VO_600005.setLink(Constant.APP_DKSQ);
			req_VO_600005.setExecuteType(Constant.CLICK_NEXT);
		}else if(link.equals("app-sqxx")){
			req_VO_600005.setLink(Constant.APP_SQXX);
			req_VO_600005.setExecuteType(Constant.CLICK_SAVE);
		}

		Response<Object> response = IAPPExecuter.checkIDCard(req_VO_600005);

		Boolean flag = false;
		String ifNext = "N";
		Map<String,Object> responseMap = null;

		JSONObject obj = new JSONObject();
		if(response.getRepCode().equals("000000")){
			responseMap = (Map<String, Object>) response.getData();
			flag = true;
			ifNext = ObjectUtils.toString(responseMap.get("ifNext"));

			obj.put("lastThreeDay", responseMap.get("lastThreeDay"));
			obj.put("lastDay", responseMap.get("lastThreeDay"));
			obj.put("lastSubmitTime", responseMap.get("lastSubmitTime"));
		}

		String promptMessages = response.getRepMsg();
		String promptZXMessage = ""; // 征信提示信息

		//身份证校验成功
		if (flag){
			if(ifNext!=null && "N".equals(ifNext)){
				throw new PlatformException(ResponseEnum.FULL_MSG,promptMessages);
			}

			String appNo = "";
			if(oldAppNo!=null && !oldAppNo.equals("")){
				appNo=oldAppNo;
			}else{
				//生成随机appNo
				Date date = new Date(); 
				SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmssSSSSSS"); 
				appNo = dateFormat.format(date).toString();
				System.out.println(appNo); 
			}

			//查询是否有征信报告
			String hasCreditReport = new String();
			String applyState = null;
			/** 为了避免因为征信报告阻塞流程，在sys.properties文件中配置了查询征信报告的开关 */
			if(systemProperties.isHasCreditReport()){
				ValidateNameIdNoVO validateNameIdNoVO = new ValidateNameIdNoVO();
				validateNameIdNoVO.setSysCode("app");
				validateNameIdNoVO.setName(vo_600005.getName());
				validateNameIdNoVO.setIdNo(idCardNo);
				validateNameIdNoVO.setProductCode(productCd);
				Response<ReqCreditCheckVO> creditCheckResponse  = applyValidateExecuter.checkCreditUser(validateNameIdNoVO);

				if(null == creditCheckResponse){
					throw new PlatformException(ResponseEnum.FULL_MSG,"调用【征信白户接口】失败！接口返回【null】");
				}
				if(!"000000".equals(creditCheckResponse.getRepCode())){
					throw new PlatformException(ResponseEnum.FULL_MSG,"调用【征信白户接口】失败！接口返回【非000000】");
				}
				ReqCreditCheckVO reqCreditCheckVO = creditCheckResponse.getData();
				if(null == reqCreditCheckVO){
					throw new PlatformException(ResponseEnum.FULL_MSG,"调用【征信白户接口】成功！接口返回【相应结果对象为null】");
				}

				String reportId = reqCreditCheckVO.getReportId();
				if(StringUtils.isNotBlank(reportId)){
					hasCreditReport = "1";
					applyState="1";
					if("2".equals(reqCreditCheckVO.getType()) && reqCreditCheckVO.getIfTrigger()){//征信白户
						promptZXMessage = reqCreditCheckVO.getMsg();
						applyState = "0";
						isThrowE = true;
					}
					promptZXMessage = reqCreditCheckVO.getMsg();
				}else{
					hasCreditReport = "0";
					applyState="0";
				}
			}else{
				hasCreditReport = "1";
				applyState="1";
			}
			//点击下一步时需要推送至借款 综合查询需要查询
			Map pushMap=pushBms(vo_600005);
			String loanNo=pushMap.get("loanNo").toString();
			if(!Strings.isEmpty(loanNo)){//
				appNo=loanNo;
				logger.info("推送申请信息至借款成功！："+loanNo);
			}else{
				logger.info("推送申请信息至借款失败！"+pushMap.get("message"));
				throw new PlatformException(ResponseEnum.FULL_MSG,pushMap.get("message"));
			}
			
			try {
	
				JSONObject applyInfo = JSONObject.parseObject(vo_600005.getApplyInfoField());
				applyInfo.remove("hasCreditReport");
				applyInfo.put("hasCreditReport", hasCreditReport);
				vo_600005.setApplyInfoField(applyInfo.toString());

				if(oldAppNo!=null && !oldAppNo.equals("")){
					applyMainInfoService.deleteByAppNo(map);
					applyMainInfoService.saveMainInfo(vo_600005, appNo, hasCreditReport, ifNext, promptMessages, promptZXMessage,
							applyDate, lastThreeDay, lastDay, lastSubmitDate);
					map.put("fieldKey", "applyInfo");
					applyFieldInfoService.deleteByCon(map);
					applyFieldInfoService.saveFieldInfo2( vo_600005, appNo,applyState);
				}else{

					if(obj.containsKey("lastSubmitTime")){
						DateFormat dlf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
						lastThreeDay = dlf.parse(obj.getString("lastThreeDay")+" 00:00:00");
						lastDay = dlf.parse(obj.getString("lastDay")+" 00:00:00");
						lastSubmitDate = dlf.parse(obj.getString("lastSubmitTime")+" 00:00:00");
					}else{
						lastThreeDay = null;
						lastDay = null;
						lastSubmitDate = null;
					}

					applyDate = new Date();

					applyMainInfoService.saveMainInfo(vo_600005, appNo, hasCreditReport, ifNext, promptMessages, promptZXMessage, 
							applyDate, lastThreeDay, lastDay, lastSubmitDate);
					applyFieldInfoService.saveFieldInfo( vo_600005, appNo,applyState);
				}
			} catch (Exception e) {
				logger.error("保存申请信息异常", e);
				throw new PlatformException(ResponseEnum.FULL_MSG,e);
			}

			obj.put("userCode", userCode);
			obj.put("appNo", appNo);
			obj.put("hasCreditReport", hasCreditReport);
			obj.put("ifNext", ifNext);
			obj.put("promptMessages", promptMessages);
			obj.put("promptZXMessage", promptZXMessage);

			if(isThrowE){
				return FuncResult.fail(promptZXMessage, obj);
			}else{
				return FuncResult.success("身份证校验成功", obj);
			}
		}else{
			if(ifNext!=null && "N".equals(ifNext)){
				throw new PlatformException(ResponseEnum.FULL_MSG,promptMessages);
			}
			String message = obj.getString("message");
			return FuncResult.fail(message, obj);
		}
	}

	private Map pushBms(Vo_600005 vo_600005) {
		Map<String,Object> applyInputMap = new HashMap<String,Object>();
		Req_VO_600005 req_VO_600005 = new Req_VO_600005();
		String userCode = vo_600005.getUserCode();
		String appNo = vo_600005.getAppNo();
		//{"userCode":"00210552","hasCreditReport":"1","moduleAuthList":["随薪贷信息表"],"productName":"随薪贷",
		//"appNo":"20170515114135000054","reportId":"","sectionKey":"applyInfo","applyRate":"0.0248","productCd":"00001","productCd1":"随薪贷","applyLmt":"10000","applyTerm":"24","applyTerm1":"24","name":"老高","idNo":"430224199111011216","creditApplication":"00001","creditApplication1":"资金周转","branchManager":"00210552","remark":""}
		//填充借款主表的默认信息
		applyInputMap.put("appNo", appNo);
		applyInputMap.put("productCd", vo_600005.getProductCd());
		applyInputMap.put("applyLmt", vo_600005.getApplyLmt());
		applyInputMap.put("applyTerm", vo_600005.getApplyTerm());
		applyInputMap.put("name", vo_600005.getName());
		applyInputMap.put("idNo", vo_600005.getIdNo());
		applyInputMap.put("creditApplication", vo_600005.getCreditApplication());
		applyInputMap.put("ifPri", vo_600005.getIfPri());
		applyInputMap.put("userCode", userCode);
		req_VO_600005.setName(vo_600005.getName());
		req_VO_600005.setIdCardNo(vo_600005.getIdNo());
		req_VO_600005.setUserCode(vo_600005.getUserCode());
		req_VO_600005.setSysCode("app");
		req_VO_600005.setUserCode(vo_600005.getUserCode());
		req_VO_600005.setApplyInfoMap(applyInputMap);
		Response<Object> response = IAPPExecuter.saveLoanBaseInfo(req_VO_600005);
		logger.info("推送借款返回："+response.isSuccess()+"|"+response.getRepMsg());
		String loanNo =response.getData()==null?"":String.valueOf(response.getData());
		Map retrunMap =new HashMap();
		retrunMap.put("loanNo", loanNo);
		retrunMap.put("message", response.getRepMsg());
		return retrunMap;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@FuncIdAnnotate(value = "600006", desc = "历史申请状态查询接口", voCls = Vo_600006.class,isDependLogin = false)
	public FuncResult getHisApplyInputList(Vo_600006 vo_600006) throws Exception {
		//对象转换
		Req_VO_600006 req_VO_600006 = new Req_VO_600006();
		BeanUtils.copyProperties(req_VO_600006, vo_600006);
		String userCode = vo_600006.getUserCode();
		PageResponse<Res_VO_600006> res = null;
		//调用接口
		req_VO_600006.setSysCode("app");
		res = IAPPExecuter.getHisApplyInputList(req_VO_600006);
		//返回结果
		if(res.isSuccess()){
			Map dataMap =new HashMap();
			dataMap.put("userCode", userCode);
			dataMap.put("totalNo", res.getRecords()==null?0: res.getRecords().size());
			dataMap.put("appInputListData", res.getRecords());
			return FuncResult.success("查询成功", dataMap);
		}else{
			String message = res.getRepMsg();
			throw new PlatformException(message);
		}
	}

	@FuncIdAnnotate(value = "600007", desc = "首页信息查询接口", voCls = Vo_600007.class,isDependLogin = false)
	public FuncResult getShowList(Vo_600007 vo_600007) throws Exception {
		boolean flag1=false;
		//对象转换
		Req_VO_600007 req_VO_600007 = new Req_VO_600007();
		BeanUtils.copyProperties(req_VO_600007, vo_600007);
		Response<Object> res = null;
		//参数封装
		req_VO_600007.setSysCode("app");
		req_VO_600007.setPageSize(20);
		req_VO_600007.setPageNum(1);
		//调用接口
		res = IAPPExecuter.getfirstPageData(req_VO_600007);
		Map result=(Map)res.getData();
		JSONObject obj = new JSONObject();
		if(res.isSuccess()){
			flag1 = true;
			obj.put("overNum", result.get("overNum"));
			obj.put("rejectNum",result.get("rejectNum"));
		}
		obj.put("message", res.getRepMsg());
		//返回结果
		if(flag1){
			Map dataMap =(Map) res.getData();
			/** 查询公告结果集 **/
			JSONObject paramGG = new JSONObject();
			/** 查询调用核心登录校验接口url **/
			String requestUrlGG = coreProperties.getServiceUrl()
					+ "/app/notice/searchNotice";
			paramGG.put("pagerNum", 1);
			paramGG.put("pagerMax", 20);
			String resultGG = httpUrlConnection.postForEntity(requestUrlGG,
					paramGG, String.class);
			resultGG = URLDecoder.decode(resultGG, "UTF-8");
			JSONObject objGG = JSONObject.parseObject(resultGG);
			String resCode = "";
			FuncResult funcResult = null;
			if (objGG.containsKey("resCode")) {
				resCode = objGG.getString("resCode");
				if ("000000".equals(resCode)) {
					JSONObject attachment = objGG.getJSONObject("attachment");
					if (attachment == null) {
						throw new PlatformException(ResponseEnum.FULL_MSG,"缺少attachment数据项");
					}
					/** 公告结果集 **/
					JSONArray noticeList = new JSONArray();
					noticeList = attachment.getJSONArray("resultList");
					if (noticeList == null || noticeList.size() == 0){
						obj.put("noticeList", new JSONObject());
					}else{
						obj.put("noticeList", noticeList.get(0));
					}
					funcResult = FuncResult.success("查询成功", obj);
				} else {
					funcResult = FuncResult.fail(objGG.getString("resMsg"));
				}
			} else {
				funcResult = FuncResult.fail("核心返回缺少【resCode】Key");
			}
			return funcResult;
		}else{
			String message = obj.getString("message");
			throw new PlatformException(message);
		}
	}

	@FuncIdAnnotate(value = "600008", desc = "贷前试算接口", voCls = Vo_600008.class)
	public FuncResult getTrialBeforeCredit(Vo_600008 vo_600008) throws Exception {

		JSONObject obj = new JSONObject();
		Req_VO_600008 req_VO_600008 = new Req_VO_600008();
		BeanUtils.copyProperties(req_VO_600008, vo_600008);

		Response<List<ResTrialBeforeCreditVO>> response = IAPPExecuter.getTrialBeforeCredit(req_VO_600008);

		if(response.getRepCode().equals("000000")){
			List<Map<String,Object>> returnList = new ArrayList<>(); 
			List<ResTrialBeforeCreditVO> resultList = response.getData();

			if(CollectionUtils.isEmpty(resultList)){
				throw new PlatformException("没有查询到结果！");
			}

			Map<String,Object> tempMap = null;
			Map<String,Object> resultMap = null;
			for (int i = 0; i < resultList.size(); i++) {
				resultMap = (Map<String, Object>) resultList.get(i);
				tempMap = new HashMap<>();
				tempMap.put("paymentMoney", ObjectUtils.toString(resultMap.get("returneterm")));
				tempMap.put("paymentAllMoney", ObjectUtils.toString(resultMap.get("repaymentAll")));
				tempMap.put("paymentDate", ObjectUtils.toString(resultMap.get("returnDate")));
				tempMap.put("applyTermNo", resultMap.get("currentTerm"));
				returnList.add(tempMap);
			}

			obj.put("flag", true);
			obj.put("trialBeforeCreditListData", returnList);
			return FuncResult.success("贷前试算信息查询成功", obj);
		}else{
			throw new PlatformException(response.getRepMsg());
		}
	}

	@FuncIdAnnotate(value = "700006", desc = "身份验证接口", voCls = Vo_700006.class)
	public FuncResult checkIDCard(Vo_700006 vo_700006) throws Exception {

		/*	Map<String, Object> map = new HashMap<String,Object>();
		map.put("idNo", vo_700006.getIdNo());
		List<TmApplyMainInfo> queryByMap = applyMainInfoService.queryByMap(map);		
		if( !queryByMap.isEmpty() && null != queryByMap.get(0)){
			String idNo = queryByMap.get(0).getIdNo();
			throw new PlatformException(ResponseEnum.FULL_MSG,"身份证号码【"+idNo+"】已存在申请");
		}*/

		/**改造部分*/
		Req_VO_600005 req_VO_600005 = new Req_VO_600005();
		req_VO_600005.setSysCode("app");
		req_VO_600005.setUserCode(vo_700006.getUserCode());
		req_VO_600005.setIdCardNo(vo_700006.getIdNo());
		req_VO_600005.setName(vo_700006.getName());
		req_VO_600005.setLink(Constant.APP_SFHC);
		req_VO_600005.setExecuteType(Constant.CLICK_CHECK);

		Response<Object> response = IAPPExecuter.checkIDCard(req_VO_600005);

		Boolean flag = false;
		String ifNext = "N";
		Map<String,Object> responseMap = null;

		JSONObject obj = new JSONObject();
		if(response.getRepCode().equals("000000")){
			responseMap = (Map<String, Object>) response.getData();
			flag = true;
			ifNext = ObjectUtils.toString(responseMap.get("ifNext"));
		}
		String promptMessages = response.getRepMsg();
		/**改造部分*/

		if (flag) {
			obj.put("ifNext", ifNext);
			obj.put("promptMessages", promptMessages);
			return FuncResult.success("身份验证成功", obj);
		} else {
			throw new PlatformException(promptMessages);
		}
	}
	
	
	@FuncIdAnnotate(value = "600012", desc = "判断是否是直通车topup或三个月内reload用户", voCls = Vo_600012.class,isDependLogin = false)
	public FuncResult getImageFileUpload(Vo_600012 vo_600012) throws Exception {
		JSONObject returnJson= new JSONObject();
		/** 查询调用核心查询该人对应债券**/
		String requestUrl = coreProperties.getServiceUrl()+"/core/loanCore/loanStatus";
		logger.info(requestUrl);
//		Map<String,String> map=new HashMap<String,String>();
//		map.put("idnum", vo_600012.getIdNo());
//		map.put("userCode", "app");
		
		JSONObject obj = new JSONObject();
		obj.put("idnum", vo_600012.getIdNo());
		obj.put("userCode", "app");
		String result = httpUrlConnection.postForEntity(requestUrl,
				obj, String.class);
		logger.info(result);
		if(null!=result&&result.length()>0){
			result=new String(result.getBytes("iso-8859-1"),"UTF-8");
		}
		
		JSONObject resultObj = JSONObject.parseObject(result);
		if(resultObj == null ||!"000000".equals(resultObj.get("code"))) {
			throw new PlatformException(ResponseEnum.FULL_MSG,"调用核心系统失败");
		}
		List<BaseLoanStatusVo> list=new ArrayList<BaseLoanStatusVo>();
		if(!resultObj.get("msg").equals(APPEnumConstants.GYHBZXTZ)){
			JSONArray results=resultObj.getJSONArray("loan");
			for (int i = 0; i < results.size(); i++) {
				JSONObject jsonObject = results.getJSONObject(i);
				BaseLoanStatusVo baseLoanStatusVo =JSONObject.toJavaObject(jsonObject, BaseLoanStatusVo.class);
				list.add(baseLoanStatusVo);
			}
		}
		Boolean PrimFlag = false;
		String applyType = "NEW";// 申请类型，默认为NEW
		for (BaseLoanStatusVo vo : list) {
			if ("正常".equals(vo.getLoanState())) {
				if ("TOPUP".equals(applyType)) {
					returnJson.put("status", "000000");
					returnJson.put("flag", false);
					returnJson.put("resMsg", URLEncoder.encode("客户最多只能存在2笔借款，请核实！", "UTF-8"));
					break;
				}
				applyType = "TOPUP";
			} else if ("结清".equals(vo.getLoanState()) || "预结清".equals(vo.getLoanState())) {
				if("结清".equals(vo.getLoanState()))
				{
				 boolean flag=Dates.getPrinFlag(vo.getCreateTime());
				 if(flag)
				 {
					 PrimFlag=flag;
				 }
				}
				applyType = "RELOAN";
			} else if ("逾期".equals(vo.getLoanState())) {
				applyType = "TOPUP";
			}
		}
		// 通过申请
		if(PrimFlag==true||"TOPUP".equals(applyType)){
			returnJson.put("status", "000000");
			returnJson.put("flag", true);
		}else{
			returnJson.put("status", "000000");
			returnJson.put("flag", false);
		}
		
		return FuncResult.success("判断是否是直通车topup或三个月内reload用户成功", returnJson);
	}
	
	@FuncIdAnnotate(value = "600013", desc = "影像文件补录申请件列表查询", voCls = Vo_600013.class,isDependLogin = false)
	public FuncResult getImageFileUpload(Vo_600013 vo_600013) throws Exception {
		//调用接口
		//vo_600013.setSysCode("app");
		Req_VO_600013 vo=new Req_VO_600013();
		BeanUtils.copyProperties(vo, vo_600013);
		vo.setSysCode("app");
		Response<Res_VO_600013> response = IAPPExecuter.getImageFileUpload(vo);

		if(response.getRepCode().equals("000000")){
			return FuncResult.success("影像文件补录申请件列表查询成功", response.getData());
		}else{
			throw new PlatformException("影像文件补录申请件列表查询失败！错误编码："+response.getRepCode());
		}
	}
	
	
	@FuncIdAnnotate(value = "600014", desc = "保存上传记录文档", voCls = Vo_600014.class,isDependLogin = false)
	public FuncResult getImageFileUpload(Vo_600014 vo_600014) throws Exception {
		//调用接口
		vo_600014.setSysCode("app");
		
		JSONObject param = new JSONObject();
		String requestUrl = systemProperties.getSaveserviceUrl();

		param.put("customerName", vo_600014.getCustomerName());
		param.put("customerNo", vo_600014.getCustomerNo());
		param.put("applicationCaseNo", vo_600014.getApplicationCaseNo());
		param.put("fileName", vo_600014.getFileName());
		param.put("fileKey", vo_600014.getFileKey());
		param.put("fileType", vo_600014.getFileType());
		param.put("fileSize", vo_600014.getFileSize());
		param.put("downloadUrl", vo_600014.getDownloadUrl());
		param.put("userName", vo_600014.getUserName());
		param.put("userNum", vo_600014.getUserNum());
		param.put("certifyBusinessDepart", vo_600014.getCertifyBusinessDepart());
		SimpleDateFormat sdf =new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		param.put("uploadTime",sdf.format(new Date()));
		param.put("sysCode",vo_600014.getSysCode());

		String result = httpUrlConnection.postForEntity(requestUrl, param, String.class);

		result = URLDecoder.decode(result, "UTF-8");
		JSONObject resultObj = JSONObject.parseObject(result);
		if(resultObj == null ||!"0000".equals(resultObj.get("code"))) {
			throw new PlatformException(ResponseEnum.FULL_MSG,"调用存储系统失败");
		}
		JSONObject jay= new JSONObject();
		jay.put("code", resultObj.get("code"));
		jay.put("msg", resultObj.get("msg"));
		jay.put("data", resultObj.get("Data"));
		return FuncResult.success("保存成功", jay);

	}
	
	@FuncIdAnnotate(value = "600009", desc = "身份证录单校验接口", voCls = Vo_600009.class)
	public FuncResult checkIDCardNotSave(Vo_600009 vo_600009) throws Exception {
		
		String oldAppNo = vo_600009.getAppNo();
		String userCode = vo_600009.getUserCode();
		String idCardNo = vo_600009.getIdNo();
		String productCd = vo_600009.getProductCd();
		
		Map<String, Object> map = new HashMap<String,Object>();
		map.put("appNo", oldAppNo);
		map.put("idNo", vo_600009.getIdNo());
		/*	List<TmApplyMainInfo> queryByMap = applyMainInfoService.queryByMap2(map);		
			if( !queryByMap.isEmpty() && null != queryByMap.get(0)){
			 String idNo = queryByMap.get(0).getIdNo();
			 throw new PlatformException(ResponseEnum.FULL_MSG,"身份证号码【"+idNo+"】已存在申请");
		}*/
		
		if(oldAppNo!=null && !oldAppNo.equals("")){
			map.remove("idNo");
			List<TmApplyMainInfo> qMap = applyMainInfoService.queryByMap3(map);
			if(qMap==null || qMap.size()==0){
				throw new PlatformException(ResponseEnum.FULL_MSG,"申请单号【"+oldAppNo+"】的申请不存在");
			}
		}
		
		
		/**改造部分*/
		Req_VO_600005 req_VO_600005 = new Req_VO_600005();
		req_VO_600005.setSysCode("app");
		req_VO_600005.setUserCode(userCode);
		req_VO_600005.setIdCardNo(idCardNo);
		req_VO_600005.setName(vo_600009.getName());
		req_VO_600005.setProductCd(productCd);
		req_VO_600005.setIsExists("Y");
		req_VO_600005.setLink(Constant.APP_DKSQ);
		req_VO_600005.setExecuteType(Constant.CLICK_NEXT);
		req_VO_600005.setApplyInfoMap(map);

		Response<Object> response = IAPPExecuter.checkIDCard(req_VO_600005);

		Boolean flag = false;
		String ifNext = "N";
		Map<String,Object> responseMap = null;

		JSONObject obj = new JSONObject();
		if(response.getRepCode().equals("000000")){
			responseMap = (Map<String, Object>) response.getData();
			flag = true;
			ifNext = ObjectUtils.toString(responseMap.get("ifNext"));
		}

		String promptMessages = response.getRepMsg();
		String promptZXMessage = ""; // 征信提示信息

		//身份证校验成功
		if (flag){
			if(ifNext!=null && "N".equals(ifNext)){
				throw new PlatformException(ResponseEnum.FULL_MSG,promptMessages);
			}

			//查询是否有征信报告
			String hasCreditReport = new String();
			String applyState = null;
			
			/** 为了避免因为征信报告阻塞流程，在sys.properties文件中配置了查询征信报告的开关 */
			ValidateNameIdNoVO validateNameIdNoVO = new ValidateNameIdNoVO();
			validateNameIdNoVO.setSysCode("app");
			validateNameIdNoVO.setName(vo_600009.getName());
			validateNameIdNoVO.setIdNo(idCardNo);
			validateNameIdNoVO.setProductCode(productCd);
			Response<ReqCreditCheckVO> creditCheckResponse  = applyValidateExecuter.checkCreditUser(validateNameIdNoVO);

			if(null == creditCheckResponse){
				throw new PlatformException(ResponseEnum.FULL_MSG,"调用【征信白户接口】失败！接口返回【null】");
			}
			if(!"000000".equals(creditCheckResponse.getRepCode())){
				throw new PlatformException(ResponseEnum.FULL_MSG,"调用【征信白户接口】失败！接口返回【非000000】");
			}
			ReqCreditCheckVO reqCreditCheckVO = creditCheckResponse.getData();
			if(null == reqCreditCheckVO){
				throw new PlatformException(ResponseEnum.FULL_MSG,"调用【征信白户接口】成功！接口返回【相应结果对象为null】");
			}

			String reportId = reqCreditCheckVO.getReportId();
			if(StringUtils.isNotBlank(reportId)){
				hasCreditReport = "1";
				applyState="1";
				if("2".equals(reqCreditCheckVO.getType()) && reqCreditCheckVO.getIfTrigger()){//征信白户
					promptZXMessage = reqCreditCheckVO.getMsg();
					applyState = "0";
				}
				promptZXMessage = reqCreditCheckVO.getMsg();
			}else{
				hasCreditReport = "0";
				applyState="0";
			}

			obj.put("promptMessages", promptMessages);
			obj.put("promptZXMessage", promptZXMessage);
			return FuncResult.success("身份证校验成功", obj);
		}else{
			if(ifNext!=null && "N".equals(ifNext)){
				throw new PlatformException(ResponseEnum.FULL_MSG,promptMessages);
			}
			String message = obj.getString("message");
			return FuncResult.fail(message, obj);
		}
	}
}