package com.webrest.hobbyte.core.logger;

/**
 * {@link IActionLogger} is main action logger interface for checking efficient
 * of logical blocks.
 * 
 * @author wojew
 *
 */
public interface IActionLogger {

	void start();

	void stop();
	
	long getTime();

}
