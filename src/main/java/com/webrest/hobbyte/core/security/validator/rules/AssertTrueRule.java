package com.webrest.hobbyte.core.security.validator.rules;

import com.webrest.hobbyte.core.security.validator.ValidRule;

public class AssertTrueRule extends ValidRule {

	private boolean result;

	public AssertTrueRule(String fieldName, String errorCodes, boolean result) {
		super(fieldName, new String[] {errorCodes}, new Object[0]);
		this.result = result;
	}

	@Override
	public boolean valid() {
		return result;
	}

}
