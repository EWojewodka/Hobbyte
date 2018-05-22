/**
 * 
 */
package com.webrest.hobbyte.core.utils;

import java.util.Arrays;
import java.util.Collection;

import org.json.JSONArray;

import com.webrest.hobbyte.core.model.json.JsonConverter;

/**
 * @author Emil Wojewódka
 *
 * @since 21 maj 2018
 */
public class JsonUtils {

	public static JSONArray toJsonObject(Collection<?> collection) {
		JSONArray array = new JSONArray();
		collection.forEach(p -> array.put(JsonConverter.toJson(p)));
		return array;
	}

	public static JSONArray toJsonbObject(Object[] objects) {
		return toJsonObject(Arrays.asList(objects));
	}

}
