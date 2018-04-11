package com.zdmoney.credit.api.framework.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.ss.usermodel.Picture;
import org.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.dubbo.common.utils.CollectionUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.gson.JsonObject;
import com.ymkj.cms.biz.api.enums.EnumConstants.sysType;
import com.zdmoney.credit.callinter.HttpUrlConnection;
import com.zdmoney.credit.common.annotate.FuncIdAnnotate;
import com.zdmoney.credit.common.exception.PlatformException;
import com.zdmoney.credit.common.exception.ResponseEnum;
import com.zdmoney.credit.common.util.AesUtil;
import com.zdmoney.credit.common.util.FileDownUtils;
import com.zdmoney.credit.common.util.HttpKit;
import com.zdmoney.credit.common.util.Strings;
import com.zdmoney.credit.common.util.base64Utils;
import com.zdmoney.credit.common.vo.FuncResult;
import com.zdmoney.credit.config.PicAppProperties;
import com.zdmoney.credit.config.SystemProperties;
import com.zdmoney.credit.framework.vo.Vo_800001;
import com.zdmoney.credit.framework.vo.Vo_800002;
import com.zdmoney.credit.framework.vo.Vo_800003;
import com.zdmoney.credit.framework.vo.Vo_800004;
import com.zdmoney.credit.framework.vo.Vo_800005;
import com.zdmoney.credit.framework.vo.Vo_800006;


@Service
public class PicAppService extends BusinessService{

	@Autowired
	PicAppProperties picAppProperties;

	@Autowired
	HttpUrlConnection httpUrlConnection;
	
	@Autowired
	private HttpKit httpKit;
	
	@Autowired
	private SystemProperties systemProperties;
	
	private static final String  HTTP_STR="http:";

	protected static Log logger = LogFactory.getLog(ApsAppService.class);
	
	



	@SuppressWarnings({ "unchecked", "rawtypes" })
	@FuncIdAnnotate(value = "800001", desc = "FTP上传接口", voCls = Vo_800001.class, isDependLogin = false)
	public FuncResult uploadFile(Vo_800001 vo_800001) throws Exception {
		String appNo = vo_800001.getAppNo();
		ArrayList<Map<String,Object>> uploadFilel=vo_800001.getUploadFileList();
		String httpResult=null;
		Object result= new Object();
		List fileList=new ArrayList();
		logger.info("上传图片开始");
		for(int i=0;i<uploadFilel.size();i++){
			Map param = new HashMap();
			String requestUrl = picAppProperties.getServiceUrl() + "/api/filedata/upload";
			String fileBytes=(String) uploadFilel.get(i).get("fileBytes");
			String fileName=(String) uploadFilel.get(i).get("fileName");
			String id=(String) uploadFilel.get(i).get("id");
			logger.info("图片："+fileName+"开始上传");
			String targetFileName=null;
			targetFileName = "./" + fileName;
			File file=null;
			boolean  generateFlag=false;
			try {
				//生成图片
				generateFlag=	base64Utils.GenerateImage(fileBytes,targetFileName);
				logger.info("******生成图片"+generateFlag);
				file = new File(targetFileName);	
				param.put("sysName", "app");
				param.put("nodeKey", "loanApplication"); //input-modify
				param.put("appNo", appNo);
				param.put("operator", vo_800001.getOperatorName());
				param.put("jobNumber", vo_800001.getOperatorCode());
				param.put("dataSources", "1");
				logger.info("*******调用PIC上传接口");
				if(!file.exists()){
					throw new PlatformException(ResponseEnum.FILE_NOT_GENERATE );
				}
				httpResult = httpKit.post(requestUrl, param, file);
				httpResult = URLDecoder.decode(httpResult, "UTF-8");
				logger.info("********上传图片返回："+httpResult);
				logger.info("********图片："+fileName+"开始上传成功");
			} catch (Exception e) {
				logger.info("*********上传图片失败：数据："+httpResult+":异常"+e);
				throw new PlatformException(ResponseEnum.PIC_FAIL,"上传图片失败" );
			}finally {
				//删除生成的图片
				if(file != null && file.exists()){
					FileDownUtils.deleteDir(file);
				}
			}
			JSONObject obj = JSONObject.parseObject(httpResult);
			logger.info("上传图片返回："+httpResult);
			if(obj == null || !"000000".equals(obj.get("errorcode"))) {
				throw new PlatformException(ResponseEnum.FULL_MSG,"PIC图片系统上传调用失败");
			}
			//传Id覆盖原图片
			if(Strings.isNotEmpty(id)){
				JSONObject delObject =new JSONObject();
				delObject.put("ids", id);
				delObject.put("operator", vo_800001.getOperatorName());
				delObject.put("jobNumber", vo_800001.getOperatorCode());
				String delRequestUrl = picAppProperties.getServiceUrl() + "/api/picture/delete";
				String delResult = httpUrlConnection.postForEntity(delRequestUrl, delObject, String.class);
				delResult = URLDecoder.decode(delResult, "UTF-8");
				JSONObject delObj = JSONObject.parseObject(delResult);
				System.out.println(obj.toString());
				if(delObj == null ||!"000000".equals(delObj.get("errorcode"))) {
					throw new PlatformException(ResponseEnum.FULL_MSG,"PIC图片系统覆盖接口调用失败");
				}
				logger.info("********覆盖图片返回："+httpResult);
				logger.info("********覆盖图片返回成功");
			}
			result=obj.getObject("result",Object.class);
			fileList.add(result);
		}
		
		JSONObject jay= new JSONObject();
		jay.put("appNo", appNo);
		jay.put("fileType", vo_800001.getFileType());
		jay.put("fileList",fileList);
		return FuncResult.success("上传成功",jay);
	}


	@FuncIdAnnotate(value = "800002", desc = "获取申请单下的所有类型的文件个数", voCls = Vo_800002.class,isDependLogin = false)
	public FuncResult getFileTypeList(Vo_800002 vo_800002) throws Exception {

		String appNo = vo_800002.getAppNo();
		JSONObject param = new JSONObject();
		String requestUrl = picAppProperties.getServiceUrl() + "/api/paperstype/list";

		param.put("appNo", appNo);
		param.put("operator", vo_800002.getOperatorName());
		param.put("jobNumber", vo_800002.getOperatorCode());
		param.put("nodeKey", "loanApplication");

		String result = httpUrlConnection.postForEntity(requestUrl, param, String.class);

		result = URLDecoder.decode(result, "UTF-8");
		JSONObject resultObj = JSONObject.parseObject(result);
		if(resultObj == null ||!"000000".equals(resultObj.get("errorcode"))) {
			throw new PlatformException(ResponseEnum.FULL_MSG,"PIC图片系统调用失败");
		}
		JSONObject jay= new JSONObject();
		jay.put("fileList", resultObj.get("result"));
		jay.put("appNo", appNo);
		logger.info("********PIC系统返回："+result);
		return FuncResult.success("查询成功", jay);
	}

	@FuncIdAnnotate(value = "800003", desc = "获取申请单下某个类型文件的列表", voCls = Vo_800003.class,isDependLogin = false)
	public FuncResult getFileList(Vo_800003 vo_800003) throws Exception {
		String appNo = vo_800003.getAppNo();
		String fileType = vo_800003.getFileType();

		JSONObject param = new JSONObject();
		String requestUrl = picAppProperties.getServiceUrl() + "/api/picture/list";

		param.put("appNo", appNo);
		param.put("subclass_sort", fileType);
		param.put("operator", vo_800003.getOperatorName());
		param.put("jobNumber", vo_800003.getOperatorCode());

		String result = httpUrlConnection.postForEntity(requestUrl, param, String.class);

		JSONObject resultObj = JSONObject.parseObject(result);
		
		if(resultObj == null ||!"000000".equals(resultObj.get("errorcode"))) {
			throw new PlatformException(ResponseEnum.FULL_MSG,"PIC图片系统调用失败");
		}
		//拼接url
		List<Map> list = (List<Map>)resultObj.get("result");
		if(list != null&&list.size()>0){
			for (Map map : list) {
				 for (Object urlKey : map.keySet()) {
					 //url 拼接
				     if("url".equals(urlKey)){
				     String	urlParam = AesUtil.encryptAES(HTTP_STR+String.valueOf(map.get(String.valueOf(urlKey))),systemProperties.getAppKey());
				    	// String	urlStr =urlParam.replace("+","2B%");
				    	 String picUrlStr=systemProperties.getAppPicGateWayUrl()+urlParam;	
							if("url".equals(urlKey)){
								map.put("url",picUrlStr);
							}
						}
						if("uptime".equals(urlKey)){
							String uptime=String.valueOf(map.get(String.valueOf(urlKey)));
							map.put("uptime", uptime.substring(0,10));
						}
				}
				}
		}
		
		JSONObject jay= new JSONObject();
		jay.put("fileList", list);
		jay.put("appNo", appNo);
		jay.put("fileType", fileType);
		jay.put("maxSortsId",list == null?0:list.size()); 
		jay.put("flag","success"); 
		return FuncResult.success("查询成功", jay);
	}


	@FuncIdAnnotate(value = "800004", desc = "删除申请单下的某个文件", voCls = Vo_800004.class,isDependLogin = false)
	public FuncResult deteleFile(Vo_800004 vo_800004) throws Exception {

		String fileId = vo_800004.getFileId();

		JSONObject param = new JSONObject();
		String requestUrl = picAppProperties.getServiceUrl() + "/api/picture/delete";
		
		param.put("ids", fileId);
		param.put("operator", vo_800004.getOperatorName());
		param.put("jobNumber", vo_800004.getOperatorCode());

		String result = httpUrlConnection.postForEntity(requestUrl, param, String.class);

		result = URLDecoder.decode(result, "UTF-8");
		JSONObject obj = JSONObject.parseObject(result);
		System.out.println(obj.toString());

		if(obj == null ||!"000000".equals(obj.get("errorcode"))) {
			throw new PlatformException(ResponseEnum.FULL_MSG,"PIC图片系统调用失败");
		}
		logger.info("********PIC系统返回："+result);
		return FuncResult.success("删除成功", new Object());
	}


	@FuncIdAnnotate(value = "800005", desc = "申请单下某类型文件重新排序", voCls = Vo_800005.class,isDependLogin = false)
	public FuncResult reOrderFileList(Vo_800005 vo_800005) throws Exception {
		List<String> ids =new ArrayList<>();
		List<Map<String, Object>> reOrderFileList=vo_800005.getReOrderFileList();
		if(reOrderFileList !=null &&reOrderFileList.size()>0){
			for (Map<String, Object> map : reOrderFileList) {
				ids.add(String.valueOf(map.get("fileId")));
			}
		}
		JSONObject param = new JSONObject();
		String requestUrl = picAppProperties.getServiceUrl() + "/api/picture/updateSortsid";
		param.put("ids", Strings.listToString(ids));
		param.put("subclassSort", vo_800005.getFileType());
		param.put("appNo", vo_800005.getAppNo());
		param.put("operator", vo_800005.getOperatorName());
		param.put("jobNumber", vo_800005.getOperatorCode());
		String result = httpUrlConnection.postForEntity(requestUrl, param, String.class);

		result = URLDecoder.decode(result, "UTF-8");
		JSONObject obj = JSONObject.parseObject(result);
		

		if(obj == null ||!"000000".equals(obj.get("errorcode"))) {
			throw new PlatformException(ResponseEnum.FULL_MSG,"PIC图片系统调用失败");
		}
		logger.info("********PIC系统返回："+result);
		return FuncResult.success("排序成功", new Object());
	}




	@FuncIdAnnotate(value = "800006", desc = "申请单文件迁移", voCls = Vo_800006.class,isDependLogin = false)
	public FuncResult migrationFileList(Vo_800006 vo_800006) throws Exception {

		String oldAppNo = vo_800006.getOldAppNo();
		String newAppNo = vo_800006.getNewAppNo();

		JSONObject param = new JSONObject();
		String requestUrl = picAppProperties.getServiceUrl() + "/api/picture/updateAppno";

		param.put("appNo", oldAppNo);
		param.put("newAppNo", newAppNo);
		param.put("subclassSort",vo_800006.getFileType());
		param.put("operator",vo_800006.getOperatorName());
		param.put("jobNumber",vo_800006.getOperatorCode());

		String result = httpUrlConnection.postForEntity(requestUrl, param, String.class);

		result = URLDecoder.decode(result, "UTF-8");
		JSONObject obj = JSONObject.parseObject(result);
		System.out.println(obj.toString());

		if(obj == null ||!"000000".equals(obj.get("errorcode"))) {
			throw new PlatformException(ResponseEnum.FULL_MSG,"PIC图片系统调用失败");
		}
		logger.info("********PIC系统返回："+result);
		return FuncResult.success("迁移成功", new Object());
	}


}
