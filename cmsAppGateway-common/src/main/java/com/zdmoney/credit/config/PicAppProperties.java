package com.zdmoney.credit.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * 
 * @author YM10102
 *
 */
@Component
public class PicAppProperties {

	/** HTTP接口地址 **/
	@Value("${picapp.serviceUrl}")
	private String serviceUrl;

	public String getServiceUrl() {
		return serviceUrl;
	}

}
