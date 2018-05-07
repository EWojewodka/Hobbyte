package com.webrest.hobbyte.core.utils.collection;

import java.util.HashMap;
import java.util.Map;

@SuppressWarnings("unchecked")
public class ParameterContainer {

	private final Map<Object, Object> parameterMap = new HashMap<>();

	public <T> T getObject(Object key) {
		return getOrDefault(key, null);
	}

	public <T> T getOrDefault(Object key, Object defaultValue) {
		return (T) parameterMap.getOrDefault(key, defaultValue);
	}

	public void put(Object key, Object value) {
		parameterMap.put(key, value);
	}

	public boolean contains(Object key) {
		return parameterMap.containsKey(key);
	}

}
