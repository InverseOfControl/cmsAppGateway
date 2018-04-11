package com.zdmoney.credit.applyinfo.service;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang.StringUtils;
import org.apache.http.Consts;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.CookieStore;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ymkj.cms.biz.api.service.master.IBMSAppPersonInfoExecuter;
import com.zdmoney.credit.applyinfo.dao.pub.ITmApplyFieldInfoDao;
import com.zdmoney.credit.applyinfo.domain.TmApplyFieldInfo;
import com.zdmoney.credit.applyinfo.service.pub.CallRecordDetailService;
import com.zdmoney.credit.config.CallDetailAppProperties;
import com.zdmoney.credit.system.dao.pub.ISysParamDefineDao;
import com.zdmoney.credit.system.domain.SysParamDefine;

/**
 * @author YM10189 通话详单接口实现
 */
@Service
public class CallRecordDetailServiceImpl implements CallRecordDetailService {
	
	@Autowired
	IBMSAppPersonInfoExecuter bmsAppPersonInfoExecuter;
	
	// 中国联通号码格式验证
	private static final String CHINA_UNICOM_PATTERN = "(^1(3[0-2]|4[5]|5[56]|7[6]|8[56])\\d{8}$)|(^170[7-9]\\d{7}$)";

	// 中国移动号码格式验证
	private static final String CHINA_MOBILE_PATTERN = "(^1(3[4-9]|4[7]|5[0-27-9]|7[8]|8[2-478])\\d{8}$)|(^1705\\d{7}$)";

	public static String[] unicom_data_key = new String[] { "calldate", "calltime", "homeareaName", "calltypeName",
			"othernum", "calllonghour", "landtype", "romatypeName", "totalfee" };

	public static String[] mobile_data_key = new String[] { "startTime", "commPlac", "commMode", "anotherNm",
			"commTime", "commType", "mealFavorable", "commFee" };

	public static Logger logger = LoggerFactory.getLogger(CallRecordDetailServiceImpl.class);

	@Autowired
	private ITmApplyFieldInfoDao iTmApplyFieldInfoService;
	
	@Autowired
	private CallDetailAppProperties callDetailAppProperties;

	@Autowired
	private ISysParamDefineDao paramDefineDao;

	@Override
	public String queryPhoneOperator(String phoneNum) {
		List<SysParamDefine> list = paramDefineDao.selectRule();
		String key = null;
		if (list != null && list.size() > 0) {
			for (SysParamDefine param : list) {
				if (Pattern.matches(param.getParamValue(), phoneNum) == true) {
					key = param.getParamKey();
				}
			}
		}
		return key;

	}

	@Override
	public Map<String, Object> loginUnicom(Map<String, String> params) throws Exception {
		JSONObject resultObj = null;
		BasicCookieStore cookieStore = new BasicCookieStore();
		Map<String, Object> resultMap = new HashMap<String, Object>();
		String url = new StringBuffer(callDetailAppProperties.getUnicomLoginUrl()).append("?&pwdType=01&productType=01&redirectType=01&rememberMe=1").toString();
		String reqUrl = buildReqParam(params, url);
		HttpPost post = new HttpPost(reqUrl);
		post.setHeader(new BasicHeader("Referer", "https://uac.10010.com/portal/homeLogin"));
		resultObj = sendCookieReq(post, cookieStore);
		if (resultObj != null) {
			if (!"0000".equals(resultObj.getString("resultCode")) || cookieStore.getCookies().isEmpty()) {
				cookieStore = null;
				resultMap.put("msg", resultObj.getString("msg"));
			}

			if ("0000".equals(resultObj.getString("resultCode"))) {
				resultMap.put("msg", "登陆成功!");
			}
			resultMap.put("code", resultObj.getString("resultCode"));
		}
		resultMap.put("cookie", cookieStore);
		return resultMap;
	}

	@Override
	public JSONObject checkUnicom(BasicCookieStore cookieStore, String phoneNum) throws IOException {
		JSONObject jsonObject = null;
		String url = new StringBuffer(callDetailAppProperties.getUnicomCheckUrl()).append("?_=1436239072215")
				.toString();
		HttpClientContext localContext = HttpClientContext.create();
		localContext.setCookieStore(cookieStore);
		HttpPost httppostcheck = new HttpPost(url);
		String result = sendContexReq(httppostcheck, localContext);
		jsonObject = JSON.parseObject(result);
		if (jsonObject.getBoolean("isLogin") == null || !jsonObject.getBoolean("isLogin")) {
			jsonObject = null;
			logger.info("联通用户{},登陆验证失败!{}", phoneNum, result);
			throw new RuntimeException("用户未登陆,请先登陆!");
		}
		return jsonObject;

	}

	@Override
	public List<Map<String, Object>> getUnicomCellRecords(Map<String, Object> params, BasicCookieStore cookieStore)
			throws Exception {
		int mounthNum=6;
		JSONObject jsonObject = null;
		List<JSONObject> callRecords = new ArrayList<JSONObject>();
		List<Map<String, Object>> dataRecords = new ArrayList<Map<String, Object>>();
		String url = new StringBuffer(callDetailAppProperties.getUnicomCallDetailUrl())
				.append("callDetail?_=1436237387020&menuid=000100030001").toString();
		List<Map<String, String>> dateMap = fetchYearMouthForChinaUnion(mounthNum);
		for (Map<String, String> tempMap : dateMap) {
			mounthNum--;
			List<NameValuePair> pairs = new ArrayList<NameValuePair>();
			pairs.add(new BasicNameValuePair("beginDate", tempMap.get("beginDate")));
			pairs.add(new BasicNameValuePair("endDate", tempMap.get("endDate")));
			pairs.add(new BasicNameValuePair("pageNo", "1"));
			pairs.add(new BasicNameValuePair("pageSize", "200000"));
			HttpPost httppost = new HttpPost(url);
			httppost.setEntity(new UrlEncodedFormEntity(pairs));
			String result = sendContexReq(httppost, buildCookieStore(cookieStore));
			jsonObject = JSON.parseObject(result);
			if (jsonObject.getBoolean("isSuccess") == null || !jsonObject.getBoolean("isSuccess")) {
				Thread.sleep(4000L);
				JSONObject errorObj=jsonObject.getJSONObject("errorMessage");
				if(errorObj!=null&&errorObj.getString("respDesc")!=null&&errorObj.getString("respDesc").contains("无详单记录")){
					continue;
				}
				
				if(callRecords.size()>0||mounthNum>0){
					continue;
				}
			}
			callRecords.add(jsonObject);
			Thread.sleep(3000L);
		}
		
		if(callRecords.size()<=0){
			logger.error("联通用户{},获取通话详单失败!{}", params.get("phoneNum"),callRecords.size());
			return dataRecords;
		}
		dataRecords = buildUnicomRecords(callRecords, unicom_data_key);
		return dataRecords;
	}

	@Override
	public String getMoileMsgCode(Map<String, String> params) throws Exception {
		String result = "";
		ArrayList<NameValuePair> paramsList = new ArrayList<NameValuePair>();
		BasicNameValuePair phoneName = new BasicNameValuePair("userName", params.get("userName"));
		BasicNameValuePair type = new BasicNameValuePair("type", "01");
		BasicNameValuePair channelCd = new BasicNameValuePair("channelID", "12003");
		paramsList.add(phoneName);
		paramsList.add(type);
		paramsList.add(channelCd);
		HttpPost httpPost = new HttpPost(callDetailAppProperties.getMobileSendCodeUrl());
		httpPost.setEntity(new UrlEncodedFormEntity(paramsList));
		result = sendReq(httpPost);
		return result;
	}

	@Override
	public Map<String, String> queryMoibleCookie(String phoneNum, String artifact, HttpClientContext httpContext) {
		Map<String, String> cookieMap = new HashMap<String, String>();
		try {
			String reqUrl = new StringBuffer(callDetailAppProperties.getMobileLoginCookieUrl())
					.append("?backUrl=http://shop.10086.cn/i/").append("&").append("artifact=").append(artifact)
					.toString();
			HttpGet httppostcheck = new HttpGet(reqUrl);
			sendContexReq(httppostcheck, httpContext);
			CookieStore cookieStore = httpContext.getCookieStore();
			List<Cookie> cookies = cookieStore.getCookies();
			for (Cookie cookie : cookies) {
				cookieMap.put(cookie.getName(), cookie.getValue());
			}
		} catch (Exception e) {
			cookieMap = null;
			logger.error("移动用户获取cookie异常!用户:{},异常{}", phoneNum, e);
		}
		return cookieMap;
	}

	@Override
	public Map<String, Object> loginMobile(Map<String, String> params) {
		Map<String, String> cookieMap = null;
		Map<String, Object> resultMap = new HashMap<String, Object>();
		HttpClientContext httpContext = HttpClientContext.create();
		try {
			String url = new StringBuffer(callDetailAppProperties.getMobileLoginUrl())
					.append("?accountType=01&pwdType=01&inputCode=&backUrl=http%3A%2F%2Fshop.10086.cn%2Fi%2F&rememberMe=0&channelID=12003&protocol=https%3A&timestamp=")
					.append(new Date().getTime()).toString();
			String reqUrl = buildReqParam(params, url);
			HttpPost httppostcheck = new HttpPost(reqUrl);
			httppostcheck.setHeader("Accept", "application/json, text/javascript, */*; q=0.01");
			httppostcheck.setHeader("Accept-Encoding", "gzip, deflate, sdch");
			httppostcheck.setHeader("Accept-Language", "zh-CN,zh;q=0.8");
			httppostcheck.setHeader("Referer",
					"https://login.10086.cn/login.html?channelID=12003&backUrl=http://shop.10086.cn/i/");
			httppostcheck.setHeader("User-Agent",
					"Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/45.0.2454.101 Safari/537.36");
			httppostcheck.setHeader("X-Requested-With", "XMLHttpRequest");
			String result = sendContexReq(httppostcheck, httpContext);
			JSONObject jsonObj = JSON.parseObject(result);
			resultMap.put("result", jsonObj);
			if (!"0000".equals(jsonObj.getString("code"))) {
				logger.error("移动用户{}登陆失败,返回信息{}", params.get("account"), result);
				throw new RuntimeException("登陆失败!");
			} else {
				cookieMap = queryMoibleCookie(params.get("account"), jsonObj.getString("artifact"), httpContext);
				if (cookieMap != null) {
					cookieMap.put("wtf", params.get("wtf"));
				}
				resultMap.put("cookie", cookieMap);
			}
		} catch (Exception e) {
			cookieMap = null;
			logger.error("移动用户{}登陆异常{}!", params.get("account"), e);
		}
		return resultMap;
	}

	@Override
	public InputStream queryMobileImageCode(Map<String, Object> params) throws Exception {
		InputStream instream = null;
		HttpGet httpGet = new HttpGet(callDetailAppProperties.getMobileImageCodeUrl());
		@SuppressWarnings("unchecked")
		Map<String, Object> cookieMap = (Map<String, Object>) params.get("cookie");
		httpGet.setHeaders(buildMoblileHeader(cookieMap));
		instream = executeStreamHttp(httpGet);
		return instream;
	}

	@Override
	public JSONObject queryMobileMsgCodeAgain(Map<String, Object> cookieMap, String phoneNum) throws Exception {
		JSONObject jsonObject = null;
		String url = new StringBuffer(callDetailAppProperties.getMobileRecordCodeUrl()).append(phoneNum)
				.append("?callback=jQuery18308022154928185046_1492065837127&_=1492065856167").toString();
		HttpGet httpGet = new HttpGet(url);
		httpGet.setHeaders(buildMoblileHeader(cookieMap));
		String result = sendReq(httpGet);
		result = result.substring(result.indexOf("(") + 1, result.lastIndexOf(")"));
		jsonObject = JSONObject.parseObject(result);
		return jsonObject;

	}

	@Override
	public JSONObject loginMobileAgain(Map<String, Object> params, String phoneNum) throws Exception {
		JSONObject jsonObject = null;
		String url = new StringBuffer(callDetailAppProperties.getMobileLoginAginUrl()).append(phoneNum)
				.append("?callback=jQuery18308022154928185046_1492065837127").append("&pwdTempSerCode=")
				.append(params.get("password")).append("&pwdTempRandCode=").append(params.get("smscode"))
				.append("&captchaVal=").append(params.get("piccode")).append("&_=1492135143831").toString();
		HttpGet httpGet = new HttpGet(url);
		httpGet.setHeaders(buildMoblileHeader(params));
		String result = sendReq(httpGet);
		result = result.substring(result.indexOf("(") + 1, result.lastIndexOf(")"));
		jsonObject = JSONObject.parseObject(result);
		return jsonObject;

	}

	public String isRequireSmsCode(Map<String, String> params) throws Exception {
		String result = null;
		String reqUrl = buildReqParam(params, callDetailAppProperties.getMobileIsrequireSmscodeUrl());
		HttpGet httpGet = new HttpGet(reqUrl);
		result = sendReq(httpGet);
		return result;
	}

	@Override
	public List<Map<String, Object>> getMobileCellRecords(Map<String, Object> params, String phoneNum)
			throws Exception {
		List<JSONObject> callRecords = new ArrayList<JSONObject>();
		List<Map<String, Object>> dataRecords = new ArrayList<Map<String, Object>>();
		String url = new StringBuffer(callDetailAppProperties.getMobileCallDetailUrl()).append(phoneNum)
				.append("?callback=jQuery183026626445911824703_1491897264416&curCuror=1&step=100&billType=02&_=1491897284566")
				.toString();
		String[] mouths = fetchYearMouthForChinaMobile(6);
		for (String string : mouths) {
			String reqUrl = new StringBuffer(url).append("&qryMonth=").append(string).toString();
			HttpGet httpGet = new HttpGet(reqUrl);
			httpGet.setHeaders(buildMoblileHeader(params));
			String result = sendReq(httpGet);
			result = result.substring(result.indexOf("(") + 1, result.lastIndexOf(")"));
			JSONObject jsonObj = JSONObject.parseObject(result);
			callRecords.add(jsonObj);
		}
		dataRecords = buildMobileRecords(callRecords, mobile_data_key);
		return dataRecords;
	}

	@Override
	public boolean uploadCellRecords(Map<String, Object> params) {
		boolean isTrue = false;
		String phoneNum = String.valueOf(params.get("phoneNum"));
		String appNo = String.valueOf(params.get("appNo"));
		try {
			byte[] bytes = (byte[]) params.get("contentByte");
			String fileName = getPhonetype(phoneNum) + ".xls";
			ArrayList<NameValuePair> arrayList = new ArrayList<>();
			arrayList.add(new BasicNameValuePair("mobile", phoneNum));
			arrayList.add(new BasicNameValuePair("fileName", fileName));
			arrayList.add(new BasicNameValuePair("fileContent", Base64.encodeBase64String(bytes)));
			arrayList.add(new BasicNameValuePair("field", "1"));
			arrayList.add(new BasicNameValuePair("clientId", String.valueOf(params.get("clientId"))));
			arrayList.add(new BasicNameValuePair("name", String.valueOf(params.get("name"))));
			arrayList.add(new BasicNameValuePair("appNo", appNo));
			HttpPost httpPost = new HttpPost(callDetailAppProperties.getRecordsUploadUrl());
			httpPost.setEntity(new UrlEncodedFormEntity(arrayList, "UTF-8"));
			String result = sendReq(httpPost);
			JSONObject jsonObject = JSON.parseObject(result);
			if ("0000".equals(jsonObject.getString("resCode"))) {
				updateFieldState(appNo, phoneNum);
				isTrue = true;
			}
		} catch (Exception e) {
			logger.error("用户{},上传通话详单异常!{}", phoneNum, e);
			return isTrue;
		}
		return isTrue;
	}

	// 构建请求参数
	public String buildReqParam(Map<String, String> params, String url) {
		int count = 0;
		StringBuffer paramBuf = new StringBuffer(url);
		Iterator<String> keys = params.keySet().iterator();
		while (keys.hasNext()) {
			String key = keys.next();
			String value = params.get(key);
			if (count == 0 && url.indexOf("?") == -1) {
				paramBuf.append("?");
				paramBuf.append(key).append("=").append(value);
			} else {
				paramBuf.append("&");
				paramBuf.append(key).append("=").append(value);
			}
		}
		return paramBuf.toString();
	}

	// 读取输入流
	public static String getStringByStream(InputStream stream) throws IOException {
		String inputLine;
		StringBuffer strBuf = new StringBuffer();
		BufferedReader reader = new BufferedReader(new InputStreamReader(stream, "UTF-8"));
		while ((inputLine = reader.readLine()) != null) {
			strBuf.append(inputLine);
		}
		reader.close();
		return strBuf.toString();
	}

	// 移动通话月份获取
	public String[] fetchYearMouthForChinaMobile(int begMonth) {
		SimpleDateFormat fmt = new SimpleDateFormat("yyyyMM");
		String[] months = new String[begMonth];
		for (int i = 0; i < begMonth; i++) {
			Calendar rightNow = Calendar.getInstance();
			rightNow.add(Calendar.MONTH, 0 - i);
			months[i] = fmt.format(rightNow.getTime());
		}
		return months;
	}

	// 联通通话月份获取
	public List<Map<String, String>> fetchYearMouthForChinaUnion(int begMonth) {
		SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
		List<Map<String, String>> lists = new ArrayList<Map<String, String>>();
		for (int i = 0; i < begMonth; i++) {
			Map<String, String> dateMap = new HashMap<String, String>();
			Calendar rightNow = Calendar.getInstance();
			rightNow.add(Calendar.MONTH, 0 - i);
			rightNow.set(Calendar.DAY_OF_MONTH, 1);
			dateMap.put("beginDate", fmt.format(rightNow.getTime()));
			rightNow.set(Calendar.DAY_OF_MONTH, rightNow.getActualMaximum(Calendar.DAY_OF_MONTH));
			dateMap.put("endDate", fmt.format(rightNow.getTime()));
			lists.add(dateMap);
		}
		return lists;
	}

	// cookie 获取
	public HttpContext buildCookieStore(BasicCookieStore cookieStore) {
		HttpClientContext localContext = HttpClientContext.create();
		localContext.setCookieStore(cookieStore);
		return localContext;
	}

	// 构建联通用户详单数据
	public List<Map<String, Object>> buildUnicomRecords(List<JSONObject> jsonObjs, String[] keys) {
		List<Map<String, Object>> records = new ArrayList<Map<String, Object>>();
		JSONObject jsonObj = null;
		Map<String, Object> dataMap = null;
		for (int i = 0; i < jsonObjs.size(); i++) {
			jsonObj = jsonObjs.get(i).getJSONObject("pageMap");
			if (jsonObj.getIntValue("totalCount") <= 0) {
				continue;
			}
			JSONArray array = jsonObj.getJSONArray("result");
			for (int j = 0; j < array.size(); j++) {
				JSONObject obj = array.getJSONObject(j);
				dataMap = new HashMap<String, Object>();
				dataMap.put("startTime",
						new StringBuffer(obj.getString(keys[0])).append(" ").append(obj.getString(keys[1])).toString());
				dataMap.put("commPlac", obj.getString(keys[2]));
				dataMap.put("commMode", obj.getString(keys[3]));
				dataMap.put("anotherNm",obj.getString(keys[4]));
				dataMap.put("commTime", obj.getString(keys[5]));
				dataMap.put("commType", obj.getString(keys[6]));
				dataMap.put("mealFavorable", obj.getString(keys[7]));
				dataMap.put("commFee", obj.getString(keys[8]));
				records.add(dataMap);
			}
		}
		return records;
	}

	// 构建移动用户详单数据
	public List<Map<String, Object>> buildMobileRecords(List<JSONObject> jsonObjs, String[] keys) {
		List<Map<String, Object>> records = new ArrayList<Map<String, Object>>();
		JSONObject jsonObj = null;
		Map<String, Object> dataMap = null;
		for (int i = 0; i < jsonObjs.size(); i++) {
			jsonObj = jsonObjs.get(i);
			String number = jsonObj.getString("totalNum") == null ? "0" : jsonObj.getString("totalNum");
			if (Integer.parseInt(number) <= 0) {
				continue;
			}
			JSONArray array = jsonObj.getJSONArray("data");
			for (int j = 0; j < array.size(); j++) {
				JSONObject obj = array.getJSONObject(j);
				dataMap = new HashMap<String, Object>();
				dataMap.put("startTime", new StringBuffer(obj.getString(keys[0])));
				dataMap.put("commPlac", obj.getString(keys[1]));
				dataMap.put("commMode", obj.getString(keys[2]));
				dataMap.put("anotherNm", obj.getString(keys[3]));
				dataMap.put("commTime", obj.getString(keys[4]));
				dataMap.put("commType", obj.getString(keys[5]));
				dataMap.put("mealFavorable", obj.getString(keys[6]));
				dataMap.put("commFee", obj.getString(keys[7]));
				records.add(dataMap);
			}
		}
		return records;
	}

	// 构建excel
	public byte[] buildExcelData(List<Map<String, Object>> dataRecords) throws IOException {
		byte[] byteArray = null;
		HSSFWorkbook work = new HSSFWorkbook();
		HSSFSheet sheet = work.createSheet();
		HSSFCellStyle style = work.createCellStyle();
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		HSSFRow rowHead = sheet.createRow(0);
		rowHead.createCell(0).setCellValue("起始时间");
		rowHead.createCell(1).setCellValue("通信地点");
		rowHead.createCell(2).setCellValue("通信方式");
		rowHead.createCell(3).setCellValue("对方号码");
		rowHead.createCell(4).setCellValue("通信时长");
		rowHead.createCell(5).setCellValue("通信类型");
		rowHead.createCell(6).setCellValue("套餐优惠");
		rowHead.createCell(7).setCellValue("实收通信费(元)");

		for (int i = 0; i < dataRecords.size(); i++) {
			HSSFRow row = sheet.createRow(i + 1);
			Map<String, Object> record = dataRecords.get(i);
			row.createCell(0).setCellValue(String.valueOf(record.get("startTime")));
			row.createCell(1).setCellValue(String.valueOf(record.get("commPlac")));
			row.createCell(2).setCellValue(String.valueOf(record.get("commMode")));
			row.createCell(3).setCellValue(String.valueOf(record.get("anotherNm")));
			row.createCell(4).setCellValue(String.valueOf(record.get("commTime")));
			row.createCell(5).setCellValue(String.valueOf(record.get("commType")));
			row.createCell(6).setCellValue(String.valueOf(record.get("mealFavorable")));
			row.createCell(7).setCellValue(String.valueOf(record.get("commFee")));
		}

		ByteArrayOutputStream os = new ByteArrayOutputStream();
		work.write(os);
		byteArray = os.toByteArray();
		os.flush();
		os.close();
		work.close();
		return byteArray;
	}

	// 获取客户手机所属运营商
	public String getPhonetype(String phone) {
		if (Pattern.matches(CHINA_MOBILE_PATTERN, phone) == true) {
			return "移动" + phone;
		} else if (Pattern.matches(CHINA_UNICOM_PATTERN, phone) == true) {
			return "联通" + phone;
		}
		return null;
	}

	// 更新用户详单状态
	public void updateFieldState(String appNo, String phoneNum) {
		if (StringUtils.isNotBlank(appNo)) {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd hh:mm:ss");
			Map<String, Object> map = new HashMap<>();
			map.put("appNo", appNo);
			List<TmApplyFieldInfo> findListByMap =iTmApplyFieldInfoService.findListByMap(map);
			if(findListByMap!=null&&findListByMap.size()>0){
				for (TmApplyFieldInfo m : findListByMap) {
					if (m.getFieldKey().equals("persionInfo")) {
						String objValue = m.getFieldObjValue();
						if (StringUtils.isNotBlank(objValue)) {
							JSONObject parseObject = JSONObject.parseObject(objValue);
							String cellPhone = parseObject.getString("cellphone");
							String cellphoneSec = parseObject.getString("cellphoneSec");
							if (cellPhone.equals(phoneNum)) {
								parseObject.put("cellphone_status", "1");
								parseObject.put("cellphone_time", sdf.format(new Date()));
							} else if (cellphoneSec.equals(phoneNum)) {
								parseObject.put("cellphoneSec_status", "1");
								parseObject.put("cellphoneSec_time", sdf.format(new Date()));
							}
							map.put("id", m.getId());
							map.put("fieldObjValue", parseObject.toString());
							iTmApplyFieldInfoService.upObjInfoById(map);
						}
					}
				}
			}else{
				map.put("phoneNum", phoneNum);
				map.put("phoneStatus", "1");
				map.put("phoneTime", sdf.format(new Date()));
				bmsAppPersonInfoExecuter.updatePhoneCellStatus(map);
			}
		}
	}
	

	// 构建移动cookie信息
	public String buildMobileCookie(Map<String, Object> cookies) {
		StringBuffer cookieBuf = new StringBuffer();
		String[] keys = new String[] { "ssologinprovince", "freelogin_userlogout", "jsessionid-echd-cpt-cmcc-jt",
				"cmccssotoken", "is_login", "userinfokey", "loginName", "c", "verifyCode" };
		for (String string : keys) {
			cookieBuf.append(string).append("=").append(cookies.get(string)).append(";");
		}
		cookieBuf.append("CaptchaCode").append("=").append("WeAPqH").append(";").append(cookies.get("wtf")).append(";");
		cookieBuf.append("CmLocation").append("=").append("371|374; CmProvid=bj");
		return cookieBuf.toString();
	}

	// 移动用户获取详单请求header信息
	public BasicHeader[] buildMoblileHeader(Map<String, Object> params) {
		BasicHeader[] headers = new BasicHeader[7];
		BasicHeader header = new BasicHeader("Accept", "*/*");
		BasicHeader header01 = new BasicHeader("Accept-Encoding", "gzip, deflate, sdch");
		BasicHeader header02 = new BasicHeader("Accept-Language", "zh-CN,zh;q=0.8");
		BasicHeader header03 = new BasicHeader("Connection", "keep-alive");
		BasicHeader header04 = new BasicHeader("Cookie", buildMobileCookie(params));
		BasicHeader header05 = new BasicHeader("Referer", "http://shop.10086.cn/i/?f=home&welcome=1492065855329");
		BasicHeader header06 = new BasicHeader("User-Agent",
				"Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/45.0.2454.101 Safari/537.36");
		headers[0] = header;
		headers[1] = header01;
		headers[2] = header02;
		headers[3] = header03;
		headers[4] = header04;
		headers[5] = header05;
		headers[6] = header06;
		return headers;
	}

	public String sendReq(HttpRequestBase request) throws IOException {
		String result = executeHttp(request, null, null);
		return result;
	}

	public String sendContexReq(HttpRequestBase request, HttpContext context) throws IOException {
		String result = executeHttp(request, null, context);
		return result;

	}

	public JSONObject sendCookieReq(HttpRequestBase request, BasicCookieStore cookieStore) throws IOException {
		CloseableHttpClient httpClient = HttpClients.custom().setDefaultCookieStore(cookieStore).build();
		String result = executeHttp(request, httpClient, null);
		JSONObject jsonObj = JSON.parseObject(result);
		return jsonObj;

	}

	public String executeHttp(HttpRequestBase request, CloseableHttpClient httpClient, HttpContext context)
			throws ClientProtocolException, IOException {
		CloseableHttpResponse response = sendRequest(request, httpClient, context);
		HttpEntity enity = response.getEntity();
		InputStream instream = enity.getContent();
		String result = getStringByStream(instream);
		logger.info("backResult:{}",result);
		EntityUtils.consume(enity);
		request.releaseConnection();
		return result;
	}

	public InputStream executeStreamHttp(HttpRequestBase request) throws ClientProtocolException, IOException {
		CloseableHttpResponse response = sendRequest(request, null, null);
		HttpEntity enity = response.getEntity();
		InputStream instream = enity.getContent();
		
		return instream;
	}

	public CloseableHttpResponse sendRequest(HttpRequestBase request, CloseableHttpClient httpClient,
			HttpContext context) throws ClientProtocolException, IOException {
		CloseableHttpResponse response = null;
		try {
			if (httpClient == null) {
				httpClient = HttpClients.createDefault();
			}
			if (context != null) {
				response = httpClient.execute(request, context);
			} else {
				response = httpClient.execute(request);
			}
			if (response == null || response.getStatusLine().getStatusCode() != 200) {
				throw new RuntimeException("请求失败或异常!");
			}
		} catch (Exception e) {
			logger.error("请求:{},异常:{}",request.getURI().getPath(),e);
			request.releaseConnection();
			throw new RuntimeException("请求失败或异常!");
		}
		
		return response;
	}

	@Override
	public void sendErrorHandle(Map<String,Object> paramMap) {
		try {
			String content=new StringBuffer().append(paramMap.get("name")).append(" ").append(paramMap.get("phoneNum")).append(" 通话详单获取失败!").toString();
			ArrayList<NameValuePair> arrayList=new ArrayList<NameValuePair>();
			arrayList.add(new BasicNameValuePair("clientId",String.valueOf(paramMap.get("clientId"))));
			arrayList.add(new BasicNameValuePair("title", "通话详单获取通知"));
			arrayList.add(new BasicNameValuePair("content",content));
			arrayList.add(new BasicNameValuePair("field","1"));
			HttpPost httpPost=new HttpPost(callDetailAppProperties.getRecordsExceptionNotice());
			httpPost.setEntity(new UrlEncodedFormEntity(arrayList,Consts.UTF_8));
			sendReq(httpPost);
		} catch (Exception e) {
			logger.error("用户{},异常通知接口异常{}",paramMap.get("phoneNum"),e);
		}
	}

}
