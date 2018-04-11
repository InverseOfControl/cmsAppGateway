package com.zdmoney.credit.api.demo.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zdmoney.credit.api.framework.service.BusinessService;
import com.zdmoney.credit.common.annotate.FuncIdAnnotate;
import com.zdmoney.credit.demo.service.pub.IDemoService;
import com.zdmoney.credit.framework.vo.Vo_100001;

/**
 * 业务接口 定义
 * 
 * @author Ivan
 *
 */
@Service
public class DemoService1 extends BusinessService {

	protected static Log logger = LogFactory.getLog(DemoService1.class);

	@Autowired
	IDemoService demoServiceImpl;

	/**
	 * 功能号 100001 代码块
	 * 
	 * @param myName
	 * @param aa
	 * @return
	 */
	@FuncIdAnnotate(value = "100001", desc = "功能号描述请补充", voCls = Vo_100001.class)
	public List<Map<String, String>> f1(Vo_100001 vo_100001) {
		List<Map<String, String>> result = new ArrayList<Map<String, String>>();
		for (int i = 0; i < 5; i++) {
			Map<String, String> data = new HashMap<String, String>();
			data.put("a", i + "!");
			data.put("b", i + "@");
			data.put("c", i + "#");
			result.add(data);
		}
		return result;
		// return "test function f1 instance :" + demoServiceImpl + " params:" +
		// vo_100001;
	}
}
