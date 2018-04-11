package com.zdmoney.credit.api.framework.service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.zdmoney.credit.applyinfo.domain.TmApplyMainInfo;
import com.zdmoney.credit.applyinfo.service.pub.ITmApplyMainInfoService;
import com.zdmoney.credit.callinter.HttpUrlConnection;
import com.zdmoney.credit.common.annotate.FuncIdAnnotate;
import com.zdmoney.credit.common.exception.PlatformException;
import com.zdmoney.credit.common.exception.ResponseEnum;
import com.zdmoney.credit.common.util.Dates;
import com.zdmoney.credit.common.util.Strings;
import com.zdmoney.credit.common.vo.FuncResult;
import com.zdmoney.credit.config.CreditZxProperties;
import com.zdmoney.credit.framework.vo.Vo_500001;
import com.zdmoney.credit.framework.vo.Vo_500002;
import com.zdmoney.credit.framework.vo.Vo_500004;
import com.zdmoney.credit.framework.vo.Vo_500005;
import com.zdmoney.credit.framework.vo.Vo_500006;
import com.zdmoney.credit.framework.vo.Vo_500007;
@Service
public class CreditZxService extends BusinessService {

	protected static Log logger = LogFactory.getLog(CreditZxService.class);

	@Autowired
	CreditZxProperties creditZxProperties;

	@Autowired
	HttpUrlConnection httpUrlConnection;
	
	@Autowired
	private ITmApplyMainInfoService applyMainInfoService;
	
	/**
	 * 征信报告录入接口
	 * 
	 * @param vo_500001
	 * @return
	 */
	@FuncIdAnnotate(value = "500001", desc = "征信报告录入", voCls = Vo_500001.class)
	public FuncResult saveReport(Vo_500001 vo_500001) {

		String name = vo_500001.getName();
		String idCard = vo_500001.getIdCard();
		String htmlContent = vo_500001.getHtmlContent();
		String userCode = vo_500001.getUserCode();

		String result = null;
		JSONObject param = new JSONObject();
		String requestUrl = creditZxProperties.getServiceUrl() + "/pbccrc/saveReport";
		param.put("customerName", name);
		param.put("customerIdCard", idCard);
		param.put("htmlContent", htmlContent);
		param.put("creatorId", userCode);
		result = httpUrlConnection.postForEntity(requestUrl, param, String.class);

		JSONObject obj = JSONObject.parseObject(result);
		logger.info("征信报告录入.返回结果" + obj.toJSONString());

		String resCode = "";
		FuncResult funcResult = null;
		if (obj.containsKey("resCode")) {
			resCode = obj.getString("resCode");
			if ("000000".equals(resCode)) {
				funcResult = FuncResult.success("保存成功", obj);
			} else {
				funcResult = FuncResult.fail(Strings.convertValue(obj.getString("resMsg"), String.class));
			}
		} else {
			funcResult = FuncResult.fail("三方返回缺少【resCode】Key");
		}
		return funcResult;
	}

	/**
	 * 征信报告查询接口
	 * 
	 * @param vo_500002
	 * @return
	 */
	@FuncIdAnnotate(value = "500002", desc = "征信报告查询", voCls = Vo_500002.class)
	public FuncResult getReportHtmlContent(Vo_500002 vo_500002) {

		String name = vo_500002.getName();
		String idCard = vo_500002.getIdCard();
		String result = null;
		JSONObject param = new JSONObject();
		String requestUrl = creditZxProperties.getServiceUrl() + "/pbccrc/getReportHtmlContent";
		param.put("customerName", name);
		param.put("customerIdCard", idCard);
		result = httpUrlConnection.postForEntity(requestUrl, param, String.class);

		JSONObject obj = JSONObject.parseObject(result);
		String resCode = "";
		FuncResult funcResult = null;
		if (obj.containsKey("resCode")) {
			resCode = obj.getString("resCode");
			if ("000000".equals(resCode)) {
				/** 有征信报告 **/
				obj.getJSONObject("attachment").put("hasReport", true);
				funcResult = FuncResult.success("查询成功", obj.getJSONObject("attachment"));
			} else {
				/** 无征信报告 **/
				JSONObject jsonObj = new JSONObject();
				jsonObj.put("hasReport", false);
				funcResult = FuncResult.success("", jsonObj);
			}
		} else {
			funcResult = FuncResult.fail("三方返回缺少【resCode】Key");
		}
		return funcResult;
	}
	
	/**
	 * 
	 * 客户历史信息状态查询
	 * @param vo_500003
	 * @return
	 */
	@FuncIdAnnotate(value = "500004", desc = "客户历史信息状态查询", voCls = Vo_500004.class)
	public FuncResult getReportHtmlContent(Vo_500004 vo_500004) {

		String name = vo_500004.getName();
		String idCard = vo_500004.getIdCard();
		String userCode = vo_500004.getUserCode();
		int pagerNum = vo_500004.getPagerNum();
		int pagerMax = vo_500004.getPagerMax();
		if(StringUtils.isNotEmpty(idCard) && idCard.indexOf("x")>0){
			idCard = idCard.replace("x", "X");
		}
		String result = null;
		JSONObject param = new JSONObject();
		String requestUrl = creditZxProperties.getServiceUrl() + "/customerHistory/customerHistoryPage";
		param.put("customerName", name);
		param.put("customerIdCard", idCard);
		param.put("userCode", userCode);
		param.put("pagerNum", pagerNum);
		param.put("pagerMax", pagerMax);
		
		result = httpUrlConnection.postForEntity(requestUrl, param, String.class);

		JSONObject obj = JSONObject.parseObject(result);
		String resCode = "";
		FuncResult funcResult = null;
		if (obj.containsKey("resCode")) {
			resCode = obj.getString("resCode");
			if ("000000".equals(resCode)) {
				obj.getJSONObject("attachment").put("hasData", true);
				funcResult = FuncResult.success("查询成功", obj.getJSONObject("attachment"));
			} else {
				JSONObject jsonObj = new JSONObject();
				jsonObj.put("hasData", false);
				funcResult = FuncResult.success("", jsonObj);
			}
		} else {
			funcResult = FuncResult.fail("三方返回缺少【resCode】Key");
		}
		return funcResult;
	}
	
	/**
	 * 
	 * APP获取央行征信信用判断结果
	 * @param vo_500005
	 * @return
	 */
	@FuncIdAnnotate(value = "500005", desc = "APP获取央行征信信用判断结果", voCls = Vo_500005.class)
	public FuncResult getCreditInfo(Vo_500005 vo_500005) {

		String name = vo_500005.getCustomerName();
		String idCard = vo_500005.getCustomerIdCard();
		String userCode = vo_500005.getUserCode();
		String reportId = vo_500005.getReportId();
		int pagerNum = vo_500005.getPagerNum();
		int pagerMax = vo_500005.getPagerMax();
		if(StringUtils.isNotEmpty(idCard) && idCard.indexOf("x")>0){
			idCard = idCard.replace("x", "X");
		}
		Date date = new Date(); 
		String result = null;
		JSONObject param = new JSONObject();
		String requestUrl = creditZxProperties.getServiceUrl() + "/creditReport/getCreditInfo";
		param.put("customerName", name);
		param.put("customerIdCard", idCard);
		param.put("userCode", userCode);
		param.put("reportId", reportId);
		param.put("timestamp", date);
		param.put("queryDate", Dates.getDateTime(date, Dates.DEFAULT_DATE_FORMAT));
		param.put("pagerNum", pagerNum);
		param.put("pagerMax", pagerMax);
		JSONObject json = new JSONObject();
		json.put("param", param.toJSONString());
		result = httpUrlConnection.postForEntity(requestUrl, json, String.class);
		
		JSONObject obj = JSONObject.parseObject(result);
		FuncResult funcResult = null;
		
		logger.info("APP查询央行信用判断结果成功.返回结果" + obj.toJSONString());
		
		if (obj.containsKey("code")) {
			logger.info("APP查询央行信用判断结果成功.返回结果" + obj.toJSONString());
			//查询征信，返回是否可以下一步和提示信息
			String ifNext = "Y";
			String promptZXMessage = "";
			String code = obj.getString("code");
			if ("000000".equals(code)){
				String type = obj.getString("type");
				if ("YES".equals(type)){
//					promptZXMessage = "初判客户征信白户，除保单贷、学历贷、网购达人贷A和网购达人贷B、结清再贷客户外均不允许提交，请检查客户贷款类型";
					promptZXMessage = "初判客户征信白户";
				}
				
				String status = obj.getString("status");
				//信用不良
				if ("NO".equals(status)){
					String data = obj.getString("data");
					//逾期
					if (data != null){
						promptZXMessage = data;
					}else{
						promptZXMessage = "初判客户信用不良";
					}
				}
			}
			
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("appNo", vo_500005.getAppNo());
			List<TmApplyMainInfo> mains = applyMainInfoService.queryByMap(map);
			if(mains.isEmpty()){
				 throw new PlatformException(ResponseEnum.FULL_MSG,"该申请单号【"+vo_500005.getAppNo()+"】不在未提交状态,或不存在");
			}
			TmApplyMainInfo tami = mains.get(0);
			tami.setHasCreditReport("1");
//			tami.setIfNext(ifNext);
//			tami.setPromptZXMessage(promptZXMessage);
			applyMainInfoService.update(tami);
			
			obj.put("hasData", true);
			obj.put("ifNext", ifNext);
			obj.put("promptZXMessage", promptZXMessage);
			
			funcResult = FuncResult.success("查询成功", obj);
		} else {
			funcResult = FuncResult.fail("三方返回缺少【code】Key");
		}
		return funcResult;
	}
	
	
	@FuncIdAnnotate(value = "500006", desc = "APP用户报表上传接口", voCls = Vo_500006.class, isDependLogin = false)
  public FuncResult uploadReport(Vo_500006 vo_500006) throws Exception {

	  String requestUrl = creditZxProperties.getServiceUrl() + "/upLoadReport";
    JSONObject param = new JSONObject();
    param.put("mobile", vo_500006.getMobile());
    param.put("fileName",vo_500006.getFileName());
    param.put("fileContent", vo_500006.getFileContent());
    param.put("staffName", vo_500006.getStaffName());
    param.put("nodeKey", "input-modify");
    String result = httpUrlConnection.postForEntity(requestUrl, param, String.class);
    JSONObject obj = JSONObject.parseObject(result);
    return FuncResult.success(obj);
  }
	
	 @FuncIdAnnotate(value = "500007", desc = "APP用户报表查询下载接口", voCls = Vo_500007.class, isDependLogin = false)
	  public FuncResult queryReport(Vo_500007 vo_500007) throws Exception {
	    String requestUrl = creditZxProperties.getServiceUrl() + "/queryReport";
	    JSONObject param = new JSONObject();
	    param.put("mobile", vo_500007.getMobile());
	    param.put("transferType", vo_500007.getTransferType());
	    param.put("localPath", vo_500007.getLocalPath());
	    param.put("startTime", vo_500007.getStartTime());
	    param.put("endTime", vo_500007.getEndTime());  
	    param.put("callType", vo_500007.getCallType());
	    param.put("isAnswer", vo_500007.getIsAnswer());
	    String result = httpUrlConnection.postForEntity(requestUrl, param, String.class);
	    JSONObject obj = JSONObject.parseObject(result);
	    return FuncResult.success(obj);
	  }
}
