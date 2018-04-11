package com.zdmoney.credit.demo.dao;

import org.springframework.stereotype.Repository;

import com.zdmoney.credit.demo.dao.pub.IDemoDao;
import com.zdmoney.credit.demo.domain.Demo;
import com.zdmoney.credit.framework.dao.BaseDaoImpl;

/**
 * 示例
 * 
 * @author Ivan
 *
 */
@Repository
public class DemoDaoImpl extends BaseDaoImpl<Demo> implements IDemoDao {

}
