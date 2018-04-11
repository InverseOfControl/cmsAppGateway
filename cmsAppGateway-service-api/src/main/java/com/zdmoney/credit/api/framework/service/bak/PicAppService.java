//package com.zdmoney.credit.api.framework.service.bak;
//
//import java.io.UnsupportedEncodingException;
//import java.net.URLDecoder;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Map;
//
//import org.apache.commons.logging.Log;
//import org.apache.commons.logging.LogFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import com.alibaba.fastjson.JSONObject;
//import com.zdmoney.credit.api.framework.service.ApsAppService;
//import com.zdmoney.credit.callinter.HttpUrlConnection;
//import com.zdmoney.credit.common.annotate.FuncIdAnnotate;
//import com.zdmoney.credit.common.exception.PlatformException;
//import com.zdmoney.credit.common.exception.ResponseEnum;
//import com.zdmoney.credit.common.vo.FuncResult;
//import com.zdmoney.credit.config.PicAppProperties;
//import com.zdmoney.credit.framework.vo.Vo_800001;
//import com.zdmoney.credit.framework.vo.Vo_800002;
//import com.zdmoney.credit.framework.vo.Vo_800003;
//import com.zdmoney.credit.framework.vo.Vo_800004;
//import com.zdmoney.credit.framework.vo.Vo_800005;
//import com.zdmoney.credit.framework.vo.Vo_800006;
//
//
//@Service
//public class PicAppService extends BusinessService{
//	
//	@Autowired
//	PicAppProperties picAppProperties;
//	
//	@Autowired
//	HttpUrlConnection httpUrlConnection;
//
//	protected static Log logger = LogFactory.getLog(ApsAppService.class);
//
//
//	
/*public FuncResult uploadFile(Vo_800001 vo_800001) throws Exception {

		String appNo = vo_800001.getAppNo();
		ArrayList<Map<String,Object>> uploadFilel=vo_800001.getUploadFileList();
		String result = null;
		
		List lists=new ArrayList();
		String maxSortId = null;
		for(int i=0;i<uploadFilel.size();i++){
			
			JSONObject json0 = new JSONObject();
			JSONObject json1 = new JSONObject();
			
			JSONObject param = new JSONObject();
			String requestUrl = picAppProperties.getServiceUrl() + "/uploadFile/uploadFileAction_uploadPicture";
			
			param.put("appNo", appNo);
			param.put("iremark", "app");
			param.put("sysName", "aps");
			param.put("nodeKey", "input-modify");
			param.put("ifPatchBolt", "N");
			param.put("uploadSource", "APP");
			
			String fileName=(String) uploadFilel.get(i).get("fileName");
			String fileBytes=(String) uploadFilel.get(i).get("fileBytes");
			
			String fn[] = fileName.split("\\.");
		    String str[]=fn[0].split("_");
		    param.put("isortsId", str[1]);
		    
			param.put("Filename",fileName);
			param.put("fileBytes", fileBytes);
			
			result = httpUrlConnection.postForEntity(requestUrl, param, String.class);
			
			result = URLDecoder.decode(result, "UTF-8");
			JSONObject obj = JSONObject.parseObject(result);
			
			List l=(List) obj.get("obj");
			json0=(JSONObject) l.get(0);
			
			if(!json0.containsKey("flag")) {
				throw new PlatformException(ResponseEnum.FULL_MSG,"图片管理系统返回缺少【flag】Key");
			}
			
			String flag = json0.getString("flag");
			
			if(flag==null || "failure".equals(flag)){
				String message = json0.getString("message");
				return FuncResult.fail(message);
			}
			
			if("success".equals(json0.get("flag").toString())){
				json1=(JSONObject) l.get(1);
				maxSortId=json1.getString("maxSortsId");
				lists.add(json1);
			}
			
		}
		
		JSONObject jay= new JSONObject();
		jay.put("appNo", appNo);
		jay.put("fileType", vo_800001.getFileType());
		jay.put("maxSortId", maxSortId);
		jay.put("fileList", lists);
		
		return FuncResult.success("上传成功",jay);
	}*/
//	
//	
//	@FuncIdAnnotate(value = "800002", desc = "获取申请单下的所有类型的文件个数", voCls = Vo_800002.class)
//	public FuncResult getFileTypeList(Vo_800002 vo_800002) throws Exception {
//
//			String appNo = vo_800002.getAppNo();
//		
//			
//			JSONObject param = new JSONObject();
//			String requestUrl = picAppProperties.getServiceUrl() + "/uploadFile/uploadFileAction_getFileTypeList";
//			
//			param.put("appNo", appNo);
//			
//			String result = httpUrlConnection.postForEntity(requestUrl, param, String.class);
//			
//			result = URLDecoder.decode(result, "UTF-8");
//			JSONObject obj = JSONObject.parseObject(result);
//			
//			if(!obj.containsKey("flag")) {
//				throw new PlatformException(ResponseEnum.FULL_MSG,"图片管理系统返回缺少【flag】Key");
//			}
//			
//			String flag = obj.getString("flag");
//			
//			if(flag==null || "failure".equals(flag)){
//				String message = obj.getString("message");
//				return FuncResult.fail(message);
//			}
//
//		return FuncResult.success("查询成功", obj);
//	}
//	
//	@FuncIdAnnotate(value = "800003", desc = "获取申请单下某个类型文件的列表", voCls = Vo_800003.class)
//	public FuncResult getFileList(Vo_800003 vo_800003) throws Exception {
//
//		String appNo = vo_800003.getAppNo();
//		String fileType = vo_800003.getFileType();
//		
//		JSONObject param = new JSONObject();
//		String requestUrl = picAppProperties.getServiceUrl() + "/uploadFile/uploadFileAction_getFileList";
//		
//		param.put("appNo", appNo);
//		param.put("subclass_sort", fileType);
//		
//		String result = httpUrlConnection.postForEntity(requestUrl, param, String.class);
//		
////		result = URLDecoder.decode(result, "UTF-8");
//		
//		JSONObject obj = JSONObject.parseObject(result);
//		
//		if(!obj.containsKey("flag")) {
//			throw new PlatformException(ResponseEnum.FULL_MSG,"图片管理系统返回缺少【flag】Key");
//		}
//		
//		String flag = obj.getString("flag");
//		
//		if(flag==null || "failure".equals(flag)){
//			String message = obj.getString("message");
//			return FuncResult.fail(message);
//		}
//
//	return FuncResult.success("查询成功", obj);
//	}
//	
//	
//	@FuncIdAnnotate(value = "800004", desc = "删除申请单下的某个文件", voCls = Vo_800004.class)
//	public FuncResult deteleFile(Vo_800004 vo_800004) throws Exception {
//
//		String fileId = vo_800004.getFileId();
//		
//		JSONObject param = new JSONObject();
//		String requestUrl = picAppProperties.getServiceUrl() + "/uploadFile/uploadFileAction_deteleFile";
//		
//		param.put("id", fileId);
//		
//		String result = httpUrlConnection.postForEntity(requestUrl, param, String.class);
//		
//		result = URLDecoder.decode(result, "UTF-8");
//		JSONObject obj = JSONObject.parseObject(result);
//		System.out.println(obj.toString());
//		
//		if(!obj.containsKey("flag")) {
//			throw new PlatformException(ResponseEnum.FULL_MSG,"图片管理系统返回缺少【flag】Key");
//		}
//		
//		String flag = obj.getString("flag");
//		
//		if(flag==null || "failure".equals(flag)){
//			String message = obj.getString("message");
//			return FuncResult.fail(message);
//		}
//
//		return FuncResult.success("删除成功", new Object());
//	}
//	
//	
//	@FuncIdAnnotate(value = "800005", desc = "申请单下某类型文件重新排序", voCls = Vo_800005.class)
//	public FuncResult reOrderFileList(Vo_800005 vo_800005) throws Exception {
//
//		ArrayList fileIdList=vo_800005.getReOrderFileList();
//		JSONObject json = new JSONObject();
//		json.put("idList", fileIdList);
//		
//		JSONObject param = new JSONObject();
//		String requestUrl = picAppProperties.getServiceUrl() + "/uploadFile/uploadFileAction_reOrderFileList";
//		param.put("reOrderFileList", json.toString());
//		
//		String result = httpUrlConnection.postForEntity(requestUrl, param, String.class);
//		
//		result = URLDecoder.decode(result, "UTF-8");
//		JSONObject obj = JSONObject.parseObject(result);
//		
//		if(!obj.containsKey("flag")) {
//			throw new PlatformException(ResponseEnum.FULL_MSG,"图片管理系统返回缺少【flag】Key");
//		}
//		
//		String flag = obj.getString("flag");
//		
//		if(flag==null || "failure".equals(flag)){
//			String message = obj.getString("message");
//			return FuncResult.fail(message);
//		}
//
//		return FuncResult.success("排序成功", new Object());
//	}
//	
//	
//	
//	
//	@FuncIdAnnotate(value = "800006", desc = "申请单文件迁移", voCls = Vo_800006.class)
//	public FuncResult migrationFileList(Vo_800006 vo_800006) throws Exception {
//
//		String oldAppNo = vo_800006.getOldAppNo();
//		String newAppNo = vo_800006.getNewAppNo();
//		String sysName = vo_800006.getOldSystemCode();
//		
//		JSONObject param = new JSONObject();
//		String requestUrl = picAppProperties.getServiceUrl() + "/uploadFile/uploadFileAction_migrationFileList";
//		
//		param.put("oldAppNo", oldAppNo);
//		param.put("newAppNo", newAppNo);
//		param.put("sysName", sysName);
//		
//		String result = httpUrlConnection.postForEntity(requestUrl, param, String.class);
//		
//		result = URLDecoder.decode(result, "UTF-8");
//		JSONObject obj = JSONObject.parseObject(result);
//		System.out.println(obj.toString());
//		
//		if(!obj.containsKey("flag")) {
//			throw new PlatformException(ResponseEnum.FULL_MSG,"图片管理系统返回缺少【flag】Key");
//		}
//		
//		String flag = obj.getString("flag");
//		
//		if(flag==null || "failure".equals(flag)){
//			String message = obj.getString("message");
//			return FuncResult.fail(message);
//		}
//
//		return FuncResult.success("迁移成功", new Object());
//	}
//	
//	
//	public String s() throws UnsupportedEncodingException {
//		
//		JSONObject param = new JSONObject();
//		String requestUrl =  "http://localhost:8080/pic-app/uploadFile/uploadFileAction_aiteUploadFile";
//		param.put("appNo", "20160021001500120001");
//		param.put("files", "[{'fileName': '测试001.jpg','url': 'http://0000d/ddddd/dddd.jpg'},{'fileName': '测试002.jpg','url': 'http://0000dddd/ddeeeeee/ddd.jpg'}]");
//			
//		String result = httpUrlConnection.postForEntity(requestUrl, param, String.class);
//		result = URLDecoder.decode(result, "UTF-8");
//		JSONObject obj = JSONObject.parseObject(result);
//		return obj.toJSONString();
//		
//	}
//	public static void main (String args[]) {
//		
//		PicAppService pas=new PicAppService();
//		try {
//			String str=pas.s();
//			System.out.println(str);
//		} catch (UnsupportedEncodingException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		
//		
//	}
//}
