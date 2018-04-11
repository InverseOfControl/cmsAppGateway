package com.zdmoney.credit.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;


@Component
public class CreditZxProperties {
	
	@Value("${creditzx.serviceUrl}")
	private String serviceUrl;

	public String getServiceUrl() {
		return serviceUrl;
	}
}
