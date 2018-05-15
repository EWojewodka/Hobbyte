package com.webrest.hobbyte.core.cache;

import java.util.Properties;

import com.webrest.hobbyte.core.file.properties.PropertiesFacade;
import com.webrest.hobbyte.core.file.properties.PropertyService;
import com.webrest.hobbyte.core.utils.StringUtils;
import com.webrest.hobbyte.core.utils.functions.ExceptionStream;

public class CacheManagerFactory {

	private static ICacheManager cacheManager;

	private static PropertyService cacheProperties = PropertiesFacade.get("cache");
	
	public static ICacheManager getCacheManager() {
		if (cacheManager == null) {
			Properties props = cacheProperties.getProperties();
			String className = props.getProperty("cache.manager-class");

			// default cache manager.
			if (StringUtils.isEmpty(className))
				className = CacheManager.class.getName();
			
			final String _className = className;

			ExceptionStream.printOnFailure().call(() -> {
				Class<?> clazz = Class.forName(_className);
				cacheManager = (ICacheManager) clazz.newInstance();
			});
			
		}
		return cacheManager;
	}

}
