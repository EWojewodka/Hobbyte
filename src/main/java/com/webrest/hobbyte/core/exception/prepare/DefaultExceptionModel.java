/**
 * 
 */
package com.webrest.hobbyte.core.exception.prepare;

import org.springframework.core.env.Environment;
import org.springframework.ui.Model;

import com.webrest.hobbyte.core.exception.ExceptionUtils;
import com.webrest.hobbyte.core.platform.PlatformUtils;

/**
 * @author Emil Wojewódka
 *
 * @since 9 mar 2018
 */
public class DefaultExceptionModel implements IExceptionModel {

	private Environment env;

	private Exception ex;

	public DefaultExceptionModel(Exception ex, Environment env) {
		this.ex = ex;
		this.env = env;
	}

	@Override
	public void addToModel(Model model) {
		boolean isDevelopment = PlatformUtils.isDevelopment(env);
		model.addAttribute("title", isDevelopment ? ex.getClass().getSimpleName() : "Internal error");
		model.addAttribute("errorMessage",
				isDevelopment ? ExceptionUtils.catchStackTrace(ex) : "Something went wrong!");
	}

	protected Environment getEnvironment() {
		return env;
	}

	public Exception getException() {
		return ex;
	}

	/**
	 * Default error page - 500
	 * 
	 * @see com.webrest.hobbyte.core.exception.prepare.IExceptionModel#getTemplate()
	 */
	@Override
	public String getTemplate() {
		return "static/templates/error/500";
	}

}
