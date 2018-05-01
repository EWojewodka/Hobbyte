package com.webrest.hobbyte.app.user.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import com.webrest.hobbyte.app.user.dao.ExtranetUserDao;
import com.webrest.hobbyte.app.user.form.RegistrationForm;
import com.webrest.hobbyte.app.user.model.ExtranetUser;
import com.webrest.hobbyte.core.security.validator.FormValidator;
import com.webrest.hobbyte.core.security.validator.rules.NullRule;

@Service
@Scope(value = "request", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class UserRegistrationService extends FormValidator {

	@Autowired
	private ExtranetUserDao userDAO;

	@Autowired
	private PasswordEncoder encoder;

	private RegistrationForm form;

	@Transactional(rollbackOn = Exception.class)
	public ExtranetUser createFromForm() throws Exception {
		ExtranetUser user = new ExtranetUser();
		user.setLogin(form.getLogin());
		user.setEmail(form.getEmail());
		user.setPassword(encoder.encode(form.getPassword()));
		userDAO.save(user);
		return user;
	}
	
	public void addForm(RegistrationForm form) {
		this.form = form;
	}

	@Override
	public boolean isValid(BindingResult binding) {
		return super.isValid(binding);
	}

	@Override
	protected void initValidRules() {
		addValidRule(new NullRule("login", "NotAvailableLogin", userDAO.findByLogin(form.getLogin())));
		addValidRule(new NullRule("email", "NotAvailableEmail", userDAO.findByEmail(form.getEmail())));
	}

}
