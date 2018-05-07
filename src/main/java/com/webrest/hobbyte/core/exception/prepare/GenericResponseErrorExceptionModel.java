/**
 * 
 */
package com.webrest.hobbyte.core.exception.prepare;

import org.springframework.core.env.Environment;
import org.springframework.ui.Model;

import com.webrest.hobbyte.core.exception.ResponseErrorException;
import com.webrest.hobbyte.core.exception.response.GenericResponseErrorException;
import com.webrest.hobbyte.core.utils.FileUtils;

/**
 * @author Emil Wojewódka
 *
 * @since 9 mar 2018
 */
public class GenericResponseErrorExceptionModel extends DefaultExceptionModel {

	/**
	 * Create new instance of {@link GenericResponseErrorExceptionModel} for fill
	 * model by data from {@link ResponseErrorException}
	 * 
	 * @param e
	 * @param env
	 */
	public GenericResponseErrorExceptionModel(GenericResponseErrorException e, Environment env) {
		super(e, env);
	}

	@Override
	public void addToModel(Model model) {
		GenericResponseErrorException throwable = getThrowable();
		model.addAttribute("title", throwable.getTitle());
		model.addAttribute("errorMessage", throwable.getMessage());
	}

	@Override
	public String getTemplate() {
		int errorCode = getThrowable().getErrorCode();
		return "error/" + (FileUtils.existsErrorPage(errorCode) ? errorCode : 500);
	}

	@Override
	public GenericResponseErrorException getThrowable() {
		return (GenericResponseErrorException) super.getThrowable();
	}

}
