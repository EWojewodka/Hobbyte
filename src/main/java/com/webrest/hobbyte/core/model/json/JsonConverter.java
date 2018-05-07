package com.webrest.hobbyte.core.model.json;

import java.lang.reflect.Field;
import java.util.Date;

import org.json.JSONObject;

import com.webrest.hobbyte.core.utils.ClassUtils;
import com.webrest.hobbyte.core.utils.DateUtils;

public class JsonConverter {

	public static JSONObject toJson(Object object) {
		JSONObject jsonObject = new JSONObject();
		if (object == null)
			return jsonObject;

		Class<? extends Object> clazz = object.getClass();
		// Get all fields if Class is annotated. Otherwise get only annotated fields.
		Field[] fields = clazz.isAnnotationPresent(AsJSON.class) ? clazz.getDeclaredFields()
				: ClassUtils.getAnnotatedFields(clazz, AsJSON.class);
		for (Field f : fields) {
			f.setAccessible(true);
			try {
				Object value = f.get(object);
				if (f.getType().isAssignableFrom(JSONable.class))
					jsonObject.put(f.getName(), ((JSONable) value).getAsJSON());
				else {
					boolean isAnnotated = f.isAnnotationPresent(AsJSON.class);
					Class<?> type = f.getType();
					// put default value if string.
					if (value == null && isAnnotated) {
						value = f.getAnnotation(AsJSON.class).defaultValue();
					} 
					//format date if it's Date
					else if (value != null && isAnnotated && type == Date.class) {
						value = DateUtils.formatDate((Date) value, f.getAnnotation(AsJSON.class).defaultDateFormat());
					}
					jsonObject.put(f.getName(), value);

				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return jsonObject;
	}

}
