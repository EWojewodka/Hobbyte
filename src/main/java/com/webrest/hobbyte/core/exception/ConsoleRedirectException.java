package com.webrest.hobbyte.core.exception;

import com.webrest.hobbyte.core.http.context.HttpContext;

public class ConsoleRedirectException extends RedirectException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 7383834179495779366L;

	public ConsoleRedirectException(HttpContext context, String consoleId) {
		super(context, "/sys/console?console-id="+consoleId);
	}

}
