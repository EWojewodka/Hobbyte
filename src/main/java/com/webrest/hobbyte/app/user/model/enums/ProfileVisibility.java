/**
 * 
 */
package com.webrest.hobbyte.app.user.model.enums;

import com.webrest.hobbyte.core.utils.EnumUtils;
import com.webrest.hobbyte.core.utils.WithId;

/**
 * @author Emil Wojewódka
 *
 * @since 17 mar 2018
 */
public enum ProfileVisibility implements WithId{

	PUBLIC(1),
	FOR_FRIENDS(2);
	
	private int id;
	
	private ProfileVisibility(int id) {
		this.id=id;
	}

	@Override
	public int getId() {
		return id;
	}

	public static ProfileVisibility getById(int id) {
		return EnumUtils.findById(ProfileVisibility.class, id);
	}
}
