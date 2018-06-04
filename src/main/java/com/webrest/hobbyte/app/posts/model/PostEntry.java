/**
 * 
 */
package com.webrest.hobbyte.app.posts.model;

import java.util.Collection;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonView;
import com.webrest.hobbyte.app.comments.Comment;
import com.webrest.hobbyte.app.reaction.model.PostEntryReaction;
import com.webrest.hobbyte.app.user.model.ExtranetUser;
import com.webrest.hobbyte.core.model.DatabaseObjectImpl;
import com.webrest.hobbyte.core.model.json.View;
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
	@JsonView(View.Basic.class)
	private int id;

	@JoinColumn(name = "author_id")
	@OneToOne
	@JsonView(View.Basic.class)
	private ExtranetUser author;

	@Column(nullable = true)
	@JsonView(View.Basic.class)
	private String content;

	@Column(name = "created_at")
	@JsonView(View.Basic.class)
	@JsonFormat(pattern = "EEEEEEEE dd.MM HH:mm")
	private Date createdAt = new Date();

	@Column(name = "image_url")
	@JsonView(View.Basic.class)
	private String imageUrl;

	@Column(name = "video_url")
	@JsonView(View.Basic.class)
	private String videoUrl;

	@Column(name = "doc_url")
	@JsonView(View.Basic.class)
	private String documentUrl;

	@Column
	private int status = PostEntryStatus.PENDING.getId();

	@OneToMany(mappedBy = "postEntry")
	@JsonView(View.Basic.class)
	private Collection<PostEntryReaction> reactions;

	@OneToMany(mappedBy = "postEntryId", fetch = FetchType.LAZY)
	@JsonView(View.Basic.class)
	private Collection<Comment> comments;
	
	@Transient
	private int commentCount = comments == null ? 0 : comments.size();

	public PostEntry(ExtranetUser author) {
		Asserts.exists(author);
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

	public ExtranetUser getAuthor() {
		return author;
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

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public String getVideoUrl() {
		return videoUrl;
	}

	public void setVideoUrl(String videoUrl) {
		this.videoUrl = videoUrl;
	}

	public String getDocumentUrl() {
		return documentUrl;
	}

	public void setDocumentUrl(String documentUrl) {
		this.documentUrl = documentUrl;
	}

	public PostEntryStatus getStatus() {
		return PostEntryStatus.getById(status);
	}

	public void setStatus(PostEntryStatus status) {
		this.status = status.getId();
	}

	public Collection<PostEntryReaction> getReactions() {
		return reactions;
	}

	public int getCommentCount() {
		return commentCount;
	}

	public PostEntry() {

	}

	public Collection<Comment> getComments() {
		return comments;
	}

}
