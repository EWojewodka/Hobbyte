/**
 * 
 */
package com.webrest.hobbyte.core.exception;

/**
 * @author Emil Wojewódka
 *
 * @since 25 mar 2018
 */
public class SilentRuntimeException extends RuntimeException {

	private static final long serialVersionUID = 2775942627885956228L;

	/** {@inheritDoc}} */
	public SilentRuntimeException() {
		super();
	}

	/** {@inheritDoc}} */
	public SilentRuntimeException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	/** {@inheritDoc}} */
	public SilentRuntimeException(String message, Throwable cause) {
		super(message, cause);
	}

	/** {@inheritDoc}} */
	public SilentRuntimeException(String message) {
		super(message);
	}

	/** {@inheritDoc}} */
	public SilentRuntimeException(Throwable cause) {
		super(cause);
	}

}
