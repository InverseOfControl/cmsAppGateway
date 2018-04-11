package com.zdmoney.credit.framework.controller;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.impl.client.BasicCookieStore;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zdmoney.credit.applyinfo.service.pub.CallRecordDetailService;

/**
 * 通话详单
 * 
 * @author YM10189
 *
 */
@Controller
@SuppressWarnings("unchecked")
@RequestMapping(value = "/mobile")
public class CallRecordDetailController extends BaseController {

	public static Logger logger = LoggerFactory.getLogger(CallRecordDetailController.class);
	
	@Autowired
	CallRecordDetailService cellRecordDetailService;

	// 页面跳转
	@RequestMapping(value = "/query")
	public String index(HttpServletRequest req, HttpServletResponse resp) {
		resp.setCharacterEncoding("UTF-8");
		String phone = req.getParameter("phone");
		String clientId = req.getParameter("clientId");
		String name = req.getParameter("name");
		String appNo = req.getParameter("appNo");
		if (phone != null) {
			req.setAttribute("phone", phone);
		}
		req.setAttribute("clientId", clientId);
		req.setAttribute("name", name);
		req.setAttribute("appNo", appNo);
		return "phoneDetail";
	}

	// 获取号码所属运营商
	@RequestMapping(value = "/check")
	@ResponseBody
	public String getPhone(HttpServletRequest req) {
		String phoneNum = req.getParameter("phone");
		if (phoneNum == null || "".equals(phoneNum)) {
			return null;
		} else {
			return cellRecordDetailService.queryPhoneOperator(phoneNum);
		}
	}

	@RequestMapping(value = "/getUniconDetail")
	@ResponseBody
	public Object loginUnico(HttpServletRequest req, HttpServletResponse resp)
			throws ClientProtocolException, IOException {
		resp.setContentType("text/html; charset=utf-8");
		String phoneNum = req.getParameter("userName");
		Map<String, Object> resultMap = new HashMap<String, Object>();
		Map<String, String> paramMap = new HashMap<String, String>();
		try {
			paramMap.put("userName", phoneNum);
			paramMap.put("password", req.getParameter("passWord"));
			Map<String, Object> data = cellRecordDetailService.loginUnicom(paramMap);
			if (data.get("cookie") != null && "0000".equals(data.get("code"))) {
				resultMap.put("respDesc", data.get("msg"));
				resultMap.put("respCode", data.get("code"));
				saveCookie(req, phoneNum, data.get("cookie"));
			} else {
				String msg=String.valueOf(data.get("msg"))==null?"请求繁忙,请稍后重试!":String.valueOf(data.get("msg"));
				String code=String.valueOf(data.get("code"))==null?"0005":String.valueOf(data.get("code"));
				resultMap.put("respDesc",msg);
				resultMap.put("respCode",code);
			}
		} catch (Exception e) {
			logger.error("联通用户{}登陆请求异常{}", phoneNum, e);
			resultMap.put("respCode", "0004");
			resultMap.put("respDesc", "请求异常,请稍后再试!");
		}
		return resultMap;
	}

	@RequestMapping(value = "/getUniconPhoneDetail")
	@ResponseBody
	public Object getUniconPhoneDetail(HttpServletRequest req) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		Map<String, Object> paramMap = new HashMap<String, Object>();
		String phoneNum = req.getParameter("phone");
		paramMap.put("phoneNum", phoneNum);
		paramMap.put("clientId", req.getParameter("cilentId"));
		paramMap.put("name", req.getParameter("names"));
		paramMap.put("appNo", req.getParameter("appNo"));
		BasicCookieStore cookieStore = null;
		try {
			cookieStore = (BasicCookieStore) getCookie(req, phoneNum);
			cellRecordDetailService.checkUnicom(cookieStore, phoneNum);
			List<Map<String, Object>> dataRecords = cellRecordDetailService.getUnicomCellRecords(paramMap, cookieStore);
			if (dataRecords == null || dataRecords.size() == 0) {
				throw new RuntimeException("联通用户通话详单数据获取失败");
			}
			byte[] bytearrays = cellRecordDetailService.buildExcelData(dataRecords);
			if (bytearrays == null) {
				throw new RuntimeException("联通用户获取详单excel文件构建异常");
			}
			paramMap.put("contentByte", bytearrays);
			boolean isUpload = cellRecordDetailService.uploadCellRecords(paramMap);
			if (!isUpload) {
				throw new RuntimeException("联通用户通话详单上传失败");
			} else {
				paramMap.put("respCode", "0000");
				paramMap.put("respDesc", "请求成功!");
			}
		} catch (Exception e) {
			logger.error("联通用户{}获取详单异常{}", phoneNum, e);
			paramMap.put("respCode", "0004");
			paramMap.put("respDesc", "请求异常,请稍后再试!");
			cellRecordDetailService.sendErrorHandle(paramMap);
		}
		return resultMap;
	}

	@RequestMapping(value = "/isVerCode")
	@ResponseBody
	public String isVerCode(HttpServletRequest req) {
		String result = null;
		String phoneNum = req.getParameter("phone");
		Map<String, String> paraMap = new HashMap<String, String>();
		try {
			paraMap.put("account", phoneNum);
			result = cellRecordDetailService.isRequireSmsCode(paraMap);
		} catch (Exception e) {
			logger.error("移动用户{}短信验证码发送异常!{}", phoneNum, e);
			result = "error";
		}
		return result;
	}

	@RequestMapping(value = "/sms")
	@ResponseBody
	public String sendSms(HttpServletRequest req) {
		String result = null;
		String phoneNum = req.getParameter("phone");
		Map<String, String> paraMap = new HashMap<String, String>();
		try {
			paraMap.put("userName", phoneNum);
			result = cellRecordDetailService.getMoileMsgCode(paraMap);
		} catch (Exception e) {
			logger.error("移动用户{}短信验证码发送异常!{}", phoneNum, e);
		}
		return result;
	}

	@RequestMapping(value = "/login")
	@ResponseBody
	public Object login(HttpServletRequest req) {
		Object result = null;
		Map<String, Object> cookieMap = null;
		String phoneNum = req.getParameter("userName");
		Map<String, String> paraMap = new HashMap<String, String>();
		try {
			paraMap.put("wtf", req.getParameter("wtf"));
			paraMap.put("account", phoneNum);
			paraMap.put("password", req.getParameter("passWord"));
			paraMap.put("smsPwd", req.getParameter("valCode").trim());
			Map<String, Object> resultMap = cellRecordDetailService.loginMobile(paraMap);
			result = resultMap.get("result");
			cookieMap = (Map<String, Object>) resultMap.get("cookie");
			if (cookieMap != null) {
				saveCookie(req, phoneNum, cookieMap);
			}
		} catch (Exception e) {
			logger.error("移动用户{}登陆异常{}", phoneNum, e);
			result = "error";
		}
		return result;
	}

	@RequestMapping(value = "/getImage")
	@ResponseBody
	public Object getImage(HttpServletRequest req) {
		Map<String, String> resultMap = new HashMap<String, String>();
		String phoneNum = req.getParameter("phone");
		Map<String, Object> paraMap = new HashMap<String, Object>();
		try {
			Object cookie = getCookie(req, phoneNum);
			String fileName = System.currentTimeMillis() + ".png";
			String fileFold = "/resources/static/upload/";
			String filePath = new StringBuffer(req.getSession().getServletContext().getRealPath(fileFold)).append("/")
					.append(fileName).toString();
			paraMap.put("cookie", cookie);
			InputStream instream = cellRecordDetailService.queryMobileImageCode(paraMap);
			if (instream != null) {
				generateImage(instream, filePath);
				resultMap.put("respCode", "0001");
				resultMap.put("respDesc", new StringBuffer("resources/static/upload/").append(fileName).toString());
			}
		} catch (Exception e) {
			logger.error("移动用户{}详单获取图片验证码获取异常{}", phoneNum, e);
			return "error";
		}
		return resultMap;
	}

	@RequestMapping(value = "/secRz")
	@ResponseBody
	public Object rz(HttpServletRequest req) {
		Object result = null;
		String phoneNum = req.getParameter("phone");
		Map<String, Object> paraMap = new HashMap<String, Object>();
		try {
			paraMap = (Map<String, Object>) getCookie(req, phoneNum);
			paraMap.put("password", req.getParameter("pwd"));
			paraMap.put("smscode", req.getParameter("rand"));
			paraMap.put("piccode", req.getParameter("val"));
			result = cellRecordDetailService.loginMobileAgain(paraMap, phoneNum);
		} catch (Exception e) {
			logger.error("移动用户{}详单获取登陆异常{}", phoneNum, e);
			result = "error";
		}
		return result;
	}

	@RequestMapping(value = "/smsSecond")
	@ResponseBody
	public Object smsSecond(HttpServletRequest req) {
		Object result = null;
		String phoneNum = req.getParameter("phone");
		Map<String, Object> cookieMap = new HashMap<String, Object>();
		try {
			cookieMap = (Map<String, Object>) getCookie(req, phoneNum);
			result = cellRecordDetailService.queryMobileMsgCodeAgain(cookieMap, phoneNum);
		} catch (Exception e) {
			logger.error("移动用户{}详单获取短信验证码发送异常{}", phoneNum, e);
			result = "error";
		}
		return result;
	}

	@RequestMapping(value = "/getDetail")
	@ResponseBody
	public Object getDetail(HttpServletRequest req) {
		Object result = null;
		String phoneNum = req.getParameter("phone");
		Map<String, Object> cookieMap = new HashMap<String, Object>();
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("phoneNum", phoneNum);
		paramMap.put("clientId", req.getParameter("cilentId"));
		paramMap.put("name", req.getParameter("names"));
		paramMap.put("appNo", req.getParameter("appNo"));
		try {
			cookieMap = (Map<String, Object>) getCookie(req, phoneNum);
			List<Map<String, Object>> dataRecords = cellRecordDetailService.getMobileCellRecords(cookieMap, phoneNum);
			result = dataRecords;
			if (dataRecords == null || dataRecords.size() == 0) {
				throw new RuntimeException("移动用户通话详单数据获取失败");
			}
			byte[] bytearrays = cellRecordDetailService.buildExcelData(dataRecords);
			if (bytearrays == null) {
				throw new RuntimeException("移动用户获取详单excel文件构建异常");
			}
			paramMap.put("contentByte", bytearrays);
			boolean isUpload = cellRecordDetailService.uploadCellRecords(paramMap);
			if (!isUpload) {
				throw new RuntimeException("移动用户通话详单上传失败");
			}
		} catch (Exception e) {
			logger.error("移动用户{}详单获取异常{}", phoneNum, e);
			result = "error";
			cellRecordDetailService.sendErrorHandle(paramMap);
		}
		return result;
	}

	public void saveCookie(HttpServletRequest request, String phoneNum, Object cookieObj) {
		String cookieKey = new StringBuffer("cookie-").append(phoneNum).toString();
		request.getSession().setAttribute(cookieKey, cookieObj);
	}

	public Object getCookie(HttpServletRequest request, String phoneNum) {
		String cookieKey = new StringBuffer("cookie-").append(phoneNum).toString();
		Object cookieStore = request.getSession().getAttribute(cookieKey);
		return cookieStore;
	}

	// 生成图片验证码
	public void generateImage(InputStream instream, String file) {
		FileOutputStream out = null;
		try {
			int i;
			out = new FileOutputStream(file);
			while ((i = instream.read()) != -1) {
				out.write(i);
			}
			out.flush();
			out.close();
			instream.close();
		} catch (Exception e) {
			logger.error("图片验证码生成异常:{}", e);
			if (out != null) {
				try {
					out.close();
				} catch (IOException e1) {
					logger.error("输出流关闭异常:{}", e);
				}
			}

			if (instream != null) {
				try {
					instream.close();
				} catch (IOException e1) {
					logger.error("输入流关闭异常:{}", e);
				}
			}
		}

	}
}
