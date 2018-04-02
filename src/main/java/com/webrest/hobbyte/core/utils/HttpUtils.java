/**
 * 
 */
package com.webrest.hobbyte.core.utils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import com.webrest.hobbyte.core.http.context.IHttpContext;

/**
 * @author Emil Wojewódka
 *
 * @since 2 kwi 2018
 */
public class HttpUtils {

	/**
	 * Return cookie from request if exists. In otherwise return null. </br>
	 * <i>Note: if any param is null or empty also return null.</i>
	 * 
	 * @param cookieName
	 * @param request
	 * @return
	 */
	public static Cookie getCookies(String cookieName, HttpServletRequest request) {
		if (StringUtils.isEmpty(cookieName) || request == null)
			return null;
		Cookie[] cookies = request.getCookies();
		for (Cookie cookie : cookies) {
			if (cookie.getName().equals(cookieName))
				return cookie;
		}
		return null;
	}

	/**
	 * Remove cookie from {@link IHttpContext}
	 * 
	 * @param cookieName
	 * @param context
	 */
	public static void removeCookieIfExists(String cookieName, IHttpContext context) {
		Cookie cookie = getCookies(cookieName, context.getRequest());
		if (cookie == null)
			return;

		cookie.setMaxAge(0);
		cookie.setValue("");
		context.getResponse().addCookie(cookie);
	}

	/**
	 * It's set for 10 years.
	 * 
	 * @param cookie
	 */
	public static void setMaxAgeCookie(Cookie cookie) {
		cookie.setMaxAge(1000 * 60 * 60 * 24 * 365 * 10);
	}

}
