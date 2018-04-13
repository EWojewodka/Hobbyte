/**
 * 
 */
package com.webrest.hobbyte.app.posts.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.webrest.hobbyte.app.user.model.ExtranetUser;
import com.webrest.hobbyte.core.model.DatabaseObjectImpl;
import com.webrest.hobbyte.core.utils.Asserts;

/**
 * @author Emil Wojewódka
 *
 * @since 10 kwi 2018
 */
@Entity
@Table(name = "hb_post_entries")
public class PostEntry extends DatabaseObjectImpl {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "hb_post_entry_id", nullable = false, unique = true, updatable = false)
	private int id;

	@Column(name = "author_id")
	private int authorId;

	@Column(nullable = false)
	private String content;

	@Column(name = "created_at")
	private Date createdAt = new Date();

	public PostEntry(ExtranetUser author) {
		Asserts.exists(author);
		this.authorId = author.getId();
	}

	public int getAuthorId() {
		return authorId;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getContent() {
		return content;
	}

	public Date getCreatedAt() {
		return createdAt;
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
