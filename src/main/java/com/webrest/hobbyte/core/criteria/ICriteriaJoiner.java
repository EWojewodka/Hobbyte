/**
 * 
 */
package com.webrest.hobbyte.core.criteria;

/**
 * @author Emil Wojewódka
 *
 * @since 24 mar 2018
 */
public interface ICriteriaJoiner<T extends ICriteriaJoiner<?>> {

	T setJoinTableName(String name);
	
	String getJoinTableName();
	
	T setJoinTableAlias(String name);
	
	String getJoinTableAlias(String name);
	
	T setJoinColumnName(String name);
	
	String getJoinColumnName();
	
	T setCondition(String condition);
	
	String getCondition();
}
