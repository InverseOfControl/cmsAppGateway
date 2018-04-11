package com.zdmoney.credit.applyinfo.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.zdmoney.credit.applyinfo.dao.pub.ITmApplyFieldInfoDao;
import com.zdmoney.credit.applyinfo.domain.TmApplyFieldInfo;
import com.zdmoney.credit.framework.dao.BaseDaoImpl;

@Repository
public class TmApplyFieldInfoDaoImpl extends BaseDaoImpl<TmApplyFieldInfo> implements ITmApplyFieldInfoDao {

	@Override
	public void deleteByAppNo(Map<String, Object> map) {

		getSqlSession().delete(getIbatisMapperNameSpace()+ ".deleteByAppNo", map);
	}
	
	@Override
	public void deleteByCon(Map<String, Object> map) {

		getSqlSession().delete(getIbatisMapperNameSpace()+ ".deleteByCon", map);
	}
	
	@Override
	public void updateAppNo(Map<String, Object> map) {

		getSqlSession().delete(getIbatisMapperNameSpace()+ ".updateAppNo", map);
	}
	
	@Override
	public int upObjInfoById(Map<String, Object> map) {
		return getSqlSession().update(getIbatisMapperNameSpace()+".upObjInfoById", map);
	}

	@Override
	public List<Map<String, Object>> queryCustomers(Map<String, String> map) {
		return getSqlSession().selectList(getIbatisMapperNameSpace()+".queryCusInfo", map);
	}

	@Override
	public List<Map<String, String>> queryCustInfo(Map<String, String> map) {
		return getSqlSession().selectList(getIbatisMapperNameSpace()+".selectObjInfoById", map);
	}
	
	@Override
	public int syncContactPersonInfo(TmApplyFieldInfo tmApplyFieldInfo) {
		return getSqlSession().update(getIbatisMapperNameSpace()+".syncContactPersonInfo", tmApplyFieldInfo);
	}

}
