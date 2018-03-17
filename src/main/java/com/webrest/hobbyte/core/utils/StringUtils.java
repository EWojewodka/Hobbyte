/**
 * 
 */
package com.webrest.hobbyte.core.utils;

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

}
