package com.webrest.hobbyte.core.cache;

import java.util.Map;

public interface ICacheManager {

	void registerCacheMap(ICacheMap<?, ?> cacheMape);
	
	<K,V> ICacheMap<K,V> getCacheMap(String name);
	
	void resetAll();
	
	Map<String, ICacheMap<?, ?>> getAll();
}
