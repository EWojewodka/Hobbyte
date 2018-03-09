package com.webrest.hobbyte.app.user.model;

public enum ExtranetUserAgreement {

	NOT_ACCEPT(0), ACCEPT(1);

	private int id;

	private ExtranetUserAgreement(int id) {
		this.id = id;
	}

	public int getId() {
		return id;
	}

	public static ExtranetUserAgreement getById(int id) {
		ExtranetUserAgreement[] v = values();
		for (ExtranetUserAgreement a : v) {
			if (id == a.getId())
				return a;
		}
		return null;
	}
	
}
