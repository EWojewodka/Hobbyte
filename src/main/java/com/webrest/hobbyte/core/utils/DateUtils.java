/**
 * 
 */
package com.webrest.hobbyte.core.utils;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

/**
 * @author Emil Wojewódka
 *
 * @since 22 kwi 2018
 */
public class DateUtils {
	
	public static Long getFutureDate(int minutes) {
		LocalDateTime now = LocalDateTime.now();
		LocalDateTime plusMinutes = now.plusMinutes(minutes);
		return plusMinutes.toEpochSecond(ZoneOffset.UTC);
	}
	
}
