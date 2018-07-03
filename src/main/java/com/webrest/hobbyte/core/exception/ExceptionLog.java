/**
 * 
 */
package com.webrest.hobbyte.core.exception;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.webrest.hobbyte.app.user.model.ExtranetUser;
import com.webrest.hobbyte.core.model.DatabaseObjectImpl;

/**
 * @author Emil Wojewódka
 *
 * @since 16 cze 2018
 */
@Entity
@Table(name = "app_exceptions")
public class ExceptionLog extends DatabaseObjectImpl {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "app_exception_id", nullable = false, unique = true, updatable = false)
	private int id;

	@Column(length = 500, nullable = true)
	private String title;

	@Column(nullable = true)
	private String content;

	@Column(name = "created_at")
	private Date createdAt = new Date();

	@Column(name = "user_id")
	private Integer userId;

	public ExceptionLog(Throwable e) {
		this(e, null);
	}

	public ExceptionLog(Throwable e, ExtranetUser user) {
		this.title = e.getClass().getName();
		this.content = ExceptionUtils.catchStackTrace(e);
		if (user != null)
			this.userId = user.getId();
	}

	protected ExceptionLog() {
	}

	@Override
	public int getId() {
		return id;
	}

	@Override
	public void setId(int id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

}
