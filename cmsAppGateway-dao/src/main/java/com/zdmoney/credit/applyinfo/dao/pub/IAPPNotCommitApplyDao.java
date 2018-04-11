package com.zdmoney.credit.applyinfo.dao.pub;

import java.util.List;
import java.util.Map;

import com.zdmoney.credit.applyinfo.domain.TmApplyMainInfo;

public interface IAPPNotCommitApplyDao {
	List<TmApplyMainInfo> queryNotCommitApplyAppNo(Map<String,Object> map);

	Integer queryNotCommitApplyAppNoCount(Map<String, Object> map);
}
