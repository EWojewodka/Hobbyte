/**
 * 
 */
package com.webrest.hobbyte.core.exception.response;

import com.webrest.hobbyte.core.exception.ExceptionController;
import com.webrest.hobbyte.core.exception.ResponseErrorException;
import com.webrest.hobbyte.core.http.context.IHttpContext;

/**
 * Generic instance of ResponseException. You don't need (but you should!)
 * create new class for one of nonpopular response exception. </br>
 * You could just pass errorCode to constructor and out
 * {@link ExceptionController} will handle this.
 * 
 * @author Emil Wojewódka
 *
 * @since 9 mar 2018
 */
public class GenericResponseErrorException extends ResponseErrorException {

	private static final long serialVersionUID = 9180717969064144680L;

	private int errorCode;

	private String title;
	
	public GenericResponseErrorException(IHttpContext context, int errorCode) {
		this(context, "", errorCode);
	}

	public GenericResponseErrorException(IHttpContext context, String message, int errorCode) {
		this(context, message, "Error", errorCode);
	}

	public GenericResponseErrorException(IHttpContext context, String message, String title, int errorCode) {
		super(context, message);
		this.errorCode = errorCode;
		this.title = title;
	}
	
	@Override
	public int getErrorCode() {
		return errorCode;
	}

	@Override
	public String getTitle() {
		return title;
	}

}
