/**
 * 
 */
package com.webrest.hobbyte.core.dao;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Id;
import javax.persistence.Table;

import com.webrest.hobbyte.core.criteria.ICriteriaFilter;
import com.webrest.hobbyte.core.model.DatabaseObject;

/**
 * @author Emil Wojewódka
 *
 * @since 24 mar 2018
 */
public interface IGenericDao<T extends DatabaseObject, ID extends Serializable> {

	/**
	 * Save entity in database
	 * 
	 * @param t
	 * @throws Exception
	 */
	void save(T t) throws Exception;

	/**
	 * Count all database rows of T entity
	 * 
	 * @return
	 * @throws Exception
	 */
	Long count() throws Exception;

	T create();

	/**
	 * Delete row from database
	 * 
	 * @param t
	 * @throws Exception
	 */
	void delete(T t) throws Exception;

	/**
	 * Delete row with specified {@link Id} in database from {@link Table}
	 * 
	 * @param id
	 * @throws Exception
	 */
	void delete(ID id) throws Exception;

	/**
	 * Return true if object exists in database.
	 * 
	 * @param t
	 * @return
	 * @throws Exception
	 */
	boolean exists(T t) throws Exception;

	/**
	 * Return true if object with specified id exists in database in {@link Table}
	 * 
	 * @param id
	 * @return
	 * @throws Exception
	 */
	boolean exists(ID id) throws Exception;

	/**
	 * Return instance of {@link DatabaseObject} with specified ID.
	 * 
	 * @param id
	 * @return
	 * @throws Exception
	 */
	T getById(ID id) throws Exception;

	/**
	 * Return {@link List} of entities which are in database where fieldName=value
	 * 
	 * @param fieldName
	 * @param value
	 * @return
	 */
	List<T> findAllBy(String fieldName, Object value);
	
	List<T> find(ICriteriaFilter<?> criteriaFilter);

	T findBy(String fieldName, Object value);
}
