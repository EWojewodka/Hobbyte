/**
 * 
 */
package com.webrest.hobbyte.core.criteria;

import java.util.Map;

/**
 * @author Emil Wojewódka
 *
 * @since 24 mar 2018
 */
public interface ICriteriaFilter<T extends ICriteriaFilter<T>> {

	T addWhere(String fieldName, Object value);
	
	Map<String, Object> getWhere();
	
	T addWhereIn(String fieldName, Object[] objects);
	
	Map<String, Object[]> getWhereIn();
	
	T setOrderBy(String columnName);
	
	String getOrderBy();
	
	T setLimit(int limit);
	
	int getLimit();
	
	T setDistinct(boolean distinct);
	
	boolean isDistinct();
	
	T addJoin(ICriteriaJoiner<?> joiner);
}
