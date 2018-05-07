package com.webrest.hobbyte.core.http.controllers;

import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;

import com.webrest.hobbyte.core.exception.RedirectException;
import com.webrest.hobbyte.core.http.context.HttpContext;
import com.webrest.hobbyte.core.http.context.IHttpContext;
import com.webrest.hobbyte.core.http.filter.BasicSecurityFilter;
import com.webrest.hobbyte.core.security.Accessible;

/**
 * Controller which should be expanded for improve controller security.
 * 
 * @deprecated since 31-03-2018 - please register {@link BasicSecurityFilter}
 *             for controller security.
 * @author wojew
 *
 */
@Deprecated
@Controller
public abstract class SecurityAbstractController implements Accessible {

	private HttpContext context;

	/**
	 * Create instance of security controller. </br>
	 * It should not be annotated by {@link Controller} or other things. Context
	 * should be passed by constructor from higher classes.
	 * 
	 * @param context
	 * @throws Exception
	 */
	public SecurityAbstractController(HttpContext context) throws Exception {
		this.context = context;
	}

	/**
	 * If {@link #hasAccess()} return false it'll be try use redirect exception or
	 * if {@link #getRedirectURL()} is null or empty it'll send
	 * {@link HttpServletResponse#SC_FORBIDDEN} status.
	 * 
	 * @throws Exception
	 */
	protected void handleAccess() throws Exception {
		if (hasAccess())
			return;
		if (!StringUtils.isEmpty(getRedirectURL()))
			throw new RedirectException(context, getRedirectURL());
		context.getResponse().sendError(HttpServletResponse.SC_FORBIDDEN);
	}

	/**
	 * Return {@link IHttpContext} interface implementation.
	 * 
	 * @return
	 */
	public IHttpContext getContext() {
		return context;
	}

	/**
	 * URL which'll be used during redirect try into {@link #handleAccess()}
	 * 
	 * @return
	 */
	protected String getRedirectURL() {
		return "";
	}

}
