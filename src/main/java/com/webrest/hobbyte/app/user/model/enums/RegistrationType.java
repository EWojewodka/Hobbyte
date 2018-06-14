/**
 * 
 */
package com.webrest.hobbyte.app.user.model.enums;

import com.webrest.hobbyte.core.utils.EnumUtils;
import com.webrest.hobbyte.core.utils.WithId;

/**
 * @author Emil Wojewódka
 *
 * @since 13 cze 2018
 */
public enum RegistrationType implements WithId {

	DEFAULT(0), FACEBOOK(1);

	private int id;

	private RegistrationType(int id) {
		this.id = id;
	}

	public static RegistrationType getById(int id) {
		return EnumUtils.findById(RegistrationType.class, id);
	}

	@Override
	public int getId() {
		return id;
	}

}
