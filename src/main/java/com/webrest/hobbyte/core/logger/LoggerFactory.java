package com.webrest.hobbyte.core.logger;

import org.slf4j.ILoggerFactory;
import org.slf4j.Logger;


/**
 * Factory for getting loggers and other linked objects, e.g.
 * {@link IActionLogger}
 * 
 * @author wojew
 *
 */
public class LoggerFactory {

	private static final ILoggerFactory factory = org.slf4j.LoggerFactory.getILoggerFactory();

	/**
	 * Return standard logger.
	 * 
	 * @param clazz
	 * @return
	 */
	public static Logger getLogger(Class<?> clazz) {
		return factory.getLogger(clazz.getName());
	}

	/**
	 * Simpler version of {@link #getLogger(Class)} </br>
	 * Returned logger is linked with invoking class.
	 * 
	 * @return
	 */
	public static Logger getLogger() {
		return getLogger(3);
	}

	/**
	 * Get logger with name of backTo class.
	 * 
	 * @param backTo
	 *            - define how many steps back class of current thread stack trace
	 *            should invoke logger.
	 * @return
	 */
	public static Logger getLogger(int backTo) {
		StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
		return org.slf4j.LoggerFactory.getLogger(stackTrace[backTo].getClassName());
	}

	/**
	 * Return default implementation of {@link IActionLogger}.
	 * 
	 * @param message
	 * @return
	 */
	public static IActionLogger getActionLogger(String message) {
		return new ActionLogger(message);
	}

	/**
	 * Return {@link #getActionLogger(String)} without custom message.
	 * 
	 * @return
	 */
	public static IActionLogger getActionLogger() {
		return getActionLogger("");
	}

}
