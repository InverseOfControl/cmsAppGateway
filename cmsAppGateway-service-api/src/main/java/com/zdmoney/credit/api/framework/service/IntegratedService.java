package com.zdmoney.credit.api.framework.service;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.ObjectUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.ymkj.base.core.biz.api.message.Response;
import com.ymkj.cms.biz.api.service.master.IBMSAppPersonInfoExecuter;
import com.ymkj.cms.biz.api.service.master.IBMSChannelExecuter;
import com.ymkj.cms.biz.api.vo.request.apply.ReqTrialBeforeCreditChannelVO;
import com.ymkj.cms.biz.api.vo.response.apply.ResTrialBeforeCreditChannelVO;
import com.zdmoney.credit.applyinfo.domain.TmApplyMainInfo;
import com.zdmoney.credit.applyinfo.service.pub.ITmApplyFieldInfoService;
import com.zdmoney.credit.applyinfo.service.pub.ITmApplyMainInfoService;
import com.zdmoney.credit.callinter.HttpUrlConnection;
import com.zdmoney.credit.common.annotate.FuncIdAnnotate;
import com.zdmoney.credit.common.exception.PlatformException;
import com.zdmoney.credit.common.exception.ResponseEnum;
import com.zdmoney.credit.common.vo.FuncResult;
import com.zdmoney.credit.config.ApsAppProperties;
import com.zdmoney.credit.config.CoreProperties;
import com.zdmoney.credit.framework.vo.Vo_200001;
import com.zdmoney.credit.framework.vo.Vo_200002;
import com.zdmoney.credit.framework.vo.Vo_900002;
import com.zdmoney.credit.framework.vo.Vo_900003;
import com.zdmoney.credit.framework.vo.Vo_900004;

/**
 * <p>Description:综合服务接口</p>
 * @uthor YM10159
 * @date 2017年6月19日 下午3:57:42
 */
@Service
public class IntegratedService extends BusinessService{
	
	protected static Log logger = LogFactory.getLog(IntegratedService.class);
	public static String PHONE_MATCH="^((13[0-9])|(15[^4,\\D])|(18[0,0-9]))\\d{8}$";

	@Autowired
	ITmApplyMainInfoService applyMainInfoService;
	
	@Autowired
	CoreProperties coreProperties;
	
	@Autowired
	ApsAppProperties apsAppProperties;
	
	@Autowired
	HttpUrlConnection httpUrlConnection;
	
	@Autowired
	IBMSChannelExecuter bmsChannelExecuter;
	
	@Autowired
	ITmApplyFieldInfoService applyFieldInfoService;
	
	@Autowired
	IBMSAppPersonInfoExecuter bmsAppPersonInfoExecuter;

	@FuncIdAnnotate(value = "700008", desc = "查询待认领的申请件", voCls = Vo_900002.class, isDependLogin = false)
	public FuncResult findLeaveEmployeeApp(Vo_900002 vo_900002) throws Exception {

		JSONObject resultObj = new JSONObject();
		DateFormat df = new SimpleDateFormat("yyyy.MM.dd");
		Date nowDate = new Date();
		
		/**调用核心客户经理离职接口接口*/
		JSONObject param = new JSONObject();
		String requestUrl = coreProperties.getServiceUrl() + "/core/findLeaveEmployee/findLeaveEmployee";
		param.put("orgCode", vo_900002.getOrgCode());
		
		String leaveEmployeeStr = httpUrlConnection.postForEntity(requestUrl, param, String.class);
		if(StringUtils.isBlank(leaveEmployeeStr)){
			throw new PlatformException(ResponseEnum.FULL_MSG,"调用核心客户经理离职接口异常！");
		}
		
		JSONObject leaveEmployeeObj = JSONObject.parseObject(leaveEmployeeStr);
		if(!"000000".equals(leaveEmployeeObj.get("resCode"))){
			throw new PlatformException(ResponseEnum.FULL_MSG,"调用核心客户经理离职接口失败！");
		}
		
		List<Map<String,Object>> employeeList = (List<Map<String, Object>>) leaveEmployeeObj.get("employeeList");
		
		//employeeList.add(new HashMap<String,Object>(){{put("userCode", "00210552");}});
		
		if(CollectionUtils.isEmpty(employeeList)){
			return FuncResult.success("查询成功", resultObj);
		}
		
		/**如果有离职的员工，查询离职员工待提交的申请件，让其它客服认领*/
		Map<String,Object> paramsMap = new HashMap<>();
		int pageSize = vo_900002.getPageSize();
		int pageNum = vo_900002.getPageNum();
		paramsMap.put("start", (pageNum-1) * pageSize );
		paramsMap.put("end", (pageNum-1) * pageSize + pageSize);
		paramsMap.put("employeeList", employeeList);
		
		List<TmApplyMainInfo> noSubmitList = applyMainInfoService.getNoSubmitList(paramsMap);
		int count = applyMainInfoService.getNoSubmitListCount(employeeList);
		ArrayList<Map<String, Object>> tempAppInputListData = new ArrayList<Map<String, Object>>();
		
		for (TmApplyMainInfo main : noSubmitList) {
			Map<String, Object> mapInfo = new HashMap<String, Object>();
			String idNo = main.getIdNo();
			String ifNext = main.getIfNext();
			Date lastThreeDay = main.getLastThreeDay();
			Date lastDay = main.getLastDay();
			
			mapInfo.put("appNo", main.getAppNo());
			mapInfo.put("applicantName", main.getUserName());
			mapInfo.put("applyTime", df.format(main.getApplyDate()));
			mapInfo.put("applyAccount", main.getApplyAccount());
			mapInfo.put("productName", main.getProductName());
			mapInfo.put("idCardNoLastFourDigits", idNo.substring(idNo.length()-4,idNo.length()));
			mapInfo.put("applyTerm", main.getApplyTerm());
			
			ifNext = (ifNext == null) ? "Y" : ifNext;
			if("N".equals(ifNext)){
				mapInfo.put("state", "0");
			}else{
				mapInfo.put("state", main.getState());
			}
			
			mapInfo.put("ifNext", "Y");
			mapInfo.put("promptMessages", ObjectUtils.toString(main.getPromptMessages()));
			mapInfo.put("promptZXMessage", ObjectUtils.toString(main.getPromptZXMessage()));
			
			/**申请件超过三个工作日未提交*/
			if(lastThreeDay != null && nowDate.after(lastThreeDay)){
				mapInfo.put("isLastThreeDay", "Y");
			}else{
				mapInfo.put("isLastThreeDay", "N");
			}
			
			/**申请件最后一个工作日未提交*/
			if(lastDay != null && nowDate.after(lastDay)){
				mapInfo.put("isLastDay", "Y");
			}else{
				mapInfo.put("isLastDay", "N");
			}
			
			mapInfo.put("lastSubmitTime", lastDay == null ? "" : df.format(lastDay));
			mapInfo.put("isMoved", ObjectUtils.toString(main.getIsMoved()));
			mapInfo.put("appId", main.getId());
			tempAppInputListData.add(mapInfo);
		}
		
		resultObj.put("tempAppInputListData", tempAppInputListData);
		resultObj.put("totalNo", count);
		resultObj.put("userCode", vo_900002.getOperatorCode());
		return FuncResult.success("查询成功", resultObj);
	}
	
	@FuncIdAnnotate(value = "700009", desc = "认领申请", voCls = Vo_900003.class)
	public FuncResult getFileTypeList(Vo_900003 vo_900003) throws Exception {

		JSONObject resultObj = new JSONObject();
		
		Map<String,Object> paramsMap = new HashMap<>();
		paramsMap.put("userCode", vo_900003.getOperatorCode());
		paramsMap.put("id", vo_900003.getId());
		paramsMap.put("userName", vo_900003.getOperatorName());
		
		int count = applyMainInfoService.claimNoSubmit(paramsMap);
		
		if(count == 0){
			throw new PlatformException(ResponseEnum.FULL_MSG,"认领失败！");
		}
		
		return FuncResult.success("认领成功！", resultObj);
	}
	
	@FuncIdAnnotate(value = "900004", desc = "获取渠道", voCls = Vo_900004.class)
	public FuncResult getChannelByProTermLmt(Vo_900004 vo_900004) throws Exception {

		ReqTrialBeforeCreditChannelVO channelVO = new ReqTrialBeforeCreditChannelVO("app");
		JSONObject obj = new JSONObject();
		
		channelVO.setProductCode(vo_900004.getProductCode());
		channelVO.setApplyLmt(new BigDecimal(vo_900004.getApplyLmt()));
		channelVO.setApplyTerm(Integer.valueOf(vo_900004.getApplyTerm()));
		channelVO.setServiceCode(vo_900004.getOperatorCode());
		channelVO.setServiceName(vo_900004.getOperatorName());
		channelVO.setIfPreferentialUser("N");
		channelVO.setIp("127.0.0.1");
		
		Response<List<ResTrialBeforeCreditChannelVO>> response = bmsChannelExecuter.getChannelByProTermLmt(channelVO);
		
		if(response.getRepCode().equals("000000")){
			List<Map<String,Object>> channelList = new ArrayList<>();
			List<ResTrialBeforeCreditChannelVO> channelVOList = response.getData();
			
			if(CollectionUtils.isEmpty(channelVOList)){
				throw new PlatformException("没有查询到符合条件的渠道！");
			}
			
			for (ResTrialBeforeCreditChannelVO resChannelVO : channelVOList) {
				Map<String,Object> tempMap = new HashMap<>();
				tempMap.put("code", resChannelVO.getCode());
				tempMap.put("name", resChannelVO.getName());
				channelList.add(tempMap);
			}
			obj.put("channelList", channelList);
			return FuncResult.success("渠道查询成功", obj);
		}else{
			throw new PlatformException("获取渠道接口调用失败！");
		}
	}
	
	@FuncIdAnnotate(value = "700007", desc = "未提交借款申请查询", voCls = Vo_200001.class)
	public FuncResult queryUnCmitApply(Vo_200001 vo_200001) throws Exception {
		Map<String,Object> datas=null;
		Map<String,String> paraMap=new HashMap<String,String>();
		String objValue=vo_200001.getObjValue();
		if(StringUtils.isNotEmpty(objValue)){
			if(Pattern.matches(PHONE_MATCH,objValue)){
				paraMap.put("phoneNum",objValue);
			}else{
				paraMap.put("userName",objValue);
			}
		}
		paraMap.put("userId", vo_200001.getUserCode());
		datas=applyFieldInfoService.queryCustomers(paraMap);
		if(datas!=null){
			return FuncResult.success("未提交借款申请客户查询成功!", datas);
		}else{
			return FuncResult.fail("未提交借款申请客户查询失败!", datas);
		}
	}
	
	@FuncIdAnnotate(value="600011",voCls=Vo_200002.class,desc="补录通话详单",isDependLogin=false)
	public FuncResult getPhoneList(Vo_200002 vo_200002) throws Exception{
		int k=0;
		int begNum=(vo_200002.getPageNum()-1)*vo_200002.getPageSize()+1;
		List<Map<String,Object>> datas=new ArrayList<Map<String,Object>>();
		Map<String,String> paramMap=new HashMap<String,String>();
		if(StringUtils.isNotEmpty(vo_200002.getObjValue())){
			if(Pattern.matches(PHONE_MATCH,vo_200002.getObjValue())){
				paramMap.put("phoneNum",vo_200002.getObjValue());
			}else{
				paramMap.put("userName",vo_200002.getObjValue());
			}
		}
		paramMap.put("branchManagerCode",vo_200002.getUserCode());
		Response<List<Map<String, Object>>> responseData=bmsAppPersonInfoExecuter.queryAdditionRecords(paramMap);
		List<Map<String, Object>> dataMap=responseData.getData();

		if(dataMap!=null){
			for (int i = 0; i < dataMap.size(); i++) {
				Map<String,Object> data=dataMap.get(i);
				String name=data.get("NAME")==null?"":String.valueOf(data.get("NAME"));
				String appNo=data.get("LOAN_NO")==null?"":String.valueOf(data.get("LOAN_NO"));
				String phoneNum=data.get("CELLPHONE")==null?"":String.valueOf(data.get("CELLPHONE"));
				String phoneNumSec=data.get("CELLPHONE_SEC")==null?"":String.valueOf(data.get("CELLPHONE_SEC"));
				String phoneStatus=data.get("CELLPHONE_STATUS")==null?null:String.valueOf(data.get("CELLPHONE_STATUS"));
				String phoneStatusSec=data.get("CELLPHONE_SEC_STATUS")==null?null:String.valueOf(data.get("CELLPHONE_SEC_STATUS"));
				String phoneTime=data.get("CELLPHONE_TIME")==null?"":String.valueOf(data.get("CELLPHONE_TIME"));
				String phoneSecTime=data.get("CELLPHONE_SEC_TIME")==null?"":String.valueOf(data.get("CELLPHONE_SEC_TIME"));
				
				if(StringUtils.isNotEmpty(paramMap.get("phoneNum"))){
					if(!paramMap.get("phoneNum").equals(phoneNum)&&!paramMap.get("phoneNum").equals(phoneNumSec)){
						continue;
					}
				}
	
				if(StringUtils.isNotEmpty(phoneNum)){
					k++;
					Map<String,Object> userInfo=new HashMap<String,Object>();
					userInfo.put("appNo",appNo==null?"":appNo);
					String status=applyFieldInfoService.queryPhoneOperator(phoneNum);
					if("3".equals(status)){
						phoneStatus=status;
					}
					
					if(StringUtils.isEmpty(phoneStatus)){
						phoneStatus="2";
					}
					
					userInfo.put("cellphone_status",phoneStatus==null?status:phoneStatus);
					userInfo.put("cellphone_name",name==null?"":name);
					userInfo.put("cellphone", phoneNum);
					if("1".equals(phoneStatus)){
						userInfo.put("cellphone_time",phoneTime==null?"":phoneTime);
					}
					
					if(k>=begNum&&datas.size()<vo_200002.getPageSize()){
						datas.add(userInfo);
					}
					
				}
				
				if(StringUtils.isNotEmpty(phoneNumSec)){
					k++;
					Map<String,Object> userInfoSec=new HashMap<String,Object>();
					userInfoSec.put("appNo",appNo==null?"":appNo);
					String statusSec=applyFieldInfoService.queryPhoneOperator(phoneNumSec);
					userInfoSec.put("cellphone", phoneNumSec);
					userInfoSec.put("cellphone_name",name==null?"":name);
					if("3".equals(statusSec)){
						phoneStatusSec=statusSec;
					}
					
					if(StringUtils.isEmpty(phoneStatusSec)){
						phoneStatusSec="2";
					}
					
					userInfoSec.put("cellphone_status", phoneStatusSec==null?statusSec:phoneStatusSec);
					if("1".equals(phoneStatusSec)){
						userInfoSec.put("cellphone_time",phoneSecTime==null?"":phoneSecTime);
					}
					if(k>=begNum&&datas.size()<vo_200002.getPageSize()){
						datas.add(userInfoSec);
					}
				}
			}
		}
		
		JSONObject json=new JSONObject();
		json.put("total",k);
		json.put("list", datas);
		return  FuncResult.success("查询成功",json);
	}
}
