package com.zdmoney.credit.applyinfo.dao.pub;

import java.util.List;
import java.util.Map;

import com.zdmoney.credit.applyinfo.domain.TmApplyFieldInfo;
import com.zdmoney.credit.framework.dao.pub.IBaseDao;

public interface ITmApplyFieldInfoDao extends IBaseDao<TmApplyFieldInfo>{

	void deleteByAppNo(Map<String, Object> map);

	void deleteByCon(Map<String, Object> map);
	
	void updateAppNo(Map<String, Object> map);
	
	//更新客户详单信息
	int upObjInfoById(Map<String, Object> map);
	//查询未提交客户信息
	List<Map<String, Object>> queryCustomers(Map<String, String> map);
	//查询客户个人信息
	List<Map<String, String>> queryCustInfo(Map<String,String>map);
	
	/**
	 * <p>Description:同步联系人信息和配偶信息</p>
	 * @uthor YM10159
	 * @date 2017年7月25日 下午6:01:04
	 */
	int syncContactPersonInfo(TmApplyFieldInfo tmApplyFieldInfo);
	
}
