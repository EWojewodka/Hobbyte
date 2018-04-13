/**
 * 
 */
package com.webrest.hobbyte.app.reaction.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.webrest.hobbyte.app.posts.model.PostEntry;
import com.webrest.hobbyte.app.reaction.IPostEntryReaction;
import com.webrest.hobbyte.app.user.model.ExtranetUser;
import com.webrest.hobbyte.core.model.DatabaseObjectImpl;
import com.webrest.hobbyte.core.utils.Asserts;
import com.webrest.hobbyte.core.utils.EnumUtils;

/**
 * @author Emil Wojewódka
 *
 * @since 10 kwi 2018
 */
@Entity
@Table(name = "hb_post_entry_reaction")
public class PostEntryReaction extends DatabaseObjectImpl implements IPostEntryReaction {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "hb_post_entry_reaction_id", nullable = false, unique = true, updatable = false)
	private int id;

	@Column(nullable = false)
	private String type;

	@Column(name = "user_id")
	private int userId;

	@Column(name = "post_entry_id")
	private int postEntryId;
	
	@Column(name = "created_at")
	private Date createdAt = new Date();

	public PostEntryReaction(ExtranetUser user, PostEntry entry, PostEntryReactions reactionType) {
		Asserts.exists(user);
		Asserts.exists(entry);
		this.userId = user.getId();
		this.postEntryId = entry.getId();
		this.type = reactionType.getCode();
	}

	@Override
	public int getId() {
		return id;
	}

	@Override
	public void setId(int id) {
		this.id = id;
	}

	public String getType() {
		return type;
	}

	public int getUserId() {
		return userId;
	}

	public int getPostEntryId() {
		return postEntryId;
	}

	@Override
	public PostEntryReactions getReactionType() {
		return EnumUtils.findByCode(PostEntryReactions.class, type);
	}

}
