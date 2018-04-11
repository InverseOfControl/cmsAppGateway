//package com.zdmoney.credit.api.framework.service.bak;
//
//import java.net.URLDecoder;
//import java.text.DateFormat;
//import java.text.SimpleDateFormat;
//import java.util.Date;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//import org.apache.commons.logging.Log;
//import org.apache.commons.logging.LogFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import com.alibaba.fastjson.JSONArray;
//import com.alibaba.fastjson.JSONObject;
//import com.zdmoney.credit.applyinfo.domain.TmApplyFieldInfo;
//import com.zdmoney.credit.applyinfo.domain.TmApplyMainInfo;
//import com.zdmoney.credit.applyinfo.service.pub.ITmApplyFieldInfoService;
//import com.zdmoney.credit.applyinfo.service.pub.ITmApplyMainInfoService;
//import com.zdmoney.credit.callinter.HttpUrlConnection;
//import com.zdmoney.credit.common.constant.system.Constant;
//import com.zdmoney.credit.config.ApsAppProperties;
//import com.zdmoney.credit.config.PicAppProperties;
//
//@Service
//public class AutoCancelService extends BusinessService{
//	
//	protected static Log logger = LogFactory.getLog(AutoCancelService.class);
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
//	private ITmApplyMainInfoService applyMainInfoService;
//	
//	@Autowired
//	private ITmApplyFieldInfoService applyFieldInfoService;
//	
//	public void dispatch() {
//		
//		logger.info("-------- 系统自动取消 定时任务    开始 ----------------");
//		
//		try{
//		
//			Map<String, Object> paramMap = new HashMap<String, Object>();
//			
//			List<TmApplyMainInfo> tamiList = applyMainInfoService.queryAPP1(paramMap);
//			
//			if(tamiList!=null && tamiList.size()>0){
//				
//				DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//				
//				for(TmApplyMainInfo tami : tamiList){
//					
//					Date applyDate = tami.getApplyDate()==null?(new Date()):tami.getApplyDate();
//					
//					JSONObject param = new JSONObject();
//					String requestUrl = apsAppProperties.getServiceUrl() + "/App/rpc/apsApp/getAPPWorkDate";
//					param.put("paramDateStr", df.format(applyDate));
//	
//					String result = httpUrlConnection.postForEntity(requestUrl, param, String.class);
//	
//					result = URLDecoder.decode(result, "UTF-8");
//					
//					JSONObject obj = JSONObject.parseObject(result);
//					
//					if(obj.containsKey("flag") && obj.getBoolean("flag")){
//					
//						Date lastThreeDay = df.parse(obj.getString("lastThreeDay")+" 00:00:00");
//						Date lastDay = df.parse(obj.getString("lastDay")+" 00:00:00");
//						Date lastSubmitTime = df.parse(obj.getString("lastSubmitTime")+" 00:00:00");
//						
//						tami.setLastThreeDay(lastThreeDay);
//						tami.setLastDay(lastDay);
//						tami.setLastSubmitTime(lastSubmitTime);
//						
//						applyMainInfoService.updateMainTime(tami);
//					}
//				}
//				
//			}
//		
//			List<TmApplyMainInfo> tamiList2 = applyMainInfoService.queryAPP3(paramMap);
//			
//			if(tamiList2!=null && tamiList2.size()>0){
//				
//				for(TmApplyMainInfo tami2 : tamiList2){
//					cancelTempApplyInputInfo(tami2.getAppNo());
//				}
//			}
//		
//		}catch(Exception e){
//			e.printStackTrace();
//			logger.error("-------- 系统自动取消 定时任务   报错 ----------------");
//		}
//		logger.info("-------- 系统自动取消 定时任务    结束 ----------------");
//	}
//	
//	public void cancelTempApplyInputInfo(String appNO) throws Exception {
//
//		Map<String, Object> map = new HashMap<String, Object>();
//		map.put("appNo", appNO);
//		List<TmApplyMainInfo> mains = applyMainInfoService.queryByMap(map);
//		if(mains.isEmpty()){
//			 logger.error("-------- 该申请单号【"+appNO+"】不在未提交状态,或不存在");
//			 return;
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
//		param.put("refuseReason", "00087");
//		param.put("applyInputJson", applyInputJson);
//
//		result = httpUrlConnection.postForEntity(requestUrl, param, String.class);
//		
//		result = URLDecoder.decode(result, "UTF-8");
//		
//		JSONObject obj = JSONObject.parseObject(result);
//
//		if(!obj.containsKey("flag")) {
//			logger.error("-------- 该申请单号【"+appNO+"】调用征审，征审返回缺少【flag】Key");
//			return;
//		}
//		
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
//				JSONObject objPic = JSONObject.parseObject(resultPic);
//				System.out.println(objPic.toString());
//				
//				if(!objPic.containsKey("flag")) {
//					logger.error("-------- 该申请单号【"+appNO+"】调用图片管理系统，图片管理系统返回缺少【flag】Key");
//					return;
//				}
//				
//				String flagPic = objPic.getString("flag");
//				
//				if(flagPic==null || "failure".equals(flagPic)){
//					String messagePic = objPic.getString("message");
//					logger.error("-------- 该申请单号【"+appNO+"】调用图片管理系统，图片管理系统返回:"+messagePic);
//					return;
//				}
//				
//				applyMainInfoService.deleteByAppNo(map);
//				applyFieldInfoService.deleteByAppNo(map);
//				
//			} catch (Exception e) {
//				e.printStackTrace();
//				tami.setNewAppNo(obj.getString("appNo"));
//				applyMainInfoService.update(tami);
//				logger.error("-------- 该申请单号【"+appNO+"】推送征审成功,图片迁移失败");
//			}
//		}else{
//			String message = obj.getString("message");
//			logger.error("-------- 该申请单号【"+appNO+"】,发生未知异常:"+message);
//		}
//
//	}
//	
//}
