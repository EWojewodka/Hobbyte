/**
 * 
 */
package com.webrest.hobbyte.core.exception;

/**
 * @author Emil Wojewódka
 *
 * @since 15 mar 2018
 */
public class AjaxMessageException extends NoStackTraceException {

	private static final long serialVersionUID = -6400024257215030726L;

	private int errorCode;

	public AjaxMessageException(String message, int errorCode) {
		super(message);
		this.errorCode = errorCode;
	}

	public int getErrorCode() {
		return errorCode;
	}

}
