package com.webrest.hobbyte.core.appParams;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.webrest.hobbyte.core.model.DatabaseObjectImpl;

@Entity
@Table(name = "app_params")
public class AppParam extends DatabaseObjectImpl {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "app_param_id", nullable = false, unique = true, updatable = false)
	private int id;

	@Column(nullable = false)
	private String code;

	@Column(nullable = true)
	private String value;

	@Column(nullable = false, name = "app_group")
	private String group;

	public AppParam() {
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getGroup() {
		return group;
	}

	public void setGroup(String group) {
		this.group = group;
	}

	@Override
	public int getId() {
		return id;
	}

	@Override
	public void setId(int id) {
		this.id = id;
	}

}
