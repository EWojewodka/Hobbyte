package com.webrest.hobbyte.core.cache;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.webrest.hobbyte.core.cache.CacheMap;

public class CacheMapTest {

	private static final String CACHE_MAP_NAME = "test_cache";
	
	private CacheMap<Object, Object> testCacheMap;
	
	@Before
	public void initialize() {
		testCacheMap = new CacheMap<>(CACHE_MAP_NAME);
	}
	
	@Test
	public void getNameTest() {
		testCacheMap.getName().equals(CACHE_MAP_NAME);
	}
	
	@Test
	public void getAndPutTest() {
		Object o = new Object();
		testCacheMap.put(o, o);
		Assert.assertSame(o, testCacheMap.get(o));
	}
	
	@Test
	public void clearTest() {
		Object o = new Object();
		Object o2 = new Object();
		
		testCacheMap.put(o, o);
		testCacheMap.put(o2, o2);
		Assert.assertFalse(testCacheMap.getMap().isEmpty());
		testCacheMap.clear();
		Assert.assertTrue(testCacheMap.getMap().isEmpty());
		
	}
	
}
