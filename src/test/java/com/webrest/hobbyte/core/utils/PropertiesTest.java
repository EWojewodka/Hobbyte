/**
 * 
 */
package com.webrest.hobbyte.core.utils;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import com.webrest.hobbyte.core.file.properties.EnvironmentProperties;
import com.webrest.hobbyte.core.platform.PlatformInfo;

/**
 * @author Emil Wojewódka
 *
 * @since 30 mar 2018
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class PropertiesTest {

	@Autowired
	private PlatformInfo platformInfo;
	
	@Test
	public void testProperties() {
		EnvironmentProperties properties = platformInfo.getUsingProperties();
		Assert.assertTrue(properties.isTest());
	}
	
}
