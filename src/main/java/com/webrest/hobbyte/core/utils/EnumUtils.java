package com.webrest.hobbyte.core.utils;

import java.lang.reflect.Field;

public class EnumUtils {
	
	@SuppressWarnings("unchecked")
	public static <T extends Enum<T>> T findByField(Class<T> enumType, String fieldName, String value) {

		try {
			Object[] objects = enumType.getEnumConstants();
			for (Object obj : objects) {
				Field keyField = obj.getClass().getDeclaredField(fieldName);
				keyField.setAccessible(true);
				if (value.equals(keyField.get(obj))) {
					return (T) obj;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static <T extends Enum<T>> T findById(Class<T> enumType, int id) {
		return findByField(enumType, "id", String.valueOf(id));
	}
	
}
