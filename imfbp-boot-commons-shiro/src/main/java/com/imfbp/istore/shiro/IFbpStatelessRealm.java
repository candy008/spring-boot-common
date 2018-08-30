package com.imfbp.istore.shiro;

import java.util.ArrayList;
import java.util.List;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.subject.PrincipalCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.yonyou.ifbp.auth.session.ISessionManager;
import com.yonyou.ifbp.auth.session.SessionClusterManager;
import com.yonyou.ifbp.auth.shiro.StatelessRealm;
import com.yonyou.ifbp.auth.shiro.StatelessToken;
import com.yonyou.ifbp.auth.token.ITokenProcessor;
import com.yonyou.ifbp.auth.token.TokenFactory;
import com.yonyou.ifbp.auth.token.TokenParameter;
import com.yonyou.ifbp.utils.PropertyUtil;

/**
 * 重写平台 的 属性注入 不进来 添加 set方法
 * 
 * @author fulq
 *
 */
public class IFbpStatelessRealm extends StatelessRealm {

	private static final Logger logger = LoggerFactory.getLogger(StatelessRealm.class);

	@Autowired
	TokenFactory tokenFactory;

	@Autowired
	private ISessionManager sessionManager;

	public boolean supports(AuthenticationToken token) {
		return token instanceof StatelessToken;
	}

	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
		List<String> roles = new ArrayList<String>();
		info.addRoles(roles);
		return info;
	}

	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken atoken) throws AuthenticationException {
		StatelessToken token = (StatelessToken) atoken;
		TokenParameter tp = token.getTp();
		String uname = (String) token.getPrincipal();
		ITokenProcessor tokenProcessor = token.getTokenProcessor();
		String tokenStr = tokenProcessor.generateToken(tp);
		if ((tokenStr == null) || (!sessionManager.validateOnlineSession(uname, tokenStr))) {
			logger.error("User [{}] authenticate fail in System, maybe session timeout!", uname);
			throw new AuthenticationException("User " + uname + " authenticate fail in System");
		}

		return new SimpleAuthenticationInfo(uname, tokenStr, getName());
	}

	public boolean validateOnlineSession(String uname, String tokenStr) {
		String type = PropertyUtil.getPropertyByKey("redis.seesion.type", "normal");
		if (type.equals("cluster")) {
			SessionClusterManager sessionClusterManager = (SessionClusterManager) sessionManager;
			return sessionClusterManager.validateOnlineSession(uname, tokenStr);
		}
		return sessionManager.validateOnlineSession(uname, tokenStr);
	}

	public TokenFactory getTokenFactory() {
		return tokenFactory;
	}

	public void setTokenFactory(TokenFactory tokenFactory) {
		this.tokenFactory = tokenFactory;
	}

	public ISessionManager getSessionManager() {
		return sessionManager;
	}

	public void setSessionManager(ISessionManager sessionManager) {
		this.sessionManager = sessionManager;
	}

}
