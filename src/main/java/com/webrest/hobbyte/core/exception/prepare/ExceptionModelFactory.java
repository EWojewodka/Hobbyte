/**
 * 
 */
package com.webrest.hobbyte.core.exception.prepare;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import com.webrest.hobbyte.core.exception.ResponseErrorException;
import com.webrest.hobbyte.core.exception.response.GenericResponseErrorException;

/**
 * @author Emil Wojewódka
 *
 * @since 9 mar 2018
 */
@Service
public class ExceptionModelFactory {

	@Autowired
	private Environment env;

	/**
	 * Return one of an {@link IExceptionModel} implementation which is best for
	 * passed exception.
	 * 
	 * @param e
	 * @return
	 */
	public IExceptionModel getModel(Throwable e) {
		if (e instanceof ResponseErrorException)
			return new GenericResponseErrorExceptionModel((GenericResponseErrorException) e, env);
		else
			return new DefaultExceptionModel(e, env);

	}
}
