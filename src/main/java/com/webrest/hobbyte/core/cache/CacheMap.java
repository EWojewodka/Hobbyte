package com.webrest.hobbyte.core.cache;

import java.util.Date;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class CacheMap<K, V> implements ICacheMap<K, V> {

	private String name;

	private String description;

	private final Map<K, V> map;
	
	private Date lastInit = new Date();

	public CacheMap(String mapName) {
		this(mapName, "");
	}

	public CacheMap(String mapName, String description) {
		this.name = mapName;
		this.description = description;
		this.map = new ConcurrentHashMap<>();
		CacheManagerFactory.getCacheManager().registerCacheMap(this);
	}

	public String getName() {
		return name;
	}

	public String getDescription() {
		return description;
	}

	public Map<K, V> getMap() {
		return map;
	}

	public void clear() {
		map.clear();
		lastInit = new Date();
	}

	public boolean containsKey(K obj) {
		return map.containsKey(obj);
	}

	public void put(K key, V value) {
		map.put(key, value);
	}

	public V get(K key) {
		return map.get(key);
	}
	
	@Override
	public Date getLastInit() {
		return lastInit;
	}
	
	public int getSize() {
		return map.size();
	}
}
