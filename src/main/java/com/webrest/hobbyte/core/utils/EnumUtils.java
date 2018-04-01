package com.webrest.hobbyte.core.utils;

public class EnumUtils {

	/**
	 * Method allows to find {@link Enum} with specified code. Class must expaning a
	 * {@link WithId} class.</br>
	 * return null if there is no enum with specified id.
	 * 
	 * @param enumType
	 * @param id
	 * @return
	 */
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

	/**
	 * Method which allow to search enum with {@link WithCode#getCode()} in
	 * specified Class. </br>
	 * If code is {@code null} or empty return null. Also if there is no enum with
	 * specified code - return null.
	 * 
	 * @see #findById(Class, int)
	 * @param enumType
	 * @param code
	 * @return
	 */
	public static <T extends Enum<? extends WithCode>> T findByCode(Class<T> enumType, String code) {
		if (StringUtils.isEmpty(code))
			return null;

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
