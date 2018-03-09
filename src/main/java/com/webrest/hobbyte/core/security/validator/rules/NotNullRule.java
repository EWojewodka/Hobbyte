package com.webrest.hobbyte.core.security.validator.rules;

import com.webrest.hobbyte.core.security.validator.ValidRule;

public class NotNullRule extends ValidRule {

	private Object result;

	public NotNullRule(String fieldName, Object result) {
		this(fieldName, "NotNullRule", result);
	}

	public NotNullRule(String fieldName, String errorCode, Object result) {
		this(fieldName, errorCode, "", result);
	}

	public NotNullRule(String fieldName, String errorCode, String defaultMessage, Object result) {
		super(fieldName, new String[] { errorCode }, new Object[] { fieldName }, defaultMessage);
		this.result = result;
	}

	@Override
	public boolean valid() {
		return result != null;
	}

}
