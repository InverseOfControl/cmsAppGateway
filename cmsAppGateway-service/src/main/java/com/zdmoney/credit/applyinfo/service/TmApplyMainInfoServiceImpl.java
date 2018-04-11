package com.zdmoney.credit.applyinfo.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.zdmoney.credit.applyinfo.dao.pub.ITmApplyMainInfoDao;
import com.zdmoney.credit.applyinfo.domain.TmApplyMainInfo;
import com.zdmoney.credit.applyinfo.service.pub.ITmApplyMainInfoService;
import com.zdmoney.credit.framework.vo.Vo_600005;

@Service
public class TmApplyMainInfoServiceImpl implements ITmApplyMainInfoService {

    @Autowired
    @Qualifier("tmApplyMainInfoDaoImpl")
	ITmApplyMainInfoDao tmApplyMainInfoDaoImpl;

	public void insert(TmApplyMainInfo tmApplyMainInfo) {
		tmApplyMainInfoDaoImpl.insert(tmApplyMainInfo);
		
	}

	@Override
	public void del(long id) {
		tmApplyMainInfoDaoImpl.deleteById((long) id);
	}

	@Override
	public void update(TmApplyMainInfo tmApplyMainInfo) {
		tmApplyMainInfoDaoImpl.updateByPrimaryKeySelective(tmApplyMainInfo);
	}
	
	@Override
	public void updateMainTime(TmApplyMainInfo tami) {
		tmApplyMainInfoDaoImpl.updateMainTime(tami);
	}
	
	
	@Override
	public List<TmApplyMainInfo> queryByMap(Map<String, Object> map) {
		
		List<TmApplyMainInfo> findListByMap = tmApplyMainInfoDaoImpl.findListByMap(map);
		return findListByMap;
	}
	
	@Override
	public List<TmApplyMainInfo> queryByMap2(Map<String, Object> map) {
		
		List<TmApplyMainInfo> findListByMap = tmApplyMainInfoDaoImpl.findWithByMap2(map);
		return findListByMap;
	}
	
	@Override
	public List<TmApplyMainInfo> queryByMap3(Map<String, Object> map) {
		
		List<TmApplyMainInfo> findListByMap = tmApplyMainInfoDaoImpl.findWithByMap3(map);
		return findListByMap;
	}
	
	@Override
	public List<TmApplyMainInfo> queryAPP1(Map<String, Object> map) {
		List<TmApplyMainInfo> findListByMap = tmApplyMainInfoDaoImpl.queryAPP1(map);
		return findListByMap;
	}
	
	@Override
	public List<TmApplyMainInfo> queryAPP2(Map<String, Object> map) {
		List<TmApplyMainInfo> findListByMap = tmApplyMainInfoDaoImpl.queryAPP2(map);
		return findListByMap;
	}

	@Override
	public List<TmApplyMainInfo> queryAPP3(Map<String, Object> map) {
		List<TmApplyMainInfo> findListByMap = tmApplyMainInfoDaoImpl.queryAPP3(map);
		return findListByMap;
	}
	
	@Override
	public List<TmApplyMainInfo> pageQueryByMap(Map<String, Object> map) {
		
		List<TmApplyMainInfo> mainInfos= (List<TmApplyMainInfo>) tmApplyMainInfoDaoImpl.findWithByMap(map);
		return mainInfos;
	}

	@Override
	public int countByMap(Map<String, Object> map) {
		
		int count = tmApplyMainInfoDaoImpl.countByMap(map);

		return count;
	}

	@Override
	public void saveMainInfo(Vo_600005 vo_600005, String appNo, String hasCreditReport, String ifNext, String promptMessages, String promptZXMessage, 
			Date applyDate, Date lastThreeDay, Date lastDay, Date lastSubmitTime) {

		TmApplyMainInfo tmApplyMainInfo = new TmApplyMainInfo();
		tmApplyMainInfo.setUserId(vo_600005.getUserCode());
		tmApplyMainInfo.setAppNo(appNo);
		tmApplyMainInfo.setIdNo(vo_600005.getIdNo());
		tmApplyMainInfo.setUserName(vo_600005.getName());
		tmApplyMainInfo.setApplyDate(applyDate);
		tmApplyMainInfo.setApplyAccount(Double.parseDouble(vo_600005.getApplyLmt().trim()));
		tmApplyMainInfo.setProductName(vo_600005.getProductName());
		tmApplyMainInfo.setApplyTerm(Long.parseLong(vo_600005.getApplyTerm()));
		tmApplyMainInfo.setProductCode(vo_600005.getProductCd());
		tmApplyMainInfo.setHasCreditReport(hasCreditReport);
		tmApplyMainInfo.setIfNext(ifNext);
		tmApplyMainInfo.setPromptMessages(promptMessages);
		tmApplyMainInfo.setPromptZXMessage(promptZXMessage);
		
		tmApplyMainInfo.setLastThreeDay(lastThreeDay);//最后第三天工作日
		tmApplyMainInfo.setLastDay(lastDay);//最后一天工作日
		tmApplyMainInfo.setLastSubmitTime(lastSubmitTime);//最后一次提交的时间
		tmApplyMainInfo.setState("0");
		
		tmApplyMainInfoDaoImpl.insert(tmApplyMainInfo);
	}

	@Override
	public void deleteByAppNo(Map<String, Object> map) {
		
		tmApplyMainInfoDaoImpl.deleteByAppNo(map);
	}

	@Override
	public List<TmApplyMainInfo> queryForPhotoMove(Map<String, Object> map) {
		List<TmApplyMainInfo> mainInfos= (List<TmApplyMainInfo>) tmApplyMainInfoDaoImpl.queryForPhotoMove(map);
		return mainInfos;
	}

	@Override
	public int countForPhotoMove(Map<String, Object> map) {
		int count = tmApplyMainInfoDaoImpl.countForPhotoMove(map);
		return count;
	}

	@Override
	public List<TmApplyMainInfo> getNoSubmitList(Map<String, Object> map) {
		return tmApplyMainInfoDaoImpl.getNoSubmitList(map);
	}
	
	@Override
	public int getNoSubmitListCount(List<Map<String, Object>> list) {
		return tmApplyMainInfoDaoImpl.getNoSubmitListCount(list);
	}

	@Override
	public int claimNoSubmit(Map<String, Object> map) {
		return tmApplyMainInfoDaoImpl.claimNoSubmit(map);
	}

	@Override
	public List<TmApplyMainInfo> getTimeoutApp() {
		return tmApplyMainInfoDaoImpl.getTimeoutApp();
	}

}
