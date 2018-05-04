package com.webrest.hobbyte.core.i18n;

import java.util.Locale;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import com.webrest.hobbyte.core.utils.HttpUtils;
import com.webrest.hobbyte.core.utils.StringUtils;

public enum Languages {
	/**
	 * Default language
	 */
	US(new Locale("us")),
	/**/
	POLISH(new Locale("pl"));

	private Locale locale;

	private Languages(Locale locale) {
		this.locale = locale;
	}

	public Locale getLocale() {
		return locale;
	}

	/**
	 * Return {@link #US} if there is no langague with specific code.
	 * 
	 * @param code
	 * @return
	 */
	public static Languages getByLangCode(String code) {
		if (StringUtils.isEmpty(code))
			return getDefault();

		Languages[] values = values();
		for (Languages l : values) {
			if (l.locale.getLanguage().equals(code))
				return l;
		}
		return getDefault();
	}

	public static Languages getFromRequest(HttpServletRequest request) {
		Cookie cookie = HttpUtils.getCookies("lang", request);
		if (cookie == null)
			return getDefault();
		return getByLangCode(cookie.getValue());
	}

	public static Languages getDefault() {
		return US;
	}
}
