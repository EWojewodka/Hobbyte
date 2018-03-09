package com.webrest.hobbyte.core.security.validator;

import org.springframework.validation.ObjectError;

/**
 * This abstract class should be implemented and using into
 * {@link FormValidator} as a rule.
 * 
 * @author wojew
 *
 */
public abstract class ValidRule implements IValidRule {

	private ObjectError error;

	public ValidRule(String fieldName, String[] errorCodes, Object[] variables) {
		this(fieldName, errorCodes, variables, "");
	}

	public ValidRule(String fieldName, String[] errorCodes, Object[] variables, String defaultMessage) {
		this(ObjectErrorUtils.createNew(fieldName, errorCodes, variables, defaultMessage));
	}
	
	public ValidRule(ObjectError error) {
		this.error = error;
	}

	@Override
	public ObjectError getError() {
		return error;
	}

}
