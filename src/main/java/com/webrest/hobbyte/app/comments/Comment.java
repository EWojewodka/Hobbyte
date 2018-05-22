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

import com.webrest.hobbyte.app.posts.model.PostEntry;
import com.webrest.hobbyte.app.user.model.ExtranetUser;
import com.webrest.hobbyte.core.model.DatabaseObjectImpl;
import com.webrest.hobbyte.core.model.json.AsJSON;

/**
 * @author Emil Wojewódka
 *
 * @since 21 maj 2018
 */
@Entity
@Table(name = "hb_comments")
@AsJSON
public class Comment extends DatabaseObjectImpl {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "hb_comment_id", nullable = false, unique = true, updatable = false)
	private int id;

	@Column(name = "content", nullable = false)
	private String content;

	@Column(name = "created_at")
	private Date createdAt = new Date();

	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "author_id")
	private ExtranetUser author;

	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "post_id")
	private PostEntry postEntryId;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "parent_comment_id")
	private Comment parentComment;

	public Comment(String content, ExtranetUser author) {
		this.content = content;
		this.author = author;
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

	public PostEntry getPostEntryId() {
		return postEntryId;
	}

	public void setPostEntryId(PostEntry postEntryId) {
		this.postEntryId = postEntryId;
	}

	public Comment getParentComment() {
		return parentComment;
	}

	public void setParentComment(Comment parentComment) {
		this.parentComment = parentComment;
	}

}
