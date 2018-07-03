/**
 * 
 */
package com.webrest.hobbyte.core.utils;

import java.text.SimpleDateFormat;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.Locale;

import com.webrest.hobbyte.core.i18n.Languages;

/**
 * @author Emil Wojewódka
 *
 * @since 22 kwi 2018
 */
public class DateUtils {

	private static final String[] AVAILABLE_DATE_PATTERNS = new String[] { "yyyy-MM-dd", "yyyy-MM-dd HH:mm",
			"yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd HH:mm:ss.SSS" };

	public static Long getFutureDate(int minutes) {
		ZonedDateTime date = ZonedDateTime.now().plusMinutes(minutes);
		return date.toInstant().toEpochMilli();
	}

	public static String formatDate(Date date, String format) {
		return formatDate(date, format, Languages.US.getLocale());
	}

	public static String formatDate(Date date, String format, Locale locale) {
		SimpleDateFormat sdf = new SimpleDateFormat(format, locale);
		return sdf.format(date);
	}

	public static Date parseDate(String date, String format) {
		return parseDate(date, format, false);
	}

	public static Date parseDate(String date, String format, boolean silentException) {
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		try {
			return sdf.parse(date);
		} catch (Exception e) {
			if (!silentException)
				e.printStackTrace();
		}
		return null;
	}

	public static Date parseDate(String date) {
		Date resultDate = null;
		for (String datePattern : AVAILABLE_DATE_PATTERNS) {
			resultDate = parseDate(date, datePattern, true);
			if (resultDate != null)
				break;
		}
		System.out.println(resultDate);
		return resultDate;
	}

	public static String parseString(String strDateSource, String strDateSourceFormat, String strDateDestFormat) {
		Date parseDate = null;
		if (strDateSourceFormat.equalsIgnoreCase("unknown")) {
			parseDate = parseDate(strDateSource);
		} else {
			parseDate = parseDate(strDateSource, strDateSourceFormat);
		}

		if (parseDate == null)
			return null;

		return formatDate(parseDate, strDateDestFormat);
	}

}
