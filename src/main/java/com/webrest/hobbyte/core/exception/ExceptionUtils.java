/**
 * 
 */
package com.webrest.hobbyte.core.exception;

import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * @author Emil Wojewódka
 *
 * @since 9 mar 2018
 */
public class ExceptionUtils {

	/**
	 * Return stacktrace as a {@link String}
	 * 
	 * @param e
	 * @return
	 */
	public static String catchStackTrace(Exception e) {
		StringWriter writer = new StringWriter();
		PrintWriter printWriter = new PrintWriter(writer);
		e.printStackTrace(printWriter);
		printWriter.flush();
		return writer.toString();
	}

}
