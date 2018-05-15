package com.webrest.hobbyte.core.utils.functions;

import org.junit.Assert;
import org.junit.Test;

public class ExceptionStreamTest {

	private static final String TEST_STRING = "TEST-STRING";
	
	@Test
	public void returnValueOnException() {
		String result = ExceptionStream.handle(e -> {
			return TEST_STRING;
		}).call(() -> {
			throw new Exception();	
		}).get();
		Assert.assertEquals(TEST_STRING, result);
	}
	
	@Test
	public void returnValueWithoutException() {
		String result = (String) ExceptionStream.handle(e -> {
			return null;
		}).call(() -> {
			return TEST_STRING;
		}).get();
		
		Assert.assertEquals(TEST_STRING, result);
	}

	@Test
	public void getOrDefaultTest() {
		String result = (String) ExceptionStream.handle(e -> {
		}).call(() -> {
			return null;
		}).getOrDefault(TEST_STRING);
		
		Assert.assertEquals(TEST_STRING, result);
	}
	
}
