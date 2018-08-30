package com.imfbp.istore.shiro;

import java.util.EventListener;

import javax.annotation.Resource;

import org.jasig.cas.client.authentication.AuthenticationFilter;
import org.jasig.cas.client.session.SingleSignOutHttpSessionListener;
import org.jasig.cas.client.util.HttpServletRequestWrapperFilter;
import org.jasig.cas.client.validation.Cas20ProxyReceivingTicketValidationFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletListenerRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

import com.yonyou.ifbp.auth.shiro.CasLogoutFilter;

@Configuration
@ImportResource(locations = { "classpath:ifbp-shiro.xml" })
public class IFbpShiroConfig {

	@Resource
	private CasProperties casProperties;

	/**
	 * caS 注銷监听
	 * 
	 * @return
	 */
	@Bean
	public ServletListenerRegistrationBean<EventListener> servletListenerRegistrationBean() {
		ServletListenerRegistrationBean<EventListener> servletListenerRegistrationBean = new ServletListenerRegistrationBean<EventListener>();
		SingleSignOutHttpSessionListener singleSignOutHttpSessionListener = new SingleSignOutHttpSessionListener();
		servletListenerRegistrationBean.setListener(singleSignOutHttpSessionListener);
		return servletListenerRegistrationBean;
	}

	/**
	 * cas token 校验
	 * 
	 * @return
	 */
	@Bean
	public FilterRegistrationBean authenticationRegistration() {
		AuthenticationFilter authenticationFilter = new AuthenticationFilter();

		FilterRegistrationBean registration = new FilterRegistrationBean();
		registration.setFilter(authenticationFilter);
		registration.addUrlPatterns("/sso/login");

		registration.addInitParameter("casServerLoginUrl", casProperties.getCasLoginUrl());
		registration.addInitParameter("serverName", casProperties.getServerUrl());

		registration.setName("CAS Authentication Filter");
		registration.setOrder(1);
		return registration;
	}

	/**
	 * cas 认证校验
	 * 
	 * @return
	 */
	@Bean
	public FilterRegistrationBean Cas20ProxyReceivingTicketValidationRegistration() {

		Cas20ProxyReceivingTicketValidationFilter cas20ProxyReceivingTicketValidationFilter = new Cas20ProxyReceivingTicketValidationFilter();

		FilterRegistrationBean registration = new FilterRegistrationBean();
		registration.setFilter(cas20ProxyReceivingTicketValidationFilter);
		registration.addUrlPatterns("/sso/login");

		registration.addInitParameter("casServerUrlPrefix", casProperties.getCasServerUrlPrefix());
		registration.addInitParameter("serverName", casProperties.getServerUrl());
		registration.addInitParameter("useSession", "false");
		registration.addInitParameter("encoding", "utf-8");

		registration.setName("CAS Validation Filter");
		registration.setOrder(2);
		return registration;
	}

	// /**
	// * shiro 登陆file
	// * @return
	// */
	// @Bean
	// public FilterRegistrationBean httpServletRequestWrapperRegistration() {
	// HttpServletRequestWrapperFilter httpServletRequestWrapperFilter = new
	// HttpServletRequestWrapperFilter();
	// FilterRegistrationBean registration = new FilterRegistrationBean();
	// registration.setFilter(httpServletRequestWrapperFilter);
	// registration.addUrlPatterns("/sso/login");
	// registration.addInitParameter("targetFilterLifecycle", "true");
	// registration.setName("httpServletRequestWrapperRegistration");
	// registration.setOrder(4);
	// return registration;
	// }

	/**
	 * 登场
	 * 
	 * @param casLogoutFilter
	 * @return
	 */
	@Bean
	public FilterRegistrationBean casLogoutFilterRegistration(CasLogoutFilter casLogoutFilter) {

		FilterRegistrationBean registration = new FilterRegistrationBean();
		registration.setFilter(casLogoutFilter);
		registration.addUrlPatterns("/sso/logout");
		registration.setName("casLogoutFilter");
		registration.addInitParameter("casLogoutUrl", casProperties.getCasLoginUrl());
		registration.setOrder(5);
		return registration;
	}

	@Bean
	public FilterRegistrationBean httpServletRequestWrapperRegistration() {
		HttpServletRequestWrapperFilter httpServletRequestWrapperFilter = new HttpServletRequestWrapperFilter();
		FilterRegistrationBean registration = new FilterRegistrationBean();
		registration.setFilter(httpServletRequestWrapperFilter);
		registration.addUrlPatterns("/sso/login");
		registration.addInitParameter("targetFilterLifecycle", "true");
		registration.setName("httpServletRequestWrapperRegistration");
		registration.setOrder(4);
		return registration;
	}

}
