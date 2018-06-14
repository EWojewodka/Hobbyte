package com.webrest.hobbyte.core.dynamicForm;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;

import com.webrest.hobbyte.core.exception.AjaxMessageException;
import com.webrest.hobbyte.core.http.context.IExtranetUserContext;

/**
 * {@link GenericAjaxDynamicForm} is base of more specific ajax forms, which
 * returns some values after action.
 * 
 * @author EWojewodka
 *
 * @param <T>
 */
public abstract class GenericAjaxDynamicForm<T> {

	@Autowired(required = true)
	private IExtranetUserContext context;

	@Transactional
	public T run() throws Exception {
		try {
			return process(getContext());
		} catch (Exception e) {
			if (e instanceof AjaxMessageException)
				throw e;
			else
				return handleException(e);
		}
		// return ExceptionStream.handle(e -> {
		// return handleException(e);
		// }).call(() -> {
		// return process(getContext());
		// }).get();
	}

	/**
	 * Method with form logic.
	 * 
	 * @return
	 */
	protected abstract T process(IExtranetUserContext context) throws Exception;

	protected abstract T handleException(Exception e);

	public IExtranetUserContext getContext() {
		return context;
	}

	public String getParameter(String name) {
		return context.getRequest().getParameter(name);
	}

	public String getCode() {
		return this.getClass().getSimpleName();
	}

}
