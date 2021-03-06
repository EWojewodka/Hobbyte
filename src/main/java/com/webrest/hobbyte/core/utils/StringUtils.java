/**
 * 
 */
package com.webrest.hobbyte.core.utils;

import java.lang.reflect.Array;
import java.util.Base64;
import java.util.Base64.Encoder;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;
import java.util.StringJoiner;
import java.util.regex.Pattern;

/**
 * @author Emil Wojewódka
 *
 * @since 15 mar 2018
 */
public class StringUtils {

	private static final String EMAIL_REGEX = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

	public static final String EMPTY_STRING = "";

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

	public static boolean isEmpty(String[] values) {
		return values == null || values.length == 0;
	}

	public static boolean isEmpty(String value) {
		return value == null || value.trim().length() == 0;
	}

	/**
	 * Return true if all of array elements are null or empty. If at least one of
	 * them is not a null or empty - return false.
	 * 
	 * @see #isEmpty(String)
	 * @param strs
	 * @return
	 */
	public static boolean onlyEmpty(String... strs) {
		if (strs == null)
			return true;
		for (String s : strs) {
			if (!StringUtils.isEmpty(s))
				return false;
		}
		return true;
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

	public static String toGenericString(Collection<?> collection, String splitter) {
		if (collection == null || collection.isEmpty())
			return "";
		return toGenericString(collection.toArray(new Object[collection.size()]), splitter);
	}

	/**
	 * Return string from concated {@link Collection} elements. Value of element is
	 * a {@link WithCode#getCode()}
	 * 
	 * @param profileList
	 * @param splitter
	 * @return
	 */
	public static String toGenericStringCodes(Collection<? extends WithCode> profileList, String splitter) {
		if (profileList == null)
			return "";
		if (splitter == null)
			splitter = ",";

		StringJoiner joiner = new StringJoiner(splitter);
		profileList.forEach(c -> joiner.add(c.getCode()));
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
			// Maybe there is too much chars and cannot be parsed to double. Let's check
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

	public static String getAsString(Object obj, String defaultValue) {
		if (obj == null)
			return defaultValue;
		return obj.toString();
	}

	/**
	 * Generate random string.
	 * 
	 * @return
	 */
	public static String generateRandom() {
		String unixCode = String.valueOf(new Date().getTime());
		Encoder encoder = Base64.getEncoder();
		return encoder.encodeToString(unixCode.getBytes()) + new Random().nextInt(99999);
	}

	/**
	 * Return random String generated by {@link #generateRandom()} and substring to
	 * {@code length} if is longer than this value.
	 * 
	 * @see #generateRandom()
	 * @param length
	 * @return
	 */
	public static String generateRandom(int length) {
		String s = generateRandom();
		return s.length() > length ? s.substring(10, 10 + length) : s;
	}

	/**
	 * Return index of a {@code null} value in array. If there is no {@code null}
	 * value return -1.
	 * 
	 * @param objs
	 * @return
	 */
	public static int getNullIndex(Object[] objs) {
		if (objs == null || objs.length == 0)
			return -1;

		int len = objs.length;
		for (int i = 0; i < len; i++) {
			if (objs[i] == null)
				return i;
		}
		return -1;
	}

	/**
	 * Return false if {@link Array} is null, has not length or if {@link Object} is
	 * null. </br>
	 * Note that if {@code obj} is null and you want to check {@code obj} contains a
	 * {@code null} value use {@link #getNullIndex(Object[])}
	 * 
	 * @param objs
	 * @param obj
	 * @return
	 */
	public static boolean contains(Object[] objs, Object obj) {
		if (objs == null || objs.length == 0 || obj == null)
			return false;
		for (Object _obj : objs) {
			if (_obj.equals(obj))
				return true;
		}
		return false;
	}

}
