package com.webrest.hobbyte.core.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.webrest.hobbyte.core.http.filter.GuestFilter;
import com.webrest.hobbyte.core.http.filter.IFilter;

@Configuration
public class SecurityConfig extends WebMvcConfigurerAdapter {

	private static final IFilter[] INTERCEPTORS = { new GuestFilter() };

	/**
	 * Add interceptors/filters to {@link InterceptorRegistry}
	 */
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		super.addInterceptors(registry);
		for (IFilter filter : INTERCEPTORS)
			registry.addInterceptor(filter).addPathPatterns(filter.getPath());
	}
	
	@Bean
	public PasswordEncoder passwordEncoder() {
	    return new BCryptPasswordEncoder();
	}

}
