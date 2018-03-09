package com.webrest.hobbyte.core.security;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface WebAccessible extends Accessible{

	boolean hasAccess(HttpServletRequest request, HttpServletResponse repsonse) throws Exception;

}
