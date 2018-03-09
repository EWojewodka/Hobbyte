package com.webrest.hobbyte.core.http.context;

import org.springframework.stereotype.Service;

import com.webrest.hobbyte.app.user.ExtranetUserUtils;
import com.webrest.hobbyte.app.user.model.ExtranetUser;
import com.webrest.hobbyte.core.http.context.HttpContext;

@Service("extranetUser")
public class ExtranetUserContext extends HttpContext implements IExtranetUserContext {

	@Override
	public boolean isUserLogged() {
		return ExtranetUserUtils.isLogged(getSession(false));
	}

	@Override
	public ExtranetUser getUser() {
		return (ExtranetUser) getSession(true).getAttribute(ExtranetUserUtils.USER_SESSION_NAME);
	}

	@Override
	public void loginUser(ExtranetUser user) {
		getSession(true).setAttribute(ExtranetUserUtils.USER_SESSION_NAME, user);
	}

}
