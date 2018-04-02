package com.webrest.hobbyte.app.user.form;

import org.hibernate.validator.constraints.NotEmpty;

public class LoginForm {

	@NotEmpty
	private String login;

	@NotEmpty
	private String password;
	
	private boolean remember;

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public boolean isRemember() {
		return remember;
	}

	public void setRemember(boolean remember) {
		this.remember = remember;
	}

}
