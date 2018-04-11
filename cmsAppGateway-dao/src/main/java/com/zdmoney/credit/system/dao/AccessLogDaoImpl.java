package com.zdmoney.credit.system.dao;

import org.springframework.stereotype.Repository;

import com.zdmoney.credit.framework.dao.BaseDaoImpl;
import com.zdmoney.credit.system.dao.pub.IAccessLogDao;
import com.zdmoney.credit.system.domain.AccessLog;

@Repository
public class AccessLogDaoImpl extends BaseDaoImpl<AccessLog> implements IAccessLogDao {

}
