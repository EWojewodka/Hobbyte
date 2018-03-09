package com.webrest.hobbyte.core.security.validator;

import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;

/**
 * Basic interface for usable ObjectError with {@link FormValidator}
 * 
 * @author wojew
 *
 */
public interface IValidRule {

	/**
	 * Valid values. Return <code>true</code> if value is correct.
	 * 
	 * @return
	 */
	boolean valid();

	/**
	 * Return {@link ObjectError} which could be added to
	 * {@link BindingResult#addError(ObjectError)}
	 * 
	 * @return
	 */
	ObjectError getError();

}
