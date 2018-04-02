package com.webrest.hobbyte.core.http.filter;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.HandlerInterceptor;

import com.webrest.hobbyte.core.security.SecurityConfig;

/**
 * Expander of {@link HandlerInterceptor} interface from
 * {@code SpringFramework}. All of {@link IFilter} implementation should be an
 * {@link Service} or {@link Component} annotated 'because all of them are
 * autowired into {@link SecurityConfig}. It's neccessery because we have to be
 * connected with DatabaseBase which is managed by {@code Hibernate} and
 * {@code SpringFramework}.
 * 
 * @author Emil Wojewódka
 *
 * @since 2 kwi 2018
 */
@Service
@Scope(scopeName = "singleton")
public interface IFilter extends HandlerInterceptor {

	/**
	 * Return path on which this {@link IFilter} is working.
	 * 
	 * @return
	 */
	String getPath();
}
