//package com.zdmoney.credit.api.framework.service.bak;
//
//import java.io.Serializable;
//import java.net.URLDecoder;
//import java.text.DateFormat;
//import java.text.SimpleDateFormat;
//import java.util.ArrayList;
//import java.util.Date;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//import org.apache.commons.lang.StringUtils;
//import org.apache.commons.logging.Log;
//import org.apache.commons.logging.LogFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import com.alibaba.fastjson.JSONArray;
//import com.alibaba.fastjson.JSONObject;
//import com.ymkj.base.core.biz.api.message.Response;
//import com.ymkj.cms.biz.api.service.app.IAPPExecuter;
//import com.ymkj.cms.biz.api.vo.request.app.Req_VO_600003;
//import com.zdmoney.credit.applyinfo.domain.TmApplyFieldInfo;
//import com.zdmoney.credit.applyinfo.domain.TmApplyMainInfo;
//import com.zdmoney.credit.applyinfo.service.pub.ITmApplyFieldInfoService;
//import com.zdmoney.credit.applyinfo.service.pub.ITmApplyMainInfoService;
//import com.zdmoney.credit.callinter.HttpUrlConnection;
//import com.zdmoney.credit.common.annotate.FuncIdAnnotate;
//import com.zdmoney.credit.common.constant.system.Constant;
//import com.zdmoney.credit.common.exception.PlatformException;
//import com.zdmoney.credit.common.exception.ResponseEnum;
//import com.zdmoney.credit.common.vo.FuncResult;
//import com.zdmoney.credit.config.ApsAppProperties;
//import com.zdmoney.credit.config.CreditZxProperties;
//import com.zdmoney.credit.config.PicAppProperties;
////import com.zdmoney.credit.framework.service.BaseServiceImpl;
//import com.zdmoney.credit.framework.vo.Vo_700001;
//import com.zdmoney.credit.framework.vo.Vo_700002;
//import com.zdmoney.credit.framework.vo.Vo_700003;
//import com.zdmoney.credit.framework.vo.Vo_700004;
//import com.zdmoney.credit.framework.vo.Vo_700005;
//
//@Service
//public class PreAPPService extends BusinessService {
//
//	protected static Log logger = LogFactory.getLog(PreAPPService.class);
//
//	@Autowired
//	HttpUrlConnection httpUrlConnection;
//	
//	@Autowired
//	ApsAppProperties apsAppProperties;
//	
//	@Autowired
//	PicAppProperties picAppProperties;
//	
//	@Autowired
//	CreditZxProperties creditZxProperties;
//	
//	@Autowired
//	private ITmApplyMainInfoService applyMainInfoService;
//	@Autowired
//	private ITmApplyFieldInfoService applyFieldInfoService;
//	
//	@Autowired
//	private IAPPExecuter IAPPExecuter;
//
//	@FuncIdAnnotate(value = "700001", desc = "保存未提交申请单的参数信息", voCls = Vo_700001.class)
//	public FuncResult saveTempApplyField(Vo_700001 vo_700001) throws Exception {
//
//		String appNO = vo_700001.getAppNo();
//		Boolean flag = false;
//		Map<String, Object> map = new HashMap<String, Object>();
//		map.put("appNo", appNO);
//		List<TmApplyMainInfo> mains = applyMainInfoService.queryByMap(map);
//		TmApplyMainInfo tmApplyMain = null;
//		if (null != mains && mains.size() !=0) {
//			tmApplyMain = mains.get(0);
//			flag = true;
//		}
//		if (flag) {
//			String fieldKey = vo_700001.getField_key();
//			Map<String, Object> map2 = new HashMap<String, Object>();
//			map2.put("appNo", appNO);
//			map2.put("fieldKey", fieldKey);
//			List<TmApplyFieldInfo> fields = applyFieldInfoService.queryByMap(map2);
//			TmApplyFieldInfo tmApplyFieldInfo = new TmApplyFieldInfo();
//			tmApplyFieldInfo.setAppNo(appNO);
//			tmApplyFieldInfo.setFieldKey(fieldKey);
//			String fieldValue = vo_700001.getField_value()==null?"{}":vo_700001.getField_value();
//			if(fieldValue.startsWith("[")){
//				tmApplyFieldInfo.setFieldArrValue(fieldValue);
//				tmApplyFieldInfo.setFieldType(Constant.ArrType);
//			}else{
//				tmApplyFieldInfo.setFieldObjValue(fieldValue);
//				tmApplyFieldInfo.setFieldType(Constant.ObjType);
//			}
//			tmApplyFieldInfo.setState(vo_700001.getState());
//			
//			if (fields != null && !fields.isEmpty()) {
//				tmApplyFieldInfo.setId(fields.get(0).getId());
//				tmApplyFieldInfo.setUpdateDate(new Date());
//				applyFieldInfoService.update(tmApplyFieldInfo);
//			} else {
//				tmApplyFieldInfo.setCreateDate(new Date());
//				applyFieldInfoService.insert(tmApplyFieldInfo);
//			}
//			
//			Map<String, Object> mapField = new HashMap<String, Object>();
//			mapField.put("appNo", appNO);
//			mapField.put("state", "0");
//			List<TmApplyFieldInfo> fieldList = applyFieldInfoService.queryByMap(mapField);
//			
//			if( fieldList.isEmpty() || fieldList.size() == 0 ){
//				if(null != tmApplyMain){
//					tmApplyMain.setState("1");
//					applyMainInfoService.update(tmApplyMain);
//				}
//			}else{
//				if(null != tmApplyMain && tmApplyMain.getState().equals("1") ){
//					tmApplyMain.setState("0");
//					applyMainInfoService.update(tmApplyMain);
//				}
//			}
//			return FuncResult.success();
//		} else {
//			throw new PlatformException(ResponseEnum.FULL_MSG, "申请单[" + appNO
//					+ "]在网关无记录，不能上传相关信息");
//		}
//	}
//
//	@FuncIdAnnotate(value = "700002", desc = "获取未提交的申请单列表", voCls = Vo_700002.class)
//	public FuncResult getTempApplyInputList(Vo_700002 vo_700002)
//			throws Exception {
//
//		JSONObject retObj = new JSONObject(); // 返回数据封装
//
//		String userCode = vo_700002.getUserCode();
//		retObj.put("userCode", userCode);
//
//		ArrayList<Map<String, Object>> tempAppInputListData = new ArrayList<Map<String, Object>>();
//
//		Map<String, Object> map = new HashMap<String, Object>();
//		map.put("userId", userCode);
//		String idCardNoLastFourDigits = vo_700002.getIdCardNoLastFourDigits()==null?"":vo_700002.getIdCardNoLastFourDigits();
//		map.put("id_no", idCardNoLastFourDigits);
//		String userName = vo_700002.getApplicantName()==null?"":vo_700002.getApplicantName();
//		map.put("user_name", userName);
//		int pageNum = vo_700002.getPageNum();
//		if(pageNum < 1){
//			pageNum = 1;
//		}
//		int pageSize = vo_700002.getPageSize();
//		map.put("start", (pageNum-1) * pageSize );
//		map.put("end", (pageNum-1) * pageSize + pageSize);
//
//		List<TmApplyMainInfo> mainInfos = applyMainInfoService.pageQueryByMap(map);
//		
//		DateFormat df = new SimpleDateFormat("yyyy.MM.dd");
//		if (null != mainInfos && !mainInfos.isEmpty()) {
//			
//			Date nowDate = new Date();
//			
//			for (TmApplyMainInfo main : mainInfos) {
//				Map<String, Object> mapInfo = new HashMap<String, Object>();
//				mapInfo.put("appNo", main.getAppNo());
//				mapInfo.put("applicantName", main.getUserName());
//				String currentTime = df.format(main.getApplyDate());
//				mapInfo.put("applyTime", currentTime);
//				mapInfo.put("applyAccount", main.getApplyAccount());
//				mapInfo.put("productName", main.getProductName());
//				if(StringUtils.isNotEmpty(main.getIdNo())){
//					mapInfo.put("idCardNoLastFourDigits", main.getIdNo().substring(main.getIdNo().length()-4,main.getIdNo().length()));
//				}
//				mapInfo.put("applyTerm", main.getApplyTerm());
//				String ifNext = main.getIfNext() == null ? "Y" : main.getIfNext();
//				if("N".equals(ifNext)){
//					mapInfo.put("state", "0");
//				}else{
//					mapInfo.put("state", main.getState());
//				}
//				
//				mapInfo.put("ifNext", "Y");
//				mapInfo.put("promptMessages", main.getPromptMessages() == null ? "" : main.getPromptMessages());
//				mapInfo.put("promptZXMessage", main.getPromptZXMessage() == null ? "" : main.getPromptZXMessage());
//				
//				Date lastThreeDay = main.getLastThreeDay();
//				if(lastThreeDay!=null && nowDate.after(lastThreeDay)){
//					mapInfo.put("isLastThreeDay", "Y");
//				}else{
//					mapInfo.put("isLastThreeDay", "N");
//				}
//				
//				Date lastDay = main.getLastDay();
//				if(lastDay!=null && nowDate.after(lastDay)){
//					mapInfo.put("isLastDay", "Y");
//				}else{
//					mapInfo.put("isLastDay", "N");
//				}
//				
//				mapInfo.put("lastSubmitTime", lastDay==null?"":df.format(lastDay));
//				
//				tempAppInputListData.add(mapInfo);
//			}
//		}
//		retObj.put("tempAppInputListData", tempAppInputListData);
//
//		int totalNo = applyMainInfoService.countByMap(map);
//		retObj.put("totalNo", totalNo);
//		retObj.put("userCode", userCode);
//
//		return FuncResult.success("查询成功", retObj);
//	}
//
//	@FuncIdAnnotate(value = "700003", desc = "获取未提交的申请单信息", voCls = Vo_700003.class)
//	public FuncResult getTempApplyInputInfo(Vo_700003 vo_700003)
//			throws Exception {
//
//		JSONObject retObj = new JSONObject(); // 返回数据封装
//
//		String appNO = vo_700003.getAppNo();
//		retObj.put("appNo", appNO);
//
//		ArrayList<Map<String, Serializable>> fieldList = new ArrayList<Map<String, Serializable>>();
//		Map<String, Object> map = new HashMap<String, Object>();
//		map.put("appNo", appNO);
//		List<TmApplyFieldInfo> fields = applyFieldInfoService.queryByMap(map);
//		if (null != fields && !fields.isEmpty()) {
//			for (TmApplyFieldInfo field : fields) {
//				Map<String, Serializable> mapField = new HashMap<String, Serializable>();
//				mapField.put("fieldKey", field.getFieldKey());
//				String fieldType = field.getFieldType();
//				mapField.put("fieldType", fieldType);
//				if(fieldType.equals(Constant.ArrType)){
//					mapField.put("fieldArrayValue", JSONArray.parseArray(field.getFieldArrValue()));
//				}else{
//					mapField.put("fieldObjValue", JSONObject.parseObject(field.getFieldObjValue()));
//				}
//				mapField.put("state", field.getState());
//
//				fieldList.add(mapField);
//			}
//		}
//		retObj.put("fieldList", fieldList);
//		
//		JSONObject param = new JSONObject();
//		String requestUrl = picAppProperties.getServiceUrl() + "/uploadFile/uploadFileAction_getFileTypeList";
//		
//		param.put("appNo", appNO);
//		
//		String result = httpUrlConnection.postForEntity(requestUrl, param, String.class);
//		
////		logger.info("----700003 uploadFileAction_getFileTypeList 返回: "+result);
//		
//		result = URLDecoder.decode(result, "UTF-8");
//		JSONObject obj = JSONObject.parseObject(result);
//		
//		if(!obj.containsKey("flag")) {
//			throw new PlatformException(ResponseEnum.FULL_MSG,"图片管理系统返回缺少【flag】Key");
//		}
//		
//		String flag = obj.getString("flag");
//		
//		if(flag==null || "failure".equals(flag)){
//			String message = obj.getString("message");
//			return FuncResult.fail(message);
//		}
//		
//		retObj.put("fileTypeList", obj.getJSONArray("fileTypeList"));
//		
//		return FuncResult.success("查询成功", retObj);
//	}
//
//	@FuncIdAnnotate(value = "700004", desc = "提交申请单到征审系统", voCls = Vo_700004.class)
//	public FuncResult submitTempApplyInputInfo(Vo_700004 vo_700004) throws Exception {
//
//		String appNO = vo_700004.getAppNo();
//
//		Map<String, Object> map = new HashMap<String, Object>();
//		map.put("appNo", appNO);
//		List<TmApplyMainInfo> mains = applyMainInfoService.queryByMap(map);
//		if(mains.isEmpty()){
//			 throw new PlatformException(ResponseEnum.FULL_MSG,"该申请单号【"+appNO+"】不在未提交状态,或不存在");
//		}
//		TmApplyMainInfo tami = mains.get(0);
//		String state = tami.getState();
//		if(state.equals("0")){
//			 throw new PlatformException(ResponseEnum.FULL_MSG,"该申请信息不完整,不能提交");
//		}
//		
//		// 在待提交申请页面，点击编辑时，对身份证和征信做同步校验
//		String ifNext = "Y";
//		String promptMessages = "";
//		String isZXBH = "N";
//
//		String userCode = tami.getUserId();
//		String idCardNo = tami.getIdNo();
//		String productCd = tami.getProductCode();
//		String resultID = null;
//		JSONObject paramID = new JSONObject();
//		String requestUrlID = apsAppProperties.getServiceUrl()
//				+ "/App/rpc/apsApp/checkIDCard";
//
//		paramID.put("idCardNo", idCardNo);
//		paramID.put("productCd", productCd);
//		paramID.put("userCode", userCode);
//		paramID.put("isExists", "Y");
//
//		resultID = httpUrlConnection.postForEntity(requestUrlID,
//				paramID, String.class);
//		resultID = URLDecoder.decode(resultID, "UTF-8");
//		
//		logger.info("----700004 checkIDCard 返回: "+resultID);
//		
//		JSONObject objID = JSONObject.parseObject(resultID);
//
//		ifNext = objID.getString("ifNext");
//		promptMessages = objID.getString("promptMessage");
//		Boolean flag = objID.getBoolean("flag");
//		// 身份证校验成功
//		if (flag) {
//			// 查询是否有征信报告			
//			String requestUrlZX = creditZxProperties.getServiceUrl() + "/creditReport/getReportId";
//			JSONObject paramZX = new JSONObject();
//			paramZX.put("sysCode", "app");
//			paramZX.put("customerName", tami.getUserName());
//			paramZX.put("customerIdCard", tami.getIdNo());
//			DateFormat df=new SimpleDateFormat("yyyy-MM-dd");
//			paramZX.put("queryDate",df.format(new Date()));
//			String date = String.valueOf(System.currentTimeMillis());
//			paramZX.put("timestamp", date);
//			
//			JSONObject paramZXgr = new JSONObject();
//			paramZXgr.put("param", paramZX.toString());
//			String resultZX = httpUrlConnection.postForEntity(requestUrlZX, paramZXgr, String.class);
//
//			logger.info("----700004 getReportId 返回: "+resultZX);
//			
//			JSONObject objZX = JSONObject.parseObject(resultZX);
//			
//			String resCode = objZX.getString("code");
//			
//			if ("000000".equals(resCode)){
//				//APP获取央行征信信用判断结果
//				String reportId = objZX.getString("reportId");
//				
//				String requestUrlZXResult = creditZxProperties.getServiceUrl() + "/creditReport/getCreditInfo";
//				JSONObject paramZXResult = new JSONObject();
//				paramZXResult.put("customerName", tami.getUserName());
//				paramZXResult.put("customerIdCard",  tami.getIdNo());
//				paramZXResult.put("reportId", reportId);
//				paramZXResult.put("queryDate",df.format(new Date()));
//				paramZXResult.put("timestamp", date);
//				
//				JSONObject paramZXgc = new JSONObject();
//				paramZXgc.put("param", paramZXResult.toString());
//				String resultZXResult = httpUrlConnection.postForEntity(requestUrlZXResult, paramZXgc, String.class);
//				
//				logger.info("----700004 getCreditInfo 返回: "+resultZXResult);
//				
//				JSONObject objZXResult = JSONObject.parseObject(resultZXResult);
//				
//				String code = objZXResult.getString("code");
//				if ("000000".equals(code)){
//					String type = objZXResult.getString("type");
//					if ("YES".equals(type)){
//						isZXBH = "Y";
//					}
//				}
//				List<TmApplyFieldInfo> fields = applyFieldInfoService.queryByMap(map);
//				
//				Map<String, String> map2 = new HashMap<String,String>();
//				if(null !=fields && !fields.isEmpty()){
//					for(TmApplyFieldInfo field:fields){
//						String fieldKey = field.getFieldKey();
//						String fieldType = field.getFieldType();
//						if(fieldType.equals(Constant.ArrType)){
//								map2.put(fieldKey, field.getFieldArrValue());
//						}else{
//								map2.put(fieldKey, field.getFieldObjValue());
//						}
//					}
//				}
//				Map<String,Object> applyInputMap = new HashMap<String,Object>();
//				
//				JSONObject applyInfo = JSONObject.parseObject(map2.get("applyInfo"));
//				if(null !=applyInfo){
//					applyInputMap.put("applyInfo", applyInfo); //申请信息
//				}
//				
//				JSONObject persionInfo = JSONObject.parseObject(map2.get("persionInfo"));
//				if(null != persionInfo ){
//					applyInputMap.put("persionInfo", persionInfo); //个人信息
//				}
//				
//				JSONArray empItemInfoList = JSONArray.parseArray(map2.get("empItemInfo"));//工作信息
//				if(null != empItemInfoList && empItemInfoList.size()!=0){
//					for(int i=0;i<empItemInfoList.size();i++){
//						JSONObject paramInfo = empItemInfoList.getJSONObject(i);
//						String sectionlKey = paramInfo.getString("sectionKey");
//						if(sectionlKey!=null && "empItemInfo".equals(sectionlKey)){
//							applyInputMap.put("empItemInfo", paramInfo); //工作信息
//						}
//						if(sectionlKey!=null && "baseItemInfo".equals(sectionlKey)){
//							applyInputMap.put("baseItemInfo", paramInfo); //私营业主信息
//						}
//					}
//				}
//				
//				JSONArray contactPersonInfoList = JSONArray.parseArray(map2.get("contactPersonInfo"));//联系人信息列表
//				if(null != contactPersonInfoList && contactPersonInfoList.size()!=0){
//					applyInputMap.put("contactPersonInfo", contactPersonInfoList);
//				}
//				
//				JSONArray assetsInfoList = JSONArray.parseArray(map2.get("assetsInfo"));//资产信息
//				if(null != assetsInfoList && assetsInfoList.size()!=0){
//					for(int j=0;j<assetsInfoList.size();j++){
//						JSONObject paramInfo = assetsInfoList.getJSONObject(j);
//						String sectionlKey = paramInfo.getString("sectionKey");
//						if(sectionlKey!=null && "salaryLoanInfo".equals(sectionlKey)){
//							applyInputMap.put("salaryLoanInfo", paramInfo); //随薪贷信息
//						}
//						if(sectionlKey!=null && "estateInfo".equals(sectionlKey)){
//							applyInputMap.put("estateInfo", paramInfo);  //房产信息
//						}
//						if(sectionlKey!=null && "carInfo".equals(sectionlKey)){
//							applyInputMap.put("carInfo", paramInfo);  //车辆信息
//						}
//						if(sectionlKey!=null && "policyInfo".equals(sectionlKey)){
//							applyInputMap.put("policyInfo", paramInfo);  //保单信息
//						}
//						if(sectionlKey!=null && "providentInfo".equals(sectionlKey)){
//							applyInputMap.put("providentInfo", paramInfo);  //公积金信息
//						}
//						if(sectionlKey!=null && "cardLoanInfo".equals(sectionlKey)){
//							applyInputMap.put("cardLoanInfo", paramInfo);  //卡友贷信息
//						}
//						if(sectionlKey!=null && "masterLoanBInfo".equals(sectionlKey)){
//							applyInputMap.put("masterLoanBInfo", paramInfo);  //网购达人贷B信息
//						}
//						if(sectionlKey!=null && "masterLoanInfo".equals(sectionlKey)){
//							applyInputMap.put("masterLoanInfo", paramInfo);  //网购达人贷信息
//						}
//						if(sectionlKey!=null && "merchantLoanInfo".equals(sectionlKey)){
//							applyInputMap.put("merchantLoanInfo", paramInfo);  //淘宝商户贷信息
//						}
//					}
//				}
//				
//				JSONObject applyInputJson = new JSONObject(applyInputMap);
//				
//				String result = null;
//				JSONObject param = new JSONObject();
//				String requestUrl = apsAppProperties.getServiceUrl() + "/App/rpc/apsApp/saveApplyInput";
//				param.put("userCode", tami.getUserId());
//				param.put("isZXBH", isZXBH);
//				SimpleDateFormat df2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
//				param.put("applyDateStr", df2.format(tami.getApplyDate()));
//				param.put("applyInputJson", applyInputJson);
//
//				result = httpUrlConnection.postForEntity(requestUrl, param, String.class);
//				
//				result = URLDecoder.decode(result, "UTF-8");
//				
//				logger.info("----700004 saveApplyInput 返回: "+result);
//				
//				JSONObject obj = JSONObject.parseObject(result);
//
//				if(!obj.containsKey("flag")) {
//					throw new PlatformException(ResponseEnum.FULL_MSG,"征审返回缺少【flag】Key");
//				}
//				
//				Boolean flag1 = obj.getBoolean("flag");
//				if(flag1){
//					
//					try {
//						JSONObject paramPic = new JSONObject();
//						String requestUrlPic = picAppProperties.getServiceUrl() + "/uploadFile/uploadFileAction_migrationFileList";
//						
//						paramPic.put("oldAppNo", appNO);
//						paramPic.put("newAppNo", obj.getString("appNo"));
//						paramPic.put("sysName", "aps");
//						
//						String resultPic = httpUrlConnection.postForEntity(requestUrlPic, paramPic, String.class);
//						
//						resultPic = URLDecoder.decode(resultPic, "UTF-8");
//						
//						logger.info("----700004 uploadFileAction_migrationFileList 返回: "+resultPic);
//						
//						JSONObject objPic = JSONObject.parseObject(resultPic);
//						System.out.println(objPic.toString());
//						
//						if(!objPic.containsKey("flag")) {
//							throw new PlatformException(ResponseEnum.FULL_MSG,"图片管理系统返回缺少【flag】Key");
//						}
//						
//						String flagPic = objPic.getString("flag");
//						
//						if(flagPic==null || "failure".equals(flagPic)){
//							String messagePic = objPic.getString("message");
//							return FuncResult.fail(messagePic);
//						}
//						
//						applyMainInfoService.deleteByAppNo(map);
//						applyFieldInfoService.deleteByAppNo(map);
//						
//						return FuncResult.success("查询成功", obj);
//					} catch (Exception e) {
//						
//						tami.setNewAppNo(obj.getString("appNo"));
//						applyMainInfoService.update(tami);
//						throw new PlatformException(ResponseEnum.FULL_MSG,"推送征审成功,图片迁移失败");
//					}
//				}else{
//					String message = obj.getString("message");
//					throw new PlatformException(message);
//				}
//			}else{
//				throw new PlatformException("征信报告id查询不成功");
//			}
//		}else{
//			String message = objID.getString("message");
//			throw new PlatformException(message);
//		}
//		
//		
//	}
//
//	@FuncIdAnnotate(value = "700005", desc = "取消未提交的申请单信息", voCls = Vo_700005.class)
//	public FuncResult cancelTempApplyInputInfo(Vo_700005 vo_700005)
//			throws Exception {
//
//		String appNO = vo_700005.getAppNo();
//
//		Map<String, Object> map = new HashMap<String, Object>();
//		map.put("appNo", appNO);
//		List<TmApplyMainInfo> mains = applyMainInfoService.queryByMap(map);
//		if(mains.isEmpty()){
//			 throw new PlatformException(ResponseEnum.FULL_MSG,"该申请单号【"+appNO+"】不在未提交状态,或不存在");
//		}
//		TmApplyMainInfo tami = mains.get(0);
//		
//		List<TmApplyFieldInfo> fields = applyFieldInfoService.queryByMap(map);
//		
//		Map<String, String> map2 = new HashMap<String,String>();
//		if(null !=fields && !fields.isEmpty()){
//			for(TmApplyFieldInfo field:fields){
//				String fieldKey = field.getFieldKey();
//				String fieldType = field.getFieldType();
//				if(fieldType.equals(Constant.ArrType)){
//						map2.put(fieldKey, field.getFieldArrValue());
//				}else{
//						map2.put(fieldKey, field.getFieldObjValue());
//				}
//			}
//		}
//		Map<String,Object> applyInputMap = new HashMap<String,Object>();
//		
//		JSONObject applyInfo = JSONObject.parseObject(map2.get("applyInfo"));
//		if(null !=applyInfo){
//			applyInputMap.put("applyInfo", applyInfo); //申请信息
//		}
//		
//		JSONObject persionInfo = JSONObject.parseObject(map2.get("persionInfo"));
//		if(null != persionInfo ){
//			applyInputMap.put("persionInfo", persionInfo); //个人信息
//		}
//		
//		JSONArray empItemInfoList = JSONArray.parseArray(map2.get("empItemInfo"));//工作信息
//		if(null != empItemInfoList && empItemInfoList.size()!=0){
//			for(int i=0;i<empItemInfoList.size();i++){
//				JSONObject paramInfo = empItemInfoList.getJSONObject(i);
//				String sectionlKey = paramInfo.getString("sectionKey");
//				if(sectionlKey!=null && "empItemInfo".equals(sectionlKey)){
//					applyInputMap.put("empItemInfo", paramInfo); //工作信息
//				}
//				if(sectionlKey!=null && "baseItemInfo".equals(sectionlKey)){
//					applyInputMap.put("baseItemInfo", paramInfo); //私营业主信息
//				}
//			}
//		}
//		
//		JSONArray contactPersonInfoList = JSONArray.parseArray(map2.get("contactPersonInfo"));//联系人信息列表
//		if(null != contactPersonInfoList && contactPersonInfoList.size()!=0){
//			applyInputMap.put("contactPersonInfo", contactPersonInfoList);
//		}
//		
//		JSONArray assetsInfoList = JSONArray.parseArray(map2.get("assetsInfo"));//资产信息
//		if(null != assetsInfoList && assetsInfoList.size()!=0){
//			for(int j=0;j<assetsInfoList.size();j++){
//				JSONObject paramInfo = assetsInfoList.getJSONObject(j);
//				String sectionlKey = paramInfo.getString("sectionKey");
//				if(sectionlKey!=null && "salaryLoanInfo".equals(sectionlKey)){
//					applyInputMap.put("salaryLoanInfo", paramInfo); //随薪贷信息
//				}
//				if(sectionlKey!=null && "estateInfo".equals(sectionlKey)){
//					applyInputMap.put("estateInfo", paramInfo);  //房产信息
//				}
//				if(sectionlKey!=null && "carInfo".equals(sectionlKey)){
//					applyInputMap.put("carInfo", paramInfo);  //车辆信息
//				}
//				if(sectionlKey!=null && "policyInfo".equals(sectionlKey)){
//					applyInputMap.put("policyInfo", paramInfo);  //保单信息
//				}
//				if(sectionlKey!=null && "providentInfo".equals(sectionlKey)){
//					applyInputMap.put("providentInfo", paramInfo);  //公积金信息
//				}
//				if(sectionlKey!=null && "cardLoanInfo".equals(sectionlKey)){
//					applyInputMap.put("cardLoanInfo", paramInfo);  //卡友贷信息
//				}
//				if(sectionlKey!=null && "masterLoanBInfo".equals(sectionlKey)){
//					applyInputMap.put("masterLoanBInfo", paramInfo);  //网购达人贷B信息
//				}
//				if(sectionlKey!=null && "masterLoanInfo".equals(sectionlKey)){
//					applyInputMap.put("masterLoanInfo", paramInfo);  //网购达人贷信息
//				}
//				if(sectionlKey!=null && "merchantLoanInfo".equals(sectionlKey)){
//					applyInputMap.put("merchantLoanInfo", paramInfo);  //淘宝商户贷信息
//				}
//			}
//		}
//		
//		JSONObject applyInputJson = new JSONObject(applyInputMap);
//		
//		String result = null;
//		JSONObject param = new JSONObject();
//		String requestUrl = apsAppProperties.getServiceUrl() + "/App/rpc/apsApp/saveCancelApplyInput";
//		param.put("userCode", tami.getUserId());
//		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
//		param.put("applyDateStr", df.format(tami.getApplyDate()));
//		param.put("refuseReason", vo_700005.getRefuseReason());
//		param.put("applyInputJson", applyInputJson);
//
//		result = httpUrlConnection.postForEntity(requestUrl, param, String.class);
//		
//		result = URLDecoder.decode(result, "UTF-8");
//		
//		logger.info("----700005 saveCancelApplyInput 返回: "+result);
//		
//		JSONObject obj = JSONObject.parseObject(result);
//
//		if(!obj.containsKey("flag")) {
//			throw new PlatformException(ResponseEnum.FULL_MSG,"征审返回缺少【flag】Key");
//		}
//		Boolean flag = obj.getBoolean("flag");
//
//		if(flag){
//			
//			try {
//				JSONObject paramPic = new JSONObject();
//				String requestUrlPic = picAppProperties.getServiceUrl() + "/uploadFile/uploadFileAction_migrationFileList";
//				
//				paramPic.put("oldAppNo", appNO);
//				paramPic.put("newAppNo", obj.getString("appNo"));
//				paramPic.put("sysName", "aps");
//				
//				String resultPic = httpUrlConnection.postForEntity(requestUrlPic, paramPic, String.class);
//				
//				resultPic = URLDecoder.decode(resultPic, "UTF-8");
//				
//				logger.info("----700005 uploadFileAction_migrationFileList 返回: "+resultPic);
//				
//				JSONObject objPic = JSONObject.parseObject(resultPic);
//				System.out.println(objPic.toString());
//				
//				if(!objPic.containsKey("flag")) {
//					throw new PlatformException(ResponseEnum.FULL_MSG,"图片管理系统返回缺少【flag】Key");
//				}
//				
//				String flagPic = objPic.getString("flag");
//				
//				if(flagPic==null || "failure".equals(flagPic)){
//					String messagePic = objPic.getString("message");
//					return FuncResult.fail(messagePic);
//				}
//				
//				applyMainInfoService.deleteByAppNo(map);
//				applyFieldInfoService.deleteByAppNo(map);
//				
//				return FuncResult.success("查询成功", obj);
//			} catch (Exception e) {
//				e.printStackTrace();
//				tami.setNewAppNo(obj.getString("appNo"));
//				applyMainInfoService.update(tami);
//				throw new PlatformException(ResponseEnum.FULL_MSG,"推送征审成功,图片迁移失败");
//			}
//		}else{
//			String message = obj.getString("message");
//			throw new PlatformException(message);
//		}
//
//	}
//}
