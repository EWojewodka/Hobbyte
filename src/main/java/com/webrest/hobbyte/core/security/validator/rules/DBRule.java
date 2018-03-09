package com.webrest.hobbyte.core.security.validator.rules;

import javax.persistence.Query;

import org.springframework.validation.ObjectError;

import com.webrest.hobbyte.core.security.validator.ValidRule;

public abstract class DBRule extends ValidRule {

	private Query query;

	public DBRule(ObjectError error, Query query) {
		super(error);
		this.query = query;
	}

	protected Query getQuery() {
		return query;
	}

}
