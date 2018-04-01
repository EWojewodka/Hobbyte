/**
 * 
 */
package com.webrest.hobbyte.app.adminPanel.http;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.webrest.hobbyte.app.user.ExtranetUserUtils;
import com.webrest.hobbyte.app.user.model.ExtranetUser;
import com.webrest.hobbyte.app.user.model.enums.ExtranetUserRoles;
import com.webrest.hobbyte.core.http.filter.BasicAbstractFilter;

/**
 * Admin panel filter. Only {@link ExtranetUserRoles#ADMIN} has access to this
 * content.
 * 
 * @author Emil Wojewódka
 *
 * @since 31 mar 2018
 */
public class AdminFilter extends BasicAbstractFilter {

	@Override
	public String getPath() {
		return "/sys/**";
	}

	@Override
	public String redirectOnFail() {
		return "/";
	}

	@Override
	public boolean hasAccess(HttpServletRequest request, HttpServletResponse repsonse) throws Exception {
		ExtranetUser user = ExtranetUserUtils.getUser(request);
		return user != null && user.getRole() == ExtranetUserRoles.ADMIN;
	}

}
