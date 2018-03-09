package com.webrest.hobbyte.core.exception;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com.webrest.hobbyte.core.http.context.IHttpContext;
import com.webrest.hobbyte.core.logger.LoggerFactory;

@ControllerAdvice
public class ExceptionController{

	private static final Logger LOGGER = LoggerFactory.getLogger();

	@Autowired
	@Qualifier("http")
	private IHttpContext context;

	@ExceptionHandler(value = RedirectException.class)
	public ModelAndView  redirectException(RedirectException e) {
		RedirectView redirectView = new RedirectView(e.getUrl());
		return new ModelAndView(redirectView , "message", "invalid token/member not found");
	}

}
