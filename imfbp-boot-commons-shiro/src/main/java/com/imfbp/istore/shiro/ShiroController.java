package com.imfbp.istore.shiro;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.jasig.cas.client.authentication.AttributePrincipal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.yonyou.ifbp.auth.token.ITokenProcessor;
import com.yonyou.ifbp.auth.token.TokenParameter;

@Controller
@ConfigurationProperties(prefix = "ifbp")
public class ShiroController {


	@Autowired
	protected ITokenProcessor webTokenProcessor;

	@Value("${ifbp.context-name}")
	private String cookiePath;

	@RequestMapping(value = "sso/login", method = { RequestMethod.GET })
	public void logindo(HttpServletRequest request, HttpServletResponse response) {
		try {
			
			
			
			
			AttributePrincipal principal = (AttributePrincipal) request.getUserPrincipal();
			if (null != principal) {
				
				Map<String, Object> attrMap = principal.getAttributes();
				
				String username = (String) attrMap.get("username");

				if (StringUtils.isNotBlank(username)) {

					String userid = (String) attrMap.get("id");
					String nickname = (String) attrMap.get("nickname");

					String rURL = request.getQueryString();
					rURL = rURL.substring(2);
					if(rURL.contains("ticket")){
						String substr = rURL.substring(rURL.indexOf("?")+1);
						if(StringUtils.isNotEmpty(substr)){
							String[] arr = substr.split("&");
							for (String str : arr) {
								if(str.contains("ticket")&& rURL.contains("&ticket")){
									rURL = rURL.replace("&"+str,"");
								}
								if(str.contains("ticket")){
									rURL = rURL.replace(str,"");
								}
							}
						}
					}

					// 用来存储用户信息
					HashMap<String, String> map = new HashMap<String, String>();

					if (null != map) {
						if (StringUtils.isBlank(nickname)) {
							nickname = map.get("username");
						}
						TokenParameter tokenParam = new TokenParameter();
						tokenParam.setUserid(userid);
						tokenParam.setLogints(String.valueOf(new Date().getTime()));
						Cookie[] cookies = webTokenProcessor.getCookieFromTokenParameter(tokenParam);
						CookieUtil.addCookiesReturnToken(cookies, response);
						
						System.out.println("======================"+username+"=======================");
						
						String enusername =  URLEncoder.encode(username, "utf-8");
						
						System.out.println("======================"+enusername+"=======================");

						CookieUtil.addCookie("c_u_c_k_tt", enusername, null, -1,cookiePath,response);
						CookieUtil.addCookie("c_u_i_k_tt", userid, null, -1,cookiePath,response);
						if(StringUtils.isNotBlank(nickname)){
							CookieUtil.addCookie("c_u_nk_k_tt", nickname, null, -1,cookiePath,response);
						}
					}
					
//					if(StringUtils.isNoneBlank(rURL)){
//						if(rURL.toLowerCase().contains("http")){
//							response.sendRedirect(rURL);
//							return;
//						}
//					}else{
//						
//					}
					
					if(rURL.toLowerCase().contains("http")){
						response.sendRedirect(rURL);
						return;
					}
					
//					rURL = new String(Base64.decodeBase64(rURL), "UTF-8");					
//					response.sendRedirect(rURL);
//					return;
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	//http://dev-test.ins.imfbp.com/istore-web/pages/istore_order/InsureInfo/index.html?ticket=ST-46-iLxFxsDzjbIlrB2CgyQJ-CAS-NODE-01&fkSkuId=7QX0075EHIINDG1000GR&&skuAttribute=FI6IB75EHIHI5V1001ON,

}
