package com.zdmoney.credit.applyinfo.service.pub;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.zdmoney.credit.applyinfo.domain.TmApplyFieldInfo;
import com.zdmoney.credit.framework.vo.Vo_600005;

/**
 * 示例
 * 
 *
 */
@Service
public interface ITmApplyFieldInfoService {

	
	public void insert(TmApplyFieldInfo tmApplyFieldInfo);

	public void del(long id);

	public List<TmApplyFieldInfo> queryByMap(Map<String, Object> map);

	public void update(TmApplyFieldInfo tmApplyFieldInfo);

	public void saveFieldInfo(Vo_600005 vo_600005, String appNo, String applyState);
	
	public void saveFieldInfo2(Vo_600005 vo_600005, String appNo, String applyState);

	public void deleteByAppNo(Map<String, Object> map);

	public void deleteByCon(Map<String, Object> map);
	
	//更新客户详单信息
	int upObjInfoById(Map<String, Object> map);
	//查询未提交客户信息
	Map<String, Object> queryCustomers(Map<String, String> map)throws Exception;
	public Map<String, String> queryCustInfo(Map<String, String> map);
	public String queryPhoneOperator(String phoneNum);

}
