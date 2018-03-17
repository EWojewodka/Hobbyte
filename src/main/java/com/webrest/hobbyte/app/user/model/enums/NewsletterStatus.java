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
public enum NewsletterStatus implements WithId{
	OFF(0),
	ON(1);
	
	private int id;
	
	private NewsletterStatus(int id) {
		this.id = id;
	}

	public int getId() {
		return id;
	}

	public static NewsletterStatus getById(int id) {
		return EnumUtils.findById(NewsletterStatus.class, id);
	}
}
