/**
 * 
 */
package com.webrest.hobbyte.core.exception.prepare;

import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

/**
 * @author Emil Wojewódka
 *
 * @since 9 mar 2018
 */
@Service
public interface IExceptionModel {

	public static final String TITLE_NAME = "title";

	public static final String MESSAGE_NAME = "errorMessage";

	/**
	 * Wrap exception properties to model. </br>
	 * Exception properties:
	 * <ul>
	 * <li>${{@value #TITLE_NAME}} -> title</li>
	 * <li>${{@value #MESSAGE_NAME}} -> stacktrace / message</li> We wouldn't show
	 * stacktrace on production.
	 * <ul>
	 * 
	 * @param e
	 * @param model
	 */
	void addToModel(Model model);

	String getTemplate();
	
}
