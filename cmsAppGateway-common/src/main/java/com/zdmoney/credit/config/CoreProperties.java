package com.zdmoney.credit.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * 配置信息跟core.properties一致
 * 
 * @author Ivan
 *
 */
@Component
public class CoreProperties {

	/** HTTP接口地址 **/
	@Value("${core.serviceUrl}")
	private String serviceUrl;

	public String getServiceUrl() {
		return serviceUrl;
	}
	
	
}
