package com.zdmoney.credit.applyinfo.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.zdmoney.credit.applyinfo.dao.pub.IAPPNotCommitApplyDao;
import com.zdmoney.credit.applyinfo.domain.TmApplyMainInfo;
import com.zdmoney.credit.applyinfo.service.pub.IAPPNotCommitApplyService;
@Service
public class APPNotCommitApplyServiceImpl implements IAPPNotCommitApplyService{
	@Autowired
	private IAPPNotCommitApplyDao iaPPNotCommitApplyDao;
	@Override
	public List<TmApplyMainInfo> queryNotCommitApplyAppNo(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return iaPPNotCommitApplyDao.queryNotCommitApplyAppNo(map);
	}
	@Override
	public Integer queryNotCommitApplyAppNoCount(Map<String, Object> map) {
		return iaPPNotCommitApplyDao.queryNotCommitApplyAppNoCount(map);
	}

}
