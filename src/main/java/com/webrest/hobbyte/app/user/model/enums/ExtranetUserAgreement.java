package com.webrest.hobbyte.app.user.model.enums;

import com.webrest.hobbyte.core.utils.EnumUtils;
import com.webrest.hobbyte.core.utils.WithId;

public enum ExtranetUserAgreement implements WithId{

	NOT_ACCEPT(0), ACCEPT(1);

	private int id;

	private ExtranetUserAgreement(int id) {
		this.id = id;
	}

	public int getId() {
		return id;
	}

	public static ExtranetUserAgreement getById(int id) {
		return EnumUtils.findById(ExtranetUserAgreement.class, id);
	}
	
}
