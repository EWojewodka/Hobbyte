package com.webrest.hobbyte.core.http.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.webrest.hobbyte.core.http.context.ExtranetUserContext;
import com.webrest.hobbyte.core.http.context.HttpContext;
import com.webrest.hobbyte.core.utils.spring.DependencyResolver;

/**
 * Helper class for store a {@link HttpContext}.
 * 
 * @author Emil Wojewódka
 *
 * @since 2 kwi 2018
 */
@Controller
public abstract class BaseController {

	@Autowired
	private ExtranetUserContext context;

	@Autowired
	private DependencyResolver dependencyResolver;
	
	protected BaseController() {
		// for higher class
	}

	protected ExtranetUserContext getContext() {
		return context;
	}
	
	protected DependencyResolver getDependencyResolver() {
		return dependencyResolver;
	}

}
