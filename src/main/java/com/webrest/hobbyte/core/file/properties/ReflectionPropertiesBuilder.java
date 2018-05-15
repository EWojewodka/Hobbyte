/**
 * 
 */
package com.webrest.hobbyte.core.file.properties;

import java.lang.reflect.Field;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;

import com.webrest.hobbyte.core.utils.Asserts;
import com.webrest.hobbyte.core.utils.ClassUtils;
import com.webrest.hobbyte.core.utils.functions.ExceptionStream;

/**
 * Class with static methods for inject values from {@link Value} fields to map.
 * 
 * @author Emil Wojewódka
 *
 * @since 30 mar 2018
 */
public class ReflectionPropertiesBuilder {

	private static final String NO_ANNOTATED_FIELDS_ERROR = "Cannot use %s #build() for %s because there are no annotated fields.";

	/**
	 * Inject {@link Field}s annotated by {@link Value} to {@link Map}, where </br>
	 * <b><i>key -> {@link #getPropertyName(Field)} </br>
	 * value -> {@link Field#get(Map passed as parameter)} </i></b>
	 * 
	 * @param map
	 */
	@SuppressWarnings("rawtypes")
	static void build(Map<Object, Object> map) {
		Class<? extends Map> propertyClass = map.getClass();
		Field[] propertyValues = ClassUtils.getAnnotatedFields(propertyClass, Value.class);

		Asserts.notEmpty(propertyValues, String.format(NO_ANNOTATED_FIELDS_ERROR,
				ReflectionPropertiesBuilder.class.getName(), propertyClass.getName()));

		for (Field f : propertyValues) {
			ExceptionStream.printOnFailure().call(() -> {
				Object propertyValue = f.get(map);
				map.put(getPropertyName(f), propertyValue);
			});
		}
	}

	/**
	 * Remove expression chars ${[...]} from property name.
	 * 
	 * @param f
	 * @return
	 */
	private static String getPropertyName(Field f) {
		return f.getAnnotation(Value.class).value().replaceAll("^\\$\\{|[\\}]?", "");
	}
}
