package com.webrest.hobbyte.app.user.model.enums;

import com.webrest.hobbyte.core.utils.EnumUtils;
import com.webrest.hobbyte.core.utils.WithId;

public enum ExtranetUserStatus implements WithId{
	ACTIVE(1),
	/**
	 * 
	 */
	NOT_ACTIVE(2),
	/**
	 * 
	 */
	PENDING(3);

	private int id;

	private ExtranetUserStatus(int id) {
		this.id = id;
	}
	
	public int getId() {
		return id;
	}

	public static ExtranetUserStatus findById(int id) {
		return EnumUtils.findById(ExtranetUserStatus.class, id);
	}
}
