package com.webrest.hobbyte.core.security;

import java.util.List;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.webrest.hobbyte.core.http.filter.IFilter;
import com.webrest.hobbyte.core.logger.LoggerFactory;

@Configuration
public class SecurityConfig extends WebMvcConfigurerAdapter {

	private static final Logger LOGGER = LoggerFactory.getLogger();

	@Autowired
	private List<IFilter> filters;

	/**
	 * Add interceptors/filters to {@link InterceptorRegistry}
	 */
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		super.addInterceptors(registry);
		filters.forEach(filter -> {
			LOGGER.info(String.format("Register fiilter (%s) for path (%s)", filter.getClass(), filter.getPath()));
			registry.addInterceptor(filter).addPathPatterns(filter.getPath());
		});
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	

}
