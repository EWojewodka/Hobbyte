package com.webrest.hobbyte.logger;

import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;

import com.webrest.hobbyte.core.logger.IActionLogger;
import com.webrest.hobbyte.core.logger.LoggerFactory;


public class LoggerFactoryTest {

	@Test
	public void loggerNameTest() {
		Logger logger = LoggerFactory.getLogger();
		Assert.assertEquals(this.getClass().getName(), logger.getName());
	}

	@Test
	public void actionLoggerTest() throws Exception {
		IActionLogger action = LoggerFactory.getActionLogger();
		action.start();
		Thread.sleep(10);
		action.stop();
		Assert.assertTrue(action.getTime() > 0);
	}

}
