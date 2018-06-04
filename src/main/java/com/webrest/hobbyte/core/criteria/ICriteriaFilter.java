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

	public enum OrderDirections {
		ASC, DESC;

		public static OrderDirections getByName(String name) {
			OrderDirections[] v = values();
			for (OrderDirections o : v) {
				if (o.name().equalsIgnoreCase(name))
					return o;
			}
			return null;
		}

	}

	T addWhere(String fieldName, Object value);

	Map<String, Object> getWhere();
	
	T addWhereNot(String fieldName, Object value);
	
	Map<String, Object> getWhereNot();

	T addWhereIn(String fieldName, Object[] objects);

	Map<String, Object[]> getWhereIn();

	T addWhereNotIn(String fieldName, Object[] objects);

	Map<String, Object[]> getWhereNotIn();
	
	T setOrderBy(String columnName);

	String getOrderBy();

	T setOrderDirection(OrderDirections direction);

	OrderDirections getOrderDirection();

	T setLimit(int limit);

	int getLimit();

	T setDistinct(boolean distinct);

	boolean isDistinct();

	T addJoin(ICriteriaJoiner<?> joiner);
}
