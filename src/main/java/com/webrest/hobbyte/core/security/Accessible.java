package com.webrest.hobbyte.core.security;

/**
 * Interface for implementing access for specified service, controller, object,
 * etc.
 * 
 * @author wojew
 *
 */
public interface Accessible {

	/**
	 * Return true if object pass security exam.
	 * 
	 * @return
	 */
	boolean hasAccess() throws Exception;
}
