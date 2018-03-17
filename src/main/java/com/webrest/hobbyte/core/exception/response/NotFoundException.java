/**
 * 
 */
package com.webrest.hobbyte.core.exception.response;

import javax.servlet.http.HttpServletResponse;

import com.webrest.hobbyte.core.http.context.IHttpContext;

/**
 * @author Emil Wojewódka
 *
 * @since 9 mar 2018
 */
public class NotFoundException extends GenericResponseErrorException {

	private static final long serialVersionUID = 1583980815625480314L;

	/**
	 * @param context
	 * @param errorCode
	 */
	public NotFoundException(IHttpContext context) {
		super(context, HttpServletResponse.SC_NOT_FOUND);
	}
	
	@Override
	public String getTitle() {
		return "404 - Not Found";
	}

}
