/**
 * 
 */
package com.webrest.hobbyte.core.view;

import java.util.HashMap;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.thymeleaf.extras.java8time.dialect.Java8TimeDialect;
import org.thymeleaf.spring4.SpringTemplateEngine;
import org.thymeleaf.spring4.view.ThymeleafViewResolver;

import com.webrest.hobbyte.core.appParams.AppParamDao;
import com.webrest.hobbyte.core.utils.DateUtils;

import nz.net.ultraq.thymeleaf.LayoutDialect;
import nz.net.ultraq.thymeleaf.decorators.strategies.GroupingStrategy;

/**
 * @author Emil Wojewódka
 *
 * @since 4 kwi 2018
 */
@Configuration
public class ViewConfig {

	@Autowired(required = true)
	private ThymeleafViewResolver thymeleafResolver;

	@Autowired(required = true)
	private ViewStaticVariableMap staticVariableMap;

	@PostConstruct
	private void init() {
		initLayoutDialect();
		addStaticVariables();
	}

	// http://www.baeldung.com/thymeleaf-spring-layouts
	private void initLayoutDialect() {
		SpringTemplateEngine engine = new SpringTemplateEngine();
		engine.addDialect(new LayoutDialect(new GroupingStrategy()));
		engine.addDialect(new Java8TimeDialect());
	}

	private void addStaticVariables() {
		thymeleafResolver.setStaticVariables(staticVariableMap);
	}

}

/**
 * Class for management a thymeleaf variables.
 * 
 * @author EWojewodka
 *
 */
@Component
class ViewStaticVariableMap extends HashMap<String, Object> {

	private static final long serialVersionUID = -1412721301402965194L;

	@Autowired
	private AppParamDao appParamDao;

	@PostConstruct
	private void init() {
		put("appParam", appParamDao);
		put("dateUtils", new DateUtils());
	}

}
