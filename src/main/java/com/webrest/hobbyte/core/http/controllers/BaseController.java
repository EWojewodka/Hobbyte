package com.webrest.hobbyte.core.http.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.webrest.hobbyte.core.http.context.IHttpContext;

@Controller
public abstract class BaseController {

	@Autowired
	private IHttpContext context;
	
	protected BaseController() {
		// for higher class
	}

	protected IHttpContext getContext() {
		return context;
	}

}
