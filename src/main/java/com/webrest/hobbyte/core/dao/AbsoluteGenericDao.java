/**
 * 
 */
package com.webrest.hobbyte.core.dao;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.webrest.hobbyte.core.model.DatabaseObject;

/**
 * @author Emil Wojewódka
 *
 * @since 24 mar 2018
 */
@Component
@Scope("prototype")
public class AbsoluteGenericDao<T extends DatabaseObject> extends GenericDao<T> {

	private Class<? extends DatabaseObject> genericType;

	@Override
	protected Class<?> getGenericType() {
		if (genericType == null)
			throw new IllegalArgumentException("Before use AbsoluteGenericDao you have to define type.");
		return genericType;
	}

	public void setGenericType(Class<? extends DatabaseObject> genericClass) {
		this.genericType = genericClass;
	}

}
