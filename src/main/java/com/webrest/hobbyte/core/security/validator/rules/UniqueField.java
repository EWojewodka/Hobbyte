package com.webrest.hobbyte.core.security.validator.rules;

import javax.persistence.Query;

import com.webrest.hobbyte.core.security.validator.ObjectErrorUtils;

public class UniqueField extends DBRule {

	public UniqueField(String fieldName, Query query) {
		super(ObjectErrorUtils.createNew(fieldName, new String[] { "NotUnique" }, new Object[] { fieldName }), query);
	}

	@Override
	public boolean valid() {
		return getQuery().getResultList().isEmpty();
	}

}
