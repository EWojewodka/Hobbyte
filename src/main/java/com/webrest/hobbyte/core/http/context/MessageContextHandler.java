package com.webrest.hobbyte.core.http.context;

import java.util.ArrayList;
import java.util.List;

public class MessageContextHandler {

	private HttpContext context;

	private List<String> errorMessages;

	private List<String> successMessages;

	public MessageContextHandler(HttpContext context) {
		this.context = context;
	}

	public List<String> popErrors() {
		List<String> _errors = getErrors();
		List<String> errors = new ArrayList<>();
		_errors.forEach(e -> {
			errors.add(e);
		});
		errorMessages.clear();
		return errors;
	}
	
	public List<String> popSuccess() {
		List<String> _success = getSuccess();
		List<String> success = new ArrayList<>();
		_success.forEach(e -> {
			success.add(e);
		});
		successMessages.clear();
		return success;
	}
	
	public List<String> getErrors() {
		return errorMessages == null ? (errorMessages = new ArrayList<>()) : errorMessages;
	}

	public List<String> getSuccess() {
		return successMessages == null ? (successMessages = new ArrayList<>()) : successMessages;
	}

	public boolean hasErrors() {
		return getErrors().size() > 0;
	}

	public boolean hasSuccess() {
		return getSuccess().size() > 0;
	}

	public void addError(String message) {
		getErrors().add(message);
	}

	public void addSuccess(String message) {
		getSuccess().add(message);
	}

}
