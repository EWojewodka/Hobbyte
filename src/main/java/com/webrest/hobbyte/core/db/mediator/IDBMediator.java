package com.webrest.hobbyte.core.db.mediator;

import java.util.Collection;

/**
 * Basic interface for raw queries between java application and database isntance.
 * @author Emil
 *
 */
public interface IDBMediator {

	/**
	 * Get result in list object.
	 * 
	 * @param query
	 * @return
	 */
	<T> Collection<T> getCollection(String query);
	
	/**
	 * Execute raw query.
	 * @param query
	 */
	boolean execute(String query);
	
	<T> T getFirst(String query);
	
	void setConnectionManager(IConnectionManager manager);
	
}
