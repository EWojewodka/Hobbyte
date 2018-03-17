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
public enum Gender implements WithId{
	UNSET(0),
	MALE(1),
	FEMALE(2);
	
	
	private int id;
	
	private Gender(int id) {
		this.id = id;
	}
	
	public static Gender getById(int id) {
		return EnumUtils.findById(Gender.class, id);
	}

	public int getId() {
		return id;
	}

}
