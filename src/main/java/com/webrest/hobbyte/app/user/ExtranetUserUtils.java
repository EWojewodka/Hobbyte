package com.webrest.hobbyte.app.user;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.webrest.hobbyte.app.user.model.ExtranetUser;
import com.webrest.hobbyte.core.http.context.IHttpContext;

public class ExtranetUserUtils {

	public static final String USER_SESSION_NAME = "logged-user";

	public static final String REMEMBER_ME_COOKIE_NAME = "remember-me";

	public static boolean isLogged(HttpServletRequest request) {
		return request != null && isLogged(request.getSession());
	}

	public static boolean isLogged(HttpSession session) {
		return getUser(session) != null;
	}
	public static boolean isLogged(IHttpContext context) {
		return isLogged(context.getRequest());
	}

	public static ExtranetUser getUser(IHttpContext context) {
		return getUser(context.getRequest());
	}
	
	/**
	 * Return user from request session;
	 * 
	 * @param request
	 * @return
	 */
	public static ExtranetUser getUser(HttpServletRequest request) {
		if (request == null)
			return null;
		return getUser(request.getSession());
	}

	/**
	 * Return user from session.
	 * 
	 * @param session
	 * @return
	 */
	public static ExtranetUser getUser(HttpSession session) {
		if (session == null)
			return null;
		return (ExtranetUser) session.getAttribute(USER_SESSION_NAME);
	}

	/**
	 * Remove {@link ExtranetUser} from {@link HttpSession}
	 * 
	 * @param request
	 */
	public static void logoutUser(HttpServletRequest request) {
		if (request == null)
			return;
		logoutUser(request.getSession(false));
	}

	/**
	 * Remove {@link ExtranetUser} from {@link HttpSession}.
	 * 
	 * @param session
	 */
	public static void logoutUser(HttpSession session) {
		if (session == null)
			return;
		session.removeAttribute(USER_SESSION_NAME);
		session.invalidate();
	}

}