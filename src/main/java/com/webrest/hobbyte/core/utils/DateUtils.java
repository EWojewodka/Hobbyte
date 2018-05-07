/**
 * 
 */
package com.webrest.hobbyte.core.utils;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Date;

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
	
	public static String formatDate(Date date, String format) {
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		return sdf.format(date);
	}
	
}
