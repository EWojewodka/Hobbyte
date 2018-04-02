package com.webrest.hobbyte.app.user.service;

import javax.servlet.http.Cookie;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.webrest.hobbyte.app.user.ExtranetUserUtils;
import com.webrest.hobbyte.app.user.dao.ExtranetUserDao;
import com.webrest.hobbyte.app.user.form.LoginForm;
import com.webrest.hobbyte.app.user.model.ExtranetUser;
import com.webrest.hobbyte.app.user.model.enums.ExtranetUserStatus;
import com.webrest.hobbyte.core.http.context.IExtranetUserContext;
import com.webrest.hobbyte.core.security.validator.FormValidator;
import com.webrest.hobbyte.core.security.validator.rules.AssertTrueRule;
import com.webrest.hobbyte.core.security.validator.rules.NotNullRule;
import com.webrest.hobbyte.core.utils.HttpUtils;
import com.webrest.hobbyte.core.utils.StringUtils;

@Service
@Scope(value = "request", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class UserLoginService extends FormValidator {

	@Autowired
	private IExtranetUserContext context;

	@Autowired
	private ExtranetUserDao userDAO;

	@Autowired
	private PasswordEncoder encoder;

	private LoginForm form;

	private ExtranetUser user;

	public void addForm(LoginForm form) {
		this.form = form;
		findUser();
	}

	private void findUser() {
		user = userDAO.findByLoginOrEmail(form.getLogin());
	}

	public void handleRememberMe() {
		if (!form.isRemember())
			return;
		
		HttpUtils.removeCookieIfExists(ExtranetUserUtils.REMEMBER_ME_COOKIE_NAME, context);
		
		String code = StringUtils.generateRandom(250);
		user.setRememberMeCode(code);
		userDAO.save(user);

		Cookie cookie = new Cookie(ExtranetUserUtils.REMEMBER_ME_COOKIE_NAME, code);
		HttpUtils.setMaxAgeCookie(cookie);
		context.getResponse().addCookie(cookie);
}

	public ExtranetUser getUser() {
		return user;
	}

	@Override
	protected void initValidRules() {
		addValidRule(new NotNullRule("", "IncorrectLogin", user));
		if (user == null)
			return;
		addValidRule(new AssertTrueRule("", "IncorrectLogin", encoder.matches(form.getPassword(), user.getPassword())));
		addValidRule(new AssertTrueRule("", "UserNotActive", user.getStatus() != ExtranetUserStatus.NOT_ACTIVE));
	}

}
