package com.zdmoney.credit.api.framework.service;

import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ymkj.base.core.biz.api.message.Response;
import com.ymkj.cms.biz.api.service.app.IAPPExecuter;
import com.ymkj.cms.biz.api.vo.request.app.Req_VO_600004;
import com.zdmoney.credit.applyinfo.domain.TmApplyFieldInfo;
import com.zdmoney.credit.applyinfo.domain.TmApplyMainInfo;
import com.zdmoney.credit.applyinfo.service.pub.ITmApplyFieldInfoService;
import com.zdmoney.credit.applyinfo.service.pub.ITmApplyMainInfoService;
import com.zdmoney.credit.callinter.HttpUrlConnection;
import com.zdmoney.credit.common.constant.system.Constant;
import com.zdmoney.credit.common.exception.PlatformException;
import com.zdmoney.credit.common.exception.ResponseEnum;
import com.zdmoney.credit.common.vo.FuncResult;
import com.zdmoney.credit.config.ApsAppProperties;
import com.zdmoney.credit.config.CreditZxProperties;
import com.zdmoney.credit.config.PicAppProperties;
import com.zdmoney.credit.framework.vo.Vo_700005;
import com.zdmoney.credit.framework.vo.Vo_700007;

@Service
public class AutoRefuseService extends BusinessService{
	protected static Log logger = LogFactory.getLog(AutoRefuseService.class);

	@Autowired
	HttpUrlConnection httpUrlConnection;
	
	@Autowired
	ApsAppProperties apsAppProperties;
	
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
	
	public FuncResult refuseTempApplyInputInfo(Vo_700007 vo_700007)
			throws Exception {

		String appNO = vo_700007.getAppNo();
		
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
		
		applyInputMap.put("refuseReason", vo_700007.getRefuseReason());//拒绝原因
		
		applyInputMap.put("loanNo", appNO); //借款編號
		
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
		req_VO_600004.setIp(vo_700007.getIp());
		
		Response<Object> response = IAPPExecuter.saveRefuseApplyInput(req_VO_600004);
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
}
