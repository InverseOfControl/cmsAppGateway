package com.zdmoney.credit.api.framework.service;

import java.io.Serializable;
import java.net.URLDecoder;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.ObjectUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ymkj.base.core.biz.api.message.Response;
import com.ymkj.cms.biz.api.service.app.IAPPExecuter;
import com.ymkj.cms.biz.api.service.apply.IApplyValidateExecuter;
import com.ymkj.cms.biz.api.vo.request.app.Req_VO_600003;
import com.ymkj.cms.biz.api.vo.request.app.Req_VO_600004;
import com.ymkj.cms.biz.api.vo.request.app.Req_VO_600005;
import com.ymkj.cms.biz.api.vo.request.app.Req_VO_900005;
import com.ymkj.cms.biz.api.vo.request.apply.ReqCreditCheckVO;
import com.ymkj.cms.biz.api.vo.request.apply.ValidateNameIdNoVO;
import com.zdmoney.credit.applyinfo.domain.TmApplyFieldInfo;
import com.zdmoney.credit.applyinfo.domain.TmApplyMainInfo;
import com.zdmoney.credit.applyinfo.service.pub.ITmApplyFieldInfoService;
import com.zdmoney.credit.applyinfo.service.pub.ITmApplyMainInfoService;
import com.zdmoney.credit.callinter.HttpUrlConnection;
import com.zdmoney.credit.common.APPEnumConstants;
import com.zdmoney.credit.common.annotate.FuncIdAnnotate;
import com.zdmoney.credit.common.constant.system.Constant;
import com.zdmoney.credit.common.exception.PlatformException;
import com.zdmoney.credit.common.exception.ResponseEnum;
import com.zdmoney.credit.common.util.IDCardValidateUtil;
import com.zdmoney.credit.common.vo.FuncResult;
import com.zdmoney.credit.config.ApsAppProperties;
import com.zdmoney.credit.config.CreditZxProperties;
import com.zdmoney.credit.config.PicAppProperties;
import com.zdmoney.credit.config.SystemProperties;
import com.zdmoney.credit.framework.vo.Vo_700001;
import com.zdmoney.credit.framework.vo.Vo_700002;
import com.zdmoney.credit.framework.vo.Vo_700003;
import com.zdmoney.credit.framework.vo.Vo_700004;
import com.zdmoney.credit.framework.vo.Vo_700005;
import com.zdmoney.credit.framework.vo.Vo_900005;
import com.zdmoney.credit.framework.vo.Vo_900006;

@Service
public class PreAPPService extends BusinessService {

	protected static Log logger = LogFactory.getLog(PreAPPService.class);

	@Autowired
	HttpUrlConnection httpUrlConnection;
	
	@Autowired
	ApsAppProperties apsAppProperties;
	
	@Autowired
	SystemProperties systemProperties;
	
	@Autowired
	PicAppProperties picAppProperties;
	
	@Autowired
	CreditZxProperties creditZxProperties;
	
	@Autowired
	private ITmApplyMainInfoService applyMainInfoService;
	@Autowired
	private ITmApplyFieldInfoService applyFieldInfoService;
	
	@Autowired
	private IAPPExecuter IAPPExecuter;
	
	@Autowired
	private IApplyValidateExecuter applyValidateExecuter;

	@FuncIdAnnotate(value = "700001", desc = "保存未提交申请单的参数信息", voCls = Vo_700001.class)
	public FuncResult saveTempApplyField(Vo_700001 vo_700001) throws Exception {

		String appNO = vo_700001.getAppNo();
		Boolean flag = false;
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("appNo", appNO);
		List<TmApplyMainInfo> mains = applyMainInfoService.queryByMap(map);
		TmApplyMainInfo tmApplyMain = null;
		if (null != mains && mains.size() !=0) {
			tmApplyMain = mains.get(0);
			flag = true;
		}
		if (flag) {
			String fieldKey = vo_700001.getField_key();
			Map<String, Object> map2 = new HashMap<String, Object>();
			map2.put("appNo", appNO);
			map2.put("fieldKey", fieldKey);
			List<TmApplyFieldInfo> fields = applyFieldInfoService.queryByMap(map2);
			TmApplyFieldInfo tmApplyFieldInfo = new TmApplyFieldInfo();
			tmApplyFieldInfo.setAppNo(appNO);
			tmApplyFieldInfo.setFieldKey(fieldKey);
			String fieldValue = vo_700001.getField_value()==null?"{}":vo_700001.getField_value();
			if(fieldValue.startsWith("[")){
				tmApplyFieldInfo.setFieldArrValue(fieldValue);
				tmApplyFieldInfo.setFieldType(Constant.ArrType);
			}else{
				tmApplyFieldInfo.setFieldObjValue(fieldValue);
				tmApplyFieldInfo.setFieldType(Constant.ObjType);
			}
			tmApplyFieldInfo.setState(vo_700001.getState());
			
			if (fields != null && !fields.isEmpty()) {
				tmApplyFieldInfo.setId(fields.get(0).getId());
				tmApplyFieldInfo.setUpdateDate(new Date());
				applyFieldInfoService.update(tmApplyFieldInfo);
			} else {
				tmApplyFieldInfo.setCreateDate(new Date());
				applyFieldInfoService.insert(tmApplyFieldInfo);
			}
			
			Map<String, Object> mapField = new HashMap<String, Object>();
			mapField.put("appNo", appNO);
			mapField.put("state", "0");
			List<TmApplyFieldInfo> fieldList = applyFieldInfoService.queryByMap(mapField);
			
			//如果婚姻为已婚、复婚、再婚时，配偶信息必填，如果不是，则不必填。此时，也是可提交状态。
			if(fieldList.size() == 1){
				Map<String, Object> map3 = new HashMap<String, Object>();
				map3.put("appNo", appNO);
				map3.put("fieldKey", "persionInfo");
				List<TmApplyFieldInfo> fieldsList1 = applyFieldInfoService.queryByMap(map3);
				JSONObject obj = new JSONObject().parseObject(fieldsList1.get(0).getFieldObjValue());
				if(obj.containsKey("maritalStatus")){
					String fieldKeyTmp = fieldList.get(0).getFieldKey();
					if(fieldKeyTmp.equals("mateInfo") && "00002@00006@00007".indexOf(obj.getString("maritalStatus")) == -1){
						fieldList.remove(fieldList.get(0));
					}
				}
			}
			
			if( fieldList.isEmpty() || fieldList.size() == 0 ){
				if(null != tmApplyMain){
					tmApplyMain.setState("1");
					applyMainInfoService.update(tmApplyMain);
				}
			}else{
				if(null != tmApplyMain && tmApplyMain.getState().equals("1") ){
					tmApplyMain.setState("0");
					applyMainInfoService.update(tmApplyMain);
				}
			}
			return FuncResult.success();
		} else {
			throw new PlatformException(ResponseEnum.FULL_MSG, "申请单[" + appNO
					+ "]在网关无记录，不能上传相关信息");
		}
	}

	@FuncIdAnnotate(value = "700002", desc = "获取未提交的申请单列表", voCls = Vo_700002.class)
	public FuncResult getTempApplyInputList(Vo_700002 vo_700002)
			throws Exception {

		JSONObject retObj = new JSONObject(); // 返回数据封装

		String userCode = vo_700002.getUserCode();
		retObj.put("userCode", userCode);

		ArrayList<Map<String, Object>> tempAppInputListData = new ArrayList<Map<String, Object>>();

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("userId", userCode);
		String idCardNoLastFourDigits = vo_700002.getIdCardNoLastFourDigits()==null?"":vo_700002.getIdCardNoLastFourDigits();
		map.put("id_no", idCardNoLastFourDigits);
		String userName = vo_700002.getApplicantName()==null?"":vo_700002.getApplicantName();
		map.put("user_name", userName);
		int pageNum = vo_700002.getPageNum();
		if(pageNum < 1){
			pageNum = 1;
		}
		int pageSize = vo_700002.getPageSize();
		map.put("start", (pageNum-1) * pageSize );
		map.put("end", (pageNum-1) * pageSize + pageSize);

		List<TmApplyMainInfo> mainInfos = applyMainInfoService.pageQueryByMap(map);
		
		DateFormat df = new SimpleDateFormat("yyyy.MM.dd");
		if (null != mainInfos && !mainInfos.isEmpty()) {
			
			Date nowDate = new Date();
			
			for (TmApplyMainInfo main : mainInfos) {
				Map<String, Object> mapInfo = new HashMap<String, Object>();
				mapInfo.put("appNo", main.getAppNo());
				mapInfo.put("applicantName", main.getUserName());
				String currentTime = df.format(main.getApplyDate());
				mapInfo.put("applyTime", currentTime);
				mapInfo.put("applyAccount", main.getApplyAccount());
				mapInfo.put("productName", main.getProductName());
				if(StringUtils.isNotEmpty(main.getIdNo())){
					mapInfo.put("idCardNoLastFourDigits", main.getIdNo().substring(main.getIdNo().length()-4,main.getIdNo().length()));
				}
				mapInfo.put("applyTerm", main.getApplyTerm());
				String ifNext = main.getIfNext() == null ? "Y" : main.getIfNext();
				if("N".equals(ifNext)){
					mapInfo.put("state", "0");
				}else{
					mapInfo.put("state", main.getState());
				}
				
				mapInfo.put("ifNext", "Y");
				mapInfo.put("promptMessages", main.getPromptMessages() == null ? "" : main.getPromptMessages());
				mapInfo.put("promptZXMessage", main.getPromptZXMessage() == null ? "" : main.getPromptZXMessage());
				
				Date lastThreeDay = main.getLastThreeDay();
				if(lastThreeDay!=null && nowDate.after(lastThreeDay)){
					mapInfo.put("isLastThreeDay", "Y");
				}else{
					mapInfo.put("isLastThreeDay", "N");
				}
				
				//标黄标志,	当前时间-申请创建时间>3个工作日
				if(APPEnumConstants.identificValue.YES.getValue().equals(main.getIsLastThreeDay())){
					mapInfo.put("isLastThreeDay", "Y");
				}
				
				Date lastDay = main.getLastDay();
				if(lastDay!=null && nowDate.after(lastDay)){
					mapInfo.put("isLastDay", "Y");
				}else{
					mapInfo.put("isLastDay", "N");
				}
				
				mapInfo.put("lastSubmitTime", lastDay==null?"":df.format(lastDay));
				mapInfo.put("isMoved", ObjectUtils.toString(main.getIsMoved()));
				
				tempAppInputListData.add(mapInfo);
			}
		}
		retObj.put("tempAppInputListData", tempAppInputListData);

		int totalNo = applyMainInfoService.countByMap(map);
		retObj.put("totalNo", totalNo);
		retObj.put("userCode", userCode);

		return FuncResult.success("查询成功", retObj);
	}

	@FuncIdAnnotate(value = "700003", desc = "获取未提交的申请单信息", voCls = Vo_700003.class)
	public FuncResult getTempApplyInputInfo(Vo_700003 vo_700003)
			throws Exception {

		JSONObject retObj = new JSONObject(); // 返回数据封装

		String appNO = vo_700003.getAppNo();
		retObj.put("appNo", appNO);

		ArrayList<Map<String, Serializable>> fieldList = new ArrayList<Map<String, Serializable>>();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("appNo", appNO);
		List<TmApplyFieldInfo> fields = applyFieldInfoService.queryByMap(map);
		if (null != fields && !fields.isEmpty()) {
			for (TmApplyFieldInfo field : fields) {
				Map<String, Serializable> mapField = new HashMap<String, Serializable>();
				mapField.put("fieldKey", field.getFieldKey());
				String fieldType = field.getFieldType();
				mapField.put("fieldType", fieldType);
				if(fieldType.equals(Constant.ArrType)){
					mapField.put("fieldArrayValue", JSONArray.parseArray(field.getFieldArrValue()));
				}else{
					mapField.put("fieldObjValue", JSONObject.parseObject(field.getFieldObjValue()));
				}
				mapField.put("state", field.getState());
				fieldList.add(mapField);
			}
		}
		
		retObj.put("fieldList", fieldList);
		
		/**新的图片服务器*/
		JSONObject param = new JSONObject();
		String requestUrl = picAppProperties.getServiceUrl() + "/api/paperstype/list";
		param.put("appNo", appNO);
		param.put("operator", vo_700003.getOperatorName());
		param.put("jobNumber", vo_700003.getOperatorCode());
		param.put("nodeKey", "loanApplication");
		
		String result = httpUrlConnection.postForEntity(requestUrl, param, String.class);
		
		result = URLDecoder.decode(result, "UTF-8");
		JSONObject obj = JSONObject.parseObject(result);
		
		if(ResponseEnum.PIC_FAIL.getCode().equals(obj.getString("errorcode"))){
			String message = obj.getString("errormsg");
			return FuncResult.fail(message);
		}
		
		retObj.put("fileTypeList", obj.getJSONArray("result"));
		return FuncResult.success("查询成功", retObj);
		/**对接新的图片服务器*/
		
	}

	@FuncIdAnnotate(value = "700004", desc = "提交申请单到征审系统", voCls = Vo_700004.class, isDependLogin = false)
	public FuncResult submitTempApplyInputInfo(Vo_700004 vo_700004) throws Exception {

		String appNO = vo_700004.getAppNo();

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("appNo", appNO);
		List<TmApplyMainInfo> mains = applyMainInfoService.queryByMap(map);
		
		if(mains.isEmpty()){
			 throw new PlatformException(ResponseEnum.FULL_MSG,"该申请单号【"+appNO+"】不在未提交状态,或不存在");
		}
		TmApplyMainInfo tami = mains.get(0);
		String state = tami.getState();
		if(state.equals("0")){
			 throw new PlatformException(ResponseEnum.FULL_MSG,"该申请信息不完整,不能提交");
		}
		
		// 在待提交申请页面，点击编辑时，对身份证和征信做同步校验
		String ifNext = "Y";
		String promptMessages = "";
		String isZXBH = "N";

		String userCode = tami.getUserId();
		String userName = tami.getUserName();
		String idCardNo = tami.getIdNo();
		String productCd = tami.getProductCode();
		String resultID = null;
		JSONObject paramID = new JSONObject();
		
		/**改造部分*/
		Req_VO_600005 req_VO_600005 = new Req_VO_600005();
		req_VO_600005.setSysCode("app");
		req_VO_600005.setUserCode(userCode);
		req_VO_600005.setIdCardNo(idCardNo);
		req_VO_600005.setName(userName);
		req_VO_600005.setProductCd(productCd);
		req_VO_600005.setIsExists("Y");
		req_VO_600005.setLink(Constant.APP_SQXX);
		req_VO_600005.setExecuteType(Constant.CLICK_SUBMIT);
		req_VO_600005.setApplyInfoMap(map);
		Response<Object> validateResponse = IAPPExecuter.checkIDCard(req_VO_600005);
		
		Map<String,Object> responseMap = null;
		
		JSONObject objID = new JSONObject();
		if(validateResponse.getRepCode().equals("000000")){
			responseMap = (Map<String, Object>) validateResponse.getData();
			ifNext = ObjectUtils.toString(responseMap.get("ifNext"));
		}else{
			throw new PlatformException(ResponseEnum.FULL_MSG,"调用规则引擎失败！接口返回【非000000】");
		}
		promptMessages = validateResponse.getRepMsg();
		objID.put("message", promptMessages);
		/**改造部分*/
		if ("N".equals(ifNext)) {
			String message = objID.getString("message");
			throw new PlatformException(message);
		}
			
		if(systemProperties.isHasCreditReport()){
			ValidateNameIdNoVO validateNameIdNoVO = new ValidateNameIdNoVO();
			validateNameIdNoVO.setSysCode("app");
			validateNameIdNoVO.setName(userName);
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
			if(StringUtils.isBlank(reportId)){
				throw new PlatformException("没有征信报告！");
			}
			if("2".equals(reqCreditCheckVO.getType())){
				isZXBH = "Y";
			}
		}else{
			isZXBH = "Y";
		}
		
		List<TmApplyFieldInfo> fields = null;
		try{
			fields = applyFieldInfoService.queryByMap(map);
		}catch(Exception e){
			e.printStackTrace();
		}
		
		Map<String, String> map2 = new HashMap<String,String>();
		if(null !=fields && !fields.isEmpty()){
			for(TmApplyFieldInfo field:fields){
				String fieldKey = field.getFieldKey();
				String fieldType = field.getFieldType();
				if(fieldType.equals(Constant.ArrType)){
						map2.put(fieldKey, field.getFieldArrValue());
				}else{
						map2.put(fieldKey, field.getFieldObjValue());
				}
			}
		}
		Map<String,Object> applyInputMap = new HashMap<String,Object>();
		
		//tami
		applyInputMap.put("loanNo", appNO); 
		logger.info("700004接口提交对象：appNo="+tami.getAppNo()+"&userId"+tami.getUserId()+"&isMoved="+tami.getIsMoved());
		
		JSONObject applyInfo = JSONObject.parseObject(map2.get("applyInfo"));
		if(null !=applyInfo){
			applyInfo.put("isMoved", tami.getIsMoved());
			applyInputMap.put("applyInfo", applyInfo); //申请信息
		}
		
		JSONObject persionInfo = JSONObject.parseObject(map2.get("persionInfo"));
		if(null != persionInfo ){
			applyInputMap.put("persionInfo", persionInfo); //个人信息
		}
		
		JSONArray empItemInfoList = JSONArray.parseArray(map2.get("empItemInfo"));//工作信息
		if(null != empItemInfoList && empItemInfoList.size()!=0){
			for(int i=0;i<empItemInfoList.size();i++){
				JSONObject paramInfo = empItemInfoList.getJSONObject(i);
				String sectionlKey = paramInfo.getString("sectionKey");
				if(sectionlKey!=null && "empItemInfo".equals(sectionlKey)){
					applyInputMap.put("empItemInfo", paramInfo); //工作信息
				}
				if(sectionlKey!=null && "baseItemInfo".equals(sectionlKey)){
					applyInputMap.put("baseItemInfo", paramInfo); //私营业主信息
				}
			}
		}
		
		JSONArray contactPersonInfoList = JSONArray.parseArray(map2.get("contactPersonInfo"));//联系人信息列表
		if(null != contactPersonInfoList && contactPersonInfoList.size()!=0){
			applyInputMap.put("contactPersonInfo", contactPersonInfoList);
		}
		
		JSONObject mateInfo = JSONObject.parseObject(map2.get("mateInfo"));//配偶信息
		if(null != mateInfo){
			applyInputMap.put("mateInfo", mateInfo);
		}
		
		JSONArray assetsInfoList = JSONArray.parseArray(map2.get("assetsInfo"));//资产信息
		if(null != assetsInfoList && assetsInfoList.size()!=0){
			for(int j=0;j<assetsInfoList.size();j++){
				JSONObject paramInfo = assetsInfoList.getJSONObject(j);
				String sectionlKey = paramInfo.getString("sectionKey");
				if(sectionlKey!=null && "salaryLoanInfo".equals(sectionlKey)){
					applyInputMap.put("salaryLoanInfo", paramInfo); //随薪贷信息
				}
				if(sectionlKey!=null && "estateInfo".equals(sectionlKey)){
					applyInputMap.put("estateInfo", paramInfo);  //房产信息
				}
				if(sectionlKey!=null && "carInfo".equals(sectionlKey)){
					applyInputMap.put("carInfo", paramInfo);  //车辆信息
				}
				if(sectionlKey!=null && "policyInfo".equals(sectionlKey)){
					applyInputMap.put("policyInfo", paramInfo);  //保单信息
				}
				if(sectionlKey!=null && "providentInfo".equals(sectionlKey)){
					applyInputMap.put("providentInfo", paramInfo);  //公积金信息
				}
				if(sectionlKey!=null && "cardLoanInfo".equals(sectionlKey)){
					applyInputMap.put("cardLoanInfo", paramInfo);  //卡友贷信息
				}
				if(sectionlKey!=null && "masterLoanBInfo".equals(sectionlKey)){
					applyInputMap.put("masterLoanBInfo", paramInfo);  //网购达人贷B信息
				}
				if(sectionlKey!=null && "masterLoanInfo".equals(sectionlKey)){
					applyInputMap.put("masterLoanInfo", paramInfo);  //网购达人贷信息
				}
				if(sectionlKey!=null && "merchantLoanInfo".equals(sectionlKey)){
					applyInputMap.put("merchantLoanInfo", paramInfo);  //淘宝商户贷信息
				}
			}
		}
		
		logger.info("700004提交借款信息："+applyInputMap);
		/**************************改造部分************************/
		SimpleDateFormat df2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
		Date moveTime = tami.getMovedTime();
		String applyDate = "";
		if(null == moveTime){
			applyDate = df2.format(tami.getApplyDate());
		}else{
			applyDate = df2.format(tami.getMovedTime());
		}
		Req_VO_600003 req_VO_600003 = new Req_VO_600003();
		
		req_VO_600003.setSysCode("app");
		req_VO_600003.setUserCode(userCode);
		req_VO_600003.setIsZXBH(isZXBH);
		req_VO_600003.setApplyDate(applyDate);
		req_VO_600003.setAppNo(vo_700004.getAppNo());
		req_VO_600003.setApplyInfoMap(applyInputMap);
		
		Response<Object> response = IAPPExecuter.saveApplyInfo(req_VO_600003);
		String repCode = response.getRepCode();
		boolean flag1 = false;
		JSONObject obj = new JSONObject();
		if(repCode.equals("000000")){
			flag1 = true;
			obj.put("appNo", response.getData());
			obj.put("flag", flag1);
		}
		obj.put("message", response.getRepMsg());
		/**************************改造部分************************/
		
		if(flag1){
			try {
				applyMainInfoService.deleteByAppNo(map);
				applyFieldInfoService.deleteByAppNo(map);
				return FuncResult.success("操作成功", obj);
			} catch (Exception e) {
				
				tami.setNewAppNo(obj.getString("appNo"));
				applyMainInfoService.update(tami);
				throw new PlatformException(ResponseEnum.FULL_MSG,"推送征审成功,图片迁移失败");
			}
		}else{
			String message = obj.getString("message");
			throw new PlatformException(message);
		}
	}

	@FuncIdAnnotate(value = "700005", desc = "取消未提交的申请单信息", voCls = Vo_700005.class,isDependLogin = false)
	public FuncResult cancelTempApplyInputInfo(Vo_700005 vo_700005)
			throws Exception {

		String appNO = vo_700005.getAppNo();
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("appNo", appNO);
		List<TmApplyMainInfo> mains = applyMainInfoService.queryByMap(map);
		if(mains.isEmpty()){
			 throw new PlatformException(ResponseEnum.FULL_MSG,"该申请单号【"+appNO+"】不在未提交状态,或不存在");
		}
		TmApplyMainInfo tami = mains.get(0);
		
		List<TmApplyFieldInfo> fields = applyFieldInfoService.queryByMap(map);
		
		Map<String, String> map2 = new HashMap<String,String>();
		if(null !=fields && !fields.isEmpty()){
			for(TmApplyFieldInfo field:fields){
				String fieldKey = field.getFieldKey();
				String fieldType = field.getFieldType();
				if(fieldType.equals(Constant.ArrType)){
						map2.put(fieldKey, field.getFieldArrValue());
				}else{
						map2.put(fieldKey, field.getFieldObjValue());
				}
			}
		}
		String ifNext = "Y";
		String promptMessages = "";
		String isZXBH = "N";

		String userCode = tami.getUserId();
		String idCardNo = tami.getIdNo();
		String productCd = tami.getProductCode();
		Map<String,Object> applyInputMap = new HashMap<String,Object>();
		
		applyInputMap.put("loanNo", appNO); //借款編號
		applyInputMap.put("refuseReason", vo_700005.getRefuseReason());//拒绝原因
		
		
		JSONObject applyInfo = JSONObject.parseObject(map2.get("applyInfo"));
		if(null !=applyInfo){
			applyInputMap.put("applyInfo", applyInfo); //申请信息
		}
		
		JSONObject persionInfo = JSONObject.parseObject(map2.get("persionInfo"));
		if(null != persionInfo ){
			applyInputMap.put("persionInfo", persionInfo); //个人信息
		}
		
		JSONArray empItemInfoList = JSONArray.parseArray(map2.get("empItemInfo"));//工作信息
		if(null != empItemInfoList && empItemInfoList.size()!=0){
			for(int i=0;i<empItemInfoList.size();i++){
				JSONObject paramInfo = empItemInfoList.getJSONObject(i);
				String sectionlKey = paramInfo.getString("sectionKey");
				if(sectionlKey!=null && "empItemInfo".equals(sectionlKey)){
					applyInputMap.put("empItemInfo", paramInfo); //工作信息
				}
				if(sectionlKey!=null && "baseItemInfo".equals(sectionlKey)){
					applyInputMap.put("baseItemInfo", paramInfo); //私营业主信息
				}
			}
		}
		
		JSONArray contactPersonInfoList = JSONArray.parseArray(map2.get("contactPersonInfo"));//联系人信息列表
		if(null != contactPersonInfoList && contactPersonInfoList.size()!=0){
			applyInputMap.put("contactPersonInfo", contactPersonInfoList);
		}
		
		JSONObject mateInfo = JSONObject.parseObject(map2.get("mateInfo"));//配偶信息
		if(null != mateInfo){
			applyInputMap.put("mateInfo", mateInfo);
		}
		
		JSONArray assetsInfoList = JSONArray.parseArray(map2.get("assetsInfo"));//资产信息
		if(null != assetsInfoList && assetsInfoList.size()!=0){
			for(int j=0;j<assetsInfoList.size();j++){
				JSONObject paramInfo = assetsInfoList.getJSONObject(j);
				String sectionlKey = paramInfo.getString("sectionKey");
				if(sectionlKey!=null && "salaryLoanInfo".equals(sectionlKey)){
					applyInputMap.put("salaryLoanInfo", paramInfo); //随薪贷信息
				}
				if(sectionlKey!=null && "estateInfo".equals(sectionlKey)){
					applyInputMap.put("estateInfo", paramInfo);  //房产信息
				}
				if(sectionlKey!=null && "carInfo".equals(sectionlKey)){
					applyInputMap.put("carInfo", paramInfo);  //车辆信息
				}
				if(sectionlKey!=null && "policyInfo".equals(sectionlKey)){
					applyInputMap.put("policyInfo", paramInfo);  //保单信息
				}
				if(sectionlKey!=null && "providentInfo".equals(sectionlKey)){
					applyInputMap.put("providentInfo", paramInfo);  //公积金信息
				}
				if(sectionlKey!=null && "cardLoanInfo".equals(sectionlKey)){
					applyInputMap.put("cardLoanInfo", paramInfo);  //卡友贷信息
				}
				if(sectionlKey!=null && "masterLoanBInfo".equals(sectionlKey)){
					applyInputMap.put("masterLoanBInfo", paramInfo);  //网购达人贷B信息
				}
				if(sectionlKey!=null && "masterLoanInfo".equals(sectionlKey)){
					applyInputMap.put("masterLoanInfo", paramInfo);  //网购达人贷信息
				}
				if(sectionlKey!=null && "merchantLoanInfo".equals(sectionlKey)){
					applyInputMap.put("merchantLoanInfo", paramInfo);  //淘宝商户贷信息
				}
			}
		}
		Req_VO_600004 req_VO_600004 = new Req_VO_600004();
		SimpleDateFormat df2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
		req_VO_600004.setSysCode("app");
		req_VO_600004.setUserCode(userCode);
		req_VO_600004.setIsZXBH(isZXBH);
		req_VO_600004.setApplyDate(df2.format(tami.getApplyDate()));
		req_VO_600004.setApplyInfoMap(applyInputMap);
		
		Response<Object> response = IAPPExecuter.saveCancelApplyInput(req_VO_600004);
		String repCode = response.getRepCode();
		boolean flag1 = false;
		JSONObject obj = new JSONObject();
		if(repCode.equals("000000")){
			flag1 = true;
			obj.put("appNo", response.getData());
			obj.put("flag", flag1);
		}
		obj.put("message", response.getRepMsg());
		
		/*
		 * 		JSONObject applyInputJson = new JSONObject(applyInputMap);
		 * String result = null;
		JSONObject param = new JSONObject();
		String requestUrl = apsAppProperties.getServiceUrl() + "/App/rpc/apsApp/saveCancelApplyInput";
		param.put("userCode", tami.getUserId());
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
		param.put("applyDateStr", df.format(tami.getApplyDate()));
		param.put("refuseReason", vo_700005.getRefuseReason());
		param.put("applyInputJson", applyInputJson);

		result = httpUrlConnection.postForEntity(requestUrl, param, String.class);
		
		result = URLDecoder.decode(result, "UTF-8");
		
		logger.info("----700005 saveCancelApplyInput 返回: "+result);
		
		JSONObject obj = JSONObject.parseObject(result);*/

		Boolean flag = obj.getBoolean("flag");

		if(flag){
			
			try {
				applyMainInfoService.deleteByAppNo(map);
				applyFieldInfoService.deleteByAppNo(map);
				
				return FuncResult.success("操作成功", obj);
			} catch (Exception e) {
				e.printStackTrace();
				tami.setNewAppNo(obj.getString("appNo"));
				applyMainInfoService.update(tami);
				throw new PlatformException(ResponseEnum.FULL_MSG,"推送征审成功,图片迁移失败");
			}
		}else{
			String message = obj.getString("message");
			throw new PlatformException(message);
		}

	}
	
	@FuncIdAnnotate(value = "900005", desc = "手机重复校验", voCls = Vo_900005.class, isDependLogin = false)
	public FuncResult validateCellphoneRepeat(Vo_900005 vo_900005)throws Exception {
		String appNO = vo_900005.getAppNo();
		logger.info("借款编号【"+vo_900005.getAppNo()+"】手机重复校验");
		
		List<TmApplyFieldInfo> fields = null;
		Map<String,Object> map = new HashMap<>();
		map.put("appNo", appNO);
		try{
			fields = applyFieldInfoService.queryByMap(map);
		}catch(Exception e){}
		
		Map<String, String> map2 = new HashMap<String,String>();
		if(null !=fields && !fields.isEmpty()){
			for(TmApplyFieldInfo field:fields){
				String fieldKey = field.getFieldKey();
				String fieldType = field.getFieldType();
				if(fieldType.equals(Constant.ArrType)){
					map2.put(fieldKey, field.getFieldArrValue());
				}else{
					map2.put(fieldKey, field.getFieldObjValue());
				}
			}
		}
		Map<String,Object> applyInputMap = new HashMap<String,Object>();
		applyInputMap.put("loanNo", appNO); 
		
		logger.info("个人信息："+map2.get("persionInfo"));
		JSONObject persionInfo = JSONObject.parseObject(map2.get("persionInfo"));
		if(null != persionInfo ){
			applyInputMap.put("persionInfo", persionInfo); //个人信息
		}
		
		JSONArray contactPersonInfoList = JSONArray.parseArray(map2.get("contactPersonInfo"));//联系人信息列表
		if(null != contactPersonInfoList && contactPersonInfoList.size() != 0){
			applyInputMap.put("contactPersonInfo", contactPersonInfoList);
		}
		
		JSONObject mateInfo = JSONObject.parseObject(map2.get("mateInfo"));//配偶信息
		if(null != mateInfo){
			applyInputMap.put("mateInfo", mateInfo);
		}
		
		Req_VO_900005 req_VO_900005 = new Req_VO_900005();
		req_VO_900005.setSysCode("app");
		req_VO_900005.setApplyInfoMap(applyInputMap);
		req_VO_900005.setCellphone(vo_900005.getCellphone());
		
		Response<Object> response = IAPPExecuter.validatePhoneRepeat(req_VO_900005);
		return FuncResult.success("校验成功",response.getData());
	}
	
	@FuncIdAnnotate(value = "900006", desc = "身份证有效性校验", voCls = Vo_900006.class, isDependLogin = false)
	public FuncResult validateIdNoeffectiveness(Vo_900006 vo_900006)throws Exception {
		String idNo = vo_900006.getIdNo();
		String appNo = ObjectUtils.toString(vo_900006.getAppNo());
		String flag = vo_900006.getFlag();
		JSONObject obj = new JSONObject();
		String msg1 = flag.equals("1") ? "身份证号码校验不通过，非有效身份证号码！" : "配偶身份证号码校验不通过，非有效身份证号码！";
		
		if(!IDCardValidateUtil.isIdentityCode(idNo)){
			obj.put("validFlag", "1");
			obj.put("validMsg", msg1);
			return FuncResult.success("校验成功",obj);
		}
		
		if(flag.equals("2")){ //校验配偶身份证是否与申请人身份证性别相同
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("appNo", appNo);
			map.put("fieldKey", "applyInfo");
			List<TmApplyFieldInfo> fieldsList = applyFieldInfoService.queryByMap(map);
			JSONObject objTmp = JSONObject.parseObject(fieldsList.get(0).getFieldObjValue());
			String idNoTmp = objTmp.getString("idNo");
			String mateIdNotmp = idNo;
			
			String idNoGender = IDCardValidateUtil.getGenderByIdNo(idNoTmp);
			String mateIdNoGender = IDCardValidateUtil.getGenderByIdNo(mateIdNotmp);
			if(idNoGender.equals(mateIdNoGender)){
				obj.put("validFlag", "1");
				obj.put("validMsg", "客户配偶非外籍人士，配偶性别不能与客户相同！");
				return FuncResult.success("校验成功",obj);
			}
		}
		
		obj.put("validFlag", "0");
		obj.put("validMsg", "");
		return FuncResult.success("校验成功",obj);
	}
}
