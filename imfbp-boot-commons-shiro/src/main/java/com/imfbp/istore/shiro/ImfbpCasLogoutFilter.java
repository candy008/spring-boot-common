package com.imfbp.istore.shiro;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.yonyou.ifbp.auth.shiro.CasLogoutFilter;

public class ImfbpCasLogoutFilter extends CasLogoutFilter {

	
	@Override
	protected boolean preHandle(ServletRequest request, ServletResponse response) throws Exception {
		HttpServletRequest req =  (HttpServletRequest)request;
		HttpServletResponse resp = (HttpServletResponse)response;
		
		CookieUtil.removeCookie("c_u_c_k_tt", null, req, resp);
		
		CookieUtil.removeCookie("c_u_i_k_tt", null, req, resp);
		CookieUtil.removeCookie("c_u_nk_k_tt", null, req, resp);
		
		boolean isSucess = super.preHandle(request, response);

		return isSucess;
		
	}
}
