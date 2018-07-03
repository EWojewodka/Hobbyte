/**
 * 
 */
package com.webrest.hobbyte.core.utils;

import java.io.File;

import org.springframework.util.Assert;
import org.w3c.dom.NamedNodeMap;

import com.webrest.hobbyte.core.model.DatabaseObjectImpl;

/**
 * Class inspired by {@link Assert}
 * 
 * @author Emil Wojewódka
 *
 * @since 24 mar 2018
 */
public class Asserts extends Assert {

	public static void exists(File file) {
		exists(file, "[File not found] - file " + file + " is not found.");
	}

	public static void exists(File file, String message) {
		notNull(file, "[File not null] - file cannot be null");
		if (!file.exists())
			throw new IllegalArgumentException(message);
	}

	public static void notEmpty(NamedNodeMap nodeMap, String message) {
		notNull(nodeMap, message);
		if (nodeMap.getLength() == 0)
			throw new IllegalArgumentException(message);
	}

	public static void notEmpty(String string, String message) {
		if (StringUtils.isEmpty(string))
			throw new IllegalArgumentException(message);
	}

	/**
	 * Throw {@link IllegalArgumentException} if dbo parameter is null or his id is
	 * zero.
	 * 
	 * @param dbo
	 * @param message
	 */
	public static void exists(DatabaseObjectImpl dbo, String message) {
		Asserts.notNull(dbo, message);
		if (dbo.getId() == 0)
			throw new IllegalArgumentException(message);
	}

	public static void exists(DatabaseObjectImpl dbo) {
		exists(dbo, String.format("Cannot create object for not exists %s.", dbo.getClass().getName()));
	}

	public static void isFalse(boolean expression, String message) {
		isTrue(!expression, message);
	}

}
