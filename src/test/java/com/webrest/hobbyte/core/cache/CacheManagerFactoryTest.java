package com.webrest.hobbyte.core.cache;

import org.junit.Test;

import com.webrest.hobbyte.core.cache.CacheManagerFactory;

import org.junit.Assert;

public class CacheManagerFactoryTest {

	@Test
	public void getCacheManagerTest() {
		Assert.assertNotNull(CacheManagerFactory.getCacheManager());
	}
	
}
