/**
 * 
 */
package com.webrest.hobbyte.app.posts.model;

import java.util.Collection;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.webrest.hobbyte.app.reaction.model.PostEntryReaction;
import com.webrest.hobbyte.app.user.model.ExtranetUser;
import com.webrest.hobbyte.core.model.DatabaseObjectImpl;
import com.webrest.hobbyte.core.model.json.AsJSON;
import com.webrest.hobbyte.core.utils.Asserts;
import com.webrest.hobbyte.core.utils.EnumUtils;
import com.webrest.hobbyte.core.utils.WithId;

/**
 * @author Emil Wojewódka
 *
 * @since 10 kwi 2018
 */
@Entity
@Table(name = "hb_post_entries")
public class PostEntry extends DatabaseObjectImpl {

	public enum PostEntryStatus implements WithId {
		PUBLISH(0), PENDING(1), REPORT(2);

		private int id;

		private PostEntryStatus(int id) {
			this.id = id;
		}

		@Override
		public int getId() {
			return id;
		}

		public static PostEntryStatus getById(int id) {
			return EnumUtils.findById(PostEntryStatus.class, id);
		}
	}

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "hb_post_entry_id", nullable = false, unique = true, updatable = false)
	@AsJSON
	private int id;

	@JoinColumn(name = "author_id")
	@OneToOne
	@AsJSON
	private ExtranetUser author;

	@Column(nullable = true)
	@AsJSON
	private String content;

	@Column(name = "created_at")
	@AsJSON
	private Date createdAt = new Date();

	@Column(name = "image_url")
	@AsJSON
	private String imageUrl;

	@Column(name = "video_url")
	@AsJSON
	private String videoUrl;

	@Column(name = "doc_url")
	@AsJSON
	private String documentUrl;

	@Column
	@AsJSON
	private int status = PostEntryStatus.PENDING.id;
	
	@AsJSON
	@OneToMany(mappedBy = "postEntry")
	private Collection<PostEntryReaction> reactions;
	
	public PostEntry(ExtranetUser author) {
		Asserts.exists(author);
		this.author = author;
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
	
	public Collection<PostEntryReaction> getReactions(){
		return reactions;
	}
	
	public PostEntry() {
		
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
