package com.webrest.hobbyte.app.user.form;

import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

import com.webrest.hobbyte.core.security.validator.FormValidator;
import com.webrest.hobbyte.core.security.validator.rules.EqualsRule;

public class RegistrationForm extends FormValidator {

	@Size(min = 6)
	private String login;

	@Email
	@NotEmpty
	private String email;

	@Size(min=8)
	private String password;

	private String passwordRepeat;

	@AssertTrue
	private boolean agreement;
	
	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPasswordRepeat() {
		return passwordRepeat;
	}

	public void setPasswordRepeat(String passwordRepeat) {
		this.passwordRepeat = passwordRepeat;
	}

	public boolean isAgreement() {
		return agreement;
	}

	public void setAgreement(boolean agreement) {
		this.agreement = agreement;
	}

	@Override
	protected void initValidRules() {
		addValidRule(new EqualsRule("password", "password confirmation", password, passwordRepeat));
	}
	
}
