/**
 * 
 */
package com.webrest.hobbyte.core.utils.spring;

import java.lang.reflect.Array;

import org.springframework.context.ApplicationContext;

/**
 * @author Emil Wojewódka
 *
 * @see DependencyRequired
 * @see DependencyResolver
 * @since 8 kwi 2018
 */
public interface IDependencyRequired {

	/**
	 * Return {@link Array} of {@link Class} which should be inject by
	 * {@link DependencyResolver} </br>
	 * <i>Note: only objects of type from {@link ApplicationContext} should be
	 * return from this method.</i>
	 * 
	 * @see #addDependency(Class, Object)
	 * @see #getDependency(Class)
	 * @return
	 */
	Class<?>[] getDependencies();

	void addDependency(Class<?> clazz, Object obj);

	/**
	 * Get dependency object inject by {@link DependencyResolver}
	 * 
	 * @param clazz
	 * @return
	 */
	<T> T getDependency(Class<T> clazz);
	
	
}
