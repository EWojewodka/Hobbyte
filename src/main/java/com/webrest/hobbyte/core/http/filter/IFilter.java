package com.webrest.hobbyte.core.http.filter;

import org.springframework.web.servlet.HandlerInterceptor;

import com.webrest.hobbyte.core.security.WebAccessible;

public interface IFilter extends WebAccessible, HandlerInterceptor{

	String getPath();
	
	String redirectOnFail();
}
