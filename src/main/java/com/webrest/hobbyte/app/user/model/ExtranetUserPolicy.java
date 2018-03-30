/**
 * 
 */
package com.webrest.hobbyte.app.user.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.webrest.hobbyte.app.user.model.enums.ProfileVisibility;
import com.webrest.hobbyte.core.model.DatabaseObjectImpl;

/**
 * @author Emil Wojewódka
 *
 * @since 17 mar 2018
 */
@Entity
@Table(name = "hb_extranet_user_policy")
public class ExtranetUserPolicy extends DatabaseObjectImpl {

	@Id
	@GeneratedValue(strategy =GenerationType.AUTO)
	@Column(name = "hb_extranet_user_policy_id")
	private int id;

	@Column(name = "user_id", nullable = false)
	private int userId;

	@Column(name = "profile_visibility")
	private int profileVisibilty = ProfileVisibility.PUBLIC.getId();

	@Transient
	private ExtranetUser user;
	
	protected ExtranetUserPolicy() {

	}

	public ExtranetUserPolicy(ExtranetUser user) {
		this.user = user;
		this.userId = user.getId();
	}

	public ProfileVisibility getProfileVisibilty() {
		return ProfileVisibility.getById(profileVisibilty);
	}

	public void setProfileVisibilty(ProfileVisibility visibility) {
		this.profileVisibilty = visibility.getId();
	}

	public ExtranetUser getUser() {
		return user;
	}

	@Override
	public int getId() {
		return id;
	}

	@Override
	public void setId(int id) {
		this.id = id;
	}

	public int getUserId() {
		return userId;
	}

	public void setProfileVisibilty(int profileVisibilty) {
		this.profileVisibilty = profileVisibilty;
	}

	public void setUser(ExtranetUser user) {
		this.user = user;
	}

}
