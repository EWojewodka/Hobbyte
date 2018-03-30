/**
 * 
 */
package com.webrest.hobbyte.core.dao;

import com.webrest.hobbyte.core.model.DatabaseObject;
import com.webrest.hobbyte.core.utils.collection.IPriority;

/**
 * Listener which is invoke in {@link GenericDao} before and after some database
 * lifecycle state. It's heavy linked with {@link DaoListenerState}. </br>
 * Listeners has {@link IPriority} nature, so they could be sort before
 * invoking.
 * 
 * @author Emil Wojewódka
 *
 * @since 24 mar 2018
 */
public interface DaoListener<T extends DatabaseObject> extends IPriority{

	public enum DaoListenerState {
		CREATE,
		BEFORE_FIRST_SAVE,
		AFTER_FIRST_SAVE,
		BEFORE_SAVE,
		AFTER_SAVE,
		BEFORE_REMOVE,
		AFTER_REMOVE;
	}
	
	/** Method invoke by {@link DaoListenerState#CREATE} state */
	void onCreate();

	/** Method invoke by {@link DaoListenerState#BEFORE_FIRST_SAVE} state */
	void beforeFirstSave(T databaseObject);

	/** Method invoke by {@link DaoListenerState#AFTER_FIRST_SAVE} state */
	void afterFirstSave(T databaseObject);

	/** Method invoke by {@link DaoListenerState#BEFORE_SAVE} state */
	void beforeSave(T databaseObject);

	/** Method invoke by {@link DaoListenerState#AFTER_SAVE} state */
	void afterSave(T databaseObject);

	/** Method invoke by {@link DaoListenerState#BEFORE_REMOVE} state */
	void beforeRemove(T databaseObject);

	/** Method invoke by {@link DaoListenerState#AFTER_REMOVE} state */
	void afterRemove(T databaseObject);
	
}
