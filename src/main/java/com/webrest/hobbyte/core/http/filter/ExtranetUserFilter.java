/**
 * 
 */
package com.webrest.hobbyte.core.http.filter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.webrest.hobbyte.app.user.ExtranetUserUtils;

/**
 * @author Emil Wojewódka
 *
 * @since 10 mar 2018
 */
public class ExtranetUserFilter extends BasicAbstractFilter{

	
	@Override
	public String getPath() {
		return "/profile/settings/**";
	}

	@Override
	public String redirectOnFail() {
		return "/auth/sign-up";
	}

	@Override
	public boolean hasAccess(HttpServletRequest request, HttpServletResponse repsonse) throws Exception {
		return ExtranetUserUtils.isLogged(request);
	}

}
