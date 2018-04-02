/**
 * 
 */
package com.webrest.hobbyte.core.http.filter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;

/**
 * @author Emil Wojewódka
 *
 * @since 2 kwi 2018
 */
public abstract class BasicFilter implements IFilter{

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		filterBefore(request, response);
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
	}
	
	public abstract void filterBefore(HttpServletRequest request, HttpServletResponse response);

}
