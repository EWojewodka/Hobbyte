package com.webrest.hobbyte.core.utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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
		List<T> result = findByCodes(enumType, code, null);
		return result.isEmpty() ? null : result.get(0);
	}

	@SuppressWarnings("unchecked")
	public static <T extends Enum<? extends WithCode>> List<T> findByCodes(Class<T> enumType, String concatedCodes,
			String splitter) {
		if (StringUtils.isEmpty(concatedCodes))
			return Collections.EMPTY_LIST;

		List<T> resultList = new ArrayList<>();

		// If splitter is null - we won't split every char. It means - if spliiter is
		// null - concatedCodes has only one code.
		String[] codes = splitter == null ? new String[] { concatedCodes } : concatedCodes.split(splitter);

		T[] objects = enumType.getEnumConstants();

		for (String code : codes) {
			for (T _obj : objects) {
				WithCode obj = (WithCode) _obj;
				if (code.equals(obj.getCode()))
					resultList.add(_obj);
			}
		}
		return resultList;
	}

}
