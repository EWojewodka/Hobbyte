/**
 * 
 */
package com.webrest.hobbyte.core.exception;

import com.webrest.hobbyte.core.exception.response.GenericResponseErrorException;
import com.webrest.hobbyte.core.http.context.IHttpContext;

/**
 * Exception prototype which is handled by {@link ExceptionController} for show
 * error page. </br>
 * 
 * @see GenericResponseErrorException
 * @author Emil Wojewódka
 * @soundtrack Paluch - Bez strachu
 *             (https://www.youtube.com/watch?v=4pu-lKgm-dg)
 * @since 9 mar 2018
 */
public abstract class ResponseErrorException extends Exception {

	private static final long serialVersionUID = -3653985554579728330L;

	private IHttpContext context;

	public ResponseErrorException(IHttpContext context) {
		this(context, "");
	}

	public ResponseErrorException(IHttpContext context, String message) {
		super(message);
		if (context == null)
			throw new IllegalArgumentException("Context cannot be null!");
		this.context = context;
	}

	public IHttpContext getContext() {
		return context;
	}

	/**
	 * Return error code.
	 * 
	 * @return
	 */
	public abstract int getErrorCode();

	public abstract String getTitle();
	
}
