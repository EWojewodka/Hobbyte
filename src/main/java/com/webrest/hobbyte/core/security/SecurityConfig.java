package com.webrest.hobbyte.core.security;

import java.util.Iterator;
import java.util.Set;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.webrest.hobbyte.core.http.filter.BasicAbstractFilter;
import com.webrest.hobbyte.core.http.filter.IFilter;
import com.webrest.hobbyte.core.utils.ClassUtils;

@Configuration
public class SecurityConfig extends WebMvcConfigurerAdapter {

	/**
	 * Add interceptors/filters to {@link InterceptorRegistry}
	 */
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		super.addInterceptors(registry);
		IFilter[] filters = findFilters();
		for (IFilter filter : filters)
			registry.addInterceptor(filter).addPathPatterns(filter.getPath());
	}

	private static IFilter[] findFilters() {
		// find non-abstract filter classes
		Set<Class<? extends BasicAbstractFilter>> _filters = ClassUtils.findNonAbstract(BasicAbstractFilter.class);
		IFilter[] arrayOfInstances = new IFilter[_filters.size()];
		Iterator<Class<? extends BasicAbstractFilter>> it = _filters.iterator();
		int i = 0;
		while (it.hasNext()) {
			try {
				// Create instances by non parameter construct.
				arrayOfInstances[i] = (IFilter) it.next().newInstance();
			} catch (InstantiationException | IllegalAccessException e) {
				e.printStackTrace();
			}
			i++;
		}
		return arrayOfInstances;
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

}
