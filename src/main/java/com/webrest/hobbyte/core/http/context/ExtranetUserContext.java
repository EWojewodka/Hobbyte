package com.webrest.hobbyte.core.http.context;

import org.slf4j.Logger;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import com.webrest.hobbyte.app.user.ExtranetUserUtils;
import com.webrest.hobbyte.app.user.model.ExtranetUser;
import com.webrest.hobbyte.core.http.context.HttpContext;
import com.webrest.hobbyte.core.logger.LoggerFactory;

@Service("extranetUser")
@Primary
public class ExtranetUserContext extends HttpContext implements IExtranetUserContext {

	private static final Logger LOGGER = LoggerFactory.getLogger();
	
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
		if(user == null) LOGGER.info("Chief! Someone try to sign in null user!");
		getSession(true).setAttribute(ExtranetUserUtils.USER_SESSION_NAME, user);
	}

}
