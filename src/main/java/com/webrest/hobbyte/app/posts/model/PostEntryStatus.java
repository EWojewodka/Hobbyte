/**
 * 
 */
package com.webrest.hobbyte.app.posts.model;

import com.webrest.hobbyte.core.utils.EnumUtils;
import com.webrest.hobbyte.core.utils.WithId;

/**
 * @author Emil Wojewódka
 *
 * @since 21 maj 2018
 */
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
