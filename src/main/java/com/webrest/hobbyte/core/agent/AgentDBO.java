/**
 * 
 */
package com.webrest.hobbyte.core.agent;

import java.util.Date;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.webrest.hobbyte.core.model.DatabaseObjectImpl;
import com.webrest.hobbyte.core.utils.collection.PropertyStringService;

/**
 * @author Emil Wojewódka
 *
 * @since 14 cze 2018
 */
@Entity
@Table(name = "app_agents")
public class AgentDBO extends DatabaseObjectImpl {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "app_agent_id", nullable = false, unique = true, updatable = false)
	private int id;

	@Column
	private String code;

	@Column(name = "agent_class")
	private Class<? extends Agent> agentClass;

	@Column(name = "created_at")
	private Date createdAt = new Date();

	@Column(name = "next_run", nullable = true)
	private Date nextRun;

	@Column(name = "last_run", nullable = true)
	private Date lastRun;

	@Column(name = "properties", nullable = true)
	private String propertyString;

	@Column(nullable = true)
	private int period;

	@Column
	private int status = AgentStatus.OFF.getId();

	@Override
	public int getId() {
		return id;
	}

	@Override
	public void setId(int id) {
		this.id = id;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Class<? extends Agent> getAgentClass() {
		return agentClass;
	}

	public void setAgentClass(Class<? extends Agent> agentClass) {
		this.agentClass = agentClass;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public Date getNextRun() {
		return nextRun;
	}

	public void setNextRun(Date nextRun) {
		this.nextRun = nextRun;
	}

	public Date getLastRun() {
		return lastRun;
	}

	public void setLastRun(Date lastRun) {
		this.lastRun = lastRun;
	}

	public String getPropertyString() {
		return propertyString;
	}

	public Map<String, String> getProperties() {
		return PropertyStringService.getMapFromString(propertyString);
	}

	public void putProperty(String key, String value) {
		this.propertyString = PropertyStringService.putKeyValue(propertyString, key, value);
	}

	public void setPropertyString(Map<String, String> map) {
		this.propertyString = PropertyStringService.mapToPropertyString(map);
	}

	public AgentStatus getStatus() {
		return AgentStatus.getStatusById(status);
	}

	public void setStatus(AgentStatus status) {
		this.status = status.getId();
	}

	public int getPeriod() {
		return period;
	}

	public void setPeriod(int period) {
		this.period = period;
	}

}
