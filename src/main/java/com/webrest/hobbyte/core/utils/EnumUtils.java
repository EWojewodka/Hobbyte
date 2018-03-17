package com.webrest.hobbyte.core.utils;

public class EnumUtils {

	public static <T extends Enum<? extends WithId>> T findById(Class<T> enumType, int id) {
		try {
			T[] objects = enumType.getEnumConstants();
			for (T _obj : objects) {
				WithId obj = (WithId) _obj;
				if (id == obj.getId())
					return _obj;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static <T extends Enum<? extends WithCode>> T findByCode(Class<T> enumType, String code) {
		try {
			T[] objects = enumType.getEnumConstants();
			for (T _obj : objects) {
				WithCode obj = (WithCode) _obj;
				if (code.equals(obj.getCode()))
					return _obj;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	

}
