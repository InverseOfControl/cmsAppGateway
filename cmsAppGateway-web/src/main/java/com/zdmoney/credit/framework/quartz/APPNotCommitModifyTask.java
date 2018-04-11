package com.zdmoney.credit.framework.quartz;

import java.net.InetAddress;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.google.gson.Gson;
import com.ymkj.base.core.biz.api.message.Response;
import com.ymkj.cms.biz.api.enums.EnumConstants;
import com.ymkj.cms.biz.api.service.apply.IApplyValidateExecuter;
import com.ymkj.cms.biz.api.service.master.IBMSTmParameterExecuter;
import com.ymkj.cms.biz.api.vo.request.apply.ReqValidateVo;
import com.ymkj.cms.biz.api.vo.request.apply.ValidateNameIdNoVO;
import com.ymkj.cms.biz.api.vo.request.master.ReqBMSTmParameterVO;
import com.ymkj.cms.biz.api.vo.response.master.ResBMSTmParameterVO;
import com.ymkj.rule.biz.api.message.MapResponse;
import com.ymkj.rule.biz.api.message.RuleEngineRequest;
import com.ymkj.rule.biz.api.service.IRuleEngineExecuter;
import com.ymkj.rule.biz.api.vo.ApplyRuleBatchExecVo;
import com.zdmoney.credit.api.framework.service.AutoRefuseService;
import com.zdmoney.credit.applyinfo.domain.TmApplyMainInfo;
import com.zdmoney.credit.applyinfo.service.pub.IAPPNotCommitApplyService;
import com.zdmoney.credit.applyinfo.service.pub.ITmApplyMainInfoService;
import com.zdmoney.credit.common.APPEnumConstants;
import com.zdmoney.credit.framework.vo.Vo_700007;

public class APPNotCommitModifyTask {
	protected static Log logger = LogFactory.getLog(APPNotCommitModifyTask.class);
	@Autowired
	private IRuleEngineExecuter iRuleEngineExecuter;
	@Autowired
	private IAPPNotCommitApplyService iAPPNotCommitApplyService;
	@Autowired
	private IApplyValidateExecuter applyValidateExecuter;
	@Autowired
	private  AutoRefuseService autoRefuseService;
	@Autowired
	private IBMSTmParameterExecuter tmParameterExecuter;
	
	// json 工具类
	private Gson gson = new Gson();

	@SuppressWarnings("unchecked")
	public void execute() {
		logger.info("--------处于APP，未提交到录入修改队列的借款定时推送益百利定时任务开始执行----------");
		boolean isException = false;
		long addCount = 0;// 更新条数
		int count = 0; // 用来记录查询到的数据总和
		Map<String, Object> map = null;
		List<String> appNoEliminateList = new ArrayList<String>();

		try {
			/**
			 * 查询app未提交申请的申请件信息
			 */
			map = new HashMap<String, Object>();
			//新展业标识，借款编号为14位
			map.put("appNoLength",14);
			
			count = iAPPNotCommitApplyService.queryNotCommitApplyAppNoCount(map);
			if(count == 0){
				logger.info("成功：没有处于APP，未提交到录入修改队列的借款，日终跑批推送益百利的单子");
				return;
			}
			//进行分页处理
			ReqBMSTmParameterVO reqDemoVO = new ReqBMSTmParameterVO();
			reqDemoVO.setSysCode(APPEnumConstants.GATEWAY_SYSCODE);
			
			Integer splitNum = 10;
			reqDemoVO.setCode("LOAN_JOB_EXECUTER_SPLITNUM");
			//配置参数查找
			Response<ResBMSTmParameterVO> splitNumResponse = tmParameterExecuter.findOneByParam(reqDemoVO);
			if(splitNumResponse.isSuccess()){
				ResBMSTmParameterVO splitNumResVO= splitNumResponse.getData();
				if(splitNumResVO != null && splitNumResVO.getParameterValue() != null ){
					splitNum = Integer.valueOf(splitNumResVO.getParameterValue());
				}
			}
			
			Integer querySize = 100;
			reqDemoVO.setCode("LOAN_JOB_EXECUTER_QUERYSIZE");
			//配置参数查找
			Response<ResBMSTmParameterVO> querySizeResponse = tmParameterExecuter.findOneByParam(reqDemoVO);
			if(splitNumResponse.isSuccess()){
				ResBMSTmParameterVO querySizeResVO= querySizeResponse.getData();
				if(querySizeResVO != null && querySizeResVO.getParameterValue() != null ){
					querySize = Integer.valueOf(querySizeResVO.getParameterValue());
				}
			}
			
			Integer time = count / querySize + 1;
			
			map.put("limitMin",1);
			map.put("limitMax",querySize);
			map.put("appNoEliminateList",appNoEliminateList);
			
			for (int i = 0; i < time; i++) {
				List<TmApplyMainInfo> newRdAppNos = iAPPNotCommitApplyService.queryNotCommitApplyAppNo(map);
				List<List<TmApplyMainInfo>> result = splitList(newRdAppNos,splitNum);

				for (List<TmApplyMainInfo> listVo : result) {
					List<ApplyRuleBatchExecVo> validateList = new ArrayList<ApplyRuleBatchExecVo>();

					for (TmApplyMainInfo tmApplyMainInfo : newRdAppNos) {
						
						appNoEliminateList.add(tmApplyMainInfo.getAppNo());

						// 提取数据
						ValidateNameIdNoVO validateVo = new ValidateNameIdNoVO();
						validateVo.setSysCode(APPEnumConstants.GATEWAY_SYSCODE);

						validateVo.setName(tmApplyMainInfo.getUserName());
						validateVo.setIdNo(tmApplyMainInfo.getIdNo());
						validateVo.setProductCode(tmApplyMainInfo.getProductCode());
						// 获取需要传给“益百利”数据
						Response<ReqValidateVo> resVo = applyValidateExecuter.validateNameIdNo(validateVo);
						if (resVo.isSuccess()) {
							ReqValidateVo demoEntity = resVo.getData();
							ApplyRuleBatchExecVo ruleVO = new ApplyRuleBatchExecVo();
							BeanUtils.copyProperties(demoEntity, ruleVO);

//								添加特定参数
							ruleVO.setAppApplyInput("Y");
							ruleVO.setApplyDate(tmApplyMainInfo.getApplyDate());
							if(tmApplyMainInfo.getMovedTime() == null){
								ruleVO.setAppServiceClaimDate(strToDate("99991231", "yyyyMMdd"));
							} else {
								ruleVO.setAppServiceClaimDate(tmApplyMainInfo.getMovedTime());
							}
							ruleVO.setFirstInModifyDate(strToDate("99991231", "yyyyMMdd"));
							ruleVO.setLoanNo(tmApplyMainInfo.getAppNo());
//								app-SQXX	App待提交列表
							ruleVO.setPreviousRtfState("app-SQXX");
							ruleVO.setRtfState("app-SQXX");
							ruleVO.setExecuteType("LDAPP005");
							validateList.add(ruleVO);
						} else {
							logger.info("-------调BMS规则数据查询接口失败");
						}
					}
					/**
					 * 调益百利接口
					 */
					@SuppressWarnings("rawtypes")
					RuleEngineRequest ruleRequest = new RuleEngineRequest();
					ruleRequest.setBizType("loanApplyBatch");
					ruleRequest.setSysCode(APPEnumConstants.GATEWAY_SYSCODE);
					ruleRequest.setData(validateList);
					
					logger.info("-------------结果: " + gson.toJson(ruleRequest));
					
					com.ymkj.rule.biz.api.message.Response resRule = iRuleEngineExecuter.executeRuleEngine(ruleRequest);
					
					if (EnumConstants.RES_CODE_SUCCESS.equals(resRule.getRepCode())) {
						MapResponse response_ = (MapResponse) resRule;
						List<Map<String, Object>> ruleMapList = response_.getMapList();

						for (Map<String, Object> ruleMap : ruleMapList) {
							System.out.println("-------------结果: " + gson.toJson(ruleMap));

							if (APPEnumConstants.JOB_JJ.equals(ruleMap.get("action"))) {
								// 如果请求成功并且响应信息为标识则更新app主表申请件为黄色
								Vo_700007 vo_700007 = new Vo_700007();
								if(ruleMap.get("loanNo") !=null){
									vo_700007.setAppNo(ruleMap.get("loanNo").toString());
								}
								if(ruleMap.get("actionCode") != null){
									vo_700007.setRefuseReason(ruleMap.get("actionCode").toString());
								}
								InetAddress ia=null;
						        try {
						            ia=ia.getLocalHost();
						             
						            String localip=ia.getHostAddress();
						            vo_700007.setIp(localip);
						        } catch (Exception e) {
						            e.printStackTrace();
						        }
								
								autoRefuseService.refuseTempApplyInputInfo(vo_700007);
								addCount++;
							}
						}
					}
				}
			}
			
		} catch (Exception e) {
			isException = true;
			logger.info("--------处于APP，未提交到录入修改队列的借款定时推送益百利定时任务执行异常保存到Job日志中----------");
		} finally {
			logger.info("--------处于APP，未提交到录入修改队列的借款定时推送益百利定时任务执行记录保存到Job日志中----------");
			logger.info("--------isException----------" + isException);
			logger.info("--------处于APP，未提交到录入修改队列的借款定时推送益百利定时任务执行总条数----------" + count);
			logger.info("--------处于APP，未提交到录入修改队列的借款定时推送益百利定时任务执行保存成功条数----------" + addCount);
		}
	}
	private List<List<TmApplyMainInfo>>  splitList(List<TmApplyMainInfo> targe,int size) {  
        List<List<TmApplyMainInfo>> listArr = new ArrayList<List<TmApplyMainInfo>>();  
        //获取被拆分的数组个数  
        int arrSize = targe.size()%size==0?targe.size()/size:targe.size()/size+1;  
        for(int i=0;i<arrSize;i++) {  
            List<TmApplyMainInfo>  sub = new ArrayList<TmApplyMainInfo>();  
            //把指定索引数据放入到list中  
            for(int j=i*size;j<=size*(i+1)-1;j++) {  
                if(j<=targe.size()-1) {  
                    sub.add(targe.get(j));  
                }  
            }  
            listArr.add(sub);  
        }  
        return listArr;  
    }
	/**
	 * 字符串转换成日期
	 * 
	 * @param str
	 * @return date
	 * @return formatStr
	 * @throws ParseException
	 */
	public static Date strToDate(String str, String formatStr) throws ParseException {
		if (formatStr == null) {
			formatStr = "yyyy-MM-dd";
		}
		SimpleDateFormat format = new SimpleDateFormat(formatStr);
		return format.parse(str);
	}
}
