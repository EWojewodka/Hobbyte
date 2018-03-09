package com.webrest.hobbyte.core.security.validator;

import org.springframework.validation.ObjectError;

public class ObjectErrorUtils {

	public static ObjectError createNew(String fieldName, String[] errorCodes, Object[] variables, String defaultMessage) {
		return new ObjectError(fieldName, errorCodes, variables, defaultMessage);
	}

	public static ObjectError createNew(String fieldName, String[] errorCodes, Object[] variable) {
		return createNew(fieldName, errorCodes, variable, "");
	}

}
