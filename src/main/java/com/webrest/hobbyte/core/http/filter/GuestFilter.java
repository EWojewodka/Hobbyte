package com.webrest.hobbyte.core.http.filter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.webrest.hobbyte.app.user.ExtranetUserUtils;

/**
 * Only guest can see this path. </br>
 * In other way they should be redirect to index.
 * 
 * @author wojew
 *
 */
public class GuestFilter extends BasicAbstractFilter {

	@Override
	public String getPath() {
		return "/auth/**";
	}

	@Override
	public boolean hasAccess(HttpServletRequest request, HttpServletResponse repsonse) throws Exception {
		return !ExtranetUserUtils.isLogged(request);
	}

	@Override
	public String redirectOnFail() {
		return "/profile";
	}

}
