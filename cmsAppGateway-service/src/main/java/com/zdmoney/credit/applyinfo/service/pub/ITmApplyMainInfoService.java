package com.zdmoney.credit.applyinfo.service.pub;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.zdmoney.credit.applyinfo.domain.TmApplyMainInfo;
import com.zdmoney.credit.framework.vo.Vo_600005;

/**
 * 示例
 * 
 *
 */
@Service
public interface ITmApplyMainInfoService {

	
	public void insert(TmApplyMainInfo tmApplyMainInfo);

	public void del(long id);

	public List<TmApplyMainInfo> queryByMap(Map<String, Object> map);
	
	public List<TmApplyMainInfo> queryByMap2(Map<String, Object> map);
	
	public List<TmApplyMainInfo> queryByMap3(Map<String, Object> map);
	
	public List<TmApplyMainInfo> queryAPP1(Map<String, Object> map);
	
	public List<TmApplyMainInfo> queryAPP2(Map<String, Object> map);
	
	public List<TmApplyMainInfo> queryAPP3(Map<String, Object> map);
	
	public void update(TmApplyMainInfo tmApplyMainInfo);
	
	public void updateMainTime(TmApplyMainInfo tami);

	public List<TmApplyMainInfo> pageQueryByMap(Map<String, Object> map);

	public int countByMap(Map<String, Object> map);

	public void saveMainInfo(Vo_600005 vo_600005, String appNo, String hasCreditReport, String ifNext, String promptMessages, String promptZXMessage, 
			Date applyDate, Date lastThreeDay, Date lastDay, Date lastSubmitTime);

	public void deleteByAppNo(Map<String, Object> map);

	public List<TmApplyMainInfo> queryForPhotoMove(Map<String, Object> map);

	public int countForPhotoMove(Map<String, Object> map);
	
	/**
	 * <p>Description:认领申请-查询离职员工待提交的申请件</p>
	 * @uthor YM10159
	 * @date 2017年6月19日 下午7:07:39
	 * @param map 参数 
	 * @return List<TmApplyMainInfo> 待提交申请件集合 
	 */
	List<TmApplyMainInfo> getNoSubmitList(Map<String,Object> map);
	
	int getNoSubmitListCount(List<Map<String,Object>> list);
	
	/**
	 * <p>Description:认领申请-认领待提交的申请件</p>
	 * @uthor YM10159
	 * @date 2017年6月20日 上午9:59:41
	 */
	int claimNoSubmit(Map<String,Object> map);
	
	/**
	 * <p>Description:获取超时的申请件</p>
	 * @uthor YM10159
	 * @date 2017年9月20日 下午2:12:22
	 * @return 返回超时的申请件列表 
	 */
	public List<TmApplyMainInfo> getTimeoutApp();

}
