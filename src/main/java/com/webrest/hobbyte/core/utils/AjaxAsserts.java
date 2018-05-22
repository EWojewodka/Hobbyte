package com.webrest.hobbyte.core.utils;

import javax.servlet.http.HttpServletResponse;

import com.webrest.hobbyte.core.exception.AjaxMessageException;

public class AjaxAsserts {

	/**
	 * Throws AjaxMessageException with specified message and errorCode.
	 * 
	 * @param message
	 * @param errorCode
	 * @throws AjaxMessageException
	 */
	public static void throwException(String message, int errorCode) throws AjaxMessageException {
		throw new AjaxMessageException(message, errorCode);
	}

	/**
	 * Throws {@link AjaxMessageException} with HTTP code ->
	 * {@link HttpServletResponse#SC_BAD_REQUEST}
	 * 
	 * @param message
	 * @throws AjaxMessageException
	 */
	public static void throwBadRequest(String message) throws AjaxMessageException {
		throwException(message, HttpServletResponse.SC_BAD_REQUEST);
	}

	public static void assertTrue(boolean value, String message) throws AjaxMessageException {
		if (!value)
			throwBadRequest(message);
	}

	public static void assertFalse(boolean value, String message) throws AjaxMessageException {
		assertTrue(!value, message);
	}

	/**
	 * HTTP Code: {@link HttpServletResponse#SC_BAD_REQUEST}
	 * 
	 * @param obj
	 * @param message
	 * @throws AjaxMessageException
	 */
	public static void notNull(Object obj, String message) throws AjaxMessageException {
		if (obj == null)
			throwBadRequest(message);
	}

	public static void notEmpty(String str, String message) throws AjaxMessageException {
		if (StringUtils.isEmpty(str))
			throwBadRequest(message);
	}
	
	public static void assertNull(Object obj, String message) throws AjaxMessageException {
		if(obj != null)
			throwBadRequest(message);
	}

	/**
	 * throw exception if all of elements are null or empty.
	 * 
	 * @param message
	 * @param str
	 * @throws AjaxMessageException
	 */
	public static void notEmpty(String message, String... str) throws AjaxMessageException {
		if (StringUtils.onlyEmpty(str))
			throwBadRequest(message);
	}

	public static void longerThan(String str, int minLength, String message) throws AjaxMessageException {
		notEmpty(str, message);
		if (str.length() < minLength)
			throwBadRequest(message);
	}
	
	public static void greaterThan(double value, double than, String message) throws AjaxMessageException {
		if(than > value)
			throwBadRequest(message);
	}

}
