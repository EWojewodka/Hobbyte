package com.webrest.hobbyte.core.i18n;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import com.webrest.hobbyte.core.http.context.IHttpContext;
import com.webrest.hobbyte.core.utils.functions.ExceptionStream;

@Service
public class MessageSourceHelper {

	private MessageSource source;

	@Autowired
	public MessageSourceHelper(MessageSource source) {
		this.source = source;
	}

	public String getMessage(String code, IHttpContext context, Object... objs) {
		HttpServletRequest request = context.getRequest();
		Languages lang = Languages.getFromRequest(request);
		Locale locale = lang.getLocale();
		return ExceptionStream.handle((e) -> {
			try {
				return source.getMessage(code, objs, Languages.US.getLocale());
			} catch (Exception e1) {
				return code;
			}
		}).call(() -> {
			return source.getMessage(code, objs, locale);
		}).getOrDefault(code);
	}

}