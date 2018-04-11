package com.zdmoney.credit.applyinfo.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.zdmoney.credit.applyinfo.dao.pub.ITmApplyMainInfoDao;
import com.zdmoney.credit.applyinfo.domain.TmApplyMainInfo;
import com.zdmoney.credit.framework.dao.BaseDaoImpl;

@Repository
public class TmApplyMainInfoDaoImpl extends BaseDaoImpl<TmApplyMainInfo> implements ITmApplyMainInfoDao {


	@Override
	public int countByMap(Map<String, Object> map) {

		int count = getSqlSession().selectOne(getIbatisMapperNameSpace() + ".count", map);
		return count;
	}

	@Override
	public int updateMainTime(TmApplyMainInfo tami) {
		int count = getSqlSession().update(getIbatisMapperNameSpace() + ".updateMainTime", tami);
		return count;
	}
	
	@Override
	public List<TmApplyMainInfo> findWithByMap(Map<String, Object> map) {
		
		List<TmApplyMainInfo> mains = getSqlSession().selectList(getIbatisMapperNameSpace() + ".findWithByMap", map);
		return mains;
	}
	
	@Override
	public List<TmApplyMainInfo> findWithByMap2(Map<String, Object> map) {
		
		List<TmApplyMainInfo> mains = getSqlSession().selectList(getIbatisMapperNameSpace() + ".findWithByMap2", map);
		return mains;
	}
	
	@Override
	public List<TmApplyMainInfo> findWithByMap3(Map<String, Object> map) {
		
		List<TmApplyMainInfo> mains = getSqlSession().selectList(getIbatisMapperNameSpace() + ".findWithByMap3", map);
		return mains;
	}
	
	@Override
	public List<TmApplyMainInfo> queryAPP1(Map<String, Object> map) {
		
		List<TmApplyMainInfo> mains = getSqlSession().selectList(getIbatisMapperNameSpace() + ".queryAPP1", map);
		return mains;
	}
	
	@Override
	public List<TmApplyMainInfo> queryAPP2(Map<String, Object> map) {
		
		List<TmApplyMainInfo> mains = getSqlSession().selectList(getIbatisMapperNameSpace() + ".queryAPP2", map);
		return mains;
	}
	
	@Override
	public List<TmApplyMainInfo> queryAPP3(Map<String, Object> map) {
		
		List<TmApplyMainInfo> mains = getSqlSession().selectList(getIbatisMapperNameSpace() + ".queryAPP3", map);
		return mains;
	}

	@Override
	public void deleteByAppNo(Map<String, Object> map) {
		
		getSqlSession().delete(getIbatisMapperNameSpace()+ ".deleteByAppNo", map);
	}

	@Override
	public List<TmApplyMainInfo> queryForPhotoMove(Map<String, Object> map) {
		List<TmApplyMainInfo> mains = getSqlSession().selectList(getIbatisMapperNameSpace() + ".queryForPhotoMove", map);
		return mains;
	}

	@Override
	public int countForPhotoMove(Map<String, Object> map) {
		int count = getSqlSession().selectOne(getIbatisMapperNameSpace() + ".countForPhotoMove", map);
		return count;
	}

	@Override
	public List<TmApplyMainInfo> getNoSubmitList(Map<String, Object> map) {
		return getSqlSession().selectList(getIbatisMapperNameSpace() + ".getNoSubmitList", map);
	}
	
	@Override
	public int getNoSubmitListCount(List<Map<String, Object>> list) {
		return getSqlSession().selectOne(getIbatisMapperNameSpace() + ".getNoSubmitListCount", list);
	}

	@Override
	public int claimNoSubmit(Map<String, Object> map) {
		return getSqlSession().update(getIbatisMapperNameSpace() + ".claimNoSubmit", map);
	}

	@Override
	public List<TmApplyMainInfo> getTimeoutApp() {
		return getSqlSession().selectList(getIbatisMapperNameSpace() + ".getTimeoutApp");
	}

}
