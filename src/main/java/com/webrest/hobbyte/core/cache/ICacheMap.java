package com.webrest.hobbyte.core.cache;

import java.util.Date;
import java.util.Map;

public interface ICacheMap<K,V> {

	String getName();
	
	V get(K key);
	
	void put(K key, V value);
	
	Map<K,V> getMap();
	
	void clear();
	
	Date getLastInit();

	int getSize();
}
