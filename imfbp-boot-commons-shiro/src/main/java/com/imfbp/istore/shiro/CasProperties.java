package com.imfbp.istore.shiro;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "imfbp.cas")
public class CasProperties {

	private String serverUrl;
	private String casLoginUrl;
	private String casServerUrlPrefix;
	public String getServerUrl() {
		return serverUrl;
	}
	public void setServerUrl(String serverUrl) {
		this.serverUrl = serverUrl;
	}
	public String getCasLoginUrl() {
		return casLoginUrl;
	}
	public void setCasLoginUrl(String casLoginUrl) {
		this.casLoginUrl = casLoginUrl;
	}
	public String getCasServerUrlPrefix() {
		return casServerUrlPrefix;
	}
	public void setCasServerUrlPrefix(String casServerUrlPrefix) {
		this.casServerUrlPrefix = casServerUrlPrefix;
	}
}
