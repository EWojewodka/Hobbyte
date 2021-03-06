/**
 * 
 */
package com.webrest.hobbyte.core.file.properties;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.webrest.hobbyte.core.platform.PlatformUtils;

public class PropertiesFacade {

	private static Map<String, PropertyService> buffer = new ConcurrentHashMap<>();

	/**
	 * Return properties service </br>
	 * 
	 * @param name
	 * @return
	 */
	public static PropertyService get(String name) {
		PropertyService service = buffer.get(name);
		if (service != null)
			return service;
		service = PlatformUtils.isRunningFromJar() ? new PropertyJarService(name) : new PropertyService(name);
		buffer.put(name, service);
		return service;
	}

}
