package com.webrest.hobbyte.core.cache;

import org.junit.Assert;
import org.junit.Test;

import com.webrest.hobbyte.core.cache.CacheManagerFactory;
import com.webrest.hobbyte.core.cache.CacheMap;
import com.webrest.hobbyte.core.cache.ICacheManager;

public class CacheManagerTest {

	private static final String TEST_MAP_NAME = "test_name";
	
	private static final String TEST_MAP_NAME2 = "test_name2";

	private static final Object TEST_OBJECT = new Object();
	
	@Test
	public void registerCacheMapAndGetMapTest() {
		ICacheManager manager = CacheManagerFactory.getCacheManager();
		new CacheMap<String, String>(TEST_MAP_NAME);
		Assert.assertNotNull(manager.getCacheMap(TEST_MAP_NAME));
	}
	
	@Test
	public void resetAllTest() {
		ICacheManager manager = CacheManagerFactory.getCacheManager();
		CacheMap<Object, Object> map1 = new CacheMap<>(TEST_MAP_NAME);
		CacheMap<Object, Object> map2 = new CacheMap<>(TEST_MAP_NAME2);
		map1.put("test", TEST_OBJECT);
		map1.put("test2", TEST_OBJECT);
		map2.put("test", TEST_OBJECT);
		map2.put("test2", TEST_OBJECT);
		Assert.assertFalse(map1.getMap().isEmpty());
		Assert.assertFalse(map2.getMap().isEmpty());
		manager.resetAll();
		Assert.assertTrue(map1.getMap().isEmpty());
		Assert.assertTrue(map2.getMap().isEmpty());
	}
	
}

