package com.webrest.hobbyte.core.security.validator.rules;

public class NullRule extends NotNullRule {

	public NullRule(String fieldName, Object result) {
		this(fieldName, "NullRule", result);
	}
	
	public NullRule(String fieldName, String errorCode, Object result) {
		super(fieldName, errorCode, result);
	}

	public NullRule(String fieldName, String errorCode, String defaultMessage, Object result) {
		super(fieldName, errorCode, defaultMessage, result);
	}

	@Override
	public boolean valid() {
		return !super.valid();
	}

}
