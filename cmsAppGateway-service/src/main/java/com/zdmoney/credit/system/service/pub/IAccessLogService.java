package com.zdmoney.credit.system.service.pub;

import com.zdmoney.credit.system.domain.AccessLog;

public interface IAccessLogService {

	/**
	 * 新增记录
	 * 
	 * @param accessLog
	 * @return
	 */
	public AccessLog insert(AccessLog accessLog);

}
