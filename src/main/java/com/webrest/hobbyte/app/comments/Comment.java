/**
 * 
 */
package com.webrest.hobbyte.app.comments;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonView;
import com.webrest.hobbyte.app.user.model.ExtranetUser;
import com.webrest.hobbyte.core.model.DatabaseObjectImpl;
import com.webrest.hobbyte.core.model.json.View;

/**
 * @author Emil Wojewódka
 *
 * @since 21 maj 2018
 */
@Entity
@Table(name = "hb_comments")
public class Comment extends DatabaseObjectImpl {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "hb_comment_id", nullable = false, unique = true, updatable = false)
	@JsonView(View.Basic.class)
	private int id;

	@Column(name = "content", nullable = false)
	@JsonView(View.Basic.class)
	private String content;

	@Column(name = "created_at")
	@JsonView(View.Basic.class)
	@JsonFormat(pattern = "EEEEEEEE dd.MM HH:mm")
	private Date createdAt = new Date();

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "author_id")
	@JsonView(View.Basic.class)
	private ExtranetUser author;

	@Column(name = "post_id")
	@JsonView(View.Basic.class)
	private int postEntryId;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "parent_comment_id")
	@JsonView(View.Basic.class)
	private Comment parentComment;

	//For spring
	protected Comment() {}
	
	public Comment(String content, ExtranetUser author, int postEntryId) {
		this.content = content;
		this.author = author;
		this.postEntryId = postEntryId;
	}

	@Override
	public int getId() {
		return id;
	}

	@Override
	public void setId(int id) {
		this.id = id;
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

	public ExtranetUser getAuthor() {
		return author;
	}

	public void setAuthor(ExtranetUser author) {
		this.author = author;
	}

	public int getPostEntryId() {
		return postEntryId;
	}

	public void setPostEntryId(int postEntryId) {
		this.postEntryId = postEntryId;
	}

	public Comment getParentComment() {
		return parentComment;
	}

	public void setParentComment(Comment parentComment) {
		this.parentComment = parentComment;
	}

}
