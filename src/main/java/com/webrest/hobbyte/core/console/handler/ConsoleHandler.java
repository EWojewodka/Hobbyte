/**
 * 
 */
package com.webrest.hobbyte.core.console.handler;

import javax.servlet.http.HttpServletRequest;

import org.springframework.ui.Model;

import com.webrest.hobbyte.core.console.IConsole;
import com.webrest.hobbyte.core.http.context.ExtranetUserContext;
import com.webrest.hobbyte.core.utils.Asserts;

/**
 * @author Emil Wojewódka
 *
 * @since 5 kwi 2018
 */
public class ConsoleHandler implements Handler {

	private IConsole console;

	public ConsoleHandler(IConsole console) {
		Asserts.notNull(console, "Cannot init ConsoleHandler for nullable console");
		this.console = console;
	}

	@Override
	public void handle(ExtranetUserContext context, Model model) {
		HttpServletRequest request = context.getRequest();
		String action = request.getParameter("action");
	}

}
