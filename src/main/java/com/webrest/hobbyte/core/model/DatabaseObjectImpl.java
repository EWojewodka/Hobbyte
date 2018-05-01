/**
 * 
 */
package com.webrest.hobbyte.core.model;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

import javax.persistence.Transient;

import com.webrest.hobbyte.core.dao.GenericDao;

/**
 * @author Emil Wojewódka
 *
 * @since 24 mar 2018
 */
public abstract class DatabaseObjectImpl implements DatabaseObject {

	@Transient
	private Map<Object, Object> parameters = new HashMap<>();

	/**
	 * Put parameter to {@link DatabaseObjectImpl}. If you want to get it, let use
	 * {@link #getParameter(Object)} and cast it to type which you need. </br>
	 * <i><b>Note that:</b> that parameters won't be stored in database. It's only for
	 * transport some data and store it here!</i>
	 * 
	 * @see #getParameter(Object)
	 * @param key
	 * @param value
	 */
	public void putParameter(Object key, Object value) {
		parameters.put(key, value);
	}

	/**
	 * Return object from {@link DatabaseObjectImpl} parameters which can be cast of
	 * any of type.
	 * 
	 * @see #putParameter(Object, Object)
	 * @param object
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public <T> T getParameter(Object object) {
		return (T) parameters.get(object);
	}
	
	public Map<Object, Object> getParameters(){
		return parameters;
	}

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

		Field[] fields = clazz.getDeclaredFields();
		for (int i = 0; i < fields.length; i++) {
			Field field = fields[i];
			field.setAccessible(true);
			try {
				Object value = field.get(this);
				String valueStr = null;
				if (value != null && value instanceof DatabaseObjectImpl) {
					// Recursive break
					if (value.equals(parent))
						valueStr = "this";
					else
						valueStr = ((DatabaseObjectImpl) value).toString(this);
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

	@Override
	public void save(GenericDao<DatabaseObject> dao) {
		dao.save(this);
	}

	public Object getProperty(String name) {
		try {
			Field field = getClass().getDeclaredField(name);
			field.setAccessible(true);
			return field.get(this);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
