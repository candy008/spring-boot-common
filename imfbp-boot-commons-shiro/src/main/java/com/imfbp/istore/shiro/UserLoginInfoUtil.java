package com.imfbp.istore.shiro;

import com.yonyou.ifbp.context.InvocationInfoProxy;

/**
 * 获取登录 信息
 * 
 * @author fulq
 */
public class UserLoginInfoUtil {

	public static String getLoginUserTenantId() {
		String user = null;
		Object paraObject = InvocationInfoProxy.getExtendAttribute("tenantid");
		if (paraObject != null)
			user = paraObject.toString();
		return user;
	}

	public static String getLoginUserId() {
		String user = null;
		Object paraObject = InvocationInfoProxy.getExtendAttribute("userId");
		if (paraObject != null)
			user = paraObject.toString();
		return user;
	}

	public static String getLoginUserCode() {
		String user = null;
		Object paraObject = InvocationInfoProxy.getExtendAttribute("userCode");
		if (paraObject != null)
			user = paraObject.toString();
		return user;
	}

	public static String getLoginUserName() {
		String user = null;
		Object paraObject = InvocationInfoProxy.getExtendAttribute("userName");
		if (paraObject != null)
			user = paraObject.toString();
		return user;
	}

	public static String getLoginUserToken() {
		String user = null;
		Object paraObject = InvocationInfoProxy.getExtendAttribute("token");
		if (paraObject != null)
			user = paraObject.toString();
		return user;
	}
}
