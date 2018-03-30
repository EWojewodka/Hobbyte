/**
 * 
 */
package com.webrest.hobbyte.core.utils;

import java.util.Iterator;
import java.util.Map;
import java.util.StringJoiner;
import java.util.regex.Pattern;

/**
 * @author Emil Wojewódka
 *
 * @since 15 mar 2018
 */
public class StringUtils {

	private static final String EMAIL_REGEX = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

	/**
	 * Return <code>true</code> if value paramter matches with {@value #EMAIL_REGEX}
	 * Also - if value is null or empty - return false.
	 * 
	 * @param value
	 * @return
	 */
	public static boolean isEmail(String value) {
		if (isEmpty(value))
			return false;

		Pattern pattern = Pattern.compile(EMAIL_REGEX);
		return pattern.matcher(value).matches();
	}

	public static boolean isEmpty(String value) {
		return value == null || value.trim().length() == 0;
	}

	/**
	 * Return string with concat objects. </br>
	 * All of these objects are convert to {@link String} by
	 * {@link String#valueOf(Object)}. For example if passed objects is a
	 * {@link Class} type and second of these is a {@link StringUtils} type returned
	 * {@link String} will be like this: </br>
	 * <code>"class com.webrest.example.class.Type[splitter]StringUtils@123dsa456"</code>
	 * </br>
	 * Note: If there are no objects - return empty string. Also, if any of object
	 * is null, it will be "null" String. - for more information go to
	 * {@link String#valueOf(Object)}
	 * 
	 * @param objs
	 * @param splitter
	 * @return
	 */
	public static String toGenericString(Object[] objs, String splitter) {
		if (objs == null)
			return "";
		StringJoiner joiner = new StringJoiner(splitter);
		for (Object obj : objs)
			joiner.add(String.valueOf(obj)); // not use toString(), cause we don't check object is not null.
		return joiner.toString();
	}

	/**
	 * If double or float is as a String {@link #isNumeric(String)} return false.
	 * 
	 * @param value
	 * @return
	 */
	public static boolean isNumeric(String value) {
		if (isEmpty(value))
			return false;
		try {
			Double.parseDouble(value);
		} catch (Exception e) {
			// Maybe there is too much chars and cannot be parset to double. Let's check
			// every char.
			char[] chars = value.toCharArray();
			for (Character _char : chars) {
				if (Character.isAlphabetic(_char))
					return false;
			}
		}
		return true;
	}

	/**
	 * Replace all of #variable-name# by {@link Object#toString()} from value of
	 * <code>variables</code>. For example: </br>
	 * <code>Hello <b>#user.name#</b>! </code></br>
	 * will be replaced by variable with key "user.name" from passed map as
	 * parameter.
	 * 
	 * @param content
	 * @param variables
	 * @return
	 */
	public static String replaceVariable(String content, Map<String, ?> variables) {
		if (variables == null || variables.size() == 0)
			return content;

		Iterator<String> it = variables.keySet().iterator();
		while (it.hasNext()) {
			String key = it.next();
			content = content.replace("#" + key + "#", variables.get(key).toString());
		}
		return content;
	}

	public static boolean getAsBoolean(Object obj) {
		return getAsBoolean(obj, false);
	}

	public static boolean getAsBoolean(Object obj, boolean defaultValue) {
		if (obj == null)
			return defaultValue;
		
		String stringObj = obj.toString();
		return stringObj.equals("1") || stringObj.equals("true") || stringObj.equals("yes");
	}

	public static int getAsInt(Object obj) {
		return getAsInt(obj, 0);
	}
	
	public static int getAsInt(Object obj, int defaultValue) {
		if (obj == null)
			return defaultValue;
		if (!isNumeric(obj.toString()))
			return defaultValue;
		return Integer.valueOf(obj.toString());
	}

}