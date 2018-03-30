/**
 * 
 */
package com.webrest.hobbyte.core.utils;

import java.io.File;

import org.springframework.util.Assert;

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
}
