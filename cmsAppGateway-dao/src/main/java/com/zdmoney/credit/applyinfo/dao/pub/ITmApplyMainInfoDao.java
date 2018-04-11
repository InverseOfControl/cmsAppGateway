package com.zdmoney.credit.applyinfo.dao.pub;

import java.util.List;
import java.util.Map;

import com.zdmoney.credit.applyinfo.domain.TmApplyMainInfo;
import com.zdmoney.credit.framework.dao.pub.IBaseDao;
import com.zdmoney.credit.framework.domain.BaseDomain;

public interface ITmApplyMainInfoDao extends IBaseDao<TmApplyMainInfo>{

	int countByMap(Map<String, Object> map);
	
	int updateMainTime(TmApplyMainInfo tami);

	List<TmApplyMainInfo> findWithByMap(Map<String, Object> map);
	
	List<TmApplyMainInfo> findWithByMap2(Map<String, Object> map);
	
	List<TmApplyMainInfo> findWithByMap3(Map<String, Object> map);
	
	List<TmApplyMainInfo> queryAPP1(Map<String, Object> map);
	
	List<TmApplyMainInfo> queryAPP2(Map<String, Object> map);

	List<TmApplyMainInfo> queryAPP3(Map<String, Object> map);

	void deleteByAppNo(Map<String, Object> map);

	List<TmApplyMainInfo> queryForPhotoMove(Map<String, Object> map);

	int countForPhotoMove(Map<String, Object> map);
	
	List<TmApplyMainInfo> getNoSubmitList(Map<String,Object> map);
	
	int getNoSubmitListCount(List<Map<String,Object>> list);
	
	int claimNoSubmit(Map<String,Object> map);
	
	List<TmApplyMainInfo> getTimeoutApp();
}
