package com.webrest.hobbyte.core.http.controllers;

import com.webrest.hobbyte.core.http.context.IHttpContext;

public class BaseController {

	private IHttpContext context;

	protected BaseController() {
		// for higher class
	}

	public BaseController(IHttpContext context) {
		this.context = context;
	}

	protected IHttpContext getContext() {
		return context;
	}

}
