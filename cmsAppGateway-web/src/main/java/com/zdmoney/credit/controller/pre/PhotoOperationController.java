package com.zdmoney.credit.controller.pre;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONObject;
import com.zdmoney.credit.api.framework.service.PreAPPService;
import com.zdmoney.credit.applyinfo.domain.TmApplyMainInfo;
import com.zdmoney.credit.applyinfo.service.pub.ITmApplyMainInfoService;
import com.zdmoney.credit.callinter.HttpUrlConnection;
import com.zdmoney.credit.config.PicAppProperties;
import com.zdmoney.credit.framework.controller.BaseController;

/**
 * 申请信息表
 * 
 * 
 */
@Controller
@RequestMapping(value = "/photo")
public class PhotoOperationController extends BaseController {

	protected static Log logger = LogFactory.getLog(PreAPPService.class);

	@Autowired
	HttpUrlConnection httpUrlConnection;
	
	
	@Autowired
	PicAppProperties picAppProperties;
	
	@Autowired
	private ITmApplyMainInfoService applyMainInfoService;

	/**
	 * 跳转页面
	 */
	@RequestMapping(value = "/query")
	public ModelAndView skipTest(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("photoTransfer");
		return modelAndView;
	}
	
	/**
	 * 查询
	 * @param req
	 * @param res
	 * @param page
	 * @param rows
	 * @return
	 */
	@RequestMapping("queryList")
	@ResponseBody
	public String queryList(HttpServletRequest req , HttpServletResponse res,Integer page,Integer rows) {
		
		String userId = req.getParameter("userId");
		String appNo = req.getParameter("appNo");
		String idNo = req.getParameter("idNo");
		String userName = req.getParameter("userName");
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("userId", userId);
		map.put("idNo", idNo);
		map.put("appNo", appNo);
		map.put("userName", userName);
		if(page < 1){
			page = 1;
		}
		map.put("start", (page-1) * rows );
		map.put("end", (page-1) * rows + rows);

		List<TmApplyMainInfo> mainInfos = applyMainInfoService.queryForPhotoMove(map);

		JSONObject retObj = new JSONObject(); // 返回数据封装
		List<Map<String, Object>> tempAppInputListData = new ArrayList<Map<String, Object>>();
		
		DateFormat df = new SimpleDateFormat("yyyy.MM.dd");
		if (null != mainInfos && !mainInfos.isEmpty()) {
			for (TmApplyMainInfo main : mainInfos) {
				Map<String, Object> mapInfo = new HashMap<String, Object>();
				mapInfo.put("userId", main.getUserId());
				mapInfo.put("oldAppNo", main.getAppNo());
				mapInfo.put("newAppNo", main.getNewAppNo());
				mapInfo.put("applicantName", main.getUserName());
				String currentTime = df.format(main.getApplyDate());
				mapInfo.put("applyTime", currentTime);
				mapInfo.put("applyAccount", main.getApplyAccount());
				mapInfo.put("productName", main.getProductName());
				mapInfo.put("idCardNoLastFourDigits", main.getIdNo());
				mapInfo.put("applyTerm", main.getApplyTerm());
				mapInfo.put("state", main.getState());
				
				tempAppInputListData.add(mapInfo);
			}
		}
		retObj.put("rows", tempAppInputListData);
		
		
		int totalNo = applyMainInfoService.countForPhotoMove(map);
		
		retObj.put("total", totalNo);
		
		return retObj.toString();
	}
	
	/**
	 * 图片迁移
	 * @param req
	 * @param res
	 * @return
	 */
	@RequestMapping("operation")
	@ResponseBody
	public JSONObject opration(HttpServletRequest req, HttpServletResponse res) {
		
		String oldAppNo = req.getParameter("oldAppNo");
		String newAppNo = req.getParameter("newAppNo");
		
		try {
			JSONObject paramPic = new JSONObject();
			String requestUrlPic = picAppProperties.getServiceUrl() + "/uploadFile/uploadFileAction_migrationFileList";
			
			paramPic.put("oldAppNo", oldAppNo);
			paramPic.put("newAppNo", newAppNo);
			paramPic.put("sysName", "aps");
			
			String resultPic = httpUrlConnection.postForEntity(requestUrlPic, paramPic, String.class);
		
			resultPic = URLDecoder.decode(resultPic, "UTF-8");
			JSONObject objPic = JSONObject.parseObject(resultPic);
			return objPic;
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			logger.error("图片迁移失败", e);
		}
		
		return null ;
	}
	
}
