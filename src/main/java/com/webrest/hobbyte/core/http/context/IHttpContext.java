package com.webrest.hobbyte.core.http.context;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Basic servlet info wrapper.
 * 
 * @author wojew
 *
 */
public interface IHttpContext {

	/**
	 * Get request.
	 * 
	 * @return
	 */
	HttpServletRequest getRequest();

	/**
	 * Get response;
	 * 
	 * @return
	 */
	HttpServletResponse getResponse();

	/**
	 * Get session from request.
	 * 
	 * @return
	 */
	HttpSession getSession();

	/**
	 * Return session from {@link HttpServletRequest} or create if not exists.
	 * 
	 * @param force
	 *            new session
	 * @return
	 */
	HttpSession getSession(boolean force);
}
