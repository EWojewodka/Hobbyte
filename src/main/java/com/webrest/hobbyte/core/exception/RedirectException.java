package com.webrest.hobbyte.core.exception;

public class RedirectException extends NoStackTraceException{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1904342897285527741L;

	private String url;


	public RedirectException(String url) {
		this(url, null);
	}

	public RedirectException(String url, String message) {
		super(message);
		this.url = url;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	
}
