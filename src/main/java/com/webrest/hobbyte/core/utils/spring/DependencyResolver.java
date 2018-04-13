/**
 * 
 */
package com.webrest.hobbyte.core.utils.spring;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

/**
 * @author Emil Wojewódka
 *
 * @since 8 kwi 2018
 */
@Service
public class DependencyResolver {

	@Autowired
	private ApplicationContext applicationContext;

	public void resolve(IDependencyRequired dependecyRequired) {
		Class<?>[] dependencies = dependecyRequired.getDependencies();
		for(Class<?> clazz : dependencies)
			dependecyRequired.addDependency(clazz, applicationContext.getBean(clazz));
	}
	
}
