package com.zdmoney.credit.framework.init;

import javax.servlet.ServletContext;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.web.context.ServletContextAware;

/**
 * 初始化应用数据
 * 
 * @author Ivan
 *
 */
public class InitDataListener implements InitializingBean, ServletContextAware {

	@Override
	public void setServletContext(ServletContext servletContext) {

	}

	@Override
	public void afterPropertiesSet() throws Exception {

	}

}
