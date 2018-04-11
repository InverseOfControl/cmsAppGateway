package com.zdmoney.credit.system.dao.pub;

import java.util.List;

import com.zdmoney.credit.framework.dao.pub.IBaseDao;
import com.zdmoney.credit.system.domain.SysParamDefine;

public interface ISysParamDefineDao extends IBaseDao<SysParamDefine> {

	/**
	 * 
	 * @param paramId
	 * @param magicId
	 * @param b
	 * @return
	 */
	public String getSysParamValue(String magicType, String param_key, boolean b);
	
	//查询运营商号码段匹配规
	public List<SysParamDefine> selectRule();

}
