package com.webrest.hobbyte.core.http.context;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;

import com.webrest.hobbyte.app.user.ExtranetUserUtils;
import com.webrest.hobbyte.app.user.dao.ExtranetUserDao;
import com.webrest.hobbyte.app.user.model.ExtranetUser;
import com.webrest.hobbyte.core.utils.HttpUtils;

@Service("extranetUser")
@Primary
@Scope(value = "session", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class ExtranetUserContext extends HttpContext implements IExtranetUserContext {

	@Autowired
	private ExtranetUserDao userDao;

	@Override
	public boolean isUserLogged() {
		return ExtranetUserUtils.isLogged(getSession());
	}

	@Override
	public ExtranetUser getUser() {
		return ExtranetUserUtils.getUser(getSession());
	}

	@Override
	public void loginUser(ExtranetUser user) {
		if (user == null)
			throw new IllegalArgumentException("Chief! Someone try to sign in null user!");
		getSession(true).setAttribute(ExtranetUserUtils.USER_SESSION_NAME, user);
	}

	@Override
	public void logoutUser() {
		if (!isUserLogged())
			return;
		ExtranetUser user = getUser();
		user.setRememberMeCode(null);
		userDao.save(user);
		HttpUtils.removeCookieIfExists(ExtranetUserUtils.REMEMBER_ME_COOKIE_NAME, this);
		ExtranetUserUtils.logoutUser(getSession());
	}

}
