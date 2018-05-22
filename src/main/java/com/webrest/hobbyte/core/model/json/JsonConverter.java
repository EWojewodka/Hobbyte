package com.webrest.hobbyte.core.model.json;

import java.lang.reflect.Field;
import java.lang.reflect.Member;
import java.lang.reflect.Method;
import java.util.Date;

import org.json.JSONException;
import org.json.JSONObject;

import com.webrest.hobbyte.core.utils.ClassUtils;
import com.webrest.hobbyte.core.utils.DateUtils;
import com.webrest.hobbyte.core.utils.StringUtils;
import com.webrest.hobbyte.core.utils.functions.ExceptionStream;

public class JsonConverter {

	public static JSONObject toJson(Object sourceObject) {
		JSONObject jsonObject = new JSONObject();
		if (sourceObject == null)
			return jsonObject;

		Class<?> clazz = sourceObject.getClass();
		processFields(sourceObject, jsonObject, clazz);
		processMethods(sourceObject, jsonObject, clazz);
		return jsonObject;
	}

	/**
	 * Add annotated {@link Field} of sourceObject to resultObject. 
	 * If clazz is annotated by {@link AsJSON} process all {@link Class} fields.
	 * 
	 * @param sourceObject - object which should be convert to json 
	 * @param resultObject - {@link JSONObject} which'll be return
	 * @param clazz - {@link Class} of sourceObject
	 */
	private static void processFields(Object sourceObject, JSONObject resultObject, Class<?> clazz) {
		// Get all fields if Class is annotated. Otherwise get only annotated fields.
		Field[] fields = clazz.isAnnotationPresent(AsJSON.class) ? clazz.getDeclaredFields()
				: ClassUtils.getAnnotatedFields(clazz, AsJSON.class);
		for (Field f : fields) {
			AsJSON annotation = f.getAnnotation(AsJSON.class);
			Object value = ExceptionStream.printOnFailure().call(() -> {return f.get(sourceObject);}).get();
			processSingle(resultObject, annotation, value, f);	
		}
	}

	/**
	 * Add annotated {@link Method} of sourceObject to resultObject. 
	 * 
	 * @param sourceObject - object which should be convert to json 
	 * @param resultObject - {@link JSONObject} which'll be return
	 * @param clazz - {@link Class} of sourceObject
	 */
	private static void processMethods(Object sourceObject, JSONObject jsonObject, Class<?> clazz) {
		Method[] methods = ClassUtils.getAnnotatedMethods(clazz, AsJSON.class);
		for (Method m : methods) {
			AsJSON annotation = m.getAnnotation(AsJSON.class);
				Object value = ExceptionStream.printOnFailure().call(() -> {return m.invoke(sourceObject);});
				processSingle(jsonObject, annotation, value, m);
		}
	}

	private static void processSingle(JSONObject jsonObject, AsJSON annotation, Object object, Member member) {
		// put default value if string.
		if (object == null)
			object = annotation.defaultValue();

		try {
			Class<? extends Object> returnType = object.getClass();
			String jsonPropertyName = StringUtils.isEmpty(annotation.jsonName()) ? member.getName()
					: annotation.jsonName();

			if (object instanceof JSONable) {
				jsonObject.put(jsonPropertyName, ((JSONable) object).getAsJSON());
			} else {
				// format date if it's Date
				if (returnType == Date.class) {
					object = DateUtils.formatDate((Date) object, annotation.defaultDateFormat());
				}

				jsonObject.put(jsonPropertyName, object);

			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

}
