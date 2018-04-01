/**
 * 
 */
package com.webrest.hobbyte.app.user.model.enums;

import com.webrest.hobbyte.core.utils.EnumUtils;
import com.webrest.hobbyte.core.utils.WithId;

/**
 * @author Emil Wojewódka
 *
 * @since 31 mar 2018
 */
public enum ExtranetUserRoles implements WithId{

	USER(0),
	PREMIUM(1),
	MODERATOR(2),
	ADMIN(7);
	
	private int id;
	
	private ExtranetUserRoles(int id) {
		this.id = id;
	}
		
	@Override
	public int getId() {
		return id;
	}
	
	public static ExtranetUserRoles getById(int id) {
		return EnumUtils.findById(ExtranetUserRoles.class, id);
	}

}
