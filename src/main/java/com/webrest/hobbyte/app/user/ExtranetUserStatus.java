package com.webrest.hobbyte.app.user;

import com.webrest.hobbyte.core.utils.EnumUtils;

public enum ExtranetUserStatus {
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
