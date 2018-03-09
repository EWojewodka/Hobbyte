package com.webrest.hobbyte.core.logger;

/**
 * Basic implementation of {@link IActionLogger} </br>
 * 
 * @author wojew
 *
 */
public class ActionLogger implements IActionLogger {

	private long startTime;

	private String message;

	private long time;

	public ActionLogger(String finishMessage) {
		this.message = finishMessage;
	}

	@Override
	public void stop() {
		time = System.currentTimeMillis() - startTime;
		LoggerFactory.getLogger(3).info("{} time - {} ms!", message, time);
	}

	@Override
	public void start() {
		this.startTime = System.currentTimeMillis();
	}

	public long getTime() {
		return time;
	}

}
