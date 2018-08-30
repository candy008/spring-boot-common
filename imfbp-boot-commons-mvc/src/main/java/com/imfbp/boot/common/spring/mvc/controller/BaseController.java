package com.imfbp.boot.common.spring.mvc.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceAware;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.imfbp.boot.common.web.cookie.CookieUtils;

/**
 * BaseController
 * @author quanjianjun       
 * @Date 2014年10月31日
 * @Time 下午1:00:34
 */
public class BaseController implements MessageSourceAware {
	
	private MessageSource messageSource;

	protected HttpServletResponse response;
	protected HttpServletRequest request;
	
	private static final String COOKIE_USER_CODE_KEY = "c_u_c_k_tt";
	private static final String COOKIE_USER_ID_KEY = "c_u_i_k_tt";

	@ModelAttribute
	public void setReqAndRes(HttpServletRequest request,HttpServletResponse response) {
		this.request = request;
		this.response = response;
	}
     
	public void setMessageSource(MessageSource messageSource) {
		this.messageSource = messageSource;
	}

	protected String getMessage(String code, Object[] args, Locale locale) {
		return this.messageSource.getMessage(code, args, locale);
	}

	@SuppressWarnings("unchecked")
	protected void addActionError(String text, ModelMap context) {
		List<String> actionMessages = (List<String>) context.get("actionMessages");
		if (actionMessages == null) {
			actionMessages = new ArrayList<String>();
			actionMessages.add(text);
		}
		context.put("actionMessages", actionMessages);
	}

	@SuppressWarnings("unchecked")
	protected void addActionMessage(String text, ModelMap context) {
		List<String> actionErrors = (List<String>) context.get("actionErrors");
		if (actionErrors == null) {
			actionErrors = new ArrayList<String>();
			actionErrors.add(text);
		}
		context.put("actionErrors", actionErrors);
	}
	
	/**
	 * 得到当前登陆用户id
	 * @param request
	 * @return
	 */
	public String getUserId(HttpServletRequest request){
		String userId= CookieUtils.getCookie(request, COOKIE_USER_ID_KEY);
		return StringUtils.isNotBlank(userId)?userId:"";
	}
	
	/**
	 *  得到当前登陆用户名称
	 * @return
	 */
	public String getUserCode(HttpServletRequest request){
		String userCode = CookieUtils.getCookie(request, COOKIE_USER_CODE_KEY);
		return StringUtils.isNotBlank(userCode)?userCode:"";
	}
}