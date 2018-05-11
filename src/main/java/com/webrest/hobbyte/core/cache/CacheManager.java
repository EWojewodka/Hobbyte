package com.webrest.hobbyte.core.cache;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;

import com.webrest.hobbyte.core.logger.LoggerFactory;
import com.webrest.hobbyte.core.utils.Asserts;

public class CacheManager implements ICacheManager {

	private static final Map<String, ICacheMap<?, ?>> GLOBAL_CACHE = new ConcurrentHashMap<>();

	private static final Logger LOGGER = LoggerFactory.getLogger();

	@Override
	public void resetAll() {
		Collection<ICacheMap<?, ?>> values = GLOBAL_CACHE.values();
		values.forEach(cacheMap -> {
			LOGGER.info("Clear {} cache map.", cacheMap.getName());
			cacheMap.clear();
		});
	}

	@Override
	public void registerCacheMap(ICacheMap<?, ?> cacheMap) {
		Asserts.notNull(cacheMap, "Cannot register nullable cache map.");
		GLOBAL_CACHE.put(cacheMap.getName(), cacheMap);
	}

	@SuppressWarnings("unchecked")
	@Override
	public <K, V> ICacheMap<K, V> getCacheMap(String name) {
		ICacheMap<?, ?> result = GLOBAL_CACHE.get(name);
		return result == null ? new CacheMap<>("null") : (CacheMap<K, V>) result;
	}

	/**
	 * Return map with all {@link ICacheMap}. It's copy - so please don't try modify
	 * this.
	 */
	@Override
	public Map<String, ICacheMap<?, ?>> getAll() {
		return new HashMap<>(GLOBAL_CACHE);
	}

}
