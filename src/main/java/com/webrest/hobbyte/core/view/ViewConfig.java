/**
 * 
 */
package com.webrest.hobbyte.core.view;

import javax.annotation.PostConstruct;

import org.springframework.context.annotation.Configuration;
import org.thymeleaf.spring4.SpringTemplateEngine;

import nz.net.ultraq.thymeleaf.LayoutDialect;
import nz.net.ultraq.thymeleaf.decorators.strategies.GroupingStrategy;

/**
 * @author Emil Wojewódka
 *
 * @since 4 kwi 2018
 */
@Configuration
public class ViewConfig {

	@PostConstruct
	private void init() {
		System.out.println("Das");
		initLayoutDialect();
	}

	// http://www.baeldung.com/thymeleaf-spring-layouts
	private void initLayoutDialect() {
		SpringTemplateEngine engine = new SpringTemplateEngine();
		engine.addDialect(new LayoutDialect(new GroupingStrategy()));
	}

}
