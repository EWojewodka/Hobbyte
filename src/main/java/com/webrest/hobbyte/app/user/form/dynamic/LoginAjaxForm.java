package com.webrest.hobbyte.app.user.form.dynamic;

import javax.servlet.http.Cookie;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.webrest.hobbyte.app.user.ExtranetUserUtils;
import com.webrest.hobbyte.app.user.dao.ExtranetUserDao;
import com.webrest.hobbyte.app.user.model.ExtranetUser;
import com.webrest.hobbyte.app.user.model.enums.ExtranetUserStatus;
import com.webrest.hobbyte.core.dynamicForm.GenericAjaxDynamicForm;
import com.webrest.hobbyte.core.dynamicForm.SimpleMessage;
import com.webrest.hobbyte.core.http.context.IExtranetUserContext;
import com.webrest.hobbyte.core.http.context.IHttpContext;
import com.webrest.hobbyte.core.i18n.MessageSourceHelper;
import com.webrest.hobbyte.core.utils.AjaxAsserts;
import com.webrest.hobbyte.core.utils.HttpUtils;
import com.webrest.hobbyte.core.utils.StringUtils;

@Service
@Scope(value = "request", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class LoginAjaxForm extends GenericAjaxDynamicForm<SimpleMessage> {

	@Autowired
	private ExtranetUserDao userDao;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private MessageSourceHelper msgHelper;

	@Override
	protected SimpleMessage process(IExtranetUserContext context) throws Exception {
		SimpleMessage msg = new SimpleMessage();
		if (ExtranetUserUtils.isLogged(context)) {
			msg.setRedirect("/");
			return msg;
		}
		ExtranetUser user = userDao.findByLoginOrEmail(getParameter("login"));

		AjaxAsserts.notNull(user, msgHelper.getMessage("IncorrectLogin", context));

		boolean isPasswordMatches = passwordEncoder.matches(getParameter("password"), user.getPassword());
		AjaxAsserts.assertTrue(isPasswordMatches, msgHelper.getMessage("IncorrectLogin", context));

		AjaxAsserts.assertTrue(user.getStatus() == ExtranetUserStatus.ACTIVE,
				msgHelper.getMessage("UserNotActive", context));

		handleRememberMe(context, user, userDao);
		context.loginUser(user);
		msg.setRedirect("/");
		return msg;
	}

	public static void handleRememberMe(IHttpContext context, ExtranetUser user, ExtranetUserDao userDao) {
		HttpUtils.removeCookieIfExists(ExtranetUserUtils.REMEMBER_ME_COOKIE_NAME, context);

		String code = StringUtils.generateRandom(250);
		user.setRememberMeCode(code);
		userDao.save(user);

		Cookie cookie = new Cookie(ExtranetUserUtils.REMEMBER_ME_COOKIE_NAME, code);
		HttpUtils.setMaxAgeCookie(cookie);
		cookie.setPath("/");
		context.getResponse().addCookie(cookie);
	}

	@Override
	public String getCode() {
		return "sign-up";
	}

	@Override
	protected SimpleMessage handleException(Exception e) {
		return new SimpleMessage().addError(e.getMessage());
	}

}