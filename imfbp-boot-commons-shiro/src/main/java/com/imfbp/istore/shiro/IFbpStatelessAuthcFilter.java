package com.imfbp.istore.shiro;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.yonyou.ifbp.auth.shiro.StatelessAuthcFilter;
import org.springframework.util.CollectionUtils;

/**
 * 重写 平台的 获取 信息
 *
 * @author fulq
 *
 */
public class IFbpStatelessAuthcFilter extends StatelessAuthcFilter {
	List<String> notCheckUrl = new ArrayList<String>();
	List<String> notCheckSubAllUrl = new ArrayList<String>();
	//过滤url中需要排除的部分
	List<String> notCheckExcludeUrl = new ArrayList<String>();

	@Override
	protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
		HttpServletRequest hReq = (HttpServletRequest) request;
		// TODO 正式环境注释掉 ==begin
		String clientDeviceType = hReq.getHeader("clientDeviceType");

		if ("mobile".equalsIgnoreCase(clientDeviceType))
			return true;
		if (CollectionUtils.isEmpty(notCheckExcludeUrl)) {
			if (isNotCheckUrl(hReq)) {
				return true;
			}
		} else {
			if (isNotCheckUrl(hReq) && (isNotCheckExcludeUrl(hReq))) {
				return true;
			}
		}
		boolean isSucess = super.onAccessDenied(request, response);
		return isSucess;
	}

	@Override
	protected void onAjaxAuthFail(ServletRequest request, ServletResponse resp) throws IOException {
		HttpServletResponse response = (HttpServletResponse) resp;
		response.setHeader("logtypelog", "logtypelog");
		super.onAjaxAuthFail(request, resp);
	}

	private boolean isNotCheckUrl(HttpServletRequest hReq) {
		boolean isNotCheck = false;
		String clientUri = hReq.getRequestURI().substring(hReq.getContextPath().length());
		clientUri = clientUri.replace("//", "/");
		isNotCheck = notCheckUrl.contains(clientUri);
		// 如果检查时再判断下 有星的情况
		if (!isNotCheck) {
			if (notCheckSubAllUrl != null && notCheckSubAllUrl.size() > 0) {
				for (String requestUrl : notCheckSubAllUrl) {
					if (clientUri.startsWith(requestUrl))
						isNotCheck = true;
				}
			}
		}
		return isNotCheck;

	}

	/**
	 * 需要过滤url中的需要排除的部分
	 * @author: zhengjm5
	 * @Date: 2018-01-09 15:41:43
	 * @param hReq
	 * @return
	 */
	private boolean isNotCheckExcludeUrl(HttpServletRequest hReq) {
		boolean isNotCheck = false;
		String clientUri = hReq.getRequestURI().substring(hReq.getContextPath().length());
		clientUri = clientUri.replace("//", "/");
		isNotCheck = notCheckExcludeUrl.contains(clientUri);
		if (isNotCheck) {
			return !isNotCheck;
		}
		return true;
	}

	public List<String> getNotCheckUrl() {
		return notCheckUrl;
	}

	public void setNotCheckUrl(List<String> notCheckUrl) {
		this.notCheckUrl = notCheckUrl;
		if (notCheckUrl != null && notCheckUrl.size() > 0) {
			for (String url : notCheckUrl) {
				if (url.contains("*"))
					notCheckSubAllUrl.add(url.substring(0, url.indexOf("*")));
			}
		}
	}

	public void setNotCheckExcludeUrl(List<String> notCheckExcludeUrl) {
		this.notCheckExcludeUrl = notCheckExcludeUrl;
	}
}
