/**
 * 
 */
package com.webrest.hobbyte.core.model;

import javax.persistence.Entity;

import com.webrest.hobbyte.core.dao.GenericDao;

/**
 * Shared interface for {@link Entity}. It's neccessery for using
 * {@link GenericDao}. </br>
 * 
 * @author Emil Wojewódka
 *
 * @since 24 mar 2018
 */
public interface DatabaseObject {

	int getId();

	void setId(int id);

	/**
	 * Return <code>true</code> if entity wasn't stored in database.
	 * 
	 * @return
	 */
	boolean isNew();

	void save(GenericDao<DatabaseObject> dao);
	
}
