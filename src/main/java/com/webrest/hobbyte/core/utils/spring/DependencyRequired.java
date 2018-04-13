/**
 * 
 */
package com.webrest.hobbyte.core.utils.spring;

import java.util.HashMap;
import java.util.Map;

import com.webrest.hobbyte.core.utils.ClassUtils;

/**
 * Abstract {@link Class} implements {@link IDependencyRequired} for easy inject
 * {@ Spring framework} objects in manual created objects.
 * 
 * @see IDependencyRequired
 * @see DependencyResolver
 * @author Emil Wojewódka
 *
 * @since 10 kwi 2018
 */
public abstract class DependencyRequired implements IDependencyRequired {

	private Map<Class<?>, Object> dependencyBuffer = new HashMap<>();

	public DependencyRequired(DependencyResolver dependencyResolver) {
		dependencyResolver.resolve(this);
	}
	
	@Override
	public Class<?>[] getDependencies() {
		return ClassUtils.EMPTY_ARRAY;
	}

	@Override
	public void addDependency(Class<?> clazz, Object obj) {
		dependencyBuffer.put(clazz, obj);
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> T getDependency(Class<T> clazz) {
		return (T) dependencyBuffer.get(clazz);
	}

}
