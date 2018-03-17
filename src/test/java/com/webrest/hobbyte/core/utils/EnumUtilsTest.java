/**
 * 
 */
package com.webrest.hobbyte.core.utils;

import org.junit.Assert;
import org.junit.Test;

import com.webrest.hobbyte.app.user.model.enums.ProfileVisibility;

/**
 * @author Emil Wojewódka
 *
 * @since 17 mar 2018
 */
public class EnumUtilsTest {

	@Test
	public void findByIdTest() {
		ProfileVisibility byId = ProfileVisibility.getById(ProfileVisibility.PUBLIC.getId());
		Assert.assertEquals(ProfileVisibility.PUBLIC, byId);
	}
	
	@Test
	public void findByIdNegativeTest() {
		ProfileVisibility byId = ProfileVisibility.getById(ProfileVisibility.PUBLIC.getId());
		Assert.assertNotEquals(ProfileVisibility.FOR_FRIENDS, byId);
	}
	
}
