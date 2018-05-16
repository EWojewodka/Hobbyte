/**
 * 
 */
package com.webrest.hobbyte.core.http.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.webrest.hobbyte.core.http.context.ExtranetUserContext;

/**
 * The most basic controller which is an advice. </br>
 * Currently (09-03-2018) it's only for putting context facade to view.
 * 
 * @author Emil Wojewódka
 * @whyAsterix - 'cause /* :v
 * @since 9 mar 2018
 */
@ControllerAdvice
public class AsterixController {

	@Autowired
	private ExtranetUserContext context;
	
	@ModelAttribute("context")
	private ExtranetUserContext getContext() {
		return context;
	}

}
