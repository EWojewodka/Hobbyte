package com.webrest.hobbyte.core.security.validator.rules;

import com.webrest.hobbyte.core.security.validator.ObjectErrorUtils;
import com.webrest.hobbyte.core.security.validator.ValidRule;

public class EqualsRule extends ValidRule {

	private String value;

	private String excepted;

	public EqualsRule(String fieldName, String equalsWithField, String value, String excepted) {
		super(ObjectErrorUtils.createNew(fieldName, new String[] { "NotEquals" },
				new Object[] { fieldName, equalsWithField }));
		this.value = value;
		this.excepted = excepted;
	}

	/**
	 * Return false if {@link #value} or {@link #excepted} is null but other side is
	 * not null and {@link #value} is not {@link #equals(Object)} with
	 * {@link #excepted}
	 */
	@Override
	public boolean valid() {
		if (value == null || excepted == null)
			return false;
		return value.equals(excepted);
	}

}
