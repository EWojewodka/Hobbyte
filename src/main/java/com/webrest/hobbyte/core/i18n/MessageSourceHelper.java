package com.webrest.hobbyte.core.i18n;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import com.webrest.hobbyte.core.http.context.IHttpContext;

@Service
public class MessageSourceHelper{

	private MessageSource source;
	
	@Autowired
	public MessageSourceHelper(MessageSource source) {
		this.source = source;
	}
	
	public String getMessage(String code, IHttpContext context, Object... objs) {
		HttpServletRequest request = context.getRequest();
		Languages lang = Languages.getFromRequest(request);
		return source.getMessage(code, objs, lang.getLocale());
	}
	
	
}
