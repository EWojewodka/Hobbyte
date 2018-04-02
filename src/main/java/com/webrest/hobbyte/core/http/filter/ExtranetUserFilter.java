/**
 * 
 */
package com.webrest.hobbyte.core.http.filter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Service;

import com.webrest.hobbyte.app.user.ExtranetUserUtils;

/**
 * @author Emil Wojewódka
 *
 * @since 10 mar 2018
 */
@Service
public class ExtranetUserFilter extends BasicSecurityFilter{

	
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
