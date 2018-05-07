package com.webrest.hobbyte.core.exception;

import com.webrest.hobbyte.core.http.context.HttpContext;
import com.webrest.hobbyte.core.http.context.IHttpContext;

public class RedirectException extends NoStackTraceException{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1904342897285527741L;

	private String url;

	private HttpContext context;

	public RedirectException(HttpContext context, String url) {
		this(context, url, null);
	}

	public RedirectException(HttpContext context, String url, String message) {
		super(message);
		this.url = url;
		this.context = context;
	}

	public String getUrl() {
		return url;
	}

	public IHttpContext getContext() {
		return context;
	}

	
}
