package com.zdmoney.credit.api.framework.service.bak;
//package com.zdmoney.credit.api.framework.service;
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
//import org.apache.commons.logging.Log;
//import org.apache.commons.logging.LogFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//import org.springframework.web.client.RestTemplate;
//
//import com.alibaba.fastjson.JSONArray;
//import com.alibaba.fastjson.JSONObject;
//import com.zdmoney.credit.applyinfo.domain.TmApplyMainInfo;
//import com.zdmoney.credit.applyinfo.service.pub.ITmApplyFieldInfoService;
//import com.zdmoney.credit.applyinfo.service.pub.ITmApplyMainInfoService;
//import com.zdmoney.credit.callinter.HttpUrlConnection;
//import com.zdmoney.credit.common.annotate.FuncIdAnnotate;
//import com.zdmoney.credit.common.exception.PlatformException;
//import com.zdmoney.credit.common.exception.ResponseEnum;
//import com.zdmoney.credit.common.vo.FuncResult;
//import com.zdmoney.credit.config.ApsAppProperties;
//import com.zdmoney.credit.config.CoreProperties;
//import com.zdmoney.credit.config.CreditZxProperties;
//import com.zdmoney.credit.config.PicAppProperties;
//import com.zdmoney.credit.framework.vo.Vo_600001;
//import com.zdmoney.credit.framework.vo.Vo_600002;
//import com.zdmoney.credit.framework.vo.Vo_600003;
//import com.zdmoney.credit.framework.vo.Vo_600004;
//import com.zdmoney.credit.framework.vo.Vo_600005;
//import com.zdmoney.credit.framework.vo.Vo_600006;
//import com.zdmoney.credit.framework.vo.Vo_600007;
//import com.zdmoney.credit.framework.vo.Vo_600008;
//import com.zdmoney.credit.framework.vo.Vo_600009;
//import com.zdmoney.credit.framework.vo.Vo_700006;
//
//@Service
//public class ApsAppServiceBakService extends BusinessService{
//	
//	@Autowired
//	ApsAppProperties apsAppProperties;
//	
//	@Autowired
//	PicAppProperties picAppProperties;
//	
//	@Autowired
//	CoreProperties coreProperties;
//	
//	@Autowired
//	CreditZxProperties creditZxProperties;
//
//	@Autowired
//	HttpUrlConnection httpUrlConnection;
//	
//	@Autowired
//    RestTemplate restTemplate;
//	
//	@Autowired
//	private ITmApplyMainInfoService applyMainInfoService;
//	
//	@Autowired
//	private ITmApplyFieldInfoService applyFieldInfoService;
//	
//	protected static Log logger = LogFactory.getLog(ApsAppServiceBakService.class);
//
//	@FuncIdAnnotate(value = "600001", desc = "展业APP获取页面控件", voCls = Vo_600001.class, isDependLogin = false)
//	public FuncResult getShowComponents(Vo_600001 vo_600001) throws Exception {
//
//		String appCurrentTime = vo_600001.getAppCurrentTime();
//
//		String result = null;
//		JSONObject param = new JSONObject();
//		String requestUrl = apsAppProperties.getServiceUrl() + "/App/rpc/apsApp/getShowComponents";
//		param.put("appCurrentTime", appCurrentTime);
//
//		result = httpUrlConnection.postForEntity(requestUrl, param, String.class);
//
//		result = URLDecoder.decode(result, "UTF-8");
//		
////		logger.info("----600001 getShowComponents 返回: "+result);
//		
////		result = "{\"applyInfo\":{\"typeName\":\"申请信息\",\"fieldList\":[{\"isEdited\":\"0\",\"inputType\":\"4\",\"label\":\"申请产品\",\"defaultValue\":\"\",\"isRequested\":\"1\",\"inputData\":\"productListData\",\"fieldKey\":\"productCd\"},{\"isEdited\":\"0\",\"inputType\":\"0\",\"label\":\"申请金额\",\"defaultValue\":\"\",\"isRequested\":\"1\",\"inputData\":\"\",\"fieldKey\":\"applyLmt\"},{\"isEdited\":\"0\",\"inputType\":\"4\",\"label\":\"申请期限\",\"defaultValue\":\"\",\"isRequested\":\"1\",\"inputData\":\"productListData\",\"fieldKey\":\"applyTerm\"},{\"isEdited\":\"0\",\"inputType\":\"0\",\"label\":\"姓名\",\"defaultValue\":\"\",\"isRequested\":\"1\",\"inputData\":\"\",\"fieldKey\":\"name\"},{\"isEdited\":\"0\",\"inputType\":\"0\",\"label\":\"身份证号码\",\"defaultValue\":\"\",\"isRequested\":\"1\",\"inputData\":\"\",\"fieldKey\":\"idNo\"},{\"isEdited\":\"0\",\"inputType\":\"3\",\"label\":\"贷款用途\",\"defaultValue\":\"\",\"isRequested\":\"1\",\"inputData\":\"creditApplication\",\"fieldKey\":\"creditApplication\"},{\"isEdited\":\"0\",\"inputType\":\"0\",\"label\":\"客户经理\",\"defaultValue\":\"\",\"isRequested\":\"1\",\"inputData\":\"\",\"fieldKey\":\"branchManager\"},{\"isEdited\":\"0\",\"inputType\":\"3\",\"label\":\"是否加急\",\"defaultValue\":\"N\",\"isRequested\":\"0\",\"inputData\":\"indicator\",\"fieldKey\":\"ifPri\"},{\"isEdited\":\"0\",\"inputType\":\"0\",\"label\":\"备注\",\"defaultValue\":\"\",\"isRequested\":\"0\",\"inputData\":\"\",\"fieldKey\":\"remark\"}],\"sectionLabel\":\"\"},\"assetsInfo\":{\"typeName\":\"房产信息表\",\"fieldList\":[{\"isEdited\":\"0\",\"inputType\":\"3\",\"label\":\"房产类型\",\"defaultValue\":\"\",\"isRequested\":\"0\",\"inputData\":\"fangType\",\"fieldKey\":\"estateType\"},{\"isEdited\":\"0\",\"inputType\":\"5\",\"label\":\"房产所在省\",\"defaultValue\":\"\",\"isRequested\":\"0\",\"inputData\":\"estate-state\",\"fieldKey\":\"estateState\"},{\"isEdited\":\"0\",\"inputType\":\"5\",\"label\":\"房产所在市\",\"defaultValue\":\"\",\"isRequested\":\"0\",\"inputData\":\"estate-city\",\"fieldKey\":\"estateCity\"},{\"isEdited\":\"0\",\"inputType\":\"5\",\"label\":\"房产所在区\",\"defaultValue\":\"\",\"isRequested\":\"0\",\"inputData\":\"estate-zone\",\"fieldKey\":\"estateZone\"},{\"isEdited\":\"0\",\"inputType\":\"0\",\"label\":\"房产地址\",\"defaultValue\":\"\",\"isRequested\":\"0\",\"inputData\":\"\",\"fieldKey\":\"estateAddress\"},{\"isEdited\":\"0\",\"inputType\":\"3\",\"label\":\"房贷情况\",\"defaultValue\":\"\",\"isRequested\":\"0\",\"inputData\":\"estateType\",\"fieldKey\":\"estateLoan\"},{\"isEdited\":\"0\",\"inputType\":\"7\",\"label\":\"购买时间\",\"defaultValue\":\"\",\"isRequested\":\"0\",\"inputData\":\"\",\"fieldKey\":\"buyDate\"},{\"isEdited\":\"0\",\"inputType\":\"0\",\"label\":\"购买总价值/万元\",\"defaultValue\":\"\",\"isRequested\":\"0\",\"inputData\":\"\",\"fieldKey\":\"estateAmt\"},{\"isEdited\":\"0\",\"inputType\":\"0\",\"label\":\"市值参考价/万元\",\"defaultValue\":\"\",\"isRequested\":\"0\",\"inputData\":\"\",\"fieldKey\":\"referenceAmt\"},{\"isEdited\":\"0\",\"inputType\":\"0\",\"label\":\"房贷金额/万元\",\"defaultValue\":\"\",\"isRequested\":\"0\",\"inputData\":\"\",\"fieldKey\":\"estateLoanAmt\"},{\"isEdited\":\"0\",\"inputType\":\"0\",\"label\":\"月供\",\"defaultValue\":\"\",\"isRequested\":\"0\",\"inputData\":\"\",\"fieldKey\":\"monthPaymentAmt\"},{\"isEdited\":\"0\",\"inputType\":\"0\",\"label\":\"已还期数\",\"defaultValue\":\"\",\"isRequested\":\"0\",\"inputData\":\"\",\"fieldKey\":\"hasRepaymentNum\"},{\"isEdited\":\"0\",\"inputType\":\"0\",\"label\":\"建筑面积\",\"defaultValue\":\"\",\"isRequested\":\"0\",\"inputData\":\"\",\"fieldKey\":\"builtupArea\"},{\"isEdited\":\"0\",\"inputType\":\"3\",\"label\":\"房产所有权\",\"defaultValue\":\"\",\"isRequested\":\"0\",\"inputData\":\"houseOwnerType\",\"fieldKey\":\"houseOwnership\"},{\"isEdited\":\"0\",\"inputType\":\"0\",\"label\":\"产权比例\",\"defaultValue\":\"\",\"isRequested\":\"1\",\"inputData\":\"\",\"fieldKey\":\"equityRate\"},{\"isEdited\":\"0\",\"inputType\":\"0\",\"label\":\"共有人姓名\",\"defaultValue\":\"\",\"isRequested\":\"1\",\"inputData\":\"\",\"fieldKey\":\"otherName\"},{\"isEdited\":\"0\",\"inputType\":\"0\",\"label\":\"共有人身份证号\",\"defaultValue\":\"\",\"isRequested\":\"0\",\"inputData\":\"\",\"fieldKey\":\"otherIdNo\"},{\"isEdited\":\"0\",\"inputType\":\"3\",\"label\":\"单位户名为本人\",\"defaultValue\":\"\",\"isRequested\":\"0\",\"inputData\":\"indicator\",\"fieldKey\":\"ifMe\"}],\"sectionLabel\":\"房产信息表\",\"expandList\":[{\"fieldList\":[{\"isEdited\":\"0\",\"inputType\":\"3\",\"label\":\"条件类型\",\"defaultValue\":\"\",\"isRequested\":\"1\",\"inputData\":\"conditionType\",\"fieldKey\":\"conditionType\"}],\"sectionLabel\":\"随薪贷信息表\"},{\"fieldList\":[{\"isEdited\":\"0\",\"inputType\":\"3\",\"label\":\"车辆类型\",\"defaultValue\":\"\",\"isRequested\":\"0\",\"inputData\":\"carType\",\"fieldKey\":\"carType\"},{\"isEdited\":\"0\",\"inputType\":\"3\",\"label\":\"是否有车贷\",\"defaultValue\":\"\",\"isRequested\":\"0\",\"inputData\":\"indicator\",\"fieldKey\":\"carLoan\"},{\"isEdited\":\"0\",\"inputType\":\"7\",\"label\":\"购买时间\",\"defaultValue\":\"\",\"isRequested\":\"0\",\"inputData\":\"\",\"fieldKey\":\"carBuyDate\"},{\"isEdited\":\"0\",\"inputType\":\"0\",\"label\":\"裸车价/万元\",\"defaultValue\":\"\",\"isRequested\":\"0\",\"inputData\":\"\",\"fieldKey\":\"nakedCarPrice\"},{\"isEdited\":\"0\",\"inputType\":\"0\",\"label\":\"购买价/万元\",\"defaultValue\":\"\",\"isRequested\":\"0\",\"inputData\":\"\",\"fieldKey\":\"carBuyPrice\"},{\"isEdited\":\"0\",\"inputType\":\"0\",\"label\":\"贷款剩余期数\",\"defaultValue\":\"\",\"isRequested\":\"0\",\"inputData\":\"\",\"fieldKey\":\"carLoanTerm\"},{\"isEdited\":\"0\",\"inputType\":\"0\",\"label\":\"月供\",\"defaultValue\":\"\",\"isRequested\":\"0\",\"inputData\":\"\",\"fieldKey\":\"monthPaymentAmt\"},{\"isEdited\":\"0\",\"inputType\":\"3\",\"label\":\"本地车牌\",\"defaultValue\":\"\",\"isRequested\":\"0\",\"inputData\":\"indicator\",\"fieldKey\":\"localPlate\"}],\"sectionLabel\":\"车辆信息表\"},{\"fieldList\":[{\"isEdited\":\"0\",\"inputType\":\"0\",\"label\":\"保险金额\",\"defaultValue\":\"\",\"isRequested\":\"0\",\"inputData\":\"\",\"fieldKey\":\"insuranceAmt\"},{\"isEdited\":\"0\",\"inputType\":\"0\",\"label\":\"保险年限\",\"defaultValue\":\"\",\"isRequested\":\"0\",\"inputData\":\"\",\"fieldKey\":\"insuranceTerm\"},{\"isEdited\":\"0\",\"inputType\":\"0\",\"label\":\"已缴年限\",\"defaultValue\":\"\",\"isRequested\":\"0\",\"inputData\":\"\",\"fieldKey\":\"paidTerm\"},{\"isEdited\":\"0\",\"inputType\":\"0\",\"label\":\"最近一次缴费时间\",\"defaultValue\":\"\",\"isRequested\":\"0\",\"inputData\":\"\",\"fieldKey\":\"lastPaymentDate\"},{\"isEdited\":\"0\",\"inputType\":\"3\",\"label\":\"缴费方式\",\"defaultValue\":\"\",\"isRequested\":\"0\",\"inputData\":\"paymentMethod\",\"fieldKey\":\"paymentMethod\"},{\"isEdited\":\"0\",\"inputType\":\"3\",\"label\":\"与被保险人关系\",\"defaultValue\":\"\",\"isRequested\":\"0\",\"inputData\":\"policyRelation\",\"fieldKey\":\"policyRelation\"},{\"isEdited\":\"0\",\"inputType\":\"0\",\"label\":\"年缴金额\",\"defaultValue\":\"\",\"isRequested\":\"0\",\"inputData\":\"\",\"fieldKey\":\"yaerPaymentAmt\"},{\"isEdited\":\"0\",\"inputType\":\"3\",\"label\":\"保单真伪核实方式\",\"defaultValue\":\"\",\"isRequested\":\"0\",\"inputData\":\"policyCheck\",\"fieldKey\":\"policyCheck\"}],\"sectionLabel\":\"保单信息表\"},{\"fieldList\":[{\"isEdited\":\"1\",\"inputType\":\"7\",\"label\":\"开户时间\",\"defaultValue\":\"\",\"isRequested\":\"0\",\"inputData\":\"\",\"fieldKey\":\"openAccountDate\"},{\"isEdited\":\"0\",\"inputType\":\"0\",\"label\":\"缴存比例\",\"defaultValue\":\"\",\"isRequested\":\"0\",\"inputData\":\"\",\"fieldKey\":\"depositRate\"},{\"isEdited\":\"0\",\"inputType\":\"0\",\"label\":\"月缴存额\",\"defaultValue\":\"\",\"isRequested\":\"0\",\"inputData\":\"\",\"fieldKey\":\"monthDepositAmt\"},{\"isEdited\":\"0\",\"inputType\":\"0\",\"label\":\"缴存基数\",\"defaultValue\":\"\",\"isRequested\":\"0\",\"inputData\":\"\",\"fieldKey\":\"depositBase\"},{\"isEdited\":\"0\",\"inputType\":\"3\",\"label\":\"公积金材料\",\"defaultValue\":\"A\",\"isRequested\":\"0\",\"inputData\":\"providentInfo\",\"fieldKey\":\"providentInfo\"},{\"isEdited\":\"0\",\"inputType\":\"3\",\"label\":\"缴存单位同申请单位\",\"defaultValue\":\"\",\"isRequested\":\"0\",\"inputData\":\"indicator\",\"fieldKey\":\"paymentUnit\"},{\"isEdited\":\"0\",\"inputType\":\"0\",\"label\":\"申请单位已缴月数\",\"defaultValue\":\"\",\"isRequested\":\"0\",\"inputData\":\"\",\"fieldKey\":\"paymentMonthNum\"}],\"sectionLabel\":\"公积金信息表\"},{\"fieldList\":[{\"isEdited\":\"1\",\"inputType\":\"7\",\"label\":\"发卡时间\",\"defaultValue\":\"\",\"isRequested\":\"0\",\"inputData\":\"\",\"fieldKey\":\"startDate\"},{\"isEdited\":\"0\",\"inputType\":\"0\",\"label\":\"额度\",\"defaultValue\":\"\",\"isRequested\":\"0\",\"inputData\":\"\",\"fieldKey\":\"creditLimit\"},{\"isEdited\":\"0\",\"inputType\":\"12\",\"label\":\"近4个月账单金额依次为\",\"defaultValue\":\"\",\"isRequested\":\"0\",\"inputData\":\"\",\"fieldKey\":\"billAmt\"},{\"isEdited\":\"0\",\"inputType\":\"0\",\"label\":\"月均\",\"defaultValue\":\"\",\"isRequested\":\"0\",\"inputData\":\"\",\"fieldKey\":\"payMonthAmt\"}],\"sectionLabel\":\"卡友贷信息\"},{\"fieldList\":[{\"isEdited\":\"0\",\"inputType\":\"3\",\"label\":\"京东用户等级\",\"defaultValue\":\"\",\"isRequested\":\"0\",\"inputData\":\"jiDongUserLevel\",\"fieldKey\":\"jiDongUserLevel\"},{\"isEdited\":\"0\",\"inputType\":\"0\",\"label\":\"小白信用分\",\"defaultValue\":\"\",\"isRequested\":\"0\",\"inputData\":\"\",\"fieldKey\":\"whiteCreditValue\"},{\"isEdited\":\"0\",\"inputType\":\"0\",\"label\":\"近一年实际消费金额\",\"defaultValue\":\"\",\"isRequested\":\"0\",\"inputData\":\"\",\"fieldKey\":\"pastYearShoppingAmount\"}],\"sectionLabel\":\"网购达人贷B信息\"},{\"fieldList\":[{\"isEdited\":\"0\",\"inputType\":\"7\",\"label\":\"开店时间\",\"defaultValue\":\"\",\"isRequested\":\"0\",\"inputData\":\"\",\"fieldKey\":\"registerDate\"},{\"isEdited\":\"0\",\"inputType\":\"3\",\"label\":\"买家信誉等级\",\"defaultValue\":\"\",\"isRequested\":\"0\",\"inputData\":\"sellerCreditLevel\",\"fieldKey\":\"buyerCreditLevel\"},{\"isEdited\":\"0\",\"inputType\":\"3\",\"label\":\"买家信用类型\",\"defaultValue\":\"\",\"isRequested\":\"0\",\"inputData\":\"sellerCreditType\",\"fieldKey\":\"buyerCreditType\"},{\"isEdited\":\"0\",\"inputType\":\"0\",\"label\":\"好评率\",\"defaultValue\":\"\",\"isRequested\":\"0\",\"inputData\":\"\",\"fieldKey\":\"goodRate\"},{\"isEdited\":\"0\",\"inputType\":\"0\",\"label\":\"上一年度支出额\",\"defaultValue\":\"\",\"isRequested\":\"0\",\"inputData\":\"\",\"fieldKey\":\"lastYearPayAmt\"},{\"isEdited\":\"0\",\"inputType\":\"11\",\"label\":\"近三个月支出总额\",\"defaultValue\":\"\",\"isRequested\":\"0\",\"inputData\":\"\",\"fieldKey\":\"payAmt\"}],\"sectionLabel\":\"网购达人贷信息表\"},{\"fieldList\":[{\"isEdited\":\"0\",\"inputType\":\"7\",\"label\":\"开店时间\",\"defaultValue\":\"\",\"isRequested\":\"0\",\"inputData\":\"\",\"fieldKey\":\"setupShopDate\"},{\"isEdited\":\"0\",\"inputType\":\"3\",\"label\":\"卖家信誉等级\",\"defaultValue\":\"\",\"isRequested\":\"0\",\"inputData\":\"sellerCreditLevel\",\"fieldKey\":\"sellerCreditLevel\"},{\"isEdited\":\"0\",\"inputType\":\"3\",\"label\":\"卖家信用类型\",\"defaultValue\":\"\",\"isRequested\":\"0\",\"inputData\":\"sellerCreditType\",\"fieldKey\":\"sellerCreditType\"},{\"isEdited\":\"0\",\"inputType\":\"0\",\"label\":\"近半年好评数\",\"defaultValue\":\"\",\"isRequested\":\"0\",\"inputData\":\"\",\"fieldKey\":\"regardedNum\"},{\"isEdited\":\"0\",\"inputType\":\"14\",\"label\":\"近6个月账单金额依次为\",\"defaultValue\":\"\",\"isRequested\":\"0\",\"inputData\":\"\",\"fieldKey\":\"biullAmt\"},{\"isEdited\":\"0\",\"inputType\":\"0\",\"label\":\"月均\",\"defaultValue\":\"\",\"isRequested\":\"0\",\"inputData\":\"\",\"fieldKey\":\"payMonthAmt\"}],\"sectionLabel\":\"淘宝商户贷信息表\"}]},\"contactPersonInfo\":{\"typeName\":\"联系人信息\",\"fieldList\":[{\"isEdited\":\"1\",\"inputType\":\"0\",\"label\":\"姓名\",\"defaultValue\":\"\",\"isRequested\":\"1\",\"inputData\":\"\",\"fieldKey\":\"contactName\"},{\"isEdited\":\"0\",\"inputType\":\"3\",\"label\":\"与申请人关系\",\"defaultValue\":\"\",\"isRequested\":\"1\",\"inputData\":\"relationship\",\"fieldKey\":\"contactRelation\"},{\"isEdited\":\"1\",\"inputType\":\"0\",\"label\":\"身份证号码\",\"defaultValue\":\"\",\"isRequested\":\"0\",\"inputData\":\"\",\"fieldKey\":\"contactIdNo\"},{\"isEdited\":\"0\",\"inputType\":\"3\",\"label\":\"性别\",\"defaultValue\":\"\",\"isRequested\":\"0\",\"inputData\":\"gender\",\"fieldKey\":\"contactGender\"},{\"isEdited\":\"1\",\"inputType\":\"0\",\"label\":\"手机\",\"defaultValue\":\"\",\"isRequested\":\"1\",\"inputData\":\"\",\"fieldKey\":\"contactMobile\"},{\"isEdited\":\"0\",\"inputType\":\"3\",\"label\":\"是否知晓贷款\",\"defaultValue\":\"N\",\"isRequested\":\"1\",\"inputData\":\"indicator\",\"fieldKey\":\"ifKnowLoan\"},{\"isEdited\":\"1\",\"inputType\":\"0\",\"label\":\"公司名称\",\"defaultValue\":\"\",\"isRequested\":\"0\",\"inputData\":\"\",\"fieldKey\":\"contactEmpName\"},{\"isEdited\":\"0\",\"inputType\":\"3\",\"label\":\"职务\",\"defaultValue\":\"\",\"isRequested\":\"0\",\"inputData\":\"empPositionAttrType\",\"fieldKey\":\"contactCorpPost\"},{\"isEdited\":\"1\",\"inputType\":\"0\",\"label\":\"公司电话号码\",\"defaultValue\":\"\",\"isRequested\":\"0\",\"inputData\":\"\",\"fieldKey\":\"contactCorpPhone\"},{\"isEdited\":\"1\",\"inputType\":\"0\",\"label\":\"公司传真\",\"defaultValue\":\"\",\"isRequested\":\"0\",\"inputData\":\"\",\"fieldKey\":\"contactCorpFax\"}],\"sectionLabel\":\"\"},\"empItemInfo\":{\"typeName\":\"工作信息\",\"fieldList\":[{\"isEdited\":\"1\",\"inputType\":\"0\",\"label\":\"单位名称\",\"defaultValue\":\"\",\"isRequested\":\"1\",\"inputData\":\"\",\"fieldKey\":\"corpName\"},{\"isEdited\":\"0\",\"inputType\":\"3\",\"label\":\"是否私营业主\",\"defaultValue\":\"N\",\"isRequested\":\"1\",\"inputData\":\"indicator\",\"fieldKey\":\"privateOwnersFlag\"},{\"isEdited\":\"0\",\"inputType\":\"5\",\"label\":\"公司所在省\",\"defaultValue\":\"\",\"isRequested\":\"1\",\"inputData\":\"emp-state\",\"fieldKey\":\"empProvince\"},{\"isEdited\":\"0\",\"inputType\":\"5\",\"label\":\"公司所在市\",\"defaultValue\":\"\",\"isRequested\":\"1\",\"inputData\":\"emp-city\",\"fieldKey\":\"empCity\"},{\"isEdited\":\"0\",\"inputType\":\"5\",\"label\":\"公司所在区/县\",\"defaultValue\":\"\",\"isRequested\":\"1\",\"inputData\":\"emp-zone\",\"fieldKey\":\"empZone\"},{\"isEdited\":\"1\",\"inputType\":\"0\",\"label\":\"公司地址\",\"defaultValue\":\"\",\"isRequested\":\"1\",\"inputData\":\"\",\"fieldKey\":\"empAdd\"},{\"isEdited\":\"1\",\"inputType\":\"0\",\"label\":\"公司邮编\",\"defaultValue\":\"\",\"isRequested\":\"0\",\"inputData\":\"\",\"fieldKey\":\"empPostcode\"},{\"isEdited\":\"0\",\"inputType\":\"3\",\"label\":\"工商网信息\",\"defaultValue\":\"\",\"isRequested\":\"1\",\"inputData\":\"businessNetWork\",\"fieldKey\":\"businessNetWork\"},{\"isEdited\":\"0\",\"inputType\":\"3\",\"label\":\"公司性质\",\"defaultValue\":\"\",\"isRequested\":\"1\",\"inputData\":\"corpStructure\",\"fieldKey\":\"empStructure\"},{\"isEdited\":\"0\",\"inputType\":\"3\",\"label\":\"公司行业类别\",\"defaultValue\":\"\",\"isRequested\":\"1\",\"inputData\":\"empType\",\"fieldKey\":\"empType\"},{\"isEdited\":\"1\",\"inputType\":\"0\",\"label\":\"任职部门\",\"defaultValue\":\"\",\"isRequested\":\"1\",\"inputData\":\"\",\"fieldKey\":\"empDepapment\"},{\"isEdited\":\"0\",\"inputType\":\"3\",\"label\":\"职务\",\"defaultValue\":\"\",\"isRequested\":\"1\",\"inputData\":\"noGovInstitution\",\"fieldKey\":\"empPost\"},{\"isEdited\":\"0\",\"inputType\":\"3\",\"label\":\"职业\",\"defaultValue\":\"\",\"isRequested\":\"1\",\"inputData\":\"occupationType\",\"fieldKey\":\"occupation\"},{\"isEdited\":\"1\",\"inputType\":\"0\",\"label\":\"单电\",\"defaultValue\":\"\",\"isRequested\":\"1\",\"inputData\":\"\",\"fieldKey\":\"comPhoneNo\"},{\"isEdited\":\"1\",\"inputType\":\"0\",\"label\":\"单电2\",\"defaultValue\":\"\",\"isRequested\":\"0\",\"inputData\":\"\",\"fieldKey\":\"corpPhone2\"},{\"isEdited\":\"1\",\"inputType\":\"7\",\"label\":\"入职时间\",\"defaultValue\":\"\",\"isRequested\":\"1\",\"inputData\":\"\",\"fieldKey\":\"startDate\"},{\"isEdited\":\"0\",\"inputType\":\"3\",\"label\":\"发薪方式\",\"defaultValue\":\"\",\"isRequested\":\"1\",\"inputData\":\"corpPayWay\",\"fieldKey\":\"corpPayWay\"},{\"isEdited\":\"1\",\"inputType\":\"0\",\"label\":\"单位月收入/元\",\"defaultValue\":\"\",\"isRequested\":\"1\",\"inputData\":\"\",\"fieldKey\":\"monthSalary\"},{\"isEdited\":\"1\",\"inputType\":\"0\",\"label\":\"其他月收入\",\"defaultValue\":\"\",\"isRequested\":\"0\",\"inputData\":\"\",\"fieldKey\":\"otherIncome\"},{\"isEdited\":\"0\",\"inputType\":\"0\",\"label\":\"月总收入/元\",\"defaultValue\":\"\",\"isRequested\":\"0\",\"inputData\":\"\",\"fieldKey\":\"totalMonthSalary\"}],\"sectionLabel\":\"工作信息\",\"expandList\":[{\"fieldList\":[{\"isEdited\":\"0\",\"inputType\":\"3\",\"label\":\"成立时间\",\"defaultValue\":\"\",\"isRequested\":\"1\",\"inputData\":\"\",\"fieldKey\":\"setupDate\"},{\"isEdited\":\"1\",\"inputType\":\"0\",\"label\":\"占股比例/%\",\"defaultValue\":\"\",\"isRequested\":\"1\",\"inputData\":\"\",\"fieldKey\":\"sharesScale\"},{\"isEdited\":\"1\",\"inputType\":\"0\",\"label\":\"注册资本/万元\",\"defaultValue\":\"\",\"isRequested\":\"1\",\"inputData\":\"\",\"fieldKey\":\"registerFunds\"},{\"isEdited\":\"0\",\"inputType\":\"3\",\"label\":\"私营企业类型\",\"defaultValue\":\"\",\"isRequested\":\"1\",\"inputData\":\"priEnterpriseType\",\"fieldKey\":\"priEnterpriseType\"},{\"isEdited\":\"0\",\"inputType\":\"3\",\"label\":\"经营场所\",\"defaultValue\":\"\",\"isRequested\":\"1\",\"inputData\":\"businessPlace\",\"fieldKey\":\"businessPlace\"},{\"isEdited\":\"1\",\"inputType\":\"0\",\"label\":\"月租金/元\",\"defaultValue\":\"\",\"isRequested\":\"0\",\"inputData\":\"\",\"fieldKey\":\"monthRent\"},{\"isEdited\":\"1\",\"inputType\":\"0\",\"label\":\"员工人数/人\",\"defaultValue\":\"\",\"isRequested\":\"1\",\"inputData\":\"\",\"fieldKey\":\"employeeNum\"},{\"isEdited\":\"1\",\"inputType\":\"0\",\"label\":\"企业净利润率/%\",\"defaultValue\":\"\",\"isRequested\":\"1\",\"inputData\":\"\",\"fieldKey\":\"enterpriseRate\"},{\"isEdited\":\"1\",\"inputType\":\"0\",\"label\":\"股东姓名(除客户外最大股东)\",\"defaultValue\":\"\",\"isRequested\":\"0\",\"inputData\":\"\",\"fieldKey\":\"sharesName\"},{\"isEdited\":\"1\",\"inputType\":\"0\",\"label\":\"股东身份证\",\"defaultValue\":\"\",\"isRequested\":\"0\",\"inputData\":\"\",\"fieldKey\":\"sharesIdNo\"},{\"isEdited\":\"1\",\"inputType\":\"0\",\"label\":\"每月净利润额/万元\",\"defaultValue\":\"\",\"isRequested\":\"1\",\"inputData\":\"\",\"fieldKey\":\"monthAmt\"}],\"sectionLabel\":\"私营业主信息\"}]},\"persionInfo\":{\"typeName\":\"个人信息\",\"fieldList\":[{\"isEdited\":\"0\",\"inputType\":\"0\",\"label\":\"姓名\",\"defaultValue\":\"\",\"isRequested\":\"0\",\"inputData\":\"\",\"fieldKey\":\"name\"},{\"isEdited\":\"0\",\"inputType\":\"0\",\"label\":\"身份证号码\",\"defaultValue\":\"\",\"isRequested\":\"0\",\"inputData\":\"\",\"fieldKey\":\"idNo\"},{\"isEdited\":\"0\",\"inputType\":\"0\",\"label\":\"性别\",\"defaultValue\":\"\",\"isRequested\":\"0\",\"inputData\":\"\",\"fieldKey\":\"gender\"},{\"isEdited\":\"0\",\"inputType\":\"0\",\"label\":\"年龄\",\"defaultValue\":\"\",\"isRequested\":\"0\",\"inputData\":\"\",\"fieldKey\":\"age\"},{\"isEdited\":\"0\",\"inputType\":\"3\",\"label\":\"婚姻状况\",\"defaultValue\":\"\",\"isRequested\":\"1\",\"inputData\":\"maritalStatus\",\"fieldKey\":\"maritalStatus\"},{\"isEdited\":\"1\",\"inputType\":\"0\",\"label\":\"子女数\",\"defaultValue\":\"\",\"isRequested\":\"0\",\"inputData\":\"\",\"fieldKey\":\"childrenNum\"},{\"isEdited\":\"0\",\"inputType\":\"3\",\"label\":\"最高学历\",\"defaultValue\":\"\",\"isRequested\":\"1\",\"inputData\":\"educationType\",\"fieldKey\":\"qualification\"},{\"isEdited\":\"0\",\"inputType\":\"7\",\"label\":\"毕业时间\",\"defaultValue\":\"\",\"isRequested\":\"0\",\"inputData\":\"\",\"fieldKey\":\"graduationDate\"},{\"isEdited\":\"0\",\"inputType\":\"5\",\"label\":\"户籍所在省\",\"defaultValue\":\"\",\"isRequested\":\"1\",\"inputData\":\"iss-state\",\"fieldKey\":\"issState\"},{\"isEdited\":\"0\",\"inputType\":\"5\",\"label\":\"户籍所在市\",\"defaultValue\":\"\",\"isRequested\":\"1\",\"inputData\":\"iss-city\",\"fieldKey\":\"issCity\"},{\"isEdited\":\"0\",\"inputType\":\"5\",\"label\":\"户籍所在区\",\"defaultValue\":\"\",\"isRequested\":\"1\",\"inputData\":\"iss-zone\",\"fieldKey\":\"issZone\"},{\"isEdited\":\"1\",\"inputType\":\"0\",\"label\":\"户籍地址\",\"defaultValue\":\"\",\"isRequested\":\"1\",\"inputData\":\"\",\"fieldKey\":\"idIssuerAddress\"},{\"isEdited\":\"1\",\"inputType\":\"0\",\"label\":\"户籍邮编\",\"defaultValue\":\"\",\"isRequested\":\"0\",\"inputData\":\"\",\"fieldKey\":\"issuerPostcode\"},{\"isEdited\":\"0\",\"inputType\":\"5\",\"label\":\"家庭所在省\",\"defaultValue\":\"\",\"isRequested\":\"1\",\"inputData\":\"home-state\",\"fieldKey\":\"homeState\"},{\"isEdited\":\"0\",\"inputType\":\"5\",\"label\":\"家庭所在市\",\"defaultValue\":\"\",\"isRequested\":\"1\",\"inputData\":\"home-city\",\"fieldKey\":\"homeCity\"},{\"isEdited\":\"0\",\"inputType\":\"5\",\"label\":\"家庭所在区县\",\"defaultValue\":\"\",\"isRequested\":\"1\",\"inputData\":\"home-zone\",\"fieldKey\":\"homeZone\"},{\"isEdited\":\"1\",\"inputType\":\"0\",\"label\":\"家庭地址\",\"defaultValue\":\"\",\"isRequested\":\"1\",\"inputData\":\"\",\"fieldKey\":\"homeAdd\"},{\"isEdited\":\"1\",\"inputType\":\"0\",\"label\":\"家庭住宅邮编\",\"defaultValue\":\"\",\"isRequested\":\"0\",\"inputData\":\"\",\"fieldKey\":\"homePostcode\"},{\"isEdited\":\"0\",\"inputType\":\"3\",\"label\":\"住宅类型\",\"defaultValue\":\"\",\"isRequested\":\"1\",\"inputData\":\"houseType\",\"fieldKey\":\"houseType\"},{\"isEdited\":\"1\",\"inputType\":\"0\",\"label\":\"租金/元\",\"defaultValue\":\"\",\"isRequested\":\"0\",\"inputData\":\"\",\"fieldKey\":\"houseRent\"},{\"isEdited\":\"1\",\"inputType\":\"0\",\"label\":\"每月家庭支出\",\"defaultValue\":\"\",\"isRequested\":\"1\",\"inputData\":\"\",\"fieldKey\":\"monthPay\"},{\"isEdited\":\"1\",\"inputType\":\"0\",\"label\":\"可接受的月最高还款\",\"defaultValue\":\"\",\"isRequested\":\"1\",\"inputData\":\"\",\"fieldKey\":\"monthMaxRepay\"},{\"isEdited\":\"1\",\"inputType\":\"0\",\"label\":\"手机1\",\"defaultValue\":\"\",\"isRequested\":\"1\",\"inputData\":\"\",\"fieldKey\":\"cellphone\"},{\"isEdited\":\"1\",\"inputType\":\"0\",\"label\":\"手机2\",\"defaultValue\":\"\",\"isRequested\":\"0\",\"inputData\":\"\",\"fieldKey\":\"cellphone2\"},{\"isEdited\":\"1\",\"inputType\":\"0\",\"label\":\"宅电\",\"defaultValue\":\"\",\"isRequested\":\"0\",\"inputData\":\"\",\"fieldKey\":\"homePhoneNo1\"},{\"isEdited\":\"1\",\"inputType\":\"0\",\"label\":\"QQ号\",\"defaultValue\":\"\",\"isRequested\":\"0\",\"inputData\":\"\",\"fieldKey\":\"qq\"},{\"isEdited\":\"1\",\"inputType\":\"0\",\"label\":\"微信号\",\"defaultValue\":\"\",\"isRequested\":\"0\",\"inputData\":\"\",\"fieldKey\":\"wechat\"},{\"isEdited\":\"1\",\"inputType\":\"0\",\"label\":\"电子邮箱\",\"defaultValue\":\"\",\"isRequested\":\"0\",\"inputData\":\"\",\"fieldKey\":\"email\"}],\"sectionLabel\":\"\"},\"listData\":{\"providentInfo\":[{\"title\":\"官方网站\",\"name\":\"A\"},{\"title\":\"网银账户\",\"name\":\"B\"},{\"title\":\"中心证明\",\"name\":\"C\"},{\"title\":\"人行记录\",\"name\":\"D\"}],\"indicator\":[{\"title\":\"是\",\"name\":\"Y\"},{\"title\":\"否\",\"name\":\"N\"}],\"creditApplication\":[{\"title\":\"资金周转\",\"name\":\"00001\"},{\"title\":\"扩大经营\",\"name\":\"00002\"},{\"title\":\"购生活品\",\"name\":\"00003\"},{\"title\":\"购原材料\",\"name\":\"00004\"},{\"title\":\"购设备\",\"name\":\"00005\"},{\"title\":\"教育支出\",\"name\":\"00006\"},{\"title\":\"装修家居\",\"name\":\"00007\"},{\"title\":\"医疗\",\"name\":\"00008\"},{\"title\":\"旅游\",\"name\":\"00009\"},{\"title\":\"购买\",\"name\":\"00010\"},{\"title\":\"其他\",\"name\":\"00011\"}],\"corpPayWay\":[{\"title\":\"网银\",\"name\":\"00001\"},{\"title\":\"现金\",\"name\":\"00002\"},{\"title\":\"网银+现金\",\"name\":\"00003\"}],\"estateType\":[{\"title\":\"还款中\",\"name\":\"ING\"},{\"title\":\"全款购\",\"name\":\"ALL\"},{\"title\":\"已结清\",\"name\":\"END\"}],\"conditionType\":[{\"title\":\"申请人名下或直系亲属名下在进件地有房产\",\"name\":\"A\"},{\"title\":\"本地户籍\",\"name\":\"B\"},{\"title\":\"满足信用条件\",\"name\":\"C\"}],\"noGovInstitution\":[{\"title\":\"法人代表\",\"name\":\"00001\"},{\"title\":\"总经理\",\"name\":\"00002\"},{\"title\":\"副总经理\",\"name\":\"00003\"},{\"title\":\"部门经理\",\"name\":\"00004\"},{\"title\":\"主管\",\"name\":\"00005\"},{\"title\":\"职员\",\"name\":\"00006\"}],\"maritalStatus\":[{\"title\":\"未婚\",\"name\":\"00001\"},{\"title\":\"已婚\",\"name\":\"00002\"},{\"title\":\"离异\",\"name\":\"00003\"},{\"title\":\"其他\",\"name\":\"00004\"}],\"sellerCreditLevel\":[{\"title\":\"1\",\"name\":\"A\"},{\"title\":\"2\",\"name\":\"B\"},{\"title\":\"3\",\"name\":\"C\"},{\"title\":\"4\",\"name\":\"D\"},{\"title\":\"5\",\"name\":\"E\"}],\"businessPlace\":[{\"title\":\"自有\",\"name\":\"00001\"},{\"title\":\"租用\",\"name\":\"00002\"}],\"priEnterpriseType\":[{\"title\":\"个体户\",\"name\":\"00001\"},{\"title\":\"独资\",\"name\":\"00002\"},{\"title\":\"合伙制\",\"name\":\"00003\"},{\"title\":\"股份制\",\"name\":\"00004\"},{\"title\":\"其他\",\"name\":\"00005\"}],\"fangType\":[{\"title\":\"商品房\",\"name\":\"00001\"},{\"title\":\"经济适用房/动迁房/房改房\",\"name\":\"00002\"},{\"title\":\"自建房\",\"name\":\"00003\"}],\"carType\":[{\"title\":\"一手车\",\"name\":\"00001\"},{\"title\":\"二手车\",\"name\":\"00002\"}],\"educationType\":[{\"title\":\"硕士及以上\",\"name\":\"00001\"},{\"title\":\"本科\",\"name\":\"00002\"},{\"title\":\"大专\",\"name\":\"00003\"},{\"title\":\"中专\",\"name\":\"00004\"},{\"title\":\"高中\",\"name\":\"00005\"},{\"title\":\"初中及以下\",\"name\":\"00006\"}],\"policyCheck\":[{\"title\":\"客服热线\",\"name\":\"A\"},{\"title\":\"网站\",\"name\":\"B\"}],\"businessNetWork\":[{\"title\":\"在营\",\"name\":\"A\"},{\"title\":\"注销/吊销/过期\",\"name\":\"C\"},{\"title\":\"查无\",\"name\":\"N\"}],\"corpStructure\":[{\"title\":\"政府机构\",\"name\":\"00001\"},{\"title\":\"事业单位\",\"name\":\"00002\"},{\"title\":\"国企\",\"name\":\"00003\"},{\"title\":\"外资\",\"name\":\"00004\"},{\"title\":\"民营\",\"name\":\"00005\"},{\"title\":\"私营\",\"name\":\"00006\"},{\"title\":\"其它\",\"name\":\"00007\"},{\"title\":\"合资\",\"name\":\"00008\"},{\"title\":\"个体\",\"name\":\"00009\"}],\"gender\":[{\"title\":\"男\",\"name\":\"M\"},{\"title\":\"女\",\"name\":\"F\"}],\"jiDongUserLevel\":[{\"title\":\"注册会员\",\"name\":\"ZC\"},{\"title\":\"铜牌会员\",\"name\":\"TP\"},{\"title\":\"银牌会员\",\"name\":\"YP\"},{\"title\":\"金牌会员\",\"name\":\"JP\"},{\"title\":\"钻石会员\",\"name\":\"ZS\"}],\"occupationType\":[{\"title\":\"工薪\",\"name\":\"00001\"},{\"title\":\"白领\",\"name\":\"00002\"},{\"title\":\"自营\",\"name\":\"00003\"},{\"title\":\"学生\",\"name\":\"00004\"}],\"relationship\":[{\"title\":\"父母\",\"name\":\"00001\"},{\"title\":\"子女\",\"name\":\"00002\"},{\"title\":\"兄弟\",\"name\":\"00003\"},{\"title\":\"姐妹\",\"name\":\"00004\"},{\"title\":\"兄妹\",\"name\":\"00005\"},{\"title\":\"姐弟\",\"name\":\"00006\"},{\"title\":\"朋友\",\"name\":\"00007\"},{\"title\":\"同事\",\"name\":\"00008\"},{\"title\":\"房东\",\"name\":\"00009\"},{\"title\":\"亲属\",\"name\":\"00010\"},{\"title\":\"同学\",\"name\":\"00011\"},{\"title\":\"其它\",\"name\":\"00012\"},{\"title\":\"配偶\",\"name\":\"00013\"}],\"policyRelation\":[{\"title\":\"本人\",\"name\":\"00001\"},{\"title\":\"夫妻\",\"name\":\"00002\"},{\"title\":\"父母\",\"name\":\"00003\"},{\"title\":\"子女\",\"name\":\"00004\"},{\"title\":\"其他\",\"name\":\"00005\"}],\"empType\":[{\"title\":\"农、林、牧、渔业，能源、采矿业\",\"name\":\"00001\"},{\"title\":\"食品、药品、工业原料、服装、日用品等制造业\",\"name\":\"00002\"},{\"title\":\"电力、热力、燃气及水生产和供应业\",\"name\":\"00003\"},{\"title\":\"建筑业\",\"name\":\"00004\"},{\"title\":\"批发和零售业\",\"name\":\"00005\"},{\"title\":\"交通运输、仓储和邮政业\",\"name\":\"00006\"},{\"title\":\"住宿、旅游、餐饮业\",\"name\":\"00007\"},{\"title\":\"信息传输、软件和信息技术服务业\",\"name\":\"00008\"},{\"title\":\"金融业\",\"name\":\"00009\"},{\"title\":\"房地产业\",\"name\":\"00010\"},{\"title\":\"租赁和商务服务业\",\"name\":\"00011\"},{\"title\":\"科学研究和技术服务业\",\"name\":\"00012\"},{\"title\":\"水利、环境和公共设施管理业\",\"name\":\"00013\"},{\"title\":\"居民服务、修理和其他服务业\",\"name\":\"00014\"},{\"title\":\"教育、培训\",\"name\":\"00015\"},{\"title\":\"卫生、医疗、社会保障、社会福利\",\"name\":\"00016\"},{\"title\":\"文化、体育和娱乐业\",\"name\":\"00017\"},{\"title\":\"政府、非赢利机构和社会组织\",\"name\":\"00018\"},{\"title\":\"警察、消防、军人\",\"name\":\"00019\"},{\"title\":\"其他\",\"name\":\"00020\"}],\"houseOwnerType\":[{\"title\":\"客户独有\",\"name\":\"Y\"},{\"title\":\"客户共有\",\"name\":\"O\"},{\"title\":\"非本人名下\",\"name\":\"N\"}],\"houseType\":[{\"title\":\"自有住房\",\"name\":\"00001\"},{\"title\":\"单位住房\",\"name\":\"00002\"},{\"title\":\"亲属住房 \",\"name\":\"00003\"},{\"title\":\"租房\",\"name\":\"00004\"}],\"empPositionAttrType\":[{\"title\":\"高层管理人员\",\"name\":\"A\"},{\"title\":\"中层管理人员\",\"name\":\"B\"},{\"title\":\"基层管理人员\",\"name\":\"C\"},{\"title\":\"一般员工\",\"name\":\"D\"},{\"title\":\"内勤\",\"name\":\"E\"},{\"title\":\"后勤\",\"name\":\"F\"},{\"title\":\"工人\",\"name\":\"G\"},{\"title\":\"销售/中介/业务代表\",\"name\":\"H\"},{\"title\":\"营业员/服务员\",\"name\":\"I\"},{\"title\":\"正部级\",\"name\":\"J\"},{\"title\":\"副部级\",\"name\":\"K\"},{\"title\":\"正厅级\",\"name\":\"L\"},{\"title\":\"副厅级\",\"name\":\"M\"},{\"title\":\"正处级\",\"name\":\"N\"},{\"title\":\"副处级\",\"name\":\"O\"},{\"title\":\"正科级\",\"name\":\"P\"},{\"title\":\"副科级\",\"name\":\"Q\"},{\"title\":\"正股级\",\"name\":\"R\"},{\"title\":\"副股级\",\"name\":\"S\"},{\"title\":\"其他\",\"name\":\"Z\"}],\"sellerCreditType\":[{\"title\":\"红心\",\"name\":\"A\"},{\"title\":\"黄钻\",\"name\":\"B\"},{\"title\":\"红冠\",\"name\":\"C\"},{\"title\":\"紫冠\",\"name\":\"D\"}],\"paymentMethod\":[{\"title\":\"年\",\"name\":\"Y\"},{\"title\":\"月\",\"name\":\"M\"}]}}";
//		
//		JSONObject obj = JSONObject.parseObject(result);
//		
//		if(!obj.containsKey("flag")) {
//			throw new PlatformException(ResponseEnum.FULL_MSG,"征审返回缺少【flag】Key");
//		}
//		
//		Boolean flag = obj.getBoolean("flag");
//
//		if(flag){
//			return FuncResult.success("查询成功", obj);
//		}else{
//			String message = obj.getString("message");
//			throw new PlatformException(message);
//		}
//	}
//	
//	@FuncIdAnnotate(value = "600002", desc = "产品信息初始化接口", voCls = Vo_600002.class)
//	public FuncResult getProductListData(Vo_600002 vo_600002) throws Exception {
//
//		String userCode = vo_600002.getUserCode();
//
//		String result = null;
//		JSONObject param = new JSONObject();
//		String requestUrl = apsAppProperties.getServiceUrl() + "/App/rpc/apsApp/getProductListData";
//		param.put("userCode", userCode);
//
//		result = httpUrlConnection.postForEntity(requestUrl, param, String.class);
//
//		result = URLDecoder.decode(result, "UTF-8");
//		
////		logger.info("----600002 getProductListData 返回: "+result);
//		
////		result = "{\"code\":\"0000\",\"msgEx\":{\"infos\":{\"userCode\":\"00236125\",\"productListData\":[{\"name\":\"00001\",\"title\":\"随薪贷\",\"default\":1,\"productDesc\":\"随薪贷\",\"floorLimit\":\"10000.00\",\"ceilingLimit\":\"50000.00\",\"applyTermList\":[{\"name\":\"6\",\"title\":\"6\",\"default\":0},{\"name\":\"12\",\"title\":\"12\",\"default\":0},{\"name\":\"18\",\"title\":\"18\",\"default\":0}]},{\"name\":\"00002\",\"title\":\"随意贷\",\"default\":0,\"productDesc\":\"随意贷\",\"floorLimit\":\"10000.00\",\"ceilingLimit\":\"100000.00\",\"applyTermList\":[{\"name\":\"6\",\"title\":\"6\",\"default\":0},{\"name\":\"12\",\"title\":\"12\",\"default\":0},{\"name\":\"18\",\"title\":\"18\",\"default\":0}]},{\"name\":\"00003\",\"title\":\"随意贷A\",\"default\":0,\"productDesc\":\"随意贷A\",\"floorLimit\":\"20000.00\",\"ceilingLimit\":\"50000.00\",\"applyTermList\":[{\"name\":\"6\",\"title\":\"6\",\"default\":0},{\"name\":\"12\",\"title\":\"12\",\"default\":0},{\"name\":\"18\",\"title\":\"18\",\"default\":0}]}]},\"respDesc\":\"正常\",\"status\":\"0\"}}";
//		
//		JSONObject obj = JSONObject.parseObject(result);
//
//		if(!obj.containsKey("flag")) {
//			throw new PlatformException(ResponseEnum.FULL_MSG,"征审返回缺少【flag】Key");
//		}
//		
//		Boolean flag = obj.getBoolean("flag");
//
//		if(flag){
//			return FuncResult.success("查询成功", obj);
//		}else{
//			String message = obj.getString("message");
//			throw new PlatformException(message);
//		}
//	}
//	
//	@FuncIdAnnotate(value = "600003", desc = "提交保存录入接口", voCls = Vo_600003.class)
//	public FuncResult saveApplyInput(Vo_600003 vo_600003) throws Exception {
//
//		HashMap<String,Object> applyInputMap = new HashMap<String,Object>();
//		
//		String userCode = vo_600003.getUserCode();
//		Date applyDate = vo_600003.getApplyDate();
//		
//		applyInputMap.put("applyInfo", vo_600003.getApplyInfo()); //申请信息
//		applyInputMap.put("persionInfo", vo_600003.getPersionInfo()); //个人信息
//		
//		ArrayList<HashMap<String,Serializable>> empItemInfo = vo_600003.getEmpItemInfo();//工作信息
//		for(HashMap<String,Serializable> eInfoMap : empItemInfo){
//			String sectionlKey = (String)eInfoMap.get("sectionKey");
//			if(sectionlKey!=null && "empItemInfo".equals(sectionlKey)){
//				applyInputMap.put("empItemInfo", eInfoMap); //工作信息
//			}
//			if(sectionlKey!=null && "baseItemInfo".equals(sectionlKey)){
//				applyInputMap.put("baseItemInfo", eInfoMap); //私营业主信息
//			}
//		}
//		
//		ArrayList<HashMap<String,Serializable>> contactPersonInfo = vo_600003.getContactPersonInfo();//联系人信息列表
//		applyInputMap.put("contactPersonInfo", contactPersonInfo);
//		
//		ArrayList<HashMap<String,Serializable>> assetsInfo = vo_600003.getAssetsInfo();//资产信息
//		for(HashMap<String,Serializable> aInfoMap : assetsInfo){
//			String sectionlKey = (String)aInfoMap.get("sectionKey");
//			if(sectionlKey!=null && "salaryLoanInfo".equals(sectionlKey)){
//				applyInputMap.put("salaryLoanInfo", aInfoMap); //随薪贷信息
//			}
//			if(sectionlKey!=null && "assetsInfo".equals(sectionlKey)){
//				applyInputMap.put("estateInfo", aInfoMap);  //房产信息
//			}
//			if(sectionlKey!=null && "carInfo".equals(sectionlKey)){
//				applyInputMap.put("carInfo", aInfoMap);  //车辆信息
//			}
//			if(sectionlKey!=null && "policyInfo".equals(sectionlKey)){
//				applyInputMap.put("policyInfo", aInfoMap);  //保单信息
//			}
//			if(sectionlKey!=null && "providentInfo".equals(sectionlKey)){
//				applyInputMap.put("providentInfo", aInfoMap);  //公积金信息
//			}
//			if(sectionlKey!=null && "cardLoanInfo".equals(sectionlKey)){
//				applyInputMap.put("cardLoanInfo", aInfoMap);  //卡友贷信息
//			}
//			if(sectionlKey!=null && "masterLoanBInfo".equals(sectionlKey)){
//				applyInputMap.put("masterLoanBInfo", aInfoMap);  //网购达人贷B信息
//			}
//			if(sectionlKey!=null && "masterLoanInfo".equals(sectionlKey)){
//				applyInputMap.put("masterLoanInfo", aInfoMap);  //网购达人贷信息
//			}
//			if(sectionlKey!=null && "merchantLoanInfo".equals(sectionlKey)){
//				applyInputMap.put("merchantLoanInfo", aInfoMap);  //淘宝商户贷信息
//			}
//		}
//		
//		JSONObject applyInputJson = new JSONObject(applyInputMap);
//
//		String result = null;
//		JSONObject param = new JSONObject();
//		String requestUrl = apsAppProperties.getServiceUrl() + "/App/rpc/apsApp/saveApplyInput";
//		param.put("userCode", userCode);
//		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
//		param.put("applyDateStr", df.format(applyDate));
//		param.put("applyInputJson", applyInputJson);
//
//		result = httpUrlConnection.postForEntity(requestUrl, param, String.class);
//		
//		result = URLDecoder.decode(result, "UTF-8");
//		
////		logger.info("----600003 saveApplyInput 返回: "+result);
//		
////		result = "{\"code\":\"0000\",\"msgEx\":{\"infos\":{},\"respDesc\":\"保存录入申请单成功\",\"status\":\"0\"}}";
//		
//		JSONObject obj = JSONObject.parseObject(result);
//
//		if(!obj.containsKey("flag")) {
//			throw new PlatformException(ResponseEnum.FULL_MSG,"征审返回缺少【flag】Key");
//		}
//		
//		Boolean flag = obj.getBoolean("flag");
//
//		if(flag){
//			return FuncResult.success("查询成功", obj);
//		}else{
//			String message = obj.getString("message");
//			throw new PlatformException(message);
//		}
//	}
//	
////	@FuncIdAnnotate(value = "600004", desc = "FTP上传接口", voCls = Vo_600004.class)
////	public FuncResult uploadFile(Vo_600004 vo_600004) throws Exception {
////
////		//String version = vo_600004.getVersion();
////		//String userCode = vo_600004.getUserCode();
////		String appNo = vo_600004.getAppNo();
////		/*String fileName = vo_600004.getFileName();
////		byte[] b = vo_600004.getFileBytes();*/
////		ArrayList<Map<String,Object>> uploadFilel=vo_600004.getUploadFileList();
////		//b.toString();
////		String result = null;
////		
////		for(int i=0;i<uploadFilel.size();i++){
////			
////			JSONObject param = new JSONObject();
////			//String requestUrl = picAppProperties.getServiceUrl() + "/upload/upload_uploadPic";
////			String requestUrl = picAppProperties.getServiceUrl() + "/uploadFile/uploadFileAction_uploadPicture";
////			param.put("appNo", appNo);
////		/*	param.put("FileName", fileName);*/
////			param.put("iremark", "app");
////			param.put("sysName", "aps");
////			param.put("nodeKey", "input-modify");
////			param.put("ifPatchBolt", "N");
////			/*param.put("b", b);*/
////			
////			String fileName=(String) uploadFilel.get(i).get("fileName");
////			String fileBytes=(String) uploadFilel.get(i).get("fileBytes");
////			
////			String fn[] = fileName.split("\\.");
////		    String str[]=fn[0].split("_");
////		    param.put("isortsId", str[1]);
////			System.out.println(str[1]);
////		    
////			param.put("Filename",fileName);
////			param.put("fileBytes", fileBytes);
////			
////			result = httpUrlConnection.postForEntity(requestUrl, param, String.class);
////			
////			result = URLDecoder.decode(result, "UTF-8");
////			
////			JSONObject obj = JSONObject.parseObject(result);
////			
////			if(!obj.containsKey("flag")) {
////				throw new PlatformException(ResponseEnum.FULL_MSG,"图片管理系统返回缺少【flag】Key");
////			}
////			
////			String flag = obj.getString("flag");
////			
////			if(flag==null || "failure".equals(flag)){
////				String message = obj.getString("message");
////				throw new PlatformException(message);
////			}
////			
////		}
////		//result = "{\"code\":\"0000\",\"msgEx\":{\"infos\":{},\"respDesc\":\"文件上传成功\",\"status\":\"0\"}}";
////
////		return FuncResult.success("上传成功", new JSONObject());
////	}
//	
//	@FuncIdAnnotate(value = "600004", desc = "保存取消的申请单信息接口", voCls = Vo_600004.class)
//	public FuncResult saveCancelApplyInput(Vo_600004 vo_600004) throws Exception {
//
//		HashMap<String,Object> applyInputMap = new HashMap<String,Object>();
//		
//		String userCode = vo_600004.getUserCode();
//		Date applyDate = vo_600004.getApplyDate();
//		String refuseReason = vo_600004.getRefuseReason();
//		
//		applyInputMap.put("applyInfo", vo_600004.getApplyInfo()); //申请信息
//		applyInputMap.put("persionInfo", vo_600004.getPersionInfo()); //个人信息
//		
//		ArrayList<HashMap<String,Serializable>> empItemInfo = vo_600004.getEmpItemInfo();//工作信息
//		for(HashMap<String,Serializable> eInfoMap : empItemInfo){
//			String sectionlKey = (String)eInfoMap.get("sectionKey");
//			if(sectionlKey!=null && "empItemInfo".equals(sectionlKey)){
//				applyInputMap.put("empItemInfo", eInfoMap); //工作信息
//			}
//			if(sectionlKey!=null && "baseItemInfo".equals(sectionlKey)){
//				applyInputMap.put("baseItemInfo", eInfoMap); //私营业主信息
//			}
//		}
//		
//		ArrayList<HashMap<String,Serializable>> contactPersonInfo = vo_600004.getContactPersonInfo();//联系人信息列表
//		applyInputMap.put("contactPersonInfo", contactPersonInfo);
//		
//		ArrayList<HashMap<String,Serializable>> assetsInfo = vo_600004.getAssetsInfo();//资产信息
//		for(HashMap<String,Serializable> aInfoMap : assetsInfo){
//			String sectionlKey = (String)aInfoMap.get("sectionKey");
//			if(sectionlKey!=null && "salaryLoanInfo".equals(sectionlKey)){
//				applyInputMap.put("salaryLoanInfo", aInfoMap); //随薪贷信息
//			}
//			if(sectionlKey!=null && "assetsInfo".equals(sectionlKey)){
//				applyInputMap.put("estateInfo", aInfoMap);  //房产信息
//			}
//			if(sectionlKey!=null && "carInfo".equals(sectionlKey)){
//				applyInputMap.put("carInfo", aInfoMap);  //车辆信息
//			}
//			if(sectionlKey!=null && "policyInfo".equals(sectionlKey)){
//				applyInputMap.put("policyInfo", aInfoMap);  //保单信息
//			}
//			if(sectionlKey!=null && "providentInfo".equals(sectionlKey)){
//				applyInputMap.put("providentInfo", aInfoMap);  //公积金信息
//			}
//			if(sectionlKey!=null && "cardLoanInfo".equals(sectionlKey)){
//				applyInputMap.put("cardLoanInfo", aInfoMap);  //卡友贷信息
//			}
//			if(sectionlKey!=null && "masterLoanBInfo".equals(sectionlKey)){
//				applyInputMap.put("masterLoanBInfo", aInfoMap);  //网购达人贷B信息
//			}
//			if(sectionlKey!=null && "masterLoanInfo".equals(sectionlKey)){
//				applyInputMap.put("masterLoanInfo", aInfoMap);  //网购达人贷信息
//			}
//			if(sectionlKey!=null && "merchantLoanInfo".equals(sectionlKey)){
//				applyInputMap.put("merchantLoanInfo", aInfoMap);  //淘宝商户贷信息
//			}
//		}
//		
//		JSONObject applyInputJson = new JSONObject(applyInputMap);
//
//		String result = null;
//		JSONObject param = new JSONObject();
//		String requestUrl = apsAppProperties.getServiceUrl() + "/App/rpc/apsApp/saveCancelApplyInput";
//		param.put("userCode", userCode);
//		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
//		param.put("applyDateStr", df.format(applyDate));
//		param.put("refuseReason", refuseReason);
//		param.put("applyInputJson", applyInputJson);
//
//		result = httpUrlConnection.postForEntity(requestUrl, param, String.class);
//		
//		result = URLDecoder.decode(result, "UTF-8");
//		
////		logger.info("----600004 saveCancelApplyInput 返回: "+result);
//		
////		result = "{\"code\":\"0000\",\"msgEx\":{\"infos\":{},\"respDesc\":\"保存录入申请单成功\",\"status\":\"0\"}}";
//		
//		JSONObject obj = JSONObject.parseObject(result);
//
//		if(!obj.containsKey("flag")) {
//			throw new PlatformException(ResponseEnum.FULL_MSG,"征审返回缺少【flag】Key");
//		}
//		
//		Boolean flag = obj.getBoolean("flag");
//
//		if(flag){
//			return FuncResult.success("查询成功", obj);
//		}else{
//			String message = obj.getString("message");
//			throw new PlatformException(message);
//		}
//	}
//	
//	@FuncIdAnnotate(value = "600005", desc = "身份证录单校验接口", voCls = Vo_600005.class)
//	public FuncResult checkIDCard(Vo_600005 vo_600005) throws Exception {
//		
//		String oldAppNo = vo_600005.getAppNo();
//		String userCode = vo_600005.getUserCode();
//		String idCardNo = vo_600005.getIdNo();
//		String productCd = vo_600005.getProductCd();
//		
//		String isExists = "N";
//		
//		Date applyDate = null;
//		Date lastThreeDay = null;
//		Date lastDay = null;
//		Date lastSubmitDate = null;
//		
//		
//		
//		Map<String, Object> map = new HashMap<String,Object>();
//		map.put("appNo", oldAppNo);
//		map.put("idNo", vo_600005.getIdNo());
//		List<TmApplyMainInfo> queryByMap = applyMainInfoService.queryByMap2(map);		
//		if( !queryByMap.isEmpty() && null != queryByMap.get(0)){
//			 String idNo = queryByMap.get(0).getIdNo();
//			 throw new PlatformException(ResponseEnum.FULL_MSG,"身份证号码【"+idNo+"】已存在申请");
//		}
//		
//		if(oldAppNo!=null && !oldAppNo.equals("")){
//			map.remove("idNo");
//			List<TmApplyMainInfo> qMap = applyMainInfoService.queryByMap3(map);
//			if(qMap==null || qMap.size()==0){
//				throw new PlatformException(ResponseEnum.FULL_MSG,"申请单号【"+oldAppNo+"】的申请不存在");
//			}
//			
//			isExists="Y";
//			
//			TmApplyMainInfo tmi = qMap.get(0);
//			applyDate = tmi.getApplyDate();
//			lastThreeDay = tmi.getLastThreeDay();
//			lastDay = tmi.getLastDay();
//			lastSubmitDate = tmi.getLastSubmitTime();
//		}
//		
//		Boolean isThrowE = false;
//		
//		String result = null;
//		System.err.println("appinfo: "+vo_600005.getApplyInfoField());
//		JSONObject param = new JSONObject();
//		String requestUrl = apsAppProperties.getServiceUrl() + "/App/rpc/apsApp/checkIDCard";
//		param.put("idCardNo", idCardNo);
//		param.put("productCd", productCd);
//		param.put("userCode", userCode);
//		param.put("isExists", isExists);
//		result = httpUrlConnection.postForEntity(requestUrl, param, String.class);
//		
//		result = URLDecoder.decode(result, "UTF-8");
//		
//		logger.info("----600005 checkIDCard 返回: "+result);
//		
//		JSONObject obj = JSONObject.parseObject(result);
//		
//		if(!obj.containsKey("flag")) {
//			throw new PlatformException(ResponseEnum.FULL_MSG,"征审返回缺少【flag】Key");
//		}
//		
//		Boolean flag = obj.getBoolean("flag");
//		
//		//客户在我司前一次贷款申请被拒绝或取消提示信息 
//		String ifNext = obj.getString("ifNext");
//		String promptMessages = obj.getString("promptMessage") == null ? "" : obj.getString("promptMessage");
//		String promptZXMessage = "";
//		
//		//身份证校验成功
//		if (flag){
//			
//			if(ifNext!=null && "N".equals(ifNext)){
//				throw new PlatformException(ResponseEnum.FULL_MSG,promptMessages);
//			}
//			
//			String appNo = "";
//			
//			if(oldAppNo!=null && !oldAppNo.equals("")){
//				appNo=oldAppNo;
//			}else{
//				//生成随机appNo
//				Date date = new Date(); 
//				SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmssSSSSSS"); 
//				appNo = dateFormat.format(date).toString();
//				System.out.println(appNo); 
//			}
//			
//			//查询是否有征信报告
//			
////			String requestUrlZX = creditZxProperties.getServiceUrl() + "/pbccrc/getReportHtmlContent";
//			String requestUrlZX = creditZxProperties.getServiceUrl() + "/creditReport/getReportId";
//			JSONObject paramZX = new JSONObject();
//			paramZX.put("sysCode", "app");
//			paramZX.put("customerName", vo_600005.getName());
//			paramZX.put("customerIdCard", idCardNo);
//			DateFormat df=new SimpleDateFormat("yyyy-MM-dd");
//			paramZX.put("queryDate",df.format(new Date()));
//			String date = String.valueOf(System.currentTimeMillis());
//			paramZX.put("timestamp", date);
//			
//			JSONObject paramZXgr = new JSONObject();
//			paramZXgr.put("param", paramZX.toString());
//			String resultZX = httpUrlConnection.postForEntity(requestUrlZX, paramZXgr, String.class);
//			
//			logger.info("----600005 getReportId 返回: "+resultZX);
//			
//			JSONObject objZX = JSONObject.parseObject(resultZX);
//			
//			String resCode = objZX.getString("code");
//			
//			String hasCreditReport = new String();
//			String applyState = null;
//
//			if ("000000".equals(resCode)){
//				//有征信报告
//				hasCreditReport = "1";
//				applyState="1";
//				
//				//APP获取央行征信信用判断结果
//				String reportId = objZX.getString("reportId");
//				
//				String requestUrlZXResult = creditZxProperties.getServiceUrl() + "/creditReport/getCreditInfo";
//				JSONObject paramZXResult = new JSONObject();
//				paramZXResult.put("customerName", vo_600005.getName());
//				paramZXResult.put("customerIdCard", idCardNo);
//				paramZXResult.put("reportId", reportId);
//				paramZXResult.put("queryDate",df.format(new Date()));
//				paramZXResult.put("timestamp", date);
//				
//				JSONObject paramZXgc = new JSONObject();
//				paramZXgc.put("param", paramZXResult.toString());
//				String resultZXResult = httpUrlConnection.postForEntity(requestUrlZXResult, paramZXgc, String.class);
//				JSONObject objZXResult = JSONObject.parseObject(resultZXResult);
//				
//				logger.info("----600005 getCreditInfo 返回: "+resultZXResult);
//				
//				String code = objZXResult.getString("code");
//				if ("000000".equals(code)){
//					String type = objZXResult.getString("type");
//					String applyType = obj.getString("applyType");
//					
//					JSONArray bHProductList = obj.getJSONArray("bHProductList");
//					
//					//符合无综合信用条件
//					if ("YES".equals(type) && !"RELOAN".equals(applyType) && !bHProductList.contains(productCd)){
//						promptZXMessage = "初判客户是征信白户，并且不是结清再贷客户，申请的产品除"+obj.getString("bHProductStrs")+"外均不允许提交，请检查客户申请的产品类型";
//						applyState="0";
//						isThrowE = true;
//					}else{
//						String status = objZXResult.getString("status");
//						//信用不良
//						if ("NO".equals(status)){
//							String data = objZXResult.getString("data");
//							//逾期
//							if (data != null){
//								promptZXMessage = data;
//							}else{
//								promptZXMessage = "初判客户信用不良";
//							}
//						}
//					}					
//				}
//				
//			}else{
//				//无征信报告
//				hasCreditReport = "0";
//				applyState="0";
//			}
//			
//			try {
//				
//				JSONObject applyInfo = JSONObject.parseObject(vo_600005.getApplyInfoField());
//				applyInfo.remove("hasCreditReport");
//				applyInfo.put("hasCreditReport", hasCreditReport);
//				vo_600005.setApplyInfoField(applyInfo.toString());
//				
//				if(oldAppNo!=null && !oldAppNo.equals("")){
//					applyMainInfoService.deleteByAppNo(map);
//					applyMainInfoService.saveMainInfo(vo_600005, appNo, hasCreditReport, ifNext, promptMessages, promptZXMessage,
//							applyDate, lastThreeDay, lastDay, lastSubmitDate);
//					map.put("fieldKey", "applyInfo");
//					applyFieldInfoService.deleteByCon(map);
//					applyFieldInfoService.saveFieldInfo2( vo_600005, appNo,applyState);
//				}else{
//					
//					if(obj.containsKey("lastSubmitTime")){
//						DateFormat dlf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//						lastThreeDay = dlf.parse(obj.getString("lastThreeDay")+" 00:00:00");
//						lastDay = dlf.parse(obj.getString("lastDay")+" 00:00:00");
//						lastSubmitDate = dlf.parse(obj.getString("lastSubmitTime")+" 00:00:00");
//					}else{
//						lastThreeDay = null;
//						lastDay = null;
//						lastSubmitDate = null;
//					}
//					
//					applyDate = new Date();
//					
//					applyMainInfoService.saveMainInfo(vo_600005, appNo, hasCreditReport, ifNext, promptMessages, promptZXMessage, 
//							applyDate, lastThreeDay, lastDay, lastSubmitDate);
//					applyFieldInfoService.saveFieldInfo( vo_600005, appNo,applyState);
//				}
//			} catch (Exception e) {
//				logger.error("保存申请信息异常", e);
//				throw new PlatformException(ResponseEnum.FULL_MSG,"保存申请信息异常");
//			}
//			
//			obj.put("userCode", userCode);
//			obj.put("appNo", appNo);
//			obj.put("hasCreditReport", hasCreditReport);
//			obj.put("ifNext", ifNext);
//			obj.put("promptMessages", promptMessages);
//			obj.put("promptZXMessage", promptZXMessage);
//
//			if(isThrowE){
//				return FuncResult.fail(promptZXMessage, obj);
////				throw new PlatformException(ResponseEnum.FULL_MSG,promptZXMessage);
//			}else{
//				return FuncResult.success("身份证校验成功", obj);
//			}
//			
//		}else{
//			
//			if(ifNext!=null && "N".equals(ifNext)){
//				throw new PlatformException(ResponseEnum.FULL_MSG,promptMessages);
//			}
//			
//			String message = obj.getString("message");
////			throw new PlatformException(message);
//			return FuncResult.fail(message, obj);
//		}
//		
//	}
//	
//	@FuncIdAnnotate(value = "600006", desc = "历史申请状态查询接口", voCls = Vo_600006.class)
//	public FuncResult getHisApplyInputList(Vo_600006 vo_600006) throws Exception {
//
//		String userCode = vo_600006.getUserCode();
//		int pageNum = vo_600006.getPageNum();
//		int pageSize = vo_600006.getPageSize();
//		String status = vo_600006.getStatus();
//
//		String result = null;
//		JSONObject param = new JSONObject();
//		String requestUrl = apsAppProperties.getServiceUrl() + "/App/rpc/apsApp/getHisApplyInputList";
//		param.put("userCode", userCode);
//		param.put("pageNum", pageNum);
//		param.put("pageSize", pageSize);
//		param.put("status", status);
//
//		result = httpUrlConnection.postForEntity(requestUrl, param, String.class);
//
//		result = URLDecoder.decode(result, "UTF-8");
//		
////		result = "{\"code\":\"0000\",\"msgEx\":{\"infos\":{\"userCode\":\"00236125\",\"appInputListData\":[{\"appNo\":\"20150914170000001013\",\"applicantName\":\"吴桐\",\"status\":\"逾期\",\"applyTime\":\"2015/9/14 15:29:20\",\"applyAccount\":\"12000.00\",\"productName\":\"车贷\",\"idNoLastFourDigits\":\"3035\"},{\"appNo\":\"20160521170000001009\",\"applicantName\":\"吴绮莉\",\"status\":\"通过\",\"applyTime\":\"2016/5/21 15:29:20\",\"applyAccount\":\"20000.00\",\"productName\":\"房贷\",\"idNoLastFourDigits\":\"3038\"},{\"appNo\":\"20161104170000001013\",\"applicantName\":\"程明\",\"status\":\"驳回\",\"applyTime\":\"2016/11/4 15:29:20\",\"applyAccount\":\"3600.00\",\"productName\":\"随薪贷\",\"idNoLastFourDigits\":\"3036\"}]},\"respDesc\":\"正常\",\"status\":\"0\"}}";
//		
//		JSONObject obj = JSONObject.parseObject(result);
//
//		if(!obj.containsKey("flag")) {
//			throw new PlatformException(ResponseEnum.FULL_MSG,"征审返回缺少【flag】Key");
//		}
//		
//		Boolean flag = obj.getBoolean("flag");
//
//		if(flag){
//			return FuncResult.success("查询成功", obj);
//		}else{
//			String message = obj.getString("message");
//			throw new PlatformException(message);
//		}
//	}
//	
//	@FuncIdAnnotate(value = "600007", desc = "首页信息查询接口", voCls = Vo_600007.class)
//	public FuncResult getShowList(Vo_600007 vo_600007) throws Exception {
//
//		String userCode = vo_600007.getUserCode();
//
//		String result = null;
//		JSONObject param = new JSONObject();
//		String requestUrl = apsAppProperties.getServiceUrl()
//				+ "/App/rpc/apsApp/getShowList";
//		param.put("userCode", userCode);
//		result = httpUrlConnection.postForEntity(requestUrl, param,String.class);
//		
//		result = URLDecoder.decode(result, "UTF-8");
//		
//		JSONObject obj = JSONObject.parseObject(result);
//
//		if(!obj.containsKey("flag")) {
//			throw new PlatformException(ResponseEnum.FULL_MSG,"征审返回缺少【flag】Key");
//		}
//		
//		Boolean flag = obj.getBoolean("flag");
//		
//		if(flag){
//		
//			/** 查询公告结果集 **/
//			JSONObject paramGG = new JSONObject();
//			/** 查询调用核心登录校验接口url **/
//			String requestUrlGG = coreProperties.getServiceUrl()
//					+ "/app/notice/searchNotice";
//			paramGG.put("pagerNum", 1);
//			paramGG.put("pagerMax", 20);
//			String resultGG = httpUrlConnection.postForEntity(requestUrlGG,
//					paramGG, String.class);
//			resultGG = URLDecoder.decode(resultGG, "UTF-8");
//			JSONObject objGG = JSONObject.parseObject(resultGG);
//			String resCode = "";
//			FuncResult funcResult = null;
//			if (objGG.containsKey("resCode")) {
//				resCode = objGG.getString("resCode");
//				if ("000000".equals(resCode)) {
//					JSONObject attachment = objGG.getJSONObject("attachment");
//					if (attachment == null) {
//						throw new PlatformException(ResponseEnum.FULL_MSG,"缺少attachment数据项");
//					}
//					/** 公告结果集 **/
//					JSONArray noticeList = new JSONArray();
//					noticeList = attachment.getJSONArray("resultList");
//					if (noticeList == null || noticeList.size() == 0){
//						obj.put("noticeList", new JSONObject());
//					}else{
//						obj.put("noticeList", noticeList.get(0));
//					}
//					funcResult = FuncResult.success("查询成功", obj);
//				} else {
//					funcResult = FuncResult.fail(objGG.getString("resMsg"));
//				}
//			} else {
//				funcResult = FuncResult.fail("核心返回缺少【resCode】Key");
//			}
//			return funcResult;
//		}else{
//			String message = obj.getString("message");
//			throw new PlatformException(message);
//		}
//	}
//	
//	@FuncIdAnnotate(value = "600008", desc = "贷前试算接口", voCls = Vo_600008.class)
//	public FuncResult getTrialBeforeCredit(Vo_600008 vo_600008) throws Exception {
//
//		String name = vo_600008.getName();
//		String productCd = vo_600008.getProductCd();
//		String applyLmt = vo_600008.getApplyLmt();
//		String applyTerm = vo_600008.getApplyTerm();
//		String fristPaymentDate = vo_600008.getFristPaymentDate();
//
//		String result = null;
//		JSONObject param = new JSONObject();
//		String requestUrl = apsAppProperties.getServiceUrl()
//				+ "/App/rpc/apsApp/getTrialBeforeCredit";
//		param.put("name", name);
//		param.put("productCd", productCd);
//		param.put("applyLmt", applyLmt);
//		param.put("applyTerm", applyTerm);
//		param.put("fristPaymentDate", fristPaymentDate);
//		
//		result = httpUrlConnection.postForEntity(requestUrl, param, String.class);
//		result = URLDecoder.decode(result, "UTF-8");
//		JSONObject obj = JSONObject.parseObject(result);
//
//		if(!obj.containsKey("flag")) {
//			throw new PlatformException(ResponseEnum.FULL_MSG,"征审返回缺少【flag】Key");
//		}
//		
//		Boolean flag = obj.getBoolean("flag");
//		
//		if(flag){
//			return FuncResult.success("贷前试算信息查询成功", obj);
//		}else{
//			String message = obj.getString("message");
//			throw new PlatformException(message);
//		}
//	}
//	
//	@FuncIdAnnotate(value = "600009", desc = "身份证录单校验接口", voCls = Vo_600009.class)
//	public FuncResult checkIDCardNotSave(Vo_600009 vo_600009) throws Exception {
//		
//		String oldAppNo = vo_600009.getAppNo();
//		String userCode = vo_600009.getUserCode();
//		String idCardNo = vo_600009.getIdNo();
//		String productCd = vo_600009.getProductCd();
//		String reportId = vo_600009.getReportId();
//		
//		Map<String, Object> map = new HashMap<String,Object>();
//		map.put("appNo", oldAppNo);
//		map.put("idNo", vo_600009.getIdNo());
//		List<TmApplyMainInfo> queryByMap = applyMainInfoService.queryByMap2(map);		
//		if( !queryByMap.isEmpty() && null != queryByMap.get(0)){
//			 String idNo = queryByMap.get(0).getIdNo();
//			 throw new PlatformException(ResponseEnum.FULL_MSG,"身份证号码【"+idNo+"】已存在申请");
//		}
//		
//		if(oldAppNo!=null && !oldAppNo.equals("")){
//			map.remove("idNo");
//			List<TmApplyMainInfo> qMap = applyMainInfoService.queryByMap3(map);
//			if(qMap==null || qMap.size()==0){
//				throw new PlatformException(ResponseEnum.FULL_MSG,"申请单号【"+oldAppNo+"】的申请不存在");
//			}
//		}
//		
//		
//		String result = null;
//		System.err.println("appinfo: "+vo_600009.getApplyInfoField());
//		JSONObject param = new JSONObject();
//		String requestUrl = apsAppProperties.getServiceUrl() + "/App/rpc/apsApp/checkIDCard";
//		param.put("idCardNo", idCardNo);
//		param.put("productCd", productCd);
//		param.put("userCode", userCode);
//		param.put("isExists", "Y");
//		result = httpUrlConnection.postForEntity(requestUrl, param, String.class);
//
//		result = URLDecoder.decode(result, "UTF-8");
//		
//		logger.info("----600009 checkIDCard 返回: "+result);
//		
//		JSONObject obj = JSONObject.parseObject(result);
//		
//		if(!obj.containsKey("flag")) {
//			throw new PlatformException(ResponseEnum.FULL_MSG,"征审返回缺少【flag】Key");
//		}
//		
//		Boolean flag = obj.getBoolean("flag");
//		
//		//客户在我司前一次贷款申请被拒绝或取消提示信息 
//		String ifNext = obj.getString("ifNext");
//		String promptMessages = obj.getString("promptMessage") == null ? "" : obj.getString("promptMessage");
//		String promptZXMessage = "";
//		
//		//身份证校验成功
//		if (flag){
//			
//			if(ifNext!=null && "N".equals(ifNext)){
//				throw new PlatformException(ResponseEnum.FULL_MSG,promptMessages);
//			}
//			
//			String requestUrlZXResult = creditZxProperties.getServiceUrl() + "/creditReport/getCreditInfo";
//			JSONObject paramZXResult = new JSONObject();
//			paramZXResult.put("customerName", vo_600009.getName());
//			paramZXResult.put("customerIdCard", idCardNo);
//			paramZXResult.put("reportId", reportId);
//			DateFormat df=new SimpleDateFormat("yyyy-MM-dd");
//			paramZXResult.put("queryDate",df.format(new Date()));
//			String date = String.valueOf(System.currentTimeMillis());
//			paramZXResult.put("timestamp", date);
//			
//			JSONObject paramZXgc = new JSONObject();
//			paramZXgc.put("param", paramZXResult.toString());
//			String resultZXResult = httpUrlConnection.postForEntity(requestUrlZXResult, paramZXgc, String.class);
//			
//			logger.info("----600009 getCreditInfo 返回: "+resultZXResult);
//			
//			JSONObject objZXResult = JSONObject.parseObject(resultZXResult);
//			
//			String code = objZXResult.getString("code");
//			if ("000000".equals(code)){
//				String type = objZXResult.getString("type");
//				String applyType = obj.getString("applyType");
//				
//				//符合无综合信用条件
//				if ("YES".equals(type) && !"RELOAN".equals(applyType)){
//					promptZXMessage = "初判客户是征信白户，并且不是结清再贷客户，申请的产品除"+obj.getString("bHProductStrs")+"外均不允许提交，请检查客户申请的产品类型";
//				}
//				
//				String status = objZXResult.getString("status");
//				//信用不良
//				if ("NO".equals(status)){
//					String data = objZXResult.getString("data");
//					//逾期
//					if (data != null){
//						promptZXMessage = data;
//					}else{
//						promptZXMessage = "初判客户信用不良";
//					}
//				}
//				
//			}
//			
//			obj.put("promptMessages", promptMessages);
//			obj.put("promptZXMessage", promptZXMessage);
//			return FuncResult.success("身份证校验成功", obj);
//		}else{
//			
//			if(ifNext!=null && "N".equals(ifNext)){
//				throw new PlatformException(ResponseEnum.FULL_MSG,promptMessages);
//			}
//			
//			String message = obj.getString("message");
//			return FuncResult.fail(message, obj);
//		}
//		
//	}
//	
//	@FuncIdAnnotate(value = "700006", desc = "身份验证接口", voCls = Vo_700006.class)
//	public FuncResult checkIDCard(Vo_700006 vo_700006) throws Exception {
//		
//		Map<String, Object> map = new HashMap<String,Object>();
//		map.put("idNo", vo_700006.getIdNo());
//		List<TmApplyMainInfo> queryByMap = applyMainInfoService.queryByMap(map);		
//		if( !queryByMap.isEmpty() && null != queryByMap.get(0)){
//			 String idNo = queryByMap.get(0).getIdNo();
//			 throw new PlatformException(ResponseEnum.FULL_MSG,"身份证号码【"+idNo+"】已存在申请");
//		}
//		
//		String name = vo_700006.getName();
//		String idCardNo = vo_700006.getIdNo();
//		String userCode = vo_700006.getUserCode();
//		String result = null;
//		JSONObject param = new JSONObject();
//		
//		String requestUrl = apsAppProperties.getServiceUrl()
//				+ "/App/rpc/apsApp/checkID";
//		param.put("userCode", userCode);
//		param.put("name", name);
//		param.put("idCardNo", idCardNo);
//		result = httpUrlConnection.postForEntity(requestUrl, param,
//				String.class);
//		result = URLDecoder.decode(result, "UTF-8");
//
//		JSONObject obj = JSONObject.parseObject(result);
//		String ifNext = obj.getString("ifNext");
//		String promptMessages = obj.getString("promptMessage");
//
//		if (!obj.containsKey("flag")) {
//			throw new PlatformException(ResponseEnum.FULL_MSG,
//					"征审返回缺少【flag】Key");
//		}
//		Boolean flag = obj.getBoolean("flag");
//
//		if (flag) {
//			obj.put("ifNext", ifNext);
//			obj.put("promptMessages", promptMessages);
//			return FuncResult.success("身份验证成功", obj);
//		} else {
//			String message = "身份验证异常";
//			throw new PlatformException(message);
//		}
//	}
//	
//	public static void main (String args[]) {
//		
//		
//		String result = "{\"applyInfo\":{\"typeName\":\"申请信息\",\"fieldList\":[{\"isEdited\":\"0\",\"inputType\":\"4\",\"label\":\"申请产品\",\"defaultValue\":\"\",\"isRequested\":\"1\",\"inputData\":\"productListData\",\"fieldKey\":\"productCd\"},{\"isEdited\":\"0\",\"inputType\":\"0\",\"label\":\"申请金额\",\"defaultValue\":\"\",\"isRequested\":\"1\",\"inputData\":\"\",\"fieldKey\":\"applyLmt\"},{\"isEdited\":\"0\",\"inputType\":\"4\",\"label\":\"申请期限\",\"defaultValue\":\"\",\"isRequested\":\"1\",\"inputData\":\"productListData\",\"fieldKey\":\"applyTerm\"},{\"isEdited\":\"0\",\"inputType\":\"0\",\"label\":\"姓名\",\"defaultValue\":\"\",\"isRequested\":\"1\",\"inputData\":\"\",\"fieldKey\":\"name\"},{\"isEdited\":\"0\",\"inputType\":\"0\",\"label\":\"身份证号码\",\"defaultValue\":\"\",\"isRequested\":\"1\",\"inputData\":\"\",\"fieldKey\":\"idNo\"},{\"isEdited\":\"0\",\"inputType\":\"3\",\"label\":\"贷款用途\",\"defaultValue\":\"\",\"isRequested\":\"1\",\"inputData\":\"creditApplication\",\"fieldKey\":\"creditApplication\"},{\"isEdited\":\"0\",\"inputType\":\"0\",\"label\":\"客户经理\",\"defaultValue\":\"\",\"isRequested\":\"1\",\"inputData\":\"\",\"fieldKey\":\"branchManager\"},{\"isEdited\":\"0\",\"inputType\":\"3\",\"label\":\"是否加急\",\"defaultValue\":\"N\",\"isRequested\":\"0\",\"inputData\":\"indicator\",\"fieldKey\":\"ifPri\"},{\"isEdited\":\"0\",\"inputType\":\"0\",\"label\":\"备注\",\"defaultValue\":\"\",\"isRequested\":\"0\",\"inputData\":\"\",\"fieldKey\":\"remark\"}],\"sectionLabel\":\"\"},\"assetsInfo\":{\"typeName\":\"房产信息表\",\"fieldList\":[{\"isEdited\":\"0\",\"inputType\":\"3\",\"label\":\"房产类型\",\"defaultValue\":\"\",\"isRequested\":\"0\",\"inputData\":\"fangType\",\"fieldKey\":\"estateType\"},{\"isEdited\":\"0\",\"inputType\":\"5\",\"label\":\"房产所在省\",\"defaultValue\":\"\",\"isRequested\":\"0\",\"inputData\":\"estate-state\",\"fieldKey\":\"estateState\"},{\"isEdited\":\"0\",\"inputType\":\"5\",\"label\":\"房产所在市\",\"defaultValue\":\"\",\"isRequested\":\"0\",\"inputData\":\"estate-city\",\"fieldKey\":\"estateCity\"},{\"isEdited\":\"0\",\"inputType\":\"5\",\"label\":\"房产所在区\",\"defaultValue\":\"\",\"isRequested\":\"0\",\"inputData\":\"estate-zone\",\"fieldKey\":\"estateZone\"},{\"isEdited\":\"0\",\"inputType\":\"0\",\"label\":\"房产地址\",\"defaultValue\":\"\",\"isRequested\":\"0\",\"inputData\":\"\",\"fieldKey\":\"estateAddress\"},{\"isEdited\":\"0\",\"inputType\":\"3\",\"label\":\"房贷情况\",\"defaultValue\":\"\",\"isRequested\":\"0\",\"inputData\":\"estateType\",\"fieldKey\":\"estateLoan\"},{\"isEdited\":\"0\",\"inputType\":\"7\",\"label\":\"购买时间\",\"defaultValue\":\"\",\"isRequested\":\"0\",\"inputData\":\"\",\"fieldKey\":\"buyDate\"},{\"isEdited\":\"0\",\"inputType\":\"0\",\"label\":\"购买总价值/万元\",\"defaultValue\":\"\",\"isRequested\":\"0\",\"inputData\":\"\",\"fieldKey\":\"estateAmt\"},{\"isEdited\":\"0\",\"inputType\":\"0\",\"label\":\"市值参考价/万元\",\"defaultValue\":\"\",\"isRequested\":\"0\",\"inputData\":\"\",\"fieldKey\":\"referenceAmt\"},{\"isEdited\":\"0\",\"inputType\":\"0\",\"label\":\"房贷金额/万元\",\"defaultValue\":\"\",\"isRequested\":\"0\",\"inputData\":\"\",\"fieldKey\":\"estateLoanAmt\"},{\"isEdited\":\"0\",\"inputType\":\"0\",\"label\":\"月供\",\"defaultValue\":\"\",\"isRequested\":\"0\",\"inputData\":\"\",\"fieldKey\":\"monthPaymentAmt\"},{\"isEdited\":\"0\",\"inputType\":\"0\",\"label\":\"已还期数\",\"defaultValue\":\"\",\"isRequested\":\"0\",\"inputData\":\"\",\"fieldKey\":\"hasRepaymentNum\"},{\"isEdited\":\"0\",\"inputType\":\"0\",\"label\":\"建筑面积\",\"defaultValue\":\"\",\"isRequested\":\"0\",\"inputData\":\"\",\"fieldKey\":\"builtupArea\"},{\"isEdited\":\"0\",\"inputType\":\"3\",\"label\":\"房产所有权\",\"defaultValue\":\"\",\"isRequested\":\"0\",\"inputData\":\"houseOwnerType\",\"fieldKey\":\"houseOwnership\"},{\"isEdited\":\"0\",\"inputType\":\"0\",\"label\":\"产权比例\",\"defaultValue\":\"\",\"isRequested\":\"1\",\"inputData\":\"\",\"fieldKey\":\"equityRate\"},{\"isEdited\":\"0\",\"inputType\":\"0\",\"label\":\"共有人姓名\",\"defaultValue\":\"\",\"isRequested\":\"1\",\"inputData\":\"\",\"fieldKey\":\"otherName\"},{\"isEdited\":\"0\",\"inputType\":\"0\",\"label\":\"共有人身份证号\",\"defaultValue\":\"\",\"isRequested\":\"0\",\"inputData\":\"\",\"fieldKey\":\"otherIdNo\"},{\"isEdited\":\"0\",\"inputType\":\"3\",\"label\":\"单位户名为本人\",\"defaultValue\":\"\",\"isRequested\":\"0\",\"inputData\":\"indicator\",\"fieldKey\":\"ifMe\"}],\"sectionLabel\":\"房产信息表\",\"expandList\":[{\"fieldList\":[{\"isEdited\":\"0\",\"inputType\":\"3\",\"label\":\"条件类型\",\"defaultValue\":\"\",\"isRequested\":\"1\",\"inputData\":\"conditionType\",\"fieldKey\":\"conditionType\"}],\"sectionLabel\":\"随薪贷信息表\"},{\"fieldList\":[{\"isEdited\":\"0\",\"inputType\":\"3\",\"label\":\"车辆类型\",\"defaultValue\":\"\",\"isRequested\":\"0\",\"inputData\":\"carType\",\"fieldKey\":\"carType\"},{\"isEdited\":\"0\",\"inputType\":\"3\",\"label\":\"是否有车贷\",\"defaultValue\":\"\",\"isRequested\":\"0\",\"inputData\":\"indicator\",\"fieldKey\":\"carLoan\"},{\"isEdited\":\"0\",\"inputType\":\"7\",\"label\":\"购买时间\",\"defaultValue\":\"\",\"isRequested\":\"0\",\"inputData\":\"\",\"fieldKey\":\"carBuyDate\"},{\"isEdited\":\"0\",\"inputType\":\"0\",\"label\":\"裸车价/万元\",\"defaultValue\":\"\",\"isRequested\":\"0\",\"inputData\":\"\",\"fieldKey\":\"nakedCarPrice\"},{\"isEdited\":\"0\",\"inputType\":\"0\",\"label\":\"购买价/万元\",\"defaultValue\":\"\",\"isRequested\":\"0\",\"inputData\":\"\",\"fieldKey\":\"carBuyPrice\"},{\"isEdited\":\"0\",\"inputType\":\"0\",\"label\":\"贷款剩余期数\",\"defaultValue\":\"\",\"isRequested\":\"0\",\"inputData\":\"\",\"fieldKey\":\"carLoanTerm\"},{\"isEdited\":\"0\",\"inputType\":\"0\",\"label\":\"月供\",\"defaultValue\":\"\",\"isRequested\":\"0\",\"inputData\":\"\",\"fieldKey\":\"monthPaymentAmt\"},{\"isEdited\":\"0\",\"inputType\":\"3\",\"label\":\"本地车牌\",\"defaultValue\":\"\",\"isRequested\":\"0\",\"inputData\":\"indicator\",\"fieldKey\":\"localPlate\"}],\"sectionLabel\":\"车辆信息表\"},{\"fieldList\":[{\"isEdited\":\"0\",\"inputType\":\"0\",\"label\":\"保险金额\",\"defaultValue\":\"\",\"isRequested\":\"0\",\"inputData\":\"\",\"fieldKey\":\"insuranceAmt\"},{\"isEdited\":\"0\",\"inputType\":\"0\",\"label\":\"保险年限\",\"defaultValue\":\"\",\"isRequested\":\"0\",\"inputData\":\"\",\"fieldKey\":\"insuranceTerm\"},{\"isEdited\":\"0\",\"inputType\":\"0\",\"label\":\"已缴年限\",\"defaultValue\":\"\",\"isRequested\":\"0\",\"inputData\":\"\",\"fieldKey\":\"paidTerm\"},{\"isEdited\":\"0\",\"inputType\":\"0\",\"label\":\"最近一次缴费时间\",\"defaultValue\":\"\",\"isRequested\":\"0\",\"inputData\":\"\",\"fieldKey\":\"lastPaymentDate\"},{\"isEdited\":\"0\",\"inputType\":\"3\",\"label\":\"缴费方式\",\"defaultValue\":\"\",\"isRequested\":\"0\",\"inputData\":\"paymentMethod\",\"fieldKey\":\"paymentMethod\"},{\"isEdited\":\"0\",\"inputType\":\"3\",\"label\":\"与被保险人关系\",\"defaultValue\":\"\",\"isRequested\":\"0\",\"inputData\":\"policyRelation\",\"fieldKey\":\"policyRelation\"},{\"isEdited\":\"0\",\"inputType\":\"0\",\"label\":\"年缴金额\",\"defaultValue\":\"\",\"isRequested\":\"0\",\"inputData\":\"\",\"fieldKey\":\"yaerPaymentAmt\"},{\"isEdited\":\"0\",\"inputType\":\"3\",\"label\":\"保单真伪核实方式\",\"defaultValue\":\"\",\"isRequested\":\"0\",\"inputData\":\"policyCheck\",\"fieldKey\":\"policyCheck\"}],\"sectionLabel\":\"保单信息表\"},{\"fieldList\":[{\"isEdited\":\"1\",\"inputType\":\"7\",\"label\":\"开户时间\",\"defaultValue\":\"\",\"isRequested\":\"0\",\"inputData\":\"\",\"fieldKey\":\"openAccountDate\"},{\"isEdited\":\"0\",\"inputType\":\"0\",\"label\":\"缴存比例\",\"defaultValue\":\"\",\"isRequested\":\"0\",\"inputData\":\"\",\"fieldKey\":\"depositRate\"},{\"isEdited\":\"0\",\"inputType\":\"0\",\"label\":\"月缴存额\",\"defaultValue\":\"\",\"isRequested\":\"0\",\"inputData\":\"\",\"fieldKey\":\"monthDepositAmt\"},{\"isEdited\":\"0\",\"inputType\":\"0\",\"label\":\"缴存基数\",\"defaultValue\":\"\",\"isRequested\":\"0\",\"inputData\":\"\",\"fieldKey\":\"depositBase\"},{\"isEdited\":\"0\",\"inputType\":\"3\",\"label\":\"公积金材料\",\"defaultValue\":\"A\",\"isRequested\":\"0\",\"inputData\":\"providentInfo\",\"fieldKey\":\"providentInfo\"},{\"isEdited\":\"0\",\"inputType\":\"3\",\"label\":\"缴存单位同申请单位\",\"defaultValue\":\"\",\"isRequested\":\"0\",\"inputData\":\"indicator\",\"fieldKey\":\"paymentUnit\"},{\"isEdited\":\"0\",\"inputType\":\"0\",\"label\":\"申请单位已缴月数\",\"defaultValue\":\"\",\"isRequested\":\"0\",\"inputData\":\"\",\"fieldKey\":\"paymentMonthNum\"}],\"sectionLabel\":\"公积金信息表\"},{\"fieldList\":[{\"isEdited\":\"1\",\"inputType\":\"7\",\"label\":\"发卡时间\",\"defaultValue\":\"\",\"isRequested\":\"0\",\"inputData\":\"\",\"fieldKey\":\"startDate\"},{\"isEdited\":\"0\",\"inputType\":\"0\",\"label\":\"额度\",\"defaultValue\":\"\",\"isRequested\":\"0\",\"inputData\":\"\",\"fieldKey\":\"creditLimit\"},{\"isEdited\":\"0\",\"inputType\":\"12\",\"label\":\"近4个月账单金额依次为\",\"defaultValue\":\"\",\"isRequested\":\"0\",\"inputData\":\"\",\"fieldKey\":\"billAmt\"},{\"isEdited\":\"0\",\"inputType\":\"0\",\"label\":\"月均\",\"defaultValue\":\"\",\"isRequested\":\"0\",\"inputData\":\"\",\"fieldKey\":\"payMonthAmt\"}],\"sectionLabel\":\"卡友贷信息\"},{\"fieldList\":[{\"isEdited\":\"0\",\"inputType\":\"3\",\"label\":\"京东用户等级\",\"defaultValue\":\"\",\"isRequested\":\"0\",\"inputData\":\"jiDongUserLevel\",\"fieldKey\":\"jiDongUserLevel\"},{\"isEdited\":\"0\",\"inputType\":\"0\",\"label\":\"小白信用分\",\"defaultValue\":\"\",\"isRequested\":\"0\",\"inputData\":\"\",\"fieldKey\":\"whiteCreditValue\"},{\"isEdited\":\"0\",\"inputType\":\"0\",\"label\":\"近一年实际消费金额\",\"defaultValue\":\"\",\"isRequested\":\"0\",\"inputData\":\"\",\"fieldKey\":\"pastYearShoppingAmount\"}],\"sectionLabel\":\"网购达人贷B信息\"},{\"fieldList\":[{\"isEdited\":\"0\",\"inputType\":\"7\",\"label\":\"开店时间\",\"defaultValue\":\"\",\"isRequested\":\"0\",\"inputData\":\"\",\"fieldKey\":\"registerDate\"},{\"isEdited\":\"0\",\"inputType\":\"3\",\"label\":\"买家信誉等级\",\"defaultValue\":\"\",\"isRequested\":\"0\",\"inputData\":\"sellerCreditLevel\",\"fieldKey\":\"buyerCreditLevel\"},{\"isEdited\":\"0\",\"inputType\":\"3\",\"label\":\"买家信用类型\",\"defaultValue\":\"\",\"isRequested\":\"0\",\"inputData\":\"sellerCreditType\",\"fieldKey\":\"buyerCreditType\"},{\"isEdited\":\"0\",\"inputType\":\"0\",\"label\":\"好评率\",\"defaultValue\":\"\",\"isRequested\":\"0\",\"inputData\":\"\",\"fieldKey\":\"goodRate\"},{\"isEdited\":\"0\",\"inputType\":\"0\",\"label\":\"上一年度支出额\",\"defaultValue\":\"\",\"isRequested\":\"0\",\"inputData\":\"\",\"fieldKey\":\"lastYearPayAmt\"},{\"isEdited\":\"0\",\"inputType\":\"11\",\"label\":\"近三个月支出总额\",\"defaultValue\":\"\",\"isRequested\":\"0\",\"inputData\":\"\",\"fieldKey\":\"payAmt\"}],\"sectionLabel\":\"网购达人贷信息表\"},{\"fieldList\":[{\"isEdited\":\"0\",\"inputType\":\"7\",\"label\":\"开店时间\",\"defaultValue\":\"\",\"isRequested\":\"0\",\"inputData\":\"\",\"fieldKey\":\"setupShopDate\"},{\"isEdited\":\"0\",\"inputType\":\"3\",\"label\":\"卖家信誉等级\",\"defaultValue\":\"\",\"isRequested\":\"0\",\"inputData\":\"sellerCreditLevel\",\"fieldKey\":\"sellerCreditLevel\"},{\"isEdited\":\"0\",\"inputType\":\"3\",\"label\":\"卖家信用类型\",\"defaultValue\":\"\",\"isRequested\":\"0\",\"inputData\":\"sellerCreditType\",\"fieldKey\":\"sellerCreditType\"},{\"isEdited\":\"0\",\"inputType\":\"0\",\"label\":\"近半年好评数\",\"defaultValue\":\"\",\"isRequested\":\"0\",\"inputData\":\"\",\"fieldKey\":\"regardedNum\"},{\"isEdited\":\"0\",\"inputType\":\"14\",\"label\":\"近6个月账单金额依次为\",\"defaultValue\":\"\",\"isRequested\":\"0\",\"inputData\":\"\",\"fieldKey\":\"biullAmt\"},{\"isEdited\":\"0\",\"inputType\":\"0\",\"label\":\"月均\",\"defaultValue\":\"\",\"isRequested\":\"0\",\"inputData\":\"\",\"fieldKey\":\"payMonthAmt\"}],\"sectionLabel\":\"淘宝商户贷信息表\"}]},\"contactPersonInfo\":{\"typeName\":\"联系人信息\",\"fieldList\":[{\"isEdited\":\"1\",\"inputType\":\"0\",\"label\":\"姓名\",\"defaultValue\":\"\",\"isRequested\":\"1\",\"inputData\":\"\",\"fieldKey\":\"contactName\"},{\"isEdited\":\"0\",\"inputType\":\"3\",\"label\":\"与申请人关系\",\"defaultValue\":\"\",\"isRequested\":\"1\",\"inputData\":\"relationship\",\"fieldKey\":\"contactRelation\"},{\"isEdited\":\"1\",\"inputType\":\"0\",\"label\":\"身份证号码\",\"defaultValue\":\"\",\"isRequested\":\"0\",\"inputData\":\"\",\"fieldKey\":\"contactIdNo\"},{\"isEdited\":\"0\",\"inputType\":\"3\",\"label\":\"性别\",\"defaultValue\":\"\",\"isRequested\":\"0\",\"inputData\":\"gender\",\"fieldKey\":\"contactGender\"},{\"isEdited\":\"1\",\"inputType\":\"0\",\"label\":\"手机\",\"defaultValue\":\"\",\"isRequested\":\"1\",\"inputData\":\"\",\"fieldKey\":\"contactMobile\"},{\"isEdited\":\"0\",\"inputType\":\"3\",\"label\":\"是否知晓贷款\",\"defaultValue\":\"N\",\"isRequested\":\"1\",\"inputData\":\"indicator\",\"fieldKey\":\"ifKnowLoan\"},{\"isEdited\":\"1\",\"inputType\":\"0\",\"label\":\"公司名称\",\"defaultValue\":\"\",\"isRequested\":\"0\",\"inputData\":\"\",\"fieldKey\":\"contactEmpName\"},{\"isEdited\":\"0\",\"inputType\":\"3\",\"label\":\"职务\",\"defaultValue\":\"\",\"isRequested\":\"0\",\"inputData\":\"empPositionAttrType\",\"fieldKey\":\"contactCorpPost\"},{\"isEdited\":\"1\",\"inputType\":\"0\",\"label\":\"公司电话号码\",\"defaultValue\":\"\",\"isRequested\":\"0\",\"inputData\":\"\",\"fieldKey\":\"contactCorpPhone\"},{\"isEdited\":\"1\",\"inputType\":\"0\",\"label\":\"公司传真\",\"defaultValue\":\"\",\"isRequested\":\"0\",\"inputData\":\"\",\"fieldKey\":\"contactCorpFax\"}],\"sectionLabel\":\"\"},\"empItemInfo\":{\"typeName\":\"工作信息\",\"fieldList\":[{\"isEdited\":\"1\",\"inputType\":\"0\",\"label\":\"单位名称\",\"defaultValue\":\"\",\"isRequested\":\"1\",\"inputData\":\"\",\"fieldKey\":\"corpName\"},{\"isEdited\":\"0\",\"inputType\":\"3\",\"label\":\"是否私营业主\",\"defaultValue\":\"N\",\"isRequested\":\"1\",\"inputData\":\"indicator\",\"fieldKey\":\"privateOwnersFlag\"},{\"isEdited\":\"0\",\"inputType\":\"5\",\"label\":\"公司所在省\",\"defaultValue\":\"\",\"isRequested\":\"1\",\"inputData\":\"emp-state\",\"fieldKey\":\"empProvince\"},{\"isEdited\":\"0\",\"inputType\":\"5\",\"label\":\"公司所在市\",\"defaultValue\":\"\",\"isRequested\":\"1\",\"inputData\":\"emp-city\",\"fieldKey\":\"empCity\"},{\"isEdited\":\"0\",\"inputType\":\"5\",\"label\":\"公司所在区/县\",\"defaultValue\":\"\",\"isRequested\":\"1\",\"inputData\":\"emp-zone\",\"fieldKey\":\"empZone\"},{\"isEdited\":\"1\",\"inputType\":\"0\",\"label\":\"公司地址\",\"defaultValue\":\"\",\"isRequested\":\"1\",\"inputData\":\"\",\"fieldKey\":\"empAdd\"},{\"isEdited\":\"1\",\"inputType\":\"0\",\"label\":\"公司邮编\",\"defaultValue\":\"\",\"isRequested\":\"0\",\"inputData\":\"\",\"fieldKey\":\"empPostcode\"},{\"isEdited\":\"0\",\"inputType\":\"3\",\"label\":\"工商网信息\",\"defaultValue\":\"\",\"isRequested\":\"1\",\"inputData\":\"businessNetWork\",\"fieldKey\":\"businessNetWork\"},{\"isEdited\":\"0\",\"inputType\":\"3\",\"label\":\"公司性质\",\"defaultValue\":\"\",\"isRequested\":\"1\",\"inputData\":\"corpStructure\",\"fieldKey\":\"empStructure\"},{\"isEdited\":\"0\",\"inputType\":\"3\",\"label\":\"公司行业类别\",\"defaultValue\":\"\",\"isRequested\":\"1\",\"inputData\":\"empType\",\"fieldKey\":\"empType\"},{\"isEdited\":\"1\",\"inputType\":\"0\",\"label\":\"任职部门\",\"defaultValue\":\"\",\"isRequested\":\"1\",\"inputData\":\"\",\"fieldKey\":\"empDepapment\"},{\"isEdited\":\"0\",\"inputType\":\"3\",\"label\":\"职务\",\"defaultValue\":\"\",\"isRequested\":\"1\",\"inputData\":\"noGovInstitution\",\"fieldKey\":\"empPost\"},{\"isEdited\":\"0\",\"inputType\":\"3\",\"label\":\"职业\",\"defaultValue\":\"\",\"isRequested\":\"1\",\"inputData\":\"occupationType\",\"fieldKey\":\"occupation\"},{\"isEdited\":\"1\",\"inputType\":\"0\",\"label\":\"单电\",\"defaultValue\":\"\",\"isRequested\":\"1\",\"inputData\":\"\",\"fieldKey\":\"comPhoneNo\"},{\"isEdited\":\"1\",\"inputType\":\"0\",\"label\":\"单电2\",\"defaultValue\":\"\",\"isRequested\":\"0\",\"inputData\":\"\",\"fieldKey\":\"corpPhone2\"},{\"isEdited\":\"1\",\"inputType\":\"7\",\"label\":\"入职时间\",\"defaultValue\":\"\",\"isRequested\":\"1\",\"inputData\":\"\",\"fieldKey\":\"startDate\"},{\"isEdited\":\"0\",\"inputType\":\"3\",\"label\":\"发薪方式\",\"defaultValue\":\"\",\"isRequested\":\"1\",\"inputData\":\"corpPayWay\",\"fieldKey\":\"corpPayWay\"},{\"isEdited\":\"1\",\"inputType\":\"0\",\"label\":\"单位月收入/元\",\"defaultValue\":\"\",\"isRequested\":\"1\",\"inputData\":\"\",\"fieldKey\":\"monthSalary\"},{\"isEdited\":\"1\",\"inputType\":\"0\",\"label\":\"其他月收入\",\"defaultValue\":\"\",\"isRequested\":\"0\",\"inputData\":\"\",\"fieldKey\":\"otherIncome\"},{\"isEdited\":\"0\",\"inputType\":\"0\",\"label\":\"月总收入/元\",\"defaultValue\":\"\",\"isRequested\":\"0\",\"inputData\":\"\",\"fieldKey\":\"totalMonthSalary\"}],\"sectionLabel\":\"工作信息\",\"expandList\":[{\"fieldList\":[{\"isEdited\":\"0\",\"inputType\":\"3\",\"label\":\"成立时间\",\"defaultValue\":\"\",\"isRequested\":\"1\",\"inputData\":\"\",\"fieldKey\":\"setupDate\"},{\"isEdited\":\"1\",\"inputType\":\"0\",\"label\":\"占股比例/%\",\"defaultValue\":\"\",\"isRequested\":\"1\",\"inputData\":\"\",\"fieldKey\":\"sharesScale\"},{\"isEdited\":\"1\",\"inputType\":\"0\",\"label\":\"注册资本/万元\",\"defaultValue\":\"\",\"isRequested\":\"1\",\"inputData\":\"\",\"fieldKey\":\"registerFunds\"},{\"isEdited\":\"0\",\"inputType\":\"3\",\"label\":\"私营企业类型\",\"defaultValue\":\"\",\"isRequested\":\"1\",\"inputData\":\"priEnterpriseType\",\"fieldKey\":\"priEnterpriseType\"},{\"isEdited\":\"0\",\"inputType\":\"3\",\"label\":\"经营场所\",\"defaultValue\":\"\",\"isRequested\":\"1\",\"inputData\":\"businessPlace\",\"fieldKey\":\"businessPlace\"},{\"isEdited\":\"1\",\"inputType\":\"0\",\"label\":\"月租金/元\",\"defaultValue\":\"\",\"isRequested\":\"0\",\"inputData\":\"\",\"fieldKey\":\"monthRent\"},{\"isEdited\":\"1\",\"inputType\":\"0\",\"label\":\"员工人数/人\",\"defaultValue\":\"\",\"isRequested\":\"1\",\"inputData\":\"\",\"fieldKey\":\"employeeNum\"},{\"isEdited\":\"1\",\"inputType\":\"0\",\"label\":\"企业净利润率/%\",\"defaultValue\":\"\",\"isRequested\":\"1\",\"inputData\":\"\",\"fieldKey\":\"enterpriseRate\"},{\"isEdited\":\"1\",\"inputType\":\"0\",\"label\":\"股东姓名(除客户外最大股东)\",\"defaultValue\":\"\",\"isRequested\":\"0\",\"inputData\":\"\",\"fieldKey\":\"sharesName\"},{\"isEdited\":\"1\",\"inputType\":\"0\",\"label\":\"股东身份证\",\"defaultValue\":\"\",\"isRequested\":\"0\",\"inputData\":\"\",\"fieldKey\":\"sharesIdNo\"},{\"isEdited\":\"1\",\"inputType\":\"0\",\"label\":\"每月净利润额/万元\",\"defaultValue\":\"\",\"isRequested\":\"1\",\"inputData\":\"\",\"fieldKey\":\"monthAmt\"}],\"sectionLabel\":\"私营业主信息\"}]},\"persionInfo\":{\"typeName\":\"个人信息\",\"fieldList\":[{\"isEdited\":\"0\",\"inputType\":\"0\",\"label\":\"姓名\",\"defaultValue\":\"\",\"isRequested\":\"0\",\"inputData\":\"\",\"fieldKey\":\"name\"},{\"isEdited\":\"0\",\"inputType\":\"0\",\"label\":\"身份证号码\",\"defaultValue\":\"\",\"isRequested\":\"0\",\"inputData\":\"\",\"fieldKey\":\"idNo\"},{\"isEdited\":\"0\",\"inputType\":\"0\",\"label\":\"性别\",\"defaultValue\":\"\",\"isRequested\":\"0\",\"inputData\":\"\",\"fieldKey\":\"gender\"},{\"isEdited\":\"0\",\"inputType\":\"0\",\"label\":\"年龄\",\"defaultValue\":\"\",\"isRequested\":\"0\",\"inputData\":\"\",\"fieldKey\":\"age\"},{\"isEdited\":\"0\",\"inputType\":\"3\",\"label\":\"婚姻状况\",\"defaultValue\":\"\",\"isRequested\":\"1\",\"inputData\":\"maritalStatus\",\"fieldKey\":\"maritalStatus\"},{\"isEdited\":\"1\",\"inputType\":\"0\",\"label\":\"子女数\",\"defaultValue\":\"\",\"isRequested\":\"0\",\"inputData\":\"\",\"fieldKey\":\"childrenNum\"},{\"isEdited\":\"0\",\"inputType\":\"3\",\"label\":\"最高学历\",\"defaultValue\":\"\",\"isRequested\":\"1\",\"inputData\":\"educationType\",\"fieldKey\":\"qualification\"},{\"isEdited\":\"0\",\"inputType\":\"7\",\"label\":\"毕业时间\",\"defaultValue\":\"\",\"isRequested\":\"0\",\"inputData\":\"\",\"fieldKey\":\"graduationDate\"},{\"isEdited\":\"0\",\"inputType\":\"5\",\"label\":\"户籍所在省\",\"defaultValue\":\"\",\"isRequested\":\"1\",\"inputData\":\"iss-state\",\"fieldKey\":\"issState\"},{\"isEdited\":\"0\",\"inputType\":\"5\",\"label\":\"户籍所在市\",\"defaultValue\":\"\",\"isRequested\":\"1\",\"inputData\":\"iss-city\",\"fieldKey\":\"issCity\"},{\"isEdited\":\"0\",\"inputType\":\"5\",\"label\":\"户籍所在区\",\"defaultValue\":\"\",\"isRequested\":\"1\",\"inputData\":\"iss-zone\",\"fieldKey\":\"issZone\"},{\"isEdited\":\"1\",\"inputType\":\"0\",\"label\":\"户籍地址\",\"defaultValue\":\"\",\"isRequested\":\"1\",\"inputData\":\"\",\"fieldKey\":\"idIssuerAddress\"},{\"isEdited\":\"1\",\"inputType\":\"0\",\"label\":\"户籍邮编\",\"defaultValue\":\"\",\"isRequested\":\"0\",\"inputData\":\"\",\"fieldKey\":\"issuerPostcode\"},{\"isEdited\":\"0\",\"inputType\":\"5\",\"label\":\"家庭所在省\",\"defaultValue\":\"\",\"isRequested\":\"1\",\"inputData\":\"home-state\",\"fieldKey\":\"homeState\"},{\"isEdited\":\"0\",\"inputType\":\"5\",\"label\":\"家庭所在市\",\"defaultValue\":\"\",\"isRequested\":\"1\",\"inputData\":\"home-city\",\"fieldKey\":\"homeCity\"},{\"isEdited\":\"0\",\"inputType\":\"5\",\"label\":\"家庭所在区县\",\"defaultValue\":\"\",\"isRequested\":\"1\",\"inputData\":\"home-zone\",\"fieldKey\":\"homeZone\"},{\"isEdited\":\"1\",\"inputType\":\"0\",\"label\":\"家庭地址\",\"defaultValue\":\"\",\"isRequested\":\"1\",\"inputData\":\"\",\"fieldKey\":\"homeAdd\"},{\"isEdited\":\"1\",\"inputType\":\"0\",\"label\":\"家庭住宅邮编\",\"defaultValue\":\"\",\"isRequested\":\"0\",\"inputData\":\"\",\"fieldKey\":\"homePostcode\"},{\"isEdited\":\"0\",\"inputType\":\"3\",\"label\":\"住宅类型\",\"defaultValue\":\"\",\"isRequested\":\"1\",\"inputData\":\"houseType\",\"fieldKey\":\"houseType\"},{\"isEdited\":\"1\",\"inputType\":\"0\",\"label\":\"租金/元\",\"defaultValue\":\"\",\"isRequested\":\"0\",\"inputData\":\"\",\"fieldKey\":\"houseRent\"},{\"isEdited\":\"1\",\"inputType\":\"0\",\"label\":\"每月家庭支出\",\"defaultValue\":\"\",\"isRequested\":\"1\",\"inputData\":\"\",\"fieldKey\":\"monthPay\"},{\"isEdited\":\"1\",\"inputType\":\"0\",\"label\":\"可接受的月最高还款\",\"defaultValue\":\"\",\"isRequested\":\"1\",\"inputData\":\"\",\"fieldKey\":\"monthMaxRepay\"},{\"isEdited\":\"1\",\"inputType\":\"0\",\"label\":\"手机1\",\"defaultValue\":\"\",\"isRequested\":\"1\",\"inputData\":\"\",\"fieldKey\":\"cellphone\"},{\"isEdited\":\"1\",\"inputType\":\"0\",\"label\":\"手机2\",\"defaultValue\":\"\",\"isRequested\":\"0\",\"inputData\":\"\",\"fieldKey\":\"cellphone2\"},{\"isEdited\":\"1\",\"inputType\":\"0\",\"label\":\"宅电\",\"defaultValue\":\"\",\"isRequested\":\"0\",\"inputData\":\"\",\"fieldKey\":\"homePhoneNo1\"},{\"isEdited\":\"1\",\"inputType\":\"0\",\"label\":\"QQ号\",\"defaultValue\":\"\",\"isRequested\":\"0\",\"inputData\":\"\",\"fieldKey\":\"qq\"},{\"isEdited\":\"1\",\"inputType\":\"0\",\"label\":\"微信号\",\"defaultValue\":\"\",\"isRequested\":\"0\",\"inputData\":\"\",\"fieldKey\":\"wechat\"},{\"isEdited\":\"1\",\"inputType\":\"0\",\"label\":\"电子邮箱\",\"defaultValue\":\"\",\"isRequested\":\"0\",\"inputData\":\"\",\"fieldKey\":\"email\"}],\"sectionLabel\":\"\"},\"listData\":{\"providentInfo\":[{\"title\":\"官方网站\",\"name\":\"A\"},{\"title\":\"网银账户\",\"name\":\"B\"},{\"title\":\"中心证明\",\"name\":\"C\"},{\"title\":\"人行记录\",\"name\":\"D\"}],\"indicator\":[{\"title\":\"是\",\"name\":\"Y\"},{\"title\":\"否\",\"name\":\"N\"}],\"creditApplication\":[{\"title\":\"资金周转\",\"name\":\"00001\"},{\"title\":\"扩大经营\",\"name\":\"00002\"},{\"title\":\"购生活品\",\"name\":\"00003\"},{\"title\":\"购原材料\",\"name\":\"00004\"},{\"title\":\"购设备\",\"name\":\"00005\"},{\"title\":\"教育支出\",\"name\":\"00006\"},{\"title\":\"装修家居\",\"name\":\"00007\"},{\"title\":\"医疗\",\"name\":\"00008\"},{\"title\":\"旅游\",\"name\":\"00009\"},{\"title\":\"购买\",\"name\":\"00010\"},{\"title\":\"其他\",\"name\":\"00011\"}],\"corpPayWay\":[{\"title\":\"网银\",\"name\":\"00001\"},{\"title\":\"现金\",\"name\":\"00002\"},{\"title\":\"网银+现金\",\"name\":\"00003\"}],\"estateType\":[{\"title\":\"还款中\",\"name\":\"ING\"},{\"title\":\"全款购\",\"name\":\"ALL\"},{\"title\":\"已结清\",\"name\":\"END\"}],\"conditionType\":[{\"title\":\"申请人名下或直系亲属名下在进件地有房产\",\"name\":\"A\"},{\"title\":\"本地户籍\",\"name\":\"B\"},{\"title\":\"满足信用条件\",\"name\":\"C\"}],\"noGovInstitution\":[{\"title\":\"法人代表\",\"name\":\"00001\"},{\"title\":\"总经理\",\"name\":\"00002\"},{\"title\":\"副总经理\",\"name\":\"00003\"},{\"title\":\"部门经理\",\"name\":\"00004\"},{\"title\":\"主管\",\"name\":\"00005\"},{\"title\":\"职员\",\"name\":\"00006\"}],\"maritalStatus\":[{\"title\":\"未婚\",\"name\":\"00001\"},{\"title\":\"已婚\",\"name\":\"00002\"},{\"title\":\"离异\",\"name\":\"00003\"},{\"title\":\"其他\",\"name\":\"00004\"}],\"sellerCreditLevel\":[{\"title\":\"1\",\"name\":\"A\"},{\"title\":\"2\",\"name\":\"B\"},{\"title\":\"3\",\"name\":\"C\"},{\"title\":\"4\",\"name\":\"D\"},{\"title\":\"5\",\"name\":\"E\"}],\"businessPlace\":[{\"title\":\"自有\",\"name\":\"00001\"},{\"title\":\"租用\",\"name\":\"00002\"}],\"priEnterpriseType\":[{\"title\":\"个体户\",\"name\":\"00001\"},{\"title\":\"独资\",\"name\":\"00002\"},{\"title\":\"合伙制\",\"name\":\"00003\"},{\"title\":\"股份制\",\"name\":\"00004\"},{\"title\":\"其他\",\"name\":\"00005\"}],\"fangType\":[{\"title\":\"商品房\",\"name\":\"00001\"},{\"title\":\"经济适用房/动迁房/房改房\",\"name\":\"00002\"},{\"title\":\"自建房\",\"name\":\"00003\"}],\"carType\":[{\"title\":\"一手车\",\"name\":\"00001\"},{\"title\":\"二手车\",\"name\":\"00002\"}],\"educationType\":[{\"title\":\"硕士及以上\",\"name\":\"00001\"},{\"title\":\"本科\",\"name\":\"00002\"},{\"title\":\"大专\",\"name\":\"00003\"},{\"title\":\"中专\",\"name\":\"00004\"},{\"title\":\"高中\",\"name\":\"00005\"},{\"title\":\"初中及以下\",\"name\":\"00006\"}],\"policyCheck\":[{\"title\":\"客服热线\",\"name\":\"A\"},{\"title\":\"网站\",\"name\":\"B\"}],\"businessNetWork\":[{\"title\":\"在营\",\"name\":\"A\"},{\"title\":\"注销/吊销/过期\",\"name\":\"C\"},{\"title\":\"查无\",\"name\":\"N\"}],\"corpStructure\":[{\"title\":\"政府机构\",\"name\":\"00001\"},{\"title\":\"事业单位\",\"name\":\"00002\"},{\"title\":\"国企\",\"name\":\"00003\"},{\"title\":\"外资\",\"name\":\"00004\"},{\"title\":\"民营\",\"name\":\"00005\"},{\"title\":\"私营\",\"name\":\"00006\"},{\"title\":\"其它\",\"name\":\"00007\"},{\"title\":\"合资\",\"name\":\"00008\"},{\"title\":\"个体\",\"name\":\"00009\"}],\"gender\":[{\"title\":\"男\",\"name\":\"M\"},{\"title\":\"女\",\"name\":\"F\"}],\"jiDongUserLevel\":[{\"title\":\"注册会员\",\"name\":\"ZC\"},{\"title\":\"铜牌会员\",\"name\":\"TP\"},{\"title\":\"银牌会员\",\"name\":\"YP\"},{\"title\":\"金牌会员\",\"name\":\"JP\"},{\"title\":\"钻石会员\",\"name\":\"ZS\"}],\"occupationType\":[{\"title\":\"工薪\",\"name\":\"00001\"},{\"title\":\"白领\",\"name\":\"00002\"},{\"title\":\"自营\",\"name\":\"00003\"},{\"title\":\"学生\",\"name\":\"00004\"}],\"relationship\":[{\"title\":\"父母\",\"name\":\"00001\"},{\"title\":\"子女\",\"name\":\"00002\"},{\"title\":\"兄弟\",\"name\":\"00003\"},{\"title\":\"姐妹\",\"name\":\"00004\"},{\"title\":\"兄妹\",\"name\":\"00005\"},{\"title\":\"姐弟\",\"name\":\"00006\"},{\"title\":\"朋友\",\"name\":\"00007\"},{\"title\":\"同事\",\"name\":\"00008\"},{\"title\":\"房东\",\"name\":\"00009\"},{\"title\":\"亲属\",\"name\":\"00010\"},{\"title\":\"同学\",\"name\":\"00011\"},{\"title\":\"其它\",\"name\":\"00012\"},{\"title\":\"配偶\",\"name\":\"00013\"}],\"policyRelation\":[{\"title\":\"本人\",\"name\":\"00001\"},{\"title\":\"夫妻\",\"name\":\"00002\"},{\"title\":\"父母\",\"name\":\"00003\"},{\"title\":\"子女\",\"name\":\"00004\"},{\"title\":\"其他\",\"name\":\"00005\"}],\"empType\":[{\"title\":\"农、林、牧、渔业，能源、采矿业\",\"name\":\"00001\"},{\"title\":\"食品、药品、工业原料、服装、日用品等制造业\",\"name\":\"00002\"},{\"title\":\"电力、热力、燃气及水生产和供应业\",\"name\":\"00003\"},{\"title\":\"建筑业\",\"name\":\"00004\"},{\"title\":\"批发和零售业\",\"name\":\"00005\"},{\"title\":\"交通运输、仓储和邮政业\",\"name\":\"00006\"},{\"title\":\"住宿、旅游、餐饮业\",\"name\":\"00007\"},{\"title\":\"信息传输、软件和信息技术服务业\",\"name\":\"00008\"},{\"title\":\"金融业\",\"name\":\"00009\"},{\"title\":\"房地产业\",\"name\":\"00010\"},{\"title\":\"租赁和商务服务业\",\"name\":\"00011\"},{\"title\":\"科学研究和技术服务业\",\"name\":\"00012\"},{\"title\":\"水利、环境和公共设施管理业\",\"name\":\"00013\"},{\"title\":\"居民服务、修理和其他服务业\",\"name\":\"00014\"},{\"title\":\"教育、培训\",\"name\":\"00015\"},{\"title\":\"卫生、医疗、社会保障、社会福利\",\"name\":\"00016\"},{\"title\":\"文化、体育和娱乐业\",\"name\":\"00017\"},{\"title\":\"政府、非赢利机构和社会组织\",\"name\":\"00018\"},{\"title\":\"警察、消防、军人\",\"name\":\"00019\"},{\"title\":\"其他\",\"name\":\"00020\"}],\"houseOwnerType\":[{\"title\":\"客户独有\",\"name\":\"Y\"},{\"title\":\"客户共有\",\"name\":\"O\"},{\"title\":\"非本人名下\",\"name\":\"N\"}],\"houseType\":[{\"title\":\"自有住房\",\"name\":\"00001\"},{\"title\":\"单位住房\",\"name\":\"00002\"},{\"title\":\"亲属住房 \",\"name\":\"00003\"},{\"title\":\"租房\",\"name\":\"00004\"}],\"empPositionAttrType\":[{\"title\":\"高层管理人员\",\"name\":\"A\"},{\"title\":\"中层管理人员\",\"name\":\"B\"},{\"title\":\"基层管理人员\",\"name\":\"C\"},{\"title\":\"一般员工\",\"name\":\"D\"},{\"title\":\"内勤\",\"name\":\"E\"},{\"title\":\"后勤\",\"name\":\"F\"},{\"title\":\"工人\",\"name\":\"G\"},{\"title\":\"销售/中介/业务代表\",\"name\":\"H\"},{\"title\":\"营业员/服务员\",\"name\":\"I\"},{\"title\":\"正部级\",\"name\":\"J\"},{\"title\":\"副部级\",\"name\":\"K\"},{\"title\":\"正厅级\",\"name\":\"L\"},{\"title\":\"副厅级\",\"name\":\"M\"},{\"title\":\"正处级\",\"name\":\"N\"},{\"title\":\"副处级\",\"name\":\"O\"},{\"title\":\"正科级\",\"name\":\"P\"},{\"title\":\"副科级\",\"name\":\"Q\"},{\"title\":\"正股级\",\"name\":\"R\"},{\"title\":\"副股级\",\"name\":\"S\"},{\"title\":\"其他\",\"name\":\"Z\"}],\"sellerCreditType\":[{\"title\":\"红心\",\"name\":\"A\"},{\"title\":\"黄钻\",\"name\":\"B\"},{\"title\":\"红冠\",\"name\":\"C\"},{\"title\":\"紫冠\",\"name\":\"D\"}],\"paymentMethod\":[{\"title\":\"年\",\"name\":\"Y\"},{\"title\":\"月\",\"name\":\"M\"}]}}";
//		
//		JSONObject obj = JSONObject.parseObject(result);
//		
//		System.out.println(obj);
//		
////		Vo_600005 v = new Vo_600005();
////		FuncResult result;
////		try {
//////		Date d = Dates.makeDate("2016-08-07", "yyyy-MM-dd");
////		v.setUserCode("00210945");
////		v.setIdNo("331003198405181376");
////		
////		ApsAppService aas = new ApsAppService();
////		
////
////			result = aas.checkIDCard(v);
////			System.out.println(result.getData().toString());
////		} catch (Exception e) {
////			// TODO Auto-generated catch block
////			e.printStackTrace();
////		}
//		
//	}
//}
