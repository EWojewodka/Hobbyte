/**
 * 
 */
package com.webrest.hobbyte.core.dao;

import com.webrest.hobbyte.core.model.DatabaseObject;

/**
 * Abstract implementation of {@link DaoListener}. It's only for override all of
 * interface methods, so you can implements only what you need. Basicly this
 * 'implementation' has default 0 priority.
 * 
 * @author Emil Wojewódka
 * @soundtrack Kękę - Nigdy Ponad Stan
 * @since 25 mar 2018
 */
public abstract class DaoListenerImpl<T extends DatabaseObject> implements DaoListener<T> {

	@Override
	public int getPriority() {
		return 0;
	}

	@Override
	public void setPriority(int priority) {
	}

	@Override
	public void onCreate() {
	}

	@Override
	public void beforeFirstSave(T databaseObject) {
	}

	@Override
	public void afterFirstSave(T databaseObject) {

	}

	@Override
	public void beforeSave(T databaseObject) {

	}

	@Override
	public void afterSave(T databaseObject) {
	}

	@Override
	public void beforeRemove(T databaseObject) {
	}

	@Override
	public void afterRemove(T databaseObject) {
	}

}
