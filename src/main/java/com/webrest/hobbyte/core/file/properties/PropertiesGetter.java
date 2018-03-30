/**
 * 
 */
package com.webrest.hobbyte.core.file.properties;

import java.util.Properties;

import com.webrest.hobbyte.core.utils.StringUtils;

/**
 * Helper class for getting values from {@link Properties}. In base this
 * {@link PropertiesGetter} use {@link StringUtils} value method as a
 * {@link StringUtils#getAsBoolean(Object)}, etc.
 * 
 * @author Emil Wojewódka
 *
 * @since 21 mar 2018
 */
public class PropertiesGetter {

	private Properties properties;

	public PropertiesGetter(PropertyService propertyService) {
		this(propertyService.getProperties());
	}

	public PropertiesGetter(Properties properties) {
		if (properties == null)
			throw new IllegalArgumentException("Cannot init PropertiesGetter for nullable properties");
		this.properties = properties;
	}

	/**
	 * Return false if not exits.
	 * 
	 * @param name
	 * @return
	 */
	public boolean getAsBoolean(String name) {
		return getAsBoolean(name, false);
	}

	public boolean getAsBoolean(String name, boolean defaultValue) {
		return StringUtils.getAsBoolean(properties.getProperty(name), defaultValue);
	}

	/**
	 * Return 0 if not exists.
	 * 
	 * @param name
	 * @return
	 */
	public int getAsInt(String name) {
		return StringUtils.getAsInt(properties.getProperty(name));
	}

	/**
	 * Can return null.
	 * 
	 * @param name
	 * @return
	 */
	public String getAsString(String name) {
		return properties.getProperty(name);
	}

	/**
	 * Return source.
	 * 
	 * @return
	 */
	public Properties getProperties() {
		return properties;
	}

}
