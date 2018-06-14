/**
 * 
 */
package com.webrest.hobbyte.core.utils.collection;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import com.webrest.hobbyte.core.utils.StringUtils;

/**
 * @author Emil Wojewódka
 *
 * @since 14 cze 2018
 */
public class PropertyStringService {

	public static final String DEFAULT_SPLITTER = ";";

	public static final String DEFAULT_KEY_VALUE_SPLIITER = "=";

	/**
	 * Convert {@link String} to {@link Map}. </br>
	 * For example: class={@link PropertyStringService};time=2018-05-20 </br>
	 * will be convert to map with two elements. </br>
	 * First: class -> {@link PropertyStringService} </br>
	 * Second: time -> 2018-05-20 </br>
	 * 
	 * @see #getMapFromString(String)
	 * @param propertyString
	 * @param splitter
	 *            - string of splitter map elements.
	 * @param keyValueSplitter
	 *            - string of splliter between key and value.
	 * @return
	 */
	public static Map<String, String> getMapFromString(String propertyString, String splitter,
			String keyValueSplitter) {
		if (StringUtils.isEmpty(propertyString))
			return new HashMap<>();

		String[] properties = propertyString.split(splitter);
		if (StringUtils.isEmpty(properties))
			return new HashMap<>();

		Map<String, String> resultMap = new HashMap<>();

		for (String property : properties) {
			String[] propertyArr = property.split(keyValueSplitter);
			resultMap.put(propertyArr[0], propertyArr[1]);
		}

		return resultMap;
	}

	/**
	 * @see #getMapFromString(String, String, String)
	 * @param propertyString
	 * @return
	 */
	public static Map<String, String> getMapFromString(String propertyString) {
		return getMapFromString(propertyString, DEFAULT_SPLITTER, DEFAULT_KEY_VALUE_SPLIITER);
	}

	public static String putKeyValue(String propertyString, String splitter, String keyValueSplitter, String key,
			String value) {

		if (!propertyString.endsWith(splitter))
			propertyString += splitter;

		propertyString += key + keyValueSplitter + value;

		return propertyString;
	}

	public static String putKeyValue(String propertyString, String key, String value) {
		return putKeyValue(propertyString, DEFAULT_SPLITTER, DEFAULT_KEY_VALUE_SPLIITER, key, value);
	}

	public static String mapToPropertyString(Map<String, String> map) {
		return mapToPropertyString(map, DEFAULT_SPLITTER, DEFAULT_KEY_VALUE_SPLIITER);
	}

	public static String mapToPropertyString(Map<String, String> map, String splitter, String keyValueSplitter) {
		if (map == null || map.isEmpty())
			return "";

		if (StringUtils.isEmpty(splitter))
			splitter = DEFAULT_SPLITTER;

		if (StringUtils.isEmpty(keyValueSplitter))
			keyValueSplitter = DEFAULT_KEY_VALUE_SPLIITER;

		StringBuilder sb = new StringBuilder();
		for (Entry<String, String> entry : map.entrySet()) {
			sb.append(entry.getKey());
			sb.append(keyValueSplitter);
			sb.append(entry.getValue());
			sb.append(splitter);
		}

		sb.setLength(sb.length() - splitter.length());

		return sb.toString();
	}

}
