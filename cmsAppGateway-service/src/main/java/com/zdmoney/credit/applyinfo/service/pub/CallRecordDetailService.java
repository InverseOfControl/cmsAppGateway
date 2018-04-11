package com.zdmoney.credit.applyinfo.service.pub;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Map;

import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.impl.client.BasicCookieStore;

import com.alibaba.fastjson.JSONObject;

/**
 * @author YM10189
 * 通话详单接口
 */
public interface CallRecordDetailService{
	/**
	 * 查询运营商
	 * @param phoneNum
	 * @return
	 */
	public String queryPhoneOperator(String phoneNum);
	/**
	 * 联通用户登陆
	 * @param params
	 * @return
	 */
	public Map<String,Object> loginUnicom(Map<String,String> params)throws Exception;
	
	/**
	 * 联通用户登陆检查
	 * @param params
	 * @return
	 * @throws IOException 
	 */
	public JSONObject checkUnicom(BasicCookieStore cookieStore,String phoneNum) throws IOException;
	
	/**
	 * 联通用户通话详单获取
	 * @param params
	 * @return
	 */
	public List<Map<String, Object>> getUnicomCellRecords(Map<String, Object> params,BasicCookieStore cookieStore)throws Exception;
	
	/**
	 * 验证移动用户是否需要验证码
	 * @param params
	 * @return
	 */
	public String isRequireSmsCode(Map<String,String> params)throws Exception;
	
	/**
	 * 移动用户登陆
	 * @param params
	 * @return
	 */
	public Map<String, Object> loginMobile(Map<String,String> params);
	
	/**
	 * 获取移动用户登陆cookie信息
	 * @param params
	 * @return
	 */
	Map<String, String> queryMoibleCookie(String phoneNum, String artifact,HttpClientContext httpContext);
	
	/**
	 * 移动用户登陆短信验证码获取
	 * @param params
	 * @return
	 */
	public String getMoileMsgCode(Map<String,String> params) throws Exception;
	
	/**
	 * 移动用户通话详单获取
	 * @param params
	 * @return
	 */
	public List<Map<String, Object>> getMobileCellRecords(Map<String,Object> params,String phoneNum) throws Exception;
	
	/**
	 * 通话详单上传
	 * @param params
	 * @return
	 * @throws UnsupportedEncodingException 
	 */
	public boolean uploadCellRecords(Map<String,Object> params) throws UnsupportedEncodingException;
	
	/**
	 * 获取移动图形验证码
	 * @param params
	 */
	public InputStream queryMobileImageCode(Map<String,Object> params) throws Exception;
	
	/**
	 * 获取移动详单验证码
	 * @param params
	 * @return
	 */
	public JSONObject queryMobileMsgCodeAgain(Map<String,Object> params,String phoneNum) throws Exception;
	
	/**
	 * 移动用户获取详单再次认证
	 * @param params
	 */
	public JSONObject loginMobileAgain(Map<String,Object> params,String phoneNum) throws Exception;
	
	/**
	 * excel生成
	 * @param dataRecords
	 * @return
	 * @throws IOException
	 */
	public  byte[] buildExcelData(List<Map<String, Object>> dataRecords) throws IOException;
	
	/**
	 * 通话详单获取异常通知
	 */
	public void sendErrorHandle(Map<String,Object> paramMap);
	
}