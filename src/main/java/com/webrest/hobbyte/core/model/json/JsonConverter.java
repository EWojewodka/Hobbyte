package com.webrest.hobbyte.core.model.json;

import java.lang.reflect.Field;

import org.json.JSONObject;

import com.webrest.hobbyte.core.utils.ClassUtils;

public class JsonConverter {

	public static JSONObject toJson(Object object) {
		JSONObject jsonObject = new JSONObject();
		if(object == null)
			return jsonObject;
		
		Class<? extends Object> clazz = object.getClass();
		//Get all fields if Class is annotated. Otherwise get only annotated fields.
		Field[] fields = clazz.isAnnotationPresent(AsJSON.class) ? clazz.getDeclaredFields()
				: ClassUtils.getAnnotatedFields(clazz, AsJSON.class);
		
		for (Field f : fields) {
			f.setAccessible(true);
			try {
				jsonObject.put(f.getName(), f.get(object));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return jsonObject;
	}

}
