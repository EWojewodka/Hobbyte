package com.webrest.hobbyte.app.user.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.webrest.hobbyte.app.user.ExtranetUserStatus;
import com.webrest.hobbyte.app.user.ExtranetUserUtils;
import com.webrest.hobbyte.app.user.dao.ExtranetUserDAO;
import com.webrest.hobbyte.app.user.form.LoginForm;
import com.webrest.hobbyte.app.user.model.ExtranetUser;
import com.webrest.hobbyte.core.http.context.IExtranetUserContext;
import com.webrest.hobbyte.core.security.validator.FormValidator;
import com.webrest.hobbyte.core.security.validator.rules.AssertTrueRule;
import com.webrest.hobbyte.core.security.validator.rules.NotNullRule;

@Service
@Scope(value = "request", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class UserLoginService extends FormValidator {

	@Autowired
	private ExtranetUserDAO userDAO;

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
