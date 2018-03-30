/**
 * 
 */
package com.webrest.hobbyte.core.criteria;

/**
 * @author Emil Wojewódka
 *
 * @since 24 mar 2018
 */
public class CriteriaJoiner implements ICriteriaJoiner<CriteriaJoiner> {

	private String joinTable;

	private String tableAlias;

	private String joinColumName;
	
	private String condition;

	@Override
	public CriteriaJoiner setJoinTableName(String name) {
		this.joinTable = name;
		return this;
	}

	@Override
	public String getJoinTableName() {
		return joinTable;
	}

	@Override
	public CriteriaJoiner setJoinTableAlias(String name) {
		tableAlias = name;
		return this;
	}

	@Override
	public String getJoinTableAlias(String name) {
		return tableAlias;
	}

	@Override
	public CriteriaJoiner setJoinColumnName(String name) {
		this.joinColumName = name;
		return this;
	}

	@Override
	public String getJoinColumnName() {
		return joinColumName;
	}

	@Override
	public CriteriaJoiner setCondition(String condition) {
		this.condition = condition;
		return this;
	}

	@Override
	public String getCondition() {
		return condition;
	}

}
