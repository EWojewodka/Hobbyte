/**
 * 
 */
package com.webrest.hobbyte.app.user.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.webrest.hobbyte.app.user.model.enums.ProfileVisibility;

/**
 * @author Emil Wojewódka
 *
 * @since 17 mar 2018
 */
@Entity
@Table(name = "hb_extranet_user_policy")
public class ExtranetUserPolicy {

	@Id
	@Column(name = "user_id")
	private int userId;
	
	
	@Column(name = "profile_visible")
	private int profileVisibilty = ProfileVisibility.PUBLIC.getId();

	@Transient
	private ExtranetUser user;

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
	
	
}
