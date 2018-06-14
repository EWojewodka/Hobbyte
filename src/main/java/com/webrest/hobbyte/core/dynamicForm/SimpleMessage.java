package com.webrest.hobbyte.core.dynamicForm;

import java.util.ArrayList;
import java.util.List;

public class SimpleMessage {

	public List<String> messages = new ArrayList<>();

	public List<String> errors = new ArrayList<>();

	public String redirect = "";
	
	private int httpStatus;

	public SimpleMessage() {
	}

	public SimpleMessage addMessage(String content) {
		messages.add(content);
		return this;
	}

	public SimpleMessage addError(String error) {
		errors.add(error);
		return this;
	}

	public SimpleMessage setRedirect(String redirect) {
		this.redirect = redirect;
		return this;
	}
	
	public void setHttpStatus(int httpStatus) {
		this.httpStatus = httpStatus;
	}
	
	public int getHttpStatus() {
		return httpStatus;
	}

}
