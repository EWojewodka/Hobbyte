/**
 * 
 */
package com.webrest.hobbyte.core.model;

import java.lang.reflect.Field;
import java.util.Collection;

import org.json.JSONObject;

import com.webrest.hobbyte.core.model.json.JSONable;
import com.webrest.hobbyte.core.model.json.JsonConverter;
import com.webrest.hobbyte.core.utils.functions.ExceptionStream;

/**
 * @author Emil Wojewódka
 *
 * @since 24 mar 2018
 */
public abstract class DatabaseObjectImpl implements DatabaseObject, JSONable {

	/** {@inheritDoc}} */
	public boolean isNew() {
		return getId() <= 0;
	}

	/**
	 * <i>Note: Look also at {@link #toString(DatabaseObjectImpl)} </i></br>
	 * {@inheritDoc}}
	 */
	@Override
	public String toString() {
		return toString(null);
	}

	/**
	 * Recursive toString for pretty print an entity in logs and console. For
	 * example: </br>
	 * <code>
	 * ExtranetUser </br>
	 * id -> 68</br>
	 * createdAt -> Sat Mar 24 16:01:41 CET 2018</br>
	 * login -> 123SDF!@#FDS</br>
	 * email -> dsafnasdf432432+dsa@gmaildcscc.com</br>
	 * password -> 098dsadcxzc4214</br>
	 * name -> null</br>
	 * lastname -> null</br>
	 * status -> 0</br>
	 * agreement -> 0</br>
	 * born -> null</br>
	 * phoneNumber -> null</br>
	 * gender -> 1</br>
	 * newsletter -> 0</br>
	 * userPolicy -> <b>(sub entity!)</b></br>
	 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;userId -> 0</br>
	 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;profileVisibilty -> 1</br>
	 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;user -> <b>this</b></br>
	 * </code> </br>
	 * <i>Note: <b>>>this<<</b> is when object value is a parent. It's a recursive
	 * breaker.</i>
	 * 
	 * @param parent
	 * @return
	 */
	protected String toString(DatabaseObjectImpl parent) {
		Class<? extends DatabaseObjectImpl> clazz = getClass();
		StringBuilder sb = new StringBuilder();

		// if its root object let's write class name.
		if (parent == null)
			sb.append(clazz.getSimpleName());

		// Every new object should be start in new line.
		sb.append("\n");

		// get subelement fields and iterate them
		Field[] fields = clazz.getDeclaredFields();
		for (int i = 0; i < fields.length; i++) {
			Field field = fields[i];
			field.setAccessible(true);
			try {
				Object value = field.get(this);
				String valueStr = null;
				if (value != null && value instanceof DatabaseObjectImpl) {
					DatabaseObjectImpl dbo = (DatabaseObjectImpl) value;
					// Recursive break
					if (dbo.isSameDBO(parent))
						valueStr = "this";
					else
						valueStr = ((DatabaseObjectImpl) value).toString(this);
				} else if (value != null && value instanceof Collection) {
					Collection<?> persistentBag = (Collection<?>) value;
					valueStr = "size -> " + persistentBag.size();
				} else {
					valueStr = String.valueOf(value);
				}
				// If its children object - let's tab start.
				if (parent != null)
					sb.append("\t");
				sb.append(field.getName() + " -> " + valueStr + "\n");
			} catch (IllegalArgumentException | IllegalAccessException e) {
				e.printStackTrace();
			}
		}
		return sb.toString();
	}

	public boolean isSameDBO(DatabaseObject dbo) {
		if (dbo == null)
			return false;
		if (!dbo.getClass().equals(getClass()))
			return false;
		return dbo.getId() == getId();
	}

	public Object getProperty(String name) {
		return ExceptionStream.printOnFailure().call(() -> {
			Field field = getClass().getDeclaredField(name);
			field.setAccessible(true);
			return field.get(this);
		}).get();
	}

	@Override
	public JSONObject getAsJSON() {
		return JsonConverter.toJson(this);
	}

	@Override
	public String getJSONAsString() {
		return getAsJSON().toString();
	}

	public DatabaseObjectImpl cloneDBO() {
		return (DatabaseObjectImpl) ExceptionStream.printOnFailure().call(() -> {
			DatabaseObjectImpl clone = (DatabaseObjectImpl) clone();
			clone.setId(0);
			return clone;
		}).get();
	}

}