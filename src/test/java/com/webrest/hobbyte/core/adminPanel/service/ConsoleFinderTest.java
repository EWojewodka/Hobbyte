package com.webrest.hobbyte.core.adminPanel.service;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.webrest.hobbyte.core.console.IConsole;
import com.webrest.hobbyte.core.console.list.ListConsole;

public class ConsoleFinderTest {

	private static final String TEST_CONSOLE_ID = "app-param-list"; 
	
	private IConsole console;
	
	@Before
	public void initialize() {
		console = ConsoleFinder.getById(TEST_CONSOLE_ID);
	}
	
	@Test
	public void getByIdTest() {
		Assert.assertNotNull(console);
	}

	@Test
	public void isCorrectConsole() {
		Assert.assertEquals(ListConsole.class, console.getClass());
	}
	
	
}
