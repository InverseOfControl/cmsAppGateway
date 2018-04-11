package com.zdmoney.credit.applyinfo.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import org.apache.commons.lang.ObjectUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.zdmoney.credit.applyinfo.dao.pub.ITmApplyFieldInfoDao;
import com.zdmoney.credit.applyinfo.domain.TmApplyFieldInfo;
import com.zdmoney.credit.applyinfo.service.pub.ITmApplyFieldInfoService;
import com.zdmoney.credit.common.constant.system.Constant;
import com.zdmoney.credit.framework.vo.Vo_600005;
import com.zdmoney.credit.system.dao.pub.ISysParamDefineDao;
import com.zdmoney.credit.system.domain.SysParamDefine;

import oracle.sql.CLOB;

@Service
public class TmApplyFieldInfoServiceImpl implements ITmApplyFieldInfoService {

    @Autowired
    @Qualifier("tmApplyFieldInfoDaoImpl")
    ITmApplyFieldInfoDao tmApplyFieldInfoDaoImpl;
    
    @Autowired
	private ISysParamDefineDao paramDefineDao;
    
	public void insert(TmApplyFieldInfo tmApplyFieldInfo) {
		tmApplyFieldInfoDaoImpl.insert(tmApplyFieldInfo);
		
	}

	@Override
	public void del(long id) {
		tmApplyFieldInfoDaoImpl.deleteById((long) id);
	}

	@Override
	public void update(TmApplyFieldInfo tmApplyFieldInfo) {
		tmApplyFieldInfoDaoImpl.updateByPrimaryKeySelective(tmApplyFieldInfo);
	}
	
	@Override
	public List<TmApplyFieldInfo> queryByMap(Map<String, Object> map) {
		
		List<TmApplyFieldInfo> findListByMap = tmApplyFieldInfoDaoImpl.findListByMap(map);
		return findListByMap;
	}

	@Override
	public void saveFieldInfo(Vo_600005 vo_600005, String appNo, String applyState) {
		
		Boolean flag=true;
		int i = 1;
		while(flag){
			
			TmApplyFieldInfo tmApplyFieldInfo = new TmApplyFieldInfo();
			tmApplyFieldInfo.setAppNo(appNo);
			tmApplyFieldInfo.setState("0");
			if(i==1){
				String fieldKey = "empItemInfo";
				tmApplyFieldInfo.setFieldKey(fieldKey);
				tmApplyFieldInfo.setFieldType(Constant.ArrType);
			}
			if(i==2){
				String fieldKey = "applyInfo";
				tmApplyFieldInfo.setFieldKey(fieldKey);
				tmApplyFieldInfo.setFieldObjValue(vo_600005.getApplyInfoField());
				tmApplyFieldInfo.setFieldType(Constant.ObjType);
				tmApplyFieldInfo.setState(applyState);
			}
			if(i==3){
				String fieldKey = "contactPersonInfo";
				tmApplyFieldInfo.setFieldKey(fieldKey);
				tmApplyFieldInfo.setFieldType(Constant.ArrType);
			}
			if(i==4){
				tmApplyFieldInfo.setFieldKey("assetsInfo");
				tmApplyFieldInfo.setFieldType(Constant.ArrType);
			}
			if(i==5){
				tmApplyFieldInfo.setFieldKey("uploadInfo");
				tmApplyFieldInfo.setFieldType(Constant.ArrType);
				tmApplyFieldInfo.setState("1");
			}
			if(i==6){
				tmApplyFieldInfo.setFieldKey("persionInfo");
				tmApplyFieldInfo.setFieldType(Constant.ObjType);
			}
			
			if(i==7){
				tmApplyFieldInfo.setFieldKey("mateInfo");
				tmApplyFieldInfo.setFieldType(Constant.ObjType);
			}
			
			tmApplyFieldInfoDaoImpl.insert(tmApplyFieldInfo);
			i++;
			if(i>7){
				flag=false;
			}
		}
		
	}
	
	@Override
	public void saveFieldInfo2(Vo_600005 vo_600005, String appNo, String applyState) {

			TmApplyFieldInfo tmApplyFieldInfo = new TmApplyFieldInfo();
			tmApplyFieldInfo.setAppNo(appNo);
			tmApplyFieldInfo.setState("0");
			String fieldKey = "applyInfo";
			tmApplyFieldInfo.setFieldKey(fieldKey);
			tmApplyFieldInfo.setFieldObjValue(vo_600005.getApplyInfoField());
			tmApplyFieldInfo.setFieldType(Constant.ObjType);
			tmApplyFieldInfo.setState(applyState);
			tmApplyFieldInfoDaoImpl.insert(tmApplyFieldInfo);
			
			Map<String, Object> map = new HashMap<String,Object>();
			map.put("oldAppNo", vo_600005.getAppNo());
			map.put("appNo", appNo);
			tmApplyFieldInfoDaoImpl.updateAppNo(map);
		
	}

	@Override
	public void deleteByAppNo(Map<String, Object> map) {
		
		tmApplyFieldInfoDaoImpl.deleteByAppNo(map);
	}


	@Override
	public void deleteByCon(Map<String, Object> map) {
		
		tmApplyFieldInfoDaoImpl.deleteByCon(map);
	}
	
	@Override
	public int upObjInfoById(Map<String, Object> map) {
		return tmApplyFieldInfoDaoImpl.upObjInfoById(map);
	}

	@Override
	public Map<String, Object> queryCustomers(Map<String, String> map) throws Exception {
		String cellPhone=map.get("phoneNum");
		Map<String,Object> result=new HashMap<String,Object>();
		List<Map<String,Object>> datas=new ArrayList<Map<String,Object>>();
		List<Map<String,Object>> dataMap=tmApplyFieldInfoDaoImpl.queryCustomers(map);
		int k=0;
		if(dataMap!=null){
			for (int i = 0; i < dataMap.size(); i++) {
				Map<String,Object> data=dataMap.get(i);
				CLOB clob=(CLOB) data.get("PERSONINFO");;
				String personInfo = clob.getSubString((long)1,(int)clob.length());
				JSONObject jsonObj=JSON.parseObject(personInfo);
				String phoneNum=jsonObj.getString("cellphone");
				if(StringUtils.isEmpty(phoneNum)){
					continue;
				}
				
				if(StringUtils.isNotEmpty(cellPhone)&&!cellPhone.equals(phoneNum)){
					continue;
				}
				Map<String,Object> userInfo=new HashMap<String,Object>();
				String status=queryPhoneOperator(phoneNum);
				userInfo.put("cellphone_status",jsonObj.getString("cellphone_status")==null?status:jsonObj.getString("cellphone_status"));
				userInfo.put("cellphone_name",data.get("USERNAME")==null?"":data.get("USERNAME"));
				userInfo.put("cellphone", phoneNum);
				if("1".equals(jsonObj.getString("cellphone_status"))){
					userInfo.put("cellphone_time",jsonObj.get("cellphone_time")==null?"":jsonObj.get("cellphone_time"));
				}
				
				userInfo.put("appNo",data.get("APPNO")==null?"":data.get("APPNO"));
				datas.add(userInfo);
				k++;
				if(StringUtils.isEmpty(cellPhone)&&StringUtils.isNotEmpty(jsonObj.getString("cellphoneSec"))){
					String statusSec=queryPhoneOperator(jsonObj.getString("cellphoneSec"));
					Map<String,Object> userInfoSec=new HashMap<String,Object>();
					userInfoSec.putAll(userInfo);
					userInfoSec.put("cellphone", jsonObj.getString("cellphoneSec"));
					userInfoSec.put("cellphone_status", jsonObj.getString("cellphoneSec_status")==null?statusSec:jsonObj.getString("cellphoneSec_status"));
					if("1".equals(jsonObj.getString("cellphoneSec_status"))){
						userInfoSec.put("cellphone_time",jsonObj.get("cellphoneSec_time")==null?"":jsonObj.get("cellphoneSec_time"));
					}
					datas.add(userInfoSec);
					k++;
				}
			}
		}
		result.put("total", k);
		result.put("list",datas);
		return result;
	}

	@Override
	public Map<String, String> queryCustInfo(Map<String, String> map) {
		List<Map<String,String>> dataMap=tmApplyFieldInfoDaoImpl.queryCustInfo(map);
		if(dataMap!=null&&dataMap.size()>0){
			return dataMap.get(0);
		}
		return null;
	}
	
	@Override
	public String queryPhoneOperator(String phoneNum) {
		List<SysParamDefine> list = paramDefineDao.selectRule();
		String keyCode = null;
		String keyValue=null;
		if (list != null && list.size() > 0) {
			for (SysParamDefine param : list) {
				if (Pattern.matches(param.getParamValue(), phoneNum) == true) {
					keyCode = param.getParamKey();
				}
			}
		}
		
		if(keyCode!=null){
			if("chinaUnicom".equals(keyCode)||"chinaMobile".equals(keyCode)){
				keyValue="2";
			}else{
				keyValue="3";
			}
		}else{
			keyValue="3";
		}
		return keyValue;

	}

}
