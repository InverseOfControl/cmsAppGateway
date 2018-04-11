package com.zdmoney.credit.applyinfo.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.zdmoney.credit.applyinfo.dao.pub.IAPPNotCommitApplyDao;
import com.zdmoney.credit.applyinfo.domain.TmApplyMainInfo;
import com.zdmoney.credit.framework.dao.BaseDaoImpl;
@Repository
public class APPNotCommitApplyDaoImpl extends BaseDaoImpl<TmApplyMainInfo> implements IAPPNotCommitApplyDao{

	@Override
	public List<TmApplyMainInfo> queryNotCommitApplyAppNo(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return getSqlSession().selectList(getIbatisMapperNameSpace()+".queryNotCommitApplyAppNo", map);
	}

	@Override
	public Integer queryNotCommitApplyAppNoCount(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return getSqlSession().selectOne(getIbatisMapperNameSpace()+".queryNotCommitApplyAppNoCount", map);
	}

}
