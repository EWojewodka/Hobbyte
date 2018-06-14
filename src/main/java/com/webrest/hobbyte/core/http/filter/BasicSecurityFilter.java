package com.webrest.hobbyte.core.http.filter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.ModelAndView;

import com.webrest.hobbyte.core.logger.LoggerFactory;
import com.webrest.hobbyte.core.security.WebAccessible;

@Service
public abstract class BasicSecurityFilter implements IFilter, WebAccessible {

	private static final Logger LOGGER = LoggerFactory.getLogger();
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		boolean hasAccess = hasAccess(request, response);
		// If has not access and redirectOnFail is defined, let's redirect that rogue!
		if (!hasAccess && !StringUtils.isEmpty(redirectOnFail())) {
			LOGGER.info("No access for Page: {}; Redirect: {}; IP:{}", request.getRequestURI(), redirectOnFail(), request.getRemoteAddr());
			response.sendRedirect(redirectOnFail());
		}
		return hasAccess;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {

	}

	@Override
	public boolean hasAccess() throws Exception {
		return false;
	}

}
