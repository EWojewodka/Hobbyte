/**
 * 
 */
package com.webrest.hobbyte.app.user.http.filter;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.webrest.hobbyte.app.user.ExtranetUserUtils;
import com.webrest.hobbyte.app.user.dao.ExtranetUserDao;
import com.webrest.hobbyte.app.user.model.ExtranetUser;
import com.webrest.hobbyte.core.http.context.ExtranetUserContext;
import com.webrest.hobbyte.core.http.filter.BasicFilter;
import com.webrest.hobbyte.core.utils.HttpUtils;

/**
 * Filter listening on "/**" path - auto login user if he agree for this.
 * 
 * @author Emil Wojewódka
 *
 * @since 2 kwi 2018
 */
@Service
public class AutoLoginFilter extends BasicFilter {

	@Autowired
	private ExtranetUserDao userDao;

	@Autowired
	private ExtranetUserContext userContext;

	@Override
	public String getPath() {
		return "/**";
	}

	// If user is not logged, check agreement for autologin. If true - try login
	// user.
	@Override
	public void filterBefore(HttpServletRequest request, HttpServletResponse response) {
		if (userContext.isUserLogged())
			return;

		Cookie cookie = HttpUtils.getCookies(ExtranetUserUtils.REMEMBER_ME_COOKIE_NAME, request);
		if (cookie == null)
			return;
		String rememberMeCode = cookie.getValue();
		ExtranetUser user = userDao.findBy("rememberMeCode", rememberMeCode);
		if (user == null)
			return;
		userContext.loginUser(user);
	}

}
