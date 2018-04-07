package com.webrest.hobbyte.core.exception;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.webrest.hobbyte.core.exception.prepare.ExceptionModelFactory;
import com.webrest.hobbyte.core.exception.prepare.IExceptionModel;
import com.webrest.hobbyte.core.exception.response.GenericResponseErrorException;

/**
 * Excpetion handler for custom behavior of {@link Exception} </br>
 * E.g. if application thrown {@link RedirectException} there will be a redirect
 * or if there were thrown one of {@link GenericResponseErrorException} library
 * will try find best template to show them and wrap exception data in model for
 * show them into template content.
 * 
 * 
 * @author Emil Wojewódka
 *
 * @since 10 mar 2018
 */
@ControllerAdvice
public class ExceptionController {

	@Autowired
	private ExceptionModelFactory exceptionModelFactory;

	/**
	 * Handle redirect invoked by thrown {@link RedirectException} instance.
	 * 
	 * @param e
	 * @throws IOException
	 */
	@ExceptionHandler(value = RedirectException.class)
	@ResponseStatus(code = HttpStatus.PERMANENT_REDIRECT)
	public void redirectException(RedirectException e) throws IOException {
		e.getContext().getResponse().sendRedirect(e.getUrl());
	}

	/**
	 * Basic exception handler for every type of {@link Exception} </br>
	 * Passed exception charge an {@link IExceptionModel} implementation and show
	 * error page which is defined into {@link IExceptionModel#getTemplate()} </br>
	 * FIXME: Always {@link HttpStatus} is 200 - we need to change it!
	 * 
	 * @param e
	 * @param model
	 * @return
	 */
	@ExceptionHandler(value = Exception.class)
	public String runtimeException(Exception e, Model model) {
		printException(e);
		IExceptionModel service = exceptionModelFactory.getModel(e);
		service.addToModel(model);
		return service.getTemplate();
	}

	private void printException(Exception e) {
		e.printStackTrace();
	}
}
