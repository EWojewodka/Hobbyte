/**
 * 
 */
package com.webrest.hobbyte.core.criteria;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Emil Wojewódka
 *
 * @since 24 mar 2018
 */
public class CriteriaFilter implements ICriteriaFilter<CriteriaFilter> {

	private List<ICriteriaJoiner<?>> joiners = new ArrayList<>();

	private Map<String, Object> whereMap = new HashMap<>();
	
	private Map<String, Object> whereNotMap = new HashMap<>();

	private Map<String, Object[]> whereInMap = new HashMap<>();

	private int limit;

	private boolean isDistinct;

	private String orderBy;
	
	private OrderDirections orderDirection = OrderDirections.DESC;
	
	public CriteriaFilter() {
	}

	public CriteriaFilter(String whereColumn, Object value) {
		whereMap.put(whereColumn, value);
	}
	
	@Override
	public CriteriaFilter setOrderBy(String columnName) {
		this.orderBy = columnName;
		return this;
	}

	@Override
	public String getOrderBy() {
		return orderBy;
	}

	@Override
	public Map<String, Object> getWhere() {
		return whereMap;
	}
	
	@Override
	public Map<String, Object> getWhereNot() {
		return whereNotMap;
	}
	
	@Override
	public CriteriaFilter addWhereNot(String fieldName, Object value) {
		whereNotMap.put(fieldName, value);
		return this;
	}

	@Override
	public Map<String, Object[]> getWhereIn() {
		return whereInMap;
	}

	@Override
	public CriteriaFilter addWhere(String fieldName, Object value) {
		whereMap.put(fieldName, value);
		return this;
	}

	@Override
	public CriteriaFilter addWhereIn(String fieldName, Object[] values) {
		whereInMap.put(fieldName, values);
		return null;
	}

	@Override
	public CriteriaFilter setLimit(int limit) {
		this.limit = limit;
		return this;
	}

	@Override
	public int getLimit() {
		return limit;
	}

	@Override
	public CriteriaFilter setDistinct(boolean distinct) {
		this.isDistinct = distinct;
		return this;
	}

	@Override
	public boolean isDistinct() {
		return isDistinct;
	}

	@Override
	public CriteriaFilter addJoin(ICriteriaJoiner<?> joiner) {
		joiners.add(joiner);
		return this;
	}

	@Override
	public CriteriaFilter setOrderDirection(OrderDirections direction) {
		this.orderDirection = direction;
		return this;
	}

	@Override
	public OrderDirections getOrderDirection() {
		return orderDirection;
	}
	
}
