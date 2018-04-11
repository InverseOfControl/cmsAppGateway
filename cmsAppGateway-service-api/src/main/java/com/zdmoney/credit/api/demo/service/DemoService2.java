package com.zdmoney.credit.api.demo.service;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zdmoney.credit.api.framework.service.BusinessService;
import com.zdmoney.credit.common.annotate.FuncIdAnnotate;
import com.zdmoney.credit.demo.service.pub.IDemoService;
import com.zdmoney.credit.framework.vo.Vo_100002;

/**
 * 业务接口 定义
 * 
 * @author Ivan
 *
 */
@Service
public class DemoService2 extends BusinessService {

	protected static Log logger = LogFactory.getLog(DemoService2.class);

	@Autowired
	IDemoService demoServiceImpl;

	/**
	 * 功能号 100002 代码块
	 * 
	 * @param myName
	 * @return
	 */
	@FuncIdAnnotate(value = "100002", desc = "功能号描述请补充", voCls = Vo_100002.class)
	public String f2(Vo_100002 vo_100002) {
		return "test function f2 instance :" + demoServiceImpl + " params:" + vo_100002;
	}
}
