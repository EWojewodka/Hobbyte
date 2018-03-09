package com.webrest.hobbyte.core.security.validator;

import java.util.ArrayList;
import java.util.List;

import org.springframework.validation.BindingResult;

public abstract class FormValidator {

	private List<IValidRule> rules = new ArrayList<>();

	public void addValidRule(IValidRule rule) {
		rules.add(rule);
	}

	protected abstract void initValidRules();

	public boolean isValid(final BindingResult binding) {
		initValidRules();
		boolean result = true;
		for (IValidRule rule : rules) {
			if (!rule.valid()) {
				binding.addError(rule.getError());
				result = false;
			}
		}
		return result;
	}
	
	
}
