package com.webrest.hobbyte.core.exception;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.webrest.hobbyte.core.dynamicForm.SimpleMessage;
import com.webrest.hobbyte.core.exception.prepare.ExceptionModelFactory;
import com.webrest.hobbyte.core.exception.prepare.IExceptionModel;
import com.webrest.hobbyte.core.exception.response.GenericResponseErrorException;
import com.webrest.hobbyte.core.http.context.ExtranetUserContext;
import com.webrest.hobbyte.core.utils.functions.ExceptionStream;

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

	@Autowired
	private ExceptionLogDao excepionDao;

	@Autowired
	private ExtranetUserContext context;

	/**
	 * Handle redirect invoked by thrown {@link RedirectException} instance.
	 * 
	 * @param e
	 * @throws IOException
	 * @throws ServletException
	 */
	@ExceptionHandler(value = RedirectException.class)
	@ResponseStatus(code = HttpStatus.PERMANENT_REDIRECT)
	public void redirectException(RedirectException e) throws IOException, ServletException {
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
	@ExceptionHandler(value = { Exception.class, Throwable.class })
	public String runtimeException(Throwable e, Model model) {
		printException(e);
		IExceptionModel service = exceptionModelFactory.getModel(e);
		service.addToModel(model);
		ExceptionLog exceptionLog = new ExceptionLog(e, context.getUser());
		excepionDao.save(exceptionLog);
		return service.getTemplate();
	}

	@ExceptionHandler(value = AjaxMessageException.class)
	@ResponseBody
	public SimpleMessage ajaxMessageException(AjaxMessageException e, HttpServletResponse resp) {
		SimpleMessage simpleMessage = new SimpleMessage();
		simpleMessage.addError(e.getMessage());
		ExceptionStream.printOnFailure().call(() -> resp.sendError(e.getErrorCode()));
		return simpleMessage;
	}

	private void printException(Throwable e) {
		e.printStackTrace();
	}
}
