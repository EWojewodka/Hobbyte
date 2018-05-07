package com.webrest.hobbyte.core.http.context;

import org.springframework.context.annotation.ScopedProxyMode;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.webrest.hobbyte.core.utils.collection.ParameterContainer;

@Service(value = "http")
@Scope(value = "request", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class HttpContext extends ParameterContainer implements IHttpContext {

	@Autowired
	private HttpServletRequest request;

	@Autowired
	private HttpServletResponse response;
	
	private MessageContextHandler messageContextHandler = new MessageContextHandler(this);

	@Override
	public HttpServletRequest getRequest() {
		return request;
	}

	@Override
	public HttpServletResponse getResponse() {
		return response;
	}

	@Override
	public HttpSession getSession() {
		return getSession(false);
	}

	@Override
	public HttpSession getSession(boolean force) {
		return request.getSession(force);
	}
	
	public MessageContextHandler getMessageHandler() {
		return messageContextHandler;
	}

}
